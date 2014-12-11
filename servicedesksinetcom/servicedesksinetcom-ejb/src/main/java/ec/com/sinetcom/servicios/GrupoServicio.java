/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.GrupoFacade;
import ec.com.sinetcom.orm.Grupo;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Andres
 */
@Stateless
@LocalBean
public class GrupoServicio {
    
    @EJB
    private GrupoFacade grupoFacade;        

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public List<Grupo> cragarGrupos() {
        return grupoFacade.findAll();
    } 
    
    public void crearGrupo(Grupo grupo) {
        grupoFacade.create(grupo);
    }
    
     public void eliminarGrupo(Integer grupo) {
        try {
            Grupo aEliminar = grupoFacade.find(grupo);
            if (aEliminar != null) {
                grupoFacade.remove(aEliminar);
            }                         
        } catch (Exception e) {
        }
               
    }
}
