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
import ec.com.sinetcom.orm.ModeloProducto;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.CondicionFisica;
import ec.com.sinetcom.orm.ComponenteElectronicoAtomico;
import ec.com.sinetcom.orm.Bodega;
import java.util.ArrayList;
import java.util.List;
import ec.com.sinetcom.orm.RegistroDeMovimientoDeInventario;
import ec.com.sinetcom.orm.AtributoItemProducto;
import ec.com.sinetcom.orm.HistorialDeMovimientoDeProducto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class ItemProductoJpaController implements Serializable {

    public ItemProductoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemProducto itemProducto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (itemProducto.getModeloProductoList() == null) {
            itemProducto.setModeloProductoList(new ArrayList<ModeloProducto>());
        }
        if (itemProducto.getRegistroDeMovimientoDeInventarioList() == null) {
            itemProducto.setRegistroDeMovimientoDeInventarioList(new ArrayList<RegistroDeMovimientoDeInventario>());
        }
        if (itemProducto.getAtributoItemProductoList() == null) {
            itemProducto.setAtributoItemProductoList(new ArrayList<AtributoItemProducto>());
        }
        if (itemProducto.getItemProductoList() == null) {
            itemProducto.setItemProductoList(new ArrayList<ItemProducto>());
        }
        if (itemProducto.getHistorialDeMovimientoDeProductoList() == null) {
            itemProducto.setHistorialDeMovimientoDeProductoList(new ArrayList<HistorialDeMovimientoDeProducto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ModeloProducto modeloProductoid = itemProducto.getModeloProductoid();
            if (modeloProductoid != null) {
                modeloProductoid = em.getReference(modeloProductoid.getClass(), modeloProductoid.getId());
                itemProducto.setModeloProductoid(modeloProductoid);
            }
            ItemProducto itemProductonumeroSerialpadre = itemProducto.getItemProductonumeroSerialpadre();
            if (itemProductonumeroSerialpadre != null) {
                itemProductonumeroSerialpadre = em.getReference(itemProductonumeroSerialpadre.getClass(), itemProductonumeroSerialpadre.getNumeroSerial());
                itemProducto.setItemProductonumeroSerialpadre(itemProductonumeroSerialpadre);
            }
            Contrato contratonumero = itemProducto.getContratonumero();
            if (contratonumero != null) {
                contratonumero = em.getReference(contratonumero.getClass(), contratonumero.getNumero());
                itemProducto.setContratonumero(contratonumero);
            }
            CondicionFisica condicionFisicaid = itemProducto.getCondicionFisicaid();
            if (condicionFisicaid != null) {
                condicionFisicaid = em.getReference(condicionFisicaid.getClass(), condicionFisicaid.getId());
                itemProducto.setCondicionFisicaid(condicionFisicaid);
            }
            ComponenteElectronicoAtomico componenteElectronicoAtomicoid = itemProducto.getComponenteElectronicoAtomicoid();
            if (componenteElectronicoAtomicoid != null) {
                componenteElectronicoAtomicoid = em.getReference(componenteElectronicoAtomicoid.getClass(), componenteElectronicoAtomicoid.getId());
                itemProducto.setComponenteElectronicoAtomicoid(componenteElectronicoAtomicoid);
            }
            Bodega bodegaid = itemProducto.getBodegaid();
            if (bodegaid != null) {
                bodegaid = em.getReference(bodegaid.getClass(), bodegaid.getId());
                itemProducto.setBodegaid(bodegaid);
            }
            List<ModeloProducto> attachedModeloProductoList = new ArrayList<ModeloProducto>();
            for (ModeloProducto modeloProductoListModeloProductoToAttach : itemProducto.getModeloProductoList()) {
                modeloProductoListModeloProductoToAttach = em.getReference(modeloProductoListModeloProductoToAttach.getClass(), modeloProductoListModeloProductoToAttach.getId());
                attachedModeloProductoList.add(modeloProductoListModeloProductoToAttach);
            }
            itemProducto.setModeloProductoList(attachedModeloProductoList);
            List<RegistroDeMovimientoDeInventario> attachedRegistroDeMovimientoDeInventarioList = new ArrayList<RegistroDeMovimientoDeInventario>();
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach : itemProducto.getRegistroDeMovimientoDeInventarioList()) {
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach = em.getReference(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach.getClass(), registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach.getCodigo());
                attachedRegistroDeMovimientoDeInventarioList.add(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach);
            }
            itemProducto.setRegistroDeMovimientoDeInventarioList(attachedRegistroDeMovimientoDeInventarioList);
            List<AtributoItemProducto> attachedAtributoItemProductoList = new ArrayList<AtributoItemProducto>();
            for (AtributoItemProducto atributoItemProductoListAtributoItemProductoToAttach : itemProducto.getAtributoItemProductoList()) {
                atributoItemProductoListAtributoItemProductoToAttach = em.getReference(atributoItemProductoListAtributoItemProductoToAttach.getClass(), atributoItemProductoListAtributoItemProductoToAttach.getAtributoItemProductoPK());
                attachedAtributoItemProductoList.add(atributoItemProductoListAtributoItemProductoToAttach);
            }
            itemProducto.setAtributoItemProductoList(attachedAtributoItemProductoList);
            List<ItemProducto> attachedItemProductoList = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoListItemProductoToAttach : itemProducto.getItemProductoList()) {
                itemProductoListItemProductoToAttach = em.getReference(itemProductoListItemProductoToAttach.getClass(), itemProductoListItemProductoToAttach.getNumeroSerial());
                attachedItemProductoList.add(itemProductoListItemProductoToAttach);
            }
            itemProducto.setItemProductoList(attachedItemProductoList);
            List<HistorialDeMovimientoDeProducto> attachedHistorialDeMovimientoDeProductoList = new ArrayList<HistorialDeMovimientoDeProducto>();
            for (HistorialDeMovimientoDeProducto historialDeMovimientoDeProductoListHistorialDeMovimientoDeProductoToAttach : itemProducto.getHistorialDeMovimientoDeProductoList()) {
                historialDeMovimientoDeProductoListHistorialDeMovimientoDeProductoToAttach = em.getReference(historialDeMovimientoDeProductoListHistorialDeMovimientoDeProductoToAttach.getClass(), historialDeMovimientoDeProductoListHistorialDeMovimientoDeProductoToAttach.getId());
                attachedHistorialDeMovimientoDeProductoList.add(historialDeMovimientoDeProductoListHistorialDeMovimientoDeProductoToAttach);
            }
            itemProducto.setHistorialDeMovimientoDeProductoList(attachedHistorialDeMovimientoDeProductoList);
            em.persist(itemProducto);
            if (modeloProductoid != null) {
                modeloProductoid.getItemProductoList().add(itemProducto);
                modeloProductoid = em.merge(modeloProductoid);
            }
            if (itemProductonumeroSerialpadre != null) {
                itemProductonumeroSerialpadre.getItemProductoList().add(itemProducto);
                itemProductonumeroSerialpadre = em.merge(itemProductonumeroSerialpadre);
            }
            if (contratonumero != null) {
                contratonumero.getItemProductoList().add(itemProducto);
                contratonumero = em.merge(contratonumero);
            }
            if (condicionFisicaid != null) {
                condicionFisicaid.getItemProductoList().add(itemProducto);
                condicionFisicaid = em.merge(condicionFisicaid);
            }
            if (componenteElectronicoAtomicoid != null) {
                componenteElectronicoAtomicoid.getItemProductoList().add(itemProducto);
                componenteElectronicoAtomicoid = em.merge(componenteElectronicoAtomicoid);
            }
            if (bodegaid != null) {
                bodegaid.getItemProductoList().add(itemProducto);
                bodegaid = em.merge(bodegaid);
            }
            for (ModeloProducto modeloProductoListModeloProducto : itemProducto.getModeloProductoList()) {
                modeloProductoListModeloProducto.getItemProductoList().add(itemProducto);
                modeloProductoListModeloProducto = em.merge(modeloProductoListModeloProducto);
            }
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario : itemProducto.getRegistroDeMovimientoDeInventarioList()) {
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario.getItemProductoList().add(itemProducto);
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario = em.merge(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario);
            }
            for (AtributoItemProducto atributoItemProductoListAtributoItemProducto : itemProducto.getAtributoItemProductoList()) {
                ItemProducto oldItemProductoOfAtributoItemProductoListAtributoItemProducto = atributoItemProductoListAtributoItemProducto.getItemProducto();
                atributoItemProductoListAtributoItemProducto.setItemProducto(itemProducto);
                atributoItemProductoListAtributoItemProducto = em.merge(atributoItemProductoListAtributoItemProducto);
                if (oldItemProductoOfAtributoItemProductoListAtributoItemProducto != null) {
                    oldItemProductoOfAtributoItemProductoListAtributoItemProducto.getAtributoItemProductoList().remove(atributoItemProductoListAtributoItemProducto);
                    oldItemProductoOfAtributoItemProductoListAtributoItemProducto = em.merge(oldItemProductoOfAtributoItemProductoListAtributoItemProducto);
                }
            }
            for (ItemProducto itemProductoListItemProducto : itemProducto.getItemProductoList()) {
                ItemProducto oldItemProductonumeroSerialpadreOfItemProductoListItemProducto = itemProductoListItemProducto.getItemProductonumeroSerialpadre();
                itemProductoListItemProducto.setItemProductonumeroSerialpadre(itemProducto);
                itemProductoListItemProducto = em.merge(itemProductoListItemProducto);
                if (oldItemProductonumeroSerialpadreOfItemProductoListItemProducto != null) {
                    oldItemProductonumeroSerialpadreOfItemProductoListItemProducto.getItemProductoList().remove(itemProductoListItemProducto);
                    oldItemProductonumeroSerialpadreOfItemProductoListItemProducto = em.merge(oldItemProductonumeroSerialpadreOfItemProductoListItemProducto);
                }
            }
            for (HistorialDeMovimientoDeProducto historialDeMovimientoDeProductoListHistorialDeMovimientoDeProducto : itemProducto.getHistorialDeMovimientoDeProductoList()) {
                ItemProducto oldItemProductonumeroSerialOfHistorialDeMovimientoDeProductoListHistorialDeMovimientoDeProducto = historialDeMovimientoDeProductoListHistorialDeMovimientoDeProducto.getItemProductonumeroSerial();
                historialDeMovimientoDeProductoListHistorialDeMovimientoDeProducto.setItemProductonumeroSerial(itemProducto);
                historialDeMovimientoDeProductoListHistorialDeMovimientoDeProducto = em.merge(historialDeMovimientoDeProductoListHistorialDeMovimientoDeProducto);
                if (oldItemProductonumeroSerialOfHistorialDeMovimientoDeProductoListHistorialDeMovimientoDeProducto != null) {
                    oldItemProductonumeroSerialOfHistorialDeMovimientoDeProductoListHistorialDeMovimientoDeProducto.getHistorialDeMovimientoDeProductoList().remove(historialDeMovimientoDeProductoListHistorialDeMovimientoDeProducto);
                    oldItemProductonumeroSerialOfHistorialDeMovimientoDeProductoListHistorialDeMovimientoDeProducto = em.merge(oldItemProductonumeroSerialOfHistorialDeMovimientoDeProductoListHistorialDeMovimientoDeProducto);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findItemProducto(itemProducto.getNumeroSerial()) != null) {
                throw new PreexistingEntityException("ItemProducto " + itemProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItemProducto itemProducto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemProducto persistentItemProducto = em.find(ItemProducto.class, itemProducto.getNumeroSerial());
            ModeloProducto modeloProductoidOld = persistentItemProducto.getModeloProductoid();
            ModeloProducto modeloProductoidNew = itemProducto.getModeloProductoid();
            ItemProducto itemProductonumeroSerialpadreOld = persistentItemProducto.getItemProductonumeroSerialpadre();
            ItemProducto itemProductonumeroSerialpadreNew = itemProducto.getItemProductonumeroSerialpadre();
            Contrato contratonumeroOld = persistentItemProducto.getContratonumero();
            Contrato contratonumeroNew = itemProducto.getContratonumero();
            CondicionFisica condicionFisicaidOld = persistentItemProducto.getCondicionFisicaid();
            CondicionFisica condicionFisicaidNew = itemProducto.getCondicionFisicaid();
            ComponenteElectronicoAtomico componenteElectronicoAtomicoidOld = persistentItemProducto.getComponenteElectronicoAtomicoid();
            ComponenteElectronicoAtomico componenteElectronicoAtomicoidNew = itemProducto.getComponenteElectronicoAtomicoid();
            Bodega bodegaidOld = persistentItemProducto.getBodegaid();
            Bodega bodegaidNew = itemProducto.getBodegaid();
            List<ModeloProducto> modeloProductoListOld = persistentItemProducto.getModeloProductoList();
            List<ModeloProducto> modeloProductoListNew = itemProducto.getModeloProductoList();
            List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioListOld = persistentItemProducto.getRegistroDeMovimientoDeInventarioList();
            List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioListNew = itemProducto.getRegistroDeMovimientoDeInventarioList();
            List<AtributoItemProducto> atributoItemProductoListOld = persistentItemProducto.getAtributoItemProductoList();
            List<AtributoItemProducto> atributoItemProductoListNew = itemProducto.getAtributoItemProductoList();
            List<ItemProducto> itemProductoListOld = persistentItemProducto.getItemProductoList();
            List<ItemProducto> itemProductoListNew = itemProducto.getItemProductoList();
            List<HistorialDeMovimientoDeProducto> historialDeMovimientoDeProductoListOld = persistentItemProducto.getHistorialDeMovimientoDeProductoList();
            List<HistorialDeMovimientoDeProducto> historialDeMovimientoDeProductoListNew = itemProducto.getHistorialDeMovimientoDeProductoList();
            List<String> illegalOrphanMessages = null;
            for (AtributoItemProducto atributoItemProductoListOldAtributoItemProducto : atributoItemProductoListOld) {
                if (!atributoItemProductoListNew.contains(atributoItemProductoListOldAtributoItemProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AtributoItemProducto " + atributoItemProductoListOldAtributoItemProducto + " since its itemProducto field is not nullable.");
                }
            }
            for (HistorialDeMovimientoDeProducto historialDeMovimientoDeProductoListOldHistorialDeMovimientoDeProducto : historialDeMovimientoDeProductoListOld) {
                if (!historialDeMovimientoDeProductoListNew.contains(historialDeMovimientoDeProductoListOldHistorialDeMovimientoDeProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HistorialDeMovimientoDeProducto " + historialDeMovimientoDeProductoListOldHistorialDeMovimientoDeProducto + " since its itemProductonumeroSerial field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (modeloProductoidNew != null) {
                modeloProductoidNew = em.getReference(modeloProductoidNew.getClass(), modeloProductoidNew.getId());
                itemProducto.setModeloProductoid(modeloProductoidNew);
            }
            if (itemProductonumeroSerialpadreNew != null) {
                itemProductonumeroSerialpadreNew = em.getReference(itemProductonumeroSerialpadreNew.getClass(), itemProductonumeroSerialpadreNew.getNumeroSerial());
                itemProducto.setItemProductonumeroSerialpadre(itemProductonumeroSerialpadreNew);
            }
            if (contratonumeroNew != null) {
                contratonumeroNew = em.getReference(contratonumeroNew.getClass(), contratonumeroNew.getNumero());
                itemProducto.setContratonumero(contratonumeroNew);
            }
            if (condicionFisicaidNew != null) {
                condicionFisicaidNew = em.getReference(condicionFisicaidNew.getClass(), condicionFisicaidNew.getId());
                itemProducto.setCondicionFisicaid(condicionFisicaidNew);
            }
            if (componenteElectronicoAtomicoidNew != null) {
                componenteElectronicoAtomicoidNew = em.getReference(componenteElectronicoAtomicoidNew.getClass(), componenteElectronicoAtomicoidNew.getId());
                itemProducto.setComponenteElectronicoAtomicoid(componenteElectronicoAtomicoidNew);
            }
            if (bodegaidNew != null) {
                bodegaidNew = em.getReference(bodegaidNew.getClass(), bodegaidNew.getId());
                itemProducto.setBodegaid(bodegaidNew);
            }
            List<ModeloProducto> attachedModeloProductoListNew = new ArrayList<ModeloProducto>();
            for (ModeloProducto modeloProductoListNewModeloProductoToAttach : modeloProductoListNew) {
                modeloProductoListNewModeloProductoToAttach = em.getReference(modeloProductoListNewModeloProductoToAttach.getClass(), modeloProductoListNewModeloProductoToAttach.getId());
                attachedModeloProductoListNew.add(modeloProductoListNewModeloProductoToAttach);
            }
            modeloProductoListNew = attachedModeloProductoListNew;
            itemProducto.setModeloProductoList(modeloProductoListNew);
            List<RegistroDeMovimientoDeInventario> attachedRegistroDeMovimientoDeInventarioListNew = new ArrayList<RegistroDeMovimientoDeInventario>();
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach : registroDeMovimientoDeInventarioListNew) {
                registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach = em.getReference(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach.getClass(), registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach.getCodigo());
                attachedRegistroDeMovimientoDeInventarioListNew.add(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach);
            }
            registroDeMovimientoDeInventarioListNew = attachedRegistroDeMovimientoDeInventarioListNew;
            itemProducto.setRegistroDeMovimientoDeInventarioList(registroDeMovimientoDeInventarioListNew);
            List<AtributoItemProducto> attachedAtributoItemProductoListNew = new ArrayList<AtributoItemProducto>();
            for (AtributoItemProducto atributoItemProductoListNewAtributoItemProductoToAttach : atributoItemProductoListNew) {
                atributoItemProductoListNewAtributoItemProductoToAttach = em.getReference(atributoItemProductoListNewAtributoItemProductoToAttach.getClass(), atributoItemProductoListNewAtributoItemProductoToAttach.getAtributoItemProductoPK());
                attachedAtributoItemProductoListNew.add(atributoItemProductoListNewAtributoItemProductoToAttach);
            }
            atributoItemProductoListNew = attachedAtributoItemProductoListNew;
            itemProducto.setAtributoItemProductoList(atributoItemProductoListNew);
            List<ItemProducto> attachedItemProductoListNew = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoListNewItemProductoToAttach : itemProductoListNew) {
                itemProductoListNewItemProductoToAttach = em.getReference(itemProductoListNewItemProductoToAttach.getClass(), itemProductoListNewItemProductoToAttach.getNumeroSerial());
                attachedItemProductoListNew.add(itemProductoListNewItemProductoToAttach);
            }
            itemProductoListNew = attachedItemProductoListNew;
            itemProducto.setItemProductoList(itemProductoListNew);
            List<HistorialDeMovimientoDeProducto> attachedHistorialDeMovimientoDeProductoListNew = new ArrayList<HistorialDeMovimientoDeProducto>();
            for (HistorialDeMovimientoDeProducto historialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProductoToAttach : historialDeMovimientoDeProductoListNew) {
                historialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProductoToAttach = em.getReference(historialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProductoToAttach.getClass(), historialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProductoToAttach.getId());
                attachedHistorialDeMovimientoDeProductoListNew.add(historialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProductoToAttach);
            }
            historialDeMovimientoDeProductoListNew = attachedHistorialDeMovimientoDeProductoListNew;
            itemProducto.setHistorialDeMovimientoDeProductoList(historialDeMovimientoDeProductoListNew);
            itemProducto = em.merge(itemProducto);
            if (modeloProductoidOld != null && !modeloProductoidOld.equals(modeloProductoidNew)) {
                modeloProductoidOld.getItemProductoList().remove(itemProducto);
                modeloProductoidOld = em.merge(modeloProductoidOld);
            }
            if (modeloProductoidNew != null && !modeloProductoidNew.equals(modeloProductoidOld)) {
                modeloProductoidNew.getItemProductoList().add(itemProducto);
                modeloProductoidNew = em.merge(modeloProductoidNew);
            }
            if (itemProductonumeroSerialpadreOld != null && !itemProductonumeroSerialpadreOld.equals(itemProductonumeroSerialpadreNew)) {
                itemProductonumeroSerialpadreOld.getItemProductoList().remove(itemProducto);
                itemProductonumeroSerialpadreOld = em.merge(itemProductonumeroSerialpadreOld);
            }
            if (itemProductonumeroSerialpadreNew != null && !itemProductonumeroSerialpadreNew.equals(itemProductonumeroSerialpadreOld)) {
                itemProductonumeroSerialpadreNew.getItemProductoList().add(itemProducto);
                itemProductonumeroSerialpadreNew = em.merge(itemProductonumeroSerialpadreNew);
            }
            if (contratonumeroOld != null && !contratonumeroOld.equals(contratonumeroNew)) {
                contratonumeroOld.getItemProductoList().remove(itemProducto);
                contratonumeroOld = em.merge(contratonumeroOld);
            }
            if (contratonumeroNew != null && !contratonumeroNew.equals(contratonumeroOld)) {
                contratonumeroNew.getItemProductoList().add(itemProducto);
                contratonumeroNew = em.merge(contratonumeroNew);
            }
            if (condicionFisicaidOld != null && !condicionFisicaidOld.equals(condicionFisicaidNew)) {
                condicionFisicaidOld.getItemProductoList().remove(itemProducto);
                condicionFisicaidOld = em.merge(condicionFisicaidOld);
            }
            if (condicionFisicaidNew != null && !condicionFisicaidNew.equals(condicionFisicaidOld)) {
                condicionFisicaidNew.getItemProductoList().add(itemProducto);
                condicionFisicaidNew = em.merge(condicionFisicaidNew);
            }
            if (componenteElectronicoAtomicoidOld != null && !componenteElectronicoAtomicoidOld.equals(componenteElectronicoAtomicoidNew)) {
                componenteElectronicoAtomicoidOld.getItemProductoList().remove(itemProducto);
                componenteElectronicoAtomicoidOld = em.merge(componenteElectronicoAtomicoidOld);
            }
            if (componenteElectronicoAtomicoidNew != null && !componenteElectronicoAtomicoidNew.equals(componenteElectronicoAtomicoidOld)) {
                componenteElectronicoAtomicoidNew.getItemProductoList().add(itemProducto);
                componenteElectronicoAtomicoidNew = em.merge(componenteElectronicoAtomicoidNew);
            }
            if (bodegaidOld != null && !bodegaidOld.equals(bodegaidNew)) {
                bodegaidOld.getItemProductoList().remove(itemProducto);
                bodegaidOld = em.merge(bodegaidOld);
            }
            if (bodegaidNew != null && !bodegaidNew.equals(bodegaidOld)) {
                bodegaidNew.getItemProductoList().add(itemProducto);
                bodegaidNew = em.merge(bodegaidNew);
            }
            for (ModeloProducto modeloProductoListOldModeloProducto : modeloProductoListOld) {
                if (!modeloProductoListNew.contains(modeloProductoListOldModeloProducto)) {
                    modeloProductoListOldModeloProducto.getItemProductoList().remove(itemProducto);
                    modeloProductoListOldModeloProducto = em.merge(modeloProductoListOldModeloProducto);
                }
            }
            for (ModeloProducto modeloProductoListNewModeloProducto : modeloProductoListNew) {
                if (!modeloProductoListOld.contains(modeloProductoListNewModeloProducto)) {
                    modeloProductoListNewModeloProducto.getItemProductoList().add(itemProducto);
                    modeloProductoListNewModeloProducto = em.merge(modeloProductoListNewModeloProducto);
                }
            }
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario : registroDeMovimientoDeInventarioListOld) {
                if (!registroDeMovimientoDeInventarioListNew.contains(registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario)) {
                    registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario.getItemProductoList().remove(itemProducto);
                    registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario = em.merge(registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario);
                }
            }
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario : registroDeMovimientoDeInventarioListNew) {
                if (!registroDeMovimientoDeInventarioListOld.contains(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario)) {
                    registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario.getItemProductoList().add(itemProducto);
                    registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario = em.merge(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario);
                }
            }
            for (AtributoItemProducto atributoItemProductoListNewAtributoItemProducto : atributoItemProductoListNew) {
                if (!atributoItemProductoListOld.contains(atributoItemProductoListNewAtributoItemProducto)) {
                    ItemProducto oldItemProductoOfAtributoItemProductoListNewAtributoItemProducto = atributoItemProductoListNewAtributoItemProducto.getItemProducto();
                    atributoItemProductoListNewAtributoItemProducto.setItemProducto(itemProducto);
                    atributoItemProductoListNewAtributoItemProducto = em.merge(atributoItemProductoListNewAtributoItemProducto);
                    if (oldItemProductoOfAtributoItemProductoListNewAtributoItemProducto != null && !oldItemProductoOfAtributoItemProductoListNewAtributoItemProducto.equals(itemProducto)) {
                        oldItemProductoOfAtributoItemProductoListNewAtributoItemProducto.getAtributoItemProductoList().remove(atributoItemProductoListNewAtributoItemProducto);
                        oldItemProductoOfAtributoItemProductoListNewAtributoItemProducto = em.merge(oldItemProductoOfAtributoItemProductoListNewAtributoItemProducto);
                    }
                }
            }
            for (ItemProducto itemProductoListOldItemProducto : itemProductoListOld) {
                if (!itemProductoListNew.contains(itemProductoListOldItemProducto)) {
                    itemProductoListOldItemProducto.setItemProductonumeroSerialpadre(null);
                    itemProductoListOldItemProducto = em.merge(itemProductoListOldItemProducto);
                }
            }
            for (ItemProducto itemProductoListNewItemProducto : itemProductoListNew) {
                if (!itemProductoListOld.contains(itemProductoListNewItemProducto)) {
                    ItemProducto oldItemProductonumeroSerialpadreOfItemProductoListNewItemProducto = itemProductoListNewItemProducto.getItemProductonumeroSerialpadre();
                    itemProductoListNewItemProducto.setItemProductonumeroSerialpadre(itemProducto);
                    itemProductoListNewItemProducto = em.merge(itemProductoListNewItemProducto);
                    if (oldItemProductonumeroSerialpadreOfItemProductoListNewItemProducto != null && !oldItemProductonumeroSerialpadreOfItemProductoListNewItemProducto.equals(itemProducto)) {
                        oldItemProductonumeroSerialpadreOfItemProductoListNewItemProducto.getItemProductoList().remove(itemProductoListNewItemProducto);
                        oldItemProductonumeroSerialpadreOfItemProductoListNewItemProducto = em.merge(oldItemProductonumeroSerialpadreOfItemProductoListNewItemProducto);
                    }
                }
            }
            for (HistorialDeMovimientoDeProducto historialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProducto : historialDeMovimientoDeProductoListNew) {
                if (!historialDeMovimientoDeProductoListOld.contains(historialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProducto)) {
                    ItemProducto oldItemProductonumeroSerialOfHistorialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProducto = historialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProducto.getItemProductonumeroSerial();
                    historialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProducto.setItemProductonumeroSerial(itemProducto);
                    historialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProducto = em.merge(historialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProducto);
                    if (oldItemProductonumeroSerialOfHistorialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProducto != null && !oldItemProductonumeroSerialOfHistorialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProducto.equals(itemProducto)) {
                        oldItemProductonumeroSerialOfHistorialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProducto.getHistorialDeMovimientoDeProductoList().remove(historialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProducto);
                        oldItemProductonumeroSerialOfHistorialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProducto = em.merge(oldItemProductonumeroSerialOfHistorialDeMovimientoDeProductoListNewHistorialDeMovimientoDeProducto);
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
                String id = itemProducto.getNumeroSerial();
                if (findItemProducto(id) == null) {
                    throw new NonexistentEntityException("The itemProducto with id " + id + " no longer exists.");
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
            ItemProducto itemProducto;
            try {
                itemProducto = em.getReference(ItemProducto.class, id);
                itemProducto.getNumeroSerial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemProducto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AtributoItemProducto> atributoItemProductoListOrphanCheck = itemProducto.getAtributoItemProductoList();
            for (AtributoItemProducto atributoItemProductoListOrphanCheckAtributoItemProducto : atributoItemProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItemProducto (" + itemProducto + ") cannot be destroyed since the AtributoItemProducto " + atributoItemProductoListOrphanCheckAtributoItemProducto + " in its atributoItemProductoList field has a non-nullable itemProducto field.");
            }
            List<HistorialDeMovimientoDeProducto> historialDeMovimientoDeProductoListOrphanCheck = itemProducto.getHistorialDeMovimientoDeProductoList();
            for (HistorialDeMovimientoDeProducto historialDeMovimientoDeProductoListOrphanCheckHistorialDeMovimientoDeProducto : historialDeMovimientoDeProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItemProducto (" + itemProducto + ") cannot be destroyed since the HistorialDeMovimientoDeProducto " + historialDeMovimientoDeProductoListOrphanCheckHistorialDeMovimientoDeProducto + " in its historialDeMovimientoDeProductoList field has a non-nullable itemProductonumeroSerial field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ModeloProducto modeloProductoid = itemProducto.getModeloProductoid();
            if (modeloProductoid != null) {
                modeloProductoid.getItemProductoList().remove(itemProducto);
                modeloProductoid = em.merge(modeloProductoid);
            }
            ItemProducto itemProductonumeroSerialpadre = itemProducto.getItemProductonumeroSerialpadre();
            if (itemProductonumeroSerialpadre != null) {
                itemProductonumeroSerialpadre.getItemProductoList().remove(itemProducto);
                itemProductonumeroSerialpadre = em.merge(itemProductonumeroSerialpadre);
            }
            Contrato contratonumero = itemProducto.getContratonumero();
            if (contratonumero != null) {
                contratonumero.getItemProductoList().remove(itemProducto);
                contratonumero = em.merge(contratonumero);
            }
            CondicionFisica condicionFisicaid = itemProducto.getCondicionFisicaid();
            if (condicionFisicaid != null) {
                condicionFisicaid.getItemProductoList().remove(itemProducto);
                condicionFisicaid = em.merge(condicionFisicaid);
            }
            ComponenteElectronicoAtomico componenteElectronicoAtomicoid = itemProducto.getComponenteElectronicoAtomicoid();
            if (componenteElectronicoAtomicoid != null) {
                componenteElectronicoAtomicoid.getItemProductoList().remove(itemProducto);
                componenteElectronicoAtomicoid = em.merge(componenteElectronicoAtomicoid);
            }
            Bodega bodegaid = itemProducto.getBodegaid();
            if (bodegaid != null) {
                bodegaid.getItemProductoList().remove(itemProducto);
                bodegaid = em.merge(bodegaid);
            }
            List<ModeloProducto> modeloProductoList = itemProducto.getModeloProductoList();
            for (ModeloProducto modeloProductoListModeloProducto : modeloProductoList) {
                modeloProductoListModeloProducto.getItemProductoList().remove(itemProducto);
                modeloProductoListModeloProducto = em.merge(modeloProductoListModeloProducto);
            }
            List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioList = itemProducto.getRegistroDeMovimientoDeInventarioList();
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario : registroDeMovimientoDeInventarioList) {
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario.getItemProductoList().remove(itemProducto);
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario = em.merge(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario);
            }
            List<ItemProducto> itemProductoList = itemProducto.getItemProductoList();
            for (ItemProducto itemProductoListItemProducto : itemProductoList) {
                itemProductoListItemProducto.setItemProductonumeroSerialpadre(null);
                itemProductoListItemProducto = em.merge(itemProductoListItemProducto);
            }
            em.remove(itemProducto);
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

    public List<ItemProducto> findItemProductoEntities() {
        return findItemProductoEntities(true, -1, -1);
    }

    public List<ItemProducto> findItemProductoEntities(int maxResults, int firstResult) {
        return findItemProductoEntities(false, maxResults, firstResult);
    }

    private List<ItemProducto> findItemProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemProducto.class));
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

    public ItemProducto findItemProducto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemProducto> rt = cq.from(ItemProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
