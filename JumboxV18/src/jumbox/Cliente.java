package jumbox;

import javax.swing.JOptionPane;
import java.util.LinkedList;
import DDL.ControllerUsuario;

import DDL.ControllerCarrito;
import DDL.ControllerProducto;

public class Cliente {

	private int id_cliente;
	private String nombre;
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

	public String getNombre() {
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
		 //INICIAR SESION
			nombre = "";
	        while (nombre.isEmpty()) {
	            nombre = JOptionPane.showInputDialog("Ingrese nombre");
	            if (nombre.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Error: campo vacío");
	            }
	        }

	        String contrasenia = "";
	        while (contrasenia.isEmpty()) {
	            contrasenia = JOptionPane.showInputDialog("Ingrese contraseña");
	            if (contrasenia.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Error: campo vacío");
	            }
	        }

	        Cliente usuario = controller.login(nombre, contrasenia);
	        if (usuario != null) {
	         JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombre());
	           // IR A MENU CLIENTE
	         ControllerProducto<Productos> controllerProducto = new ControllerProducto<>();
	         LinkedList<Productos> producto = controllerProducto.mostrarProducto();  
	         LinkedList<Carrito> carrito = new LinkedList<>();
	           int opciones = 0;        	   
	           
               do {
            	   opciones = JOptionPane.showOptionDialog(null, "¿Que Quieres Hacer?", "Jumbox", 0, 0, null, OpcionesCliente.values(), OpcionesCliente.values());
            	   switch (opciones) {
					case 0: //COMPRAR
						controllerCarr.compras(producto,  usuario);
						break;
	
					case 1: //VER CARRITO
						controllerCarr.verCarrito(usuario, null);
						break;
						
					case 2: //EDITAR CARRITO
						controllerCarr.editarCarrito();
						break;
						
					case 3: //ESTADO DE LA COMPRA
						
						break;
						
					case 4: //SALIR
						JOptionPane.showMessageDialog(null, "Saliendo...");
						break;
					}
			} while (opciones!=4);
	            
	        } else {
	            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
	        }
		}
	
	
	public static void RegistroCliente(String nombre, String contrasena, String direccion, int telefono) {
		//REGISTRARSE
		 nombre = "";
	    while (nombre.isEmpty()) {
	        nombre = JOptionPane.showInputDialog("Ingrese nombre");
	        if (nombre.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Error: campo vacío");
	        }
	    }
	    
	     direccion = "";
	    while (direccion.isEmpty()) {
	    	direccion = JOptionPane.showInputDialog("Ingrese su direccion");
	        if (direccion.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Error: campo vacío");
	        }
	    }
	    
	     telefono = 0;
	    while (telefono<=0) {
	    	telefono = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su telefono"));
	        if (telefono<=0) {
	            JOptionPane.showMessageDialog(null, "Error: campo = 0");
	        }
	    }
	    
	     contrasena = "";
	    while (contrasena.isEmpty()) {
	        contrasena = JOptionPane.showInputDialog("Ingrese contraseña");
	        if (contrasena.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Error: campo vacío");
	        }
	    }
	    Cliente usuario = new Cliente(nombre, direccion, telefono, contrasena);
	    controller.verificarUsuario(usuario);
		}
	
}
