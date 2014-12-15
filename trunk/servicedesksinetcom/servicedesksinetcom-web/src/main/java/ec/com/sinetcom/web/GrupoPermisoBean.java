/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Grupo;
import ec.com.sinetcom.orm.Permiso;
import ec.com.sinetcom.servicios.UsuarioServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "grupoPermisoBean")
@ViewScoped
public class GrupoPermisoBean implements Serializable {

    @EJB
    private UsuarioServicio usuarioServicio;
    //Todos los grupos
    private List<Grupo> grupos;
    //Grupo seleccionado
    private Grupo grupoSeleccionado;
    //Nuevo Permisos para Grupo
    private Permiso nuevoPermisos;
    //Seleccion
    private Boolean existeSeleccion;

    @PostConstruct
    public void doInit() {
        this.grupos = this.usuarioServicio.obtenerTodosLosGrupos();
        this.grupoSeleccionado = new Grupo();
        this.existeSeleccion = false;
    }

    public Permiso obtenerPermisosDeGrupo() {
        if (this.grupoSeleccionado != null && this.grupoSeleccionado.getPermiso() != null) {
            this.nuevoPermisos = this.grupoSeleccionado.getPermiso();
        } else {
            this.nuevoPermisos = new Permiso();
        }
        return this.nuevoPermisos;
    }

    public void registroSeleccionado(SelectEvent event) {
        this.grupoSeleccionado = (Grupo) event.getObject();
        if(this.grupoSeleccionado.getPermiso()==null){
            this.grupoSeleccionado.setPermiso(new Permiso());
            this.grupoSeleccionado.getPermiso().setGrupo(this.grupoSeleccionado);
            this.grupoSeleccionado.getPermiso().setGrupoid(this.grupoSeleccionado.getId());
        }
        this.existeSeleccion = Boolean.TRUE;
        //this.nuevoPermisos = this.grupoSeleccionado.getPermisos() != null ? this.grupoSeleccionado.getPermisos() : new Permisos();
    }

    public void registroDeSeleccionado(UnselectEvent event) {
        this.existeSeleccion = Boolean.FALSE;
    }

    public void actualizarGrupo(ActionEvent event) {
        this.usuarioServicio.actualizarGrupo(this.grupoSeleccionado);
        Mensajes.mostrarMensajeInformativo("Permisos Actualizados!");
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public Grupo getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    public Permiso getNuevoPermisos() {
        return nuevoPermisos;
    }

    public void setNuevoPermisos(Permiso nuevoPermisos) {
        this.nuevoPermisos = nuevoPermisos;
    }

    public Boolean getExisteSeleccion() {
        return existeSeleccion;
    }

    public void setExisteSeleccion(Boolean existeSeleccion) {
        this.existeSeleccion = existeSeleccion;
    }

    
}
