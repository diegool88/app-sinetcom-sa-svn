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
import ec.com.sinetcom.orm.HistorialDeContratosYEquipos;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.Pago;
import ec.com.sinetcom.orm.Sla;
import ec.com.sinetcom.orm.TipoContrato;
import ec.com.sinetcom.orm.TipoDeVisita;
import ec.com.sinetcom.orm.TipoGarantia;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.orm.VisitaTecnica;
import ec.com.sinetcom.servicios.ContratoServicio;
import ec.com.sinetcom.servicios.ProductoServicio;
import ec.com.sinetcom.webutil.Mensajes;
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
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.mozilla.javascript.Context;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "ingresoAdendumBean")
@ViewScoped
public class ingresoAdendumBean implements Serializable {

    @EJB
    private ContratoServicio contratoServicio;
    @EJB
    private ProductoServicio productoServicio;

    private List<Contrato> contratosDisponibles;
    private Contrato nuevoAdendum;
    private Contrato contratoSeleccionado;

    private UIInput filtroInputText;
    private String filtro;
    private List<TipoContrato> tiposContrato;
    private List<ClienteEmpresa> clientes;
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
    private List<ItemProducto> equipos;
    private List<ItemProducto> equiposStockLocal;
    private Curso nuevoCurso;
    private GarantiaEconomica nuevaGarantiaEconomica;
    private Integer cantidadPagos;
    private Integer tipoGarantia;
    private Integer tipoDeVisita;
    private Integer cantidadDeVisitasPorA;
    private String descripcionVisitaT;
    private Integer instructorSinetcom;
    private List<Date> fechasPagos = new ArrayList<Date>();
    private UploadedFile poliza;
    private UploadedFile contratoDigital;
    private UploadedFile actaDeERProyecto;
    private UploadedFile actaDeEREquipos;
    private TreeNode equiposPadre;
    private TreeNode equiposPadreStockLocal;
    private TreeNode equiposFiltrados;
    private TreeNode[] equiposPadreSeleccionados;
    private TreeNode[] equiposPadreStockLocalSeleccionados;
    private List<TreeNode> equiposPadreStockLocalSeleccionados1;

    @PostConstruct
    public void doInit() {
        this.contratosDisponibles = this.contratoServicio.cargarContratos();
        this.nuevoAdendum = new Contrato();
        this.tiposContrato = contratoServicio.cargarTiposContrato();
        this.clientes = contratoServicio.cargarEmpresas();
        this.slas = contratoServicio.cargarSlas();
        this.Usuarios = contratoServicio.cargarUsuariosVentas();
        this.tipoGarantias = contratoServicio.cargarTipoDeGarantias();
        this.tipoDeVisitas = contratoServicio.cargarTipoDeVisita();
        this.tecnicos = contratoServicio.cargarUsuariosTecnicos();
        this.nuevaGarantiaEconomica = new GarantiaEconomica();
        this.garantiasE = new ArrayList<GarantiaEconomica>();
        this.visitasTecnicas = new ArrayList<VisitaTecnica>();
        this.cursos = new ArrayList<Curso>();
        this.pagos = new ArrayList<Pago>();
        this.nuevoCurso = new Curso();
        this.contratoSeleccionado = new Contrato();
        this.equiposPadre = new DefaultTreeNode();
        this.equiposStockLocal = this.productoServicio.obtenerTodosLosProductosPadresDeStockLocal();
        this.equiposPadreStockLocalSeleccionados1 = new ArrayList<TreeNode>();
        if (!this.equiposStockLocal.isEmpty()) {
            this.equiposPadreStockLocal = crearNodoPrincipalDeEquipos(this.equiposStockLocal);
        }
    }

    public void asignarContratoSeleccionado(SelectEvent event) {
        this.contratoSeleccionado = (Contrato) event.getObject();
        this.nuevoAdendum = obtenerNuevaInstanciaDeContrato(this.contratoSeleccionado);
    }

