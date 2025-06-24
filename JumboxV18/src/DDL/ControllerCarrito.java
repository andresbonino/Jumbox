package DDL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import GUI.EleccionCarrito;
import GUI.PantallaPrincipal;

import java.sql.Statement;

import java.sql.PreparedStatement;



import jumbox.Carrito;
import jumbox.Cliente;
import jumbox.OpcionesSucursales;
import jumbox.Productos;
import jumbox.Sucursal;
import repository.CarritoRepository;

public class ControllerCarrito <T extends Carrito> implements CarritoRepository{
	
	static ControllerUsuario controller = new ControllerUsuario();
	
	private static Connection con = Conexion.getInstance().getConnection();
    private LinkedList<Carrito> carrito = new LinkedList<>();

        LinkedList<Carrito> carritoRecuperado = new LinkedList<>();

        private int idCarritoActual;
        private Sucursal sucursalSeleccionada;


	public void compras(LinkedList<Productos> productos, Cliente cliente) {
		
	    try {
	    	// CREAR CARRITO
	    	PreparedStatement stmtCarrito = con.prepareStatement(
	    	    "INSERT INTO `carrito`(`fk_cliente`) VALUES (?)", Statement.RETURN_GENERATED_KEYS
	    	);
	    	stmtCarrito.setInt(1, cliente.getIdCliente());
	    	stmtCarrito.executeUpdate();

	    	ResultSet generatedKeys = stmtCarrito.getGeneratedKeys();
	    	int idCarrito = -1;
	    	if (generatedKeys.next()) {
	    	    idCarrito = generatedKeys.getInt(1);
	    	}
	    	idCarritoActual = idCarrito;


	    	// ELEGIR SUCURSAL
	    	OpcionesSucursales opcionSeleccionada = (OpcionesSucursales) JOptionPane.showInputDialog(
	    	    null,
	    	    "¿En qué sucursal querés comprar?",
	    	    "Jumbox",
	    	    JOptionPane.QUESTION_MESSAGE,
	    	    null,
	    	    OpcionesSucursales.values(),
	    	    OpcionesSucursales.values()[0]
	    	);

	    	// Validar si se seleccionó una opción
	    	if (opcionSeleccionada == null) return;

	    	// Crear la sucursal con el ID correspondiente y contraseña null (o lo que uses)
	    	this.sucursalSeleccionada = new Sucursal(opcionSeleccionada.getId(), null);


	        int idSucursal = opcionSeleccionada.getId();

	        // BUSCAR PRODUCTOS DE LA SUCURSAL SELECCIONADA
	        PreparedStatement stmt = Conexion.getInstance().getConnection().prepareStatement(
	            "SELECT a.fk_producto, a.cantidad, p.nombre, p.precio, p.id_producto "+
	            "FROM almacen_sucursal a " +
	            "JOIN producto p ON a.fk_producto = p.id_producto " +
	            "WHERE a.fk_sucursal = ? AND a.cantidad > 0"
	        );

	        stmt.setInt(1, idSucursal);
	        ResultSet rs = stmt.executeQuery();

	        LinkedList<Productos> productosSucursal = new LinkedList<>();
	        StringBuilder sb = new StringBuilder("Productos disponibles en la " + opcionSeleccionada + " Sucursal:\n");

	        while (rs.next()) {
	            int idProducto = rs.getInt("fk_producto");
	            String nombre = rs.getString("nombre");
	            double precio = rs.getDouble("precio");
	            int stock = rs.getInt("cantidad");

	            Productos prod = new Productos(nombre, precio, stock);
	            productosSucursal.add(prod);
	            prod.setIdProducto(idProducto);

	            sb.append("- ").append(nombre)
	              .append(" | Precio: $").append(precio)
	              .append(" | Stock: ").append(stock)
	              .append("\n");
	        }
	        
	        sb.append("\nSeleccione un producto:");

	        if (productosSucursal.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No hay productos disponibles en esta sucursal.");
	            return;
	        }

	        // MOSTRAR PRODUCTOS PARA ELEGIR
	        String[] nombres = new String[productosSucursal.size()];
	        for (int i = 0; i < productosSucursal.size(); i++) {
	            nombres[i] = productosSucursal.get(i).getNombre();
	        }

	        String seleccion = (String) JOptionPane.showInputDialog(
	            null,
	            sb.toString(),
	            "Agregar al carrito",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            nombres,
	            nombres[0]
	        );

	        if (seleccion == null) return;

	        Productos productoElegido = null;
	        for (Productos p : productosSucursal) {
	            if (p.getNombre().equals(seleccion)) {
	                productoElegido = p;
	                break;
	            }
	        }

	        if (productoElegido != null) {
	        	
	        	// VERIFICAR SI YA ESTA EN EL CARRITO
	        	boolean yaEnCarrito = false;
	        	for (Carrito item : carrito) {
	        	    if (item.getProducto().getNombre().equals(productoElegido.getNombre())) {
	        	        yaEnCarrito = true;
	        	        break;
	        	    }
	        	}

	        	if (yaEnCarrito) {
	        	    JOptionPane.showMessageDialog(null, "Este producto ya está en el carrito.");
	        	    return;
	        	}

	        	
	        	
	            int cantidad = 0;
	            do {
	                String input = JOptionPane.showInputDialog("Cantidad:");
	                if (input == null || input.isEmpty()) return;

	                try {
	                    cantidad = Integer.parseInt(input);

	                    if (cantidad <= 0) {
	                        JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a cero.");
	                    } else if (cantidad > productoElegido.getStock()) {
	                        JOptionPane.showMessageDialog(null, "No hay suficiente stock.");
	                    } else {
	                        carrito.add(new Carrito(productoElegido, cantidad));
	                        JOptionPane.showMessageDialog(null, "Producto agregado al carrito.");

	                        
	                     
	                        PreparedStatement stmtProductoId = con.prepareStatement(
	                            "SELECT id_producto FROM producto WHERE nombre = ?"
	                        );
	                        stmtProductoId.setString(1, productoElegido.getNombre());

	                        ResultSet rsProducto = stmtProductoId.executeQuery();

	                        int idProductoReal = -1;
	                        if (rsProducto.next()) {
	                            idProductoReal = rsProducto.getInt("id_producto");
	                        } else {
	                            JOptionPane.showMessageDialog(null, "No se encontró el producto.");
	                            return;
	                        }
	                        
	                        PreparedStatement stmtProductoCarrito = (PreparedStatement) con.prepareStatement(
	                        		"INSERT INTO `producto_carrito`(`fk_producto`, `fk_carrito`, `cantidad`) VALUES (?, ?, ?)"
	                            );
	                        stmtProductoCarrito.setInt(1, idProductoReal);
	                        stmtProductoCarrito.setInt(2, idCarrito);
	                        stmtProductoCarrito.setInt(3, cantidad);
	                        stmtProductoCarrito.executeUpdate();
	                        
	                        break;
	                    }
	                } catch (NumberFormatException e) {
	                    JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.");
	                }
	            } while (true);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al obtener productos de la sucursal.");
	    }
	}


	@Override
	public void verCarrito(Cliente cliente, Sucursal sucursal) {
	    if (carrito.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El carrito está vacío.");
	        return;
	    }

	    StringBuilder resumen = new StringBuilder("Carrito de Compras:\n");
	    double total = 0;

	    for (Carrito item : carrito) {
	        resumen.append(item.getProducto().getNombre())
	               .append(" x ")
	               .append(item.getCantidad())
	               .append(" = $")
	               .append(item.getTotal())
	               .append("\n");
	        total += item.getTotal();
	    }

	    resumen.append("\nTOTAL: $").append(total);

	    String[] opciones = {"Confirmar compra", "Salir"};
	    int seleccion = JOptionPane.showOptionDialog(
	        null,
	        resumen.toString() + "\n\n¿Desea realizar la compra?",
	        "Carrito",
	        JOptionPane.DEFAULT_OPTION,
	        JOptionPane.QUESTION_MESSAGE,
	        null,
	        opciones,
	        opciones[0]
	    );

	    if (seleccion == 0) {
	    	realizarCompra(carrito, cliente, sucursalSeleccionada, idCarritoActual);

	    } else {
	        
	    }
	}


	@Override
	public void editarCarrito() {
		if (carrito.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El carrito está vacío.");
	        return;
	    }

	    String[] nombres = new String[carrito.size()];
	    for (int i = 0; i < carrito.size(); i++) {
	        nombres[i] = carrito.get(i).getProducto().getNombre() + " (x" + carrito.get(i).getCantidad() + ")";
	    }

	    String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione un producto a editar:", "Editar Carrito", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);

	    if (seleccion != null) {
	    	Carrito itemSeleccionado = null;
	        for (Carrito item : carrito) {
	            String nombreItem = item.getProducto().getNombre() + " (x" + item.getCantidad() + ")";
	            if (nombreItem.equals(seleccion)) {
	                itemSeleccionado = item;
	                break;
	            }
	        }

	        EleccionCarrito carri = new EleccionCarrito(itemSeleccionado, carrito);
	        carri.setVisible(true);
	    }
		
	}
	
	
	public void realizarCompra(LinkedList<Carrito> carrito, Cliente cliente, Sucursal sucursal, int idCarritoActual) {
	    try {
	        // INSERTAR PEDIDO
	        PreparedStatement psPedido = con.prepareStatement(
	            "INSERT INTO pedido (fecha, estado, fk_cliente, fk_sucursal) VALUES (?, ?, ?, ?)",
	            Statement.RETURN_GENERATED_KEYS
	        );
	        psPedido.setDate(1, new java.sql.Date(System.currentTimeMillis()));
	        psPedido.setString(2, "pendiente");
	        psPedido.setInt(3, cliente.getIdCliente());
	        psPedido.setInt(4, sucursal.getId_Sucursal());
	        psPedido.executeUpdate();

	        ResultSet rs = psPedido.getGeneratedKeys();
	        int idPedido = 0;
	        if (rs.next()) {
	            idPedido = rs.getInt(1);
	        }

	        // INSERTAR DETALLES
	        for (Carrito item : carrito) {
	            PreparedStatement psDetalle = con.prepareStatement(
	                "INSERT INTO detalles_pedido (cantidad, fk_producto, fk_pedido) VALUES (?, ?, ?)"
	            );
	            psDetalle.setInt(1, item.getCantidad());
	            psDetalle.setInt(2, item.getProducto().getIdProducto());
	            psDetalle.setInt(3, idPedido);
	            psDetalle.executeUpdate();
	        }

	        // BORRAR DEL CARRITO EN BD
	        PreparedStatement psBorrar = con.prepareStatement(
	            "DELETE FROM producto_carrito WHERE fk_carrito = ?"
	        );
	        psBorrar.setInt(1, idCarritoActual);
	        psBorrar.executeUpdate();

	        carrito.clear();
	        
	     // BORRAR EL CARRITO EN SI
	        PreparedStatement psBorrarCarrito = con.prepareStatement(
	            "DELETE FROM carrito WHERE id_carrito = ?"
	        );
	        psBorrarCarrito.setInt(1, idCarritoActual);
	        psBorrarCarrito.executeUpdate();

	        JOptionPane.showMessageDialog(null, "Compra realizada con éxito.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al realizar la compra.");
	    }
	}


	@Override
	public void verCompra() {
		
		try {
            int telefono = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su telefono para ver sus pedidos:"));
            PreparedStatement stmt =  con.prepareStatement(
            		"SELECT p.id_pedido, p.fecha, p.estado, d.cantidad, prod.nombre, prod.precio " +
                    "FROM pedido p " +
                    "JOIN detalles_pedido d ON p.id_pedido = d.fk_pedido " +
                    "JOIN producto prod ON d.fk_producto = prod.id_producto " +
                    "JOIN cliente c ON p.fk_cliente = c.id_cliente " +
                    "WHERE c.telefono = ? AND p.estado != 'notificado'");

            stmt.setInt(1, telefono);
            ResultSet rs = stmt.executeQuery();

            String resumen = "";
            int pedidoActual = -1;

            while (rs.next()) {
                int idPedido = rs.getInt("id_pedido");
                String fecha = rs.getString("fecha");
                String estado = rs.getString("estado");
                String producto = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio");

                if (pedidoActual != idPedido) {
                    if (pedidoActual != -1) resumen += "\n-----------------------------\n";
                    resumen += "Pedido #" + idPedido +
                               "\nFecha: " + fecha +
                               "\nEstado: " + estado +
                               "\nProductos:\n";
                    pedidoActual = idPedido;
                }
                resumen += "- " + producto + " x " + cantidad + " = $" + (precio * cantidad) + "\n";
            }

            if (resumen.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron compras para ese cliente.");
            } else {
                JOptionPane.showMessageDialog(null, resumen);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar compras.");
        }
		
	}
}
