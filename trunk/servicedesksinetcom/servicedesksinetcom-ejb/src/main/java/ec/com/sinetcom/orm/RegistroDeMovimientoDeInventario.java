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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "RegistroDeMovimientoDeInventario", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroDeMovimientoDeInventario.findAll", query = "SELECT r FROM RegistroDeMovimientoDeInventario r")})
public class RegistroDeMovimientoDeInventario implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "registroDeMovimientoDeInventariocodigo", fetch = FetchType.EAGER)
    private List<DetalleDeMovimientoDeProducto> detalleDeMovimientoDeProductoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "detalleMotivoDelMovimiento")
    private String detalleMotivoDelMovimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaDeEmision")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeEmision;
    @Column(name = "fechaDeEntrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeEntrada;
    @Column(name = "fechaDeSalida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeSalida;
    @Size(max = 45)
    @Column(name = "numeroDeFactura")
    private String numeroDeFactura;
//    @ManyToMany(mappedBy = "registroDeMovimientoDeInventarioList")
//    @JoinTable(name = "ItemProductosRegistroMovimiento", joinColumns = {
//        @JoinColumn(name = "RegistroDeMovimientoDeInventario_codigo", referencedColumnName = "codigo")}, inverseJoinColumns = {
//        @JoinColumn(name = "ItemProducto_numeroSerial", referencedColumnName = "numeroSerial")})
//    @ManyToMany
//    private List<ItemProducto> itemProductoList;
    @JoinColumn(name = "TipoDeMovimiento_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoDeMovimiento tipoDeMovimientoid;
    @JoinColumn(name = "Contrato_numero", referencedColumnName = "numero")
    @ManyToOne
    private Contrato contratonumero;
    @JoinColumn(name = "ClienteEmpresa_ruc", referencedColumnName = "ruc")
    @ManyToOne
    private ClienteEmpresa clienteEmpresaruc;

    public RegistroDeMovimientoDeInventario() {
    }

    public RegistroDeMovimientoDeInventario(Integer codigo) {
        this.codigo = codigo;
    }

    public RegistroDeMovimientoDeInventario(Integer codigo, String detalleMotivoDelMovimiento, Date fechaDeEmision) {
        this.codigo = codigo;
        this.detalleMotivoDelMovimiento = detalleMotivoDelMovimiento;
        this.fechaDeEmision = fechaDeEmision;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDetalleMotivoDelMovimiento() {
        return detalleMotivoDelMovimiento;
    }

    public void setDetalleMotivoDelMovimiento(String detalleMotivoDelMovimiento) {
        this.detalleMotivoDelMovimiento = detalleMotivoDelMovimiento;
    }

    public Date getFechaDeEmision() {
        return fechaDeEmision;
    }

    public void setFechaDeEmision(Date fechaDeEmision) {
        this.fechaDeEmision = fechaDeEmision;
    }

    public Date getFechaDeEntrada() {
        return fechaDeEntrada;
    }

    public void setFechaDeEntrada(Date fechaDeEntrada) {
        this.fechaDeEntrada = fechaDeEntrada;
    }

    public Date getFechaDeSalida() {
        return fechaDeSalida;
    }

    public void setFechaDeSalida(Date fechaDeSalida) {
        this.fechaDeSalida = fechaDeSalida;
    }

    public String getNumeroDeFactura() {
        return numeroDeFactura;
    }

    public void setNumeroDeFactura(String numeroDeFactura) {
        this.numeroDeFactura = numeroDeFactura;
    }

//    @XmlTransient
//    public List<ItemProducto> getItemProductoList() {
//        return itemProductoList;
//    }
//
//    public void setItemProductoList(List<ItemProducto> itemProductoList) {
//        this.itemProductoList = itemProductoList;
//    }

    public TipoDeMovimiento getTipoDeMovimientoid() {
        return tipoDeMovimientoid;
    }

    public void setTipoDeMovimientoid(TipoDeMovimiento tipoDeMovimientoid) {
        this.tipoDeMovimientoid = tipoDeMovimientoid;
    }

    public Contrato getContratonumero() {
        return contratonumero;
    }

    public void setContratonumero(Contrato contratonumero) {
        this.contratonumero = contratonumero;
    }

    public ClienteEmpresa getClienteEmpresaruc() {
        return clienteEmpresaruc;
    }

    public void setClienteEmpresaruc(ClienteEmpresa clienteEmpresaruc) {
        this.clienteEmpresaruc = clienteEmpresaruc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroDeMovimientoDeInventario)) {
            return false;
        }
        RegistroDeMovimientoDeInventario other = (RegistroDeMovimientoDeInventario) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.RegistroDeMovimientoDeInventario[ codigo=" + codigo + " ]";
    }

    @XmlTransient
    public List<DetalleDeMovimientoDeProducto> getDetalleDeMovimientoDeProductoList() {
        return detalleDeMovimientoDeProductoList;
    }

    public void setDetalleDeMovimientoDeProductoList(List<DetalleDeMovimientoDeProducto> detalleDeMovimientoDeProductoList) {
        this.detalleDeMovimientoDeProductoList = detalleDeMovimientoDeProductoList;
    }
    
}
