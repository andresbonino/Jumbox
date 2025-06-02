package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DLL.ControllerUsuario;
import jumbox.Cliente;
import jumbox.Deposito;
import jumbox.Usuarios;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class LoginCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	ControllerUsuario controllerU = new ControllerUsuario();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginCliente frame = new LoginCliente();
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
	public LoginCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 511, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("src\\img\\logo-chico.png"));
		lblNewLabel.setBounds(0, -11, 109, 469);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Cliente");
		lblNewLabel_1.setBackground(new Color(0, 128, 0));
		lblNewLabel_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1.setFont(new Font("Swis721 Blk BT", Font.BOLD, 90));
		lblNewLabel_1.setBounds(97, 0, 436, 80);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre");
		lblNewLabel_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(98, 115, 167, 26);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Contraseña");
		lblNewLabel_2_1.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2_1.setBounds(97, 183, 167, 32);
		contentPane.add(lblNewLabel_2_1);
		
		JTextField txtUsuario = new JTextField();
		txtUsuario.setBounds(97, 146, 288, 26);
		txtUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
		txtUsuario.setColumns(10);
		contentPane.add(txtUsuario);
		
		JPasswordField txtContrasena = new JPasswordField();
		txtContrasena.setBounds(96, 210, 288, 34);
		txtContrasena.setFont(new Font("Arial", Font.PLAIN, 18));
		contentPane.add(txtContrasena);
		
		JButton btnNewButton = new JButton("Iniciar Sesion");
		btnNewButton.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnNewButton.setBounds(98, 273, 287, 50);
		contentPane.add(btnNewButton);
		
		JLabel LblError = new JLabel("");
		LblError.setForeground(new Color(255, 0, 0));
		LblError.setFont(new Font("Arial", Font.PLAIN, 15));
		LblError.setBounds(142, 208, 354, 14);
		contentPane.add(LblError);
		
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	String usu = new String(txtUsuario.getName()).trim();
		    	String contrasena = new String(txtContrasena.getPassword()).trim();
		        LblError.setText("");

		        if (contrasena.isEmpty() || usu.isEmpty()) {
		            LblError.setText("Campo vacío");
		        } else {
		            Cliente usuario = controllerU.login(usu, contrasena);
		            if (usuario != null) {
		            	dispose();
		            	Cliente.LoginCliente(usu, contrasena);
		            } else {
		                LblError.setText("Contraseña o usuario incorrecto");
		            }
		        }
		    }
		});

	}
}
