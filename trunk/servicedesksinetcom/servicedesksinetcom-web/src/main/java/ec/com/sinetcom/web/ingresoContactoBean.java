/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.Contacto;
import ec.com.sinetcom.servicios.ContactoServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Andres
 */
@ManagedBean(name = "ingresoContactoBean")
@ViewScoped
public class ingresoContactoBean implements Serializable {

    @EJB
    private ContactoServicio contactoServicio;

    private Contacto contactoSeleccionado;
    private boolean esNuevoContacto;

    private List<Contacto> contactos;
    private List<ClienteEmpresa> clientes;

    @PostConstruct
    public void init() {
        this.contactos = contactoServicio.cargarContactos();
        this.clientes = contactoServicio.cargarClientesEmpresas();
        this.esNuevoContacto = false;
    }

    public void definirContacto(ActionEvent event) {
        boolean esNuevo = Boolean.parseBoolean((String) event.getComponent().getAttributes().get("esNuevo"));
        if (esNuevo) {
            this.contactoSeleccionado = new Contacto();
            this.esNuevoContacto = true;
        } else {
            this.esNuevoContacto = false;
        }
    }

    public void crearContacto() {
        if (this.esNuevoContacto) {
            if (contactoServicio.crearContacto(contactoSeleccionado)) {
                Mensajes.mostrarMensajeInformativo("Contacto creado exitosamente!");
            } else {
                Mensajes.mostrarMensajeDeError("Error interno, Contacto no se pudo crear!");
            }
        } else {
            if (contactoServicio.editarContacto(contactoSeleccionado)) {
                Mensajes.mostrarMensajeInformativo("Contacto actualizado exitosamente!");
            } else {
                Mensajes.mostrarMensajeDeError("Error interno, Contacto no se pudo actualizar!");
            }
        }
        
        this.contactos = contactoServicio.cargarContactos();
    }

    public void eliminarContacto() {
        if (contactoServicio.eliminarTipoContrato(contactoSeleccionado.getId())) {
            Mensajes.mostrarMensajeInformativo("Contacto eliminado exitosamente!");
        } else {
            Mensajes.mostrarMensajeDeError("Error interno, Contacto no se pudo eliminar");
        }
        this.contactos = contactoServicio.cargarContactos();
    }
    
    @PermitAll
    public void validarActualizacionCedulaDeCiudadania(FacesContext context, UIComponent component, Object value){
        if(!this.contactoServicio.verificarActualizacionCedulaCiudadania(this.contactoSeleccionado, (String)value)){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cédula de Ciudadanía existente!"));
        }
    }
    
    @PermitAll
    public void validarActualizacionEmail(FacesContext context, UIComponent component, Object value){
        if(!this.contactoServicio.verificarActualizacionEmail(this.contactoSeleccionado, (String)value)){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Email ingresado existente!"));
        }
    }


    public Contacto getContactoSeleccionado() {
        return contactoSeleccionado;
    }

    public void setContactoSeleccionado(Contacto contactoSeleccionado) {
        this.contactoSeleccionado = contactoSeleccionado;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }

    public List<ClienteEmpresa> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteEmpresa> clientes) {
        this.clientes = clientes;
    }

    public boolean isEsNuevoContacto() {
        return esNuevoContacto;
    }

    public void setEsNuevoContacto(boolean esNuevoContacto) {
        this.esNuevoContacto = esNuevoContacto;
    }
    
}
