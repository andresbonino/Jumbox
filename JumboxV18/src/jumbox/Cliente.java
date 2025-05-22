package jumbox;

import javax.swing.JOptionPane;

import DLL.ControllerUsuario;

public class Cliente {

	
	private String nombre;
	private String direccion;
	private int telefono;
	private String contrasena;
	static ControllerUsuario controller = new ControllerUsuario();
	
	public Cliente(String nombre, String direccion, int telefono, String contrasena) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.contrasena = contrasena;
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
	
	
	public static void LoginCliente(String nombre, String contrasena) {
		 //Iniciar Sesion
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
	           // Ir a menu de cliente
	           Cliente comprador = (Cliente)usuario;
	           
	           int opciones = 0;
               do {
            	   opciones = JOptionPane.showOptionDialog(null, "¿Que Quieres Hacer?", "Jumbox", 0, 0, null, OpcionesCliente.values(), OpcionesCliente.values());
            	   
            	   switch (opciones) {
				case 0: //Comprar
					
					break;

				case 1: //Ver el carrito
					
					break;
				
				case 2: //Editar carrito
					
					break;
					
				case 3: //Estado de la compra
					
					break;

				case 4: //Salir
					JOptionPane.showMessageDialog(null, "Saliendo...");
					break;

				}
               } while (opciones!=4);
	            
	        } else {
	            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
	        }
		}
	
	
	public static void RegistroCliente(String nombre, String contrasena, String direccion, int telefono) {
		//Registrarse
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
