package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jumbox.Usuarios;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Design extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Design frame = new Design();
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
	public Design() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 504);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bienvenido a Jumbox");
		lblNewLabel.setForeground(new Color(0, 64, 0));
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		lblNewLabel.setBounds(104, -11, 208, 82);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("src\\img\\logo.png"));
		lblNewLabel_1.setBounds(81, 46, 274, 199);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(58, 294, 320, 32);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Correo electronico:");
		lblNewLabel_2.setBounds(58, 279, 124, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Contraseña:");
		lblNewLabel_3.setBounds(53, 355, 87, 14);
		contentPane.add(lblNewLabel_3);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(58, 380, 320, 20);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(110, 254, 227, 14);
		contentPane.add(lblNewLabel_4);
		
		JButton btnNewButton = new JButton("Ingresar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				LinkedList<Usuarios> usuarios = new LinkedList<>();
				
				if (usuarios.isEmpty() || usuarios == null) {
					JOptionPane.showMessageDialog(lblNewLabel_4, "Usuario o contraseña son incorrectos");
				}
			}
		});
		btnNewButton.setBounds(170, 442, 89, 23);
		contentPane.add(btnNewButton);
	}
}
