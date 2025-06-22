package GUI;


import java.util.LinkedList;

import javax.swing.JOptionPane;

<<<<<<< Updated upstream
import DDL.Conexion;
import DDL.ControllerCarrito;
import DDL.ControllerDeposito;
import DDL.ControllerPedidoSucursal;
import DDL.ControllerProducto;
import DDL.ControllerSucursal;
import DDL.ControllerUsuario;
=======
import DLL.Conexion;
import DLL.ControllerCarrito;
import DLL.ControllerDeposito;
import DLL.ControllerPedidoSucursal;
import DLL.ControllerProducto;
import DLL.ControllerSucursal;
import DLL.ControllerUsuario;
>>>>>>> Stashed changes
import jumbox.Cliente;
import jumbox.Deposito;
import jumbox.OpcionesCliente;
import jumbox.OpcionesDeposito;
import jumbox.OpcionesSucursal;
import jumbox.OpcionesSucursales;
import jumbox.Productos;
import jumbox.Registro;
import jumbox.Sucursal;
import jumbox.Usuarios;

public class Main {

	public static void main(String[] args) {
		
		ControllerUsuario controller = new ControllerUsuario();
		ControllerDeposito controllerD = new ControllerDeposito();
		ControllerSucursal controllerS = new ControllerSucursal();
		ControllerProducto controllerP = new ControllerProducto();
		ControllerPedidoSucursal controllerPS = new ControllerPedidoSucursal();
		ControllerCarrito controllerC = new ControllerCarrito();
		
		Conexion.getInstance();
		int opcion = 0;

		do {
			
			opcion = JOptionPane.showOptionDialog(null, "¿Quien Eres?", "Jumbox", 0, 0, null, Usuarios.values(), Usuarios.values());

			switch (opcion) {
				case 0: //CLIENTE
					int opcionR = 0;
					opcionR = JOptionPane.showOptionDialog(null, "¿Que Quieres Hacer?", "Jumbox", 0, 0, null, Registro.values(), Registro.values());
					if (opcionR==0) { //INICIAR SESION
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
							usuario.verificarPedidosEnviados();
							Cliente comprador = (Cliente)usuario;

							LinkedList<Productos> listaProductos = controllerP.mostrarProducto();

							int opciones = 0;
							do {

								opciones = JOptionPane.showOptionDialog(null, "¿Qué deseas hacer?", "Menú Cliente", 0, 0, null,
									OpcionesCliente.values(), OpcionesCliente.values());
								
								switch (opciones) {
									case 0: // COMPRAR
										controllerC.compras(listaProductos, comprador);
										break;

									case 1: // VER_CARRITO
										Sucursal idSucursal = new Sucursal(0, "");
										idSucursal.getId_Sucursal();
										controllerC.verCarrito(comprador, idSucursal);
										break;

									case 2: // EDITAR CARRITO
										controllerC.editarCarrito();
										break;
										
									case 3: // MI COMPRA
										controllerC.verCompra();

									case 4: // SALIR 
										
										JOptionPane.showMessageDialog(null, "Saliendo...");
										break;
										
								}
							} while (opciones != 4);
	                        
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
	                    }
					} else { //REGISTRARSE
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
					
					
					
					
				case 1: //ENCARGADO DEPOSITO
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
                       // MENU DEPOSITO
                     Deposito encargadoD = (Deposito)usuario; 
                       int opciones2 = 0;
                       do {
                       opciones2 = JOptionPane.showOptionDialog(null, "¿Que Quieres Hacer?", "Jumbox", 0, 0, null, OpcionesDeposito.values(), OpcionesDeposito.values());
                       		switch (opciones2) {
                       		case 0: //ARMAR ENVIO
                       			controllerP.procesarPedidosPendientes();
                       			break;
                       		case 1: //CREAR PRODUCTO
                       			Productos.crearProducto();
                       			break; 
                       		case 2: //EDITAR PRODUCTO
                       			LinkedList<Productos> listaProductos = controllerP.mostrarProducto();
                       		    if (!listaProductos.isEmpty()) {
                       		        Productos productoSeleccionado = (Productos) JOptionPane.showInputDialog(null,"Selecciona el producto a editar", "Seleccionar Producto",JOptionPane.QUESTION_MESSAGE,null,listaProductos.toArray(),null);
                       		        if (productoSeleccionado != null) {
                       		            controllerP.editar(productoSeleccionado);
                       		        } else {
                       		            JOptionPane.showMessageDialog(null, "No se seleccionó ningún producto.");
                       		        }
                       		    } else {
                       		        JOptionPane.showMessageDialog(null, "No hay productos disponibles para editar.");
                       		    }
                       		    break;
                       		case 3: //VER STOCK
                       			controllerP.verStock();	
                       			break;
                       		case 4: //SALIR
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
					
					
					
				case 2: //ENCARGADO SUCURSAL
					
					OpcionesSucursales opcionesSucursales = (OpcionesSucursales) JOptionPane.showInputDialog(null, "¿A que Sucursal quieres acceder?", "Jumbox", JOptionPane.QUESTION_MESSAGE, null,OpcionesSucursales.values(), OpcionesSucursales.values()[0]);

			        int id_sucursal = opcionesSucursales.getId();

					
					String contraseniaS = "";
                    while (contraseniaS.isEmpty()) {
                        contraseniaS = JOptionPane.showInputDialog("Ingrese contraseña");
                        if (contraseniaS.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Error: campo vacio");
                        }
                    }
                    
                    Sucursal usuario2 = controllerS.loginSucursal(id_sucursal, contraseniaS);
                    if (usuario2 != null) {
                     JOptionPane.showMessageDialog(null, "Bienvenido a la Sucursal");
                       // MENU SUCURSAL
                     Sucursal encargadoS = (Sucursal)usuario2; 
                     int opciones3 = 0;
                     do {
                    	 opciones3 = JOptionPane.showOptionDialog(null, "¿Que Quieres Hacer?", "Jumbox", 0, 0, null, OpcionesSucursal.values(), OpcionesSucursal.values());
                    	 
                    	 switch (opciones3) {
						case 0: //CONSULTAR PRODUCTOS
								controllerPS.generarPedido(usuario2);
							break;
								
						case 1: //GESTIONAR PEDIDOS
							controllerS.gestionarPedidos(id_sucursal);
							break;
							
						case 2:
							JOptionPane.showMessageDialog(null, "Saliendo de las sucursales...");
							break;
						}
					} while (opciones3!=2);
 					
                    } else {
                        JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    }
					break;
					
					
					
				case 3: //SALIR
					JOptionPane.showMessageDialog(null, "Saliendo de la app...");
					break;
				default:
					break;
				}
			} while (opcion!=3);
			
		
		
	}
}
