package repository;

import java.util.LinkedList;
import java.util.List;

import jumbox.Carrito;
import jumbox.Cliente;
import jumbox.Productos;

public interface CarritoRepository {
	void compras(LinkedList<Productos> productos,  Cliente cliente);
	void verCarrito();
	void editarCarrito();
	void verCompra();
}
