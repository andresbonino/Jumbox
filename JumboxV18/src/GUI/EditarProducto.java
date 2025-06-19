package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class EditarProducto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;


	/**
	 * Create the frame.
	 */
	public EditarProducto() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, -39, 450, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Editar Producto");
		lblNewLabel.setBounds(72, 0, 414, 66);
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Swis721 Blk BT", Font.BOLD, 30));
		contentPane.add(lblNewLabel);
		
		
		JLabel lblNewLabel_1_1 = new JLabel("Nuevo Precio");
		lblNewLabel_1_1.setBounds(53, 77, 249, 25);
		lblNewLabel_1_1.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		contentPane.add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(53, 112, 307, 20);
		textField_1.setColumns(10);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Nueva Categoria");
		lblNewLabel_1_1_1.setBounds(53, 209, 249, 25);
		lblNewLabel_1_1_1.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton btnNewButton = new JButton("Editar");
		btnNewButton.setBounds(81, 291, 249, 33);
		btnNewButton.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		contentPane.add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(53, 243, 307, 22);
		contentPane.add(comboBox);
		
		textField_2 = new JTextField();
		textField_2.setBounds(53, 178, 307, 20);
		textField_2.setColumns(10);
		contentPane.add(textField_2);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Nuevo Stock");
		lblNewLabel_1_1_2.setBounds(53, 143, 249, 25);
		lblNewLabel_1_1_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		contentPane.add(lblNewLabel_1_1_2);
	}

}
