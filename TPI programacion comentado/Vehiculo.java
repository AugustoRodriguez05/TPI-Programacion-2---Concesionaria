import java.io.Serializable;

// CLASE ABSTRACTA: No se puede hacer 'new Vehiculo()', es solo un molde base.
// Implements Serializable: Permite guardar el objeto en un archivo .dat.
// Implements IRevisable: Firma el contrato de mantenimiento.
public abstract class Vehiculo implements Serializable, IRevisable {
    
    // ATRIBUTOS PRIVADOS: Aplicación del patrón JAVABEAN (Encapsulamiento).
    // Solo se accede a ellos mediante getters y setters.
    private String marca;
    private String modelo;
    private String patente; // Clave única del sistema
    private int anio;
    private String color;
    private double precio;
    private Condicion condicion; // Uso del Enum
    private boolean mantenimientoRealizado;

    // Constructor vacío (Requisito JavaBean para serialización)
    public Vehiculo() {}

    // Constructor completo para inicializar objetos rápidamente
    public Vehiculo(String marca, String modelo, String patente, int anio, String color, double precio, Condicion condicion) {
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.anio = anio;
        this.color = color;
        this.precio = precio;
        this.condicion = condicion;
        // Lógica de Negocio simple: Si es NUEVO, ya está "listo". Si es USADO, requiere taller.
        this.mantenimientoRealizado = (condicion == Condicion.NUEVO);
    }

    // --- Implementación de la Interfaz IRevisable ---
    // POLIMORFISMO: Define cómo se comporta cualquier vehículo ante una revisión.
    @Override
    public boolean necesitaMantenimiento() {
        // Solo necesita si es USADO y aún NO se hizo el mantenimiento.
        return condicion == Condicion.USADO && !mantenimientoRealizado;
    }

    @Override
    public void realizarMantenimiento() {
        this.mantenimientoRealizado = true; // Cambia el estado del objeto
        System.out.println("🔧 Mantenimiento realizado a: " + this.modelo);
    }

    // --- Getters y Setters (Acceso controlado a los datos) ---
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public Condicion getCondicion() { return condicion; }
    public void setCondicion(Condicion condicion) { this.condicion = condicion; }
    public boolean isMantenimientoRealizado() { return mantenimientoRealizado; }
    public void setMantenimientoRealizado(boolean mantenimientoRealizado) { this.mantenimientoRealizado = mantenimientoRealizado; }

    @Override
    public String toString() {
        return "Patente: [" + patente + "] | " + marca + " " + modelo + " (" + anio + ") | $" + precio + " | " + condicion;
    }
}