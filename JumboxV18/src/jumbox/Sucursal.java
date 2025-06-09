package jumbox;


public class Sucursal {

	private int id_sucursal;
	private String contrasena;
	
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
	
}
