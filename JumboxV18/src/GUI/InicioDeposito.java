package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DLL.ControllerDeposito;
import jumbox.Deposito;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

public class InicioDeposito extends JFrame {

	private static final long serialVersionUID = 1L;
	protected static final JLabel LblError = null;
	
	private JPanel contentPane;
	private JPasswordField passwordField;
	ControllerDeposito controllerD = new ControllerDeposito();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InicioDeposito frame = new InicioDeposito();
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
	public InicioDeposito() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("src\\img\\logo-chico.png"));
		lblNewLabel.setBounds(0, 0, 133, 449);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Deposito");
		lblNewLabel_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1.setFont(new Font("Swis721 Blk BT", Font.BOLD, 90));
		lblNewLabel_1.setBounds(120, -33, 538, 157);
		contentPane.add(lblNewLabel_1);
		

		JPasswordField txtContrasena = new JPasswordField();
		txtContrasena.setBounds(143, 171, 353, 34);
		txtContrasena.setFont(new Font("Arial", Font.PLAIN, 18));
		contentPane.add(txtContrasena);


		JLabel LblError = new JLabel("");
		LblError.setForeground(new Color(255, 0, 0));
		LblError.setFont(new Font("Arial", Font.PLAIN, 15));
		LblError.setBounds(142, 208, 354, 14);
		contentPane.add(LblError);


		JLabel lblNewLabel_2 = new JLabel("Introduzca la Contraseña");
		lblNewLabel_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(144, 126, 353, 34);
		contentPane.add(lblNewLabel_2);


		JButton btnNewButton = new JButton("Ingresar");
		btnNewButton.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnNewButton.setBounds(143, 233, 353, 50);
		contentPane.add(btnNewButton);


		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String contrasena = new String(txtContrasena.getPassword()).trim();
		        LblError.setText("");

		        if (contrasena.isEmpty()) {
		            LblError.setText("Campo vacío");
		        } else {
		            Deposito usuario = controllerD.loginDeposito(contrasena);
		            if (usuario != null) {
		            	dispose();
		            	Deposito.IngresoDeposito(contrasena);
		            } else {
		                LblError.setText("Contraseña incorrecta");
		            }
		        }
		    }
		});


		
		
	}
}
