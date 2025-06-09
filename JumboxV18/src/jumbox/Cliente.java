package jumbox;

import javax.swing.JOptionPane;
import java.util.LinkedList;
import DDL.ControllerUsuario;
import GUI.MenuCliente;
import DDL.ControllerCarrito;
import DDL.ControllerProducto;

public class Cliente {

	private int id_cliente;
	private static String nombre;
	private String direccion;
	private int telefono;
	private String contrasena;
	static ControllerUsuario controller = new ControllerUsuario();
	static ControllerCarrito controllerCarr = new ControllerCarrito();
	
	public Cliente(String nombre, String direccion, int telefono, String contrasena) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.contrasena = contrasena;
	}
	
	public Cliente(int id_cliente, String nombre, String direccion, int telefono, String contrasena) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.contrasena = contrasena;
		this.id_cliente = id_cliente;
	}

	public static String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	
	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public int getIdCliente() {
		return id_cliente;
	}

	public void setIdCliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	
	
	public static void LoginCliente(String nombre, String contrasena) {		

	        Cliente usuario = controller.login(nombre, contrasena);
	        if (usuario != null) {
	        	MenuCliente menu = new MenuCliente();
	        	menu.setVisible(true);
	        // JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombre());
	           // IR A MENU CLIENTE
	        // ControllerProducto<Productos> controllerProducto = new ControllerProducto<>();
	        // LinkedList<Productos> producto = controllerProducto.mostrarProducto();  
	        // LinkedList<Carrito> carrito = new LinkedList<>();
	        //   int opciones = 0;        	   
	        //   
            //   do {
           // 	   opciones = JOptionPane.showOptionDialog(null, "¿Que Quieres Hacer?", "Jumbox", 0, 0, null, OpcionesCliente.values(), OpcionesCliente.values());
           // 	   switch (opciones) {
			//		case 0: //COMPRAR
			//			controllerCarr.compras(producto,  usuario);
			//			break;
	//
			//		case 1: //VER CARRITO
			//			controllerCarr.verCarrito(usuario, null);
			//			break;
			//			
			//		case 2: //EDITAR CARRITO
			//			controllerCarr.editarCarrito();
			//			break;
			//			
			//		case 3: //ESTADO DE LA COMPRA
			//			
			//			break;
			//			
			//		case 4: //SALIR
			//			JOptionPane.showMessageDialog(null, "Saliendo...");
			//			break;
			//		}
			//} while (opciones!=4);
	            
	        } else {
	            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
	        }
		}
	
	
	public static Cliente RegistroCliente(String nombre, String contrasena, String direccion, int telefono) {
		 	Cliente usuario = new Cliente(nombre, direccion, telefono, contrasena);
		    controller.verificarUsuario(usuario);
			return usuario;
			
	}
	
}
