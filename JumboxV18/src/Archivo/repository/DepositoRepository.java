package repository;

import java.util.List;
import jumbox.Deposito;

public interface DepositoRepository {
    void agregarEncargadoD(Deposito EncargadoD);
    List<Deposito> mostrarEncargadoD();
	<T> T loginDeposito(String contrasena);
}
