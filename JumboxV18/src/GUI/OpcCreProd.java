package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class OpcCreProd extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpcCreProd frame = new OpcCreProd();
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
	public OpcCreProd() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 638, 848);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Ingrese nombre");
		lblNewLabel_2.setBounds(190, 217, 248, 45);
		lblNewLabel_2.setFont(new Font("Leelawadee UI", Font.BOLD, 25));
		contentPane.add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
		passwordField.setBounds(80, 286, 456, 39);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel_2_1 = new JLabel("Ingrese precio");
		lblNewLabel_2_1.setFont(new Font("Leelawadee UI", Font.BOLD, 25));
		lblNewLabel_2_1.setBounds(190, 383, 248, 45);
		contentPane.add(lblNewLabel_2_1);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setFont(new Font("Arial", Font.PLAIN, 18));
		passwordField_1.setBounds(80, 452, 456, 39);
		contentPane.add(passwordField_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Ingrese stock");
		lblNewLabel_2_1_1.setFont(new Font("Leelawadee UI", Font.BOLD, 25));
		lblNewLabel_2_1_1.setBounds(190, 540, 248, 45);
		contentPane.add(lblNewLabel_2_1_1);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setFont(new Font("Arial", Font.PLAIN, 18));
		passwordField_2.setBounds(80, 604, 456, 39);
		contentPane.add(passwordField_2);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Dialog", Font.PLAIN, 22));
		btnAceptar.setBounds(211, 712, 158, 64);
		contentPane.add(btnAceptar);
	}
}
