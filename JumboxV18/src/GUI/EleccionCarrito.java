package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jumbox.Carrito;
import jumbox.Cliente;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;

public class EleccionCarrito extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCantNueva;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EleccionCarrito frame = new EleccionCarrito(null, null);
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
	private Carrito itemSeleccionado;
	private LinkedList<Carrito> carrito = new LinkedList<>();
	
	public EleccionCarrito(Carrito itemSeleccionado, LinkedList<Carrito> carrito) {
		this.itemSeleccionado = itemSeleccionado;
	    this.carrito = carrito;
		
		// Editar cantidad de productos en el carrito
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 690, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(27, 28, 620, 416);
		contentPane.add(tabbedPane);
		
		JPanel CambiarCantidad = new JPanel();
		tabbedPane.addTab("New tab", null, CambiarCantidad, null);
		CambiarCantidad.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nueva cantidad");
		lblNewLabel.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
		lblNewLabel.setBounds(168, 122, 200, 38);
		CambiarCantidad.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Editar Producto");
		lblNewLabel_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 70));
		lblNewLabel_1.setBackground(new Color(0, 128, 0));
		lblNewLabel_1.setBounds(52, 11, 553, 80);
		CambiarCantidad.add(lblNewLabel_1);
		
		txtCantNueva = new JTextField();
		txtCantNueva.setFont(new Font("Arial", Font.PLAIN, 18));
		txtCantNueva.setColumns(10);
		txtCantNueva.setBounds(168, 171, 313, 32);
		CambiarCantidad.add(txtCantNueva);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnAceptar.setBounds(184, 249, 287, 50);
		CambiarCantidad.add(btnAceptar);
		
		JLabel LblError = new JLabel("");
		LblError.setForeground(Color.RED);
		LblError.setFont(new Font("Arial", Font.PLAIN, 15));
		LblError.setBounds(168, 224, 312, 14);
		CambiarCantidad.add(LblError);
		
		btnAceptar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		        try {
		            String texto = txtCantNueva.getText();
		            LblError.setText("");
		            if (texto.isEmpty()) {
		                LblError.setText("Debe ingresar una cantidad");
		                return;
		            }
		            int nuevaCantidad = Integer.parseInt(texto);
		            
		            if (nuevaCantidad <= 0) {
		                LblError.setText("La cantidad debe ser mayor a cero");
		                return;
		            }
		            if (nuevaCantidad > itemSeleccionado.getProducto().getStock()) {
		                LblError.setText("No hay suficiente stock");
		                return;
		            }

		            itemSeleccionado.setCantidad(nuevaCantidad);
		            LblError.setText("Cantidad actualizada correctamente");
		            dispose();

		        } catch (NumberFormatException e) {
		            LblError.setText("Debe ingresar un número válido");
		        }
		    }
		});

		
		
		
		//Borrar carrito
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("Borrar Producto");
		lblNewLabel_1_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.BOLD, 70));
		lblNewLabel_1_1.setBackground(new Color(0, 128, 0));
		lblNewLabel_1_1.setBounds(38, 11, 556, 80);
		panel_1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("¿Estas seguro de que querés borrar este producto?");
		lblNewLabel_2.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(133, 115, 359, 60);
		panel_1.add(lblNewLabel_2);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnBorrar.setBounds(38, 205, 225, 50);
		panel_1.add(btnBorrar);
		
		btnBorrar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	carrito.remove(itemSeleccionado);
                JOptionPane.showMessageDialog(null, "Producto eliminado del carrito");
                dispose();
		    }
		});
		
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
		btnCancelar.setBounds(364, 205, 230, 50);
		panel_1.add(btnCancelar);
		
		btnCancelar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
                dispose();
		    }
		});
	}
}
