package jumbox;


public class Sucursal {

	private int numero;
	private String contrasena;
	
	public Sucursal(int numero, String contrasena) {
		this.numero = numero;
		this.contrasena = contrasena;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
}
