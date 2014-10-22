/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.servicios.ProductoServicio;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "emitirActaDeERBean")
@ViewScoped
public class EmitirActaDeEntregaRecepcionBean {
    
    @ManagedProperty(value = "#{reportesBean}")
    private ReportesBean reportesBean;
    @EJB
    private ProductoServicio productoServicio;
    //Se obtiene todos los contratos
    private List<Contrato> contratos;
    
    @PostConstruct
    public void doInit(){
        this.contratos = this.productoServicio.obtenerTodosLosContratos();
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

}
