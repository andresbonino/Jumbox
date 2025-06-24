package GUI;

import javax.swing.*;

import DDL.Conexion;
import DDL.ControllerCarrito;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.LinkedList;
import jumbox.*;

public class ClienteCompras extends JFrame {

    private Cliente cliente;
    private JComboBox<OpcionesSucursales> comboSucursales;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaProductos;
    private LinkedList<Productos> productosSucursal = new LinkedList<>();
    private Connection con = Conexion.getInstance().getConnection();
    private int idCarritoActual;
    private JTextField txtCantidad;
    ControllerCarrito<Carrito> controllerCarrito = new ControllerCarrito<>();
    ControllerCarrito<Carrito> controllerC = new ControllerCarrito<>();



    public ClienteCompras(Cliente cliente) {
        this.cliente = cliente;

        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        // Sucursal
        comboSucursales = new JComboBox<>(OpcionesSucursales.values());
        JButton btnCargarProductos = new JButton("Cargar productos");
        JPanel panelSucursal = new JPanel();
        panelSucursal.setBounds(0, 0, 584, 33);
        panelSucursal.add(new JLabel("Sucursal:"));
        panelSucursal.add(comboSucursales);
        panelSucursal.add(btnCargarProductos);
        getContentPane().add(panelSucursal);

        // Lista productos
        modeloLista = new DefaultListModel<>();
        listaProductos = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaProductos);
        scrollPane.setBounds(0, 33, 584, 355);
        getContentPane().add(scrollPane);

        // Confirmar selección
        JButton btnAgregar = new JButton("Agregar al carrito");
        btnAgregar.setBounds(0, 438, 293, 23);
        getContentPane().add(btnAgregar);
        
        // Cerrar la ventana
        JButton btnSalir = new JButton("Volver al menu");
        btnSalir.setBounds(291, 438, 293, 23);
        getContentPane().add(btnSalir);
        
        JLabel lblNewLabel = new JLabel("Insertar la cantidad");
        lblNewLabel.setBounds(10, 399, 134, 14);
        getContentPane().add(lblNewLabel);
        
        txtCantidad = new JTextField();
        txtCantidad.setFont(new Font("Arial", Font.PLAIN, 18));
        txtCantidad.setColumns(10);
        txtCantidad.setBounds(10, 413, 283, 20);
        getContentPane().add(txtCantidad);
        
        // EVENTO VOLVER AL MENU
        btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

        // EVENTO CARGAR PRODUCTOS
        btnCargarProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarProductos();
			}
		});

        // EVENTO AGREGAR PRODUCTO

        btnAgregar.addActionListener(new ActionListener() { // --> ARREGLAR ESTO
			public void actionPerformed(ActionEvent arg0) {
				agregarAlCarrito();
			}
		});

        crearCarrito();
    }

    private void crearCarrito() {
        try {
            PreparedStatement stmtCarrito = con.prepareStatement(
                "INSERT INTO carrito(fk_cliente) VALUES (?)", Statement.RETURN_GENERATED_KEYS
            );
            stmtCarrito.setInt(1, cliente.getIdCliente());
            stmtCarrito.executeUpdate();

            ResultSet generatedKeys = stmtCarrito.getGeneratedKeys();
            if (generatedKeys.next()) {
                idCarritoActual = generatedKeys.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al crear carrito.");
        }
    }

    private void cargarProductos() {
        try {
            productosSucursal.clear();
            modeloLista.clear();

            int idSucursal = ((OpcionesSucursales) comboSucursales.getSelectedItem()).getId();

            PreparedStatement stmt = con.prepareStatement(
                "SELECT a.fk_producto, a.cantidad, p.nombre, p.precio " +
                "FROM almacen_sucursal a " +
                "JOIN producto p ON a.fk_producto = p.id_producto " +
                "WHERE a.fk_sucursal = ? AND a.cantidad > 0"
            );
            stmt.setInt(1, idSucursal);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Productos prod = new Productos(
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad")
                );
                prod.setIdProducto(rs.getInt("fk_producto"));
                productosSucursal.add(prod);
                modeloLista.addElement(prod.getNombre() + " | $" + prod.getPrecio() + " | Stock: " + prod.getStock());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar productos.");
        }
    }

    private void agregarAlCarrito() { 	// --> ARREGLAR ESTO
        int index = listaProductos.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }

        Productos producto = productosSucursal.get(index);

        String texto = txtCantidad.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(texto);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero.");
                return;
            }
            if (cantidad > producto.getStock()) {
                JOptionPane.showMessageDialog(this, "No hay suficiente stock.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.");
            return;
        }

        try (PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO producto_carrito(fk_producto, fk_carrito, cantidad) VALUES (?, ?, ?)"
            )) {
            stmt.setInt(1, producto.getIdProducto());
            stmt.setInt(2, idCarritoActual);
            stmt.setInt(3, cantidad);
            int filasInsertadas = stmt.executeUpdate();

            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(this, "Producto agregado al carrito.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo agregar el producto al carrito.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar al carrito: " + ex.getMessage());
        }
    }


}
