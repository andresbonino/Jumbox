package jumbox;

public class Carrito{
	
	private Productos producto;
    private int cantidad;

    public Carrito(Productos producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Productos getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return producto.getPrecio() * cantidad;
    }

}
