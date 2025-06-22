package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DLL.ControllerSucursal;
import jumbox.Deposito;
import jumbox.Sucursal;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class InicioSucursal extends JFrame {

	private static final long serialVersionUID = 1L;
	protected static final JLabel LblError = null;
	private JPanel contentPane;
	private JPasswordField passwordField;
	ControllerSucursal controllerS = new ControllerSucursal();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InicioSucursal frame = new InicioSucursal();
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
	public InicioSucursal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("src\\img\\logo-chico.png"));
		lblNewLabel.setBounds(0, 0, 133, 449);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Sucursal");
		lblNewLabel_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1.setFont(new Font("Swis721 Blk BT", Font.BOLD, 90));
		lblNewLabel_1.setBounds(120, -33, 538, 157);
		contentPane.add(lblNewLabel_1);
		

		JPasswordField txtContrasena = new JPasswordField();
		txtContrasena.setBounds(143, 219, 353, 34);
		txtContrasena.setFont(new Font("Arial", Font.PLAIN, 18));
		contentPane.add(txtContrasena);


		JLabel LblError = new JLabel("");
		LblError.setForeground(new Color(255, 0, 0));
		LblError.setFont(new Font("Arial", Font.PLAIN, 15));
		LblError.setBounds(143, 256, 354, 14);
		contentPane.add(LblError);


		JLabel lblNewLabel_2 = new JLabel("Introduzca la Contraseña");
		lblNewLabel_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(144, 174, 353, 34);
		contentPane.add(lblNewLabel_2);


		JButton btnNewButton = new JButton("Ingresar");
		btnNewButton.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnNewButton.setBounds(143, 281, 353, 50);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_2_1 = new JLabel("Numero de Sucursal");
		lblNewLabel_2_1.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2_1.setBounds(143, 102, 353, 34);
		contentPane.add(lblNewLabel_2_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(143, 135, 353, 28);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox.addItem("Primera");
		comboBox.addItem("Segunda");
		comboBox.addItem("Tercera");
		comboBox.addItem("Cuarta");
		contentPane.add(comboBox);


		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String contrasena = new String(txtContrasena.getPassword()).trim();
		        LblError.setText("");

		        if (contrasena.isEmpty()) {
		            LblError.setText("Campo vacío");
		        } else {
		        	int idSucursal = comboBox.getSelectedIndex() + 1;
		            Sucursal usuario = controllerS.loginSucursal(idSucursal, contrasena);
		            if (usuario != null) {
		            	dispose();
		            	Sucursal.IngresoSucursal(idSucursal, contrasena);
		            } else {
		                LblError.setText("Contraseña incorrecta");
		            }
		        }
		    }
		});


		
		
	}
}
