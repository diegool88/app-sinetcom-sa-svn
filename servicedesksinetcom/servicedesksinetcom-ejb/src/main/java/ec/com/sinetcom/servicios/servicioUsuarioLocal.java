/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.configuracion.ConfiguracionUsuario;
import ec.com.sinetcom.entidades.Usuario;
import javax.ejb.Local;

/**
 *
 * @author diegoflores
 */
@Local
public interface servicioUsuarioLocal {
    
    void configuracionActual(ConfiguracionUsuario configuracionUsuario);
    Usuario validarUsuario(String nombreUsuario, String password);
}
