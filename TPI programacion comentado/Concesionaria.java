import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

// CONTROLADOR: Gestiona la lógica principal, validaciones y persistencia.
public class Concesionaria {
    // COLECCIÓN: ArrayList (Lista dinámica). Ideal para búsquedas por índice o patente.
    private List<Vehiculo> inventario;
    private Taller<Vehiculo> taller; 
    private final String ARCHIVO = "inventario.dat";
    private Scanner scanner; 

    public Concesionaria() {
        this.inventario = new ArrayList<>();
        this.taller = new Taller<>();
        this.scanner = new Scanner(System.in); 
        cargarDatos(); // Carga automática al iniciar el programa
    }
    
    // --- Lógica CRUD (Create, Read, Update, Delete) ---
    
    // Método que lanza una excepción si hay error (throws)
    public void agregarVehiculo(Vehiculo v) throws PatenteDuplicadaException {
        // VALIDACIÓN DE NEGOCIO: Unicidad de patente
        if (existePatente(v.getPatente())) {
            throw new PatenteDuplicadaException("La patente " + v.getPatente() + " ya existe en el sistema.");
        }

        inventario.add(v);
        System.out.println("\n✅ Vehículo " + v.getModelo() + " (Patente: " + v.getPatente() + ") agregado exitosamente.");
        
        // LÓGICA DE DERIVACIÓN: Si es usado, va al taller automáticamente.
        if (v.getCondicion() == Condicion.USADO) {
            taller.ingresarVehiculo(v);
        }
        guardarDatos(); // PERSISTENCIA AUTOMÁTICA
    }

    public boolean existePatente(String patente) {
        String patenteLimpia = patente.replace(" ", ""); // Normalización de datos
        for (Vehiculo v : inventario) {
            String pGuardada = v.getPatente().replace(" ", "");
            if (pGuardada.equalsIgnoreCase(patenteLimpia)) {
                return true; 
            }
        }
        return false; 
    }

    public void mostrarInventario() {
        System.out.println("\n--- INVENTARIO ACTUAL ---");
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            for (int i = 0; i < inventario.size(); i++) {
                Vehiculo v = inventario.get(i);
                System.out.println(v.toString());
            }
        }
    }
    
    // Búsqueda que lanza excepción si falla 
    public Vehiculo buscarVehiculoPorPatente(String patente) throws VehiculoNoEncontradoException {
        String patenteBusqueda = patente.replace(" ", "");
        for (Vehiculo v : inventario) {
            String pGuardada = v.getPatente().replace(" ", "");
            // equals() para tener en cuenta mayúsculas/minúsculas al borrar
            if (pGuardada.equals(patenteBusqueda)) {
                return v;
            }
        }
        throw new VehiculoNoEncontradoException("No existe ningún vehículo con la patente exacta: " + patente);
    }
    
    public void eliminarVehiculo(String patente) throws VehiculoNoEncontradoException {
        Vehiculo v = buscarVehiculoPorPatente(patente); // Reutilizamos la lógica de búsqueda
        inventario.remove(v);
        System.out.println("🗑 Vehículo con patente [" + v.getPatente() + "] eliminado correctamente.");
        guardarDatos();
    }
    
    public void atenderTaller() {
        taller.procesarSiguiente();
        guardarDatos(); 
    }
    
    public boolean isInventarioEmpty() {
        return inventario.isEmpty();
    }

    // --- Persistencia (Serialización Binaria) ---
    // Transforma los objetos en bytes para guardarlos en disco.
    
    public void guardarDatos() {
        // Try-with-resources: Cierra el archivo automáticamente al terminar.
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            out.writeObject(inventario); // Guarda toda la lista de una vez
            out.writeObject(taller);     // Guarda el estado del taller
        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void cargarDatos() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            // Lee los objetos en el MISMO orden en que se guardaron
            inventario = (List<Vehiculo>) in.readObject();
            taller = (Taller<Vehiculo>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("📂 Archivo de datos no encontrado. Iniciando sistema vacío.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
    }
    
    // --- Métodos Auxiliares para Data Entry (UX) ---
    // Usan REGEX para validar lo que escribe el usuario
    
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
    
    // Valida: Solo letras y espacios (Regex: ^[a-zA-ZñÑ\s]+$)
    public String leerTextoSoloLetras(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("⚠ Error: El campo no puede estar vacío.");
                continue;
            }
            if (!input.matches("^[a-zA-ZñÑ\\s]+$")) {
                System.out.println("⚠ Error: Ingrese solo letras.");
                continue; 
            }
            return input; 
        }
    }

    // Valida: Letras y Números (Regex: ^[a-zA-Z0-9\s]+$)
    public String leerTextoAlfanumerico(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("⚠ Error: El campo no puede estar vacío.");
                continue;
            }
            if (!input.matches("^[a-zA-Z0-9\\s]+$")) {
                System.out.println("⚠ Error: Ingrese solo letras y números.");
                continue; 
            }
            return input.toUpperCase(); // Estandariza a mayúsculas
        }
    }
    
    public int leerOpcionRango(String mensaje, int min, int max) {
        while (true) {
            try {
                System.out.print(mensaje);
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion >= min && opcion <= max) {
                    return opcion; 
                } else {
                    System.out.println("⚠ Error: La opción debe estar entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Error: Debe ingresar un número válido.");
            }
        }
    }
    
    public int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠ Error: Debe ingresar un número válido.");
            }
        }
    }
}
