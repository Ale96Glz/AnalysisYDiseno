package mx.uam.ayd.proyecto.negocio.modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioTest {
    
    private Usuario usuario;
    private Usuario otroUsuario;
    
    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEdad(25);
        
        otroUsuario = new Usuario();
        otroUsuario.setIdUsuario(2L);
        otroUsuario.setNombre("María");
        otroUsuario.setApellido("González");
        otroUsuario.setEdad(30);
    }
    
    @Test
    void testGettersAndSetters() {
        assertEquals(1L, usuario.getIdUsuario());
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Pérez", usuario.getApellido());
        assertEquals(25, usuario.getEdad());
        
        usuario.setIdUsuario(3L);
        usuario.setNombre("Pedro");
        usuario.setApellido("Sánchez");
        usuario.setEdad(35);
        
        assertEquals(3L, usuario.getIdUsuario());
        assertEquals("Pedro", usuario.getNombre());
        assertEquals("Sánchez", usuario.getApellido());
        assertEquals(35, usuario.getEdad());
    }
    
    @Test
    void testEquals() {
        // Test reflexividad
        assertTrue(usuario.equals(usuario));
        
        // Test simetría
        Usuario usuarioMismoId = new Usuario();
        usuarioMismoId.setIdUsuario(1L);
        assertTrue(usuario.equals(usuarioMismoId));
        assertTrue(usuarioMismoId.equals(usuario));
        
        // Test con null
        assertFalse(usuario.equals(null));
        
        // Test con diferente clase
        assertFalse(usuario.equals("No soy un usuario"));
        
        // Test con diferente id
        assertFalse(usuario.equals(otroUsuario));
    }
    
    @Test
    void testHashCode() {
        // El mismo objeto debe tener el mismo hashCode
        assertEquals(usuario.hashCode(), usuario.hashCode());
        
        // Objetos con el mismo ID deben tener el mismo hashCode
        Usuario usuarioMismoId = new Usuario();
        usuarioMismoId.setIdUsuario(1L);
        assertEquals(usuario.hashCode(), usuarioMismoId.hashCode());
        
        // Objetos con diferente ID deben tener diferente hashCode
        assertNotEquals(usuario.hashCode(), otroUsuario.hashCode());
    }
    
    @Test
    void testToString() {
        String expectedString = "Usuario [idUsuario=1, nombre=Juan, apellido=Pérez, edad=25]";
        assertEquals(expectedString, usuario.toString());
    }
}
