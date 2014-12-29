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
@ManagedBean(name = "correoElectronicoValidator")
@RequestScoped
public class CorreoElectronicoValidator implements Validator{

    @EJB
    private UsuarioServicio usuarioServicio;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(this.usuarioServicio.verificarCorreoElectronico((String)value)){
            FacesMessage facesMessage = new FacesMessage("El correo electrónico ingresado ya existe!");
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMessage);
        }
    }
    
}
