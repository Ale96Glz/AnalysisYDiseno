package mx.uam.ayd.proyecto.negocio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;

@ExtendWith(MockitoExtension.class)
class ServicioGrupoTest {
	
	@Mock
	private GrupoRepository grupoRepository;
	
	@InjectMocks
	private ServicioGrupo servicioGrupo;

	@Test
	void testRecuperaGrupos_ListaVacia() {
		when(grupoRepository.findAll()).thenReturn(new ArrayList<>());
		
		List<Grupo> grupos = servicioGrupo.recuperaGrupos();
		
		assertNotNull(grupos);
		assertTrue(grupos.isEmpty());
	}
	
	@Test
	void testRecuperaGrupos_ConGrupos() {
		List<Grupo> grupos = new ArrayList<>();
		Grupo grupo = new Grupo();
		grupo.setNombre("Grupo1");
		grupos.add(grupo);
		
		when(grupoRepository.findAll()).thenReturn(grupos);
		
		List<Grupo> result = servicioGrupo.recuperaGrupos();
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Grupo1", result.get(0).getNombre());
	}
}
