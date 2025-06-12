package repository;

import java.util.List;
import jumbox.Productos;

public interface ProductoRepository {
    void agregarProducto(Productos Producto);
    List<Productos> mostrarProducto();
    void verStock();
    void editarProducto(Productos Producto);
    void editar(Productos Producto);
}
