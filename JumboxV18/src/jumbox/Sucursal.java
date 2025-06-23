package jumbox;

import javax.swing.JOptionPane;

import DDL.ControllerPedidoSucursal;
import DDL.ControllerSucursal;
import GUI.MenuSucursal;

public class Sucursal {

	private int id_sucursal;
	private String contrasena;
	static ControllerSucursal controllerS = new ControllerSucursal();
	static ControllerPedidoSucursal controllerPS = new ControllerPedidoSucursal();
	
	public Sucursal(int id_sucursal, String contrasena) {
		this.id_sucursal = id_sucursal;
		this.contrasena = contrasena;
	}

	public int getId_Sucursal() {
		return id_sucursal;
	}

	public void setId_Sucursal(int id_sucursal) {
		this.id_sucursal = id_sucursal;
	}
	
	
	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public static void IngresoSucursal(int idSucursal, String contrasena) {
	    Sucursal usuario2 = controllerS.loginSucursal(idSucursal, contrasena);
	    if (usuario2 != null) {
	        
	        MenuSucursal menu = new MenuSucursal(usuario2);
	        menu.setVisible(true);
	    } else {
	        JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	
}
