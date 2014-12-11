/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Grupo;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.servicios.UsuarioServicio;
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
    
    private List<Usuario> usuarios;
    private List<Grupo> grupos;
        
    
    @PostConstruct
    public void init() {
        this.grupos = usuarioServicio.cragarGrupos();
        this.usuarios = usuarioServicio.cragarUsuarios();        
    }
    
    public void crearUsuario() {
        Usuario usuario = new Usuario();
        
        usuario.setGrupoid(usuarioServicio.recuperarGrupo(numGrupo));
        usuario.setCedulaDeCuidadania(numCedula);
        usuario.setCorreoElectronico(mail);
        usuario.setPassword(password);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setTelefonoMovil(celular);
        usuario.setTelefonoFijo(telefono);
        usuario.setTelefonoEmpresarial(telfoficina);
        usuario.setActivo(estado);

        usuarioServicio.crearUsuario(usuario);
        this.usuarios = usuarioServicio.cragarUsuarios();
    }
    
    public void eliminarUsuario() {
        usuarioServicio.eliminarUsuario(usuarioSeleccionado.getId());
        this.usuarios = usuarioServicio.cragarUsuarios();
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
}
