package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

public class EleccionCarrito extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EleccionCarrito frame = new EleccionCarrito();
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
	public EleccionCarrito() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 690, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(27, 28, 620, 416);
		contentPane.add(tabbedPane);
		
		JPanel CambiarCantidad = new JPanel();
		tabbedPane.addTab("New tab", null, CambiarCantidad, null);
		CambiarCantidad.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nueva cantidad");
		lblNewLabel.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel.setBounds(168, 122, 200, 38);
		CambiarCantidad.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Editar Producto");
		lblNewLabel_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 70));
		lblNewLabel_1.setBackground(new Color(0, 128, 0));
		lblNewLabel_1.setBounds(52, 11, 553, 80);
		CambiarCantidad.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 18));
		textField.setColumns(10);
		textField.setBounds(168, 171, 313, 32);
		CambiarCantidad.add(textField);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnAceptar.setBounds(184, 249, 287, 50);
		CambiarCantidad.add(btnAceptar);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("Borrar Producto");
		lblNewLabel_1_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.BOLD, 70));
		lblNewLabel_1_1.setBackground(new Color(0, 128, 0));
		lblNewLabel_1_1.setBounds(38, 11, 567, 80);
		panel_1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("¿Estas seguro de que querés \r\nborrar este producto?");
		lblNewLabel_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(85, 102, 473, 60);
		panel_1.add(lblNewLabel_2);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnBorrar.setBounds(74, 205, 221, 50);
		panel_1.add(btnBorrar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnCancelar.setBounds(328, 205, 230, 50);
		panel_1.add(btnCancelar);
	}
}
