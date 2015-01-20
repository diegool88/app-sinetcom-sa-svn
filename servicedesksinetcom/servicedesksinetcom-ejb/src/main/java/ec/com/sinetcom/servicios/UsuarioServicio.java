/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.configuracion.UtilidadDeEncriptacion;
import ec.com.sinetcom.dao.CompetenciaFacade;
import ec.com.sinetcom.dao.GrupoFacade;
import ec.com.sinetcom.dao.PermisoFacade;
import ec.com.sinetcom.dao.UsuarioFacade;
import ec.com.sinetcom.orm.Competencia;
import ec.com.sinetcom.orm.Grupo;
import ec.com.sinetcom.orm.Permiso;
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
    private CompetenciaFacade competenciasFacade;
    @EJB
    private GrupoFacade grupoFacade;
    @EJB
    private PermisoFacade permisoFacade;

    
    public List<Usuario> cargarUsuarios() {
        return usuarioFacade.findAll();
    }

    public boolean crearUsuario(Usuario usuario) {
        try{
            usuarioFacade.create(usuario);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean actualizarUsuario(Usuario usuario){
        try{
            this.usuarioFacade.edit(usuario);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean eliminarUsuario(Usuario usuario) {
        try{
            if(usuarioFacade.tieneAsociacionConAlgunaEntidad(usuario.getId())){
                throw new Exception("El usuario no puede ser borrado, ya que tiene asociado varias entidades");
            }
            usuarioFacade.remove(usuario);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean actualizarPassword(Usuario usuario, String nuevoPassword){
        try{
            UtilidadDeEncriptacion utilidadDeEncriptacion = new UtilidadDeEncriptacion();
            usuario.setPassword(utilidadDeEncriptacion.encriptar(nuevoPassword));
            usuarioFacade.edit(usuario);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    

    public List<Grupo> cargarGrupos() {
        return grupoFacade.findAll();
    }

    public Grupo recuperarGrupo(Integer id) {
        return grupoFacade.find(id);
    }

    /**
     * Servicio para bloquear/desbloquear Usuario Registrado
     *
     * @param usuario
     * @param bloquear
     * @return
     */
    public boolean cambiarActividadDeUsuario(Usuario usuario, boolean bloquear) {

        if (bloquear) {
            usuario.setActivo(false);
        } else {
            usuario.setActivo(true);
        }

        try {
            this.usuarioFacade.edit(usuario);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Permite modificar un usuario
     *
     * @param usuario
     */
    public void modificarUsuario(Usuario usuario) {
        this.usuarioFacade.edit(usuario);
    }

    /**
     * Permite obtener todos los clientes
     *
     * @return
     */
    public List<Usuario> obtenerTodosLosClientes() {
        return this.usuarioFacade.obtenerTodosLosClientes();
    }

    /**
     * Permite obtener una competencia
     *
     * @param id
     * @return
     */
    public Competencia obtenerCompetencia(int id) {
        return this.competenciasFacade.find(id);
    }

    /**
     * Obtiene todas las competencias
     *
     * @return
     */
    public List<Competencia> obtenerTodasLasCompetencias() {
        return this.competenciasFacade.findAll();
    }

    /**
     * Obtiene todos los grupos
     *
     * @return
     */
    public List<Grupo> obtenerTodosLosGrupos() {
        return this.grupoFacade.findAll();
    }

    /**
     * Obtiene un grupo por id
     *
     * @param id
     * @return
     */
    public Grupo obtenerGrupoPorId(int id) {
        return this.grupoFacade.find(id);
    }

    /**
     * Obtiene un Permiso
     *
     * @param id
     * @return
     */
    public Permiso obtenerPermisosPorId(int id) {
        return this.permisoFacade.find(id);
    }

    /**
     * Actualiza el grupo seleccionado
     *
     * @param grupo
     * @return
     */
    public boolean actualizarGrupo(Grupo grupo) {
        try {
            this.grupoFacade.edit(grupo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Obtiene un usuario por Id
     *
     * @param id
     * @return
     */
    public Usuario obtenerUsuarioPorId(int id) {
        return this.usuarioFacade.find(id);
    }

    public void eliminarUsuario(Integer usuario) {
        try {
            Usuario aEliminar = usuarioFacade.find(usuario);
            if (aEliminar != null) {
                usuarioFacade.remove(aEliminar);
            }
        } catch (Exception e) {
        }

    }

    public boolean verificarCedulaCiudadania(String cedula) {
        return this.usuarioFacade.obtenerUsuarioPorCedulaCiudadania(cedula) != null;
    }

    public boolean verificarCorreoElectronico(String correoElectronico) {
        return this.usuarioFacade.obtenerUsuarioPorCorreoElectronico(correoElectronico) != null;
    }
}
