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
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Andres
 */
@ManagedBean(name = "ingresoUsuarioBean")
@ViewScoped
public class ingresoUsuarioBean implements Serializable {

    @EJB
    private UsuarioServicio usuarioServicio;

    private Integer numGrupo;
    private String numCedula;
    private String mail;
    private String password; 
    private String nombre;
    private String apellido;
    private String celular;
    private String telefono;
    private String telfoficina;
    private Boolean estado;
    private Usuario usuarioSeleccionado;
    private boolean esNuevoUsuario;

    private List<Usuario> usuarios;
    private List<Grupo> grupos;

    @PostConstruct
    public void init() {
        this.grupos = usuarioServicio.cargarGrupos();
        this.usuarios = usuarioServicio.cargarUsuarios();
        this.esNuevoUsuario = false;
    }

    public void definirUsuario(ActionEvent event) {
        boolean esNuevo = Boolean.parseBoolean((String) event.getComponent().getAttributes().get("esNuevo"));
        if (esNuevo) {
            this.usuarioSeleccionado = new Usuario();
            this.esNuevoUsuario = true;
            this.password = null;
        } else {
            this.esNuevoUsuario = false;
        }
    }

    public void guardarUsuario() {
        if (this.esNuevoUsuario) {
            UtilidadDeEncriptacion utilidadDeEncriptacion = new UtilidadDeEncriptacion();
            usuarioSeleccionado.setPassword(utilidadDeEncriptacion.encriptar(password));
            if (usuarioServicio.crearUsuario(usuarioSeleccionado)) {
                Mensajes.mostrarMensajeInformativo("Usuario creado exitosamente!");
            } else {
                Mensajes.mostrarMensajeDeError("Error interno, Usuario no se pudo crear!");
            }
        } else {
            if (usuarioServicio.actualizarUsuario(usuarioSeleccionado)) {
                Mensajes.mostrarMensajeInformativo("Usuario actualizado exitosamente!");
            } else {
                Mensajes.mostrarMensajeDeError("Error interno, Usuario no se pudo actualizar!");
            }
        }
        this.usuarios = usuarioServicio.cargarUsuarios();
        this.usuarioSeleccionado = null;
    }

    public void eliminarUsuario() {
        if (usuarioServicio.eliminarUsuario(usuarioSeleccionado)) {
            Mensajes.mostrarMensajeInformativo("Usuario eliminado exitosamente!");
        } else {
            Mensajes.mostrarMensajeDeError("Error interno, Usuario no se pudo eliminar!");
        }
        this.usuarios = usuarioServicio.cargarUsuarios();
        this.usuarioSeleccionado = null;
    }
    
    public void actualizarPassword(ActionEvent event){
        if(this.usuarioServicio.actualizarPassword(usuarioSeleccionado, password)){
            Mensajes.mostrarMensajeInformativo("Password actualizado con éxito!");
        }else{
            Mensajes.mostrarMensajeDeError("Error interno, no se pudo actualizar el password");
        }
        this.usuarios = usuarioServicio.cargarUsuarios();
        this.usuarioSeleccionado = null;
    }
    
    @PermitAll
    public void validarActualizacionCedulaDeCiudadania(FacesContext context, UIComponent component, Object value){
        if(!this.usuarioServicio.verificarActualizacionCedulaCiudadania(this.usuarioSeleccionado, (String)value)){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cédula de Ciudadanía existente!"));
        }
    }
    
    @PermitAll
    public void validarActualizacionEmail(FacesContext context, UIComponent component, Object value){
        if(!this.usuarioServicio.verificarActualizacionEmail(this.usuarioSeleccionado, (String)value)){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Email ingresado existente!"));
        }
    }
    
    public void limpiarCampos(){
        this.usuarioSeleccionado = null;
    }

    public Integer getNumGrupo() {
        return numGrupo;
    }

    public void setNumGrupo(Integer numGrupo) {
        this.numGrupo = numGrupo;
    }

    public String getNumCedula() {
        return numCedula;
    }

    public void setNumCedula(String numCedula) {
        this.numCedula = numCedula;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public String getTelfoficina() {
        return telfoficina;
    }

    public void setTelfoficina(String telfoficina) {
        this.telfoficina = telfoficina;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public boolean isEsNuevoUsuario() {
        return esNuevoUsuario;
    }

    public void setEsNuevoUsuario(boolean esNuevoUsuario) {
        this.esNuevoUsuario = esNuevoUsuario;
    }
}
