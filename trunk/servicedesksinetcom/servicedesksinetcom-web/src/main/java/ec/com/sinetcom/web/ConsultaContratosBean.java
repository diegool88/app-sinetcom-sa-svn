/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.Curso;
import ec.com.sinetcom.orm.GarantiaEconomica;
import ec.com.sinetcom.orm.Pago;
import ec.com.sinetcom.orm.TipoDeVisita;
import ec.com.sinetcom.orm.TipoGarantia;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.orm.VisitaTecnica;
import ec.com.sinetcom.servicios.ContratoServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

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
    private List<TipoGarantia> tipoGarantias;
    private List<TipoDeVisita> tipoDeVisitas;
    private List<Usuario> tecnicos;
    private StreamedContent adendum;
    private StreamedContent contratoDigital;
    private Contrato contratoSeleccionado;
    private UploadedFile polizaACargar;
    private GarantiaEconomica nuevaGarantiaE;
    private Curso nuevaCapacitacion;
    private VisitaTecnica nuevaVisitaTecnica;
    private Usuario tecnicoSinetcom;
    
    
    @PostConstruct
    public void doInit(){
        this.contratos = this.contratoServicio.cargarContratos();
        this.accountManagers = this.contratoServicio.cargarUsuariosVentas();
        this.tipoGarantias = this.contratoServicio.cargarTipoDeGarantias();
        this.tipoDeVisitas = this.contratoServicio.cargarTipoDeVisita();
        this.tecnicos = this.contratoServicio.cargarUsuariosTecnicos();
        this.nuevaGarantiaE = new GarantiaEconomica();
        this.nuevaCapacitacion = new Curso();
        this.nuevaVisitaTecnica = new VisitaTecnica();
    }
    
    public String formatoCortoDeFecha(Date fecha) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(fecha);
    }
    
     public BigDecimal formatoDosDecimales(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.CEILING);
    }
    
    public void calcularValorDeGarantiaE() {
        if (this.nuevaGarantiaE != null && this.nuevaGarantiaE.getPorcentaje() != 0) {
            this.nuevaGarantiaE.setValor(new BigDecimal(this.contratoSeleccionado.getPrecioTotal().doubleValue() * ((double) this.nuevaGarantiaE.getPorcentaje() / 100.00)).setScale(2, RoundingMode.CEILING));
        }
    }
     
    public void agregarGarantia(ActionEvent event) {
        if(this.polizaACargar != null){
            this.nuevaGarantiaE.setAdendum(this.polizaACargar.getContents());
            this.polizaACargar = null;
        }
        this.nuevaGarantiaE.setContratonumero(this.contratoSeleccionado);
        this.contratoSeleccionado.getGarantiaEconomicaList().add(this.nuevaGarantiaE);
        this.nuevaGarantiaE = new GarantiaEconomica();
    }
    
    public void agregarVisitaT(ActionEvent event){
        this.nuevaVisitaTecnica.setContratonumero(this.contratoSeleccionado);
        this.contratoSeleccionado.getVisitaTecnicaList().add(this.nuevaVisitaTecnica);
        this.nuevaVisitaTecnica = new VisitaTecnica();
    }
    
    public void agregarCapacitacion(ActionEvent event){
        this.nuevaCapacitacion.setContratonumero(this.contratoSeleccionado);
        this.contratoSeleccionado.getCursoList().add(this.nuevaCapacitacion);
        this.nuevaCapacitacion = new Curso();
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
    
    public void actualizarNombreDeInstructor(){
        if(this.tecnicoSinetcom != null){
            this.nuevaCapacitacion.setNombreInstructor(this.tecnicoSinetcom.getNombreCompleto());
        }
    }
    
    public void limpiarNombreDeInstructor() {
        if(this.tecnicoSinetcom != null){
            this.tecnicoSinetcom = null;
            this.nuevaCapacitacion.setNombreInstructor("");
        }
    }
    
    public void cargarPolizaEnNuevaGarantiaEconomica(FileUploadEvent event){
        this.polizaACargar = event.getFile();
    }
    
    public void cargarPolizaEnGarantiaEconomica(RowEditEvent event){
        if(this.polizaACargar != null){
            GarantiaEconomica garantiaEconomica = ((GarantiaEconomica)event.getObject());
            garantiaEconomica.setAdendum(this.polizaACargar.getContents());
            this.polizaACargar = null;
        }
    }
    
    public void eliminarGarantia(ActionEvent event) {
        int index = (Integer) event.getComponent().getAttributes().get("garantiaIndex");
        this.contratoSeleccionado.getGarantiaEconomicaList().remove(index);
    }
    
    public void eliminarCapacitacion(ActionEvent event){
        int index = (Integer) event.getComponent().getAttributes().get("capacitacionIndex");
        this.contratoSeleccionado.getCursoList().remove(index);
    }
    
    public void eliminarVisitaT(ActionEvent event){
        int index = (Integer) event.getComponent().getAttributes().get("visitaTIndex");
        this.contratoSeleccionado.getVisitaTecnicaList().remove(index);
    }
    
    public void cargarPoliza(FileUploadEvent event){
        this.polizaACargar = event.getFile();
        Mensajes.mostrarMensajeInformativo("La póliza " + event.getFile().getFileName() + " se ha cargado con éxito!");
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

    public List<TipoGarantia> getTipoGarantias() {
        return tipoGarantias;
    }

    public void setTipoGarantias(List<TipoGarantia> tipoGarantias) {
        this.tipoGarantias = tipoGarantias;
    }

    public UploadedFile getPolizaACargar() {
        return polizaACargar;
    }

    public void setPolizaACargar(UploadedFile polizaACargar) {
        this.polizaACargar = polizaACargar;
    }

    public GarantiaEconomica getNuevaGarantiaE() {
        return nuevaGarantiaE;
    }

    public void setNuevaGarantiaE(GarantiaEconomica nuevaGarantiaE) {
        this.nuevaGarantiaE = nuevaGarantiaE;
    }

    public Curso getNuevaCapacitacion() {
        return nuevaCapacitacion;
    }

    public void setNuevaCapacitacion(Curso nuevaCapacitacion) {
        this.nuevaCapacitacion = nuevaCapacitacion;
    }

    public VisitaTecnica getNuevaVisitaTecnica() {
        return nuevaVisitaTecnica;
    }

    public void setNuevaVisitaTecnica(VisitaTecnica nuevaVisitaTecnica) {
        this.nuevaVisitaTecnica = nuevaVisitaTecnica;
    }

    public List<TipoDeVisita> getTipoDeVisitas() {
        return tipoDeVisitas;
    }

    public void setTipoDeVisitas(List<TipoDeVisita> tipoDeVisitas) {
        this.tipoDeVisitas = tipoDeVisitas;
    }

    public List<Usuario> getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(List<Usuario> tecnicos) {
        this.tecnicos = tecnicos;
    }

    public Usuario getTecnicoSinetcom() {
        return tecnicoSinetcom;
    }

    public void setTecnicoSinetcom(Usuario tecnicoSinetcom) {
        this.tecnicoSinetcom = tecnicoSinetcom;
    }
    
    
    
}
