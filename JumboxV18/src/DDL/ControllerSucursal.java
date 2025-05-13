package DDL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import jumbox.Sucursal;
import repository.SucursalRepository;

public class ControllerSucursal<T extends Sucursal> implements SucursalRepository {
	
    private static Connection con = Conexion.getInstance().getConnection();

    @Override
    public T loginSucursal(String contrasena) {
        T EncargadoS = null;
        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM sucursal WHERE contrasena = ?"
            );
            stmt.setString(1, contrasena);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

            	EncargadoS = (T) new Sucursal(contrasena);
                       
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EncargadoS;
    }

	@Override
	public void agregarEncargadoS(Sucursal EncargadoS) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Sucursal> mostrarEncargadoS() {
		// TODO Auto-generated method stub
		return null;
	}
}
