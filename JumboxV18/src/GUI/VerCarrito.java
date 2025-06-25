package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import DDL.ControllerCarrito;
import jumbox.Carrito;
import jumbox.Cliente;
import jumbox.Sucursal;

public class VerCarrito extends JFrame {
    
	ControllerCarrito<Carrito> controllerC = new ControllerCarrito();

    public VerCarrito(ControllerCarrito controllerCarrito, LinkedList<Carrito> carritoProductos, Cliente cliente, Sucursal sucursalSeleccionada, int idCarrito) {

    	setSize(538, 454);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        double total = 0;
        StringBuilder carritoTxt = new StringBuilder("Productos en el carrito:\n\n");
        for (Carrito item : carritoProductos) {
        	carritoTxt.append(item.getProducto().getNombre())
              .append(" x ").append(item.getCantidad())
              .append(" = $").append(item.getTotal()).append("\n");
            total += item.getTotal();
        }
        carritoTxt.append("\nTOTAL: $").append(total);
        getContentPane().setLayout(null);
        
        JTextArea area = new JTextArea();
        area.setBounds(40, 107, 441, 248);
        getContentPane().add(area);
        area.setFont(new Font("Dialog", Font.PLAIN, 15));
        area.setEditable(false);
        area.setText(carritoTxt.toString());
        
        JLabel lblNewLabel = new JLabel("Ver Carrito");
        lblNewLabel.setForeground(new Color(0, 128, 0));
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 80));
        lblNewLabel.setBounds(40, 11, 441, 102);
        getContentPane().add(lblNewLabel);
        
        JButton btnComprar = new JButton("Realizar la compra");
        btnComprar.setFont(new Font("Dialog", Font.PLAIN, 15));
        btnComprar.setBounds(40, 366, 177, 23);
        getContentPane().add(btnComprar);
        
        // Realizar Compra
        btnComprar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	controllerC.realizarCompra(carritoProductos, cliente, sucursalSeleccionada, idCarrito);
                dispose();
            }
        });

        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Dialog", Font.PLAIN, 15));
        btnCancelar.setBounds(328, 366, 153, 23);
        getContentPane().add(btnCancelar);
        
        // Salir del JFrame
        btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	            dispose();
				
			}
		});
    }
}
