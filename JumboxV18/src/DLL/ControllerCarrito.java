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
	public void compras(LinkedList<Productos> productos, LinkedList<Carrito> carrito, Cliente cliente) {
	    if (productos.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No hay productos disponibles.");
	        return;
	    }

	    // Mostrar los nombres de los productos para que el usuario elija
	    String[] nombres = new String[productos.size()];
	    for (int i = 0; i < productos.size(); i++) {
	        nombres[i] = productos.get(i).getNombre();
	    }

	    String seleccion = (String) JOptionPane.showInputDialog(
	        null, "Seleccione producto:", "Agregar al carrito",
	        JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]
	    );

	    if (seleccion == null) return; // usuario canceló

	    Productos productoElegido = null;
	    for (Productos p : productos) {
	        if (p.getNombre().equals(seleccion)) {
	            productoElegido = p;
	            break;
	        }
	    }

	    if (productoElegido != null) {
	        // 🚨 SETEAR ID DEL PRODUCTO desde la base de datos
	        int idProducto = obtenerIdProductoPorNombre(productoElegido.getNombre());
	        productoElegido.setId_producto(idProducto);

	        if (idProducto <= 0) {
	            JOptionPane.showMessageDialog(null, "Error al obtener ID del producto.");
	            return;
	        }

	        int Cant = 0;
	        String cantidad;
	        do {
	            cantidad = JOptionPane.showInputDialog("Cantidad:");
	            if (cantidad != null && !cantidad.isEmpty()) {
	                Cant = Integer.parseInt(cantidad);
	                if (Cant <= 0 || Cant > productoElegido.getStock()) {
	                    JOptionPane.showMessageDialog(null, "Cantidad inválida.");
	                } else {
	                    Carrito nuevo = new Carrito(productoElegido, Cant, cliente);
	                    carrito.add(nuevo);
	                    guardarProductoBD(nuevo, cliente);
	                    JOptionPane.showMessageDialog(null, "Producto agregado al carrito.");
	                    break;
	                }
	            }
	        } while (cantidad == null || cantidad.isEmpty() || Cant <= 0 || Cant > productoElegido.getStock());
	    }
	}

	@Override
	public void verCarrito(LinkedList<Carrito> carrito) {
		if (carrito.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El carrito está vacío.");
	        return;
	    }

		String resumen = "Carrito de Compras:\n";
	    double total = 0;

	    for (Carrito item : carrito) {
	    	resumen += item.getProducto().getNombre() + " x " + item.getCantidad()
            + " = $" + item.getTotal() + "\n";
	    	total += item.getTotal();
	    }

	    resumen+=("\nTOTAL: $")+(total);
	    JOptionPane.showMessageDialog(null, resumen);
		
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
            stmtPedido.setInt(2, cliente.getId_cliente());
            stmtPedido.executeUpdate();

            // Obtener id del pedido
            PreparedStatement buscarPedido = (PreparedStatement) con.prepareStatement(
                "SELECT id_pedido FROM pedido WHERE fecha = ? AND fk_cliente = ? ORDER BY id_pedido DESC LIMIT 1"
            );
            buscarPedido.setString(1, fecha.toString());
            buscarPedido.setInt(2, cliente.getId_cliente());
            ResultSet rs = buscarPedido.executeQuery();
            
            int idPedido = -1;
            if (rs.next()) {
                idPedido = rs.getInt("id_pedido");
            }

            
            for (Carrito item : carrito) {
                PreparedStatement stmtDetalle = con.prepareStatement(
                        "INSERT INTO detalles_pedido (cantidad, fk_producto, fk_pedido) VALUES (?, ?, ?)");
                stmtDetalle.setInt(1, item.getCantidad());
                stmtDetalle.setInt(2, item.getProducto().getId_producto());
                stmtDetalle.setInt(3, idPedido);
                stmtDetalle.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Compra realizada con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al realizar la compra.");
        }
    }

	@Override
	public void verCompra() {
		
		try {
            String nombre = JOptionPane.showInputDialog("Ingrese su nombre para ver sus pedidos:");
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
	        buscar.setInt(1, cliente.getId_cliente());
	        ResultSet rs = buscar.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id_carrito");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return -1;
	}

	
	@Override
	public void guardarProductoBD(Carrito item, Cliente cliente) {
	    try {
	    	
	    	int idCliente = cliente.getId_cliente();
	    	if (idCliente <= 0) {
				
	    		JOptionPane.showMessageDialog(null, "ID del cliente inválido: " + idCliente);
	            return;
	    		
			}
	        int idCarrito = obtenerIdCarrito(cliente);
	        if (idCarrito == -1) {
	            PreparedStatement crearCarrito = con.prepareStatement("INSERT INTO carrito (fk_cliente) VALUES (?)");
	            crearCarrito.setInt(1, idCliente);
	            crearCarrito.executeUpdate();
	            idCarrito = obtenerIdCarrito(cliente);
	        }
	        
	        

	        

	        PreparedStatement verificar = con.prepareStatement(
	            "SELECT cantidad FROM producto_carrito WHERE fk_carrito = ? AND fk_producto = ?"
	        );
	        verificar.setInt(1, idCarrito);
	        verificar.setInt(2, item.getProducto().getId_producto());
	        ResultSet rs = verificar.executeQuery();

	        if (rs.next()) {
	            int cantidadActual = rs.getInt("cantidad");
	            int nuevaCantidad = cantidadActual + item.getCantidad();
	            PreparedStatement update = con.prepareStatement(
	                "UPDATE producto_carrito SET cantidad = ? WHERE fk_carrito = ? AND fk_producto = ?"
	            );
	            update.setInt(1, nuevaCantidad);
	            update.setInt(2, idCarrito);
	            update.setInt(3, item.getProducto().getId_producto());
	            update.executeUpdate();
	        } else {
	            PreparedStatement insertar = con.prepareStatement(
	                "INSERT INTO producto_carrito (fk_carrito, fk_producto, cantidad) VALUES (?, ?, ?)"
	            );
	            insertar.setInt(1, idCarrito);
	            insertar.setInt(2, item.getProducto().getId_producto());
	            insertar.setInt(3, item.getCantidad());
	            insertar.executeUpdate();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al guardar producto en la base de datos.");
	    }
	    
	}   
	
	
	/*public void guardarProductoBD2(LinkedList<Productos> productos, Carrito item, Cliente cliente) {
try {
	    	
	    	int idCliente = cliente.getId_cliente();
	    	if (idCliente <= 0) {
				
	    		JOptionPane.showMessageDialog(null, "ID del cliente inválido: " + idCliente);
	            return;
	    		
			}
	        int idCarrito = obtenerIdCarrito(cliente);
	        if (idCarrito == -1) {
	            PreparedStatement crearCarrito = con.prepareStatement("INSERT INTO carrito (fk_cliente) VALUES (?)");
	            crearCarrito.setInt(1, idCliente);
	            crearCarrito.executeUpdate();
	            idCarrito = obtenerIdCarrito(cliente);
	        }
	        
	        String[] nombres = new String[productos.size()];
	        for (int i = 0; i < productos.size(); i++) {
	            nombres[i] = productos.get(i).getNombre();
	        }

	        // ACÁ DECLARÁS Y DEFINÍS `seleccion`
	        String seleccion = (String) JOptionPane.showInputDialog(
	            null,
	            "Seleccione producto:",
	            "Agregar al carrito",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            nombres,
	            nombres[0]
	        );

	        // Validación
	        if (seleccion == null) {
	            JOptionPane.showMessageDialog(null, "No seleccionaste ningún producto.");
	            return;
	        }

	        Productos productoElegido = null;
	        for (Productos p : productos) {
	            if (p.getNombre().equalsIgnoreCase(seleccion)) {
	                productoElegido = p;
	                break;
	            }
	        }

	        if (productoElegido == null) {
	            JOptionPane.showMessageDialog(null, "Producto no encontrado.");
	            return;
	        }

	        

	        PreparedStatement verificar = con.prepareStatement(
	            "SELECT cantidad FROM producto_carrito WHERE fk_carrito = ? AND fk_producto = ?"
	        );
	        verificar.setInt(1, idCarrito);
	        verificar.setInt(2, item.getProducto().getIdProducto());
	        ResultSet rs = verificar.executeQuery();

	        if (rs.next()) {
	            int cantidadActual = rs.getInt("cantidad");
	            int nuevaCantidad = cantidadActual + item.getCantidad();
	            PreparedStatement update = con.prepareStatement(
	                "UPDATE producto_carrito SET cantidad = ? WHERE fk_carrito = ? AND fk_producto = ?"
	            );
	            
	            int idProducto = item.getProducto().getIdProducto();
	            update.setInt(1, nuevaCantidad);
	            update.setInt(2, idCarrito);
	            update.setInt(3, idProducto); 
	            update.executeUpdate();
	        } else {
	            PreparedStatement insertar = con.prepareStatement(
	                "INSERT INTO producto_carrito (fk_carrito, fk_producto, cantidad) VALUES (?, ?, ?)"
	            );
	            insertar.setInt(1, idCarrito);
	            insertar.setInt(2, item.getProducto().getIdProducto());
	            insertar.setInt(3, item.getCantidad());
	            insertar.executeUpdate();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al guardar producto en la base de datos.");
	    }
	}*/
	
	@Override
	public void cargarCarritoDesdeBD(LinkedList<Carrito> carrito, Cliente cliente) {
	    try {
	        // Verificar si existe el carrito
	        int idCarrito = obtenerIdCarrito(cliente);
	        if (idCarrito == -1) {
	            // Si no existe, crearlo
	            PreparedStatement crearCarrito = con.prepareStatement("INSERT INTO carrito (fk_cliente) VALUES (?)");
	            crearCarrito.setInt(1, cliente.getId_cliente());
	            crearCarrito.executeUpdate();
	            idCarrito = obtenerIdCarrito(cliente);
	        }

	        PreparedStatement stmt = con.prepareStatement(
	            "SELECT p.id_producto, p.nombre, p.precio, p.stock, pc.cantidad " +
	            "FROM producto_carrito pc " +
	            "JOIN producto p ON pc.fk_producto = p.id_producto " +
	            "JOIN carrito c ON pc.fk_carrito = c.id_carrito " +
	            "WHERE c.fk_cliente = ?"
	        );
	        stmt.setInt(1, cliente.getId_cliente());
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Productos producto = new Productos(
	                rs.getString("nombre"),
	                rs.getDouble("precio"),
	                rs.getInt("stock"),
	                0
	            );
	            producto.setId_producto(rs.getInt("id_producto"));
	            int cantidad = rs.getInt("cantidad");

	            Carrito item = new Carrito(producto, cantidad, cliente);
	            carrito.add(item);
	        }

	    } catch (Exception e) {
	        e.printStackTrace(); // mantené esto para desarrollo
	        JOptionPane.showMessageDialog(null, "Error al cargar el carrito desde la base de datos.");
	    }
	}


	@Override
	public void limpiarCarritoBD(Cliente cliente) {
		try {
            int idCarrito = obtenerIdCarrito(cliente);
            if (idCarrito != -1) {
                PreparedStatement delete = con.prepareStatement(
                        "DELETE FROM producto_carrito WHERE fk_carrito = ?");
                delete.setInt(1, idCarrito);
                delete.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	private int obtenerIdProductoPorNombre(String nombre) {
	    try {
	        PreparedStatement stmt = con.prepareStatement("SELECT id_producto FROM producto WHERE nombre = ?");
	        stmt.setString(1, nombre);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id_producto");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return -1;
	}

	

}
