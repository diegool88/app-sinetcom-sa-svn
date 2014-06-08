/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Sinetcom
 */
@ManagedBean(name = "language")
@SessionScoped
public class LanguageBean implements Serializable{

    private static final long serialVersionUID = 1L;
    private String localeCode;
    private static Map<String, Object> countries;
    private String country;
    private String language;
    
    static {
        countries = new LinkedHashMap<String, Object>();
        countries.put("English", Locale.ENGLISH); //label, value
        countries.put("French", Locale.FRENCH);
    }

    public Map<String, Object> getCountriesInMap() {
        return countries;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

//    public String getCountry() {
//        return country = Locale.forLanguageTag("es_EC").getCountry();
//    }
    
    public void setCountry(String country) {
        this.country = country;
    }

//    public String getLanguage() {
//        return language = Locale.forLanguageTag("es_EC").getLanguage();
//    }

    public void setLanguage(String language) {
        this.language = language;
    }

    //value change event listener
    public void countryLocaleCodeChanged(ValueChangeEvent e) {

        String newLocaleValue = e.getNewValue().toString();

        //loop country map to compare the locale code
        for (Map.Entry<String, Object> entry : countries.entrySet()) {

            if (entry.getValue().toString().equals(newLocaleValue)) {

                FacesContext.getCurrentInstance()
                        .getViewRoot().setLocale((Locale) entry.getValue());
                
            }
        }
    }
}
