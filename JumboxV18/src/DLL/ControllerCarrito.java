package DLL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import java.sql.PreparedStatement;



import jumbox.Carrito;
import jumbox.Cliente;
import jumbox.Productos;
import repository.CarritoRepository;


public class ControllerCarrito <T extends Carrito> implements CarritoRepository{
	
	static ControllerUsuario controller = new ControllerUsuario();
	
	private static Connection con = Conexion.getInstance().getConnection();
	
	@Override
	public void compras(LinkedList<Productos> productos, LinkedList<Carrito> carrito) {
		if (productos.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No hay productos disponibles.");
	        return;
	    }else {
			String[] nombres = new String[productos.size()];
		    for (int i = 0; i < productos.size(); i++) {
		        nombres[i] = productos.get(i).getNombre();
		    }
	
		    String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione producto:", "Agregar al carrito", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);
	
		    Productos productoElegido = null;
		    for (Productos p : productos) {
		        if (p.getNombre().equals(seleccion)) {
		            productoElegido = p;
		            break;
		        }
		    }
	
		    if (productoElegido != null) {
		    	
		    	int Cant=0;
                String cantidad;
				do {
					cantidad = JOptionPane.showInputDialog("Cantidad:", null);
					if (!cantidad.isEmpty()) {
						Cant = Integer.parseInt(cantidad);
						if (Cant > productoElegido.getStock()) {
							JOptionPane.showMessageDialog(null, "No hay suficiente stock.");
						}else if(Cant<=0){
							JOptionPane.showMessageDialog(null, "No puede ser menor o igual a 0");
							return;
						}else {
		                    carrito.add(new Carrito(productoElegido, Cant));
		                    JOptionPane.showMessageDialog(null, "Producto agregado al carrito.");
		                }
					}
				} while (cantidad.isEmpty() || Cant<=0 || Cant > productoElegido.getStock());
		    }
		
	    }
	}

	@Override
	public void verCarrito(LinkedList<Carrito> carrito) {
		if (carrito.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El carrito está vacío.");
	        return;
	    }

	    StringBuilder resumen = new StringBuilder("Carrito de Compras:\n");
	    double total = 0;

	    for (Carrito item : carrito) {
	        resumen.append(item.getProducto().getNombre()).append(" x ").append(item.getCantidad()).append(" = $").append(item.getTotal()).append("\n");
	        total += item.getTotal();
	    }

	    resumen.append("\nTOTAL: $").append(total);
	    JOptionPane.showMessageDialog(null, resumen.toString());
		
	}

