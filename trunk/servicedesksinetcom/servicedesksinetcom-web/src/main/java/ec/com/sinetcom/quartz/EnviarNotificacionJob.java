/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.quartz;

import ec.com.sinetcom.configuracion.UtilidadDeEmail;
import ec.com.sinetcom.dao.TicketFacade;
import ec.com.sinetcom.orm.Ticket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author diegoflores
 */
@ManagedBean
public class EnviarNotificacionJob implements Job {

    @EJB
    private TicketFacade ticketFacade;
    
    public EnviarNotificacionJob() {
        //Solo para fines de inicializacion y paso de datos
    }

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        //throw new UnsupportedOperationException("Not supported yet.");
        JobDataMap data = jec.getMergedJobDataMap();
        String ticketNumber = (String) data.get("ticket");

        Ticket ticket = this.ticketFacade.find(Integer.parseInt(ticketNumber));

        if (ticket.getEstadoTicketcodigo().getCodigo() == 1
                || ticket.getEstadoTicketcodigo().getCodigo() == 2) {
            UtilidadDeEmail utilidadDeEmail = new UtilidadDeEmail();
            utilidadDeEmail.enviarMensajeConAdjunto("soporte@sinetcom.com.ec", ticket.getUsuarioidpropietario().getCorreoElectronico(), "Recordatorio Atención Ticket# " + ticket.getTicketNumber(), crearMensajeRecordatorio(ticket), null, null, null);
            System.out.println("Se ha enviado un correo de recordatorio del ticket#" + ticketNumber);
        }
    }

    public String crearMensajeRecordatorio(Ticket ticket) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Este es un recordatorio de que se debe actualizar el ticket# ").append(ticket.getTicketNumber()).append(".\n");
        mensaje.append("Le recordamos que la fecha límite de actualización es: ").append(ticket.getFechaDeProximaActualizacion()).append(".\n");
        mensaje.append("Saludos").append(".\n");
        return mensaje.toString();
    }
}
