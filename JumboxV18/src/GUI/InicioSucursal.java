package GUI;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DDL.ControllerSucursal;
import jumbox.Sucursal;

public class InicioSucursal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPasswordField txtContrasena;
    private JLabel lblError;
    private JComboBox<String> comboSucursal;
    ControllerSucursal controllerS = new ControllerSucursal();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                InicioSucursal frame = new InicioSucursal();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public InicioSucursal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 654, 380);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblLogo = new JLabel("");
        lblLogo.setIcon(new ImageIcon("src\\img\\logo-chico.png"));
        lblLogo.setBounds(10, 0, 133, 449);
        contentPane.add(lblLogo);

        JLabel lblTitulo = new JLabel("Sucursal");
        lblTitulo.setForeground(new Color(0, 128, 0));
        lblTitulo.setFont(new Font("Swis721 Blk BT", Font.BOLD, 90));
        lblTitulo.setBounds(150, -30, 538, 157);
        contentPane.add(lblTitulo);

        JLabel lblSucursal = new JLabel("Seleccione la Sucursal:");
        lblSucursal.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
        lblSucursal.setBounds(150, 100, 353, 34);
        contentPane.add(lblSucursal);

        comboSucursal = new JComboBox<>();
        comboSucursal.setFont(new Font("Arial", Font.PLAIN, 18));
        comboSucursal.setBounds(150, 130, 353, 30);
        comboSucursal.addItem("Sucursal 1");
        comboSucursal.addItem("Sucursal 2");
        comboSucursal.addItem("Sucursal 3");
        contentPane.add(comboSucursal);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 20));
        lblPass.setBounds(150, 165, 353, 34);
        contentPane.add(lblPass);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(150, 195, 353, 34);
        txtContrasena.setFont(new Font("Arial", Font.PLAIN, 18));
        contentPane.add(txtContrasena);

        lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Arial", Font.PLAIN, 15));
        lblError.setBounds(150, 230, 354, 14);
        contentPane.add(lblError);

        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Swis721 Blk BT", Font.PLAIN, 30));
        btnIngresar.setBounds(150, 260, 353, 50);
        contentPane.add(btnIngresar);

        btnIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String contrasena = txtContrasena.getText();
                int idSucursal = comboSucursal.getSelectedIndex() + 1;

                if (contrasena.isEmpty()) {
                    lblError.setText("Por favor ingrese la contraseña.");
                    return;
                }

                Sucursal usuario2 = controllerS.loginSucursal(idSucursal, contrasena);
                if (usuario2 != null) {
                    JOptionPane.showMessageDialog(null, "Bienvenido a la Sucursal");
                    dispose();
                    MenuSucursal menu = new MenuSucursal(usuario2);
                    menu.setVisible(true);
                } else {
                    lblError.setText("Contraseña incorrecta.");
                   
                }
            }
        });
    }
}
