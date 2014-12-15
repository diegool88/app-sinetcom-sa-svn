/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.Contacto;
import ec.com.sinetcom.servicios.ContactoServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Andres
 */
@ManagedBean(name = "ingresoContactoBean")
@ViewScoped
public class ingresoContactoBean implements Serializable{
    
    @EJB
    private ContactoServicio contactoServicio;
    
    private String ruc;
    private String cedula;
    private String nombre;
    private String Cargo;
    private String celular;
    private String telefono;
    private String mail;
    private Contacto contactoSeleccionado;
    
    private List<Contacto> contactos;
    private List<ClienteEmpresa> clientes;
    
    @PostConstruct
    public void init() {
        this.contactos = contactoServicio.cragarContactos();
        this.clientes = contactoServicio.cargarClientesEmpresas();
    }
    
    public void crearContacto() {
        Contacto contacto = new Contacto();
        
        contacto.setClienteEmpresaruc(contactoServicio.recuperarClienteEmpresa(ruc));
        contacto.setCedulaDeCuidadania(cedula);
        contacto.setNombre(nombre);
        contacto.setCargo(Cargo);
        contacto.setTelefonoMovil(celular);
        contacto.setTelefonoFijo(telefono);
        contacto.setCorreoElectronico(mail);
        
        contactoServicio.crearContacto(contacto);
        this.contactos = contactoServicio.cragarContactos();
    }
    
    public void eliminarContacto() {
        contactoServicio.eliminarTipoContrato(contactoSeleccionado.getId());
        this.contactos = contactoServicio.cragarContactos();
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String Cargo) {
        this.Cargo = Cargo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
}
