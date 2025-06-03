package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		setBounds(100, 100, 613, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("src\\img\\logo-chico.png"));
		lblNewLabel.setBounds(10, 11, 109, 469);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Cliente");
		lblNewLabel_1.setBackground(new Color(0, 128, 0));
		lblNewLabel_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1.setFont(new Font("Swis721 Blk BT", Font.BOLD, 90));
		lblNewLabel_1.setBounds(118, 10, 631, 109);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Iniciar Sesion");
		btnNewButton.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnNewButton.setBounds(118, 147, 370, 79);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Registrarse");
		btnNewButton_1.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnNewButton_1.setBounds(118, 248, 370, 79);
		contentPane.add(btnNewButton_1);
		
	}
}
