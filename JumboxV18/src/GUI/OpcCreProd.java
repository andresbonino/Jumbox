package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class OpcCreProd extends JFrame {

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
					OpcCreProd frame = new OpcCreProd();
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
	public OpcCreProd() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 922);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Ingrese nombre");
		lblNewLabel_2.setBounds(190, 188, 248, 45);
		lblNewLabel_2.setFont(new Font("Leelawadee UI", Font.BOLD, 25));
		contentPane.add(lblNewLabel_2);

		nombretxt = new JTextField();
		nombretxt.setBounds(80, 244, 456, 39);
		nombretxt.setFont(new Font("Arial", Font.PLAIN, 18));
		contentPane.add(nombretxt);

		JLabel lblNewLabel_2_1 = new JLabel("Ingrese precio");
		lblNewLabel_2_1.setBounds(190, 334, 248, 45);
		lblNewLabel_2_1.setFont(new Font("Leelawadee UI", Font.BOLD, 25));
		contentPane.add(lblNewLabel_2_1);

		preciotxt = new JTextField();
		preciotxt.setBounds(80, 390, 456, 39);
		preciotxt.setFont(new Font("Arial", Font.PLAIN, 18));
		contentPane.add(preciotxt);

		JLabel lblNewLabel_2_1_1 = new JLabel("Ingrese stock");
		lblNewLabel_2_1_1.setBounds(203, 482, 248, 45);
		lblNewLabel_2_1_1.setFont(new Font("Leelawadee UI", Font.BOLD, 25));
		contentPane.add(lblNewLabel_2_1_1);

		stocktxt = new JTextField();
		stocktxt.setBounds(80, 538, 456, 39);
		stocktxt.setFont(new Font("Arial", Font.PLAIN, 18));
		contentPane.add(stocktxt);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(241, 791, 141, 64);
		btnAceptar.setFont(new Font("Dialog", Font.PLAIN, 20));
		contentPane.add(btnAceptar);

		JLabel lblNewLabel_1 = new JLabel("Deposito");
		lblNewLabel_1.setBounds(105, 20, 431, 157);
		lblNewLabel_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 90));
		contentPane.add(lblNewLabel_1);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(242, 700, 155, 55);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 21));
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

		JLabel lblNewLabel_2_1_2 = new JLabel("¿En que categoria entra tu producto?");
		lblNewLabel_2_1_2.setFont(new Font("Leelawadee UI", Font.BOLD, 25));
		lblNewLabel_2_1_2.setBounds(92, 639, 444, 45);
		contentPane.add(lblNewLabel_2_1_2);

		JLabel LblError = new JLabel("");
		LblError.setForeground(Color.RED);
		LblError.setFont(new Font("Arial", Font.PLAIN, 15));
		LblError.setBounds(130, 294, 354, 29);
		contentPane.add(LblError);

		JLabel LblError_1 = new JLabel("");
		LblError_1.setForeground(Color.RED);
		LblError_1.setFont(new Font("Arial", Font.PLAIN, 15));
		LblError_1.setBounds(115, 440, 354, 31);
		contentPane.add(LblError_1);

		JLabel LblError_2 = new JLabel("");
		LblError_2.setForeground(Color.RED);
		LblError_2.setFont(new Font("Arial", Font.PLAIN, 15));
		LblError_2.setBounds(115, 588, 354, 40);
		contentPane.add(LblError_2);

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
	}
}
