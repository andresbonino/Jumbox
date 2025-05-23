package DLL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import jumbox.Carrito;
import jumbox.Cliente;
import jumbox.Productos;
import repository.CarritoRepository;


public class ControllerCarrito <T extends Carrito> implements CarritoRepository{
	static ControllerUsuario controller = new ControllerUsuario();
	private static Connection con = Conexion.getInstance().getConnection();
	
	@Override
	public void compras(LinkedList<Productos> productos, LinkedList<Carrito> carrito) {
		if (productos.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No hay productos disponibles.");
	        return;
	    }else {
			String[] nombres = new String[productos.size()];
		    for (int i = 0; i < productos.size(); i++) {
		        nombres[i] = productos.get(i).getNombre();
		    }
	
		    String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione producto:", "Agregar al carrito", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);
	
		    Productos productoElegido = null;
		    for (Productos p : productos) {
		        if (p.getNombre().equals(seleccion)) {
		            productoElegido = p;
		            break;
		        }
		    }
	
		    if (productoElegido != null) {
		    	
		    	int Cant=0;
                String cantidad;
				do {
					cantidad = JOptionPane.showInputDialog("Cantidad:", null);
					if (!cantidad.isEmpty()) {
						Cant = Integer.parseInt(cantidad);
						if (Cant > productoElegido.getStock()) {
							JOptionPane.showMessageDialog(null, "No hay suficiente stock.");
						}else if(Cant<=0){
							JOptionPane.showMessageDialog(null, "No puede ser menor o igual a 0");
							return;
						}else {
		                    carrito.add(new Carrito(productoElegido, Cant));
		                    JOptionPane.showMessageDialog(null, "Producto agregado al carrito.");
		                }
					}
				} while (cantidad.isEmpty() || Cant<=0 || Cant > productoElegido.getStock());
		    }
		
	    }
	}

	@Override
	public void verCarrito(LinkedList<Carrito> carrito) {
		if (carrito.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El carrito está vacío.");
	        return;
	    }

	    StringBuilder resumen = new StringBuilder("Carrito de Compras:\n");
	    double total = 0;

	    for (Carrito item : carrito) {
	        resumen.append(item.getProducto().getNombre()).append(" x ").append(item.getCantidad()).append(" = $").append(item.getTotal()).append("\n");
	        total += item.getTotal();
	    }

	    resumen.append("\nTOTAL: $").append(total);
	    JOptionPane.showMessageDialog(null, resumen.toString());
		
	}

	@Override
	public void editarCarrito(LinkedList<Carrito> carrito) {
		if (carrito.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El carrito está vacío.");
	        return;
	    }

	    String[] nombres = new String[carrito.size()];
	    for (int i = 0; i < carrito.size(); i++) {
	        nombres[i] = carrito.get(i).getProducto().getNombre() + " (x" + carrito.get(i).getCantidad() + ")";
	    }

	    String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione un producto a editar:", "Editar Carrito", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);

	    if (seleccion != null) {
	    	Carrito itemSeleccionado = null;
	        for (Carrito item : carrito) {
	            String nombreItem = item.getProducto().getNombre() + " (x" + item.getCantidad() + ")";
	            if (nombreItem.equals(seleccion)) {
	                itemSeleccionado = item;
	                break;
	            }
	        }

	        if (itemSeleccionado != null) {
	            String[] opciones = {"Cambiar cantidad", "Eliminar del carrito"};
	            int opcion = JOptionPane.showOptionDialog(null, "¿Qué desea hacer con " + itemSeleccionado.getProducto().getNombre() + "?", "Opciones",
	                    0, 0, null, opciones, opciones[0]);

	            if (opcion == 0) {
	                int nuevaC=0;
	                String nuevaCantidad;
					do {
						nuevaCantidad = JOptionPane.showInputDialog("Nueva Catidad:", itemSeleccionado.getCantidad());
						if (!nuevaCantidad.isEmpty()) {
							nuevaC = Integer.parseInt(nuevaCantidad);
							if (nuevaC > itemSeleccionado.getProducto().getStock()) {
								JOptionPane.showMessageDialog(null, "No hay suficiente stock.");
							}else if(nuevaC<=0){
								JOptionPane.showMessageDialog(null, "No puede ser menor o igual a 0");
								return;
							}else {
							}
			                    itemSeleccionado.setCantidad(nuevaC);
			                    JOptionPane.showMessageDialog(null, "Cantidad actualizada.");
			                
						}
					} while (nuevaCantidad.isEmpty() || nuevaC<=0 || nuevaC > itemSeleccionado.getProducto().getStock());
					
	            } else {
	                carrito.remove(itemSeleccionado);
	                JOptionPane.showMessageDialog(null, "Producto eliminado del carrito.");
	            }
	        }
	    }
		
	}

	@Override
	public void verCompra() {
		// TODO Auto-generated method stub
		
	}
}
