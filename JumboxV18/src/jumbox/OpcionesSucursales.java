package jumbox;

public enum OpcionesSucursales {

	
	Primera(1),
	Segunda(2),
	Tercera(3),
	Cuarta(4);
	
	private final int id;
	OpcionesSucursales(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
