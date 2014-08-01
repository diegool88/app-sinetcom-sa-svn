/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "Curso", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c")})
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "temaATratar")
    private String temaATratar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroDeParticipantes")
    private int numeroDeParticipantes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroDeHorasTotales")
    private int numeroDeHorasTotales;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oficial")
    private boolean oficial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "nombreInstructor")
    private String nombreInstructor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "centroDeCapacitacion")
    private String centroDeCapacitacion;
    @Column(name = "fechaDeInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeInicio;
    @Size(max = 45)
    @Column(name = "idNotificador")
    private String idNotificador;
    @JoinColumn(name = "intructorSinetcom", referencedColumnName = "id")
    @ManyToOne
    private Usuario intructorSinetcom;
    @JoinColumn(name = "Contrato_numero", referencedColumnName = "numero")
    @ManyToOne(optional = false)
    private Contrato contratonumero;

    public Curso() {
    }

    public Curso(Integer codigo) {
        this.codigo = codigo;
    }

    public Curso(Integer codigo, String temaATratar, int numeroDeParticipantes, int numeroDeHorasTotales, boolean oficial, String nombreInstructor, String centroDeCapacitacion) {
        this.codigo = codigo;
        this.temaATratar = temaATratar;
        this.numeroDeParticipantes = numeroDeParticipantes;
        this.numeroDeHorasTotales = numeroDeHorasTotales;
        this.oficial = oficial;
        this.nombreInstructor = nombreInstructor;
        this.centroDeCapacitacion = centroDeCapacitacion;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getTemaATratar() {
        return temaATratar;
    }

    public void setTemaATratar(String temaATratar) {
        this.temaATratar = temaATratar;
    }

    public int getNumeroDeParticipantes() {
        return numeroDeParticipantes;
    }

    public void setNumeroDeParticipantes(int numeroDeParticipantes) {
        this.numeroDeParticipantes = numeroDeParticipantes;
    }

    public int getNumeroDeHorasTotales() {
        return numeroDeHorasTotales;
    }

    public void setNumeroDeHorasTotales(int numeroDeHorasTotales) {
        this.numeroDeHorasTotales = numeroDeHorasTotales;
    }

    public boolean getOficial() {
        return oficial;
    }

    public void setOficial(boolean oficial) {
        this.oficial = oficial;
    }

    public String getNombreInstructor() {
        return nombreInstructor;
    }

    public void setNombreInstructor(String nombreInstructor) {
        this.nombreInstructor = nombreInstructor;
    }

    public String getCentroDeCapacitacion() {
        return centroDeCapacitacion;
    }

    public void setCentroDeCapacitacion(String centroDeCapacitacion) {
        this.centroDeCapacitacion = centroDeCapacitacion;
    }

    public Date getFechaDeInicio() {
        return fechaDeInicio;
    }

    public void setFechaDeInicio(Date fechaDeInicio) {
        this.fechaDeInicio = fechaDeInicio;
    }

    public String getIdNotificador() {
        return idNotificador;
    }

    public void setIdNotificador(String idNotificador) {
        this.idNotificador = idNotificador;
    }

    public Usuario getIntructorSinetcom() {
        return intructorSinetcom;
    }

    public void setIntructorSinetcom(Usuario intructorSinetcom) {
        this.intructorSinetcom = intructorSinetcom;
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
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.Curso[ codigo=" + codigo + " ]";
    }
    
}
