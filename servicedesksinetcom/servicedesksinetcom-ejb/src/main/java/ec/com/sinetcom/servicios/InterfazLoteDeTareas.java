/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.servicios;

import javax.ejb.Local;
import javax.ejb.Timer;

/**
 *
 * @author diegoflores
 */
@Local
public interface InterfazLoteDeTareas {
    public void ejecutarTarea(Timer timer);
}