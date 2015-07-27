/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
import ec.com.sinetcom.configuracion.TareaCursoInfo;
import ec.com.sinetcom.configuracion.TareaVisitaTecnicaInfo;
import ec.com.sinetcom.configuracion.UtilidadDeEmail;
import ec.com.sinetcom.dao.CiudadFacade;
import ec.com.sinetcom.dao.ClienteDireccionFacade;
import ec.com.sinetcom.dao.ClienteEmpresaFacade;
import ec.com.sinetcom.dao.ContactoFacade;
import ec.com.sinetcom.dao.ContratoFacade;
import ec.com.sinetcom.dao.CursoFacade;
import ec.com.sinetcom.dao.DatosSinetcomFacade;
import ec.com.sinetcom.dao.GarantiaEconomicaFacade;
import ec.com.sinetcom.dao.PagoFacade;
import ec.com.sinetcom.dao.ProvinciaFacade;
import ec.com.sinetcom.dao.SlaFacade;
import ec.com.sinetcom.dao.TipoContratoFacade;
import ec.com.sinetcom.dao.TipoDeVisitaFacade;
import ec.com.sinetcom.dao.TipoDisponibilidadFacade;
import ec.com.sinetcom.dao.TipoGarantiaFacade;
import ec.com.sinetcom.dao.UsuarioFacade;
import ec.com.sinetcom.dao.VisitaTecnicaFacade;
import ec.com.sinetcom.orm.Ciudad;
import ec.com.sinetcom.orm.ClienteDireccion;
import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.Contacto;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.Curso;
import ec.com.sinetcom.orm.DatosSinetcom;
import ec.com.sinetcom.orm.GarantiaEconomica;
import ec.com.sinetcom.orm.Pago;
import ec.com.sinetcom.orm.Provincia;
import ec.com.sinetcom.orm.Sla;
import ec.com.sinetcom.orm.Ticket;
import ec.com.sinetcom.orm.TipoContrato;
import ec.com.sinetcom.orm.TipoDeVisita;
import ec.com.sinetcom.orm.TipoDisponibilidad;
import ec.com.sinetcom.orm.TipoGarantia;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.orm.VisitaTecnica;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.internet.MailDateFormat;

/**
 *
 * @author diegoflores
 */
@Stateless
@LocalBean
public class ContratoServicio {

    @EJB
    private ContratoFacade contratoFacade;
    @EJB
    private TipoContratoFacade tipoContratoFacade;
    @EJB
    private ClienteEmpresaFacade clienteEmpresaFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private ContactoFacade contactoFacade;
    @EJB
    private SlaFacade slaFacade;
    @EJB
    private TipoDisponibilidadFacade tipoDisponibilidadFacade;
    @EJB
    private GarantiaEconomicaFacade garantiaEconomicaFacade;
    @EJB
    private PagoFacade pagoFacade;
    @EJB
    private TipoGarantiaFacade tipoGarantiaFacade;
    @EJB
    private TipoDeVisitaFacade tipoDeVisitaFacade;
    @EJB
    private ClienteDireccionFacade clienteDireccionFacade;
    @EJB
    private CiudadFacade ciudadFacade;
    @EJB
    private ProvinciaFacade provinciaFacade;
    @EJB
    private CursoFacade cursoFacade;
    @EJB
    private VisitaTecnicaFacade visitaTecnicaFacade;
    @EJB
    private CursoNotificadorServicio cursoNotificadorServicio;
    @EJB
    private VisitaTecnicaNotificadorServicio visitaTecnicaNotificadorServicio;
    @EJB
    private DatosSinetcomFacade datosSinetcomFacade;
    @EJB
    private TicketServicio ticketServicio;
    
    public List<Contrato> cargarContratos() {
        return contratoFacade.findAll();
    }