    public void grabarAdendum(ActionEvent event) {
        //Se agregan los pagos, garantias, visitas tecnicas y cursos así como los adjuntos
        if(this.contratoDigital == null || this.actaDeEREquipos == null || this.actaDeERProyecto == null){
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.validationFailed();
            StringBuilder builder = new StringBuilder();
            if(this.contratoDigital == null) builder.append("El Contrato digital no se ha cargado!\n");
            if(this.actaDeEREquipos == null) builder.append("El Acta E/R de Equipos digital no se ha cargado!\n");
            if(this.actaDeERProyecto == null) builder.append("El Acta E/R de Proyecto digital no se ha cargado!");
            Mensajes.mostrarMensajeDeError(builder.toString());
            return;
        }
        
        this.nuevoAdendum.setContratoDigital(this.contratoDigital.getContents());
        this.nuevoAdendum.setActaEREquiposDigital(this.actaDeEREquipos.getContents());
        this.nuevoAdendum.setActaERProyectoDigital(this.actaDeERProyecto.getContents());

        if (!pagos.isEmpty()) {
            int i = 0;
            for (Pago pago : pagos) {
                pago.setContratonumero(this.nuevoAdendum);
                if (i == 0) {
                    pago.setAnticipo(true);
                }
                i++;
            }
            this.nuevoAdendum.setPagoList(this.pagos);
        }
        if (!garantiasE.isEmpty()) {
            for (GarantiaEconomica garantiaEconomica : garantiasE) {
                garantiaEconomica.setContratonumero(this.nuevoAdendum);
            }
            this.nuevoAdendum.setGarantiaEconomicaList(this.garantiasE);
        }
        if (!cursos.isEmpty()) {
            for (Curso curso : cursos) {
                curso.setContratonumero(this.nuevoAdendum);
            }
            this.nuevoAdendum.setCursoList(this.cursos);
        }
        if (!visitasTecnicas.isEmpty()) {
            for (VisitaTecnica visitaTecnica : visitasTecnicas) {
                visitaTecnica.setContratonumero(this.nuevoAdendum);
            }
            this.nuevoAdendum.setVisitaTecnicaList(this.visitasTecnicas);
        }

        if (contratoServicio.crearContrato(this.nuevoAdendum)) {
            if (this.equiposPadreSeleccionados != null && this.equiposPadreSeleccionados.length > 0) {
                for (TreeNode equiposPadreSeleccionado : equiposPadreSeleccionados) {
                    cambiarEquipoDeContrato(equiposPadreSeleccionado, this.nuevoAdendum);
                }
            }
            if (this.equiposPadreStockLocalSeleccionados1 != null && this.equiposPadreStockLocalSeleccionados1.size() > 0) {
                for (TreeNode equiposPadreSeleccionado : equiposPadreStockLocalSeleccionados1) {
                    cambiarEquipoDeContrato(equiposPadreSeleccionado, this.nuevoAdendum);
                }
            }
            Mensajes.mostrarMensajeInformativo("Adendum creado con éxito!");
        } else {
            Mensajes.mostrarMensajeDeError("Adendum no creado, error interno!");
        }
        this.contratosDisponibles = this.contratoServicio.cargarContratos();
    }

