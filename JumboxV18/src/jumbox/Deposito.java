package jumbox;

import javax.swing.JOptionPane;

import DDL.ControllerDeposito;
import DDL.ControllerProducto;
import GUI.PantallaPrincipal;

public class Deposito {
	
	
	private String contrasena;
	static ControllerDeposito controllerD = new ControllerDeposito();
	static ControllerProducto controllerP = new ControllerProducto();
	
	public Deposito(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public static void IngresoDeposito(String contrasena) {
		
        
        Deposito usuario = controllerD.loginDeposito(contrasena);
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
           			Productos.crearProducto("", 0, -1, 0, 0);
           			break; 
           		case 2: //Editar Producto
           				controllerP.editar();
           			break; 
           		case 3: //Ver Stock
           				controllerP.verStock();	
           			break;
           		case 4: //Salir
           			PantallaPrincipal menu = new PantallaPrincipal();
           	        menu.setVisible(true);
           			break;
           		default:
           			break;
           		}
        } while (opciones2!=4);

        }
	}
}
