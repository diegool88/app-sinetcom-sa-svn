/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import java.util.logging.Logger;
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
    static Logger logger = Logger.getLogger("NotificadoresAutomaticos");
    
    //Notificadores Automáticos para ser ejecutados una vez al día
    @Schedule(second = "0", minute = "0", hour = "8", dayOfWeek = "*", persistent = false)
    public void enviarNotificacionesDiariasDeContratos(){
        logger.info("Se han invocado los notificadores diarios de contratos!!");
        this.contratoServicio.verificarGarantiasEconomicasPorVencerEnProx2Semanas();
        this.contratoServicio.verificarPagosPorVencerEnProx2Semanas();
        this.contratoServicio.verificarCursosADictarEnProx2Semanas();
        this.contratoServicio.verificarVisitasTecnicasEnProx2Semanas();
        logger.info("Fin de la ejecución!!");
    }
}