    public Contrato obtenerNuevaInstanciaDeContrato(Contrato contrato) {
        Contrato nuevaInstancia = new Contrato();
        int indiceInicial = contrato.getNumero().length() - 4;
        int indiceFinal = contrato.getNumero().length();
        nuevaInstancia.setNumero(contrato.getNumero().substring(indiceInicial, indiceFinal).contains("-AD") ? contrato.getNumero().substring(0, indiceFinal - 1) + (Integer.parseInt(contrato.getNumero().substring(indiceFinal - 1, indiceFinal)) + 1) : contrato.getNumero() + "-AD" + this.contratoServicio.obtenerIndiceDeAdendum(contrato));
        nuevaInstancia.setTipoContratoid(contrato.getTipoContratoid());
        nuevaInstancia.setClienteEmpresaid(contrato.getClienteEmpresaid());
        nuevaInstancia.setSlaid(contrato.getSlaid());
        nuevaInstancia.setAccountManagerAsignado(contrato.getAccountManagerAsignado());
        nuevaInstancia.setAdministradorDeContrato(contrato.getAdministradorDeContrato());
        this.Contactos = contrato.getClienteEmpresaid().getContactoList();
        nuevaInstancia.setFechaDeSuscripcion(contrato.getFechaDeSuscripcion());
        nuevaInstancia.setObjeto(contrato.getObjeto());
        nuevaInstancia.setPrecioTotal(contrato.getPrecioTotal());
        nuevaInstancia.setTiempoDeValidez(contrato.getTiempoDeValidez());
        nuevaInstancia.setGarantiaTecnica(contrato.getGarantiaTecnica());
        nuevaInstancia.setFechaInicioGarantiaTecnica(contrato.getFechaInicioGarantiaTecnica());
        nuevaInstancia.setServicioSoporteMantenimiento(contrato.getServicioSoporteMantenimiento());
        nuevaInstancia.setIncluyeRepuestos(contrato.getIncluyeRepuestos());
        nuevaInstancia.setHorasDeSoporteAnual(contrato.getHorasDeSoporteAnual());
        nuevaInstancia.setHorasDeSoporteUtilizadas(contrato.getHorasDeSoporteUtilizadas());
        nuevaInstancia.setFechaDeFacturacion(contrato.getFechaDeFacturacion());
        nuevaInstancia.setNumeroDeFactura(contrato.getNumeroDeFactura());
        nuevaInstancia.setPagosAlDia(contrato.getPagosAlDia());
        nuevaInstancia.setRazonDemoraDelPago(contrato.getRazonDemoraDelPago());
        nuevaInstancia.setInicioPorAnticipo(contrato.getInicioPorAnticipo());
        nuevaInstancia.setPlazoDeEntrega(contrato.getPlazoDeEntrega());
        nuevaInstancia.setFechaDeEntregaRecepcion(contrato.getFechaDeEntregaRecepcion());
        nuevaInstancia.setActaEREquiposDigital(contrato.getActaEREquiposDigital());
        nuevaInstancia.setActaERProyectoDigital(contrato.getActaERProyectoDigital());
        nuevaInstancia.setObservaciones(contrato.getObservaciones());
        nuevaInstancia.setAdendumDe(contrato);
        //nuevaInstancia.setItemProductoList(contrato.getItemProductoList());
        this.equipos = this.productoServicio.obtenerTodosLosProductosPadreDeUnContrato(contrato);
        if (!this.equipos.isEmpty()) {
            this.equiposPadre = crearNodoPrincipalDeEquipos(this.equipos);
        } else {
            this.equiposPadre = new DefaultTreeNode();
        }
        return nuevaInstancia;
    }

    public void cambiarEquipoDeContrato(TreeNode nodo, Contrato contrato) {
        ItemProducto producto = (ItemProducto) nodo.getData();
        producto.setContratonumero(contrato);
        producto.setBodegaid(null);
        this.productoServicio.actualizarItemProducto(producto);
        this.productoServicio.crearRegistroDeHistorialDeEquiposYContratos(producto, contrato);
        for (TreeNode hijos : nodo.getChildren()) {
            cambiarEquipoDeContrato(hijos, contrato);
        }
    }

    public TreeNode crearNodoPrincipalDeEquipos(List<ItemProducto> equipos) {
        TreeNode root = new DefaultTreeNode(new ItemProducto("N/A"), null);
        for (ItemProducto producto : equipos) {
            TreeNode nuevoEquipo = nuevoNodoPadreDeEquipo(producto, root);
        }
        return root;
    }

    /**
     * Función recursiva que permite buscar todos las parte de un producto padre
     *
     * @param item
     * @param padre
     * @return
     */
    public TreeNode nuevoNodoPadreDeEquipo(ItemProducto item, TreeNode padre) {
        TreeNode nuevoNodo = new DefaultTreeNode(item, padre);
        for (ItemProducto itemHijo : item.getItemProductoList()) {
            TreeNode nuevoNodo2 = nuevoNodoPadreDeEquipo(itemHijo, nuevoNodo);
        }
        return nuevoNodo;
    }

    public void actualizarTreeNodeEquipos() {
        List<ItemProducto> productosFiltrados = new ArrayList<ItemProducto>();
        for (ItemProducto item : this.equiposStockLocal) {
            if (item.getNumeroSerial().contains(this.filtro)) {
                productosFiltrados.add(item);
            }
        }
        this.equiposPadreStockLocal = crearNodoPrincipalDeEquipos(productosFiltrados);
        //this.equiposPadreStockLocalSeleccionados = null;
        navegarNodoYEditarSeleccion(this.equiposPadreStockLocal);
    }

    public void navegarNodoYEditarSeleccion(TreeNode nodoPrincipal) {
        if (this.equiposPadreStockLocalSeleccionados1.contains(nodoPrincipal)) {
            if (nodoPrincipal.getParent() != null && !nodoPrincipal.getParent().isExpanded()) {
                nodoPrincipal.getParent().setExpanded(true);
            }
            nodoPrincipal.setSelected(true);
            //int index = this.equiposPadreStockLocalSeleccionados.length;
            //this.equiposPadreStockLocalSeleccionados[index] = nodoPrincipal;
        }
        for (TreeNode hijo : nodoPrincipal.getChildren()) {
            navegarNodoYEditarSeleccion(hijo);
        }
    }

