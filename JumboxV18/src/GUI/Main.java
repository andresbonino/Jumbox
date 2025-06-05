package GUI;

import javax.swing.JOptionPane;
import java.util.LinkedList;

import DLL.Conexion;
import DLL.ControllerCarrito;
import DLL.ControllerDeposito;
import DLL.ControllerProducto;
import DLL.ControllerSucursal;
import DLL.ControllerUsuario;
import jumbox.Carrito;
import jumbox.Cliente;
import jumbox.Deposito;
import jumbox.OpcionesCliente;
import jumbox.OpcionesDeposito;
import jumbox.OpcionesSucursal;
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
		ControllerCarrito controllerC = new ControllerCarrito();

		Conexion.getInstance();
		int opcion = 0;

;
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
							
							LinkedList<Carrito> carrito = new LinkedList<>();
<<<<<<< HEAD

			                ControllerCarrito controllerCarrito = new ControllerCarrito();
			                ControllerProducto controllerProducto = new ControllerProducto();
			                controllerC.cargarCarritoDesdeBD(carrito, usuario);
							
							Cliente comprador = (Cliente)usuario;
							controllerC.cargarCarritoDesdeBD(carrito, comprador);
							LinkedList<Productos> listaProductos = controllerP.mostrarProducto();

							
							controllerC.cargarCarritoDesdeBD(carrito, comprador);
							LinkedList<Productos> listaProductos1 = controllerP.mostrarProducto();
=======
							
							
							controllerC.cargarCarritoDesdeBD(carrito, comprador);
							LinkedList<Productos> listaProductos = controllerP.mostrarProducto();
>>>>>>> 18da00c31858eaba0c7ab70f96ea9509212ac361

							int opciones = 0;
							do {
								opciones = JOptionPane.showOptionDialog(null, "¿Qué deseas hacer?", "Menú Cliente", 0, 0, null,
									OpcionesCliente.values(), OpcionesCliente.values());

								LinkedList<Productos> productosActualizados = controllerProducto.mostrarProducto();	
								
								switch (opciones) {
									case 0: // VER_CARRITO
										controllerC.verCarrito(carrito);
										break;

									case 1: // AGREGAR_PRODUCTO
										controllerC.compras(listaProductos1, carrito, comprador);
										break;

									case 2: // EDITAR CARRITO
										controllerC.editarCarrito(carrito);
										break;
										
									case 3: // REALIZAR COMPRA
										controllerC.realizarCompra(carrito, comprador);

									case 4: // SALIR
										
										controllerC.verCompra();
										break;
										
										
								}
							} while (opciones != 3);

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
						while (telefono == 0) {
							telefono = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su telefono"));
							if (telefono == 0) {
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

						Cliente nuevoUsuario = new Cliente(nombre, direccion, telefono, contrasena);
						controller.verificarUsuario(nuevoUsuario);
					}
					break;

				case 1: //Encargado Deposito
					
					break;
				case 2: //Encargado Sucursal
					
					break;
				case 3: //Salir
					JOptionPane.showMessageDialog(null, "Saliendo de la app...");
					break;
				default:
					break;
			}
		} while (opcion != 3);
	}
}