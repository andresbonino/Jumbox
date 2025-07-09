package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import DLL.Conexion;
import jumbox.Carrito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OpcionCarrito extends JFrame {

    private JList<String> listaProductos;
    private DefaultListModel<String> modeloLista;
    private JTextField txtNuevaCantidad;
    private LinkedList<Carrito> carrito;
    private Carrito itemSeleccionado;

    public OpcionCarrito(LinkedList<Carrito> carrito) {
        this.carrito = carrito;

        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        modeloLista = new DefaultListModel<>();
        for (Carrito c : carrito) {
            modeloLista.addElement(c.getProducto().getNombre() + " (x" + c.getCantidad() + ")");
        }
        
        JLabel logo = new JLabel("");
        logo.setBounds(10, 0, 97, 450);
		logo.setIcon(new ImageIcon("src\\img\\logo-chico.png"));
		getContentPane().add(logo);
        
        listaProductos = new JList<>(modeloLista);
        listaProductos.setFont(new Font("Calibri", Font.PLAIN, 15));
        JScrollPane scroll = new JScrollPane(listaProductos);
        scroll.setBounds(24, 92, 296, 348);
        getContentPane().add(scroll);

        JPanel panelAcciones = new JPanel();
        panelAcciones.setBounds(340, 20, 320, 420);
        panelAcciones.setLayout(null);
        getContentPane().add(panelAcciones);

        JLabel lblTitulo = new JLabel("Editar Producto");
        lblTitulo.setFont(new Font("Dialog", Font.BOLD, 30));
        lblTitulo.setForeground(new Color(0, 128, 0));
        lblTitulo.setBounds(30, 29, 260, 30);
        panelAcciones.add(lblTitulo);

        JLabel lblCantidad = new JLabel("Nueva cantidad:");
        lblCantidad.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblCantidad.setBounds(30, 70, 150, 20);
        panelAcciones.add(lblCantidad);

        txtNuevaCantidad = new JTextField();
        txtNuevaCantidad.setFont(new Font("Calibri", Font.PLAIN, 15));
        txtNuevaCantidad.setBounds(30, 95, 260, 25);
        panelAcciones.add(txtNuevaCantidad);

        JButton btnActualizar = new JButton("Actualizar cantidad");
        btnActualizar.setFont(new Font("Dialog", Font.PLAIN, 15));
        btnActualizar.setBounds(30, 140, 260, 30);
        panelAcciones.add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar del carrito");
        btnEliminar.setFont(new Font("Dialog", Font.PLAIN, 15));
        btnEliminar.setBounds(30, 185, 260, 30);
        panelAcciones.add(btnEliminar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Dialog", Font.PLAIN, 15));
        btnCerrar.setBounds(30, 230, 260, 30);
        panelAcciones.add(btnCerrar);

        JLabel lblError = new JLabel("");
        lblError.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblError.setForeground(Color.RED);
        lblError.setBounds(30, 270, 260, 20);
        panelAcciones.add(lblError);

        listaProductos.addListSelectionListener(e -> {
            int index = listaProductos.getSelectedIndex();
            if (index >= 0) {
                itemSeleccionado = carrito.get(index);
                txtNuevaCantidad.setText(String.valueOf(itemSeleccionado.getCantidad()));
                lblError.setText("");
            }
        });

        btnActualizar.addActionListener(e -> {
            if (itemSeleccionado == null) return;

            String texto = txtNuevaCantidad.getText().trim();
            if (texto.isEmpty()) {
                lblError.setText("Ingrese una cantidad válida.");
                return;
            }
            if (ValidarNumero(texto)==false) {
                lblError.setText("Ingrese una cantidad válida.");
                return;
            }

            try {
                int nuevaCantidad = Integer.parseInt(texto);
                if (nuevaCantidad <= 0) {
                    lblError.setText("Debe ser mayor a cero.");
                    return;
                }

                if (nuevaCantidad > itemSeleccionado.getProducto().getStock()) {
                    lblError.setText("Cantidad supera el stock.");
                    return;
                }

                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement stmt = con.prepareStatement(
                    "UPDATE producto_carrito SET cantidad = ? WHERE fk_producto = ? AND fk_carrito = ?"
                );
                stmt.setInt(1, nuevaCantidad);
                stmt.setInt(2, itemSeleccionado.getProducto().getIdProducto());
                stmt.setInt(3, itemSeleccionado.getIdCarrito());

                int updated = stmt.executeUpdate();
                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Cantidad actualizada.");
                    itemSeleccionado.setCantidad(nuevaCantidad);
                    modeloLista.set(listaProductos.getSelectedIndex(),
                        itemSeleccionado.getProducto().getNombre() + " (x" + nuevaCantidad + ")");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                lblError.setText("Error al actualizar.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (itemSeleccionado == null) return;

            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Connection con = Conexion.getInstance().getConnection();
                    PreparedStatement stmt = con.prepareStatement(
                        "DELETE FROM producto_carrito WHERE fk_producto = ? AND fk_carrito = ?"
                    );
                    stmt.setInt(1, itemSeleccionado.getProducto().getIdProducto());
                    stmt.setInt(2, itemSeleccionado.getIdCarrito());

                    int deleted = stmt.executeUpdate();
                    if (deleted > 0) {
                        JOptionPane.showMessageDialog(this, "Producto eliminado.");
                        int index = listaProductos.getSelectedIndex();
                        carrito.remove(index);
                        modeloLista.remove(index);
                        itemSeleccionado = null;
                        txtNuevaCantidad.setText("");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    lblError.setText("Error al eliminar.");
                }
            }
        });

        btnCerrar.addActionListener(e -> dispose());
    }
    
    public static boolean ValidarNumero(String numero) {
		boolean letra = false;
			for (int i = 0; i < numero.length(); i++) {
				if (Character.isLetter(numero.charAt(i))) {
					letra=true;
				}
			}
			if (letra==true) {
				return false;
			} else {
				return true;
			}	
	}
}
