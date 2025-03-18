package mx.uam.ayd.proyecto.presentacion.eliminarUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@Component
public class ControlEliminarUsuario {
    
    private static final Logger log = LoggerFactory.getLogger(ControlEliminarUsuario.class);
    
    @Autowired
    private ServicioUsuario servicioUsuario;

    /**
     * Elimina un usuario
     * 
     * @param usuario el usuario a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminaUsuario(Usuario usuario) {
        try {
            servicioUsuario.eliminaUsuario(usuario);
            return true;
        } catch (IllegalArgumentException e) {
            log.error("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
}
