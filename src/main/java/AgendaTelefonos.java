import java.io.*;
import java.util.*;
 //Comentario de prueba 
class Contacto implements Serializable {
    private String nombre;
    private String telefono;

    // Constructor de la clase Contacto
    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Método para obtener el nombre del contacto
    public String getNombre() {
        return nombre;
    }

    // Método para obtener el teléfono del contacto
    public String getTelefono() {
        return telefono;
    }

    // Método para representar el contacto como una cadena de texto
    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Teléfono: " + telefono;
    }
}

public class AgendaTelefonos {
    private static final String FICHERO_AGENDA = "agenda.dat";
    private List<Contacto> contactos;

    // Constructor de la clase AgendaTelefonos
    public AgendaTelefonos() {
        contactos = new ArrayList<>();
        cargarAgenda();
    }
    public List<Contacto> getContacts() {
        return contactos;
    }
    
    // Método para mostrar el menú de opciones
    public void mostrarMenu() {
        System.out.println("¡Bienvenido a su Agenda!");
        System.out.println("1. Agregar contacto");
        System.out.println("2. Eliminar contacto");
        System.out.println("3. Mostrar contactos");
        System.out.println("4. Salir");
    }

    public void agregarContacto(String nombre, String telefono) {
        contactos.add(new Contacto(nombre, telefono));
        guardarAgenda();
    }

    public void eliminarContacto(String nombre) {
        contactos.removeIf(contacto -> contacto.getNombre().equalsIgnoreCase(nombre));
        guardarAgenda();
    }

    public void mostrarAgenda() {
        if (contactos.isEmpty()) {
            System.out.println("La agenda está vacía.");
        } else {
            contactos.forEach(System.out::println);
        }
    }

    private void cargarAgenda() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHERO_AGENDA))) {
            contactos = (List<Contacto>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el fichero de la agenda. Se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void guardarAgenda() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO_AGENDA))) {
            oos.writeObject(contactos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String showContacts() {
        if (contactos.isEmpty()) {
            return "La agenda está vacía.";
        } else {
            StringBuilder result = new StringBuilder();
            for (Contacto contacto : contactos) {
                result.append(contacto.getNombre())
                      .append(": ")
                      .append(contacto.getTelefono())
                      .append("\n");
            }
            return result.toString();
        }
    }
    
    public static void main(String[] args) {
        AgendaTelefonos agenda = new AgendaTelefonos();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            agenda.mostrarMenu();
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese el teléfono: ");
                    String telefono = scanner.nextLine();
                    agenda.agregarContacto(nombre, telefono);
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del contacto a eliminar: ");
                    String nombreEliminar = scanner.nextLine();
                    agenda.eliminarContacto(nombreEliminar);
                    break;
                case 3:
                    agenda.mostrarAgenda();
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 4);

        scanner.close();
    }
}