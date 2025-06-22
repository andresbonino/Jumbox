package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OpcEdiProd extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nombretxt;
	private JTextField preciotxt;
	private JTextField stocktxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpcEdiProd frame = new OpcEdiProd();
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
	public OpcEdiProd() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 637, 994);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Deposito");
		lblNewLabel_1.setBounds(88, 23, 431, 157);
		lblNewLabel_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 90));
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Ingrese nombre");
		lblNewLabel_2.setBounds(161, 205, 248, 45);
		lblNewLabel_2.setFont(new Font("Leelawadee UI", Font.BOLD, 25));
		contentPane.add(lblNewLabel_2);
		
		nombretxt = new JTextField();
		nombretxt.setBounds(63, 261, 456, 39);
		nombretxt.setFont(new Font("Arial", Font.PLAIN, 18));
		contentPane.add(nombretxt);
		
		JLabel LblError = new JLabel("");
		LblError.setBounds(109, 322, 354, 29);
		LblError.setForeground(Color.RED);
		LblError.setFont(new Font("Arial", Font.PLAIN, 15));
		contentPane.add(LblError);
		
		JLabel lblNewLabel_2_1 = new JLabel("Ingrese precio");
		lblNewLabel_2_1.setBounds(174, 362, 248, 45);
		lblNewLabel_2_1.setFont(new Font("Leelawadee UI", Font.BOLD, 25));
		contentPane.add(lblNewLabel_2_1);
		
		preciotxt = new JTextField();
		preciotxt.setBounds(63, 418, 456, 39);
		preciotxt.setFont(new Font("Arial", Font.PLAIN, 18));
		contentPane.add(preciotxt);
		
		JLabel LblError_1 = new JLabel("");
		LblError_1.setBounds(109, 497, 354, 31);
		LblError_1.setForeground(Color.RED);
		LblError_1.setFont(new Font("Arial", Font.PLAIN, 15));
		contentPane.add(LblError_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Ingrese stock");
		lblNewLabel_2_1_1.setBounds(185, 510, 248, 45);
		lblNewLabel_2_1_1.setFont(new Font("Leelawadee UI", Font.BOLD, 25));
		contentPane.add(lblNewLabel_2_1_1);
		
		stocktxt = new JTextField();
		stocktxt.setBounds(63, 566, 456, 39);
		stocktxt.setFont(new Font("Arial", Font.PLAIN, 18));
		contentPane.add(stocktxt);
		
		JLabel LblError_1_1 = new JLabel("");
		LblError_1_1.setBounds(109, 468, 354, 31);
		LblError_1_1.setForeground(Color.RED);
		LblError_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		contentPane.add(LblError_1_1);
		
		JLabel LblError_2 = new JLabel("");
		LblError_2.setBounds(120, 629, 354, 40);
		LblError_2.setForeground(Color.RED);
		LblError_2.setFont(new Font("Arial", Font.PLAIN, 15));
		contentPane.add(LblError_2);
		
		JLabel lblNewLabel_2_1_2 = new JLabel("¿En que categoria entra tu producto?");
		lblNewLabel_2_1_2.setBounds(75, 690, 444, 45);
		lblNewLabel_2_1_2.setFont(new Font("Leelawadee UI", Font.BOLD, 25));
		contentPane.add(lblNewLabel_2_1_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 21));
		comboBox.setBounds(214, 756, 155, 55);
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
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAceptar.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnAceptar.setBounds(228, 867, 141, 64);
		contentPane.add(btnAceptar);
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = new String(nombretxt.getText()).trim();

				LblError.setText("");
				LblError_1.setText("");
				LblError_2.setText("");

				if (nombre.isEmpty()) {
					LblError.setText("Campo vacío");
				}

				String precio = new String(preciotxt.getText()).trim();
				if (precio.isEmpty()) {
					LblError_1.setText("Campo vacío");
				}

				String stock = new String(stocktxt.getText()).trim();
				if (stock.isEmpty()) {
					LblError_2.setText("Campo vacío");
				}

			}

		});
		
		JLabel LblError_2_1 = new JLabel("");
		LblError_2_1.setForeground(Color.RED);
		LblError_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		LblError_2_1.setBounds(109, 822, 354, 40);
		contentPane.add(LblError_2_1);
	}
}
