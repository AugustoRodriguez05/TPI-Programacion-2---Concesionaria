// HERENCIA: Moto "Es Un" Vehiculo.
public class Moto extends Vehiculo {
    private EstiloMoto estilo; // Atributo específico de Moto

    public Moto() {}

    public Moto(String marca, String modelo, String patente, int anio, String color, double precio, Condicion condicion, EstiloMoto estilo) {
        super(marca, modelo, patente, anio, color, precio, condicion);
        this.estilo = estilo;
    }

    public EstiloMoto getEstilo() { return estilo; }
    public void setEstilo(EstiloMoto estilo) { this.estilo = estilo; }

    @Override
    public String toString() {
        return super.toString() + " | Estilo: " + estilo;
    }
}