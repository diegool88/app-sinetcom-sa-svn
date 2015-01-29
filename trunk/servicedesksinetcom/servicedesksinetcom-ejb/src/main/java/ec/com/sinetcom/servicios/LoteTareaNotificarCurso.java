/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.configuracion.TareaCursoInfo;
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
public class LoteTareaNotificarCurso implements InterfazLoteDeTareas{

    static Logger logger = Logger.getLogger("LoteTareaNotificarCurso");
    
    @EJB
    private ContratoServicio contratoServicio;
    
    @Asynchronous
    @Override
    public void ejecutarTarea(Timer timer) {
        logger.info("Start of LoteTareaNotificarCurso at " + new Date() + "...");
        TareaCursoInfo jobInfo = (TareaCursoInfo) timer.getInfo();
        try
        {
            System.out.println("Disparador del Notificador Curso# " + jobInfo.getCurso().getCodigo());
            this.contratoServicio.enviarNotificacionDeCurso(jobInfo.getCurso());
            logger.info("Running job: " + jobInfo);
            Thread.sleep(30000); //Sleep for 30 seconds
        }
        catch (InterruptedException ex)
        {
        }
        logger.info("End of LoteTareaNotificarCurso at " + new Date());
    }
    
}
