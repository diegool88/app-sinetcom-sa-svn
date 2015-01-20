/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.servicios.CargaDeDatosServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "subirDatosBDBean")
@ViewScoped 
public class SubirDatosBDBean implements Serializable {

    @EJB
    private CargaDeDatosServicio cargaDeDatosServicio;
    private String tablaSeleccionada;
    private UploadedFile archivoCargado;

    @PostConstruct
    public void doInit() {

    }

    public void cargar(ActionEvent event) {
        try {
            this.tablaSeleccionada = (String) event.getComponent().getAttributes().get("tabla");
            File archivo = new File("/temp", this.tablaSeleccionada + ".csv");
            FileOutputStream fos = new FileOutputStream(archivo);
            fos.write(this.archivoCargado.getContents());
            fos.close();
            if (this.tablaSeleccionada.equals("ClienteEmpresa")) {
                if(this.cargaDeDatosServicio.cargarDatosClienteEmpresa()){
                    Mensajes.mostrarMensajeInformativo("Datos Cliente Empresa Cargados con éxito!");
                }else{
                    Mensajes.mostrarMensajeDeError("Ningún dato cargado!");
                }
            }else if(this.tablaSeleccionada.equals("Contrato")){
                if(this.cargaDeDatosServicio.cargarDatosContrato()){
                    Mensajes.mostrarMensajeInformativo("Datos Contrato Cargados con éxito!");
                }else{
                    Mensajes.mostrarMensajeDeError("Ningún dato cargado!");
                }
            }else if(this.tablaSeleccionada.equals("ItemProducto")){
                if(this.cargaDeDatosServicio.cargarDatosItemProducto()){
                    Mensajes.mostrarMensajeInformativo("Datos Item Producto Cargados con éxito!");
                }else{
                    Mensajes.mostrarMensajeDeError("Ningún dato cargado!");
                }
            }
        } catch (FileNotFoundException e1) {
            Mensajes.mostrarMensajeDeError("No se encontró el archivo especificado!");
            e1.printStackTrace();
        } catch (IOException e2) {
            Mensajes.mostrarMensajeDeError("Error de lectura/escritura de datos!");
            e2.printStackTrace();
        } catch(Exception e){
            Mensajes.mostrarMensajeDeError(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void cargarArchivo(FileUploadEvent event){
        this.archivoCargado = event.getFile();
        Mensajes.mostrarMensajeInformativo("Archivo " + event.getFile().getFileName() + " cargado exitosamente");
    }

    public UploadedFile getArchivoCargado() {
        return archivoCargado;
    }

    public void setArchivoCargado(UploadedFile archivoCargado) {
        this.archivoCargado = archivoCargado;
    }

}
