/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author diegoflores
 */
public class EnviarNotificacionJob implements Job{

    public EnviarNotificacionJob(){
        //Solo para fines de inicializacion y paso de datos
    }
    
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        //throw new UnsupportedOperationException("Not supported yet.");
        JobDataMap data = jec.getMergedJobDataMap();
        
    }
    
}
