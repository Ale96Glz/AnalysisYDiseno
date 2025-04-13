package mx.uam.ayd.proyecto.presentacion.listarUsuarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

class ControlListarUsuariosTest {

    @Mock
    private ServicioUsuario servicioUsuario;
    
    @Mock
    private VentanaListarUsuarios ventana;
    
    @InjectMocks
    private ControlListarUsuarios controlListarUsuarios;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testInicia() {
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuarios.add(usuario);
        
        when(servicioUsuario.recuperaUsuarios()).thenReturn(usuarios);
        
        controlListarUsuarios.inicia();
        
        verify(servicioUsuario).recuperaUsuarios();
        verify(ventana).muestra(controlListarUsuarios, usuarios);
    }
    
    @Test
    void testGetUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuarios.add(usuario);
        
        when(servicioUsuario.recuperaUsuarios()).thenReturn(usuarios);
        
        List<Usuario> result = controlListarUsuarios.getUsuarios();
        
        assertEquals(usuarios, result);
    }
}
