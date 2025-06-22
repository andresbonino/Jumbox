package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import DLL.ControllerUsuario;
import jumbox.Cliente;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class ClienteEleccion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	ControllerUsuario controllerU = new ControllerUsuario();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteEleccion frame = new ClienteEleccion();
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
	public ClienteEleccion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 896, 648);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(21, 11, 836, 574);
		contentPane.add(tabbedPane);
		
		
		//Iniciar Sesion
		JPanel panel = new JPanel();
		tabbedPane.addTab("Iniciar Sesión", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(10, 11, 109, 469);
		lblNewLabel.setIcon(new ImageIcon("src\\img\\logo-chico.png"));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Cliente");
		lblNewLabel_1.setBounds(268, 11, 324, 80);
		lblNewLabel_1.setBackground(new Color(0, 128, 0));
		lblNewLabel_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 90));
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre");
		lblNewLabel_2.setBounds(268, 131, 167, 26);
		lblNewLabel_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Contraseña");
		lblNewLabel_2_1.setBounds(268, 224, 167, 32);
		lblNewLabel_2_1.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		panel.add(lblNewLabel_2_1);
		
		JTextField txtUsuario = new JTextField();
		txtUsuario.setBounds(268, 162, 313, 32);
		txtUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
		txtUsuario.setColumns(10);
		panel.add(txtUsuario);
		
		JPasswordField txtContrasena = new JPasswordField();
		txtContrasena.setBounds(268, 256, 313, 34);
		txtContrasena.setFont(new Font("Arial", Font.PLAIN, 18));
		panel.add(txtContrasena);
		
		JButton btnNewButton = new JButton("Iniciar Sesion");
		btnNewButton.setBounds(279, 336, 287, 50);
		btnNewButton.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		panel.add(btnNewButton);
		
		JLabel LblError = new JLabel("");
		LblError.setBounds(268, 311, 312, 14);
		LblError.setForeground(new Color(255, 0, 0));
		LblError.setFont(new Font("Arial", Font.PLAIN, 15));
		panel.add(LblError);
		
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	String usu = txtUsuario.getText().trim();
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
		
		
		
		//Registrarse
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Registrarse", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon("src\\img\\logo-chico.png"));
		logo.setBounds(10, 11, 109, 469);
		panel_1.add(logo);
		
		JTextField txtUsu = new JTextField();
		txtUsu.setFont(new Font("Arial", Font.PLAIN, 18));
		txtUsu.setBounds(278, 152, 288, 26);
		panel_1.add(txtUsu);
		txtUsu.setColumns(10);
		
		JTextField txtPass = new JPasswordField();
		txtPass.setFont(new Font("Arial", Font.PLAIN, 18));
		txtPass.setBounds(278, 355, 288, 26);
		panel_1.add(txtPass);
		btnNewButton.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		
		JLabel LblErrorRegis = new JLabel("");
		LblErrorRegis.setForeground(new Color(255, 0, 0));
		LblErrorRegis.setFont(new Font("Arial", Font.PLAIN, 15));
		LblErrorRegis.setBounds(278, 392, 286, 14);
		panel_1.add(LblErrorRegis);
		
		JTextField txtDire = new JTextField();
		txtDire.setFont(new Font("Arial", Font.PLAIN, 18));
		txtDire.setColumns(10);
		txtDire.setBounds(278, 221, 288, 26);
		panel_1.add(txtDire);
		
		JLabel lblNewLabel_2_2 = new JLabel("Direccion");
		lblNewLabel_2_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2_2.setBounds(278, 189, 167, 26);
		panel_1.add(lblNewLabel_2_2);
		
		JTextField txtTel = new JTextField();
		txtTel.setFont(new Font("Arial", Font.PLAIN, 18));
		txtTel.setColumns(10);
		txtTel.setBounds(278, 289, 288, 26);
		panel_1.add(txtTel);
		
		JLabel lblNewLabel_2_3 = new JLabel("Telefono");
		lblNewLabel_2_3.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2_3.setBounds(280, 258, 167, 26);
		panel_1.add(lblNewLabel_2_3);
		
		JLabel lblNewLabel_1_1 = new JLabel("Cliente");
		lblNewLabel_1_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.BOLD, 90));
		lblNewLabel_1_1.setBackground(new Color(0, 128, 0));
		lblNewLabel_1_1.setBounds(268, 11, 324, 80);
		panel_1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Contraseña");
		lblNewLabel_2_1_1.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2_1_1.setBounds(278, 326, 167, 32);
		panel_1.add(lblNewLabel_2_1_1);
		
		JLabel lblNewLabel_2_4 = new JLabel("Nombre");
		lblNewLabel_2_4.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2_4.setBounds(278, 115, 167, 26);
		panel_1.add(lblNewLabel_2_4);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnRegistrarse.setBounds(278, 410, 288, 50);
		panel_1.add(btnRegistrarse);
		
		btnRegistrarse.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	String usu = txtUsu.getText().trim();
		    	String contrasena = txtPass.getText().trim();
		    	String dire = txtDire.getText().trim();
		    	String tel = txtTel.getText().trim();
		    	LblErrorRegis.setText("");
		        
		        int tele;
		        
		        
		        if (contrasena.isEmpty() || usu.isEmpty() || dire.isEmpty() || tel.isEmpty()) {
		        	LblErrorRegis.setText("Campo vacío");
		        } else if(ValidarNombre(usu)==false) {
		        	LblErrorRegis.setText("El usuario no cumple con lo pedido");
		        }else if(ValidarDire(dire)==false) {
		        	LblErrorRegis.setText("La direccion no cumple con lo pedido");
		        }else {
		        	try {
			        	tele = Integer.parseInt(txtTel.getText().trim());
			        } catch (NumberFormatException e1) {
			        	LblErrorRegis.setText("El teléfono es incorrecto");
			            return;
			        }
		            Cliente usuario = Cliente.RegistroCliente(usu, contrasena, dire, tele);
		            if (usuario != null) {
		            	dispose();
		            	ClienteEleccion menuLog = new ClienteEleccion();
						menuLog.setVisible(true);
		            } else {
		            	LblErrorRegis.setText("Datos ingresados incorrectos, vuelva a intentarlo");
		            }
		        }
		    }
		});
		
		
	}
	
	//Validaciones
	public static boolean ValidarNombre(String usuario) {
		boolean numero = false;
		if (usuario.isEmpty()) {
			return false;
		} else {
			for (int i = 0; i < usuario.length(); i++) {
				if (Character.isDigit(usuario.charAt(i))) {
					numero=true;
				}
			}
			if (numero==true) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	public static boolean ValidarDire(String direccion) {
		boolean minus = false;
		boolean num = false;
		
		for (int i = 0; i < direccion.length(); i++) {
			if (Character.isLowerCase(direccion.charAt(i))) {
				minus=true;
			}
			if (Character.isDigit(direccion.charAt(i))) {
				num=true;
			}
			}
		if (minus && num) {
			return true;
		} else {
			return false;
		}
	}
}
