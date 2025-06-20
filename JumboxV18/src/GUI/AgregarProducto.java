package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class AgregarProducto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;


	/**
	 * Create the frame.
	 */
	public AgregarProducto() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Agregar Producto");
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Swis721 Blk BT", Font.BOLD, 30));
		lblNewLabel.setBounds(61, 0, 414, 66);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(55, 69, 249, 25);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(55, 104, 307, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Precio");
		lblNewLabel_1_1.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(55, 134, 249, 25);
		contentPane.add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(55, 169, 307, 20);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Categoria");
		lblNewLabel_1_1_1.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_1_1_1.setBounds(55, 266, 249, 25);
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton btnNewButton = new JButton("Agregar");
		btnNewButton.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		btnNewButton.setBounds(83, 348, 249, 33);
		contentPane.add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(55, 300, 307, 22);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox.addItem("Mueble");
		comboBox.addItem("Electrodomestico");
		comboBox.addItem("Alimento");
		comboBox.addItem("Limpieza");
		comboBox.addItem("Higiene_Personal");
		comboBox.addItem("Farmacia");
		comboBox.addItem("Mascotas");
		comboBox.addItem("Hogar");
		comboBox.addItem("Ferreteria");
		comboBox.addItem("Jugueteria");
		comboBox.addItem("Libreria");
		comboBox.addItem("Bebidas");
		comboBox.addItem("Despensa");
		contentPane.add(comboBox);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(55, 235, 307, 20);
		contentPane.add(textField_2);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Stock");
		lblNewLabel_1_1_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_1_1_2.setBounds(55, 200, 249, 25);
		contentPane.add(lblNewLabel_1_1_2);
	}
}
