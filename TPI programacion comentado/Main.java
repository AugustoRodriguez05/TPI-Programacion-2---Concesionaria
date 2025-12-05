import java.util.Scanner;

public class Main {
    
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Concesionaria concesionaria = new Concesionaria();
        int opcion = -1;
        
    //BUCLE PRINCIPAL: Mantiene el menu corriendo hasta que el usuario elija una opcion
        while (opcion != 0) {
            System.out.println("\n========================================");
            System.out.println("       CONCESIONARIA - GESTION 2025");
            System.out.println("========================================");
            System.out.println("1. ➕ Agregar Vehículo");
            System.out.println("2. 📋 Ver Inventario");
            System.out.println("3. 🔧 Procesar Taller");
            System.out.println("4. ❌ Eliminar Vehículo (Por Patente)");
            System.out.println("0. 💾 Salir");
            System.out.print(">> Elija opción: ");
            
            try {
                String input = scanner.nextLine();
                if(input.isEmpty()) continue;
                opcion = Integer.parseInt(input);
                
                switch (opcion) {
                    case 1:
                        agregarVehiculoInteractivo(concesionaria); 
                        break;
                    case 2:
                        concesionaria.mostrarInventario();
                        break;
                    case 3:
                        concesionaria.atenderTaller();
                        break;
                    case 4:
                        eliminarVehiculoInteractivo(concesionaria);
                        break;
                    case 0:
                        concesionaria.guardarDatos(); 
                        System.out.println("Guardando cambios... ¡Hasta luego!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("⚠ Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Error: Ingrese un número.");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
    }

    // --- Métodos del Menú ---
    
    private static void eliminarVehiculoInteractivo(Concesionaria c) {
        c.mostrarInventario();
        if (c.isInventarioEmpty()) return;

        while (true) {
            try {
                System.out.println("\n(Escriba '0' para cancelar)");
                String patente = c.leerTextoAlfanumerico("Ingrese la PATENTE del vehículo a eliminar: ");
                
                if (patente.equals("0")) {
                    System.out.println("Operación cancelada.");
                    break; 
                }

                c.eliminarVehiculo(patente);
                break; 

            } catch (VehiculoNoEncontradoException e) {
                System.out.println("❌ ERROR: " + e.getMessage());
                System.out.println("Intente nuevamente.");
               
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                break; 
            }
        }
    }
    
    public static void agregarVehiculoInteractivo(Concesionaria c) {
        try {
            System.out.println("\n--- TIPO DE VEHÍCULO ---");
            System.out.println("1. Auto");
            System.out.println("2. Moto");
            int tipo = c.leerOpcionRango("Seleccione tipo (1-2): ", 1, 2);

            System.out.println("\n--- CONDICIÓN ---");
            System.out.println("1. Nuevo (0 Km)");
            System.out.println("2. Usado (Va a Taller)");
            int cond = c.leerOpcionRango("Seleccione condición (1-2): ", 1, 2);
            Condicion condicionSeleccionada = (cond == 1) ? Condicion.NUEVO : Condicion.USADO;

            Vehiculo nuevoVehiculo = null;

            if (tipo == 1) {
                nuevoVehiculo = crearAuto(c, condicionSeleccionada);
            } else if (tipo == 2) {
                nuevoVehiculo = crearMoto(c, condicionSeleccionada);
            }

            if (nuevoVehiculo != null) {
                // Aquí capturamos si la patente está duplicada
                c.agregarVehiculo(nuevoVehiculo);
            }
            
        } catch (PatenteDuplicadaException e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            System.out.println("No se guardó el vehículo. Intente de nuevo.");
            
        } catch (Exception e) {
            System.out.println("Error en la carga: " + e.getMessage());
        }
    }
    
    // --- Creadores Auxiliares ---
    
    public static Auto crearAuto(Concesionaria c, Condicion condicion) {
        System.out.println("\n--- DATOS DEL AUTO ---");
        
      
        String marca = c.leerTextoAlfanumerico("Marca: "); 
        String modelo = c.leerTextoAlfanumerico("Modelo: ");
        String patente = c.leerTextoAlfanumerico("Patente: "); 
        
        int anio = c.leerEntero("Año: ");
        
        
        String color = c.leerTextoSoloLetras("Color: "); 
        
        double precio = (double) c.leerEntero("Precio: "); 
        
        System.out.println("Carroceria: 1.SEDAN, 2.HATCHBACK, 3.PICKUP, 4.SUV");
        int tipo = c.leerOpcionRango("Seleccione (1-4): ", 1, 4);
        
        TipoCarroceria tc = TipoCarroceria.SEDAN;
        if (tipo == 2) tc = TipoCarroceria.HATCHBACK;
        if (tipo == 3) tc = TipoCarroceria.PICKUP;
        if (tipo == 4) tc = TipoCarroceria.SUV;
        
        return new Auto(marca, modelo, patente, anio, color, precio, condicion, tc);
    }

    public static Moto crearMoto(Concesionaria c, Condicion condicion) {
        System.out.println("\n--- DATOS DE LA MOTO ---");
        
        String marca = c.leerTextoAlfanumerico("Marca: ");
        String modelo = c.leerTextoAlfanumerico("Modelo: ");
        String patente = c.leerTextoAlfanumerico("Patente (Sin guiones): "); 
        
        int anio = c.leerEntero("Año: ");
        
        String color = c.leerTextoSoloLetras("Color: ");
        
        double precio = (double) c.leerEntero("Precio: ");
        
        System.out.println("Estilo: 1.SCOOTER, 2.CALLE, 3.DEPORTIVA, 4.ENDURO");
        int tipo = c.leerOpcionRango("Seleccione (1-4): ", 1, 4);
        
        EstiloMoto em = EstiloMoto.CALLE;
        if (tipo == 1) em = EstiloMoto.SCOOTER;
        if (tipo == 3) em = EstiloMoto.DEPORTIVA;
        if (tipo == 4) em = EstiloMoto.ENDURO;

        return new Moto(marca, modelo, patente, anio, color, precio, condicion, em);
    }
}

