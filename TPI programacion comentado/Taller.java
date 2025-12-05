import java.util.LinkedList;
import java.util.Queue;
import java.io.Serializable;

// CLASE GENÉRICA <T>: Permite reutilizar el taller para cualquier hijo de Vehiculo.
public class Taller<T extends Vehiculo> implements Serializable {
    
    // COLECCIÓN: Queue (Cola).
    // Implementación: LinkedList (Eficiente para sacar el primero).
    // Lógica: FIFO (First In, First Out - Primero en llegar, primero en salir).
    private Queue<T> colaDeEspera;

    public Taller() {
        this.colaDeEspera = new LinkedList<>();
    }

    // Método para encolar (Poner al final de la fila)
    public void ingresarVehiculo(T vehiculo) {
        if (vehiculo.necesitaMantenimiento()) {
            colaDeEspera.add(vehiculo);
            System.out.println("📋 Vehiculo " + vehiculo.getModelo() + " ingresado a la cola del taller.");
        }
    }

    // Método para desencolar (Atender al primero)
    public void procesarSiguiente() {
        // .poll() recupera y ELIMINA la cabeza de la cola. Devuelve null si está vacía.
        T vehiculo = colaDeEspera.poll();
        
        if (vehiculo != null) {
            System.out.println("\n--- 🛠 INICIANDO SERVICIO MECANICO ---");
            vehiculo.realizarMantenimiento(); // Cambia el estado a revisado
            System.out.println("💦 Lavando y detallando: " + vehiculo.getModelo());
            System.out.println("--- ✅ VEHICULO LISTO PARA VENTA ---");
        } else {
            System.out.println("No hay vehiculos en espera en el taller.");
        }
    }

    public int cantidadEnEspera() {
        return colaDeEspera.size();
    }
}