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
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;

/**
 *
 * @author diegoflores
 */
@Stateless
@LocalBean
public class UsuarioServicio {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private UsuarioFacade usuarioFacade;
    
    private Usuario usuario;
    
    
    
    /**
     * Servicio para bloquear/desbloquear Usuario Registrado
     * @param usuario
     * @param bloquear
     * @return 
     */
    public boolean cambiarActividadDeUsuario(Usuario usuario, boolean bloquear){
        
        if(bloquear){
            usuario.setActivo(false);
        }else{
            usuario.setActivo(true);
        }
        
        try{
            this.usuarioFacade.edit(usuario);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    
}
