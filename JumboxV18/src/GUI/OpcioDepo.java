package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;

public class OpcioDepo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpcioDepo frame = new OpcioDepo();
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
	public OpcioDepo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 842, 508);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("¿Que quieres hacer ?");
		lblNewLabel_2.setBounds(23, 162, 484, 54);
		lblNewLabel_2.setFont(new Font("Leelawadee UI", Font.BOLD, 35));
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("Deposito");
		lblNewLabel_1.setBounds(242, 0, 538, 157);
		lblNewLabel_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 75));
		contentPane.add(lblNewLabel_1);
		
		JButton btnArmarenvio = new JButton("Armar_Envio");
		btnArmarenvio.setBounds(25, 261, 178, 64);
		btnArmarenvio.setFont(new Font("Dialog", Font.PLAIN, 23));
		contentPane.add(btnArmarenvio);
		
		JButton btnCrearproducto = new JButton("Crear_Producto");
		btnCrearproducto.setBounds(213, 261, 198, 64);
		btnCrearproducto.setFont(new Font("Dialog", Font.PLAIN, 23));
		contentPane.add(btnCrearproducto);
		
		JButton btnEditarproducto = new JButton("Editar_Producto");
		btnEditarproducto.setBounds(421, 261, 198, 64);
		btnEditarproducto.setFont(new Font("Dialog", Font.PLAIN, 23));
		contentPane.add(btnEditarproducto);
		
		JButton btnVerstock = new JButton("Ver_Stock");
		btnVerstock.setBounds(629, 261, 153, 64);
		btnVerstock.setFont(new Font("Dialog", Font.PLAIN, 23));
		contentPane.add(btnVerstock);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Dialog", Font.PLAIN, 23));
		btnSalir.setBounds(325, 363, 153, 75);
		contentPane.add(btnSalir);
	}

}
