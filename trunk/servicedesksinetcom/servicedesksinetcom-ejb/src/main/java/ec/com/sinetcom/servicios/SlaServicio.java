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
    public List<Sla> cargarSlas() {
        return slaFacade.findAll();
    }

    public boolean crearSLA(Sla sla) {
        try {
            slaFacade.create(sla);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<TipoDisponibilidad> cargarTiposDisponibilidad() {
        return tipoDisponibilidadFacade.findAll();
    }

    public TipoDisponibilidad recuperarTipoDisponibilidad(Integer id) {
        return tipoDisponibilidadFacade.find(id);
    }

    public boolean eliminarSLA(Integer sla) {
        try {
            Sla aEliminar = slaFacade.find(sla);
            if (aEliminar != null) {
                slaFacade.remove(aEliminar);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
