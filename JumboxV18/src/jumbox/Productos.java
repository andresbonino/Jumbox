package jumbox;

import javax.swing.JOptionPane;

import DDL.ControllerProducto;
import DDL.ControllerUsuario;

public class Productos {

	private String nombre;
	private double precio;
	private int stock;
	private int categoria;
	private int idProducto;
	
	public Productos(int idProducto, String nombre, double precio, int stock, int categoria) {
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
		this.categoria = categoria;
		this.idProducto = idProducto;
	}
	
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
	
	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	
	public static void crearProducto(String nombre, double precio, int stock, int categoria, int idProducto) {
		ControllerProducto controller = new ControllerProducto();
		
        while (nombre.isEmpty()) {
            nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto");
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error: campo vacío");
            }
        }
           
        while (precio<=0) {
        	 precio = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el precio del producto"));
        	 if (precio<=0) {
                JOptionPane.showMessageDialog(null, "Error");
                }
            }
         
         while (stock<0) {
        	 stock = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el stock del producto"));
             if (stock<0) {
                JOptionPane.showMessageDialog(null, "Error");
                }
            }
         
         Categorias categoriaSeleccionada = (Categorias) JOptionPane.showInputDialog(null, "¿En qué categoría entra tu producto?", "Jumbox", JOptionPane.QUESTION_MESSAGE, null,Categorias.values(), Categorias.values()[0]);

         int fk_categoria = categoriaSeleccionada.getId();

            
        
         Productos producto = new Productos(idProducto, nombre, precio, stock, fk_categoria);
        controller.agregarProducto(producto);
	}

	@Override
	public String toString() {
		return nombre;
	}

	

	
	
	
}
