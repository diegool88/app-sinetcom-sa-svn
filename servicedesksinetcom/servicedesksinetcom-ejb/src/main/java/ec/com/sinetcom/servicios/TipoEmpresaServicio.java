/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.TipoEmpresaFacade;
import ec.com.sinetcom.orm.TipoEmpresa;
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
public class TipoEmpresaServicio {

    @EJB
    private TipoEmpresaFacade tipoEmpresaFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public List<TipoEmpresa> cargarTiposEmpresa() {
        return tipoEmpresaFacade.findAll();
    }

    public boolean crearTipoEmpresa(TipoEmpresa tipoEmpresa) {
        try {
            tipoEmpresaFacade.create(tipoEmpresa);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean eliminarTipoEmpresa(Integer tipoEmpresa) {
        try {
            TipoEmpresa aEliminar = tipoEmpresaFacade.find(tipoEmpresa);
            if (aEliminar != null) {
                tipoEmpresaFacade.remove(aEliminar);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
}
