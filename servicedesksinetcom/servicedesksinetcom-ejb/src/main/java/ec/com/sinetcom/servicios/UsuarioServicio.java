/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.UsuarioFacade;
import ec.com.sinetcom.orm.Usuario;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

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
    
    /**
     * Servicio para bloquear/desbloquear Usuario Registrado
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
