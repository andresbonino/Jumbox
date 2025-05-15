package DDL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import jumbox.Productos;
import repository.ProductoRepository;

public class ControllerProducto<T extends Productos> implements ProductoRepository {
	
    private static Connection con = Conexion.getInstance().getConnection();

    
    @Override
    public void agregarProducto(Productos producto) {
        try {
            PreparedStatement statement = con.prepareStatement(
                "INSERT INTO producto (nombre, precio, stock) VALUES (?, ?, ?)"
            );
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getStock());

            int filas = statement.executeUpdate();
            if (filas > 0) {
                System.out.println("Producto agregado correctamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public LinkedList<Productos> mostrarProducto() {
        LinkedList<Productos> producto = new LinkedList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM producto");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                Double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
               
                producto.add((T) new Productos(nombre, precio, stock));
                        
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producto;
    }

}
