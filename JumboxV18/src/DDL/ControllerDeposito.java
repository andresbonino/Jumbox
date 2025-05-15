package DDL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import jumbox.Deposito;
import repository.DepositoRepository;

public class ControllerDeposito<T extends Deposito> implements DepositoRepository {
	
    private static Connection con = Conexion.getInstance().getConnection();

    @Override
    public T loginDeposito(String contrasena) {
        T EncargadoD = null;
        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM deposito WHERE contrasena = ?"
            );
            stmt.setString(1, contrasena);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

            	EncargadoD = (T) new Deposito(contrasena);
                       
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EncargadoD;
    }

	@Override
	public void agregarEncargadoD(Deposito EncargadoD) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Deposito> mostrarEncargadoD() {
		// TODO Auto-generated method stub
		return null;
	}
}
