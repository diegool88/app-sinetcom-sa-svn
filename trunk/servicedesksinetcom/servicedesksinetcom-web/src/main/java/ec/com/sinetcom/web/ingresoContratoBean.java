/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.Contacto;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.Sla;
import ec.com.sinetcom.orm.TipoContrato;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.servicios.ContratoServicio;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Andres
 */
@ManagedBean(name = "ingresoContratoBean")
@ViewScoped
public class ingresoContratoBean implements Serializable {
    
    @EJB
    private ContratoServicio contratoServicio;
    
    private String numeroContrato;
    private Integer tipoContrato;
    private String rucClinete;
    private Integer numSla;
    private Integer numAccountManager;
    private Integer numAdminContrato;
    private String objeto;
    private BigDecimal precio;
    private Integer tiempoValidez;
    private Integer garantiatecnica;
    private UploadedFile contratoDigital;    
    private Integer srvSoportemantenimiento;
    private Boolean repuestos;
    private Integer soporteAnual;
    private Integer soporteutilizado;
    private String numeroFactura;
    
    private Date fechaSuscripcion;
    private Date fechaInicioGTecnica;
    private Date fechaFacturacion;
    private Date fechaEntregaRecepcion;
    
    private List<Contrato> contratos;
    private List<TipoContrato> tiposContrato;
    private List<ClienteEmpresa> clientesRuc;
    private List<Sla> slas;
    private List<Usuario> Usuarios;
    private List<Contacto> Contactos;
    
    @PostConstruct
    public void init() {
        
        this.contratos = contratoServicio.cargarContratos();
        this.tiposContrato = contratoServicio.cargarTiposContrato();
        this.clientesRuc = contratoServicio.cargarEmpresas();
        this.slas = contratoServicio.cargarSlas();
        this.Usuarios = contratoServicio.cargarUsuarios();
        this.Contactos = contratoServicio.cargarContactos();
    }    
    
    public void grabarContrato() {
        Contrato contrato = new Contrato();
        
        contrato.setNumero(numeroContrato);
        contrato.setTipoContratoid(contratoServicio.recuperarTipoContrato(tipoContrato));
        contrato.setClienteEmpresaruc(contratoServicio.recuperarRucEmpresa(rucClinete));
        contrato.setSlaid(contratoServicio.recuperarSla(numSla));
        contrato.setAccountManagerAsignado(contratoServicio.recuperarUsuario(numAccountManager));
        contrato.setAdministradorDeContrato(contratoServicio.recuperarContacto(numAdminContrato));
        contrato.setFechaDeSuscripcion(fechaSuscripcion);
        contrato.setObjeto(objeto);
        contrato.setPrecioTotal(precio);
        contrato.setTiempoDeValidez(tiempoValidez);
        contrato.setGarantiaTecnica(garantiatecnica);
        contrato.setFechaInicioGarantiaTecnica(fechaInicioGTecnica);
        contrato.setContratoDigital(convetirPDF(contratoDigital));
        contrato.setServicioSoporteMantenimiento(srvSoportemantenimiento);
        contrato.setIncluyeRepuestos(repuestos);
        contrato.setHorasDeSoporteAnual(soporteAnual);
        contrato.setHorasDeSoporteUtilizadas(soporteutilizado);
        contrato.setFechaDeFacturacion(fechaFacturacion);
        contrato.setNumeroDeFactura(numeroFactura);
        contrato.setFechaDeEntregaRecepcion(fechaEntregaRecepcion);
        
        contratoServicio.crearContrato(contrato);                
    }
    
