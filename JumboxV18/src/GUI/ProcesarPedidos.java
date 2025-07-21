package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;

import DDL.Conexion;

public class ProcesarPedidos extends JFrame {

	private static Connection con = Conexion.getInstance().getConnection();
	private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JTextField filtroPedido;
    private JLabel lblSeleccionado;
    private JLabel lblError;
    private JLabel lblError2;
    private int pedidoSeleccionado = -1;
    private static final long serialVersionUID = 1L;
    
    /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TablaProductos frame = new TablaProductos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

    public ProcesarPedidos() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 796, 400);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("Pedidos Pendientes");
        lblTitulo.setBackground(new Color(0, 128, 0));
        lblTitulo.setForeground(new Color(0, 128, 0));
        lblTitulo.setFont(new Font("Swis721 Blk BT", Font.BOLD, 50));
        lblTitulo.setBounds(150, 8, 530, 40);
        contentPane.add(lblTitulo);

        model = new DefaultTableModel(new String[]{"ID Pedido", "Sucursal", "Producto", "Cantidad", "Stock"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 59, 744, 180);
        contentPane.add(scrollPane);

        lblSeleccionado = new JLabel("Seleccionado: Ninguno");
        lblSeleccionado.setBounds(20, 250, 600, 20);
        contentPane.add(lblSeleccionado);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(667, 331, 97, 25);
        contentPane.add(btnSalir);

        filtroPedido = new JTextField();
        filtroPedido.setBounds(20, 300, 120, 25);
        contentPane.add(filtroPedido);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(150, 300, 80, 25);
        contentPane.add(btnFiltrar);

        JButton btnProcesar = new JButton("Procesar Pedido");
        btnProcesar.setBounds(240, 300, 140, 25);
        contentPane.add(btnProcesar);
        
        JLabel lblNewLabel_2 = new JLabel("* selecciona Flitrar para actualizar.");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lblNewLabel_2.setBounds(22, 336, 208, 14);
        contentPane.add(lblNewLabel_2);
        
        lblError = new JLabel("");
        lblError.setForeground(new Color(255, 0, 0));
        lblError.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblError.setBounds(405, 281, 359, 20);
        contentPane.add(lblError);
        
        lblError2 = new JLabel("");
        lblError2.setForeground(new Color(255, 0, 0));
        lblError2.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblError2.setBounds(405, 300, 359, 20);
        contentPane.add(lblError2);

        // Evento selección de fila
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
            	lblError.setText("");
	        	lblError2.setText("");
                pedidoSeleccionado = (int) model.getValueAt(table.getSelectedRow(), 0);
                lblSeleccionado.setText("Seleccionado: Pedido #" + pedidoSeleccionado);
            }
        });

        // Botón Filtrar
        btnFiltrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		lblError.setText("");
	        	lblError2.setText("");
	            String filtro = filtroPedido.getText().trim();
	            cargarTablaFiltrada(filtro);
	            
        	}
        });

        // Botón Salir
        btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblError.setText("");
	        	lblError2.setText("");
				dispose();
			}
		});

        // Botón Procesar
        btnProcesar.addActionListener (new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		lblError.setText("");
	        	lblError2.setText("");
            if (pedidoSeleccionado != -1) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Procesar pedido #" + pedidoSeleccionado + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    armarEnvioASucursal(pedidoSeleccionado);
                    cargarPedidos();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un pedido.");
            }
        	}
        });

        cargarPedidos();
    }

    private void cargarPedidos() {
        model.setRowCount(0);
        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT p.id_pedido_reposicion, p.fk_sucursal, d.fk_producto, d.cantidad, pr.nombre, pr.stock " +
                "FROM pedido_reposicion p " +
                "JOIN detalle_pedido_reposicion d ON p.id_pedido_reposicion = d.fk_pedido_reposicion " +
                "JOIN producto pr ON pr.id_producto = d.fk_producto " +
                "ORDER BY p.id_pedido_reposicion"
            );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_pedido_reposicion"),
                    rs.getInt("fk_sucursal"),
                    rs.getString("nombre"),
                    rs.getInt("cantidad"),
                    rs.getInt("stock")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarTablaFiltrada(String filtro) {
        model.setRowCount(0);
        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT p.id_pedido_reposicion, p.fk_sucursal, d.fk_producto, d.cantidad, pr.nombre, pr.stock " +
                "FROM pedido_reposicion p " +
                "JOIN detalle_pedido_reposicion d ON p.id_pedido_reposicion = d.fk_pedido_reposicion " +
                "JOIN producto pr ON pr.id_producto = d.fk_producto " +
                "WHERE pr.nombre LIKE ? " +
                "ORDER BY p.id_pedido_reposicion"
            );
            stmt.setString(1, filtro + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_pedido_reposicion"),
                    rs.getInt("fk_sucursal"),
                    rs.getString("nombre"),
                    rs.getInt("cantidad"),
                    rs.getInt("stock")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void armarEnvioASucursal(int idPedido) {
    	try {
    		
	        // OBTENER LOS PRODUCTOS Y LAS CANTIDADES
	        PreparedStatement stmt = con.prepareStatement(
	            "SELECT d.fk_producto, d.cantidad, p.fk_sucursal " +
	            "FROM detalle_pedido_reposicion d " +
	            "JOIN pedido_reposicion p ON d.fk_pedido_reposicion = p.id_pedido_reposicion " +
	            "WHERE d.fk_pedido_reposicion = ?"
	        );
	        stmt.setInt(1, idPedido);
	        ResultSet rs = stmt.executeQuery();

	        List<Integer> productos = new ArrayList<>();
	        List<Integer> cantidades = new ArrayList<>();
	        List<Integer> stocks = new ArrayList<>();
	        int idSucursal = -1;

	        boolean stockInsuficiente = false;
	        StringBuilder mensajeStock = new StringBuilder();

	        // VERIFICAR STOCK DE PRODUCTOS
	        while (rs.next()) {
	            int idProducto = rs.getInt("fk_producto");
	            int cantidad = rs.getInt("cantidad");
	            idSucursal = rs.getInt("fk_sucursal");

	            // CONSULTAR STOCK EN EL DEPOSITO
	            PreparedStatement stockStmt = con.prepareStatement(
	            		"SELECT nombre, stock FROM producto WHERE id_producto = ?"
	            );
	            stockStmt.setInt(1, idProducto);
	            ResultSet rsStock = stockStmt.executeQuery();
	            


	            if (rsStock.next()) {
		            String nombreProducto = rsStock.getString("nombre");
	                int stockActual = rsStock.getInt("stock");

	                if (stockActual < cantidad) {
	                    stockInsuficiente = true;
	                    mensajeStock.append("Pedido: ").append(idPedido)
	                    .append(" | Producto: ").append(nombreProducto)
	                    .append(" | Cantidad: ").append(cantidad)
	                    .append(" | Stock: ").append(stockActual).append("\n");

	                } else {
	                    productos.add(idProducto);
	                    cantidades.add(cantidad);
	                    stocks.add(stockActual);
	                }
	            }
	        }

	        // SI NO HAY STOCK SUFICIENTE NO SE PROCESA EL PEDIDO
	        if (stockInsuficiente) {
	        	lblError.setText("No se pudo enviar el pedido porque hay productos sin stock suficiente:");
	        	lblError2.setText(mensajeStock.toString());
	            return;
	        }

	        // SI HAY STOCK SUFICIENTE SE PROCESA EL PEDIDO
	        for (int i = 0; i < productos.size(); i++) {
	            int idProducto = productos.get(i);
	            int cantidad = cantidades.get(i);

	         // VER SI YA EXISTE EL PRODUCTO EN EL ALMACEN DE LA SUCURSAL
	            PreparedStatement checkStmt = con.prepareStatement(
	                "SELECT cantidad FROM almacen_sucursal WHERE fk_sucursal = ? AND fk_producto = ?"
	            );
	            checkStmt.setInt(1, idSucursal);
	            checkStmt.setInt(2, idProducto);
	            ResultSet rsCheck = checkStmt.executeQuery();

	            if (rsCheck.next()) {
	                // SI YA EXISTE, SE ACTUALIZA EL STOCK
	                int cantidadActual = rsCheck.getInt("cantidad");
	                int nuevaCantidad = cantidadActual + cantidad;

	                PreparedStatement updateStmt = con.prepareStatement(
	                    "UPDATE almacen_sucursal SET cantidad = ? WHERE fk_sucursal = ? AND fk_producto = ?"
	                );
	                updateStmt.setInt(1, nuevaCantidad);
	                updateStmt.setInt(2, idSucursal);
	                updateStmt.setInt(3, idProducto);
	                updateStmt.executeUpdate();
	            } else {
	                // SI NO EXISTE SE CREA UNO NUEVO
	                PreparedStatement insertStmt = con.prepareStatement(
	                    "INSERT INTO almacen_sucursal (fk_sucursal, fk_producto, cantidad) VALUES (?, ?, ?)"
	                );
	                insertStmt.setInt(1, idSucursal);
	                insertStmt.setInt(2, idProducto);
	                insertStmt.setInt(3, cantidad);
	                insertStmt.executeUpdate();
	            }


	            // DESCONTAR STOCK DEL DEPOSITO
	            PreparedStatement updateStock = con.prepareStatement(
	                "UPDATE producto SET stock = stock - ? WHERE id_producto = ?"
	            );
	            updateStock.setInt(1, cantidad);
	            updateStock.setInt(2, idProducto);
	            updateStock.executeUpdate();
	        }

	        // BORRAR PEDIDO
	        PreparedStatement deleteDetalles = con.prepareStatement(
	            "DELETE FROM detalle_pedido_reposicion WHERE fk_pedido_reposicion = ?"
	        );
	        deleteDetalles.setInt(1, idPedido);
	        deleteDetalles.executeUpdate();

	        PreparedStatement deletePedido = con.prepareStatement(
	            "DELETE FROM pedido_reposicion WHERE id_pedido_reposicion = ?"
	        );
	        deletePedido.setInt(1, idPedido);
	        deletePedido.executeUpdate();

	        JOptionPane.showMessageDialog(null, "Envío realizado correctamente.");

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al preparar el envío.");
	    }
    }
}
