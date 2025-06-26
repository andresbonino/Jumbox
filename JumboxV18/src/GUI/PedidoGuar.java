package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class PedidoGuar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PedidoGuar frame = new PedidoGuar();
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
	public PedidoGuar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Pedido guardado correctamente");
		lblNewLabel_2.setBounds(95, 112, 386, 45);
		lblNewLabel_2.setFont(new Font("Leelawadee UI", Font.BOLD, 25));
		contentPane.add(lblNewLabel_2);
		
		JButton btnOk = new JButton("Ok");
		btnOk.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnOk.setBounds(233, 199, 114, 64);
		contentPane.add(btnOk);
	}

}
