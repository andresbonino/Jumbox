package GUI;

import javax.swing.*;
import jumbox.Sucursal;
import java.awt.*;
import java.awt.event.*;

public class MenuSucursal extends JFrame {

    private Sucursal sucursal;

    public MenuSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;

        setTitle("Menú Sucursal");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel lbl = new JLabel("Sucursal: " + sucursal.getId_Sucursal(), JLabel.CENTER);
        add(lbl);

        JButton btnPedidos = new JButton("Gestionar pedidos");
        JButton btnProductos = new JButton("Ver productos");

        add(btnPedidos);
        add(btnProductos);

        btnPedidos.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Función aún no implementada");
        });

        btnProductos.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Función aún no implementada");
        });
    }
}
