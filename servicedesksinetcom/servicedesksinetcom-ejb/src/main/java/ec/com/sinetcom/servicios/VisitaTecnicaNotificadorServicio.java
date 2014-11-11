/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.configuracion.TareaVisitaTecnicaInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.DuplicateKeyException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.ScheduleExpression;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author diegoflores
 */
@Stateless
@LocalBean
public class VisitaTecnicaNotificadorServicio {

    @Resource
    TimerService timerService;	//Resource Injection
    static Logger logger = Logger.getLogger("VisitaTecnicaNotificadorServicio");
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    /*
     * Callback method for the timers. Calls the corresponding Batch Job Session Bean based on the TareaVisitaTecnicaInfo
     * bounded to the timer
     */
    @Timeout
    public void timeout(Timer timer)
    {
        System.out.println("###Timer <" + timer.getInfo() + "> timeout at " + new Date());
        try
        {
            TareaVisitaTecnicaInfo tareaInfo = (TareaVisitaTecnicaInfo) timer.getInfo();
            InterfazLoteDeTareas loteTarea = (InterfazLoteDeTareas) InitialContext.doLookup(
                    tareaInfo.getJobClassName());
            loteTarea.ejecutarTarea(timer); //Asynchronous method
        }
        catch (NamingException ex)
        {
            logger.log(Level.SEVERE, null, ex);
        }
        catch (Exception ex1)
        {
            logger.severe("Exception caught: " + ex1);
        }
    }

    /*
     * Returns the Timer object based on the given TareaVisitaTecnicaInfo
     */
    private Timer getTimer(TareaVisitaTecnicaInfo tareaInfo)
    {
        Collection<Timer> timers = timerService.getTimers();
        for (Timer t : timers)
        {
            if (tareaInfo.equals((TareaVisitaTecnicaInfo) t.getInfo()))
            {
                return t;
            }
        }
        return null;
    }

    /*
     * Creates a timer based on the information in the TareaVisitaTecnicaInfo
     */
    public TareaVisitaTecnicaInfo createJob(TareaVisitaTecnicaInfo tareaInfo)
            throws Exception
    {
        //Check for duplicates
        if (getTimer(tareaInfo) != null)
        {
            throw new DuplicateKeyException("La tarea con ese ID ya existe!");
        }

        TimerConfig timerAConf = new TimerConfig(tareaInfo, true);
        
        ScheduleExpression schedExp = new ScheduleExpression();
        schedExp.start(tareaInfo.getStartDate());
        schedExp.end(tareaInfo.getEndDate());
        schedExp.second(tareaInfo.getSecond());
        schedExp.minute(tareaInfo.getMinute());
        schedExp.hour(tareaInfo.getHour());
        schedExp.dayOfMonth(tareaInfo.getDayOfMonth());
        schedExp.month(tareaInfo.getMonth());
        schedExp.year(tareaInfo.getYear());
        schedExp.dayOfWeek(tareaInfo.getDayOfWeek());
        logger.info("### Scheduler expr: " + schedExp.toString());

        Timer newTimer = timerService.createCalendarTimer(schedExp, timerAConf);
        logger.info("New timer created: " + newTimer.getInfo());
        tareaInfo.setNextTimeout(newTimer.getNextTimeout());

        return tareaInfo;
    }

    /*
     * Returns a list of TareaVisitaTecnicaInfo for the active timers
     */
    public List<TareaVisitaTecnicaInfo> getJobList()
    {
        logger.info("getJobList() called!!!");
        ArrayList<TareaVisitaTecnicaInfo> jobList = new ArrayList<TareaVisitaTecnicaInfo>();
        Collection<Timer> timers = timerService.getTimers();
        for (Timer t : timers)
        {
            TareaVisitaTecnicaInfo tareaInfo = (TareaVisitaTecnicaInfo) t.getInfo();
            tareaInfo.setNextTimeout(t.getNextTimeout());
            jobList.add(tareaInfo);
        }
        return jobList;
    }

    /*
     * Returns the updated TareaVisitaTecnicaInfo from the timer
     */
    public TareaVisitaTecnicaInfo getTareaInfo(TareaVisitaTecnicaInfo tareaInfo)
    {
        Timer t = getTimer(tareaInfo);
        if (t != null)
        {
            TareaVisitaTecnicaInfo j = (TareaVisitaTecnicaInfo) t.getInfo();
            j.setNextTimeout(t.getNextTimeout());
            return j;
        }
        return null;
    }

    /*
     * Updates a timer with the given TareaVisitaTecnicaInfo
     */
    public TareaVisitaTecnicaInfo updateJob(TareaVisitaTecnicaInfo tareaInfo)
            throws Exception
    {
        Timer t = getTimer(tareaInfo);
        if (t != null)
        {
            logger.info("Removing timer: " + t.getInfo());
            t.cancel();
            return createJob(tareaInfo);
        }
        return null;
    }

    /*
     * Remove a timer with the given TareaVisitaTecnicaInfo
     */
    public void deleteJob(TareaVisitaTecnicaInfo tareaInfo)
    {
        Timer t = getTimer(tareaInfo);
        if (t != null)
        {
            t.cancel();
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public Timer getTareaInfoById(String id){
        Collection<Timer> timers = timerService.getTimers();
        for (Timer t : timers)
        {
            if (((TareaVisitaTecnicaInfo)t.getInfo()).getJobId().equals(id))
            {
                return t;
            }
        }
        return null; 
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
