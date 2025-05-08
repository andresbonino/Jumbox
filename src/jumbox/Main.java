package jumbox;


import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		
		
		Conexion.getInstance();
		int opcion = 0;

		do {
			
			opcion = JOptionPane.showOptionDialog(null, "¿Quien Eres?", "Jumbox", 0, 0, null, Usuarios.values(), Usuarios.values());

			switch (opcion) {
				case 0:
					//Cliente
					int opciones = 0;
					opciones = JOptionPane.showOptionDialog(null, "¿Que Quieres Hacer?", "Jumbox", 0, 0, null, OpcionesCliente.values(), OpcionesCliente.values());

					break;
				case 1:
					//Encargado Deposito
					int opciones2 = 0;
					opciones2 = JOptionPane.showOptionDialog(null, "¿Que Quieres Hacer?", "Jumbox", 0, 0, null, OpcionesDeposito.values(), OpcionesDeposito.values());

					break;
				case 2:
					//Encargado Sucursal
					int opciones3 = 0;
					opciones3 = JOptionPane.showOptionDialog(null, "¿Que Quieres Hacer?", "Jumbox", 0, 0, null, OpcionesSucursal.values(), OpcionesSucursal.values());

					break;
				case 3:
					//Salir
					break;
				default:
					break;
				}
			} while (opcion!=3);
			
		
		
	}
}
