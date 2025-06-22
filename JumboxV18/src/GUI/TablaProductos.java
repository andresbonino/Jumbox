package GUI;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

<<<<<<< Updated upstream
import DDL.ControllerProducto;
=======
import DLL.ControllerProducto;
>>>>>>> Stashed changes
import jumbox.Categorias;
import jumbox.Cliente;
import jumbox.Productos;
import jumbox.Usuarios;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TablaProductos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private Productos productoSeleccionado;
	private JTextField textField;
    private JTextField inpFiltro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TablaProductos frame = new TablaProductos();
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
	public TablaProductos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 489, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Productos.crearProducto();
			}
		});
		btnAgregar.setBounds(10, 195, 89, 33);
		contentPane.add(btnAgregar);
		
		
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (productoSeleccionado != null) {
					ControllerProducto controller = new ControllerProducto();
					controller.editar(productoSeleccionado);
				} else {
					JOptionPane.showMessageDialog(null, "Seleccione un producto para editar.");
				}
			}
		});
		btnEditar.setBounds(107, 195, 89, 33);
		contentPane.add(btnEditar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (productoSeleccionado != null && productoSeleccionado.getIdProducto() > 0) {
		        	String[] confirmar = {"Cancelar","Confirmar"};
		        	int confirmacion = 0;
		        	confirmacion = JOptionPane.showOptionDialog(null, "ESTAS SEGURO QUE DESEAS ELIMINAR EL PRODUCTO", "Jumbox", 0, 0, null, confirmar, confirmar);
		            if (confirmacion==1) {
		            	ControllerProducto eliminarP = new ControllerProducto();
			            eliminarP.eliminarProducto(productoSeleccionado);
					} else {

					}
		        } else {
		            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar.");
		        }
		    }
		});
		btnEliminar.setBounds(206, 195, 89, 33);
		contentPane.add(btnEliminar);
		
		textField = new JTextField();
		textField.setBounds(374, 198, 89, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MenuDeposito menu = new MenuDeposito();
				menu.setVisible(true);
			}
		});
		btnSalir.setBounds(10, 256, 89, 23);
		contentPane.add(btnSalir);
		
		JLabel lblNewLabel_1 = new JLabel("Productos");
		lblNewLabel_1.setBackground(new Color(0, 128, 0));
		lblNewLabel_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1.setFont(new Font("Swis721 Blk BT", Font.BOLD, 50));
		lblNewLabel_1.setBounds(87, -20, 505, 80);
		contentPane.add(lblNewLabel_1);
		

		
        JLabel lblSeleccionado = new JLabel("Seleccionado:");
        lblSeleccionado.setBounds(10, 49, 760, 20);
        contentPane.add(lblSeleccionado);
		
        model = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Stock", "Categoria", "IDCategoria"}, 0);
        table = new JTable(model);
        
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 67, 453, 103);
		contentPane.add(scrollPane);;
		
        table.getColumnModel().getColumn(5).setMinWidth(0);
		table.getColumnModel().getColumn(5).setMaxWidth(0);
		table.getColumnModel().getColumn(5).setWidth(0);
        
		  table.getSelectionModel().addListSelectionListener(e -> {
	            if (!e.getValueIsAdjusting()) {
	                int row = table.getSelectedRow();
	                if (row != -1) {
	                	productoSeleccionado = new Productos(
	                		    (int) model.getValueAt(row, 0),
	                		    (String) model.getValueAt(row, 1),
	                		    (double) model.getValueAt(row, 2),
	                		    (int) model.getValueAt(row, 3),
	                		    (int) model.getValueAt(row, 5)
	                		);

	                    lblSeleccionado.setText("Seleccionado: ID:" + productoSeleccionado.getIdProducto()
	                            + ", Nombre:" + productoSeleccionado.getNombre()
	                            + ", Precio:" + productoSeleccionado.getPrecio()
	                            + ", Stock:" + productoSeleccionado.getStock()
	                            + ", Categoria:" + obtenerNombreCategoria(productoSeleccionado.getCategoria()));
	                    
	                   
	            	
	                }
	            }
	        });
		  inpFiltro = new JTextField();
          inpFiltro.setBounds(10, 347, 118, 40);
          contentPane.add(inpFiltro);
          inpFiltro.setColumns(10);
          inpFiltro.setVisible(true);
          JButton btnNewButton = new JButton("Filtrar");
          btnNewButton.setVisible(true);
          btnNewButton.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				cargarTablaFiltrada(inpFiltro.getText());
  			}
  		});
  		btnNewButton.setBounds(374, 221, 89, 23);
  		contentPane.add(btnNewButton);
          
          
          
  		JLabel lblNewLabel = new JLabel("Filtro");
  		lblNewLabel.setBounds(374, 184, 46, 14);
  		contentPane.add(lblNewLabel);
  		
  		JLabel lblNewLabel_2 = new JLabel("* selecciona Flitrar para actualizar.");
  		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
  		lblNewLabel_2.setBounds(74, 231, 208, 14);
  		contentPane.add(lblNewLabel_2);
		  cargarTabla();
	}
	
	private String obtenerNombreCategoria(int idCategoria) {
	    for (Categorias cat : Categorias.values()) {
	        if (cat.getId() == idCategoria) {
	            return cat.name().replace("_", " ");
	        }
	    }
	    return "Desconocida";
	}

	
    private void cargarTablaFiltrada(String filtro) {
        model.setRowCount(0);
        LinkedList<Productos> productos = ControllerProducto.mostrarProducto2();
        for (Productos u : productos) {
        	if (u.getNombre().toLowerCase() .startsWith(filtro.toLowerCase())) {
		
            model.addRow(
            		new Object[]{
            				u.getIdProducto(),
            				u.getNombre(),
            				u.getPrecio(),
            				u.getStock(),
            				obtenerNombreCategoria(u.getCategoria()),
            		        u.getCategoria()}
            		);
    		
			}
        }
    }
    private void cargarTabla() {
        model.setRowCount(0);
        LinkedList<Productos> productos = ControllerProducto.mostrarProducto2();
        for (Productos u : productos) {
        	
            model.addRow(
            		new Object[]{
            				u.getIdProducto(),
            				u.getNombre(),
            				u.getPrecio(),
            				u.getStock(),
            				obtenerNombreCategoria(u.getCategoria()),
            		        u.getCategoria()}
            		);
    		
			
        }
    }
}