    public void nodoSeleccionado(NodeSelectEvent event) {
        if (!this.equiposPadreStockLocalSeleccionados1.contains(event.getTreeNode())) {
            //this.equiposPadreStockLocalSeleccionados1.add(event.getTreeNode());
            seleccionarNodosHijos(event.getTreeNode());
        }
    }

    public void nodoDeseleccionado(NodeUnselectEvent event) {
        if (this.equiposPadreStockLocalSeleccionados1.contains(event.getTreeNode())) {
            //this.equiposPadreStockLocalSeleccionados1.remove(event.getTreeNode());
            deseleccionarNodosHijos(event.getTreeNode());
        }
    }

    public void seleccionarNodosHijos(TreeNode nodo) {
        this.equiposPadreStockLocalSeleccionados1.add(nodo);
        for (TreeNode hijo : nodo.getChildren()) {
            seleccionarNodosHijos(hijo);
        }
    }

    public void deseleccionarNodosHijos(TreeNode nodo) {
        this.equiposPadreStockLocalSeleccionados1.remove(nodo);
        for (TreeNode hijo : nodo.getChildren()) {
            deseleccionarNodosHijos(hijo);
        }
    }

    public void enFechaSeleccionada(SelectEvent evento) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fecha Seleccionada", format.format(evento.getObject())));
    }

    public void calcularFechasPagos() {
        if (this.cantidadPagos != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(this.nuevoAdendum.getFechaDeSuscripcion());
            c.add(Calendar.MONTH, this.nuevoAdendum.getTiempoDeValidez());
            int diasTotales = (int) ((c.getTime().getTime() - this.nuevoAdendum.getFechaDeSuscripcion().getTime()) / (1000 * 60 * 60 * 24));
            int diasEntre = (int) Math.round((double) diasTotales / (double) this.cantidadPagos);
            double monto = this.nuevoAdendum.getPrecioTotal().doubleValue() / (double) this.cantidadPagos;
            this.pagos = new ArrayList<Pago>();

            c.setTime(this.nuevoAdendum.getFechaDeSuscripcion());
            for (int i = 0; i < this.cantidadPagos; i++) {
                if (i > 0) {
                    c.add(Calendar.DAY_OF_MONTH, diasEntre);
                }
                this.pagos.add(new Pago(null, i + 1, new BigDecimal(monto), c.getTime()));
            }
        }
    }

    public void calcularFechasVisitasTecnicas(ActionEvent event) {
        if (this.cantidadDeVisitasPorA != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(this.nuevoAdendum.getFechaDeSuscripcion());
            c.add(Calendar.MONTH, this.nuevoAdendum.getTiempoDeValidez());
            int diasTotales = (int) ((c.getTime().getTime() - this.nuevoAdendum.getFechaDeSuscripcion().getTime()) / (1000 * 60 * 60 * 24));
            int diasEntre = (int) Math.round((double) diasTotales / (double) this.cantidadDeVisitasPorA);
            //Eliminamos los registros que sean del mismo tipo
            Iterator visitasT = this.visitasTecnicas.iterator();
            while (visitasT.hasNext()) {
                if (((VisitaTecnica) visitasT.next()).getTipoDeVisitaid().equals(this.contratoServicio.recuperarTipoDeVisita(this.tipoDeVisita))) {
                    visitasT.remove();
                }
            }

            c.setTime(this.nuevoAdendum.getFechaDeSuscripcion());
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
            this.nuevaGarantiaEconomica.setValor(new BigDecimal(this.nuevoAdendum.getPrecioTotal().doubleValue() * ((double) this.nuevaGarantiaEconomica.getPorcentaje() / 100.00)).setScale(2, RoundingMode.CEILING));
        }
    }

    public void cargarPoliza(FileUploadEvent event) {
        this.poliza = event.getFile();
        Mensajes.mostrarMensajeInformativo("La póliza " + event.getFile().getFileName() + " se ha cargado con éxito!");
    }

    public void eliminarGarantia(ActionEvent event) {
        int index = (Integer) event.getComponent().getAttributes().get("garantiaIndex");
        this.garantiasE.remove(index);
    }

    public void agregarGarantia(ActionEvent event) {
        this.nuevaGarantiaEconomica.setTipoGarantiaid(this.contratoServicio.recuperarTipoDeGarantia(this.tipoGarantia));
        if (this.poliza != null) {
            this.nuevaGarantiaEconomica.setPoliza(this.poliza.getContents());
        }
        this.garantiasE.add(nuevaInstanciaGarantiaEconomica(this.nuevaGarantiaEconomica));
        this.nuevaGarantiaEconomica = new GarantiaEconomica();
    }

    public GarantiaEconomica nuevaInstanciaGarantiaEconomica(GarantiaEconomica garantiaEconomica) {
        GarantiaEconomica garantiaE = new GarantiaEconomica();
        garantiaE.setFechaFin(garantiaEconomica.getFechaFin());
        garantiaE.setFechaInicio(garantiaEconomica.getFechaInicio());
        garantiaE.setPorcentaje(garantiaEconomica.getPorcentaje());
        garantiaE.setRenovacion(garantiaEconomica.getRenovacion());
        garantiaE.setTipoGarantiaid(garantiaEconomica.getTipoGarantiaid());
        garantiaE.setValor(garantiaEconomica.getValor());
        garantiaE.setPoliza(garantiaEconomica.getPoliza());
        garantiaE.setNumeroPoliza(garantiaEconomica.getNumeroPoliza());
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

    public BigDecimal formatoDosDecimales(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.CEILING);
    }

    public void cargarContactosDeCliente() {
        if (this.nuevoAdendum.getClienteEmpresaid()!= null || this.nuevoAdendum.getClienteEmpresaid().getRuc().length() > 0) {
            this.Contactos = this.contratoServicio.cargarTodosLosContactosDeCliente(this.contratoServicio.recuperarRucEmpresa(this.nuevoAdendum.getClienteEmpresaid().getId()));
        }
    }

    public void cargarContratoDigital(FileUploadEvent event) {
        this.contratoDigital = event.getFile();
        Mensajes.mostrarMensajeInformativo("El contrato " + event.getFile().getFileName() + " se ha cargado con éxito!");
    }

    public void cargarActaDeERProyecto(FileUploadEvent event) {
        this.actaDeERProyecto = event.getFile();
        Mensajes.mostrarMensajeInformativo("El acta de E/R de Proyecto " + event.getFile().getFileName() + " se ha cargado con éxito!");
    }

    public void cargarActaDeEREquipos(FileUploadEvent event) {
        this.actaDeEREquipos = event.getFile();
        Mensajes.mostrarMensajeInformativo("El acta de E/R de Equipos " + event.getFile().getFileName() + " se ha cargado con éxito!");
    }

    public List<TipoContrato> getTiposContrato() {
        return tiposContrato;
    }

    public void setTiposContrato(List<TipoContrato> tiposContrato) {
        this.tiposContrato = tiposContrato;
    }

    public List<ClienteEmpresa> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteEmpresa> clientes) {
        this.clientes = clientes;
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

    public List<TipoGarantia> getTipoGarantias() {
        return tipoGarantias;
    }

    public void setTipoGarantias(List<TipoGarantia> tipoGarantias) {
        this.tipoGarantias = tipoGarantias;
    }

    public List<TipoDeVisita> getTipoDeVisitas() {
        return tipoDeVisitas;
    }

    public void setTipoDeVisitas(List<TipoDeVisita> tipoDeVisitas) {
        this.tipoDeVisitas = tipoDeVisitas;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public List<GarantiaEconomica> getGarantiasE() {
        return garantiasE;
    }

    public void setGarantiasE(List<GarantiaEconomica> garantiasE) {
        this.garantiasE = garantiasE;
    }

    public List<Usuario> getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(List<Usuario> tecnicos) {
        this.tecnicos = tecnicos;
    }

    public List<VisitaTecnica> getVisitasTecnicas() {
        return visitasTecnicas;
    }

    public void setVisitasTecnicas(List<VisitaTecnica> visitasTecnicas) {
        this.visitasTecnicas = visitasTecnicas;
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

    public GarantiaEconomica getNuevaGarantiaEconomica() {
        return nuevaGarantiaEconomica;
    }

    public void setNuevaGarantiaEconomica(GarantiaEconomica nuevaGarantiaEconomica) {
        this.nuevaGarantiaEconomica = nuevaGarantiaEconomica;
    }

    public Integer getCantidadPagos() {
        return cantidadPagos;
    }

    public void setCantidadPagos(Integer cantidadPagos) {
        this.cantidadPagos = cantidadPagos;
    }

    public Integer getTipoGarantia() {
        return tipoGarantia;
    }

    public void setTipoGarantia(Integer tipoGarantia) {
        this.tipoGarantia = tipoGarantia;
    }

    public Integer getTipoDeVisita() {
        return tipoDeVisita;
    }

    public void setTipoDeVisita(Integer tipoDeVisita) {
        this.tipoDeVisita = tipoDeVisita;
    }

    public Integer getCantidadDeVisitasPorA() {
        return cantidadDeVisitasPorA;
    }

    public void setCantidadDeVisitasPorA(Integer cantidadDeVisitasPorA) {
        this.cantidadDeVisitasPorA = cantidadDeVisitasPorA;
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

    public List<Date> getFechasPagos() {
        return fechasPagos;
    }

    public void setFechasPagos(List<Date> fechasPagos) {
        this.fechasPagos = fechasPagos;
    }

    public UploadedFile getPoliza() {
        return poliza;
    }

    public void setPoliza(UploadedFile poliza) {
        this.poliza = poliza;
    }

    public List<Contrato> getContratosDisponibles() {
        return contratosDisponibles;
    }

    public void setContratosDisponibles(List<Contrato> contratosDisponibles) {
        this.contratosDisponibles = contratosDisponibles;
    }

    public Contrato getNuevoAdendum() {
        return nuevoAdendum;
    }

    public void setNuevoAdendum(Contrato nuevoAdendum) {
        this.nuevoAdendum = nuevoAdendum;
    }

    public Contrato getContratoSeleccionado() {
        return contratoSeleccionado;
    }

    public void setContratoSeleccionado(Contrato contratoSeleccionado) {
        this.contratoSeleccionado = contratoSeleccionado;
    }

    public UploadedFile getContratoDigital() {
        return contratoDigital;
    }

    public void setContratoDigital(UploadedFile contratoDigital) {
        this.contratoDigital = contratoDigital;
    }

    public List<ItemProducto> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<ItemProducto> equipos) {
        this.equipos = equipos;
    }

    public TreeNode getEquiposPadre() {
        return equiposPadre;
    }

    public void setEquiposPadre(TreeNode equiposPadre) {
        this.equiposPadre = equiposPadre;
    }

    public TreeNode[] getEquiposPadreSeleccionados() {
        return equiposPadreSeleccionados;
    }

    public void setEquiposPadreSeleccionados(TreeNode[] equiposPadreSeleccionados) {
        this.equiposPadreSeleccionados = equiposPadreSeleccionados;
    }

    public TreeNode getEquiposPadreStockLocal() {
        return equiposPadreStockLocal;
    }

    public void setEquiposPadreStockLocal(TreeNode equiposPadreStockLocal) {
        this.equiposPadreStockLocal = equiposPadreStockLocal;
    }

    public TreeNode[] getEquiposPadreStockLocalSeleccionados() {
        return equiposPadreStockLocalSeleccionados;
    }

    public void setEquiposPadreStockLocalSeleccionados(TreeNode[] equiposPadreStockLocalSeleccionados) {
        this.equiposPadreStockLocalSeleccionados = equiposPadreStockLocalSeleccionados;
    }

    public List<ItemProducto> getEquiposStockLocal() {
        return equiposStockLocal;
    }

    public void setEquiposStockLocal(List<ItemProducto> equiposStockLocal) {
        this.equiposStockLocal = equiposStockLocal;
    }

    public TreeNode getEquiposFiltrados() {
        return equiposFiltrados;
    }

    public void setEquiposFiltrados(TreeNode equiposFiltrados) {
        this.equiposFiltrados = equiposFiltrados;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public UIInput getFiltroInputText() {
        return filtroInputText;
    }

    public void setFiltroInputText(UIInput filtroInputText) {
        this.filtroInputText = filtroInputText;
    }

    public List<TreeNode> getEquiposPadreStockLocalSeleccionados1() {
        return equiposPadreStockLocalSeleccionados1;
    }

    public void setEquiposPadreStockLocalSeleccionados1(List<TreeNode> equiposPadreStockLocalSeleccionados1) {
        this.equiposPadreStockLocalSeleccionados1 = equiposPadreStockLocalSeleccionados1;
    }

}
