package DDL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                int id = rs.getInt("id_cliente");
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");

                usuario = (T) new Cliente(id, nombre, direccion, telefono, contrasena);
                       
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
                System.out.println("Usuario agregado correctamente.");
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
}
