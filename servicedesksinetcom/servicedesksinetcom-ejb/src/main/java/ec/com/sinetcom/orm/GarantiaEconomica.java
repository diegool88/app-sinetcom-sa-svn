/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "GarantiaEconomica", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GarantiaEconomica.findAll", query = "SELECT g FROM GarantiaEconomica g")})
public class GarantiaEconomica implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "renovacion")
    private boolean renovacion;
    @Lob
    @Column(name = "poliza")
    private byte[] poliza;
    @Size(max = 50)
    @Column(name = "numeroPoliza")
    private String numeroPoliza;
    @Basic(optional = false)
    @NotNull()
    @Column(name = "porcentaje")
    private int porcentaje;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private BigDecimal valor;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaFin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Size(max = 45)
    @Column(name = "idNotificador")
    private String idNotificador;
    @JoinColumn(name = "TipoGarantia_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoGarantia tipoGarantiaid;
    @JoinColumn(name = "Contrato_numero", referencedColumnName = "numero")
    @ManyToOne(optional = false)
    private Contrato contratonumero;

    public GarantiaEconomica() {
    }

    public GarantiaEconomica(Integer id) {
        this.id = id;
    }

    public GarantiaEconomica(Integer id, int porcentaje, BigDecimal valor, boolean renovacion, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.porcentaje = porcentaje;
        this.valor = valor;
        this.renovacion = renovacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getIdNotificador() {
        return idNotificador;
    }

    public void setIdNotificador(String idNotificador) {
        this.idNotificador = idNotificador;
    }

    public TipoGarantia getTipoGarantiaid() {
        return tipoGarantiaid;
    }

    public void setTipoGarantiaid(TipoGarantia tipoGarantiaid) {
        this.tipoGarantiaid = tipoGarantiaid;
    }

    public Contrato getContratonumero() {
        return contratonumero;
    }

    public void setContratonumero(Contrato contratonumero) {
        this.contratonumero = contratonumero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GarantiaEconomica)) {
            return false;
        }
        GarantiaEconomica other = (GarantiaEconomica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.GarantiaEconomica[ id=" + id + " ]";
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean getRenovacion() {
        return renovacion;
    }

    public void setRenovacion(boolean renovacion) {
        this.renovacion = renovacion;
    }

    public byte[] getPoliza() {
        return poliza;
    }

    public void setPoliza(byte[] poliza) {
        this.poliza = poliza;
    }

    public String getNumeroPoliza() {
        return numeroPoliza;
    }

    public void setNumeroPoliza(String numeroPoliza) {
        this.numeroPoliza = numeroPoliza;
    }
    
}
