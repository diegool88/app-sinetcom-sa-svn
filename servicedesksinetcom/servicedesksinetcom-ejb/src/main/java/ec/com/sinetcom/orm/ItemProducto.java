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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "ItemProducto", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemProducto.findAll", query = "SELECT i FROM ItemProducto i")})
@Cache(isolation = CacheIsolationType.ISOLATED)
public class ItemProducto implements Serializable, Cloneable {
    @Column(name = "fechaIngresoABodega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngresoABodega;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fobUnitario")
    private BigDecimal fobUnitario;
    @Column(name = "indice")
    private BigDecimal indice;
    @Column(name = "costeo")
    private BigDecimal costeo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemProductonumeroSerial")
    private List<HistorialDeContratosYEquipos> historialDeContratosYEquiposList;
    @ManyToMany(mappedBy = "itemProductoList1")
    private List<Contrato> contratoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemProductonumeroSerial")
    private List<Ticket> ticketList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "numeroSerial")
    private String numeroSerial;
    @Size(max = 45)
    @Column(name = "numeroDeParte")
    private String numeroDeParte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaDeCompra")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeCompra;
    @Size(max = 45)
    @Column(name = "numeroDeFactura")
    private String numeroDeFactura;
    @Size(max = 45)
    @Column(name = "numeroDePedido")
    private String numeroDePedido;
    @Size(max = 300)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinTable(name = "Compatibilidad", joinColumns = {
        @JoinColumn(name = "ItemProducto_numeroSerial", referencedColumnName = "numeroSerial")}, inverseJoinColumns = {
        @JoinColumn(name = "ModeloProducto_id", referencedColumnName = "id")})
    @ManyToMany
    private List<ModeloProducto> modeloProductoList;
