/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;

/**
 *
 * @author diegoflores
 */
@Singleton
@LocalBean
public class NotificadoresAutomaticos {

    @EJB
    private ContratoServicio contratoServicio;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    //Notificadores Automáticos para ser ejecutados una vez al día
    @Schedule(second = "0", minute = "0", hour = "8", dayOfWeek = "*", persistent = false)
    public void enviarNotificacionesDiariasDeContratos(){
        this.contratoServicio.verificarGarantiasEconomicasPorVencerEnProx2Semanas();
        this.contratoServicio.verificarPagosPorVencerEnProx2Semanas();
    }
}
