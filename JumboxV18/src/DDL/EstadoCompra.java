package DDL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EstadoCompra extends JFrame {
    private JTextField txtTelefono;
    private JTextArea txtResultado;
    private Connection con;

    public EstadoCompra(Connection con) {
        this.con = con;
		
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel lblTelefono = new JLabel("Ingrese su teléfono:");
        lblTelefono.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblTelefono.setBounds(117, 39, 150, 25);
        getContentPane().add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setFont(new Font("Dialog", Font.PLAIN, 15));
        txtTelefono.setBounds(270, 41, 200, 25);
        getContentPane().add(txtTelefono);

        JButton btnConsultar = new JButton("Consultar Pedidos");
        btnConsultar.setFont(new Font("Dialog", Font.PLAIN, 15));
        btnConsultar.setBounds(480, 41, 180, 25);
        getContentPane().add(btnConsultar);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Calibri", Font.PLAIN, 15));

        JScrollPane scroll = new JScrollPane(txtResultado);
        scroll.setBounds(20, 101, 640, 339);
        getContentPane().add(scroll);
        
        JLabel logo = new JLabel("");
        logo.setBounds(10, 0, 97, 450);
		logo.setIcon(new ImageIcon("src\\img\\logo-chico.png"));
		getContentPane().add(logo);

        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarPedidos();
            }
        });
    }

    private void consultarPedidos() {
        txtResultado.setText("");
        String telefonoStr = txtTelefono.getText().trim();

        if (telefonoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un número de teléfono.");
            return;
        }

        try {
            int telefono = Integer.parseInt(telefonoStr);

            PreparedStatement stmt = con.prepareStatement(
                "SELECT p.id_pedido, p.fecha, p.estado, d.cantidad, prod.nombre, prod.precio " +
                "FROM pedido p " +
                "JOIN detalles_pedido d ON p.id_pedido = d.fk_pedido " +
                "JOIN producto prod ON d.fk_producto = prod.id_producto " +
                "JOIN cliente c ON p.fk_cliente = c.id_cliente " +
                "WHERE c.telefono = ? AND p.estado != 'notificado'"
            );

            stmt.setInt(1, telefono);
            ResultSet rs = stmt.executeQuery();

            StringBuilder resumen = new StringBuilder();
            int pedidoActual = -1;

            while (rs.next()) {
                int idPedido = rs.getInt("id_pedido");
                String fecha = rs.getString("fecha");
                String estado = rs.getString("estado");
                String producto = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio");

                if (pedidoActual != idPedido) {
                    if (pedidoActual != -1) resumen.append("\n-----------------------------\n");
                    resumen.append("Pedido #").append(idPedido)
                           .append("\nFecha: ").append(fecha)
                           .append("\nEstado: ").append(estado)
                           .append("\nProductos:\n");
                    pedidoActual = idPedido;
                }

                resumen.append("- ").append(producto)
                       .append(" x ").append(cantidad)
                       .append(" = $").append(precio * cantidad).append("\n");
            }

            if (resumen.length() == 0) {
                txtResultado.setText("No se encontraron compras para ese cliente.");
            } else {
                txtResultado.setText(resumen.toString());
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El teléfono debe ser un número.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al consultar compras.");
        }
    }
}

