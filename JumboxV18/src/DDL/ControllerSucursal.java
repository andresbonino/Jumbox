package DDL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import jumbox.Productos;
import jumbox.Sucursal;
import repository.SucursalRepository;

public class ControllerSucursal{
	
    private static Connection con = Conexion.getInstance().getConnection();

    public Sucursal loginSucursal(int id_sucursal, String contrasena) {
        Sucursal EncargadoS = null;
        try {
            PreparedStatement stmt = con.prepareStatement(
            		"SELECT * FROM sucursal WHERE id_sucursal = ? AND contrasena = ?"
            );
            stmt.setInt(1, id_sucursal);
            stmt.setString(2, contrasena);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                EncargadoS = new Sucursal(id_sucursal, contrasena);
                       
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EncargadoS;
    }
    
    
    public LinkedList<Productos> mostrarAlmacen() {
        LinkedList<Productos> producto = new LinkedList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM producto");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                Double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
               
                Productos p = new Productos(nombre, precio, stock);
                p.setIdProducto(id);
                producto.add(p);
                        
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producto;
    }

}
