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

import DDL.ControllerProducto;
import jumbox.Productos;

public class EditarProducto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private int idProducto;
	private ControllerProducto controllerP = new ControllerProducto();


	/**
	 * Create the frame.
	 */
	public EditarProducto(Productos producto) {
		this.idProducto = producto.getIdProducto();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("Editar Producto");
		lblNewLabel.setBounds(72, 0, 414, 66);
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Swis721 Blk BT", Font.BOLD, 30));
		contentPane.add(lblNewLabel);
		
		textField_1 = new JTextField(String.valueOf(producto.getPrecio()));
		JLabel lblNewLabel_1_1 = new JLabel("Nuevo Precio");
		lblNewLabel_1_1.setBounds(53, 77, 249, 25);
		lblNewLabel_1_1.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		contentPane.add(lblNewLabel_1_1);
		

		textField_1.setBounds(53, 112, 307, 20);
		textField_1.setColumns(10);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Nueva Categoria");
		lblNewLabel_1_1_1.setBounds(53, 209, 249, 25);
		lblNewLabel_1_1_1.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton btnNewButton = new JButton("Editar");
		btnNewButton.setBounds(83, 301, 249, 33);
		btnNewButton.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		contentPane.add(btnNewButton);
		
		JLabel LblError = new JLabel("");
		LblError.setForeground(new Color(255, 0, 0));
		LblError.setFont(new Font("Arial", Font.PLAIN, 15));
		LblError.setBounds(53, 276, 354, 14);
		contentPane.add(LblError);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(53, 243, 307, 22);
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
		comboBox.setSelectedIndex(producto.getCategoria() - 1);

		
		textField_2 = new JTextField(String.valueOf(producto.getStock()));
		textField_2.setBounds(53, 178, 307, 20);
		textField_2.setColumns(10);
		contentPane.add(textField_2);
		
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Nuevo Stock");
		lblNewLabel_1_1_2.setBounds(53, 143, 249, 25);
		lblNewLabel_1_1_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		contentPane.add(lblNewLabel_1_1_2);
		

		btnNewButton.addActionListener(e -> {
		    try {
		        double nuevoPrecio = Double.parseDouble(textField_1.getText().trim());
		        int nuevoStock = Integer.parseInt(textField_2.getText().trim());
		        int nuevaCategoria = comboBox.getSelectedIndex() + 1;

		        if (nuevoPrecio <= 0 || nuevoStock < 0) {
		            LblError.setText("Valores Invalidos");
		            return;
		        }

		        producto.setPrecio(nuevoPrecio);
		        producto.setStock(nuevoStock);
		        producto.setCategoria(nuevaCategoria);

		        controllerP.editarProducto(producto);

		        
		        dispose();

		    } catch (NumberFormatException ex) {
		        LblError.setForeground(Color.RED);
		        LblError.setText("Valores Inválidos");
		    } catch (Exception ex) {
		        ex.printStackTrace();
		        LblError.setForeground(Color.RED);
		        LblError.setText("Error al editar el producto");
		    }
		});


		

	}

}
