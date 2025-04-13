package mx.uam.ayd.proyecto.presentacion.agregarUsuario;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mx.uam.ayd.proyecto.negocio.ServicioGrupo;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

class ControlAgregarUsuarioTest {

    @Mock
    private ServicioUsuario servicioUsuario;
    
    @Mock
    private ServicioGrupo servicioGrupo;
    
    @Mock
    private VentanaAgregarUsuario ventana;
    
    @InjectMocks
    private ControlAgregarUsuario controlAgregarUsuario;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testInicia() {
        List<Grupo> grupos = new ArrayList<>();
        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo1");
        grupos.add(grupo);
        
        when(servicioGrupo.recuperaGrupos()).thenReturn(grupos);
        
        controlAgregarUsuario.inicia();
        
        verify(servicioGrupo).recuperaGrupos();
        verify(ventana).muestra(controlAgregarUsuario, grupos);
    }
    
    @Test
    void testAgregaUsuario_Exitoso() {
        controlAgregarUsuario.agregaUsuario("Juan", "Pérez", "Grupo1");
        
        verify(servicioUsuario).agregaUsuario("Juan", "Pérez", "Grupo1");
        verify(ventana).muestraDialogoConMensaje("Usuario agregado exitosamente");
        verify(ventana).setVisible(false);
    }
    
    @Test
    void testAgregaUsuario_Error() {
        String mensajeError = "Error de prueba";
        
        doThrow(new IllegalArgumentException(mensajeError))
            .when(servicioUsuario).agregaUsuario("Juan", "Pérez", "Grupo1");
        
        controlAgregarUsuario.agregaUsuario("Juan", "Pérez", "Grupo1");
        
        verify(servicioUsuario).agregaUsuario("Juan", "Pérez", "Grupo1");
        verify(ventana).muestraDialogoConMensaje("Error al agregar usuario: " + mensajeError);
        verify(ventana).setVisible(false);
    }
    
    @Test
    void testTermina() {
        controlAgregarUsuario.termina();
        
        verify(ventana).setVisible(false);
    }
}
