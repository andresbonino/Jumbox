package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;

import GUI.AgregarProducto;
import GUI.EditarProducto;
import jumbox.Categorias;
import jumbox.OpcionesSucursal;
import jumbox.Productos;
import repository.ProductoRepository;

public class ControllerProducto<T extends Productos> implements ProductoRepository {
	
    private static Connection con = Conexion.getInstance().getConnection();

    
    @Override
    public void agregarProducto(Productos producto) {
        try {
            PreparedStatement statement = con.prepareStatement(
            	"INSERT INTO producto (nombre, precio, stock, fk_categoria) VALUES (?, ?, ?, ?)",
            	Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getStock());
            statement.setInt(4, producto.getCategoria());

            int filas = statement.executeUpdate();
            if (filas > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    producto.setIdProducto(idGenerado);
                    System.out.println("Producto agregado correctamente");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean existeProductoConNombre(String nombre) {
        try {
            String sql = "SELECT COUNT(*) FROM producto WHERE LOWER(nombre) = LOWER(?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void eliminarProducto(Productos producto) {
        try {
            // ELIMINAR PEDIDO DE DETALLE_PEDIDO_REPOSICION
            PreparedStatement ps0 = con.prepareStatement(
                "DELETE FROM detalle_pedido_reposicion WHERE fk_producto = ?"
            );
            ps0.setInt(1, producto.getIdProducto());
            ps0.executeUpdate();

            // ELIMINAR PRODUCTO DE ALMACEN_SUCURSAL
            PreparedStatement ps1 = con.prepareStatement(
                "DELETE FROM almacen_sucursal WHERE fk_producto = ?"
            );
            ps1.setInt(1, producto.getIdProducto());
            ps1.executeUpdate();

            // ELIMINAR PRODUCTO DE LA TABLA PRODUCTO
            PreparedStatement ps2 = con.prepareStatement(
                "DELETE FROM producto WHERE id_producto = ?"
            );
            ps2.setInt(1, producto.getIdProducto());
            int filas = ps2.executeUpdate();

            if (filas > 0) {
                System.out.println("Producto eliminado correctamente");
            } else {
                System.out.println("No se encontró el producto para eliminar");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public static LinkedList<Productos> mostrarProducto2() {
        LinkedList<Productos> producto = new LinkedList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM producto");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                Double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                int categoria = rs.getInt("fk_categoria");
                Productos p = new Productos(nombre, precio, stock, categoria);
                p.setIdProducto(id);
                producto.add(p);
                        
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producto;
    }
    
    @Override
    public LinkedList<Productos> mostrarProducto() {
        LinkedList<Productos> producto = new LinkedList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM producto");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                Double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                int categoria = rs.getInt("fk_categoria");
                Productos p = new Productos(nombre, precio, stock, categoria);
                p.setIdProducto(id);
                producto.add(p);
                        
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producto;
    }
    
    @Override
    public void editarProducto(Productos producto) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                "UPDATE producto SET precio = ?, stock = ?, fk_categoria = ? WHERE id_producto = ?"
            );
            stmt.setDouble(1, producto.getPrecio());
            stmt.setInt(2, producto.getStock());
            stmt.setInt(3, producto.getCategoria());
            stmt.setInt(4, producto.getIdProducto());

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Producto actualizado correctamente.");
            } else {
                System.out.println("No se encontró el producto para actualizar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	@Override
	public void verStock() {
		    LinkedList<Productos> productos = mostrarProducto();

		    if (productos.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "No hay productos cargados.");
		    } else {
		    	String[] nombres = new String[productos.size()];
		    	for (int i = 0; i < productos.size(); i++) {
		    	    nombres[i] = productos.get(i).getNombre();
		    	}
		        String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione un producto:", "Ver Stock", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);

		        if (seleccion != null) {
		            for (Productos prod : productos) {
		                if (prod.getNombre().equals(seleccion)) {
		                    JOptionPane.showMessageDialog(null, "Producto: " + prod.getNombre() + "\nStock disponible: " + prod.getStock());
		                    break;
		                }
		            }
		        }
		    }
	}

	@Override
	public void editar(Productos seleccionado) {
		if (seleccionado == null) {
			JOptionPane.showMessageDialog(null, "No hay producto seleccionado.");
			return;
		}
		EditarProducto editar = new EditarProducto(seleccionado);
		editar.setVisible(true);
    
	}

	
	public void procesarPedidosPendientes() {
	    try {
	        PreparedStatement stmt = con.prepareStatement(
	            "SELECT p.id_pedido_reposicion, p.fk_sucursal, d.fk_producto, d.cantidad, pr.nombre, pr.stock " +
	            "FROM pedido_reposicion p " +
	            "JOIN detalle_pedido_reposicion d ON p.id_pedido_reposicion = d.fk_pedido_reposicion " +
	            "JOIN producto pr ON pr.id_producto = d.fk_producto " +
	            "ORDER BY p.id_pedido_reposicion"
	        );

	        ResultSet rs = stmt.executeQuery();
	        StringBuilder sb = new StringBuilder("Pedidos pendientes:\n\n");
	        List<Integer> idsPedidos = new LinkedList<>();

	        while (rs.next()) {
	            int idPedido = rs.getInt("id_pedido_reposicion");
	            int idSucursal = rs.getInt("fk_sucursal");
	            String nombreProducto = rs.getString("nombre");
	            int cantidad = rs.getInt("cantidad");
	            int stockActual = rs.getInt("stock");

	            sb.append("Pedido #").append(idPedido)
	              .append(" | Sucursal ").append(idSucursal)
	              .append(" | Producto: ").append(nombreProducto)
	              .append(" x").append(cantidad)
	              .append(" (Stock actual: ").append(stockActual).append(")\n");

	            if (!idsPedidos.contains(idPedido)) {
	                idsPedidos.add(idPedido);
	            }
	        }

	        if (idsPedidos.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No hay pedidos pendientes.");
	            return;
	        }

	        int opcion = JOptionPane.showOptionDialog(null, sb.toString() + "\n¿Desea armar un envío?",
	                "Pedidos Pendientes", JOptionPane.YES_NO_OPTION,
	                JOptionPane.QUESTION_MESSAGE, null,
	                new Object[]{"Armar Envío", "Salir"}, "Armar Envío");

	        if (opcion == JOptionPane.YES_OPTION) {
	            Object seleccion = JOptionPane.showInputDialog(null,
	                    "Seleccione el número de pedido a procesar:", "Armar Envío",
	                    JOptionPane.QUESTION_MESSAGE, null,
	                    idsPedidos.toArray(), idsPedidos.get(0));

	            if (seleccion != null) {
	                int idSeleccionado = (Integer) seleccion;
	                armarEnvioASucursal(idSeleccionado);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al procesar pedidos.");
	    }
	}

	public void armarEnvioASucursal(int idPedido) {
	    try {
	        // OBTENER LOS PRODUCTOS Y LAS CANTIDADES
	        PreparedStatement stmt = con.prepareStatement(
	            "SELECT d.fk_producto, d.cantidad, p.fk_sucursal " +
	            "FROM detalle_pedido_reposicion d " +
	            "JOIN pedido_reposicion p ON d.fk_pedido_reposicion = p.id_pedido_reposicion " +
	            "WHERE d.fk_pedido_reposicion = ?"
	        );
	        stmt.setInt(1, idPedido);
	        ResultSet rs = stmt.executeQuery();

	        List<Integer> productos = new ArrayList<>();
	        List<Integer> cantidades = new ArrayList<>();
	        List<Integer> stocks = new ArrayList<>();
	        int idSucursal = -1;

	        boolean stockInsuficiente = false;
	        StringBuilder mensajeStock = new StringBuilder();

	        // VERIFICAR STOCK DE PRODUCTOS
	        while (rs.next()) {
	            int idProducto = rs.getInt("fk_producto");
	            int cantidad = rs.getInt("cantidad");
	            idSucursal = rs.getInt("fk_sucursal");

	            // CONSULTAR STOCK EN EL DEPOSITO
	            PreparedStatement stockStmt = con.prepareStatement(
	            		"SELECT nombre, stock FROM producto WHERE id_producto = ?"
	            );
	            stockStmt.setInt(1, idProducto);
	            ResultSet rsStock = stockStmt.executeQuery();
	            


	            if (rsStock.next()) {
		            String nombreProducto = rsStock.getString("nombre");
	                int stockActual = rsStock.getInt("stock");

	                if (stockActual < cantidad) {
	                    stockInsuficiente = true;
	                    mensajeStock.append("Pedido: ").append(idPedido)
	                    .append(" | Producto: ").append(nombreProducto)
	                    .append(" | Cantidad: ").append(cantidad)
	                    .append(" | Stock: ").append(stockActual).append("\n");

	                } else {
	                    productos.add(idProducto);
	                    cantidades.add(cantidad);
	                    stocks.add(stockActual);
	                }
	            }
	        }

	        // SI NO HAY STOCK SUFICIENTE NO SE PROCESA EL PEDIDO
	        if (stockInsuficiente) {
	            JOptionPane.showMessageDialog(null,
	                "No se pudo enviar el pedido porque hay productos sin stock suficiente:\n\n" +
	                mensajeStock.toString());
	            return;
	        }

	        // SI HAY STOCK SUFICIENTE SE PROCESA EL PEDIDO
	        for (int i = 0; i < productos.size(); i++) {
	            int idProducto = productos.get(i);
	            int cantidad = cantidades.get(i);

	         // VER SI YA EXISTE EL PRODUCTO EN EL ALMACEN DE LA SUCURSAL
	            PreparedStatement checkStmt = con.prepareStatement(
	                "SELECT cantidad FROM almacen_sucursal WHERE fk_sucursal = ? AND fk_producto = ?"
	            );
	            checkStmt.setInt(1, idSucursal);
	            checkStmt.setInt(2, idProducto);
	            ResultSet rsCheck = checkStmt.executeQuery();

	            if (rsCheck.next()) {
	                // SI YA EXISTE, SE ACTUALIZA EL STOCK
	                int cantidadActual = rsCheck.getInt("cantidad");
	                int nuevaCantidad = cantidadActual + cantidad;

	                PreparedStatement updateStmt = con.prepareStatement(
	                    "UPDATE almacen_sucursal SET cantidad = ? WHERE fk_sucursal = ? AND fk_producto = ?"
	                );
	                updateStmt.setInt(1, nuevaCantidad);
	                updateStmt.setInt(2, idSucursal);
	                updateStmt.setInt(3, idProducto);
	                updateStmt.executeUpdate();
	            } else {
	                // SI NO EXISTE SE CREA UNO NUEVO
	                PreparedStatement insertStmt = con.prepareStatement(
	                    "INSERT INTO almacen_sucursal (fk_sucursal, fk_producto, cantidad) VALUES (?, ?, ?)"
	                );
	                insertStmt.setInt(1, idSucursal);
	                insertStmt.setInt(2, idProducto);
	                insertStmt.setInt(3, cantidad);
	                insertStmt.executeUpdate();
	            }


	            // DESCONTAR STOCK DEL DEPOSITO
	            PreparedStatement updateStock = con.prepareStatement(
	                "UPDATE producto SET stock = stock - ? WHERE id_producto = ?"
	            );
	            updateStock.setInt(1, cantidad);
	            updateStock.setInt(2, idProducto);
	            updateStock.executeUpdate();
	        }

	        // BORRAR PEDIDO
	        PreparedStatement deleteDetalles = con.prepareStatement(
	            "DELETE FROM detalle_pedido_reposicion WHERE fk_pedido_reposicion = ?"
	        );
	        deleteDetalles.setInt(1, idPedido);
	        deleteDetalles.executeUpdate();

	        PreparedStatement deletePedido = con.prepareStatement(
	            "DELETE FROM pedido_reposicion WHERE id_pedido_reposicion = ?"
	        );
	        deletePedido.setInt(1, idPedido);
	        deletePedido.executeUpdate();

	        JOptionPane.showMessageDialog(null, "Envío realizado correctamente.");

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al preparar el envío.");
	    }
	}





}
