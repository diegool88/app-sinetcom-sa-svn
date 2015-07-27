/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.configuracion.UtilidadDeEncriptacion;
import ec.com.sinetcom.orm.Grupo;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.servicios.UsuarioServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Andres
 */
@ManagedBean(name = "edicionDatosPersonalesBean")
@ViewScoped
public class EdicionDatosPersonalesBean implements Serializable {

    @EJB
    private UsuarioServicio usuarioServicio;
    
    @ManagedProperty(value = "#{login}")
    private AdministracionUsuarioBean administracionUsuarioBean;
    
    public void setAdministracionUsuarioBean(AdministracionUsuarioBean administracionUsuarioBean) {
        this.administracionUsuarioBean = administracionUsuarioBean;
    }
    
    private Usuario usuario;
    private String password;

    @PostConstruct
    public void init() {
        this.usuario = this.administracionUsuarioBean.getUsuarioActual();
    }

    public void guardarUsuario() {
        if (usuarioServicio.actualizarUsuario(usuario)) {
            Mensajes.mostrarMensajeInformativo("Usuario actualizado exitosamente!");
        } else {
            Mensajes.mostrarMensajeDeError("Error interno, Usuario no se pudo actualizar!");
        }
    }

    public void actualizarPassword(ActionEvent event) {
        if (this.usuarioServicio.actualizarPassword(usuario, password)) {
            Mensajes.mostrarMensajeInformativo("Password actualizado con éxito!");
        } else {
            Mensajes.mostrarMensajeDeError("Error interno, no se pudo actualizar el password");
        }
    }

    @PermitAll
    public void validarActualizacionCedulaDeCiudadania(FacesContext context, UIComponent component, Object value) {
        if (!this.usuarioServicio.verificarActualizacionCedulaCiudadania(this.usuario, (String) value)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cédula de Ciudadanía existente!"));
        }
    }

    @PermitAll
    public void validarActualizacionEmail(FacesContext context, UIComponent component, Object value) {
        if (!this.usuarioServicio.verificarActualizacionEmail(this.usuario, (String) value)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Email ingresado existente!"));
        }
    }
    
    @PermitAll
    public void validarAntiguoPassword(FacesContext context, UIComponent component, Object value) {
        UtilidadDeEncriptacion utilidadDeEncriptacion = new UtilidadDeEncriptacion();
        if(!((String)value).equals(utilidadDeEncriptacion.desencriptar(this.usuario.getPassword()))){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El antiguo password no coincide!"));
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
