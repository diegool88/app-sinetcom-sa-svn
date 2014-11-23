/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.CompetenciasFacade;
import ec.com.sinetcom.dao.GrupoFacade;
import ec.com.sinetcom.dao.PermisosFacade;
import ec.com.sinetcom.dao.UsuarioFacade;
import ec.com.sinetcom.orm.Competencias;
import ec.com.sinetcom.orm.Grupo;
import ec.com.sinetcom.orm.Permisos;
import ec.com.sinetcom.orm.Usuario;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
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
    @EJB
    private GrupoFacade grupoFacade;
    @EJB
    private PermisosFacade permisosFacade;
    
    
    /**
     * Servicio para bloquear/desbloquear Usuario Registrado
     * @param usuario
     * @param bloquear
     * @return 
     */
    
    public List<Usuario> cragarUsuarios() {
        return usuarioFacade.findAll();
    }
    
    public void crearUsuario(Usuario usuario) {
        usuarioFacade.create(usuario);
    }
    
    public void eliminarUsuario(Usuario usuario) {
        usuarioFacade.remove(usuario);
    }
    
    public List<Grupo> cragarGrupos() {
        return grupoFacade.findAll();
    }
    
    public Grupo recuperarGrupo(Integer id) {
        return grupoFacade.find(id);
    }
    
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
    
    /**
     * Obtiene todos los grupos
     * @return 
     */
    public List<Grupo> obtenerTodosLosGrupos(){
        return this.grupoFacade.findAll();
    }
    
    /**
     * Obtiene un grupo por id
     * @param id
     * @return 
     */
    public Grupo obtenerGrupoPorId(int id){
        return this.grupoFacade.find(id);
    }
    
    /**
     * Obtiene un Permiso
     * @param id
     * @return 
     */
    public Permisos obtenerPermisosPorId(int id){
        return this.permisosFacade.find(id);
    }
    
    /**
     * Actualiza el grupo seleccionado
     * @param grupo
     * @return 
     */
    public boolean actualizarGrupo(Grupo grupo){
        try{
            this.grupoFacade.edit(grupo);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Obtiene un usuario por Id
     * @param id
     * @return 
     */
    public Usuario obtenerUsuarioPorId(int id){
        return this.usuarioFacade.find(id);
    }
}
