package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jumbox.Cliente;
import jumbox.Registro;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PantallaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaPrincipal frame = new PantallaPrincipal();
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
	public PantallaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 728, 715);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bienvenido");
		lblNewLabel.setBounds(77, 0, 795, 100);
		lblNewLabel.setFont(new Font("Swis721 Blk BT", Font.BOLD, 90));
		lblNewLabel.setForeground(new Color(0, 128, 0));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("src\\img\\logo.png"));
		lblNewLabel_1.setBounds(126, 79, 459, 429);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Cliente");
		btnNewButton.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				EleccionCliente eleccion = new EleccionCliente();
				eleccion.setVisible(true);
			}
		});
		btnNewButton.setBounds(41, 519, 190, 50);
		contentPane.add(btnNewButton);
		
		JButton btnDeposito = new JButton("Deposito");
		btnDeposito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				InicioDeposito menu = new InicioDeposito();
       	        menu.setVisible(true);
			}
		});
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
		btnSalir.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        System.exit(0);
		    }
		});
	}
}
