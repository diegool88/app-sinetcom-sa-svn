/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "ItemProducto", catalog = "dbsinetcom", schema = "")
@NamedQueries({
    @NamedQuery(name = "ItemProducto.findAll", query = "SELECT i FROM ItemProducto i")})
public class ItemProducto implements Serializable {
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
    @JoinTable(name = "Compatibilidad", joinColumns = {
        @JoinColumn(name = "ItemProducto_numeroSerial", referencedColumnName = "numeroSerial")}, inverseJoinColumns = {
        @JoinColumn(name = "ModeloProducto_id", referencedColumnName = "id")})
    @ManyToMany
    private List<ModeloProducto> modeloProductoList;
    @JoinTable(name = "ItemProductosRegistroMovimiento", joinColumns = {
        @JoinColumn(name = "ItemProducto_numeroSerial", referencedColumnName = "numeroSerial")}, inverseJoinColumns = {
        @JoinColumn(name = "RegistroDeMovimientoDeInventario_codigo", referencedColumnName = "codigo")})
    @ManyToMany
    private List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioList;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemProductonumeroSerial")
    private List<HistorialDeMovimientoDeProducto> historialDeMovimientoDeProductoList;

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

    public List<ModeloProducto> getModeloProductoList() {
        return modeloProductoList;
    }

    public void setModeloProductoList(List<ModeloProducto> modeloProductoList) {
        this.modeloProductoList = modeloProductoList;
    }

    public List<RegistroDeMovimientoDeInventario> getRegistroDeMovimientoDeInventarioList() {
        return registroDeMovimientoDeInventarioList;
    }

    public void setRegistroDeMovimientoDeInventarioList(List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioList) {
        this.registroDeMovimientoDeInventarioList = registroDeMovimientoDeInventarioList;
    }

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

    public List<HistorialDeMovimientoDeProducto> getHistorialDeMovimientoDeProductoList() {
        return historialDeMovimientoDeProductoList;
    }

    public void setHistorialDeMovimientoDeProductoList(List<HistorialDeMovimientoDeProducto> historialDeMovimientoDeProductoList) {
        this.historialDeMovimientoDeProductoList = historialDeMovimientoDeProductoList;
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
    
}