//    @JoinTable(name = "ItemProductosRegistroMovimiento", joinColumns = {
//        @JoinColumn(name = "ItemProducto_numeroSerial", referencedColumnName = "numeroSerial")}, inverseJoinColumns = {
//        @JoinColumn(name = "RegistroDeMovimientoDeInventario_codigo", referencedColumnName = "codigo")})
//    @ManyToMany
//    @ManyToMany(mappedBy = "itemProductoList")
//    private List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemProducto")
    private List<AtributoItemProducto> atributoItemProductoList;
    @JoinColumn(name = "ModeloProducto_id", referencedColumnName = "id")
    @ManyToOne
    private ModeloProducto modeloProductoid;
    @OneToMany(mappedBy = "itemProductonumeroSerialpadre")
    private List<ItemProducto> itemProductoList;
    @JoinColumn(name = "ItemProducto_numeroSerial_padre", referencedColumnName = "numeroSerial")
    @ManyToOne
    private ItemProducto itemProductonumeroSerialpadre;
    @JoinColumn(name = "Contrato_numero", referencedColumnName = "numero")
    @ManyToOne
    private Contrato contratonumero;
    @JoinColumn(name = "CondicionFisica_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CondicionFisica condicionFisicaid;
    @JoinColumn(name = "ComponenteElectronicoAtomico_id", referencedColumnName = "id")
    @ManyToOne
    private ComponenteElectronicoAtomico componenteElectronicoAtomicoid;
    @JoinColumn(name = "Bodega_id", referencedColumnName = "id")
    @ManyToOne
    private Bodega bodegaid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemProductonumeroSerialentra")
    private List<DetalleDeMovimientoDeProducto> DetalleDeMovimientoDeProductoEntraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemProductonumeroSerialsale")
    private List<DetalleDeMovimientoDeProducto> DetalleDeMovimientoDeProductoSaleList;

    public ItemProducto() {
    }

    public ItemProducto(String numeroSerial) {
        this.numeroSerial = numeroSerial;
    }

    public ItemProducto(String numeroSerial, Date fechaDeCompra) {
        this.numeroSerial = numeroSerial;
        this.fechaDeCompra = fechaDeCompra;
    }

    public String getNumeroSerial() {
        return numeroSerial;
    }

    public void setNumeroSerial(String numeroSerial) {
        this.numeroSerial = numeroSerial;
    }

    public String getNumeroDeParte() {
        return numeroDeParte;
    }

    public void setNumeroDeParte(String numeroDeParte) {
        this.numeroDeParte = numeroDeParte;
    }

    public Date getFechaDeCompra() {
        return fechaDeCompra;
    }

    public void setFechaDeCompra(Date fechaDeCompra) {
        this.fechaDeCompra = fechaDeCompra;
    }

    public String getNumeroDeFactura() {
        return numeroDeFactura;
    }

    public void setNumeroDeFactura(String numeroDeFactura) {
        this.numeroDeFactura = numeroDeFactura;
    }

    public String getNumeroDePedido() {
        return numeroDePedido;
    }

    public void setNumeroDePedido(String numeroDePedido) {
        this.numeroDePedido = numeroDePedido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<ModeloProducto> getModeloProductoList() {
        return modeloProductoList;
    }

    public void setModeloProductoList(List<ModeloProducto> modeloProductoList) {
        this.modeloProductoList = modeloProductoList;
    }

//    @XmlTransient
//    public List<RegistroDeMovimientoDeInventario> getRegistroDeMovimientoDeInventarioList() {
//        return registroDeMovimientoDeInventarioList;
//    }
//
//    public void setRegistroDeMovimientoDeInventarioList(List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioList) {
//        this.registroDeMovimientoDeInventarioList = registroDeMovimientoDeInventarioList;
//    }

    @XmlTransient
    public List<AtributoItemProducto> getAtributoItemProductoList() {
        return atributoItemProductoList;
    }

    public void setAtributoItemProductoList(List<AtributoItemProducto> atributoItemProductoList) {
        this.atributoItemProductoList = atributoItemProductoList;
    }

    public ModeloProducto getModeloProductoid() {
        return modeloProductoid;
    }

    public void setModeloProductoid(ModeloProducto modeloProductoid) {
        this.modeloProductoid = modeloProductoid;
    }

    @XmlTransient
    public List<ItemProducto> getItemProductoList() {
        return itemProductoList;
    }

    public void setItemProductoList(List<ItemProducto> itemProductoList) {
        this.itemProductoList = itemProductoList;
    }

    public ItemProducto getItemProductonumeroSerialpadre() {
        return itemProductonumeroSerialpadre;
    }

    public void setItemProductonumeroSerialpadre(ItemProducto itemProductonumeroSerialpadre) {
        this.itemProductonumeroSerialpadre = itemProductonumeroSerialpadre;
    }

    public Contrato getContratonumero() {
        return contratonumero;
    }

    public void setContratonumero(Contrato contratonumero) {
        this.contratonumero = contratonumero;
    }

    public CondicionFisica getCondicionFisicaid() {
        return condicionFisicaid;
    }

    public void setCondicionFisicaid(CondicionFisica condicionFisicaid) {
        this.condicionFisicaid = condicionFisicaid;
    }

    public ComponenteElectronicoAtomico getComponenteElectronicoAtomicoid() {
        return componenteElectronicoAtomicoid;
    }

    public void setComponenteElectronicoAtomicoid(ComponenteElectronicoAtomico componenteElectronicoAtomicoid) {
        this.componenteElectronicoAtomicoid = componenteElectronicoAtomicoid;
    }

    public Bodega getBodegaid() {
        return bodegaid;
    }

    public void setBodegaid(Bodega bodegaid) {
        this.bodegaid = bodegaid;
    }

    @XmlTransient
    public List<DetalleDeMovimientoDeProducto> getDetalleDeMovimientoDeProductoEntraList() {
        return DetalleDeMovimientoDeProductoEntraList;
    }

    public void setDetalleDeMovimientoDeProductoEntraList(List<DetalleDeMovimientoDeProducto> DetalleDeMovimientoDeProductoEntraList) {
        this.DetalleDeMovimientoDeProductoEntraList = DetalleDeMovimientoDeProductoEntraList;
    }
    
    @XmlTransient
    public List<DetalleDeMovimientoDeProducto> getDetalleDeMovimientoDeProductoSaleList() {
        return DetalleDeMovimientoDeProductoSaleList;
    }

    public void setDetalleDeMovimientoDeProductoSaleList(List<DetalleDeMovimientoDeProducto> DetalleDeMovimientoDeProductoSaleList) {
        this.DetalleDeMovimientoDeProductoSaleList = DetalleDeMovimientoDeProductoSaleList;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroSerial != null ? numeroSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemProducto)) {
            return false;
        }
        ItemProducto other = (ItemProducto) object;
        if ((this.numeroSerial == null && other.numeroSerial != null) || (this.numeroSerial != null && !this.numeroSerial.equals(other.numeroSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.ItemProducto[ numeroSerial=" + numeroSerial + " ]";
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    @XmlTransient
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @XmlTransient
    public List<Contrato> getContratoList() {
        return contratoList;
    }

    public void setContratoList(List<Contrato> contratoList) {
        this.contratoList = contratoList;
    }
    
    public String getDescripcionDelComponente(){
        StringBuilder resultado = new StringBuilder();
        if(this.componenteElectronicoAtomicoid != null){
            resultado.append(this.componenteElectronicoAtomicoid.getNombre()).append(" de");
            for(AtributoItemProducto atributo : this.atributoItemProductoList){
                resultado.append(" ").append(atributo.getParametroDeProducto().getNombre()).append(": ").append(atributo.getValor()).append(" ").append(atributo.getParametroDeProducto().getUnidadMedidaid().getNombre());
                if(this.atributoItemProductoList.indexOf(atributo) < this.atributoItemProductoList.size() -1){
                    resultado.append(",");
                }
            }
            return resultado.toString();
        }else{
            return "No disponible";
        }
    }

    @XmlTransient
    public List<HistorialDeContratosYEquipos> getHistorialDeContratosYEquiposList() {
        return historialDeContratosYEquiposList;
    }

    public void setHistorialDeContratosYEquiposList(List<HistorialDeContratosYEquipos> historialDeContratosYEquiposList) {
        this.historialDeContratosYEquiposList = historialDeContratosYEquiposList;
    }

    public BigDecimal getFobUnitario() {
        return fobUnitario;
    }

    public void setFobUnitario(BigDecimal fobUnitario) {
        this.fobUnitario = fobUnitario;
    }

    public BigDecimal getIndice() {
        return indice;
    }

    public void setIndice(BigDecimal indice) {
        this.indice = indice;
    }

    public BigDecimal getCosteo() {
        return costeo;
    }

    public void setCosteo(BigDecimal costeo) {
        this.costeo = costeo;
    }

    public Date getFechaIngresoABodega() {
        return fechaIngresoABodega;
    }

    public void setFechaIngresoABodega(Date fechaIngresoABodega) {
        this.fechaIngresoABodega = fechaIngresoABodega;
    }
    
}
