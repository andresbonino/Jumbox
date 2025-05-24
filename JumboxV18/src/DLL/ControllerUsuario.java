package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import jumbox.Cliente;
import repository.UsuarioRepository;

public class ControllerUsuario<T extends Cliente> implements UsuarioRepository {

    private static Connection con = Conexion.getInstance().getConnection();

    @Override
    public T login(String nombre, String contrasena) {
        T usuario = null;
        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM cliente WHERE nombre = ? AND contrasena = ?"
            );
            stmt.setString(1, nombre);
            stmt.setString(2, contrasena);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");

                usuario = (T) new Cliente(nombre, direccion, telefono, contrasena);
                       
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public void agregarUsuario(Cliente usuario) {
        try {
            PreparedStatement statement = con.prepareStatement(
                "INSERT INTO cliente (nombre, direccion, telefono, contrasena) VALUES (?, ?, ?, ?)"
            );
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getDireccion());
            statement.setInt(3, usuario.getTelefono());
            statement.setString(4, usuario.getContrasena());

            int filas = statement.executeUpdate();
            if (filas > 0) {
            	ResultSet rs = statement.getGeneratedKeys();
            	
            	if (rs.next()) {
            		int idCliente = rs.getInt(1); //id generado
            		
            		//inserto aca el carrito para cliente
            		PreparedStatement stmtCarrito = con.prepareStatement("INSERT INTO carrito(fk_cliente) VALUES (?)");
            		
            		stmtCarrito.setInt(1, idCliente);
            		stmtCarrito.executeUpdate();
            		
            		JOptionPane.showMessageDialog(null, "Usuario y Carrito agregado");
            		
				} else {
					JOptionPane.showMessageDialog(null, "No se pudo agregar el usuario");
				}
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public LinkedList<Cliente> mostrarUsuarios() {
        LinkedList<Cliente> usuarios = new LinkedList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM cliente");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                int telefono = rs.getInt("telefono");
                String contrasena = rs.getString("contrasena");

               
                usuarios.add((T) new Cliente(nombre, direccion, telefono, contrasena));
                        
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    
    @Override
	public void verificarUsuario(Cliente usuario) {
		LinkedList<Cliente> existentes = mostrarUsuarios();
    	boolean flag = true;
    	for (Cliente existente : existentes) {
			if (existente.getTelefono()==usuario.getTelefono()) {
				flag = false;
				break;
			}
		}
    	if (flag) {
    		agregarUsuario(usuario);
		}else {
			JOptionPane.showMessageDialog(null, "El usuario se registró anteriormente");
		}
		
	}
    
    public int obtenerIdCarritoPorCliente(int idCliente) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT id_carrito FROM carrito WHERE fk_cliente = ?"
            );
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_carrito");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    
    public void verCarrito(int idCliente) {
		
    	
    	try {
    		int idCarrito = obtenerIdCarritoPorCliente(idCliente);
    		PreparedStatement stmt = con.prepareStatement( "SELECT p.nombre, p.precio FROM producto p " + "JOIN producto_carrito pc ON p.id_producto = pc.fk_producto " + "WHERE pc.fk_carrito = ?");
    		
    		stmt.setInt(1, idCarrito);
    		
    		ResultSet rs = stmt.executeQuery();
    		
    		boolean hayProductos = false;
    		
    		while (rs.next()) {
    			int idProducto = rs.getInt("fk_producto");

                PreparedStatement pStmt = con.prepareStatement(
                    "SELECT nombre, precio FROM producto WHERE id_producto = ?"
                );
                
                pStmt.setInt(1, idProducto);
                ResultSet prodRs = pStmt.executeQuery();

                if (prodRs.next()) {
                	
                    hayProductos = true;
                    String nombre = prodRs.getString("nombre");
                    double precio = prodRs.getDouble("precio");

                    JOptionPane.showMessageDialog(null, 
                        "Producto: " + nombre + "\nPrecio: $" + precio);
                }
                
                if (!hayProductos) {
                    JOptionPane.showMessageDialog(null, "El carrito está vacío.");
                }

                
                
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}

    
    public void agregarProductoACarrito(int idCliente, int idProducto) {
        try {
            int idCarrito = obtenerIdCarritoPorCliente(idCliente);

            PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO producto_carrito (fk_producto, fk_carrito) VALUES (?, ?)"
            );
            stmt.setInt(1, idProducto);
            stmt.setInt(2, idCarrito);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "producto agregado al carrito");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void realizarCompra(int idCliente) {
        try {
            // 1. Obtener fecha como texto
            LocalDate fecha = LocalDate.now();
            String fechaTexto = fecha.toString(); // yyyy-MM-dd

            // 2. Insertar pedido
            PreparedStatement stmtPedido = con.prepareStatement(
                "INSERT INTO pedido (fecha, estado, fk_cliente) VALUES (?, 'pendiente', ?)"
            );
            stmtPedido.setString(1, fechaTexto);
            stmtPedido.setInt(2, idCliente);
            stmtPedido.executeUpdate();

            // 3. Buscar el ID del pedido recién insertado (sin MAX ni RETURN_GENERATED_KEYS)
            PreparedStatement buscarPedido = con.prepareStatement(
                "SELECT id_pedido FROM pedido WHERE fecha = ? AND estado = 'pendiente' AND fk_cliente = ? ORDER BY id_pedido DESC LIMIT 1"
            );
            buscarPedido.setString(1, fechaTexto);
            buscarPedido.setInt(2, idCliente);
            ResultSet rs = buscarPedido.executeQuery();

            int idPedido = -1;
            if (rs.next()) {
                idPedido = rs.getInt("id_pedido");
            }

            // 4. Obtener carrito
            int idCarrito = obtenerIdCarritoPorCliente(idCliente);

            // 5. Obtener productos del carrito
            PreparedStatement stmtProductos = con.prepareStatement(
                "SELECT fk_producto FROM producto_carrito WHERE fk_carrito = ?"
            );
            stmtProductos.setInt(1, idCarrito);
            ResultSet productos = stmtProductos.executeQuery();

            // 6. Insertar productos en detalles_pedido
            while (productos.next()) {
                int idProducto = productos.getInt("fk_producto");

                PreparedStatement stmtDetalle = con.prepareStatement(
                    "INSERT INTO detalles_pedido (cantidad, fk_producto, fk_pedido) VALUES (?, ?, ?)"
                );
                stmtDetalle.setInt(1, 1);
                stmtDetalle.setInt(2, idProducto);
                stmtDetalle.setInt(3, idPedido);
                stmtDetalle.executeUpdate();
            }

            // 7. Vaciar carrito
            PreparedStatement eliminar = con.prepareStatement(
                "DELETE FROM producto_carrito WHERE fk_carrito = ?"
            );
            eliminar.setInt(1, idCarrito);
            eliminar.executeUpdate();

            JOptionPane.showMessageDialog(null, "compra realizada con éxito");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "error al realizar la compra");
        }
    }


    public void estadoCompra(int idCliente) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT id_pedido, fecha, estado FROM pedido WHERE fk_cliente = ?"
            );
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            boolean hayPedidos = false;

            while (rs.next()) {
                hayPedidos = true;
                int idPedido = rs.getInt("id_pedido");
                String fecha = rs.getString("fecha");
                String estado = rs.getString("estado");

                JOptionPane.showMessageDialog(null,
                    "Pedido #" + idPedido +
                    "\nFecha: " + fecha +
                    "\nEstado: " + estado);
            }

            if (!hayPedidos) {
                JOptionPane.showMessageDialog(null, "no hay pedidos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "error al consultar estado de pedidos.");
        }
    }

    

}
