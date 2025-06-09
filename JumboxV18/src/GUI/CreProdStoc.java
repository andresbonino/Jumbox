package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class CreProdStoc extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreProdStoc frame = new CreProdStoc();
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
	public CreProdStoc() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 765, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Ingrese el stock del producto");
		lblNewLabel_2.setFont(new Font("Leelawadee UI", Font.BOLD, 35));
		lblNewLabel_2.setBounds(120, 106, 516, 77);
		contentPane.add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
		passwordField.setBounds(143, 217, 456, 50);
		contentPane.add(passwordField);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Dialog", Font.PLAIN, 22));
		btnAceptar.setBounds(204, 312, 158, 64);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Dialog", Font.PLAIN, 22));
		btnCancelar.setBounds(396, 312, 164, 64);
		contentPane.add(btnCancelar);
	}

}
