package DDL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import jumbox.Productos;
import jumbox.Sucursal;
import repository.SucursalRepository;

public class ControllerSucursal{
	
    private static Connection con = Conexion.getInstance().getConnection();

    public Sucursal loginSucursal(int id_sucursal, String contrasena) {
        Sucursal EncargadoS = null;
        try {
            PreparedStatement stmt = con.prepareStatement(
            		"SELECT * FROM sucursal WHERE id_sucursal = ? AND contrasena = ?"
            );
            stmt.setInt(1, id_sucursal);
            stmt.setString(2, contrasena);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                EncargadoS = new Sucursal(id_sucursal, contrasena);
                       
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EncargadoS;
    }
    
    
    public LinkedList<Productos> mostrarAlmacen() {
        LinkedList<Productos> producto = new LinkedList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM producto");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                Double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
               
                Productos p = new Productos(nombre, precio, stock);
                p.setIdProducto(id);
                producto.add(p);
                        
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producto;
    }
    public void gestionarPedidos(int idSucursal) {
        try {
            // 1. Buscar pedidos pendientes
            PreparedStatement psPedidos = con.prepareStatement(
                "SELECT p.id_pedido, c.nombre, p.fecha FROM pedido p " +
                "JOIN cliente c ON p.fk_cliente = c.id_cliente " +
                "WHERE p.estado = 'pendiente' AND p.fk_sucursal = ?"
            );
            psPedidos.setInt(1, idSucursal);
            ResultSet rsPedidos = psPedidos.executeQuery();

            ArrayList<Integer> idsPedidos = new ArrayList<>();
            ArrayList<String> resumenes = new ArrayList<>();
            ArrayList<String> opciones = new ArrayList<>();

            while (rsPedidos.next()) {
                int idPedido = rsPedidos.getInt("id_pedido");
                String cliente = rsPedidos.getString("nombre");
                Date fecha = rsPedidos.getDate("fecha");

                StringBuilder resumen = new StringBuilder("Pedido #" + idPedido + " - " + cliente + " - " + fecha + "\n");

                PreparedStatement psDetalles = con.prepareStatement(
                    "SELECT dp.cantidad, dp.fk_producto, p.nombre FROM detalles_pedido dp " +
                    "JOIN producto p ON dp.fk_producto = p.id_producto " +
                    "WHERE dp.fk_pedido = ?"
                );
                psDetalles.setInt(1, idPedido);
                ResultSet rsDetalles = psDetalles.executeQuery();

                while (rsDetalles.next()) {
                    int cantidadPedida = rsDetalles.getInt("cantidad");
                    int idProducto = rsDetalles.getInt("fk_producto");
                    String nombreProducto = rsDetalles.getString("nombre");

                    PreparedStatement psStock = con.prepareStatement(
                        "SELECT cantidad FROM almacen_sucursal WHERE fk_producto = ? AND fk_sucursal = ?"
                    );
                    psStock.setInt(1, idProducto);
                    psStock.setInt(2, idSucursal);
                    ResultSet rsStock = psStock.executeQuery();

                    int stock = 0;
                    if (rsStock.next()) {
                        stock = rsStock.getInt("cantidad");
                    }

                    resumen.append(" - ").append(nombreProducto)
                           .append(": Cantidad ").append(cantidadPedida)
                           .append(" | Stock: ").append(stock).append("\n");
                }

                idsPedidos.add(idPedido);
                resumenes.add(resumen.toString());
                opciones.add("Pedido #" + idPedido + " - " + cliente);
            }

            if (resumenes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay pedidos pendientes.");
                return;
            }

            StringBuilder vistaGeneral = new StringBuilder("Pedidos pendientes:\n\n");
            for (String resumen : resumenes) {
                vistaGeneral.append(resumen).append("\n");
            }

            JOptionPane.showMessageDialog(null, vistaGeneral.toString());

            String seleccion = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione un pedido para gestionar:",
                "Gestión de pedidos",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones.toArray(),
                opciones.get(0)
            );

            if (seleccion == null) return;

            int indexSeleccionado = opciones.indexOf(seleccion);
            int idPedidoSeleccionado = idsPedidos.get(indexSeleccionado);

            // Validar stock antes de continuar
            PreparedStatement psDetallesFinal = con.prepareStatement(
                "SELECT cantidad, fk_producto FROM detalles_pedido WHERE fk_pedido = ?"
            );
            psDetallesFinal.setInt(1, idPedidoSeleccionado);
            ResultSet rsFinal = psDetallesFinal.executeQuery();

            boolean stockSuficiente = true;
            StringBuilder erroresStock = new StringBuilder("No hay stock suficiente para:\n");

            while (rsFinal.next()) {
                int cantidad = rsFinal.getInt("cantidad");
                int idProducto = rsFinal.getInt("fk_producto");

                PreparedStatement psCheck = con.prepareStatement(
                    "SELECT cantidad FROM almacen_sucursal WHERE fk_producto = ? AND fk_sucursal = ?"
                );
                psCheck.setInt(1, idProducto);
                psCheck.setInt(2, idSucursal);
                ResultSet rsCheck = psCheck.executeQuery();

                int stock = 0;
                if (rsCheck.next()) {
                    stock = rsCheck.getInt("cantidad");
                }

                if (stock < cantidad) {
                    stockSuficiente = false;

                    // Obtener nombre producto
                    PreparedStatement psNombre = con.prepareStatement("SELECT nombre FROM producto WHERE id_producto = ?");
                    psNombre.setInt(1, idProducto);
                    ResultSet rsNombre = psNombre.executeQuery();
                    String nombre = rsNombre.next() ? rsNombre.getString("nombre") : "Producto #" + idProducto;

                    erroresStock.append("- ").append(nombre).append(" (pedidos: ").append(cantidad)
                                .append(", stock: ").append(stock).append(")\n");
                }
            }

            if (!stockSuficiente) {
                JOptionPane.showMessageDialog(null, erroresStock.toString(), "Stock insuficiente", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Confirmación final
            int confirmar = JOptionPane.showConfirmDialog(
                null,
                resumenes.get(indexSeleccionado) + "\n¿Desea enviar este pedido?",
                "Confirmar envío",
                JOptionPane.YES_NO_OPTION
            );
            if (confirmar != JOptionPane.YES_OPTION) return;

            // DESCONTAR STOCK EN EL ALMACEN SUCURSAL
            rsFinal.beforeFirst();
            while (rsFinal.next()) {
                int cantidad = rsFinal.getInt("cantidad");
                int idProducto = rsFinal.getInt("fk_producto");

                PreparedStatement psDescontar = con.prepareStatement(
                    "UPDATE almacen_sucursal SET cantidad = cantidad - ? WHERE fk_producto = ? AND fk_sucursal = ?"
                );
                psDescontar.setInt(1, cantidad);
                psDescontar.setInt(2, idProducto);
                psDescontar.setInt(3, idSucursal);
                psDescontar.executeUpdate();
            }

            // Marcar como enviado
            PreparedStatement psActualizar = con.prepareStatement(
                "UPDATE pedido SET estado = 'enviado' WHERE id_pedido = ?"
            );
            psActualizar.setInt(1, idPedidoSeleccionado);
            psActualizar.executeUpdate();

            JOptionPane.showMessageDialog(null, "Pedido #" + idPedidoSeleccionado + " enviado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al gestionar pedidos.");
        }
    }

}
