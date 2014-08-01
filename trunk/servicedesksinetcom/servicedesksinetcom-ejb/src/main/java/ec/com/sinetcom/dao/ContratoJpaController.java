/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.IllegalOrphanException;
import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.PreexistingEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.orm.TipoContrato;
import ec.com.sinetcom.orm.Sla;
import ec.com.sinetcom.orm.Contacto;
import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.Pago;
import java.util.ArrayList;
import java.util.List;
import ec.com.sinetcom.orm.Curso;
import ec.com.sinetcom.orm.VisitasMantenimiento;
import ec.com.sinetcom.orm.RegistroDeMovimientoDeInventario;
import ec.com.sinetcom.orm.GarantiaEconomica;
import ec.com.sinetcom.orm.ItemProducto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class ContratoJpaController implements Serializable {

    public ContratoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contrato contrato) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (contrato.getPagoList() == null) {
            contrato.setPagoList(new ArrayList<Pago>());
        }
        if (contrato.getCursoList() == null) {
            contrato.setCursoList(new ArrayList<Curso>());
        }
        if (contrato.getVisitasMantenimientoList() == null) {
            contrato.setVisitasMantenimientoList(new ArrayList<VisitasMantenimiento>());
        }
        if (contrato.getRegistroDeMovimientoDeInventarioList() == null) {
            contrato.setRegistroDeMovimientoDeInventarioList(new ArrayList<RegistroDeMovimientoDeInventario>());
        }
        if (contrato.getGarantiaEconomicaList() == null) {
            contrato.setGarantiaEconomicaList(new ArrayList<GarantiaEconomica>());
        }
        if (contrato.getItemProductoList() == null) {
            contrato.setItemProductoList(new ArrayList<ItemProducto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario accountManagerAsignado = contrato.getAccountManagerAsignado();
            if (accountManagerAsignado != null) {
                accountManagerAsignado = em.getReference(accountManagerAsignado.getClass(), accountManagerAsignado.getId());
                contrato.setAccountManagerAsignado(accountManagerAsignado);
            }
            TipoContrato tipoContratoid = contrato.getTipoContratoid();
            if (tipoContratoid != null) {
                tipoContratoid = em.getReference(tipoContratoid.getClass(), tipoContratoid.getId());
                contrato.setTipoContratoid(tipoContratoid);
            }
            Sla slaid = contrato.getSlaid();
            if (slaid != null) {
                slaid = em.getReference(slaid.getClass(), slaid.getId());
                contrato.setSlaid(slaid);
            }
            Contacto administradorDeContrato = contrato.getAdministradorDeContrato();
            if (administradorDeContrato != null) {
                administradorDeContrato = em.getReference(administradorDeContrato.getClass(), administradorDeContrato.getId());
                contrato.setAdministradorDeContrato(administradorDeContrato);
            }
            ClienteEmpresa clienteEmpresaruc = contrato.getClienteEmpresaruc();
            if (clienteEmpresaruc != null) {
                clienteEmpresaruc = em.getReference(clienteEmpresaruc.getClass(), clienteEmpresaruc.getRuc());
                contrato.setClienteEmpresaruc(clienteEmpresaruc);
            }
            List<Pago> attachedPagoList = new ArrayList<Pago>();
            for (Pago pagoListPagoToAttach : contrato.getPagoList()) {
                pagoListPagoToAttach = em.getReference(pagoListPagoToAttach.getClass(), pagoListPagoToAttach.getId());
                attachedPagoList.add(pagoListPagoToAttach);
            }
            contrato.setPagoList(attachedPagoList);
            List<Curso> attachedCursoList = new ArrayList<Curso>();
            for (Curso cursoListCursoToAttach : contrato.getCursoList()) {
                cursoListCursoToAttach = em.getReference(cursoListCursoToAttach.getClass(), cursoListCursoToAttach.getCodigo());
                attachedCursoList.add(cursoListCursoToAttach);
            }
            contrato.setCursoList(attachedCursoList);
            List<VisitasMantenimiento> attachedVisitasMantenimientoList = new ArrayList<VisitasMantenimiento>();
            for (VisitasMantenimiento visitasMantenimientoListVisitasMantenimientoToAttach : contrato.getVisitasMantenimientoList()) {
                visitasMantenimientoListVisitasMantenimientoToAttach = em.getReference(visitasMantenimientoListVisitasMantenimientoToAttach.getClass(), visitasMantenimientoListVisitasMantenimientoToAttach.getId());
                attachedVisitasMantenimientoList.add(visitasMantenimientoListVisitasMantenimientoToAttach);
            }
            contrato.setVisitasMantenimientoList(attachedVisitasMantenimientoList);
            List<RegistroDeMovimientoDeInventario> attachedRegistroDeMovimientoDeInventarioList = new ArrayList<RegistroDeMovimientoDeInventario>();
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach : contrato.getRegistroDeMovimientoDeInventarioList()) {
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach = em.getReference(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach.getClass(), registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach.getCodigo());
                attachedRegistroDeMovimientoDeInventarioList.add(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach);
            }
            contrato.setRegistroDeMovimientoDeInventarioList(attachedRegistroDeMovimientoDeInventarioList);
            List<GarantiaEconomica> attachedGarantiaEconomicaList = new ArrayList<GarantiaEconomica>();
            for (GarantiaEconomica garantiaEconomicaListGarantiaEconomicaToAttach : contrato.getGarantiaEconomicaList()) {
                garantiaEconomicaListGarantiaEconomicaToAttach = em.getReference(garantiaEconomicaListGarantiaEconomicaToAttach.getClass(), garantiaEconomicaListGarantiaEconomicaToAttach.getId());
                attachedGarantiaEconomicaList.add(garantiaEconomicaListGarantiaEconomicaToAttach);
            }
            contrato.setGarantiaEconomicaList(attachedGarantiaEconomicaList);
            List<ItemProducto> attachedItemProductoList = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoListItemProductoToAttach : contrato.getItemProductoList()) {
                itemProductoListItemProductoToAttach = em.getReference(itemProductoListItemProductoToAttach.getClass(), itemProductoListItemProductoToAttach.getNumeroSerial());
                attachedItemProductoList.add(itemProductoListItemProductoToAttach);
            }
            contrato.setItemProductoList(attachedItemProductoList);
            em.persist(contrato);
            if (accountManagerAsignado != null) {
                accountManagerAsignado.getContratoList().add(contrato);
                accountManagerAsignado = em.merge(accountManagerAsignado);
            }
            if (tipoContratoid != null) {
                tipoContratoid.getContratoList().add(contrato);
                tipoContratoid = em.merge(tipoContratoid);
            }
            if (slaid != null) {
                slaid.getContratoList().add(contrato);
                slaid = em.merge(slaid);
            }
            if (administradorDeContrato != null) {
                administradorDeContrato.getContratoList().add(contrato);
                administradorDeContrato = em.merge(administradorDeContrato);
            }
            if (clienteEmpresaruc != null) {
                clienteEmpresaruc.getContratoList().add(contrato);
                clienteEmpresaruc = em.merge(clienteEmpresaruc);
            }
            for (Pago pagoListPago : contrato.getPagoList()) {
                Contrato oldContratonumeroOfPagoListPago = pagoListPago.getContratonumero();
                pagoListPago.setContratonumero(contrato);
                pagoListPago = em.merge(pagoListPago);
                if (oldContratonumeroOfPagoListPago != null) {
                    oldContratonumeroOfPagoListPago.getPagoList().remove(pagoListPago);
                    oldContratonumeroOfPagoListPago = em.merge(oldContratonumeroOfPagoListPago);
                }
            }
            for (Curso cursoListCurso : contrato.getCursoList()) {
                Contrato oldContratonumeroOfCursoListCurso = cursoListCurso.getContratonumero();
                cursoListCurso.setContratonumero(contrato);
                cursoListCurso = em.merge(cursoListCurso);
                if (oldContratonumeroOfCursoListCurso != null) {
                    oldContratonumeroOfCursoListCurso.getCursoList().remove(cursoListCurso);
                    oldContratonumeroOfCursoListCurso = em.merge(oldContratonumeroOfCursoListCurso);
                }
            }
            for (VisitasMantenimiento visitasMantenimientoListVisitasMantenimiento : contrato.getVisitasMantenimientoList()) {
                Contrato oldContratonumeroOfVisitasMantenimientoListVisitasMantenimiento = visitasMantenimientoListVisitasMantenimiento.getContratonumero();
                visitasMantenimientoListVisitasMantenimiento.setContratonumero(contrato);
                visitasMantenimientoListVisitasMantenimiento = em.merge(visitasMantenimientoListVisitasMantenimiento);
                if (oldContratonumeroOfVisitasMantenimientoListVisitasMantenimiento != null) {
                    oldContratonumeroOfVisitasMantenimientoListVisitasMantenimiento.getVisitasMantenimientoList().remove(visitasMantenimientoListVisitasMantenimiento);
                    oldContratonumeroOfVisitasMantenimientoListVisitasMantenimiento = em.merge(oldContratonumeroOfVisitasMantenimientoListVisitasMantenimiento);
                }
            }
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario : contrato.getRegistroDeMovimientoDeInventarioList()) {
                Contrato oldContratonumeroOfRegistroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario = registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario.getContratonumero();
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario.setContratonumero(contrato);
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario = em.merge(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario);
                if (oldContratonumeroOfRegistroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario != null) {
                    oldContratonumeroOfRegistroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario.getRegistroDeMovimientoDeInventarioList().remove(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario);
                    oldContratonumeroOfRegistroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario = em.merge(oldContratonumeroOfRegistroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario);
                }
            }
            for (GarantiaEconomica garantiaEconomicaListGarantiaEconomica : contrato.getGarantiaEconomicaList()) {
                Contrato oldContratonumeroOfGarantiaEconomicaListGarantiaEconomica = garantiaEconomicaListGarantiaEconomica.getContratonumero();
                garantiaEconomicaListGarantiaEconomica.setContratonumero(contrato);
                garantiaEconomicaListGarantiaEconomica = em.merge(garantiaEconomicaListGarantiaEconomica);
                if (oldContratonumeroOfGarantiaEconomicaListGarantiaEconomica != null) {
                    oldContratonumeroOfGarantiaEconomicaListGarantiaEconomica.getGarantiaEconomicaList().remove(garantiaEconomicaListGarantiaEconomica);
                    oldContratonumeroOfGarantiaEconomicaListGarantiaEconomica = em.merge(oldContratonumeroOfGarantiaEconomicaListGarantiaEconomica);
                }
            }
            for (ItemProducto itemProductoListItemProducto : contrato.getItemProductoList()) {
                Contrato oldContratonumeroOfItemProductoListItemProducto = itemProductoListItemProducto.getContratonumero();
                itemProductoListItemProducto.setContratonumero(contrato);
                itemProductoListItemProducto = em.merge(itemProductoListItemProducto);
                if (oldContratonumeroOfItemProductoListItemProducto != null) {
                    oldContratonumeroOfItemProductoListItemProducto.getItemProductoList().remove(itemProductoListItemProducto);
                    oldContratonumeroOfItemProductoListItemProducto = em.merge(oldContratonumeroOfItemProductoListItemProducto);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findContrato(contrato.getNumero()) != null) {
                throw new PreexistingEntityException("Contrato " + contrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contrato contrato) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contrato persistentContrato = em.find(Contrato.class, contrato.getNumero());
            Usuario accountManagerAsignadoOld = persistentContrato.getAccountManagerAsignado();
            Usuario accountManagerAsignadoNew = contrato.getAccountManagerAsignado();
            TipoContrato tipoContratoidOld = persistentContrato.getTipoContratoid();
            TipoContrato tipoContratoidNew = contrato.getTipoContratoid();
            Sla slaidOld = persistentContrato.getSlaid();
            Sla slaidNew = contrato.getSlaid();
            Contacto administradorDeContratoOld = persistentContrato.getAdministradorDeContrato();
            Contacto administradorDeContratoNew = contrato.getAdministradorDeContrato();
            ClienteEmpresa clienteEmpresarucOld = persistentContrato.getClienteEmpresaruc();
            ClienteEmpresa clienteEmpresarucNew = contrato.getClienteEmpresaruc();
            List<Pago> pagoListOld = persistentContrato.getPagoList();
            List<Pago> pagoListNew = contrato.getPagoList();
            List<Curso> cursoListOld = persistentContrato.getCursoList();
            List<Curso> cursoListNew = contrato.getCursoList();
            List<VisitasMantenimiento> visitasMantenimientoListOld = persistentContrato.getVisitasMantenimientoList();
            List<VisitasMantenimiento> visitasMantenimientoListNew = contrato.getVisitasMantenimientoList();
            List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioListOld = persistentContrato.getRegistroDeMovimientoDeInventarioList();
            List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioListNew = contrato.getRegistroDeMovimientoDeInventarioList();
            List<GarantiaEconomica> garantiaEconomicaListOld = persistentContrato.getGarantiaEconomicaList();
            List<GarantiaEconomica> garantiaEconomicaListNew = contrato.getGarantiaEconomicaList();
            List<ItemProducto> itemProductoListOld = persistentContrato.getItemProductoList();
            List<ItemProducto> itemProductoListNew = contrato.getItemProductoList();
            List<String> illegalOrphanMessages = null;
            for (Pago pagoListOldPago : pagoListOld) {
                if (!pagoListNew.contains(pagoListOldPago)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pago " + pagoListOldPago + " since its contratonumero field is not nullable.");
                }
            }
            for (Curso cursoListOldCurso : cursoListOld) {
                if (!cursoListNew.contains(cursoListOldCurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Curso " + cursoListOldCurso + " since its contratonumero field is not nullable.");
                }
            }
            for (VisitasMantenimiento visitasMantenimientoListOldVisitasMantenimiento : visitasMantenimientoListOld) {
                if (!visitasMantenimientoListNew.contains(visitasMantenimientoListOldVisitasMantenimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VisitasMantenimiento " + visitasMantenimientoListOldVisitasMantenimiento + " since its contratonumero field is not nullable.");
                }
            }
            for (GarantiaEconomica garantiaEconomicaListOldGarantiaEconomica : garantiaEconomicaListOld) {
                if (!garantiaEconomicaListNew.contains(garantiaEconomicaListOldGarantiaEconomica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GarantiaEconomica " + garantiaEconomicaListOldGarantiaEconomica + " since its contratonumero field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (accountManagerAsignadoNew != null) {
                accountManagerAsignadoNew = em.getReference(accountManagerAsignadoNew.getClass(), accountManagerAsignadoNew.getId());
                contrato.setAccountManagerAsignado(accountManagerAsignadoNew);
            }
            if (tipoContratoidNew != null) {
                tipoContratoidNew = em.getReference(tipoContratoidNew.getClass(), tipoContratoidNew.getId());
                contrato.setTipoContratoid(tipoContratoidNew);
            }
            if (slaidNew != null) {
                slaidNew = em.getReference(slaidNew.getClass(), slaidNew.getId());
                contrato.setSlaid(slaidNew);
            }
            if (administradorDeContratoNew != null) {
                administradorDeContratoNew = em.getReference(administradorDeContratoNew.getClass(), administradorDeContratoNew.getId());
                contrato.setAdministradorDeContrato(administradorDeContratoNew);
            }
            if (clienteEmpresarucNew != null) {
                clienteEmpresarucNew = em.getReference(clienteEmpresarucNew.getClass(), clienteEmpresarucNew.getRuc());
                contrato.setClienteEmpresaruc(clienteEmpresarucNew);
            }
            List<Pago> attachedPagoListNew = new ArrayList<Pago>();
            for (Pago pagoListNewPagoToAttach : pagoListNew) {
                pagoListNewPagoToAttach = em.getReference(pagoListNewPagoToAttach.getClass(), pagoListNewPagoToAttach.getId());
                attachedPagoListNew.add(pagoListNewPagoToAttach);
            }
            pagoListNew = attachedPagoListNew;
            contrato.setPagoList(pagoListNew);
            List<Curso> attachedCursoListNew = new ArrayList<Curso>();
            for (Curso cursoListNewCursoToAttach : cursoListNew) {
                cursoListNewCursoToAttach = em.getReference(cursoListNewCursoToAttach.getClass(), cursoListNewCursoToAttach.getCodigo());
                attachedCursoListNew.add(cursoListNewCursoToAttach);
            }
            cursoListNew = attachedCursoListNew;
            contrato.setCursoList(cursoListNew);
            List<VisitasMantenimiento> attachedVisitasMantenimientoListNew = new ArrayList<VisitasMantenimiento>();
            for (VisitasMantenimiento visitasMantenimientoListNewVisitasMantenimientoToAttach : visitasMantenimientoListNew) {
                visitasMantenimientoListNewVisitasMantenimientoToAttach = em.getReference(visitasMantenimientoListNewVisitasMantenimientoToAttach.getClass(), visitasMantenimientoListNewVisitasMantenimientoToAttach.getId());
                attachedVisitasMantenimientoListNew.add(visitasMantenimientoListNewVisitasMantenimientoToAttach);
            }
            visitasMantenimientoListNew = attachedVisitasMantenimientoListNew;
            contrato.setVisitasMantenimientoList(visitasMantenimientoListNew);
            List<RegistroDeMovimientoDeInventario> attachedRegistroDeMovimientoDeInventarioListNew = new ArrayList<RegistroDeMovimientoDeInventario>();
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach : registroDeMovimientoDeInventarioListNew) {
                registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach = em.getReference(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach.getClass(), registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach.getCodigo());
                attachedRegistroDeMovimientoDeInventarioListNew.add(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach);
            }
            registroDeMovimientoDeInventarioListNew = attachedRegistroDeMovimientoDeInventarioListNew;
            contrato.setRegistroDeMovimientoDeInventarioList(registroDeMovimientoDeInventarioListNew);
            List<GarantiaEconomica> attachedGarantiaEconomicaListNew = new ArrayList<GarantiaEconomica>();
            for (GarantiaEconomica garantiaEconomicaListNewGarantiaEconomicaToAttach : garantiaEconomicaListNew) {
                garantiaEconomicaListNewGarantiaEconomicaToAttach = em.getReference(garantiaEconomicaListNewGarantiaEconomicaToAttach.getClass(), garantiaEconomicaListNewGarantiaEconomicaToAttach.getId());
                attachedGarantiaEconomicaListNew.add(garantiaEconomicaListNewGarantiaEconomicaToAttach);
            }
            garantiaEconomicaListNew = attachedGarantiaEconomicaListNew;
            contrato.setGarantiaEconomicaList(garantiaEconomicaListNew);
            List<ItemProducto> attachedItemProductoListNew = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoListNewItemProductoToAttach : itemProductoListNew) {
                itemProductoListNewItemProductoToAttach = em.getReference(itemProductoListNewItemProductoToAttach.getClass(), itemProductoListNewItemProductoToAttach.getNumeroSerial());
                attachedItemProductoListNew.add(itemProductoListNewItemProductoToAttach);
            }
            itemProductoListNew = attachedItemProductoListNew;
            contrato.setItemProductoList(itemProductoListNew);
            contrato = em.merge(contrato);
            if (accountManagerAsignadoOld != null && !accountManagerAsignadoOld.equals(accountManagerAsignadoNew)) {
                accountManagerAsignadoOld.getContratoList().remove(contrato);
                accountManagerAsignadoOld = em.merge(accountManagerAsignadoOld);
            }
            if (accountManagerAsignadoNew != null && !accountManagerAsignadoNew.equals(accountManagerAsignadoOld)) {
                accountManagerAsignadoNew.getContratoList().add(contrato);
                accountManagerAsignadoNew = em.merge(accountManagerAsignadoNew);
            }
            if (tipoContratoidOld != null && !tipoContratoidOld.equals(tipoContratoidNew)) {
                tipoContratoidOld.getContratoList().remove(contrato);
                tipoContratoidOld = em.merge(tipoContratoidOld);
            }
            if (tipoContratoidNew != null && !tipoContratoidNew.equals(tipoContratoidOld)) {
                tipoContratoidNew.getContratoList().add(contrato);
                tipoContratoidNew = em.merge(tipoContratoidNew);
            }
            if (slaidOld != null && !slaidOld.equals(slaidNew)) {
                slaidOld.getContratoList().remove(contrato);
                slaidOld = em.merge(slaidOld);
            }
            if (slaidNew != null && !slaidNew.equals(slaidOld)) {
                slaidNew.getContratoList().add(contrato);
                slaidNew = em.merge(slaidNew);
            }
            if (administradorDeContratoOld != null && !administradorDeContratoOld.equals(administradorDeContratoNew)) {
                administradorDeContratoOld.getContratoList().remove(contrato);
                administradorDeContratoOld = em.merge(administradorDeContratoOld);
            }
            if (administradorDeContratoNew != null && !administradorDeContratoNew.equals(administradorDeContratoOld)) {
                administradorDeContratoNew.getContratoList().add(contrato);
                administradorDeContratoNew = em.merge(administradorDeContratoNew);
            }
            if (clienteEmpresarucOld != null && !clienteEmpresarucOld.equals(clienteEmpresarucNew)) {
                clienteEmpresarucOld.getContratoList().remove(contrato);
                clienteEmpresarucOld = em.merge(clienteEmpresarucOld);
            }
            if (clienteEmpresarucNew != null && !clienteEmpresarucNew.equals(clienteEmpresarucOld)) {
                clienteEmpresarucNew.getContratoList().add(contrato);
                clienteEmpresarucNew = em.merge(clienteEmpresarucNew);
            }
            for (Pago pagoListNewPago : pagoListNew) {
                if (!pagoListOld.contains(pagoListNewPago)) {
                    Contrato oldContratonumeroOfPagoListNewPago = pagoListNewPago.getContratonumero();
                    pagoListNewPago.setContratonumero(contrato);
                    pagoListNewPago = em.merge(pagoListNewPago);
                    if (oldContratonumeroOfPagoListNewPago != null && !oldContratonumeroOfPagoListNewPago.equals(contrato)) {
                        oldContratonumeroOfPagoListNewPago.getPagoList().remove(pagoListNewPago);
                        oldContratonumeroOfPagoListNewPago = em.merge(oldContratonumeroOfPagoListNewPago);
                    }
                }
            }
            for (Curso cursoListNewCurso : cursoListNew) {
                if (!cursoListOld.contains(cursoListNewCurso)) {
                    Contrato oldContratonumeroOfCursoListNewCurso = cursoListNewCurso.getContratonumero();
                    cursoListNewCurso.setContratonumero(contrato);
                    cursoListNewCurso = em.merge(cursoListNewCurso);
                    if (oldContratonumeroOfCursoListNewCurso != null && !oldContratonumeroOfCursoListNewCurso.equals(contrato)) {
                        oldContratonumeroOfCursoListNewCurso.getCursoList().remove(cursoListNewCurso);
                        oldContratonumeroOfCursoListNewCurso = em.merge(oldContratonumeroOfCursoListNewCurso);
                    }
                }
            }
            for (VisitasMantenimiento visitasMantenimientoListNewVisitasMantenimiento : visitasMantenimientoListNew) {
                if (!visitasMantenimientoListOld.contains(visitasMantenimientoListNewVisitasMantenimiento)) {
                    Contrato oldContratonumeroOfVisitasMantenimientoListNewVisitasMantenimiento = visitasMantenimientoListNewVisitasMantenimiento.getContratonumero();
                    visitasMantenimientoListNewVisitasMantenimiento.setContratonumero(contrato);
                    visitasMantenimientoListNewVisitasMantenimiento = em.merge(visitasMantenimientoListNewVisitasMantenimiento);
                    if (oldContratonumeroOfVisitasMantenimientoListNewVisitasMantenimiento != null && !oldContratonumeroOfVisitasMantenimientoListNewVisitasMantenimiento.equals(contrato)) {
                        oldContratonumeroOfVisitasMantenimientoListNewVisitasMantenimiento.getVisitasMantenimientoList().remove(visitasMantenimientoListNewVisitasMantenimiento);
                        oldContratonumeroOfVisitasMantenimientoListNewVisitasMantenimiento = em.merge(oldContratonumeroOfVisitasMantenimientoListNewVisitasMantenimiento);
                    }
                }
            }
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario : registroDeMovimientoDeInventarioListOld) {
                if (!registroDeMovimientoDeInventarioListNew.contains(registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario)) {
                    registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario.setContratonumero(null);
                    registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario = em.merge(registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario);
                }
            }
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario : registroDeMovimientoDeInventarioListNew) {
                if (!registroDeMovimientoDeInventarioListOld.contains(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario)) {
                    Contrato oldContratonumeroOfRegistroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario = registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario.getContratonumero();
                    registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario.setContratonumero(contrato);
                    registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario = em.merge(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario);
                    if (oldContratonumeroOfRegistroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario != null && !oldContratonumeroOfRegistroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario.equals(contrato)) {
                        oldContratonumeroOfRegistroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario.getRegistroDeMovimientoDeInventarioList().remove(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario);
                        oldContratonumeroOfRegistroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario = em.merge(oldContratonumeroOfRegistroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario);
                    }
                }
            }
            for (GarantiaEconomica garantiaEconomicaListNewGarantiaEconomica : garantiaEconomicaListNew) {
                if (!garantiaEconomicaListOld.contains(garantiaEconomicaListNewGarantiaEconomica)) {
                    Contrato oldContratonumeroOfGarantiaEconomicaListNewGarantiaEconomica = garantiaEconomicaListNewGarantiaEconomica.getContratonumero();
                    garantiaEconomicaListNewGarantiaEconomica.setContratonumero(contrato);
                    garantiaEconomicaListNewGarantiaEconomica = em.merge(garantiaEconomicaListNewGarantiaEconomica);
                    if (oldContratonumeroOfGarantiaEconomicaListNewGarantiaEconomica != null && !oldContratonumeroOfGarantiaEconomicaListNewGarantiaEconomica.equals(contrato)) {
                        oldContratonumeroOfGarantiaEconomicaListNewGarantiaEconomica.getGarantiaEconomicaList().remove(garantiaEconomicaListNewGarantiaEconomica);
                        oldContratonumeroOfGarantiaEconomicaListNewGarantiaEconomica = em.merge(oldContratonumeroOfGarantiaEconomicaListNewGarantiaEconomica);
                    }
                }
            }
            for (ItemProducto itemProductoListOldItemProducto : itemProductoListOld) {
                if (!itemProductoListNew.contains(itemProductoListOldItemProducto)) {
                    itemProductoListOldItemProducto.setContratonumero(null);
                    itemProductoListOldItemProducto = em.merge(itemProductoListOldItemProducto);
                }
            }
            for (ItemProducto itemProductoListNewItemProducto : itemProductoListNew) {
                if (!itemProductoListOld.contains(itemProductoListNewItemProducto)) {
                    Contrato oldContratonumeroOfItemProductoListNewItemProducto = itemProductoListNewItemProducto.getContratonumero();
                    itemProductoListNewItemProducto.setContratonumero(contrato);
                    itemProductoListNewItemProducto = em.merge(itemProductoListNewItemProducto);
                    if (oldContratonumeroOfItemProductoListNewItemProducto != null && !oldContratonumeroOfItemProductoListNewItemProducto.equals(contrato)) {
                        oldContratonumeroOfItemProductoListNewItemProducto.getItemProductoList().remove(itemProductoListNewItemProducto);
                        oldContratonumeroOfItemProductoListNewItemProducto = em.merge(oldContratonumeroOfItemProductoListNewItemProducto);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = contrato.getNumero();
                if (findContrato(id) == null) {
                    throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contrato contrato;
            try {
                contrato = em.getReference(Contrato.class, id);
                contrato.getNumero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pago> pagoListOrphanCheck = contrato.getPagoList();
            for (Pago pagoListOrphanCheckPago : pagoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contrato (" + contrato + ") cannot be destroyed since the Pago " + pagoListOrphanCheckPago + " in its pagoList field has a non-nullable contratonumero field.");
            }
            List<Curso> cursoListOrphanCheck = contrato.getCursoList();
            for (Curso cursoListOrphanCheckCurso : cursoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contrato (" + contrato + ") cannot be destroyed since the Curso " + cursoListOrphanCheckCurso + " in its cursoList field has a non-nullable contratonumero field.");
            }
            List<VisitasMantenimiento> visitasMantenimientoListOrphanCheck = contrato.getVisitasMantenimientoList();
            for (VisitasMantenimiento visitasMantenimientoListOrphanCheckVisitasMantenimiento : visitasMantenimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contrato (" + contrato + ") cannot be destroyed since the VisitasMantenimiento " + visitasMantenimientoListOrphanCheckVisitasMantenimiento + " in its visitasMantenimientoList field has a non-nullable contratonumero field.");
            }
            List<GarantiaEconomica> garantiaEconomicaListOrphanCheck = contrato.getGarantiaEconomicaList();
            for (GarantiaEconomica garantiaEconomicaListOrphanCheckGarantiaEconomica : garantiaEconomicaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contrato (" + contrato + ") cannot be destroyed since the GarantiaEconomica " + garantiaEconomicaListOrphanCheckGarantiaEconomica + " in its garantiaEconomicaList field has a non-nullable contratonumero field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario accountManagerAsignado = contrato.getAccountManagerAsignado();
            if (accountManagerAsignado != null) {
                accountManagerAsignado.getContratoList().remove(contrato);
                accountManagerAsignado = em.merge(accountManagerAsignado);
            }
            TipoContrato tipoContratoid = contrato.getTipoContratoid();
            if (tipoContratoid != null) {
                tipoContratoid.getContratoList().remove(contrato);
                tipoContratoid = em.merge(tipoContratoid);
            }
            Sla slaid = contrato.getSlaid();
            if (slaid != null) {
                slaid.getContratoList().remove(contrato);
                slaid = em.merge(slaid);
            }
            Contacto administradorDeContrato = contrato.getAdministradorDeContrato();
            if (administradorDeContrato != null) {
                administradorDeContrato.getContratoList().remove(contrato);
                administradorDeContrato = em.merge(administradorDeContrato);
            }
            ClienteEmpresa clienteEmpresaruc = contrato.getClienteEmpresaruc();
            if (clienteEmpresaruc != null) {
                clienteEmpresaruc.getContratoList().remove(contrato);
                clienteEmpresaruc = em.merge(clienteEmpresaruc);
            }
            List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioList = contrato.getRegistroDeMovimientoDeInventarioList();
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario : registroDeMovimientoDeInventarioList) {
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario.setContratonumero(null);
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario = em.merge(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario);
            }
            List<ItemProducto> itemProductoList = contrato.getItemProductoList();
            for (ItemProducto itemProductoListItemProducto : itemProductoList) {
                itemProductoListItemProducto.setContratonumero(null);
                itemProductoListItemProducto = em.merge(itemProductoListItemProducto);
            }
            em.remove(contrato);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contrato> findContratoEntities() {
        return findContratoEntities(true, -1, -1);
    }

    public List<Contrato> findContratoEntities(int maxResults, int firstResult) {
        return findContratoEntities(false, maxResults, firstResult);
    }

    private List<Contrato> findContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contrato.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Contrato findContrato(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contrato> rt = cq.from(Contrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
