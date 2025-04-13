package mx.uam.ayd.proyecto.negocio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

@ExtendWith(MockitoExtension.class)
class ServicioUsuarioTest {
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private GrupoRepository grupoRepository;
	
	@InjectMocks
	private ServicioUsuario servicioUsuario;

	@Test
	void testRecuperaUsuarios() {

		List <Usuario> usuarios = servicioUsuario.recuperaUsuarios();
		
		assertEquals(0,usuarios.size());
		
		
		ArrayList <Usuario> lista = new ArrayList <> ();

		Usuario usuario1 = new Usuario();
		usuario1.setNombre("Juan");
		usuario1.setApellido("Perez");

		Usuario usuario2 = new Usuario();
		usuario2.setNombre("María");
		usuario2.setApellido("Ramírez");
		
		lista.add(usuario1);
		lista.add(usuario2);

		Iterable <Usuario> listaIterable = lista;
		
		when(usuarioRepository.findAll()).thenReturn(listaIterable);
		
		usuarios = servicioUsuario.recuperaUsuarios();
		
		assertEquals(2,usuarios.size());
	}

	@Test
	void testEliminaUsuario_UsuarioExistente() {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(1L);
		usuario.setNombre("Juan");
		usuario.setApellido("Pérez");

		when(usuarioRepository.existsById(1L)).thenReturn(true);

		assertDoesNotThrow(() -> servicioUsuario.eliminaUsuario(usuario));
	}

	@Test
	void testEliminaUsuario_UsuarioNoExistente() {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(1L);
		usuario.setNombre("Juan");
		usuario.setApellido("Pérez");

		when(usuarioRepository.existsById(1L)).thenReturn(false);

		assertThrows(IllegalArgumentException.class, () -> servicioUsuario.eliminaUsuario(usuario));
	}

	@Test
	void testEliminaUsuario_UsuarioNull() {
		assertThrows(IllegalArgumentException.class, () -> servicioUsuario.eliminaUsuario(null));
	}

	@Test
	void testAgregaUsuario_Exitoso() {
		String nombre = "Juan";
		String apellido = "Pérez";
		String nombreGrupo = "Grupo1";
		
		when(usuarioRepository.findByNombreAndApellido(nombre, apellido)).thenReturn(null);
		
		Grupo grupo = new Grupo();
		grupo.setNombre(nombreGrupo);
		when(grupoRepository.findByNombre(nombreGrupo)).thenReturn(grupo);
		
		Usuario resultado = servicioUsuario.agregaUsuario(nombre, apellido, nombreGrupo);
		
		assertNotNull(resultado);
		assertEquals(nombre, resultado.getNombre());
		assertEquals(apellido, resultado.getApellido());
		verify(grupoRepository).save(any(Grupo.class));
	}
	
	@Test
	void testAgregaUsuario_UsuarioYaExiste() {
		String nombre = "Juan";
		String apellido = "Pérez";
		String nombreGrupo = "Grupo1";
		
		Usuario usuarioExistente = new Usuario();
		usuarioExistente.setNombre(nombre);
		usuarioExistente.setApellido(apellido);
		
		when(usuarioRepository.findByNombreAndApellido(nombre, apellido)).thenReturn(usuarioExistente);
		
		assertThrows(IllegalArgumentException.class, 
			() -> servicioUsuario.agregaUsuario(nombre, apellido, nombreGrupo));
	}
	
	@Test
	void testAgregaUsuario_GrupoNoExiste() {
		String nombre = "Juan";
		String apellido = "Pérez";
		String nombreGrupo = "GrupoInexistente";
		
		when(usuarioRepository.findByNombreAndApellido(nombre, apellido)).thenReturn(null);
		
		when(grupoRepository.findByNombre(nombreGrupo)).thenReturn(null);
		
		assertThrows(IllegalArgumentException.class, 
			() -> servicioUsuario.agregaUsuario(nombre, apellido, nombreGrupo));
	}
}
