/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;


import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.servicios.AutenticacionServicio;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name="login")
@SessionScoped
public class AdministracionUsuarioBean implements Serializable{
    
    @EJB
    private AutenticacionServicio autenticacionServicio;
    //private servicioUsuarioLocal servicioUsuario;
    
    private String nombreUsuario;
    private String password;
    private Usuario usuarioActual;
    
    //@EJB
    //private servicioUsuarioLocal servicioUsuario;
    
    
    @PostConstruct
    public void doInit(){
        //usuarioActual = new Usuario();
    }
    
    public String login(){
        this.usuarioActual = new Usuario();
        this.usuarioActual = this.autenticacionServicio.validarUsuario(nombreUsuario, password);
        
        if(this.usuarioActual == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error de Autenticacion","Usuario/Contraseña inválidos!"));
            return (this.nombreUsuario = this.password = null);
        } else {
            return "welcome?faces-redirect=true";
        }
    }
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }
    
    public boolean estaUsuarioAutenticado() {
        return this.usuarioActual != null;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}
