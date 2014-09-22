/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.GrupoFacade;
import ec.com.sinetcom.dao.PermisosFacade;
import ec.com.sinetcom.orm.Grupo;
import ec.com.sinetcom.orm.Permisos;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author diegoflores
 */
@Stateless
@LocalBean
public class GrupoServicio {

    @EJB
    private GrupoFacade grupoFacade;
    @EJB
    private PermisosFacade permisosFacade;
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
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
