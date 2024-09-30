import java.util.*; 
import java.util.concurrent.*;


public class Biblioteca {
    private static List<String> librosDisponibles = new ArrayList<>(Arrays.asList("El Quijote", "Cien años de soledad", "La Odisea"));
    private static List<String> librosPrestados = new ArrayList<>();
    private static Map<String, String> prestamos = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static  boolean continuar = true;

    private static ExecutorService executor = Executors.newFixedThreadPool(3);
   
    public static void main(String[] args) {
       
        while (continuar) {

            mostrarMenu();
                
        }
    }


    public static void iniciarProcesoParalelo(Runnable tarea) {
        executor.submit(tarea);
        
    }

    public static void mostrarMenu() {
        System.out.println("\n---- Menú de la Biblioteca ----\n");
        System.out.println("1. Consultar disponibilidad de libros");
        System.out.println("2. Asignar libro a un lector");
        System.out.println("3. Registrar devolución de libros");
        System.out.println("4. Mostrar lista de libros prestados");
        System.out.println("5. Completar devolución");
        System.out.println("6. Salir \n");
        System.out.print("Seleccione una opción: \n");

        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); 
            switch (opcion) {
                case 1:
                    iniciarProcesoParalelo(() -> consultarDisponibilidadLibros());
                    break;
                case 2:
                    iniciarProcesoParalelo(() -> asignarLibro());
                    continuar =false;
                    break;
                case 3:
                    iniciarProcesoParalelo(() -> registrarDevolucion());
                    break;
                case 4:
                    iniciarProcesoParalelo(() -> mostrarLibrosPrestados());
                    break;
                case 5:
                    iniciarProcesoParalelo(() -> completarDevolucion());
                    mostrarMenu();
                    break;
                case 6:
                    continuar = false;
                    executor.shutdown(); 
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.nextLine(); 
        }
    }

    public static void consultarDisponibilidadLibros() {
        System.out.println("Consultando libros disponibles...");
        try {
            TimeUnit.SECONDS.sleep(1); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (librosDisponibles.isEmpty()) {
            System.out.println("No hay libros disponibles.");
        } else {
            System.out.println("Libros disponibles: " + librosDisponibles);
        }
    }

    public static void asignarLibro() {
        consultarDisponibilidadLibros();
     

        if (!librosDisponibles.isEmpty()) {
            System.out.print("Ingrese el nombre del lector: ");
            String lector = scanner.nextLine();
            System.out.print("Ingrese el nombre del libro que desea prestar: ");
            String libro = scanner.nextLine();

            if (librosDisponibles.contains(libro)) {
                librosDisponibles.remove(libro);
                librosPrestados.add(libro);
                prestamos.put(lector, libro);
                System.out.println("Libro '" + libro + "' asignado al lector " + lector + ".");
            } else {
                System.out.println("El libro no está disponible.");
            }

            continuar = true;
            mostrarMenu();
        }
    }

    public static void registrarDevolucion() {
       
        System.out.print("Ingrese el nombre del lector que devuelve el libro: ");
        String lector = scanner.nextLine();
       

        if (prestamos.containsKey(lector)) {
            String libro = prestamos.get(lector);
            prestamos.remove(lector);
            librosPrestados.remove(libro);
            librosDisponibles.add(libro);
            System.out.println("Libro '" + libro + "' devuelto por el lector " + lector + ".");
            mostrarMenu();
        } else {
            System.out.println("El lector no tiene libros prestados.");
            mostrarMenu();
        }
    }

    public static void mostrarLibrosPrestados() {
        
        System.out.println("Consultando libros prestados...");
        try {
            TimeUnit.SECONDS.sleep(1); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (librosPrestados.isEmpty()) {
            System.out.println("No hay libros prestados.");
            mostrarMenu();
        } else {
            System.out.println("Libros prestados: " + librosPrestados);
            System.out.println("Prestamos actuales: " + prestamos);
            mostrarMenu();
        }
    }

    public static void completarDevolucion() {
    
        System.out.println("Devolución completada exitosamente.");
        mostrarMenu();
    }
}
