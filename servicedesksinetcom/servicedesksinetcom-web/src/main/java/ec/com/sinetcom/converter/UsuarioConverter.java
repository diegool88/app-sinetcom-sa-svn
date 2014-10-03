/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.converter;

import ec.com.sinetcom.servicios.ProductoServicio;
import ec.com.sinetcom.servicios.UsuarioServicio;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name="usuarioConverter")
public class UsuarioConverter implements Converter{

    @EJB
    private UsuarioServicio usuarioServicio;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.trim().length() > 0){
            return this.usuarioServicio.obtenerUsuarioPorId(Integer.parseInt(value));
        }else{
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
}
