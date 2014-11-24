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
    
    public List<TipoDisponibilidad> ccargarTiposDisponibilidad() {
        return tipoDisponibilidadFacade.findAll();
    }
    
    public void crearTipoDisponibilidad(TipoDisponibilidad tipoDisponibilidad ) {
        tipoDisponibilidadFacade.create(tipoDisponibilidad);
    }
}
