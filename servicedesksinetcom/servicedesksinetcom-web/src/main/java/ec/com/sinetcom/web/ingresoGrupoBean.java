/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Grupo;
import ec.com.sinetcom.servicios.GrupoServicio;
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
@ManagedBean(name = "ingresoGrupoBean")
@ViewScoped
public class ingresoGrupoBean implements Serializable{
    
    @EJB
    private GrupoServicio grupoServicio;
    
    private String nombre;
    private String descripcion;
    private Grupo grupoSeleccionado;
    
    private List<Grupo> grupos;
    
    @PostConstruct
    public void init() {
        this.grupos = grupoServicio.cragarGrupos();
    }
    
    public void crearGrupo() {
        Grupo grupo = new Grupo();
        
        grupo.setNombre(nombre);
        grupo.setDescripcion(descripcion);
        
        grupoServicio.crearGrupo(grupo);
        this.grupos = grupoServicio.cragarGrupos();
    }
    
    public void eliminarGrupo() {
        grupoServicio.eliminarGrupo(grupoSeleccionado.getId());
        this.grupos = grupoServicio.cragarGrupos();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }                    

    public Grupo getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }
}
