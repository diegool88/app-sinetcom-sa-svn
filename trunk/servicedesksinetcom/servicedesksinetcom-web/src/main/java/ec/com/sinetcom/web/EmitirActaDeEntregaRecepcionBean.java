/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.servicios.ProductoServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "emitirActaDeERBean")
@ViewScoped
public class EmitirActaDeEntregaRecepcionBean implements Serializable {

    @ManagedProperty(value = "#{reportesBean}")
    private ReportesBean reportesBean;
    @EJB
    private ProductoServicio productoServicio;
    //Se obtiene todos los contratos
    private List<Contrato> contratos;
    //Contratos Filtrados
    private List<Contrato> contratosFiltrados;
    //El contrato seleccionado
    private Contrato contratoSeleccionado;

    @PostConstruct
    public void doInit() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            this.contratos = this.productoServicio.obtenerTodosLosContratos();
        }
    }

    public String formatoCortoDeFecha(Date fecha) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(fecha);
    }

    public void seleccion(SelectEvent event) {
        this.contratoSeleccionado = (Contrato) event.getObject();
        Mensajes.mostrarMensajeInformativo("Contrato # " + this.contratoSeleccionado.getNumero());
    }

    public ReportesBean getReportesBean() {
        return reportesBean;
    }

    public void setReportesBean(ReportesBean reportesBean) {
        this.reportesBean = reportesBean;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public Contrato getContratoSeleccionado() {
        return contratoSeleccionado;
    }

    public void setContratoSeleccionado(Contrato contratoSeleccionado) {
        this.contratoSeleccionado = contratoSeleccionado;
    }

    public List<Contrato> getContratosFiltrados() {
        return contratosFiltrados;
    }

    public void setContratosFiltrados(List<Contrato> contratosFiltrados) {
        this.contratosFiltrados = contratosFiltrados;
    }

}
