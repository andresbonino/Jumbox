package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class EleccionCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EleccionCliente frame = new EleccionCliente();
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
	public EleccionCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 787, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLoguearse = new JButton("Loguearse");
		btnLoguearse.setBounds(77, 219, 248, 45);
		btnLoguearse.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnLoguearse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				LoginCliente menuLog = new LoginCliente();
				menuLog.setVisible(true);
			}
		});
		contentPane.add(btnLoguearse);
		
		JLabel lblTitulo = new JLabel("Login");
		lblTitulo.setBounds(220, 11, 283, 109);
		lblTitulo.setForeground(new Color(0, 128, 0));
		lblTitulo.setFont(new Font("Swis721 Blk BT", Font.BOLD, 90));
		contentPane.add(lblTitulo);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				RegistroCliente menuReg = new RegistroCliente();
				menuReg.setVisible(true);
			}
		});
		btnRegistrarse.setBounds(413, 219, 248, 45);
		contentPane.add(btnRegistrarse);
	}
}
