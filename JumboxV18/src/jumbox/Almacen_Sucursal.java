package jumbox;

public class Almacen_Sucursal {
	
	private int fk_sucursal;
	private int fk_producto;
	private int cantidad;

	public Almacen_Sucursal(int cantidad, int fk_sucursal, int fk_producto) {
		this.cantidad = cantidad;
		this.fk_sucursal = fk_sucursal;
		this.fk_producto = fk_producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public int getFk_Sucursal() {
		return fk_sucursal;
	}

	public void setFk_Sucursal(int fk_sucursal) {
		this.fk_sucursal = fk_sucursal;
	}
	
	public int getFk_Producto() {
		return fk_producto;
	}

	public void setFk_Producto(int fk_producto) {
		this.fk_producto = fk_producto;
	}
	
}
