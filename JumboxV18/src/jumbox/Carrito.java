package jumbox;

public class Carrito{
	
	private Productos producto;
    private int cantidad;
    private Cliente cliente;
    

    public Carrito(Productos producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    

    public Carrito(Productos producto, int cantidad, Cliente cliente) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
		this.cliente = cliente;
	}

    


	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}



	public void setProducto(Productos producto) {
		this.producto = producto;
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
