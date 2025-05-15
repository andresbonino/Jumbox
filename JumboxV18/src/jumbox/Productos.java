package jumbox;

import javax.swing.JOptionPane;

import DDL.ControllerProducto;
import DDL.ControllerUsuario;

public class Productos {

	private String nombre;
	private double precio;
	private int stock;
	
	public Productos(String nombre, double precio, int stock) {
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public static void crearProducto(String nombre, double precio, int stock) {//, int categoria
		ControllerProducto controller = new ControllerProducto();
		
        while (nombre.isEmpty()) {
            nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto");
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error: campo vacío");
            }
        }
           
         while (precio==0) {
        	 precio = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el precio del producto"));
             if (precio==0) {
                JOptionPane.showMessageDialog(null, "Error");
                }
            }
         
         while (stock<0) {
        	 stock = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el stock del producto"));
             if (stock<0) {
                JOptionPane.showMessageDialog(null, "Error");
                }
            }
         
		//categoria = (int) JOptionPane.showInputDialog(null, "¿En que categoria entra tu producto?", "Jumbox", categoria, null, Categorias.values(), Categorias.values()[0]);

            
        
        Productos producto = new Productos(nombre, precio, stock);
        controller.agregarProducto(producto);
	}
	
	
}
