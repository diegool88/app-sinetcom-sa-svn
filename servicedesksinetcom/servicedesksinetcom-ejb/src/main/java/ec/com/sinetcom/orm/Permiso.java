/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.orm;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "Permiso", catalog = "dbsinetcom", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permiso.findAll", query = "SELECT p FROM Permiso p"),
    @NamedQuery(name = "Permiso.findByGrupoid", query = "SELECT p FROM Permiso p WHERE p.grupoid = :grupoid"),
    @NamedQuery(name = "Permiso.findByAdministra", query = "SELECT p FROM Permiso p WHERE p.administra = :administra"),
    @NamedQuery(name = "Permiso.findByRegistraContratos", query = "SELECT p FROM Permiso p WHERE p.registraContratos = :registraContratos"),
    @NamedQuery(name = "Permiso.findByConsultaContratos", query = "SELECT p FROM Permiso p WHERE p.consultaContratos = :consultaContratos"),
    @NamedQuery(name = "Permiso.findByGeneraReportesDeContratos", query = "SELECT p FROM Permiso p WHERE p.generaReportesDeContratos = :generaReportesDeContratos"),
    @NamedQuery(name = "Permiso.findByCreaTicketDeSoporte", query = "SELECT p FROM Permiso p WHERE p.creaTicketDeSoporte = :creaTicketDeSoporte"),
    @NamedQuery(name = "Permiso.findByBloqueaDesbloqueaDeCliente", query = "SELECT p FROM Permiso p WHERE p.bloqueaDesbloqueaDeCliente = :bloqueaDesbloqueaDeCliente"),
    @NamedQuery(name = "Permiso.findByAsignaTickets", query = "SELECT p FROM Permiso p WHERE p.asignaTickets = :asignaTickets"),
    @NamedQuery(name = "Permiso.findByAdministraTicketsPropietarios", query = "SELECT p FROM Permiso p WHERE p.administraTicketsPropietarios = :administraTicketsPropietarios"),
    @NamedQuery(name = "Permiso.findByConsultaTodosLosTickets", query = "SELECT p FROM Permiso p WHERE p.consultaTodosLosTickets = :consultaTodosLosTickets"),
    @NamedQuery(name = "Permiso.findByConsultaTicketsPropietarios", query = "SELECT p FROM Permiso p WHERE p.consultaTicketsPropietarios = :consultaTicketsPropietarios"),
    @NamedQuery(name = "Permiso.findByGeneraReportesDeTicketsDeSoporte", query = "SELECT p FROM Permiso p WHERE p.generaReportesDeTicketsDeSoporte = :generaReportesDeTicketsDeSoporte"),
    @NamedQuery(name = "Permiso.findByIngresaInventarioAlSistema", query = "SELECT p FROM Permiso p WHERE p.ingresaInventarioAlSistema = :ingresaInventarioAlSistema"),
    @NamedQuery(name = "Permiso.findByAdministraRegistroDeMovimientoDeInventario", query = "SELECT p FROM Permiso p WHERE p.administraRegistroDeMovimientoDeInventario = :administraRegistroDeMovimientoDeInventario"),
    @NamedQuery(name = "Permiso.findByBuscaInventario", query = "SELECT p FROM Permiso p WHERE p.buscaInventario = :buscaInventario"),
    @NamedQuery(name = "Permiso.findByGeneraActaDeEntregaRecepcion", query = "SELECT p FROM Permiso p WHERE p.generaActaDeEntregaRecepcion = :generaActaDeEntregaRecepcion"),
    @NamedQuery(name = "Permiso.findByGeneraReportesDeInventarios", query = "SELECT p FROM Permiso p WHERE p.generaReportesDeInventarios = :generaReportesDeInventarios"),
    @NamedQuery(name = "Permiso.findByAdministraProductos", query = "SELECT p FROM Permiso p WHERE p.administraProductos = :administraProductos"),
    @NamedQuery(name = "Permiso.findByAdministraPerfilesDeUsuario", query = "SELECT p FROM Permiso p WHERE p.administraPerfilesDeUsuario = :administraPerfilesDeUsuario"),
    @NamedQuery(name = "Permiso.findByAdministraMarcasFabricantes", query = "SELECT p FROM Permiso p WHERE p.administraMarcasFabricantes = :administraMarcasFabricantes"),
    @NamedQuery(name = "Permiso.findByAdministraCategoriasDeProductoPorFabricante", query = "SELECT p FROM Permiso p WHERE p.administraCategoriasDeProductoPorFabricante = :administraCategoriasDeProductoPorFabricante"),
    @NamedQuery(name = "Permiso.findByAdministraCategoriasDeProducto", query = "SELECT p FROM Permiso p WHERE p.administraCategoriasDeProducto = :administraCategoriasDeProducto"),
    @NamedQuery(name = "Permiso.findByAdministraModelosRevisiones", query = "SELECT p FROM Permiso p WHERE p.administraModelosRevisiones = :administraModelosRevisiones"),
    @NamedQuery(name = "Permiso.findByAdministraClientes", query = "SELECT p FROM Permiso p WHERE p.administraClientes = :administraClientes")})
