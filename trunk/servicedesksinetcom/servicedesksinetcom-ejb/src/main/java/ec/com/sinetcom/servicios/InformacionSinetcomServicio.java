/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.DatosSinetcomFacade;
import ec.com.sinetcom.orm.DatosSinetcom;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author diegoflores
 */
@Stateless
@LocalBean
public class InformacionSinetcomServicio {

    @EJB
    private DatosSinetcomFacade datosSinetcomFacade;
    
    public boolean actualizarInformacionDeSinetcom(DatosSinetcom datosSinetcom){
        try{
            this.datosSinetcomFacade.edit(datosSinetcom);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public DatosSinetcom cargarInformacionDeSinetcom(){
        return this.datosSinetcomFacade.find("1791839692001");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
