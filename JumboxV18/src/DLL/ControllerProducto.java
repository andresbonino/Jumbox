package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;

import jumbox.Categorias;
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
               
                Productos p = new Productos(nombre, precio, stock);
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
                "UPDATE producto SET precio = ?, stock = ?, fk_categoria = ? WHERE nombre = ?"
            );
            stmt.setDouble(1, producto.getPrecio());
            stmt.setInt(2, producto.getStock());
            stmt.setInt(3, producto.getCategoria());
            stmt.setString(4, producto.getNombre());

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
	public void editar() {
		LinkedList<Productos> productos = mostrarProducto();
		if (productos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay productos cargados.");
		} else {
			String[] nombres = new String[productos.size()];
			for (int i = 0; i < productos.size(); i++) {
				nombres[i] = productos.get(i).getNombre();
			}

			String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione un producto para editar:", "Editar Producto", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);

			if (seleccion != null) {
				Productos seleccionado = null;
				for (Productos prod : productos) {
					if (prod.getNombre().equals(seleccion)) {
						seleccionado = prod;
						break;
					}
				}

				if (seleccionado != null) {
					String nuevoPrecio, nuevoStock;
					Double nuevoP = null;
					int nuevoS = 0;
					do {
						nuevoPrecio = JOptionPane.showInputDialog("Nuevo precio:", seleccionado.getPrecio());
						if (!nuevoPrecio.isEmpty()) {
							nuevoP = Double.parseDouble(nuevoPrecio);
						}
					} while (nuevoPrecio.isEmpty() || nuevoP<=0);
		            
					do {
		            	nuevoStock = JOptionPane.showInputDialog("Nuevo stock:", seleccionado.getStock());
						if (!nuevoStock.isEmpty()) {
							nuevoS = Integer.parseInt(nuevoStock);
						}
					} while (nuevoStock.isEmpty() || nuevoS<=0);
					 Categorias categoriaSeleccionada = (Categorias) JOptionPane.showInputDialog(null, "Cambie la categoria de su producto", "Jumbox", JOptionPane.QUESTION_MESSAGE, null,Categorias.values(), Categorias.values()[0]);

					seleccionado.setCategoria(categoriaSeleccionada.getId());
		            seleccionado.setPrecio(nuevoP);
		            seleccionado.setStock(nuevoS);
		                
					editarProducto(seleccionado);
					JOptionPane.showMessageDialog(null, "Producto actualizado.");
				}
			}
		}
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
	        // 1. Obtener todos los productos y cantidades del pedido
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

	        // 2. Recorremos los productos para verificar stock
	        while (rs.next()) {
	            int idProducto = rs.getInt("fk_producto");
	            int cantidad = rs.getInt("cantidad");
	            idSucursal = rs.getInt("fk_sucursal"); // todos los productos van a la misma sucursal

	            // Consultar stock del depósito
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
	                    mensajeStock.append("Pedido: ").append(idPedido) // si lo tenés disponible
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

	        // 3. Si hay algún producto con stock insuficiente, mostrar mensaje y cancelar todo
	        if (stockInsuficiente) {
	            JOptionPane.showMessageDialog(null,
	                "No se pudo enviar el pedido porque hay productos sin stock suficiente:\n\n" +
	                mensajeStock.toString());
	            return;
	        }

	        // 4. Si todo el stock es suficiente, se procesa el pedido
	        for (int i = 0; i < productos.size(); i++) {
	            int idProducto = productos.get(i);
	            int cantidad = cantidades.get(i);

	         // Verificar si ya existe el producto en el almacén de la sucursal
	            PreparedStatement checkStmt = con.prepareStatement(
	                "SELECT cantidad FROM almacen_sucursal WHERE fk_sucursal = ? AND fk_producto = ?"
	            );
	            checkStmt.setInt(1, idSucursal);
	            checkStmt.setInt(2, idProducto);
	            ResultSet rsCheck = checkStmt.executeQuery();

	            if (rsCheck.next()) {
	                // Ya existe → actualizar stock
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
	                // No existe → insertar nuevo registro
	                PreparedStatement insertStmt = con.prepareStatement(
	                    "INSERT INTO almacen_sucursal (fk_sucursal, fk_producto, cantidad) VALUES (?, ?, ?)"
	                );
	                insertStmt.setInt(1, idSucursal);
	                insertStmt.setInt(2, idProducto);
	                insertStmt.setInt(3, cantidad);
	                insertStmt.executeUpdate();
	            }


	            // Descontar del stock del depósito
	            PreparedStatement updateStock = con.prepareStatement(
	                "UPDATE producto SET stock = stock - ? WHERE id_producto = ?"
	            );
	            updateStock.setInt(1, cantidad);
	            updateStock.setInt(2, idProducto);
	            updateStock.executeUpdate();
	        }

	        // 5. Borrar los datos del pedido
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
