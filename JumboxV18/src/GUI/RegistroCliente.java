package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jumbox.Cliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class RegistroCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsu;
	private JPasswordField txtPass;
	private JTextField txtDire;
	private JTextField txtTel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroCliente frame = new RegistroCliente();
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
	public RegistroCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 500);
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
		lblNewLabel_1.setBounds(99, 0, 436, 80);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre");
		lblNewLabel_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(99, 107, 167, 26);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Contraseña");
		lblNewLabel_2_1.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2_1.setBounds(99, 299, 167, 32);
		contentPane.add(lblNewLabel_2_1);
		
		JTextField txtUsu = new JTextField();
		txtUsu.setBounds(97, 138, 288, 26);
		contentPane.add(txtUsu);
		txtUsu.setColumns(10);
		
		JTextField txtPass = new JPasswordField();
		txtPass.setBounds(97, 331, 288, 26);
		contentPane.add(txtPass	);
		
		JButton btnNewButton = new JButton("Registrarse");
		btnNewButton.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnNewButton.setBounds(98, 400, 287, 50);
		contentPane.add(btnNewButton);
		
		JLabel LblError = new JLabel("");
		LblError.setForeground(new Color(255, 0, 0));
		LblError.setFont(new Font("Arial", Font.PLAIN, 15));
		LblError.setBounds(99, 368, 286, 14);
		contentPane.add(LblError);
		
		JTextField txtDire = new JTextField();
		txtDire.setColumns(10);
		txtDire.setBounds(97, 207, 288, 26);
		contentPane.add(txtDire);
		
		JLabel lblNewLabel_2_2 = new JLabel("Direccion");
		lblNewLabel_2_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2_2.setBounds(97, 175, 167, 26);
		contentPane.add(lblNewLabel_2_2);
		
		JTextField txtTel = new JTextField();
		txtTel.setColumns(10);
		txtTel.setBounds(97, 275, 288, 26);
		contentPane.add(txtTel);
		
		JLabel lblNewLabel_2_3 = new JLabel("Telefono");
		lblNewLabel_2_3.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel_2_3.setBounds(99, 244, 167, 26);
		contentPane.add(lblNewLabel_2_3);
		
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	String usu = txtUsu.getText().trim();
		    	String contrasena = txtPass.getText().trim();
		    	String dire = txtDire.getText().trim();
		    	String tel = txtTel.getText().trim();
		        LblError.setText("");
		        
		        int tele;
		        try {
		        	tele = Integer.parseInt(txtTel.getText().trim());
		        } catch (NumberFormatException e1) {
		            LblError.setText("El teléfono es incorrecto");
		            return;
		        }
		        
		        if (contrasena.isEmpty() || usu.isEmpty() || dire.isEmpty() || tel.isEmpty()) {
		            LblError.setText("Campo vacío");
		        } else {
		            Cliente usuario = Cliente.RegistroCliente(usu, contrasena, dire, tele);
		            if (usuario != null) {
		            	dispose();
		            	LoginCliente menuLog = new LoginCliente();
						menuLog.setVisible(true);
		            } else {
		                LblError.setText("Datos ingresados incorrectos, vuelva a intentarlo");
		            }
		        }
		    }
		});
	}
}
