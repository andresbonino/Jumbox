package repository;

import java.util.LinkedList;
import java.util.List;

import jumbox.Carrito;
import jumbox.Cliente;
import jumbox.Productos;

public interface CarritoRepository {
	void compras(LinkedList<Productos> productos, LinkedList<Carrito> carrito);
	void verCarrito(LinkedList<Carrito> carrito);
	void editarCarrito(LinkedList<Carrito> carrito);
	void verCompra();
}
