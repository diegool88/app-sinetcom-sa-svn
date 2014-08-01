/*
 * To change this template, choose Tools | Templates
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

/**
 *
 * @author diegoflores
 */
@Entity
@Table(name = "Permisos", catalog = "dbsinetcom", schema = "")
@NamedQueries({
    @NamedQuery(name = "Permisos.findAll", query = "SELECT p FROM Permisos p")})
public class Permisos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Grupo_id")
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
    @JoinColumn(name = "Grupo_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Grupo grupo;

    public Permisos() {
    }

    public Permisos(Integer grupoid) {
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
        if (!(object instanceof Permisos)) {
            return false;
        }
        Permisos other = (Permisos) object;
        if ((this.grupoid == null && other.grupoid != null) || (this.grupoid != null && !this.grupoid.equals(other.grupoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.sinetcom.orm.Permisos[ grupoid=" + grupoid + " ]";
    }
    
}
