/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "Contrato", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contrato.findAll", query = "SELECT c FROM Contrato c")})
public class Contrato implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "contratoDigital")
    private byte[] contratoDigital;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contratonumero")
    private List<VisitaTecnica> visitaTecnicaList;
    @JoinTable(name = "HistorialDeContratosYEquipos", joinColumns = {
        @JoinColumn(name = "Contrato_numero", referencedColumnName = "numero")}, inverseJoinColumns = {
        @JoinColumn(name = "ItemProducto_numeroSerial", referencedColumnName = "numeroSerial")})
    @ManyToMany
    private List<ItemProducto> itemProductoList1;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "numero")
    private String numero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaDeSuscripcion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeSuscripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "objeto")
    private String objeto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "precioTotal")
    private BigDecimal precioTotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tiempoDeValidez")
    private int tiempoDeValidez;
    @Column(name = "garantiaTecnica")
    private Integer garantiaTecnica;
    @Column(name = "fechaInicioGarantiaTecnica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioGarantiaTecnica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "servicioSoporteMantenimiento")
    private int servicioSoporteMantenimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "incluyeRepuestos")
    private boolean incluyeRepuestos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horasDeSoporteAnual")
    private int horasDeSoporteAnual;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horasDeSoporteUtilizadas")
    private int horasDeSoporteUtilizadas;
    @Column(name = "fechaDeFacturacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeFacturacion;
    @Size(max = 45)
    @Column(name = "numeroDeFactura")
    private String numeroDeFactura;
    @Column(name = "pagosAlDia")
    private Boolean pagosAlDia;
    @Size(max = 120)
    @Column(name = "razonDemoraDelPago")
    private String razonDemoraDelPago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inicioPorAnticipo")
    private boolean inicioPorAnticipo;
    @Column(name = "plazoDeEntrega")
    private Integer plazoDeEntrega;
    @Column(name = "fechaDeEntregaRecepcion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeEntregaRecepcion;
    @Size(max = 45)
    @Column(name = "idNotificadorFinGarantiaTecnica")
    private String idNotificadorFinGarantiaTecnica;
    @Size(max = 45)
    @Column(name = "idNotificadorFinDeContrato")
    private String idNotificadorFinDeContrato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contratonumero")
    private List<Pago> pagoList;
    @JoinColumn(name = "accountManagerAsignado", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario accountManagerAsignado;
    @JoinColumn(name = "TipoContrato_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoContrato tipoContratoid;
    @JoinColumn(name = "Sla_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sla slaid;
    @JoinColumn(name = "administradorDeContrato", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contacto administradorDeContrato;
    @JoinColumn(name = "ClienteEmpresa_ruc", referencedColumnName = "ruc")
    @ManyToOne(optional = false)
    private ClienteEmpresa clienteEmpresaruc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contratonumero")
    private List<Curso> cursoList;
    @OneToMany(mappedBy = "contratonumero")
    private List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contratonumero")
    private List<GarantiaEconomica> garantiaEconomicaList;
    @OneToMany(mappedBy = "contratonumero")
    private List<ItemProducto> itemProductoList;

    public Contrato() {
    }

    public Contrato(String numero) {
        this.numero = numero;
    }

    public Contrato(String numero, Date fechaDeSuscripcion, String objeto, BigDecimal precioTotal, int tiempoDeValidez, byte[] contratoDigital, int servicioSoporteMantenimiento, boolean incluyeRepuestos, int horasDeSoporteAnual, int horasDeSoporteUtilizadas, boolean inicioPorAnticipo) {
        this.numero = numero;
        this.fechaDeSuscripcion = fechaDeSuscripcion;
        this.objeto = objeto;
        this.precioTotal = precioTotal;
        this.tiempoDeValidez = tiempoDeValidez;
        this.contratoDigital = contratoDigital;
        this.servicioSoporteMantenimiento = servicioSoporteMantenimiento;
        this.incluyeRepuestos = incluyeRepuestos;
        this.horasDeSoporteAnual = horasDeSoporteAnual;
        this.horasDeSoporteUtilizadas = horasDeSoporteUtilizadas;
        this.inicioPorAnticipo = inicioPorAnticipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFechaDeSuscripcion() {
        return fechaDeSuscripcion;
    }

    public void setFechaDeSuscripcion(Date fechaDeSuscripcion) {
        this.fechaDeSuscripcion = fechaDeSuscripcion;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public int getTiempoDeValidez() {
        return tiempoDeValidez;
    }

    public void setTiempoDeValidez(int tiempoDeValidez) {
        this.tiempoDeValidez = tiempoDeValidez;
    }

    public Integer getGarantiaTecnica() {
        return garantiaTecnica;
    }

    public void setGarantiaTecnica(Integer garantiaTecnica) {
        this.garantiaTecnica = garantiaTecnica;
    }

    public Date getFechaInicioGarantiaTecnica() {
        return fechaInicioGarantiaTecnica;
    }

    public void setFechaInicioGarantiaTecnica(Date fechaInicioGarantiaTecnica) {
        this.fechaInicioGarantiaTecnica = fechaInicioGarantiaTecnica;
    }

    public int getServicioSoporteMantenimiento() {
        return servicioSoporteMantenimiento;
    }

    public void setServicioSoporteMantenimiento(int servicioSoporteMantenimiento) {
        this.servicioSoporteMantenimiento = servicioSoporteMantenimiento;
    }

    public boolean getIncluyeRepuestos() {
        return incluyeRepuestos;
    }

    public void setIncluyeRepuestos(boolean incluyeRepuestos) {
        this.incluyeRepuestos = incluyeRepuestos;
    }

    public int getHorasDeSoporteAnual() {
        return horasDeSoporteAnual;
    }

    public void setHorasDeSoporteAnual(int horasDeSoporteAnual) {
        this.horasDeSoporteAnual = horasDeSoporteAnual;
    }

    public int getHorasDeSoporteUtilizadas() {
        return horasDeSoporteUtilizadas;
    }

    public void setHorasDeSoporteUtilizadas(int horasDeSoporteUtilizadas) {
        this.horasDeSoporteUtilizadas = horasDeSoporteUtilizadas;
    }

    public Date getFechaDeFacturacion() {
        return fechaDeFacturacion;
    }

    public void setFechaDeFacturacion(Date fechaDeFacturacion) {
        this.fechaDeFacturacion = fechaDeFacturacion;
    }

    public String getNumeroDeFactura() {
        return numeroDeFactura;
    }

    public void setNumeroDeFactura(String numeroDeFactura) {
        this.numeroDeFactura = numeroDeFactura;
    }

    public Boolean getPagosAlDia() {
        return pagosAlDia;
    }

    public void setPagosAlDia(Boolean pagosAlDia) {
        this.pagosAlDia = pagosAlDia;
    }

    public String getRazonDemoraDelPago() {
        return razonDemoraDelPago;
    }

    public void setRazonDemoraDelPago(String razonDemoraDelPago) {
        this.razonDemoraDelPago = razonDemoraDelPago;
    }

    public boolean getInicioPorAnticipo() {
        return inicioPorAnticipo;
    }

    public void setInicioPorAnticipo(boolean inicioPorAnticipo) {
        this.inicioPorAnticipo = inicioPorAnticipo;
    }

    public Integer getPlazoDeEntrega() {
        return plazoDeEntrega;
    }

    public void setPlazoDeEntrega(Integer plazoDeEntrega) {
        this.plazoDeEntrega = plazoDeEntrega;
    }

    public Date getFechaDeEntregaRecepcion() {
        return fechaDeEntregaRecepcion;
    }

    public void setFechaDeEntregaRecepcion(Date fechaDeEntregaRecepcion) {
        this.fechaDeEntregaRecepcion = fechaDeEntregaRecepcion;
    }

    public String getIdNotificadorFinGarantiaTecnica() {
        return idNotificadorFinGarantiaTecnica;
    }

    public void setIdNotificadorFinGarantiaTecnica(String idNotificadorFinGarantiaTecnica) {
        this.idNotificadorFinGarantiaTecnica = idNotificadorFinGarantiaTecnica;
    }

    public String getIdNotificadorFinDeContrato() {
        return idNotificadorFinDeContrato;
    }

    public void setIdNotificadorFinDeContrato(String idNotificadorFinDeContrato) {
        this.idNotificadorFinDeContrato = idNotificadorFinDeContrato;
    }

    @XmlTransient
    public List<Pago> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        this.pagoList = pagoList;
    }

    public Usuario getAccountManagerAsignado() {
        return accountManagerAsignado;
    }

    public void setAccountManagerAsignado(Usuario accountManagerAsignado) {
        this.accountManagerAsignado = accountManagerAsignado;
    }

    public TipoContrato getTipoContratoid() {
        return tipoContratoid;
    }

    public void setTipoContratoid(TipoContrato tipoContratoid) {
        this.tipoContratoid = tipoContratoid;
    }

    public Sla getSlaid() {
        return slaid;
    }

    public void setSlaid(Sla slaid) {
        this.slaid = slaid;
    }

    public Contacto getAdministradorDeContrato() {
        return administradorDeContrato;
    }

    public void setAdministradorDeContrato(Contacto administradorDeContrato) {
        this.administradorDeContrato = administradorDeContrato;
    }

    public ClienteEmpresa getClienteEmpresaruc() {
        return clienteEmpresaruc;
    }

    public void setClienteEmpresaruc(ClienteEmpresa clienteEmpresaruc) {
        this.clienteEmpresaruc = clienteEmpresaruc;
    }

    @XmlTransient
    public List<Curso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<Curso> cursoList) {
        this.cursoList = cursoList;
    }

    @XmlTransient
    public List<RegistroDeMovimientoDeInventario> getRegistroDeMovimientoDeInventarioList() {
        return registroDeMovimientoDeInventarioList;
    }

    public void setRegistroDeMovimientoDeInventarioList(List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioList) {
        this.registroDeMovimientoDeInventarioList = registroDeMovimientoDeInventarioList;
    }

    @XmlTransient
    public List<GarantiaEconomica> getGarantiaEconomicaList() {
        return garantiaEconomicaList;
    }

    public void setGarantiaEconomicaList(List<GarantiaEconomica> garantiaEconomicaList) {
        this.garantiaEconomicaList = garantiaEconomicaList;
    }

    @XmlTransient
    public List<ItemProducto> getItemProductoList() {
        return itemProductoList;
    }

    public void setItemProductoList(List<ItemProducto> itemProductoList) {
        this.itemProductoList = itemProductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numero != null ? numero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contrato)) {
            return false;
        }
        Contrato other = (Contrato) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.Contrato[ numero=" + numero + " ]";
    }


    @XmlTransient
    public List<ItemProducto> getItemProductoList1() {
        return itemProductoList1;
    }

    public void setItemProductoList1(List<ItemProducto> itemProductoList) {
        this.itemProductoList1 = itemProductoList;
    }

    public byte[] getContratoDigital() {
        return contratoDigital;
    }

    public void setContratoDigital(byte[] contratoDigital) {
        this.contratoDigital = contratoDigital;
    }

    @XmlTransient
    public List<VisitaTecnica> getVisitaTecnicaList() {
        return visitaTecnicaList;
    }

    public void setVisitaTecnicaList(List<VisitaTecnica> visitaTecnicaList) {
        this.visitaTecnicaList = visitaTecnicaList;
    }
    
}
