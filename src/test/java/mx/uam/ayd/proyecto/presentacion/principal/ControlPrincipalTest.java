package mx.uam.ayd.proyecto.presentacion.principal;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mx.uam.ayd.proyecto.presentacion.agregarUsuario.ControlAgregarUsuario;
import mx.uam.ayd.proyecto.presentacion.listarUsuarios.ControlListarUsuarios;

class ControlPrincipalTest {
    
    @Mock
    private ControlAgregarUsuario controlAgregarUsuario;
    
    @Mock
    private ControlListarUsuarios controlListarUsuarios;
    
    @Mock
    private VentanaPrincipal ventana;
    
    @InjectMocks
    private ControlPrincipal controlPrincipal;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testInicia() {
        controlPrincipal.inicia();
        
        verify(ventana).muestra(controlPrincipal);
    }
    
    @Test
    void testAgregarUsuario() {
        controlPrincipal.agregarUsuario();
        
        verify(controlAgregarUsuario).inicia();
    }
    
    @Test
    void testListarUsuarios() {
        controlPrincipal.listarUsuarios();
        
        verify(controlListarUsuarios).inicia();
    }
}
