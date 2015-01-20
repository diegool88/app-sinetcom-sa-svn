/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.converter;

import ec.com.sinetcom.servicios.ContratoServicio;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "provinciaConverter")
public class ProvinciaConverter implements Converter{

    @EJB
    private ContratoServicio contratoServicio;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.trim().length() > 0){
            return this.contratoServicio.recuperarProvincia(Integer.parseInt(value));
        }else{
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
}
