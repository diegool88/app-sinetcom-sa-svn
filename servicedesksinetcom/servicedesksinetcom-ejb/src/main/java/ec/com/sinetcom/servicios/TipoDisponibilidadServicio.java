/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.TipoDisponibilidadFacade;
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
public class TipoDisponibilidadServicio {

    @EJB
    private TipoDisponibilidadFacade tipoDisponibilidadFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public List<TipoDisponibilidad> cargarTiposDisponibilidad() {
        return tipoDisponibilidadFacade.findAll();
    }

    public boolean crearTipoDisponibilidad(TipoDisponibilidad tipoDisponibilidad) {
        try {
            tipoDisponibilidadFacade.create(tipoDisponibilidad);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean eliminarTipoDisp(Integer tipoDisp) {
        try {
            TipoDisponibilidad aEliminar = tipoDisponibilidadFacade.find(tipoDisp);
            if (aEliminar != null) {
                tipoDisponibilidadFacade.remove(aEliminar);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
