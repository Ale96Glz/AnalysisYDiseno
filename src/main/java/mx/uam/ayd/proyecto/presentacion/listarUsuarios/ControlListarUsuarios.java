package mx.uam.ayd.proyecto.presentacion.listarUsuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@Component
public class ControlListarUsuarios {
	
	private static final Logger log = LoggerFactory.getLogger(ControlListarUsuarios.class);
	
	@Autowired
	private ServicioUsuario servicioUsuario;
	
	@Autowired
	private VentanaListarUsuarios ventana;

	/**
	 * Inicia el caso de uso
	 */
	public void inicia() {
		List<Usuario> usuarios = servicioUsuario.recuperaUsuarios();
		
		for(Usuario usuario : usuarios) {
			log.info("usuario: " + usuario);
		}
		
		ventana.muestra(this, usuarios);
	}

	/**
	 * Elimina un usuario y actualiza la vista
	 * 
	 * @param usuario el usuario a eliminar
	 */
	public void eliminaUsuario(Usuario usuario) {
		try {
			servicioUsuario.eliminaUsuario(usuario);
			// Actualiza la vista con la lista actualizada
			inicia();
		} catch (IllegalArgumentException e) {
			log.error("Error al eliminar usuario: " + e.getMessage());
		}
	}

	/**
	 * Recupera la lista actualizada de usuarios
	 * 
	 * @return lista de usuarios
	 */
	public List<Usuario> getUsuarios() {
		return servicioUsuario.recuperaUsuarios();
	}
}
