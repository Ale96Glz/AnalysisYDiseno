package mx.uam.ayd.proyecto.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import mx.uam.ayd.proyecto.negocio.ServicioGrupo;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.datos.GrupoRepository;

@SpringBootTest
class GrupoIntegrationTest {

    @Autowired
    private ServicioGrupo servicioGrupo;

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private GrupoRepository grupoRepository;

    @Test
    @Transactional
    void testIntegracionCrearGrupo() {
        // Creamos un grupo
        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo de Prueba");
        
        // Guardamos el grupo
        Grupo grupoGuardado = grupoRepository.save(grupo);
        
        // Verificaciones
        assertNotNull(grupoGuardado);
        assertNotNull(grupoGuardado.getIdGrupo());
        assertEquals("Grupo de Prueba", grupoGuardado.getNombre());
        assertTrue(grupoGuardado.getUsuarios().isEmpty());
    }

    @Test
    @Transactional
    void testIntegracionRecuperarGrupos() {
        // Creamos dos grupos
        Grupo grupo1 = new Grupo();
        grupo1.setNombre("Grupo 1");
        grupoRepository.save(grupo1);

        Grupo grupo2 = new Grupo();
        grupo2.setNombre("Grupo 2");
        grupoRepository.save(grupo2);

        // Recuperamos todos los grupos
        var grupos = servicioGrupo.recuperaGrupos();
        
        // Verificamos que hay al menos dos grupos
        assertTrue(grupos.size() >= 2);
        
        // Verificamos que los grupos que agregamos están en la lista
        boolean encontroGrupo1 = false;
        boolean encontroGrupo2 = false;
        
        for(Grupo g : grupos) {
            if(g.getNombre().equals("Grupo 1")) {
                encontroGrupo1 = true;
            }
            if(g.getNombre().equals("Grupo 2")) {
                encontroGrupo2 = true;
            }
        }
        
        assertTrue(encontroGrupo1);
        assertTrue(encontroGrupo2);
    }

    @Test
    @Transactional
    void testIntegracionBuscarGrupoPorNombre() {
        // Creamos un grupo
        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo Especial");
        grupoRepository.save(grupo);

        // Buscamos el grupo por nombre
        Grupo grupoEncontrado = grupoRepository.findByNombre("Grupo Especial");
        
        // Verificaciones
        assertNotNull(grupoEncontrado);
        assertEquals("Grupo Especial", grupoEncontrado.getNombre());
    }

    @Test
    @Transactional
    void testIntegracionGrupoConUsuarios() {
        // Creamos un grupo
        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo Con Usuarios");
        grupo = grupoRepository.save(grupo);

        // Agregamos dos usuarios al grupo usando el servicio de usuarios
        servicioUsuario.agregaUsuario("Juan", "Pérez", "Grupo Con Usuarios");
        servicioUsuario.agregaUsuario("Ana", "García", "Grupo Con Usuarios");

        // Recuperamos el grupo actualizado
        Grupo grupoActualizado = grupoRepository.findByNombre("Grupo Con Usuarios");
        
        // Verificaciones
        assertNotNull(grupoActualizado);
        assertEquals(2, grupoActualizado.getUsuarios().size());
        
        // Verificamos que los usuarios están en el grupo
        boolean encontroJuan = false;
        boolean encontroAna = false;
        
        for(Usuario u : grupoActualizado.getUsuarios()) {
            if(u.getNombre().equals("Juan") && u.getApellido().equals("Pérez")) {
                encontroJuan = true;
            }
            if(u.getNombre().equals("Ana") && u.getApellido().equals("García")) {
                encontroAna = true;
            }
        }
        
        assertTrue(encontroJuan);
        assertTrue(encontroAna);
    }

    @Test
    @Transactional
    void testIntegracionBuscarGrupoInexistente() {
        // Intentamos buscar un grupo que no existe
        Grupo grupoInexistente = grupoRepository.findByNombre("Grupo Inexistente");
        
        // Verificamos que no se encontró el grupo
        assertNull(grupoInexistente);
    }
}
