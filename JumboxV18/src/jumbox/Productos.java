package jumbox;

import javax.swing.JOptionPane;

import DDL.ControllerProducto;
import DDL.ControllerUsuario;

public class Productos {

	private String nombre;
	private double precio;
	private int stock;
	private int fk_categoria;
	
	

	public Productos(String nombre, double precio, int stock, int fk_categoria) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
		this.fk_categoria = fk_categoria;
	}
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
	
	public int getFk_categoria() {
		return fk_categoria;
	}

	public void setFk_categoria(int fk_categoria) {
		this.fk_categoria = fk_categoria;
	}

	public static void crearProducto(String nombre, double precio, int stock) {//, int categoria
		ControllerProducto controller = new ControllerProducto();
		
        while (nombre.isEmpty()) {
            nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto");
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error: campo vacío");
            }
        }
           
         while (precio==0) {
        	 precio = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el precio del producto"));
             if (precio==0) {
                JOptionPane.showMessageDialog(null, "Error");
                }
            }
         
         while (stock<0) {
        	 stock = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el stock del producto"));
             if (stock<0) {
                JOptionPane.showMessageDialog(null, "Error");
                }
            }
         
         Categorias fk_categoriaa;
         
		int categoria = (int) JOptionPane.showInputDialog(null, "¿En que categoria entra tu producto?", "Jumbox", 0, null, Categorias.values(), Categorias.values()[0]);


         switch (categoria) {
		case 0:
			fk_categoriaa = Categorias.Refrigerado;
			break;
		case 1:
			fk_categoriaa = Categorias.Mueble;
			break;
		case 2:
			fk_categoriaa = Categorias.Electrodomestico;
			break;
		case 3:
			fk_categoriaa = Categorias.Alimento;
			break;
		case 4:
			fk_categoriaa = Categorias.Limpieza;
			break;
		case 5:
			fk_categoriaa = Categorias.Higiene_Personal;
			break;
			
		case 6:
			fk_categoriaa = Categorias.Farmacia;
			break;
			
		case 7:
			fk_categoriaa = Categorias.Farmacia;
			break;
			
		case 8:
			fk_categoriaa = Categorias.Mascotas;
			break;
			
		case 9:
			fk_categoriaa = Categorias.Hogar;
			break;
			
		case 10:
			fk_categoriaa = Categorias.Ferreteria;
			break;
			
		case 11:
			fk_categoriaa = Categorias.Libreria;
			break;
			
		case 12:
			fk_categoriaa = Categorias.Bebidas;
			break;
			
		case 13:
			fk_categoriaa = Categorias.Despensa;
			break;
		

		default:
			break;
		}
        

	}
	
	
}
