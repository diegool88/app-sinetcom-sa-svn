/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "welcomeBean")
public class WelcomeBean implements Serializable{
    private List<String> imagenes;
    
    @PostConstruct
    public void doInit(){
        imagenes = new ArrayList<String>();
        for(int i=0 ; i < 7 ; i++){
            imagenes.add("slide" + (i + 1) + ".png");
        }
    }

    public List<String> getImagenes() {
        return imagenes;
    }
}
