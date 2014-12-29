/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.GarantiaEconomica;
import ec.com.sinetcom.orm.Pago;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.servicios.ContratoServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "consultaContratoBean")
@ViewScoped
public class ConsultaContratosBean implements Serializable{
    @EJB
    private ContratoServicio contratoServicio;
    
    private List<Contrato> contratos;
    private List<Usuario> accountManagers;
    private StreamedContent adendum;
    private StreamedContent contratoDigital;
    private Contrato contratoSeleccionado;
    
    
    @PostConstruct
    public void doInit(){
        this.contratos = this.contratoServicio.cargarContratos();
        this.accountManagers = this.contratoServicio.cargarUsuariosVentas();
    }
    
    public String formatoCortoDeFecha(Date fecha) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(fecha);
    }
    
    public void asignarContratoDescarga(ActionEvent event){
        String contratoNumero = (String) event.getComponent().getAttributes().get("contratoNumero");
        Contrato contrato = this.contratoServicio.recuperarContrato(contratoNumero);
        this.contratoDigital = new DefaultStreamedContent(new ByteArrayInputStream(contrato.getContratoDigital()),"application/pdf", "contrato_" + contrato.getNumero() + ".pdf");

    }
    
    public void asignarAdendumDescarga(ActionEvent event){
        Integer pagoId = (Integer) event.getComponent().getAttributes().get("garantiaId");
        GarantiaEconomica garantiaE = this.contratoServicio.recuperarGarantiaE(pagoId);
        this.adendum = new DefaultStreamedContent(new ByteArrayInputStream(garantiaE.getAdendum()),"application/pdf", "adendum_" + garantiaE.getId() + "_" + garantiaE.getContratonumero().getNumero() + ".pdf");
    }

    public void editarContrato(ActionEvent event){
        if(this.contratoServicio.editarContrato(this.contratoSeleccionado)){
            Mensajes.mostrarMensajeInformativo("Contrato " + this.contratoSeleccionado.getNumero() + " actualizado con éxito!");
        }else{
            Mensajes.mostrarMensajeDeError("Hubo un error interno al actualizar el contrato!");
        }
    }
    
    public void asignarContratoSeleccionado(SelectEvent event){
        this.contratoSeleccionado = (Contrato) event.getObject();
    }
    
    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public StreamedContent getAdendum() {
        return adendum;
    }

    public void setAdendum(StreamedContent adendum) {
        this.adendum = adendum;
    }

    public StreamedContent getContratoDigital() {
        return contratoDigital;
    }

    public void setContratoDigital(StreamedContent contratoDigital) {
        this.contratoDigital = contratoDigital;
    }

    public Contrato getContratoSeleccionado() {
        return contratoSeleccionado;
    }

    public void setContratoSeleccionado(Contrato contratoSeleccionado) {
        this.contratoSeleccionado = contratoSeleccionado;
    }

    public List<Usuario> getAccountManagers() {
        return accountManagers;
    }

    public void setAccountManagers(List<Usuario> accountManagers) {
        this.accountManagers = accountManagers;
    }
    
}
