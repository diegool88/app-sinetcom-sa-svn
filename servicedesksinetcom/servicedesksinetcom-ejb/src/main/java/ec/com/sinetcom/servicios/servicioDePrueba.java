/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import javax.ejb.Stateless;

/**
 *
 * @author Sinetcom
 */
@Stateless
public class servicioDePrueba implements servicioDePruebaLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public String imprimirHolaMundo(){
        return "Hola Mundo!!";
    }
}
