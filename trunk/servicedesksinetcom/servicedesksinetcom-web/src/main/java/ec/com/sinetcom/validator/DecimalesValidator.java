/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.validator;

import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.omnifaces.util.Messages;

/**
 *
 * @author diegoflores
 */
@FacesValidator("decimalesValidator")
public class DecimalesValidator implements Validator{
    private static final Pattern PATTERN = Pattern.compile("\\d+(\\.\\d{1,2})?");

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null){
            return;
        }
        
        if(!PATTERN.matcher(value.toString()).matches()){
            throw new ValidatorException(Messages.createError("Ingrese solo decimales!"));
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
