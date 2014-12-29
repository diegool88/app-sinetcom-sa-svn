/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.validator;

import ec.com.sinetcom.servicios.UsuarioServicio;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "cedulaCiudadaniaValidator")
@RequestScoped
public class CedulaCiudadaniaValidator implements Validator{

    @EJB
    private UsuarioServicio usuarioServicio;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(this.usuarioServicio.verificarCedulaCiudadania((String)value)){
            FacesMessage facesMessage = new FacesMessage("La cédula de ciudadanía ingresada ya existe!");
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMessage);
        }
    }
    
}
