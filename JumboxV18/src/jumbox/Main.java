package jumbox;


import javax.swing.JOptionPane;

import DDL.Conexion;
import DDL.ControllerDeposito;
import DDL.ControllerProducto;
import DDL.ControllerSucursal;
import DDL.ControllerUsuario;

public class Main {

	public static void main(String[] args) {
		
		ControllerUsuario controller = new ControllerUsuario();
		ControllerDeposito controllerD = new ControllerDeposito();
		ControllerSucursal controllerS = new ControllerSucursal();
		ControllerProducto controllerP = new ControllerProducto();
		
		Conexion.getInstance();
		int opcion = 0;

		do {
			
			opcion = JOptionPane.showOptionDialog(null, "¿Quien Eres?", "Jumbox", 0, 0, null, Usuarios.values(), Usuarios.values());

			switch (opcion) {
				case 0: //Cliente
					int opcionR = 0;
					opcionR = JOptionPane.showOptionDialog(null, "¿Que Quieres Hacer?", "Jumbox", 0, 0, null, Registro.values(), Registro.values());
					if (opcionR==0) { //Iniciar Sesion
						String nombre = "";
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
	                       opciones = JOptionPane.showOptionDialog(null, "¿Que Quieres Hacer?", "Jumbox", 0, 0, null, OpcionesCliente.values(), OpcionesCliente.values());

	                        
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
	                    }
					} else { //Registrarse
						String nombre = "";
	                    while (nombre.isEmpty()) {
	                        nombre = JOptionPane.showInputDialog("Ingrese nombre");
	                        if (nombre.isEmpty()) {
	                            JOptionPane.showMessageDialog(null, "Error: campo vacío");
	                        }
	                    }
	                    
	                    String direccion = "";
	                    while (direccion.isEmpty()) {
	                    	direccion = JOptionPane.showInputDialog("Ingrese su direccion");
	                        if (direccion.isEmpty()) {
	                            JOptionPane.showMessageDialog(null, "Error: campo vacío");
	                        }
	                    }
	                    
	                    int telefono = 0;
	                    while (telefono==0) {
	                    	telefono = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su telefono"));
	                        if (telefono==0) {
	                            JOptionPane.showMessageDialog(null, "Error: campo = 0");
	                        }
	                    }
	                    
	                    String contrasena = "";
	                    while (contrasena.isEmpty()) {
	                        contrasena = JOptionPane.showInputDialog("Ingrese contraseña");
	                        if (contrasena.isEmpty()) {
	                            JOptionPane.showMessageDialog(null, "Error: campo vacío");
	                        }
	                    }
	                    Cliente usuario = new Cliente(nombre, direccion, telefono, contrasena);
	                    controller.verificarUsuario(usuario);
					}

					break;
					
					
					
					
				case 1: //Encargado Deposito
					String contrasenia = "";
                    while (contrasenia.isEmpty()) {
                        contrasenia = JOptionPane.showInputDialog("Ingrese contraseña");
                        if (contrasenia.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Error: campo vacío");
                        }
                    }
                    
                    Deposito usuario = controllerD.loginDeposito(contrasenia);
                    if (usuario != null) {
                     JOptionPane.showMessageDialog(null, "Bienvenido al Deposito");
                       // Ir a menu del encargado del deposito
                     Deposito encargadoD = (Deposito)usuario; 
                       int opciones2 = 0;
                       do {
                       opciones2 = JOptionPane.showOptionDialog(null, "¿Que Quieres Hacer?", "Jumbox", 0, 0, null, OpcionesDeposito.values(), OpcionesDeposito.values());
                       		switch (opciones2) {
                       		case 0: //Armar Envio
                       			
                       			break;
                       		case 1: //Crear Producto
                       				Productos.crearProducto("", 0, 0);
                       			break; 
                       		case 2: //Editar Producto
        						
                       			break; 
                       		case 3: //Ver Stock
                       			controllerP.verStock();	
                       			break;
                       		case 4: //Salir
                       			JOptionPane.showMessageDialog(null, "Saliendo del depósito...");
                       			break;
                       		default:
                       			break;
                       		}
                    } while (opciones2!=4);
                    } else {
                        JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    }
					break;
					
					
					
				case 2: //Encargado Sucursal
					String contraseniaS = "";
                    while (contraseniaS.isEmpty()) {
                        contraseniaS = JOptionPane.showInputDialog("Ingrese contraseña");
                        if (contraseniaS.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Error: campo vacio");
                        }
                    }
                    
                    Sucursal usuario2 = controllerS.loginSucursal(contraseniaS);
                    if (usuario2 != null) {
                     JOptionPane.showMessageDialog(null, "Bienvenido a la Sucursal");
                       // Ir a menu de la sucursal
                     Sucursal encargadoS = (Sucursal)usuario2; 
                     int opciones3 = 0;
                     opciones3 = JOptionPane.showOptionDialog(null, "¿Que Quieres Hacer?", "Jumbox", 0, 0, null, OpcionesSucursal.values(), OpcionesSucursal.values());
 					
                    } else {
                        JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    }
					break;
					
					
					
				case 3: //Salir
					JOptionPane.showMessageDialog(null, "Saliendo de la app...");
					break;
				default:
					break;
				}
			} while (opcion!=3);
			
		
		
	}
}
