package repository;

import java.util.LinkedList;
import java.util.List;

import jumbox.Carrito;
import jumbox.Cliente;
import jumbox.Productos;
import jumbox.Sucursal;

public interface CarritoRepository {
	void compras(LinkedList<Productos> productos,  Cliente cliente);
	void verCarrito(Cliente cliente, Sucursal sucursal);
	void editarCarrito();
	void verCompra();
}
