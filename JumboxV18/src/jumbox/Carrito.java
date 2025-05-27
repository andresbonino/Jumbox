package jumbox;

public class Carrito{
	
	private Productos producto;
    private int cantidad;
    private int id_carrito;

    public Carrito(Productos producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    public Carrito(Productos producto, int cantidad, int id_carrito) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.id_carrito = id_carrito;
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

    public int getIdCarrito() {
        return id_carrito;
    }

    public void setIdCarrito(int id_carrito) {
        this.id_carrito = id_carrito;
    }
    
    
    public double getTotal() {
        return producto.getPrecio() * cantidad;
    }

}
