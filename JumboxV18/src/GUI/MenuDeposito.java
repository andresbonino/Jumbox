package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DLL.ControllerProducto;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class MenuDeposito extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	ControllerProducto controllerP = new ControllerProducto();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuDeposito frame = new MenuDeposito();
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
	public MenuDeposito() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 784, 704);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Deposito");
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Swis721 Blk BT", Font.BOLD, 90));
		lblNewLabel.setBounds(131, -32, 812, 151);
		contentPane.add(lblNewLabel);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("src\\img\\logo.png"));
		lblLogo.setBounds(152, 100, 440, 402);
		contentPane.add(lblLogo);
		
		JButton btnNewButton = new JButton("Armar Envio");
		btnNewButton.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnNewButton.setBounds(31, 511, 302, 57);
		contentPane.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controllerP.procesarPedidosPendientes();
			}
		});
		
		JButton btnTablaProductos = new JButton("Tabla Productos");
		btnTablaProductos.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnTablaProductos.setBounds(392, 511, 309, 57);
		contentPane.add(btnTablaProductos);
		
		btnTablaProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				TablaProductos menu = new TablaProductos();
				menu.setVisible(true);
			}
		});
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnSalir.setBounds(275, 592, 187, 45);
		contentPane.add(btnSalir);
		
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				PantallaPrincipal menup = new PantallaPrincipal();
				menup.setVisible(true);
			}
		});
	}
}