	@Override
	public void editarCarrito(LinkedList<Carrito> carrito) {
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

	        if (itemSeleccionado != null) {
	            String[] opciones = {"Cambiar cantidad", "Eliminar del carrito"};
	            int opcion = JOptionPane.showOptionDialog(null, "¿Qué desea hacer con " + itemSeleccionado.getProducto().getNombre() + "?", "Opciones",
	                    0, 0, null, opciones, opciones[0]);

	            if (opcion == 0) {
	                int nuevaC=0;
	                String nuevaCantidad;
					do {
						nuevaCantidad = JOptionPane.showInputDialog("Nueva Catidad:", itemSeleccionado.getCantidad());
						if (!nuevaCantidad.isEmpty()) {
							nuevaC = Integer.parseInt(nuevaCantidad);
							if (nuevaC > itemSeleccionado.getProducto().getStock()) {
								JOptionPane.showMessageDialog(null, "No hay suficiente stock.");
							}else if(nuevaC<=0){
								JOptionPane.showMessageDialog(null, "No puede ser menor o igual a 0");
								return;
							}else {
							}
			                    itemSeleccionado.setCantidad(nuevaC);
			                    JOptionPane.showMessageDialog(null, "Cantidad actualizada.");
			                
						}
					} while (nuevaCantidad.isEmpty() || nuevaC<=0 || nuevaC > itemSeleccionado.getProducto().getStock());
					
	            } else {
	                carrito.remove(itemSeleccionado);
	                JOptionPane.showMessageDialog(null, "Producto eliminado del carrito.");
	            }
	        }
	    }
		
	}
	
	
	public void realizarCompra(LinkedList<Carrito> carrito, Cliente cliente) {
        try {
            // Insertar pedido
            LocalDate fecha = LocalDate.now();
            PreparedStatement stmtPedido = (PreparedStatement) con.prepareStatement(
                "INSERT INTO pedido (fecha, estado, fk_cliente) VALUES (?, 'pendiente', ?)"
            );
            stmtPedido.setString(1, fecha.toString());
            stmtPedido.setInt(2, cliente.getId());
            stmtPedido.executeUpdate();

            // Obtener id del pedido
            PreparedStatement buscarPedido = (PreparedStatement) con.prepareStatement(
                "SELECT id_pedido FROM pedido WHERE fecha = ? AND fk_cliente = ? ORDER BY id_pedido DESC LIMIT 1"
            );
            buscarPedido.setString(1, fecha.toString());
            buscarPedido.setInt(2, cliente.getId());
            ResultSet rs = buscarPedido.executeQuery();

            int idPedido = -1;
            if (rs.next()) {
                idPedido = rs.getInt("id_pedido");
            }

            // Insertar detalles
            for (Carrito item : carrito) {
                PreparedStatement stmtDetalle = (PreparedStatement) con.prepareStatement(
                    "INSERT INTO detalles_pedido (cantidad, fk_producto, fk_pedido) VALUES (?, ?, ?)"
                );
                stmtDetalle.setInt(1, item.getCantidad());
                stmtDetalle.setInt(2, item.getProducto().getIdProducto());
                stmtDetalle.setInt(3, idPedido);
                stmtDetalle.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "compra realizada con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "error al realizar la compra.");
        }
    }

	@Override
	public void verCompra() {
		
		try {
            String nombre = JOptionPane.showInputDialog("ingrese su nombre para ver sus pedidos:");
            PreparedStatement stmt =  con.prepareStatement(
            		"SELECT p.id_pedido, p.fecha, p.estado, d.cantidad, prod.nombre, prod.precio " +
                    "FROM pedido p " +
                    "JOIN detalles_pedido d ON p.id_pedido = d.fk_pedido " +
                    "JOIN producto prod ON d.fk_producto = prod.id_producto " +
                    "JOIN cliente c ON p.fk_cliente = c.id_cliente " +
                    "WHERE c.nombre = ?");

            stmt.setString(1, nombre);
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
	
	
	private int obtenerIdCarrito(Cliente cliente) {
	    try {
	        PreparedStatement buscar = con.prepareStatement("SELECT id_carrito FROM carrito WHERE fk_cliente = ?");
	        buscar.setInt(1, cliente.getId()); 
	        ResultSet rs = buscar.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id_carrito");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return -1;
	}




	public void guardarProductoBD(Carrito item, Cliente cliente) {
	    try {
	        int idCarrito = obtenerIdCarrito(cliente);
	        
	            PreparedStatement crearCarrito = con.prepareStatement("INSERT INTO carrito (fk_cliente) VALUES (?)");
	            crearCarrito.setInt(1, cliente.getId());
	            crearCarrito.executeUpdate();
	            idCarrito = obtenerIdCarrito(cliente);
	        

	        int idProducto = item.getProducto().getIdProducto();
	        if (idProducto <= 0) {
	            JOptionPane.showMessageDialog(null, "ID del producto inválido.");
	            return;
	        }

	        PreparedStatement verificar = con.prepareStatement(
	            "SELECT cantidad FROM producto_carrito WHERE fk_producto = ? AND fk_carrito = ?"
	        );
	        verificar.setInt(1, idProducto);
	        verificar.setInt(2, idCarrito);
	        ResultSet rs = verificar.executeQuery();

	        if (rs.next()) {
	            int cantidadActual = rs.getInt("cantidad");
	            PreparedStatement update = con.prepareStatement(
	                "UPDATE producto_carrito SET cantidad = ? WHERE fk_carrito = ? AND fk_producto = ?"
	            );
	            update.setInt(1, cantidadActual + item.getCantidad());
	            update.setInt(2, idCarrito);
	            update.setInt(3, idProducto);
	            update.executeUpdate();
	        } else {
	            PreparedStatement insertar = con.prepareStatement(
	                "INSERT INTO producto_carrito (fk_producto, fk_carrito) VALUES (?, ?)"
	            );
	            insertar.setInt(1, idCarrito);
	            insertar.setInt(2, idProducto);
	            insertar.executeUpdate();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al guardar producto en la base de datos.");
	    }
	}


	

	

}