public class Permiso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Grupo_id", nullable = false)
    private Integer grupoid;
    @Column(name = "administra")
    private Boolean administra;
    @Column(name = "registraContratos")
    private Boolean registraContratos;
    @Column(name = "consultaContratos")
    private Boolean consultaContratos;
    @Column(name = "generaReportesDeContratos")
    private Boolean generaReportesDeContratos;
    @Column(name = "creaTicketDeSoporte")
    private Boolean creaTicketDeSoporte;
    @Column(name = "bloqueaDesbloqueaDeCliente")
    private Boolean bloqueaDesbloqueaDeCliente;
    @Column(name = "asignaTickets")
    private Boolean asignaTickets;
    @Column(name = "administraTicketsPropietarios")
    private Boolean administraTicketsPropietarios;
    @Column(name = "consultaTodosLosTickets")
    private Boolean consultaTodosLosTickets;
    @Column(name = "consultaTicketsPropietarios")
    private Boolean consultaTicketsPropietarios;
    @Column(name = "generaReportesDeTicketsDeSoporte")
    private Boolean generaReportesDeTicketsDeSoporte;
    @Column(name = "ingresaInventarioAlSistema")
    private Boolean ingresaInventarioAlSistema;
    @Column(name = "administraRegistroDeMovimientoDeInventario")
    private Boolean administraRegistroDeMovimientoDeInventario;
    @Column(name = "buscaInventario")
    private Boolean buscaInventario;
    @Column(name = "generaActaDeEntregaRecepcion")
    private Boolean generaActaDeEntregaRecepcion;
    @Column(name = "generaReportesDeInventarios")
    private Boolean generaReportesDeInventarios;
    @Column(name = "administraProductos")
    private Boolean administraProductos;
    @Column(name = "administraPerfilesDeUsuario")
    private Boolean administraPerfilesDeUsuario;
    @Column(name = "administraMarcasFabricantes")
    private Boolean administraMarcasFabricantes;
    @Column(name = "administraCategoriasDeProductoPorFabricante")
    private Boolean administraCategoriasDeProductoPorFabricante;
    @Column(name = "administraCategoriasDeProducto")
    private Boolean administraCategoriasDeProducto;
    @Column(name = "administraModelosRevisiones")
    private Boolean administraModelosRevisiones;
    @Column(name = "administraClientes")
    private Boolean administraClientes;
    @JoinColumn(name = "Grupo_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Grupo grupo;

    public Permiso() {
    }

    public Permiso(Integer grupoid) {
        this.grupoid = grupoid;
    }

    public Integer getGrupoid() {
        return grupoid;
    }

    public void setGrupoid(Integer grupoid) {
        this.grupoid = grupoid;
    }

    public Boolean getAdministra() {
        return administra;
    }

    public void setAdministra(Boolean administra) {
        this.administra = administra;
    }

    public Boolean getRegistraContratos() {
        return registraContratos;
    }

    public void setRegistraContratos(Boolean registraContratos) {
        this.registraContratos = registraContratos;
    }

    public Boolean getConsultaContratos() {
        return consultaContratos;
    }

    public void setConsultaContratos(Boolean consultaContratos) {
        this.consultaContratos = consultaContratos;
    }

    public Boolean getGeneraReportesDeContratos() {
        return generaReportesDeContratos;
    }

    public void setGeneraReportesDeContratos(Boolean generaReportesDeContratos) {
        this.generaReportesDeContratos = generaReportesDeContratos;
    }

    public Boolean getCreaTicketDeSoporte() {
        return creaTicketDeSoporte;
    }

    public void setCreaTicketDeSoporte(Boolean creaTicketDeSoporte) {
        this.creaTicketDeSoporte = creaTicketDeSoporte;
    }

    public Boolean getBloqueaDesbloqueaDeCliente() {
        return bloqueaDesbloqueaDeCliente;
    }

    public void setBloqueaDesbloqueaDeCliente(Boolean bloqueaDesbloqueaDeCliente) {
        this.bloqueaDesbloqueaDeCliente = bloqueaDesbloqueaDeCliente;
    }

    public Boolean getAsignaTickets() {
        return asignaTickets;
    }

    public void setAsignaTickets(Boolean asignaTickets) {
        this.asignaTickets = asignaTickets;
    }

    public Boolean getAdministraTicketsPropietarios() {
        return administraTicketsPropietarios;
    }

    public void setAdministraTicketsPropietarios(Boolean administraTicketsPropietarios) {
        this.administraTicketsPropietarios = administraTicketsPropietarios;
    }

    public Boolean getConsultaTodosLosTickets() {
        return consultaTodosLosTickets;
    }

    public void setConsultaTodosLosTickets(Boolean consultaTodosLosTickets) {
        this.consultaTodosLosTickets = consultaTodosLosTickets;
    }

    public Boolean getConsultaTicketsPropietarios() {
        return consultaTicketsPropietarios;
    }

    public void setConsultaTicketsPropietarios(Boolean consultaTicketsPropietarios) {
        this.consultaTicketsPropietarios = consultaTicketsPropietarios;
    }

    public Boolean getGeneraReportesDeTicketsDeSoporte() {
        return generaReportesDeTicketsDeSoporte;
    }

    public void setGeneraReportesDeTicketsDeSoporte(Boolean generaReportesDeTicketsDeSoporte) {
        this.generaReportesDeTicketsDeSoporte = generaReportesDeTicketsDeSoporte;
    }

    public Boolean getIngresaInventarioAlSistema() {
        return ingresaInventarioAlSistema;
    }

    public void setIngresaInventarioAlSistema(Boolean ingresaInventarioAlSistema) {
        this.ingresaInventarioAlSistema = ingresaInventarioAlSistema;
    }

    public Boolean getAdministraRegistroDeMovimientoDeInventario() {
        return administraRegistroDeMovimientoDeInventario;
    }

    public void setAdministraRegistroDeMovimientoDeInventario(Boolean administraRegistroDeMovimientoDeInventario) {
        this.administraRegistroDeMovimientoDeInventario = administraRegistroDeMovimientoDeInventario;
    }

    public Boolean getBuscaInventario() {
        return buscaInventario;
    }

    public void setBuscaInventario(Boolean buscaInventario) {
        this.buscaInventario = buscaInventario;
    }

    public Boolean getGeneraActaDeEntregaRecepcion() {
        return generaActaDeEntregaRecepcion;
    }

    public void setGeneraActaDeEntregaRecepcion(Boolean generaActaDeEntregaRecepcion) {
        this.generaActaDeEntregaRecepcion = generaActaDeEntregaRecepcion;
    }

    public Boolean getGeneraReportesDeInventarios() {
        return generaReportesDeInventarios;
    }

    public void setGeneraReportesDeInventarios(Boolean generaReportesDeInventarios) {
        this.generaReportesDeInventarios = generaReportesDeInventarios;
    }

    public Boolean getAdministraProductos() {
        return administraProductos;
    }

    public void setAdministraProductos(Boolean administraProductos) {
        this.administraProductos = administraProductos;
    }

    public Boolean getAdministraPerfilesDeUsuario() {
        return administraPerfilesDeUsuario;
    }

    public void setAdministraPerfilesDeUsuario(Boolean administraPerfilesDeUsuario) {
        this.administraPerfilesDeUsuario = administraPerfilesDeUsuario;
    }

    public Boolean getAdministraMarcasFabricantes() {
        return administraMarcasFabricantes;
    }

    public void setAdministraMarcasFabricantes(Boolean administraMarcasFabricantes) {
        this.administraMarcasFabricantes = administraMarcasFabricantes;
    }

    public Boolean getAdministraCategoriasDeProductoPorFabricante() {
        return administraCategoriasDeProductoPorFabricante;
    }

    public void setAdministraCategoriasDeProductoPorFabricante(Boolean administraCategoriasDeProductoPorFabricante) {
        this.administraCategoriasDeProductoPorFabricante = administraCategoriasDeProductoPorFabricante;
    }

    public Boolean getAdministraCategoriasDeProducto() {
        return administraCategoriasDeProducto;
    }

    public void setAdministraCategoriasDeProducto(Boolean administraCategoriasDeProducto) {
        this.administraCategoriasDeProducto = administraCategoriasDeProducto;
    }

    public Boolean getAdministraModelosRevisiones() {
        return administraModelosRevisiones;
    }

    public void setAdministraModelosRevisiones(Boolean administraModelosRevisiones) {
        this.administraModelosRevisiones = administraModelosRevisiones;
    }

    public Boolean getAdministraClientes() {
        return administraClientes;
    }

    public void setAdministraClientes(Boolean administraClientes) {
        this.administraClientes = administraClientes;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoid != null ? grupoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permiso)) {
            return false;
        }
        Permiso other = (Permiso) object;
        if ((this.grupoid == null && other.grupoid != null) || (this.grupoid != null && !this.grupoid.equals(other.grupoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.Permiso[ grupoid=" + grupoid + " ]";
    }
    
}
