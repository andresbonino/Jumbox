package GUI;

import javax.swing.*;

import DLL.ControllerPedidoSucursal;
import DLL.ControllerProducto;
import DLL.ControllerSucursal;
import jumbox.Sucursal;


import java.awt.*;
import java.awt.event.ActionListener;

public class MenuSucursal extends JFrame {

    private Sucursal sucursal;
    private ControllerSucursal controllerS = new ControllerSucursal();
    private ControllerProducto controllerP = new ControllerProducto();
    ControllerPedidoSucursal controllerPS = new ControllerPedidoSucursal();
    

    public MenuSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;

        setTitle("Panel de Sucursal");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 255, 250));
        setLayout(null);

        //titulo
        JLabel lblTitulo = new JLabel("Sucursal #" + sucursal.getId_Sucursal(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Swis721 Blk BT", Font.BOLD, 40));
        lblTitulo.setForeground(new Color(0, 128, 0));
        lblTitulo.setBounds(50, 30, 500, 50);
        add(lblTitulo);

        //boton: ver productos
        JButton btnProductos = new JButton("Ver productos");
        btnProductos.setFont(new Font("Arial", Font.BOLD, 20));
        btnProductos.setBounds(180, 120, 240, 50);
        btnProductos.setBackground(new Color(200, 255, 200));
        btnProductos.setFocusPainted(false);
        add(btnProductos);

        //boton: gestionar pedidos
        JButton btnPedidos = new JButton("Gestionar pedidos");
        btnPedidos.setFont(new Font("Arial", Font.BOLD, 20));
        btnPedidos.setBounds(180, 190, 240, 50);
        btnPedidos.setBackground(new Color(200, 255, 200));
        btnPedidos.setFocusPainted(false);
        add(btnPedidos);

        //boton: salir
        JButton btnSalir = new JButton("Cerrar sesión");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 16));
        btnSalir.setBounds(420, 310, 150, 30);
        btnSalir.setBackground(new Color(255, 128, 128));
        btnSalir.setFocusPainted(false);
        add(btnSalir);

        //accion: ver productos
        btnProductos.addActionListener(e -> {
        	controllerPS.generarPedido(sucursal);
            
        });

        //accion: gestionar pedidos
        btnPedidos.addActionListener(e -> {
            controllerS.gestionarPedidos(sucursal.getId_Sucursal());
        });

        //accion: cerrar sesión
        btnSalir.addActionListener(e -> {
            dispose();
            new PantallaPrincipal().setVisible(true);
        });
    }
}
