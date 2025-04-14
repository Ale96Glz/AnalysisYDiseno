package mx.uam.ayd.proyecto.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@SpringBootTest
class UsuarioIntegrationTest {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private GrupoRepository grupoRepository;

    @BeforeEach
    void setUp() {
        // Creamos el grupo de prueba si no existe
        if(grupoRepository.findByNombre("Grupo de Prueba") == null) {
            Grupo grupo = new Grupo();
            grupo.setNombre("Grupo de Prueba");
            grupoRepository.save(grupo);
        }
    }

    @Test
    @Transactional
    void testIntegracionAgregarUsuarioAGrupo() {
        // Agregamos un usuario al grupo de prueba
        Usuario usuario = servicioUsuario.agregaUsuario("Juan", "Pérez", "Grupo de Prueba");
        
        // Validamos que se haya agregado correctamente
        assertNotNull(usuario);
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Pérez", usuario.getApellido());
    }

    @Test
    @Transactional
    void testIntegracionEliminarUsuario() {
        // Primero agregamos un usuario
        Usuario usuario = servicioUsuario.agregaUsuario("Ana", "García", "Grupo de Prueba");
        
        // Ahora lo eliminamos
        assertDoesNotThrow(() -> servicioUsuario.eliminaUsuario(usuario));
        
        // Verificamos que el usuario ya no existe en la lista
        var usuarios = servicioUsuario.recuperaUsuarios();
        boolean encontrado = false;
        
        for(Usuario u : usuarios) {
            if(u.getNombre().equals("Ana") && u.getApellido().equals("García")) {
                encontrado = true;
                break;
            }
        }
        
        assertFalse(encontrado);
    }

    @Test
    @Transactional
    void testIntegracionRecuperarUsuarios() {
        // Agregamos dos usuarios al grupo de prueba
        servicioUsuario.agregaUsuario("María", "López", "Grupo de Prueba");
        servicioUsuario.agregaUsuario("Pedro", "Sánchez", "Grupo de Prueba");
        
        // Recuperamos todos los usuarios
        var usuarios = servicioUsuario.recuperaUsuarios();
        
        // Verificamos que hay al menos dos usuarios
        assertTrue(usuarios.size() >= 2);
        
        // Verificamos que los usuarios que agregamos están en la lista
        boolean encontroMaria = false;
        boolean encontroPedro = false;
        
        for(Usuario u : usuarios) {
            if(u.getNombre().equals("María") && u.getApellido().equals("López")) {
                encontroMaria = true;
            }
            if(u.getNombre().equals("Pedro") && u.getApellido().equals("Sánchez")) {
                encontroPedro = true;
            }
        }
        
        assertTrue(encontroMaria);
        assertTrue(encontroPedro);
    }
}
