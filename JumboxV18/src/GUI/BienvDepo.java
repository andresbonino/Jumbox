package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class BienvDepo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BienvDepo frame = new BienvDepo();
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
	public BienvDepo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 732, 466);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Bienvenido al Deposito");
		lblNewLabel_2.setBounds(134, 110, 484, 54);
		lblNewLabel_2.setFont(new Font("Leelawadee UI", Font.BOLD, 40));
		contentPane.add(lblNewLabel_2);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(223, 239, 281, 50);
		btnAceptar.setFont(new Font("Dialog", Font.PLAIN, 30));
		contentPane.add(btnAceptar);
	}
}
