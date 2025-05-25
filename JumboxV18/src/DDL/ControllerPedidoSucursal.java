package DLL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import jumbox.Almacen_Sucursal;
import jumbox.OpcionesSucursales;
import jumbox.Productos;
import jumbox.Sucursal;
import repository.ProductoRepository;

public class ControllerPedidoSucursal<T extends Sucursal> implements ProductoRepository {
	
    private static Connection con = Conexion.getInstance().getConnection();
	ControllerProducto controllerP = new ControllerProducto();
    
	public void almacenSucursal(Sucursal almacen, Almacen_Sucursal almacenProducto) {
	    try {
	        // Verificar si ya existe ese producto en esa sucursal
	        PreparedStatement checkStmt = con.prepareStatement(
	            "SELECT cantidad FROM almacen_sucursal WHERE fk_sucursal = ? AND fk_producto = ?"
	        );
	        checkStmt.setInt(1, almacen.getId_Sucursal());
	        checkStmt.setInt(2, almacenProducto.getFk_Producto());
	        ResultSet rs = checkStmt.executeQuery();

	        if (rs.next()) {
	            // Ya existe, sumamos la cantidad
	            int cantidadExistente = rs.getInt("cantidad");
	            int nuevaCantidad = cantidadExistente + almacenProducto.getCantidad();

	            PreparedStatement updateStmt = con.prepareStatement(
	                "UPDATE almacen_sucursal SET cantidad = ? WHERE fk_sucursal = ? AND fk_producto = ?"
	            );
	            updateStmt.setInt(1, nuevaCantidad);
	            updateStmt.setInt(2, almacen.getId_Sucursal());
	            updateStmt.setInt(3, almacenProducto.getFk_Producto());

	            updateStmt.executeUpdate();
	            System.out.println("Cantidad actualizada en Almacén Sucursal.");
	        } else {
	            // No existe, insertamos nuevo
	            PreparedStatement insertStmt = con.prepareStatement(
	                "INSERT INTO almacen_sucursal (fk_sucursal, fk_producto, cantidad) VALUES (?, ?, ?)"
	            );
	            insertStmt.setInt(1, almacen.getId_Sucursal());
	            insertStmt.setInt(2, almacenProducto.getFk_Producto());
	            insertStmt.setInt(3, almacenProducto.getCantidad());

	            insertStmt.executeUpdate();
	            System.out.println("Producto agregado al Almacén Sucursal.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


    public LinkedList<Almacen_Sucursal> mostrarAlmacen() {
        LinkedList<Almacen_Sucursal> almacen = new LinkedList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM almacen_sucursal");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int fk_producto = rs.getInt("fk_producto");
                int fk_cantidad = rs.getInt("cantidad");
               
                Almacen_Sucursal a = new Almacen_Sucursal(0, fk_producto, fk_cantidad);
                almacen.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return almacen;
    }

    public void generarPedido(Sucursal sucursalActual) {
        LinkedList<Productos> productosDeposito = controllerP.mostrarProducto();

        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT a.id_almacen_sucursal, a.fk_sucursal, a.fk_producto, a.cantidad, p.nombre, p.precio " +
                "FROM almacen_sucursal a " +
                "JOIN producto p ON a.fk_producto = p.id_producto " +
                "WHERE a.fk_sucursal = ?"  // si querés filtrar por sucursal específica
            );

            // Setear el id de la sucursal que querés mostrar
            int idSucursal = sucursalActual.getId_Sucursal(); 
            stmt.setInt(1, idSucursal);

            ResultSet rs = stmt.executeQuery();
            StringBuilder sb = new StringBuilder("Productos en el Almacén de la Sucursal:\n\n");

            boolean hayProductos = false;
            while (rs.next()) {
                hayProductos = true;

                int idAlmacen = rs.getInt("id_almacen_sucursal");
                int fkSucursal = rs.getInt("fk_sucursal");
                int fkProducto = rs.getInt("fk_producto");
                int cantidad = rs.getInt("cantidad");
                String nombreProducto = rs.getString("nombre");
                double precio = rs.getDouble("precio");

                sb.append("Producto: ").append(nombreProducto)
                  .append(" | Cantidad: ").append(cantidad)
                  .append(" | Precio: $").append(precio)
                  .append("\n");
            }

            if (!hayProductos) {
                JOptionPane.showMessageDialog(null, "No hay productos en el almacén de esta sucursal.");
                return;
            } else {
                JOptionPane.showMessageDialog(null, sb.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener productos del almacén.");
        }

        
        
        

        // 2. Preguntar si quiere hacer pedido o salir
        int opcion = JOptionPane.showOptionDialog(null, "¿Desea realizar un pedido al depósito?", "Jumbox",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"Hacer pedido", "Salir"}, "Hacer pedido");

        if (opcion != JOptionPane.YES_OPTION) {
            return; // Si elige salir, no sigue con el pedido
        }

        // 3. Iniciar proceso de pedido
        List<Productos> pedidoFinal = new LinkedList<>();
        List<Integer> cantidadesFinales = new LinkedList<>();

        boolean seguir = true;
        while (seguir) {
            // Mostrar productos para seleccionar
            String[] nombres = new String[productosDeposito.size()];
            for (int i = 0; i < productosDeposito.size(); i++) {
                nombres[i] = productosDeposito.get(i).getNombre();
            }

            String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione un producto para pedir:",
                    "Pedido a Depósito", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);

            if (seleccion != null) {
                Productos seleccionado = null;
                for (Productos prod : productosDeposito) {
                    if (prod.getNombre().equals(seleccion)) {
                        seleccionado = prod;
                        break;
                    }
                }

                if (seleccionado != null) {
                    int cantidad = -1;
                    do {
                        String inputCantidad = JOptionPane.showInputDialog("Ingrese la cantidad que desea pedir:");
                        if (inputCantidad != null && !inputCantidad.isEmpty()) {
                            try {
                                cantidad = Integer.parseInt(inputCantidad);
                            } catch (NumberFormatException e) {
                                cantidad = -1;
                            }
                        }
                    } while (cantidad <= 0);

                    pedidoFinal.add(seleccionado);
                    cantidadesFinales.add(cantidad);
                }
            }

            int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea agregar otro producto al pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
            seguir = (confirmar == JOptionPane.YES_OPTION);
        }

        try {
            // 1. Insertar el pedido general
            PreparedStatement stmtPedido = con.prepareStatement(
                "INSERT INTO pedido_reposicion (fecha, fk_sucursal) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmtPedido.setDate(1, java.sql.Date.valueOf (LocalDate.now()));
            stmtPedido.setInt(2, sucursalActual.getId_Sucursal()); // o la sucursal actual si tenés forma de pasarla
            stmtPedido.executeUpdate();
            
            ResultSet rs = stmtPedido.getGeneratedKeys();
            int idPedido = 1;
            if (rs.next()) {
                idPedido = rs.getInt(1);
            }

            // 2. Insertar cada línea del pedido
            for (int i = 0; i < pedidoFinal.size(); i++) {
                Productos prod = pedidoFinal.get(i);
                int cantidad = cantidadesFinales.get(i);

                PreparedStatement stmtDetalle = con.prepareStatement(
                    "INSERT INTO detalle_pedido_reposicion (cantidad, fk_pedido_reposicion, fk_producto) VALUES (?, ?, ?)"
                );
                stmtDetalle.setInt(1, cantidad);
                stmtDetalle.setInt(2, idPedido);
                stmtDetalle.setInt(3, prod.getIdProducto());

                stmtDetalle.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Pedido guardado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el pedido.");
        }

    }


	@Override
	public void agregarProducto(Productos Producto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Productos> mostrarProducto() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void verStock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editarProducto(Productos Producto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}

}
