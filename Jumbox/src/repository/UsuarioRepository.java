package repository;

import java.util.List;
import jumbox.Cliente;

public interface UsuarioRepository {
    void agregarUsuario(Cliente usuario);
    List<Cliente> mostrarUsuarios();
	<T> T login(String nombre, String contrasena);
}