    public void enFechaSeleccionada(SelectEvent evento) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Fecha Seleccionada", format.format(evento.getObject())));
    }
    
    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        
        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }
    
    public byte[] convetirPDF(UploadedFile file ) {
        FileInputStream fileInputStream = null;
        
        byte[] bFile = new byte[(int) file.toString().length()];
        
        try {
            fileInputStream = new FileInputStream(file.toString());
            fileInputStream.read(bFile);
            fileInputStream.close();                               
        } catch(IOException e) {
        }
        
        return bFile;
    }
    
     public void handleFileUpload(FileUploadEvent event) {        
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);        
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public Integer getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(Integer tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getRucClinete() {
        return rucClinete;
    }

    public void setRucClinete(String rucClinete) {
        this.rucClinete = rucClinete;
    }

    public Integer getNumSla() {
        return numSla;
    }

    public void setNumSla(Integer numSla) {
        this.numSla = numSla;
    }

    public Integer getNumAccountManager() {
        return numAccountManager;
    }

    public void setNumAccountManager(Integer numAccountManager) {
        this.numAccountManager = numAccountManager;
    }

    public Integer getNumAdminContrato() {
        return numAdminContrato;
    }

    public void setNumAdminContrato(Integer numAdminContrato) {
        this.numAdminContrato = numAdminContrato;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getTiempoValidez() {
        return tiempoValidez;
    }

    public void setTiempoValidez(Integer tiempoValidez) {
        this.tiempoValidez = tiempoValidez;
    }

    public Integer getGarantiatecnica() {
        return garantiatecnica;
    }

    public void setGarantiatecnica(Integer garantiatecnica) {
        this.garantiatecnica = garantiatecnica;
    }

    public UploadedFile getContratoDigital() {
        return contratoDigital;
    }

    public void setContratoDigital(UploadedFile contratoDigital) {
        this.contratoDigital = contratoDigital;
    }   

    public Integer getSrvSoportemantenimiento() {
        return srvSoportemantenimiento;
    }

    public void setSrvSoportemantenimiento(Integer srvSoportemantenimiento) {
        this.srvSoportemantenimiento = srvSoportemantenimiento;
    }

    public Boolean getRepuestos() {
        return repuestos;
    }

    public void setRepuestos(Boolean repuestos) {
        this.repuestos = repuestos;
    }

    public Integer getSoporteAnual() {
        return soporteAnual;
    }

    public void setSoporteAnual(Integer soporteAnual) {
        this.soporteAnual = soporteAnual;
    }

    public Integer getSoporteutilizado() {
        return soporteutilizado;
    }

    public void setSoporteutilizado(Integer soporteutilizado) {
        this.soporteutilizado = soporteutilizado;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }          

    public Date getFechaSuscripcion() {
        return fechaSuscripcion;
    }

    public void setFechaSuscripcion(Date fechaSuscripcion) {
        this.fechaSuscripcion = fechaSuscripcion;
    }

    public Date getFechaInicioGTecnica() {
        return fechaInicioGTecnica;
    }

    public void setFechaInicioGTecnica(Date fechaInicioGTecnica) {
        this.fechaInicioGTecnica = fechaInicioGTecnica;
    }

    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public Date getFechaEntregaRecepcion() {
        return fechaEntregaRecepcion;
    }

    public void setFechaEntregaRecepcion(Date fechaEntregaRecepcion) {
        this.fechaEntregaRecepcion = fechaEntregaRecepcion;
    }   

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }
    
    public List<TipoContrato> getTiposContrato() {
        return tiposContrato;
    }

    public void setTiposContrato(List<TipoContrato> tiposContrato) {
        this.tiposContrato = tiposContrato;
    }        

    public List<ClienteEmpresa> getClientesRuc() {
        return clientesRuc;
    }

    public void setClientesRuc(List<ClienteEmpresa> clientesRuc) {
        this.clientesRuc = clientesRuc;
    }    

    public List<Sla> getSlas() {
        return slas;
    }

    public void setSlas(List<Sla> slas) {
        this.slas = slas;
    }        

    public List<Usuario> getUsuarios() {
        return Usuarios;
    }

    public void setUsuarios(List<Usuario> Usuarios) {
        this.Usuarios = Usuarios;
    }

    public List<Contacto> getContactos() {
        return Contactos;
    }

    public void setContactos(List<Contacto> Contactos) {
        this.Contactos = Contactos;
    }        
}
