// Excepción personalizada para manejar errores de búsqueda por patente.
public class VehiculoNoEncontradoException extends Exception {
    public VehiculoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}