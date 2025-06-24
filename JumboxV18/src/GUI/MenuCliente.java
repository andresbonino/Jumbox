package GUI;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DDL.Conexion;
import DDL.ControllerCarrito;
import DDL.ControllerProducto;
import jumbox.Carrito;
import jumbox.Cliente;
import jumbox.OpcionesSucursales;
import jumbox.Productos;
import jumbox.Sucursal;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.awt.Color;
import javax.swing.JButton;

public class MenuCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuCliente frame = new MenuCliente(null, null);
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
	private Cliente cliente;
	ControllerCarrito controlador = new ControllerCarrito();
	ControllerProducto controllerP = new ControllerProducto();
	LinkedList<Productos> listaProductos = controllerP.mostrarProducto();

	public MenuCliente(LinkedList<Productos> productos,Cliente cliente) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 924, 766);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bienvenido "+cliente.getNombre());
		lblNewLabel.setBounds(20, 0, 867, 114);
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 80));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBackground(new Color(255, 255, 255));
		lblNewLabel_1.setIcon(new ImageIcon("src\\img\\logo.png"));
		lblNewLabel_1.setBounds(213, 118, 440, 402);
		contentPane.add(lblNewLabel_1);
		
		JButton btnComprar = new JButton("Comprar");
		btnComprar.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnComprar.setBounds(73, 531, 207, 50);
		contentPane.add(btnComprar);
		
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClienteCompras menuPrinci = new ClienteCompras(cliente);
				menuPrinci.setVisible(true);
		        //controlador.compras(listaProductos,cliente);
				
			}
		});
		
		
		JButton btnVerCarrito = new JButton("Ver Carrito");
		btnVerCarrito.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnVerCarrito.setBounds(315, 531, 207, 50);
		contentPane.add(btnVerCarrito);

		btnVerCarrito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        controlador.mostrarCarritoGUI(cliente);
			}
		});
		
		JButton btnEdiCarrito = new JButton("Editar Carrito");
		btnEdiCarrito.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnEdiCarrito.setBounds(559, 531, 225, 50);
		contentPane.add(btnEdiCarrito);
		
		btnEdiCarrito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlador.editarCarrito();
				
			}
		});
		
		
		JButton btnEstadoCompra = new JButton("Estado de la compra");
		btnEstadoCompra.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnEstadoCompra.setBounds(143, 636, 334, 50);
		contentPane.add(btnEstadoCompra);
		
		btnEstadoCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlador.verCompra();
				
			}
		});
		
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnSalir.setBounds(507, 636, 207, 50);
		contentPane.add(btnSalir);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				PantallaPrincipal menuPrinci = new PantallaPrincipal();
				menuPrinci.setVisible(true);
			}
		});
	}
}
