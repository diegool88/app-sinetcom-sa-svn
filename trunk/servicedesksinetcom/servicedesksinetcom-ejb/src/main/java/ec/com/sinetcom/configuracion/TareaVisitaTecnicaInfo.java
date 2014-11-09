/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.configuracion;

import ec.com.sinetcom.orm.VisitasTecnicas;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author diegoflores
 */
public class TareaVisitaTecnicaInfo {
    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    private String jobId;
    private String jobName;
    private String jobClassName;
    private String description;
    //Details required by the SchedulerExpression
    private Date startDate;
    private Date endDate;
    private String second;
    private String minute;
    private String hour;
    private String dayOfWeek;
    private String dayOfMonth;
    private String month;
    private String year;
    
    private Date nextTimeout;
    private VisitasTecnicas visitaTecnica;
    
    
    public TareaVisitaTecnicaInfo()
    {
        this("<Job ID>", "<Job Name>", "java:global/servicedesksinetcom-ear/servicedesksinetcom-ejb-1/", null);
    }

    public TareaVisitaTecnicaInfo(String jobId, String jobName, String jobClassName, VisitasTecnicas visitaTecnica)
    {
        this.jobId = jobId;
        this.jobName = jobName;
        this.jobClassName = "java:global/servicedesksinetcom-ear/servicedesksinetcom-ejb-1/" + jobClassName;
        this.description = "";
        //Default values, everyday midnight
        this.startDate = new Date();
        this.endDate = null;
        this.second = "0";
        this.minute = "0";
        this.hour = "0";
        this.dayOfMonth = "*";  //Every Day
        this.month = "*";       //Every Month
        this.year = "*";        //Every Year
        this.dayOfWeek = "*";   //Every Day of Week (Sun-Sat)
        this.visitaTecnica = visitaTecnica;
    }

    //Getter and Setter methods for the above attributes...
    
    /*
     * Expression of the schedule set in the object
     */
    public String getExpression()
    {
        return "sec=" + second + ";min=" + minute + ";hour=" + hour
                + ";dayOfMonth=" + dayOfMonth + ";month=" + month + ";year=" + year
                + ";dayOfWeek=" + dayOfWeek;
    }

    @Override
    public boolean equals(Object anotherObj)
    {
        if (anotherObj instanceof TareaVisitaTecnicaInfo)
        {
            return jobId.equals(((TareaVisitaTecnicaInfo) anotherObj).jobId);
        }
        return false;
    }

    @Override
    public String toString()
    {
        return jobId + "-" + jobName + "-" + jobClassName;
    }

    public static SimpleDateFormat getSdf() {
        return sdf;
    }

    public static void setSdf(SimpleDateFormat sdf) {
        TareaVisitaTecnicaInfo.sdf = sdf;
    }

    public static SimpleDateFormat getSdf2() {
        return sdf2;
    }

    public static void setSdf2(SimpleDateFormat sdf2) {
        TareaVisitaTecnicaInfo.sdf2 = sdf2;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = "java:global/servicedesksinetcom-ear/servicedesksinetcom-ejb-1/" + jobClassName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Date getNextTimeout() {
        return nextTimeout;
    }

    public void setNextTimeout(Date nextTimeout) {
        this.nextTimeout = nextTimeout;
    }

    public VisitasTecnicas getVisitaTecnica() {
        return visitaTecnica;
    }

    public void setVisitaTecnica(VisitasTecnicas visitaTecnica) {
        this.visitaTecnica = visitaTecnica;
    }
    
}
