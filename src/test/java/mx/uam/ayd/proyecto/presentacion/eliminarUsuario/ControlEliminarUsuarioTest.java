package mx.uam.ayd.proyecto.presentacion.eliminarUsuario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

class ControlEliminarUsuarioTest {

    @Mock
    private ServicioUsuario servicioUsuario;

    @InjectMocks
    private ControlEliminarUsuario controlEliminarUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEliminaUsuario_Exitoso() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        
        doNothing().when(servicioUsuario).eliminaUsuario(usuario);

        boolean resultado = controlEliminarUsuario.eliminaUsuario(usuario);

        assertTrue(resultado);
        verify(servicioUsuario).eliminaUsuario(usuario);
    }

    @Test
    void testEliminaUsuario_Error() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        
        doThrow(new IllegalArgumentException("El usuario no existe"))
            .when(servicioUsuario).eliminaUsuario(usuario);

        boolean resultado = controlEliminarUsuario.eliminaUsuario(usuario);

        assertFalse(resultado);
        verify(servicioUsuario).eliminaUsuario(usuario);
    }
}
