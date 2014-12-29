/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.configuracion.TareaCursoInfo;
import ec.com.sinetcom.configuracion.TareaVisitaTecnicaInfo;
import static ec.com.sinetcom.servicios.LoteTareaNotificarCurso.logger;
import java.util.Date;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Timer;

/**
 *
 * @author diegoflores
 */
@Stateless
public class LoteTareaNotificarVisitaTecnica implements InterfazLoteDeTareas{

    static Logger logger = Logger.getLogger("LoteTareaNotificarVisitaTecnica");
    
    @EJB
    private ContratoServicio contratoServicio;
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Asynchronous
    @Override
    public void ejecutarTarea(Timer timer) {
        logger.info("Start of LoteTareaNotificarVisitaTecnica at " + new Date() + "...");
        TareaVisitaTecnicaInfo jobInfo = (TareaVisitaTecnicaInfo) timer.getInfo();
        try
        {
            System.out.println("Disparador del Notificador Visita Técnica # " + jobInfo.getVisitaTecnica().getId());
            this.contratoServicio.enviarNotificacionDeVisitaTecnica(jobInfo.getVisitaTecnica());
            logger.info("Running job: " + jobInfo);
            Thread.sleep(30000); //Sleep for 30 seconds
        }
        catch (InterruptedException ex)
        {
        }
        logger.info("End of LoteTareaNotificarVisitaTecnica at " + new Date());
    }
}
