package repository;

import java.util.List;
import jumbox.Sucursal;

public interface SucursalRepository {
    void agregarEncargadoS(Sucursal EncargadoS);
    List<Sucursal> mostrarEncargadoS();
	<T> T loginSucursal(String contrasena);
}
