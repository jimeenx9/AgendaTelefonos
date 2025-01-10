import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AgendaTest {

    private AgendaTelefonos agenda;

    @BeforeEach
    public void setUp() {
        agenda = new AgendaTelefonos();
    }

    @Test
    public void testAddContact() {
        Contacto contact = new Contacto("John Doe", "123456789");
        agenda.agregarContacto(contact.getNombre(), contact.getTelefono());
        assertEquals(1, agenda.getContacts().size());
        assertEquals("John Doe", agenda.getContacts().get(0).getNombre());
    }

    @Test
    public void testRemoveContact() {
        Contacto contact = new Contacto("John Doe", "123456789");
        agenda.agregarContacto(contact.getNombre(), contact.getTelefono());
        agenda.eliminarContacto(contact.getNombre());
        assertEquals(0, agenda.getContacts().size());
    }

    @Test
    public void testShowContacts() {
        Contacto contact1 = new Contacto("John Doe", "123456789");
        Contacto contact2 = new Contacto("Jane Doe", "987654321");
        agenda.agregarContacto(contact1.getNombre(), contact1.getTelefono());
        agenda.agregarContacto(contact2.getNombre(), contact2.getTelefono());
        String expectedOutput = "John Doe: 123456789\nJane Doe: 987654321\n";
        assertEquals(expectedOutput, agenda.showContacts());
    }

    @Test
    public void testFindContactByName() {
        Contacto contact = new Contacto("John Doe", "123456789");
        agenda.agregarContacto(contact.getNombre(), contact.getTelefono());
        Contacto foundContact = agenda.getContacts().stream()
                .filter(c -> c.getNombre().equals("John Doe"))
                .findFirst()
                .orElse(null);
        assertNotNull(foundContact);
        assertEquals("John Doe", foundContact.getNombre());
    }

    @Test
    public void testFindContactByPhoneNumber() {
        Contacto contact = new Contacto("John Doe", "123456789");
        agenda.agregarContacto(contact.getNombre(), contact.getTelefono());
        Contacto foundContact = agenda.getContacts().stream()
                .filter(c -> c.getTelefono().equals("123456789"))
                .findFirst()
                .orElse(null);
        assertNotNull(foundContact);
        assertEquals("123456789", foundContact.getTelefono());
    }

    @Test
    public void testUpdateContact() {
        Contacto contact = new Contacto("John Doe", "123456789");
        agenda.agregarContacto(contact.getNombre(), contact.getTelefono());
        agenda.eliminarContacto(contact.getNombre());
        agenda.agregarContacto("John Doe", "987654321");
        Contacto updatedContact = agenda.getContacts().stream()
                .filter(c -> c.getNombre().equals("John Doe"))
                .findFirst()
                .orElse(null);
        assertNotNull(updatedContact);
        assertEquals("987654321", updatedContact.getTelefono());
    }
}
