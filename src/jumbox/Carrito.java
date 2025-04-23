import java.util.LinkedList;

public class Carrito {
	
	LinkedList<Productos> Carrito = new LinkedList<>();

	public Carrito(LinkedList<Productos> carrito) {
		Carrito = carrito;
	}

	public LinkedList<Productos> getCarrito() {
		return Carrito;
	}

	public void setCarrito(LinkedList<Productos> carrito) {
		Carrito = carrito;
	}

}
