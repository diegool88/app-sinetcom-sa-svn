/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.CompetenciasFacade;
import ec.com.sinetcom.dao.UsuarioFacade;
import ec.com.sinetcom.orm.Competencias;
import ec.com.sinetcom.orm.Usuario;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.Stateless;

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
    @EJB
    private CompetenciasFacade competenciasFacade;
    
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
    
    /**
     * Permite modificar un usuario
     * @param usuario 
     */
    public void modificarUsuario(Usuario usuario){
        this.usuarioFacade.edit(usuario);
    }
    
    /**
     * Permite obtener todos los clientes
     * @return 
     */
    public List<Usuario> obtenerTodosLosClientes(){
        return this.usuarioFacade.obtenerTodosLosClientes();
    }
    
    /**
     * Permite obtener una competencia
     * @param id
     * @return 
     */
    public Competencias obtenerCompetencia(int id){
        return this.competenciasFacade.find(id);
    }
    
    /**
     * Obtiene todas las competencias
     * @return 
     */
    public List<Competencias> obtenerTodasLasCompetencias(){
        return this.competenciasFacade.findAll();
    }
    
}
