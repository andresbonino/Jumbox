package GUI;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				PantallaPrincipal frame = new PantallaPrincipal();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public PantallaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 728, 715);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblTitulo = new JLabel("Bienvenido");
		lblTitulo.setBounds(77, 0, 795, 100);
		lblTitulo.setFont(new Font("Swis721 Blk BT", Font.BOLD, 90));
		lblTitulo.setForeground(new Color(0, 128, 0));
		contentPane.add(lblTitulo);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("src\\img\\logo.png"));
		lblLogo.setBounds(126, 79, 459, 429);
		contentPane.add(lblLogo);

		JButton btnCliente = new JButton("Cliente");
		btnCliente.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnCliente.setBounds(41, 519, 190, 50);
		contentPane.add(btnCliente);

		JButton btnDeposito = new JButton("Deposito");
		btnDeposito.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnDeposito.setBounds(261, 519, 190, 50);
		contentPane.add(btnDeposito);

		JButton btnSucursal = new JButton("Sucursal");
		btnSucursal.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnSucursal.setBounds(481, 519, 190, 50);
		contentPane.add(btnSucursal);

		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnSalir.setBounds(261, 599, 190, 50);
		contentPane.add(btnSalir);

		// Acciones

		btnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				ClienteEleccion menuLog = new ClienteEleccion();
				menuLog.setVisible(true);
			}
		});

		btnDeposito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				InicioDeposito menu = new InicioDeposito();
				menu.setVisible(true);
			}
		});

		btnSucursal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				InicioSucursal sucursalLogin = new InicioSucursal();
				sucursalLogin.setVisible(true);
			}
		});

		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}
