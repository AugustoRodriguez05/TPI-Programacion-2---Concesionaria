// HERENCIA: Auto "Es Un" Vehiculo. Hereda todos sus atributos y métodos.
public class Auto extends Vehiculo {
    private TipoCarroceria carroceria; // Atributo específico de Auto

    public Auto() {}

    public Auto(String marca, String modelo, String patente, int anio, String color, double precio, Condicion condicion, TipoCarroceria carroceria) {
        // SUPER: Llama al constructor del padre (Vehiculo) para guardar los datos comunes.
        super(marca, modelo, patente, anio, color, precio, condicion);
        this.carroceria = carroceria;
    }

    public TipoCarroceria getCarroceria() { return carroceria; }
    public void setCarroceria(TipoCarroceria carroceria) { this.carroceria = carroceria; }

    // POLIMORFISMO: Sobrescribe toString para mostrar también la carrocería.
    @Override
    public String toString() {
        return super.toString() + " | Carroceria: " + carroceria;
    }
}