    public boolean crearContrato(Contrato contrato) {
        try {
            contratoFacade.create(contrato);
//            if (!contrato.getCursoList().isEmpty()) {
//                for (Curso curso : contrato.getCursoList()) {
//                    TareaCursoInfo tareaCursoInfo = new TareaCursoInfo("c_" + curso.getCodigo(), "Notificador Curso # " + curso.getCodigo(), "LoteTareaNotificarCurso", curso);
//                    Calendar c = Calendar.getInstance();
//                    c.setTime(curso.getFechaDeInicio());
//                    c.add(Calendar.DAY_OF_MONTH, -15);
//                    tareaCursoInfo.setStartDate(c.getTime());
//                    c.setTime(curso.getFechaDeInicio());
//                    tareaCursoInfo.setEndDate(c.getTime());
//                    tareaCursoInfo.setSecond("0");
//                    tareaCursoInfo.setMinute("0");
//                    tareaCursoInfo.setHour("8");
//                    tareaCursoInfo.setDayOfMonth("*");
//                    tareaCursoInfo.setMonth("*");
//                    tareaCursoInfo.setYear("*");
//                    tareaCursoInfo.setDescription("Tarea Notificador Curso# " + curso.getCodigo());
//                    this.cursoNotificadorServicio.createJob(tareaCursoInfo);
//                }
//            }
//            if (!contrato.getVisitaTecnicaList().isEmpty()) {
//                for (VisitaTecnica visitaT : contrato.getVisitaTecnicaList()) {
//                    TareaVisitaTecnicaInfo tareaVisitaTecnicaInfo = new TareaVisitaTecnicaInfo("v_" + visitaT.getId(), "Notificador Visita Técnica # " + visitaT.getId(), "LoteTareaNotificadorVisitaTecnica", visitaT);
//                    Calendar c = Calendar.getInstance();
//                    c.setTime(visitaT.getFecha());
//                    c.add(Calendar.DAY_OF_MONTH, -15);
//                    tareaVisitaTecnicaInfo.setStartDate(c.getTime());
//                    c.setTime(visitaT.getFecha());
//                    tareaVisitaTecnicaInfo.setEndDate(c.getTime());
//                    tareaVisitaTecnicaInfo.setSecond("0");
//                    tareaVisitaTecnicaInfo.setMinute("0");
//                    tareaVisitaTecnicaInfo.setHour("8");
//                    tareaVisitaTecnicaInfo.setDayOfMonth("*");
//                    tareaVisitaTecnicaInfo.setMonth("*");
//                    tareaVisitaTecnicaInfo.setYear("*");
//                    tareaVisitaTecnicaInfo.setDescription("Tarea Notificador Curso# " + visitaT.getId());
//                    this.visitaTecnicaNotificadorServicio.createJob(tareaVisitaTecnicaInfo);
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public void enviarNotificacionDeCurso(Curso curso){
        UtilidadDeEmail utilidadDeEmail = new UtilidadDeEmail();
        DatosSinetcom datosSinetcom = this.datosSinetcomFacade.find("1791839692001");
        List<String> cc = new ArrayList<String>();
        cc.add(datosSinetcom.getEmailGerenteComercial());
        cc.add(datosSinetcom.getEmailPresidente());
        cc.add(datosSinetcom.getEmailGerenteTecnico());
        utilidadDeEmail.enviarMensajeConAdjunto(datosSinetcom.getEmailNoResponder(), datosSinetcom.getEmailSupervisorContratos(), "Recordatorio de Curso", crearCuerpoDeCorreoDeCursoProximo(curso), cc, null, null);
    }
    
    public void enviarNotificacionDeVisitaTecnica(VisitaTecnica visitaTecnica){
        UtilidadDeEmail utilidadDeEmail = new UtilidadDeEmail();
        DatosSinetcom datosSinetcom = this.datosSinetcomFacade.find("1791839692001");
        List<String> cc = new ArrayList<String>();
        cc.add(datosSinetcom.getEmailGerenteComercial());
        cc.add(datosSinetcom.getEmailPresidente());
        cc.add(datosSinetcom.getEmailGerenteTecnico());
        utilidadDeEmail.enviarMensajeConAdjunto(datosSinetcom.getEmailNoResponder(), datosSinetcom.getEmailSupervisorContratos(), "Recordatorio de Visita Técnica", crearCuerpoDeVisitaTecnicaProxima(visitaTecnica), cc, null, null);
    }

    public boolean editarContrato(Contrato contrato) {
        try {
            contratoFacade.edit(contrato);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public String formatoCortoDeFecha(Date fecha){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(fecha);
    }

    public List<TipoContrato> cargarTiposContrato() {
        return tipoContratoFacade.findAll();
    }

    public TipoContrato recuperarTipoContrato(Integer id) {
        return tipoContratoFacade.find(id);
    }

    public List<ClienteEmpresa> cargarEmpresas() {
        return clienteEmpresaFacade.findAll();
    }

    public ClienteEmpresa recuperarRucEmpresa(Integer id) {
        return clienteEmpresaFacade.find(id);
    }

    public List<Sla> cargarSlas() {
        return slaFacade.findAll();
    }

    public Sla recuperarSla(Integer id) {
        return slaFacade.find(id);
    }

    public List<Usuario> cargarUsuarios() {
        return usuarioFacade.findAll();
    }

    public Usuario recuperarUsuario(Integer id) {
        return usuarioFacade.find(id);
    }

    public List<Contacto> cargarContactos() {
        return contactoFacade.findAll();
    }

    public Contacto recuperarContacto(Integer id) {
        return contactoFacade.find(id);
    }

    public List<TipoGarantia> cargarTipoDeGarantias() {
        return tipoGarantiaFacade.findAll();
    }

    public TipoGarantia recuperarTipoDeGarantia(Integer id) {
        return tipoGarantiaFacade.find(id);
    }

    public List<TipoDeVisita> cargarTipoDeVisita() {
        return tipoDeVisitaFacade.findAll();
    }

    public TipoDeVisita recuperarTipoDeVisita(Integer id) {
        return tipoDeVisitaFacade.find(id);
    }

    public List<Usuario> cargarUsuariosTecnicos() {
        return usuarioFacade.obtenerTodosLosIngenieros();
    }

    public List<Usuario> cargarUsuariosVentas() {
        return usuarioFacade.obtenerTodosLosAccountManager();
    }

    public List<ClienteDireccion> cargarDirecciones() {
        return this.clienteDireccionFacade.findAll();
    }

    public List<Contacto> cargarTodosLosContactosDeCliente(ClienteEmpresa clienteEmpresa) {
        return contactoFacade.obtenerContactosDeCliente(clienteEmpresa);
    }

    public Contrato recuperarContrato(String numeroContrato) {
        return contratoFacade.find(numeroContrato);
    }

    public Pago recuperarPago(Integer id) {
        return pagoFacade.find(id);
    }

    public GarantiaEconomica recuperarGarantiaE(Integer id) {
        return garantiaEconomicaFacade.find(id);
    }

    public Ciudad recuperarCiudad(Integer id) {
        return ciudadFacade.find(id);
    }
    
    public Provincia recuperarProvincia(Integer id){
        return provinciaFacade.find(id);
    }

    public List<Ciudad> cargarCiudades() {
        return ciudadFacade.findAll();
    }
    
    public List<Provincia> cargarProvincias(){
        return provinciaFacade.findAll();
    }
    
    public boolean crearCiudad(Ciudad ciudad){
        try{
            this.ciudadFacade.create(ciudad);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean crearProvincia(Provincia provincia){
        try{
            this.provinciaFacade.create(provincia);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public int obtenerIndiceDeAdendum(Contrato contrato){
        return this.contratoFacade.obtenerIndiceDeAdendum(contrato);
    }

    /**
     * Verifica los pagos por vencer en la prox 2 semanas
     */
    public void verificarPagosPorVencerEnProx2Semanas() {
        List<Pago> pagos = this.pagoFacade.obtenerTodosLosPagosPorVencerEnProx2Semanas();
        UtilidadDeEmail utilidadDeEmail = new UtilidadDeEmail();
        DatosSinetcom datosSinetcom = this.datosSinetcomFacade.find("1791839692001");
        if (pagos != null && !pagos.isEmpty()) {
            List<String> cc = new ArrayList<String>();
            cc.add(datosSinetcom.getEmailPresidente());
            cc.add(datosSinetcom.getEmailGerenteComercial());
            cc.add(datosSinetcom.getEmailGerenteGeneral());
            for (Pago pago : pagos) {
                utilidadDeEmail.enviarMensajeConAdjunto(datosSinetcom.getEmailNoResponder(), datosSinetcom.getEmailSupervisorContratos(), "Notificación Pago por Vencer", crearCuerpoDeCorreoDeNotificacionDePagoPorVencer(pago), null, null, null);
            }
        }
    }

    /**
     * Verifica las garantias economicas por vencer en las prox 2 semanas
     */
    public void verificarGarantiasEconomicasPorVencerEnProx2Semanas() {
        List<GarantiaEconomica> garantias = this.garantiaEconomicaFacade.obtenerTodasLasGarantiasEconomicasPorVencerEnProx2Semanas();
        UtilidadDeEmail utilidadDeEmail = new UtilidadDeEmail();
        DatosSinetcom datosSinetcom = this.datosSinetcomFacade.find("1791839692001");
        if (garantias != null && !garantias.isEmpty()) {
            List<String> cc = new ArrayList<String>();
            cc.add(datosSinetcom.getEmailPresidente());
            cc.add(datosSinetcom.getEmailGerenteGeneral());
            cc.add(datosSinetcom.getEmailGerenteComercial());
            for (GarantiaEconomica garantia : garantias) {
                utilidadDeEmail.enviarMensajeConAdjunto(datosSinetcom.getEmailNoResponder(), datosSinetcom.getEmailSupervisorContratos(), "Notificación Garantía E. por Vencer", crearCuerpoDeCorreoDeNotificacionDeGarantiaEconomicaPorVencer(garantia), null, null, null);
            }
        }
    }
    
    /**
     * Verifica los cursos que se dictarán en las prox 2 semanas
     */
    public void verificarCursosADictarEnProx2Semanas(){
        List<Curso> cursos = this.cursoFacade.obtenerTodosLosCursosADictarEnProx2Semanas();
        UtilidadDeEmail utilidadDeEmail = new UtilidadDeEmail();
        DatosSinetcom datosSinetcom = this.datosSinetcomFacade.find("1791839692001");
        if(cursos != null && !cursos.isEmpty()){
            List<String> cc = new ArrayList<String>();
            cc.add(datosSinetcom.getEmailPresidente());
            cc.add(datosSinetcom.getEmailGerenteComercial());
            cc.add(datosSinetcom.getEmailGerenteTecnico());
            for(Curso curso : cursos){
                utilidadDeEmail.enviarMensajeConAdjunto(datosSinetcom.getEmailNoResponder(), datosSinetcom.getEmailSupervisorContratos(), "Notificación Curso por dictar", crearCuerpoDeCorreoDeCursoProximo(curso), cc, null, null);
            }
        }
    }
    
    /**
     * Verifica las visitas tecnicas que se deben realizar en las proximas 2 semanas
     */
    public void verificarVisitasTecnicasEnProx2Semanas(){
        List<VisitaTecnica> visitasTecnicas = this.visitaTecnicaFacade.obtenerTodasLasVisitasTecnicasEnProx2Semanas();
        UtilidadDeEmail utilidadDeEmail = new UtilidadDeEmail();
        DatosSinetcom datosSinetcom = this.datosSinetcomFacade.find("1791839692001");
        if(visitasTecnicas != null && !visitasTecnicas.isEmpty()){
            List<String> cc = new ArrayList<String>();
            cc.add(datosSinetcom.getEmailPresidente());
            cc.add(datosSinetcom.getEmailGerenteComercial());
            cc.add(datosSinetcom.getEmailGerenteTecnico());
            for(VisitaTecnica visitaTecnica : visitasTecnicas){
                utilidadDeEmail.enviarMensajeConAdjunto(datosSinetcom.getEmailNoResponder(), datosSinetcom.getEmailGerenteTecnico(), "Notificación Visita Técnica", crearCuerpoDeVisitaTecnicaProxima(visitaTecnica), cc, null, null);
            }
        }
    }
    
//    public void verificarVisitasTecnicasDelPresenteDia(){
//        List<VisitaTecnica> visitaTecnicas = this.visitaTecnicaFacade.obtenerTodasLasVisitasTecnicasProgramadasParaEsteDia();
//        if(visitaTecnicas != null && !visitaTecnicas.isEmpty()){
//            for(VisitaTecnica visitaTecnica: visitaTecnicas){
//                Ticket ticket = new Ticket();
//                ticket.setClienteEmpresaruc(visitaTecnica.getContratonumero().getClienteEmpresaruc());
//                ticket.setColaid(this.ticketServicio.obtenerColaTicket(7));
//                ticket.setEstadoTicketcodigo(this.ticketServicio.obtenerTodosLosEstados().get(0));
//                ticket.setServicioTicketcodigo(this.ticketServicio.obtenerServicioTicket(2));
//                ticket.setPrioridadTicketcodigo(this.ticketServicio.obtenerPrioridadTicket(3));
//                ticket.setUsuarioidcreador(this.ticketServicio.obtenerUsuario(2));
//                ticket.setUsuarioidpropietario(this.ticketServicio.obtenerUsuario(2));
//                ticket.setUsuarioidresponsable(this.ticketServicio.obtenerUsuario(2));
//            }
//        }
//    }

    /**
     * Crea el cuerpo del mensaje de correo electrónico de un pago por vencer
     *
     * @param pago
     * @return
     */
    private String crearCuerpoDeCorreoDeNotificacionDePagoPorVencer(Pago pago) {
        StringBuilder cuerpo = new StringBuilder();
        //Se arma el cuerpo del correo electrónico
        cuerpo.append("Estimad@").append("\n\n\n");
        cuerpo.append("Le informamos que un pago se encuentra por vencer, a continuación los detalles: ").append("\n\n");
        cuerpo.append("Contrato: ").append(pago.getContratonumero().getNumero()).append("\n");
        cuerpo.append("Monto: ").append(pago.getMonto()).append("\n");
        cuerpo.append("Plazo Máximo: ").append(formatoCortoDeFecha(pago.getPlazo())).append("\n");
        cuerpo.append("Pago Número: ").append(pago.getOrden()).append("\n");
        cuerpo.append("¿Es anticipo?: ").append(pago.getAnticipo() != null && pago.getAnticipo() ? "Si" : "No").append("\n\n\n");
        cuerpo.append("Saludos Cordiales.").append("\n");

        return cuerpo.toString();
    }

    /**
     * Crea el cuerpo del mensaje de correo electrónico de una garantía
     * económica por vencer
     *
     * @param garantiaEconomica
     * @return
     */
    private String crearCuerpoDeCorreoDeNotificacionDeGarantiaEconomicaPorVencer(GarantiaEconomica garantiaEconomica) {
        StringBuilder cuerpo = new StringBuilder();
        //Se arma el cuerpo del correo electrónico
        cuerpo.append("Estimad@").append("\n\n\n");
        cuerpo.append("Le informamos que un pago se encuentra por vencer, a continuación los detalles: ").append("\n\n");
        cuerpo.append("Contrato: ").append(garantiaEconomica.getContratonumero().getNumero()).append("\n");
        cuerpo.append("Monto: ").append(garantiaEconomica.getValor()).append("\n");
        cuerpo.append("Fecha de finalización: ").append(formatoCortoDeFecha(garantiaEconomica.getFechaFin())).append("\n");
        cuerpo.append("Porcentaje: ").append(garantiaEconomica.getPorcentaje()).append("%\n");
        cuerpo.append("¿Es renovación?: ").append(garantiaEconomica.getRenovacion()? "Si" : "No").append("\n");
        cuerpo.append("Tipo de Garantía E.: ").append(garantiaEconomica.getTipoGarantiaid().getTipo()).append("\n\n\n");
        cuerpo.append("Saludos Cordiales.").append("\n");

        return cuerpo.toString();
    }

    /**
     * Crea un nuevo sla
     *
     * @param sla
     * @return
     */
    public boolean crearSla(Sla sla) {
        try {
            this.slaFacade.create(sla);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Elimina un sla
     *
     * @param sla
     * @return
     */
    public boolean eliminarSla(Sla sla) {
        try {
            this.slaFacade.remove(sla);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Modifica un SLA
     *
     * @param sla
     * @return
     */
    public boolean modificarSla(Sla sla) {
        try {
            this.slaFacade.edit(sla);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Crea un tipo de disponibilidad
     *
     * @param tipoDisponibilidad
     * @return
     */
    public boolean crearTipoDeDisponibilidad(TipoDisponibilidad tipoDisponibilidad) {
        try {
            this.tipoDisponibilidadFacade.create(tipoDisponibilidad);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Eliminar un tipo de disponibilidad
     *
     * @param tipoDisponibilidad
     * @return
     */
    public boolean eliminarTipoDeDisponibilidad(TipoDisponibilidad tipoDisponibilidad) {
        try {
            this.tipoDisponibilidadFacade.remove(tipoDisponibilidad);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Modifica un tipo de disponibilidad
     *
     * @param tipoDisponibilidad
     * @return
     */
    public boolean modificarTipoDeDisponibilidad(TipoDisponibilidad tipoDisponibilidad) {
        try {
            this.tipoDisponibilidadFacade.edit(tipoDisponibilidad);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 
     * @param clienteDireccion
     * @return 
     */
    public boolean existeDireccionCliente(ClienteDireccion clienteDireccion){
        return this.clienteDireccionFacade.existeDireccionCliente(clienteDireccion.getCiudad(), clienteDireccion.getClienteEmpresaid()) != null;
    }

    /**
     * Crea una nueva dirección de un cliente
     *
     * @param clienteDireccion
     * @return
     */
    public boolean crearDireccionCliente(ClienteDireccion clienteDireccion) {
        try {
            this.clienteDireccionFacade.create(clienteDireccion);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Eliminar la dirección de un cliente
     *
     * @param clienteDireccion
     * @return
     */
    public boolean eliminarDireccionCliente(ClienteDireccion clienteDireccion) {
        try {
            this.clienteDireccionFacade.remove(clienteDireccion);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    private String crearCuerpoDeCorreoDeCursoProximo(Curso curso){
        StringBuilder sb = new StringBuilder();
        sb.append("Reciba un cordial saludo,").append("\n\n");
        sb.append("Le recordamos que un nuevo curso se aproxima, a continuación los detalles:").append("\n");
        sb.append("Tema a tratar: ").append(curso.getTemaATratar()).append("\n");
        sb.append("Cliente: ").append(curso.getContratonumero().getClienteEmpresaid().getNombreComercial()).append("\n");
        sb.append("Número de contrato: ").append(curso.getContratonumero().getNumero()).append("\n");
        sb.append("Fecha: ").append(formatoCortoDeFecha(curso.getFechaDeInicio())).append("\n");
        sb.append("Instructor: ").append(curso.getNombreInstructor()).append("\n");
        sb.append("¿Es un curso oficial?: ").append(curso.getOficial() ? "Si" : "No").append("\n");
        sb.append("Número de horas totales: ").append(curso.getNumeroDeHorasTotales()).append(" horas").append("\n");
        sb.append("Número de participantes: ").append(curso.getNumeroDeParticipantes()).append("\n\n");
        sb.append("Saludos Cordiales.").append("\n\n");
        
        return sb.toString();
    }
    
    private String crearCuerpoDeVisitaTecnicaProxima(VisitaTecnica visitaTecnica){
        StringBuilder sb = new StringBuilder();
        sb.append("Reciba un cordial saludo,").append("\n\n");
        sb.append("Le recordamos que se acerca una visita técnica, a continuación los detalles:").append("\n");
        sb.append("Descripción: ").append(visitaTecnica.getDescripcion()).append("\n");
        sb.append("Tipo de Mantenimiento: ").append(visitaTecnica.getTipoDeVisitaid().getNombre()).append("\n");
        sb.append("Fecha: ").append(formatoCortoDeFecha(visitaTecnica.getFecha())).append("\n");
        sb.append("Cliente: ").append(visitaTecnica.getContratonumero().getClienteEmpresaid().getNombreComercial()).append("\n");
        sb.append("Contrato: ").append(visitaTecnica.getContratonumero().getNumero()).append("\n\n");
        sb.append("Saludos Cordiales.").append("\n\n");
        
        return sb.toString();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
