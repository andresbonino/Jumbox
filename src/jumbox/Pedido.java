package jumbox;

import java.time.LocalDate;
import java.util.LinkedList;

public class Pedido {

	
	private LocalDate fecha;
	private double total;
	private Cliente cliente;
	private boolean estado;
	LinkedList<Productos> Carrito = new LinkedList<>();

	
	public Pedido(LocalDate fecha, double total, Cliente cliente, boolean estado, LinkedList<Productos> carrito) {
		this.fecha = fecha;
		this.total = total;
		this.cliente = cliente;
		this.estado = estado;
		Carrito = carrito;
	}


	public LocalDate getFecha() {
		return fecha;
	}


	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public boolean isEstado() {
		return estado;
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}


	public LinkedList<Productos> getCarrito() {
		return Carrito;
	}


	public void setCarrito(LinkedList<Productos> carrito) {
		Carrito = carrito;
	}

	
	
	
	
}
