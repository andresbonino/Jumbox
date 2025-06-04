package repository;

import java.util.LinkedList;
import java.util.List;

import jumbox.Carrito;
import jumbox.Cliente;
import jumbox.Productos;

public interface CarritoRepository {
	void compras(LinkedList<Productos> productos, LinkedList<Carrito> carrito, Cliente cliente);
	void verCarrito(LinkedList<Carrito> carrito);
	void editarCarrito(LinkedList<Carrito> carrito);
	void verCompra();
	void guardarProductoBD(Carrito item, Cliente cliente);
	void cargarCarritoDesdeBD(LinkedList<Carrito> carrito, Cliente cliente);
	void limpiarCarritoBD(Cliente cliente);
	void realizarCompra(LinkedList<Carrito> carrito, Cliente cliente);
}
