/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.configuracion.TareaTicketInfo;
import static ec.com.sinetcom.servicios.LoteTareaNotificarSLA.logger;
import java.util.Date;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timer;

/**
 *
 * @author diegoflores
 */
@Stateless
public class LoteTareaNotificarTiempoDeActualizacion implements InterfazLoteDeTareas{

    static Logger logger = Logger.getLogger("LoteTareaNotificarSLA");
    
    @EJB
    private TicketServicio ticketServicio;
    
    @Asynchronous
    @Override
    public void ejecutarTarea(Timer timer) {
        logger.info("Start of LoteTareaNotificarTiempoDeActualizacion at " + new Date() + "...");
        TareaTicketInfo jobInfo = (TareaTicketInfo) timer.getInfo();
        try
        {
            System.out.println("Disparador de tiempo de actualización del ticket# " + jobInfo.getTicket().getTicketNumber());
            this.ticketServicio.enviarNotificacionDeActualizacionDeTicket(jobInfo.getTicket());
            logger.info("Running job: " + jobInfo);
            Thread.sleep(30000); //Sleep for 30 seconds
        }
        catch (InterruptedException ex)
        {
        }
        logger.info("End of LoteTareaNotificadorSLA at " + new Date());
    }
    
}
