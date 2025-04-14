package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@Service
public class ServicioUsuario {
	
	// Define a static logger field
	private static final Logger log = LoggerFactory.getLogger(ServicioUsuario.class);
	
	@Autowired 
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	/**
	 * 
	 * Permite agregar un usuario
	 * 
	 * @param nombre nombre del usuario
	 * @param apellido apellido del usuario
	 * @param grupo nombre grupo al que debe pertencer
	 * @return el usuario que se agregó
	 * @throws IllegalArgumentException si el usuario ya 
	 * existe o no existe el grupo
	 * 
	 */
	public Usuario agregaUsuario(String nombre, String apellido, String nombreGrupo) {
		
		// Regla de negocio: No se permite agregar dos usuarios con el mismo nombre y apellido
		
		
		Usuario usuario = usuarioRepository.findByNombreAndApellido(nombre, apellido);
		
		if(usuario != null) {
			throw new IllegalArgumentException("Ese usuario ya existe");
		}
		
		Grupo grupo = grupoRepository.findByNombre(nombreGrupo);
		
		if(grupo == null) {
			throw new IllegalArgumentException("No se encontró el grupo");
		}
		
		// Se validaron correctamente las reglas de negocio
		
		log.info("Agregando usuario nombre: "+nombre+" apellido:"+apellido);
		
		// Crea el usuario
		
		usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		
		usuario = usuarioRepository.save(usuario); // Esto es el create
		
		// Conecta al grupo con el usuario
		
		grupo.addUsuario(usuario);
		
		grupoRepository.save(grupo); // Esto es el update
		
		return usuario;
	}

	/**
	 * Recupera todos los usuarios existentes
	 * 
	 * @return Una lista con los usuarios (o lista vacía)
	 */
	public List <Usuario> recuperaUsuarios() {

		
		System.out.println("usuarioRepository = "+usuarioRepository);
		
		List <Usuario> usuarios = new ArrayList<>();
		
		for(Usuario usuario:usuarioRepository.findAll()) {
			usuarios.add(usuario);
		}
				
		return usuarios;
	}

	/**
	 * Elimina un usuario existente
	 * 
	 * @param usuario el usuario a eliminar
	 * @throws IllegalArgumentException si el usuario no existe
	 */
	public void eliminaUsuario(Usuario usuario) {
		if (usuario == null || !usuarioRepository.existsById(usuario.getIdUsuario())) {
			throw new IllegalArgumentException("El usuario no existe");
		}

		// Primero encontrar y remover el usuario de su grupo
		for (Grupo grupo : grupoRepository.findAll()) {
			if (grupo.getUsuarios().contains(usuario)) {
				grupo.removeUsuario(usuario);
				grupoRepository.save(grupo);
				break;
			}
		}

		// Ahora sí podemos eliminar el usuario
		usuarioRepository.delete(usuario);
	}
}
