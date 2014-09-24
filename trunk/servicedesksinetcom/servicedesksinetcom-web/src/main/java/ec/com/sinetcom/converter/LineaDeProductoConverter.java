/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.converter;

import ec.com.sinetcom.dao.ClienteEmpresaFacade;
import ec.com.sinetcom.servicios.ProductoServicio;
import ec.com.sinetcom.servicios.TicketServicio;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author diegoflores
 */
//@FacesConverter(value="clienteEmpresaConverter")
@ManagedBean(name="lineaProductoConverter")
public class LineaDeProductoConverter implements Converter{

    @EJB
    private ProductoServicio productoServicio;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.trim().length() > 0){
            return this.productoServicio.obtenerLineaDeProductoPorId(Integer.parseInt(value));
        }else{
            return null;
        }
        
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
    
}
