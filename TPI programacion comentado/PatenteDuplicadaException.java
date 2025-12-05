// Excepción para evitar el ingreso de vehículos repetidos.
public class PatenteDuplicadaException extends Exception {
    public PatenteDuplicadaException(String mensaje) {
        super(mensaje);
    }
}