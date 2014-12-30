/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.Contacto;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.Curso;
import ec.com.sinetcom.orm.GarantiaEconomica;
import ec.com.sinetcom.orm.Pago;
import ec.com.sinetcom.orm.Sla;
import ec.com.sinetcom.orm.TipoContrato;
import ec.com.sinetcom.orm.TipoDeVisita;
import ec.com.sinetcom.orm.TipoGarantia;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.orm.VisitaTecnica;
import ec.com.sinetcom.servicios.ContratoServicio;
import ec.com.sinetcom.webutil.Mensajes;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
    private String rucCliente;
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
    private Integer cantidadPagos;
    private Integer tipoGarantia;
    private Integer tipoDeVisita;
    private Integer cantidadDeVisitasPorA;
    private String descripcionVisitaT;
    private Integer instructorSinetcom;

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
    private List<TipoGarantia> tipoGarantias;
    private List<TipoDeVisita> tipoDeVisitas;
    private List<Pago> pagos;
    private List<GarantiaEconomica> garantiasE;
    private List<Usuario> tecnicos;
    private List<VisitaTecnica> visitasTecnicas;
    private List<Curso> cursos;
    private Curso nuevoCurso;
    private GarantiaEconomica nuevaGarantiaEconomica;
    //private VisitaTecnica nuevaVisitaTecnica;

    private List<Date> fechasPagos = new ArrayList<Date>();
    private UploadedFile adendum;

    @PostConstruct
    public void init() {

        this.contratos = contratoServicio.cargarContratos();
        this.tiposContrato = contratoServicio.cargarTiposContrato();
        this.clientesRuc = contratoServicio.cargarEmpresas();
        this.slas = contratoServicio.cargarSlas();
        this.Usuarios = contratoServicio.cargarUsuariosVentas();
        //this.Usuarios = contratoServicio.cargarUsuarios();
        //this.Contactos = contratoServicio.cargarContactos();
        this.tipoGarantias = contratoServicio.cargarTipoDeGarantias();
        this.tipoDeVisitas = contratoServicio.cargarTipoDeVisita();
        this.tecnicos = contratoServicio.cargarUsuariosTecnicos();
        this.nuevaGarantiaEconomica = new GarantiaEconomica();
        this.garantiasE = new ArrayList<GarantiaEconomica>();
        this.visitasTecnicas = new ArrayList<VisitaTecnica>();
        this.cursos = new ArrayList<Curso>();
        this.pagos = new ArrayList<Pago>();
        this.nuevoCurso = new Curso();
    }

    public void grabarContrato(ActionEvent event) {
        Contrato contrato = new Contrato();

        contrato.setNumero(numeroContrato);
        contrato.setTipoContratoid(contratoServicio.recuperarTipoContrato(tipoContrato));
        contrato.setClienteEmpresaruc(contratoServicio.recuperarRucEmpresa(rucCliente));
        contrato.setSlaid(contratoServicio.recuperarSla(numSla));
        contrato.setAccountManagerAsignado(contratoServicio.recuperarUsuario(numAccountManager));
        contrato.setAdministradorDeContrato(contratoServicio.recuperarContacto(numAdminContrato));
        contrato.setFechaDeSuscripcion(fechaSuscripcion);
        contrato.setObjeto(objeto);
        contrato.setPrecioTotal(precio);
        contrato.setTiempoDeValidez(tiempoValidez);
        contrato.setGarantiaTecnica(garantiatecnica);
        contrato.setFechaInicioGarantiaTecnica(fechaInicioGTecnica);
        contrato.setContratoDigital(contratoDigital.getContents());
        contrato.setServicioSoporteMantenimiento(srvSoportemantenimiento);
        contrato.setIncluyeRepuestos(repuestos);
        contrato.setHorasDeSoporteAnual(soporteAnual);
        contrato.setHorasDeSoporteUtilizadas(soporteutilizado);
        contrato.setFechaDeFacturacion(fechaFacturacion);
        contrato.setNumeroDeFactura(numeroFactura);
        contrato.setFechaDeEntregaRecepcion(fechaEntregaRecepcion);
        //Se agregan los pagos, garantias, visitas tecnicas y cursos
        if (!pagos.isEmpty()) {
            for (Pago pago : pagos) {
                pago.setContratonumero(contrato);
            }
            contrato.setPagoList(this.pagos);
        }
        if (!garantiasE.isEmpty()) {
            for (GarantiaEconomica garantiaEconomica : garantiasE) {
                garantiaEconomica.setContratonumero(contrato);
            }
            contrato.setGarantiaEconomicaList(this.garantiasE);
        }
        if (!cursos.isEmpty()) {
            for (Curso curso : cursos) {
                curso.setContratonumero(contrato);
            }
            contrato.setCursoList(this.cursos);
        }
        if (!visitasTecnicas.isEmpty()) {
            for (VisitaTecnica visitaTecnica : visitasTecnicas) {
                visitaTecnica.setContratonumero(contrato);
            }
            contrato.setVisitaTecnicaList(this.visitasTecnicas);
        }

        if (contratoServicio.crearContrato(contrato)) {
            Mensajes.mostrarMensajeInformativo("Contrato creado con éxito!");
        } else {
            Mensajes.mostrarMensajeDeError("Contrato no creado, error interno!");
        }

    }

    public void enFechaSeleccionada(SelectEvent evento) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fecha Seleccionada", format.format(evento.getObject())));
    }

    public void fechasPago() {

        RequestContext requestContext = RequestContext.getCurrentInstance();

        requestContext.execute("PF('fechas').show()");
    }

    public void calcularFechasPagos() {
        if (cantidadPagos != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(this.fechaSuscripcion);
            c.add(Calendar.YEAR, this.getTiempoValidez());
            int diasTotales = (int) ((c.getTime().getTime() - this.fechaSuscripcion.getTime()) / (1000 * 60 * 60 * 24));
            int diasEntre = (int) Math.round((double) diasTotales / (double) this.cantidadPagos);
            double monto = this.precio.doubleValue() / (double) this.cantidadPagos;
            this.pagos = new ArrayList<Pago>();

            c.setTime(this.fechaSuscripcion);
            for (int i = 0; i < this.cantidadPagos; i++) {
                if (i > 0) {
                    c.add(Calendar.DAY_OF_MONTH, diasEntre);
                }
                this.pagos.add(new Pago(null, i + 1, new BigDecimal(monto), c.getTime()));
            }
        }
    }

    public void calcularFechasVisitasTecnicas(ActionEvent event) {
        if (cantidadDeVisitasPorA != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(this.fechaSuscripcion);
            c.add(Calendar.YEAR, this.getTiempoValidez());
            int diasTotales = (int) ((c.getTime().getTime() - this.fechaSuscripcion.getTime()) / (1000 * 60 * 60 * 24));
            int diasEntre = (int) Math.round((double) diasTotales / (double) this.cantidadDeVisitasPorA);
            //Eliminamos los registros que sean del mismo tipo
            Iterator visitasT = this.visitasTecnicas.iterator();
            while (visitasT.hasNext()) {
                if (((VisitaTecnica) visitasT.next()).getTipoDeVisitaid().equals(this.contratoServicio.recuperarTipoDeVisita(this.tipoDeVisita))) {
                    visitasT.remove();
                }
            }

            c.setTime(this.fechaSuscripcion);
            for (int i = 0; i < this.cantidadDeVisitasPorA; i++) {
                if (i > 0) {
                    c.add(Calendar.DAY_OF_MONTH, diasEntre);
                }
                this.visitasTecnicas.add(new VisitaTecnica(c.getTime(), this.contratoServicio.recuperarTipoDeVisita(this.tipoDeVisita), this.descripcionVisitaT));
            }
        }
    }

    public void actualizarNombreDeInstructor() {
        if (this.instructorSinetcom != null) {
            this.nuevoCurso.setNombreInstructor(this.contratoServicio.recuperarUsuario(this.instructorSinetcom).getNombreCompleto());
        }
    }

    public void limpiarNombreDeInstructor() {
        if (this.instructorSinetcom != null) {
            this.instructorSinetcom = null;
            this.nuevoCurso.setNombreInstructor("");
        }
    }

    public void eliminarCurso(ActionEvent event) {
        int index = (Integer) event.getComponent().getAttributes().get("cursoIndex");
        this.cursos.remove(index);
    }

    public void limpiarVisitasTecnicas(ActionEvent event) {
        this.visitasTecnicas = new ArrayList<VisitaTecnica>();
    }

    public void eliminarVisitaTecnica(ActionEvent event) {
        int index = (Integer) event.getComponent().getAttributes().get("visitaIndex");
        this.visitasTecnicas.remove(index);
    }

    public void calcularValorDeGarantiaE() {
        if (this.nuevaGarantiaEconomica != null && this.nuevaGarantiaEconomica.getPorcentaje() != 0) {
            this.nuevaGarantiaEconomica.setValor(new BigDecimal(this.precio.doubleValue() * ((double) this.nuevaGarantiaEconomica.getPorcentaje() / 100.00)).setScale(2, RoundingMode.CEILING));
        }
    }

    public void cargarAdendum(FileUploadEvent event) {
        this.adendum = event.getFile();
        Mensajes.mostrarMensajeInformativo("El adendum " + event.getFile().getFileName() + " se ha cargado con éxito!");
    }

    public void eliminarGarantia(ActionEvent event) {
        int index = (Integer) event.getComponent().getAttributes().get("garantiaIndex");
        this.garantiasE.remove(index);
    }

    public void agregarGarantia(ActionEvent event) {
        this.nuevaGarantiaEconomica.setTipoGarantiaid(this.contratoServicio.recuperarTipoDeGarantia(this.tipoGarantia));
        if (this.adendum != null) {
            this.nuevaGarantiaEconomica.setAdendum(this.adendum.getContents());
        }
        this.garantiasE.add(nuevaInstanciaGarantiaEconomica(this.nuevaGarantiaEconomica));
        this.nuevaGarantiaEconomica = new GarantiaEconomica();
    }

    public GarantiaEconomica nuevaInstanciaGarantiaEconomica(GarantiaEconomica garantiaEconomica) {
        GarantiaEconomica garantiaE = new GarantiaEconomica();
        garantiaE.setFechaFin(garantiaEconomica.getFechaFin());
        garantiaE.setFechaInicio(garantiaEconomica.getFechaInicio());
        garantiaE.setPorcentaje(garantiaEconomica.getPorcentaje());
        garantiaE.setRenovable(garantiaEconomica.getRenovable());
        garantiaE.setTipoGarantiaid(garantiaEconomica.getTipoGarantiaid());
        garantiaE.setValor(garantiaEconomica.getValor());
        garantiaE.setAdendum(garantiaEconomica.getAdendum());
        return garantiaE;
    }

    public void agregarCurso(ActionEvent event) {
        if (this.instructorSinetcom != null) {
            this.nuevoCurso.setIntructorSinetcom(this.contratoServicio.recuperarUsuario(this.instructorSinetcom));
        }
        this.cursos.add(nuevaInstanciaCurso(this.nuevoCurso));
        this.nuevoCurso = new Curso();
    }

    public Curso nuevaInstanciaCurso(Curso curso) {
        Curso cursoP = new Curso();
        cursoP.setFechaDeInicio(curso.getFechaDeInicio());
        cursoP.setCentroDeCapacitacion(curso.getCentroDeCapacitacion());
        cursoP.setIntructorSinetcom(curso.getIntructorSinetcom());
        cursoP.setNombreInstructor(curso.getNombreInstructor());
        cursoP.setNumeroDeHorasTotales(curso.getNumeroDeHorasTotales());
        cursoP.setNumeroDeParticipantes(curso.getNumeroDeParticipantes());
        cursoP.setOficial(curso.getOficial());
        cursoP.setTemaATratar(curso.getTemaATratar());
        return cursoP;
    }

    public String formatoCortoDeFecha(Date fecha) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(fecha);
    }

    public void cargarContactosDeCliente() {
        if (this.rucCliente != null || this.rucCliente.length() > 0) {
            this.Contactos = this.contratoServicio.cargarTodosLosContactosDeCliente(this.contratoServicio.recuperarRucEmpresa(this.rucCliente));
        }
    }

    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }

    public byte[] convetirPDF(UploadedFile file) {
        FileInputStream fileInputStream = null;

        byte[] bFile = new byte[(int) file.toString().length()];

        try {
            fileInputStream = new FileInputStream(file.toString());
            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (IOException e) {
        }

        return bFile;
    }

    public void cargarContratoDigital(FileUploadEvent event) {
        this.contratoDigital = event.getFile();
        Mensajes.mostrarMensajeInformativo("El contrato " + event.getFile().getFileName() + " se ha cargado con éxito!");
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

    public String getRucCliente() {
        return rucCliente;
    }

    public void setRucCliente(String rucCliente) {
        this.rucCliente = rucCliente;
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

    public Integer getCantidadPagos() {
        return cantidadPagos;
    }

    public void setCantidadPagos(Integer cantidadPagos) {
        this.cantidadPagos = cantidadPagos;
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

    public List<Date> getFechasPagos() {
        return fechasPagos;
    }

    public void setFechasPagos(List<Date> fechasPagos) {
        this.fechasPagos = fechasPagos;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public GarantiaEconomica getNuevaGarantiaEconomica() {
        return nuevaGarantiaEconomica;
    }

    public void setNuevaGarantiaEconomica(GarantiaEconomica nuevaGarantiaEconomica) {
        this.nuevaGarantiaEconomica = nuevaGarantiaEconomica;
    }

    public Integer getTipoGarantia() {
        return tipoGarantia;
    }

    public void setTipoGarantia(Integer tipoGarantia) {
        this.tipoGarantia = tipoGarantia;
    }

    public List<TipoGarantia> getTipoGarantias() {
        return tipoGarantias;
    }

    public void setTipoGarantias(List<TipoGarantia> tipoGarantias) {
        this.tipoGarantias = tipoGarantias;
    }

    public List<GarantiaEconomica> getGarantiasE() {
        return garantiasE;
    }

    public void setGarantiasE(List<GarantiaEconomica> garantiasE) {
        this.garantiasE = garantiasE;
    }

    public Integer getTipoDeVisita() {
        return tipoDeVisita;
    }

    public void setTipoDeVisita(Integer tipoDeVisita) {
        this.tipoDeVisita = tipoDeVisita;
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

    public Integer getCantidadDeVisitasPorA() {
        return cantidadDeVisitasPorA;
    }

    public void setCantidadDeVisitasPorA(Integer cantidadDeVisitasPorA) {
        this.cantidadDeVisitasPorA = cantidadDeVisitasPorA;
    }

    public List<VisitaTecnica> getVisitasTecnicas() {
        return visitasTecnicas;
    }

    public void setVisitasTecnicas(List<VisitaTecnica> visitasTecnicas) {
        this.visitasTecnicas = visitasTecnicas;
    }

    public String getDescripcionVisitaT() {
        return descripcionVisitaT;
    }

    public void setDescripcionVisitaT(String descripcionVisitaT) {
        this.descripcionVisitaT = descripcionVisitaT;
    }

    public Integer getInstructorSinetcom() {
        return instructorSinetcom;
    }

    public void setInstructorSinetcom(Integer instructorSinetcom) {
        this.instructorSinetcom = instructorSinetcom;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public Curso getNuevoCurso() {
        return nuevoCurso;
    }

    public void setNuevoCurso(Curso nuevoCurso) {
        this.nuevoCurso = nuevoCurso;
    }

}
