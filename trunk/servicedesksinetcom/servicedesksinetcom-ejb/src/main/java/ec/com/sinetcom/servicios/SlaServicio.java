/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.SlaFacade;
import ec.com.sinetcom.dao.TipoDisponibilidadFacade;
import ec.com.sinetcom.orm.Sla;
import ec.com.sinetcom.orm.TipoDisponibilidad;
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
public class SlaServicio {
    
    @EJB
    private SlaFacade slaFacade;
    @EJB
    private TipoDisponibilidadFacade tipoDisponibilidadFacade;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public List<Sla> cragarSlas() {
        return slaFacade.findAll();
    }
    
    public void crearSLA(Sla sla) {
        slaFacade.create(sla);
    }
    
    public List<TipoDisponibilidad> cragarTiposDisponibilidad() {
        return tipoDisponibilidadFacade.findAll();
    }
    
    public TipoDisponibilidad recuperarTipoDisponibilidad(Integer id) {
        return tipoDisponibilidadFacade.find(id);
    }
    
     public void eliminarSLA(Integer sla) {
        try {
            Sla aEliminar = slaFacade.find(sla);
            if (aEliminar != null) {
                slaFacade.remove(aEliminar);
            }                         
        } catch (Exception e) {
        }
               
    }
}
