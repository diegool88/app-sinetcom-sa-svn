/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.UsuarioFacade;
import ec.com.sinetcom.orm.Usuario;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.ejb.StatefulTimeout;

/**
 *
 * @author diegoflores
 */
@Stateful
@StatefulTimeout(unit = TimeUnit.MINUTES, value = 10)
@LocalBean
public class AutenticacionServicio {

    @EJB
    private UsuarioFacade usuarioFacade;
    
    private Usuario usuario;
    
    @PostConstruct
    public void inicializarServicio() {
        usuario = new Usuario();
    }
    
    /**
     * Permite validar la existencia del usuario
     * @param nombreUsuario
     * @param password
     * @return 
     */
    public Usuario validarUsuario(String nombreUsuario, String password){
        
        this.usuario = this.usuarioFacade.verificarExistenciaDeUsuario(nombreUsuario, password);
        if(this.usuario != null){
            return this.usuario;
        }else{
            return null;
        }

    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
