/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.TipoContratoFacade;
import ec.com.sinetcom.orm.TipoContrato;
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
public class TipoContratoServicio {

    @EJB
    private TipoContratoFacade tipoContratoFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public List<TipoContrato> cargarTiposContactos() {
        return tipoContratoFacade.findAll();
    }

    public boolean crearTipoContrato(TipoContrato tipoContrato) {
        try {
            tipoContratoFacade.create(tipoContrato);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean eliminarTipoContrato(Integer tipoContrato) {
        try {
            TipoContrato aEliminar = tipoContratoFacade.find(tipoContrato);
            if (aEliminar != null) {
                tipoContratoFacade.remove(aEliminar);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
}
