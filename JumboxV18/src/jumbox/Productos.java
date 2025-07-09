package jumbox;



import javax.swing.JOptionPane;

import DLL.ControllerProducto;
import DLL.ControllerUsuario;
import GUI.AgregarProducto;
import GUI.MenuCliente;

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
	
	public Productos(String nombre, double precio, int stock, int categoria) {
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
		this.categoria = categoria;
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
	
	public static void crearProducto() {
	    AgregarProducto menu = new AgregarProducto();
	    menu.setVisible(true);
	}


	

	
	
	
}
