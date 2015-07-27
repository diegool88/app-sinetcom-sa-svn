/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.webutil;

import com.fasterxml.jackson.core.JsonParseException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 *
 * @author diegoflores
 */
public class Calendario {
    
    public static Date obtenerSiguienteDiaHabil(Date fecha) throws IOException, JsonParseException, ParseException{
        Calendar fechaIngresada = Calendar.getInstance();
        fechaIngresada.setTime(fecha);
        if(fechaIngresada.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || fechaIngresada.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || verificarDiaFestivo(fechaIngresada.getTime())){
            fechaIngresada.add(Calendar.DAY_OF_MONTH, 1);
            return obtenerSiguienteDiaHabil(fechaIngresada.getTime());
        }else{
            return fechaIngresada.getTime();
        }
    }
    
    public static boolean verificarDiaFestivo(Date fecha) throws IOException, JsonParseException, ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar fechaIngresada = Calendar.getInstance();
        fechaIngresada.setTime(fecha);
        String url = "http://holidayapi.com/v1/holidays?country=US&year=" + fechaIngresada.get(Calendar.YEAR);
        JSONObject result = JsonReader.readJsonFromUrl(url);
        for (Iterator iterator = result.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            try {
                if(fecha.compareTo(format.parse(key)) == 0) return true;
                //System.out.println(key);
                //System.out.println(result.get(key));
            } catch (java.text.ParseException ex) {
                Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
