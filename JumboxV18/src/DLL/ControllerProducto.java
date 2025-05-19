package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import jumbox.Productos;
import repository.ProductoRepository;

public class ControllerProducto<T extends Productos> implements ProductoRepository {
	
    private static Connection con = Conexion.getInstance().getConnection();

    
    @Override
    public void agregarProducto(Productos producto) {
        try {
            PreparedStatement statement = con.prepareStatement(
            	"INSERT INTO producto (nombre, precio, stock, fk_categoria) VALUES (?, ?, ?, ?)"
            );
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getStock());
            statement.setInt(4, producto.getCategoria());

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
                int categoria = rs.getInt("categoria");
               
                producto.add((T) new Productos(nombre, precio, stock, categoria));
                        
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producto;
    }
    
    @Override
    public void editarProducto(Productos producto) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                "UPDATE producto SET precio = ?, stock = ? WHERE nombre = ?"
            );
            stmt.setDouble(1, producto.getPrecio());
            stmt.setInt(2, producto.getStock());
            stmt.setString(3, producto.getNombre());

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Producto actualizado correctamente.");
            } else {
                System.out.println("No se encontró el producto para actualizar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public void verStock() {
		    LinkedList<Productos> productos = mostrarProducto();

		    if (productos.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "No hay productos cargados.");
		    } else {
		    	String[] nombres = new String[productos.size()];
		    	for (int i = 0; i < productos.size(); i++) {
		    	    nombres[i] = productos.get(i).getNombre();
		    	}
		        String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione un producto:", "Ver Stock", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);

		        if (seleccion != null) {
		            for (Productos prod : productos) {
		                if (prod.getNombre().equals(seleccion)) {
		                    JOptionPane.showMessageDialog(null, "Producto: " + prod.getNombre() + "\nStock disponible: " + prod.getStock());
		                    break;
		                }
		            }
		        }
		    }
	}

	@Override
	public void editar() {
		LinkedList<Productos> productos = mostrarProducto();
		if (productos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay productos cargados.");
		} else {
			String[] nombres = new String[productos.size()];
			for (int i = 0; i < productos.size(); i++) {
				nombres[i] = productos.get(i).getNombre();
			}

			String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione un producto para editar:", "Editar Producto", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);

			if (seleccion != null) {
				Productos seleccionado = null;
				for (Productos prod : productos) {
					if (prod.getNombre().equals(seleccion)) {
						seleccionado = prod;
						break;
					}
				}

				if (seleccionado != null) {
					String nuevoPrecio, nuevoStock;
					Double nuevoP = null;
					int nuevoS = 0;
					do {
						nuevoPrecio = JOptionPane.showInputDialog("Nuevo precio:", seleccionado.getPrecio());
						if (!nuevoPrecio.isEmpty()) {
							nuevoP = Double.parseDouble(nuevoPrecio);
						}
					} while (nuevoPrecio.isEmpty() || nuevoP<=0);
		            
					do {
		            	nuevoStock = JOptionPane.showInputDialog("Nuevo stock:", seleccionado.getStock());
						if (!nuevoStock.isEmpty()) {
							nuevoS = Integer.parseInt(nuevoStock);
						}
					} while (nuevoStock.isEmpty() || nuevoS<=0);
		            seleccionado.setPrecio(nuevoP);
		            seleccionado.setStock(nuevoS);
		                
					editarProducto(seleccionado);
					JOptionPane.showMessageDialog(null, "Producto actualizado.");
				}
			}
		}
	}

}
