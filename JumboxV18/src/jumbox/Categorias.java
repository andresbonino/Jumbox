package jumbox;


public enum Categorias {
    Refrigerado(1),
    Mueble(2),
    Electrodomestico(3),
    Alimento(4),
    Limpieza(5),
    Higiene_Personal(6),
    Farmacia(7),
    Mascotas(8),
    Hogar(9),
    Ferreteria(10),
    Jugueteria(11),
    Libreria(12),
    Bebidas(13),
    Despensa(14);

    private final int id;

    Categorias(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
