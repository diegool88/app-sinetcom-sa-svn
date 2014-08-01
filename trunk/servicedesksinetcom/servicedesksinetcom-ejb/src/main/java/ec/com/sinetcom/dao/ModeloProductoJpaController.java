/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.PreexistingEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.LineaDeProducto;
import ec.com.sinetcom.orm.ItemProducto;
import ec.com.sinetcom.orm.ModeloProducto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class ModeloProductoJpaController implements Serializable {

    public ModeloProductoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ModeloProducto modeloProducto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (modeloProducto.getItemProductoList() == null) {
            modeloProducto.setItemProductoList(new ArrayList<ItemProducto>());
        }
        if (modeloProducto.getItemProductoList1() == null) {
            modeloProducto.setItemProductoList1(new ArrayList<ItemProducto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            LineaDeProducto lineaDeProductoid = modeloProducto.getLineaDeProductoid();
            if (lineaDeProductoid != null) {
                lineaDeProductoid = em.getReference(lineaDeProductoid.getClass(), lineaDeProductoid.getId());
                modeloProducto.setLineaDeProductoid(lineaDeProductoid);
            }
            List<ItemProducto> attachedItemProductoList = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoListItemProductoToAttach : modeloProducto.getItemProductoList()) {
                itemProductoListItemProductoToAttach = em.getReference(itemProductoListItemProductoToAttach.getClass(), itemProductoListItemProductoToAttach.getNumeroSerial());
                attachedItemProductoList.add(itemProductoListItemProductoToAttach);
            }
            modeloProducto.setItemProductoList(attachedItemProductoList);
            List<ItemProducto> attachedItemProductoList1 = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoList1ItemProductoToAttach : modeloProducto.getItemProductoList1()) {
                itemProductoList1ItemProductoToAttach = em.getReference(itemProductoList1ItemProductoToAttach.getClass(), itemProductoList1ItemProductoToAttach.getNumeroSerial());
                attachedItemProductoList1.add(itemProductoList1ItemProductoToAttach);
            }
            modeloProducto.setItemProductoList1(attachedItemProductoList1);
            em.persist(modeloProducto);
            if (lineaDeProductoid != null) {
                lineaDeProductoid.getModeloProductoList().add(modeloProducto);
                lineaDeProductoid = em.merge(lineaDeProductoid);
            }
            for (ItemProducto itemProductoListItemProducto : modeloProducto.getItemProductoList()) {
                itemProductoListItemProducto.getModeloProductoList().add(modeloProducto);
                itemProductoListItemProducto = em.merge(itemProductoListItemProducto);
            }
            for (ItemProducto itemProductoList1ItemProducto : modeloProducto.getItemProductoList1()) {
                ModeloProducto oldModeloProductoidOfItemProductoList1ItemProducto = itemProductoList1ItemProducto.getModeloProductoid();
                itemProductoList1ItemProducto.setModeloProductoid(modeloProducto);
                itemProductoList1ItemProducto = em.merge(itemProductoList1ItemProducto);
                if (oldModeloProductoidOfItemProductoList1ItemProducto != null) {
                    oldModeloProductoidOfItemProductoList1ItemProducto.getItemProductoList1().remove(itemProductoList1ItemProducto);
                    oldModeloProductoidOfItemProductoList1ItemProducto = em.merge(oldModeloProductoidOfItemProductoList1ItemProducto);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findModeloProducto(modeloProducto.getId()) != null) {
                throw new PreexistingEntityException("ModeloProducto " + modeloProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ModeloProducto modeloProducto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ModeloProducto persistentModeloProducto = em.find(ModeloProducto.class, modeloProducto.getId());
            LineaDeProducto lineaDeProductoidOld = persistentModeloProducto.getLineaDeProductoid();
            LineaDeProducto lineaDeProductoidNew = modeloProducto.getLineaDeProductoid();
            List<ItemProducto> itemProductoListOld = persistentModeloProducto.getItemProductoList();
            List<ItemProducto> itemProductoListNew = modeloProducto.getItemProductoList();
            List<ItemProducto> itemProductoList1Old = persistentModeloProducto.getItemProductoList1();
            List<ItemProducto> itemProductoList1New = modeloProducto.getItemProductoList1();
            if (lineaDeProductoidNew != null) {
                lineaDeProductoidNew = em.getReference(lineaDeProductoidNew.getClass(), lineaDeProductoidNew.getId());
                modeloProducto.setLineaDeProductoid(lineaDeProductoidNew);
            }
            List<ItemProducto> attachedItemProductoListNew = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoListNewItemProductoToAttach : itemProductoListNew) {
                itemProductoListNewItemProductoToAttach = em.getReference(itemProductoListNewItemProductoToAttach.getClass(), itemProductoListNewItemProductoToAttach.getNumeroSerial());
                attachedItemProductoListNew.add(itemProductoListNewItemProductoToAttach);
            }
            itemProductoListNew = attachedItemProductoListNew;
            modeloProducto.setItemProductoList(itemProductoListNew);
            List<ItemProducto> attachedItemProductoList1New = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoList1NewItemProductoToAttach : itemProductoList1New) {
                itemProductoList1NewItemProductoToAttach = em.getReference(itemProductoList1NewItemProductoToAttach.getClass(), itemProductoList1NewItemProductoToAttach.getNumeroSerial());
                attachedItemProductoList1New.add(itemProductoList1NewItemProductoToAttach);
            }
            itemProductoList1New = attachedItemProductoList1New;
            modeloProducto.setItemProductoList1(itemProductoList1New);
            modeloProducto = em.merge(modeloProducto);
            if (lineaDeProductoidOld != null && !lineaDeProductoidOld.equals(lineaDeProductoidNew)) {
                lineaDeProductoidOld.getModeloProductoList().remove(modeloProducto);
                lineaDeProductoidOld = em.merge(lineaDeProductoidOld);
            }
            if (lineaDeProductoidNew != null && !lineaDeProductoidNew.equals(lineaDeProductoidOld)) {
                lineaDeProductoidNew.getModeloProductoList().add(modeloProducto);
                lineaDeProductoidNew = em.merge(lineaDeProductoidNew);
            }
            for (ItemProducto itemProductoListOldItemProducto : itemProductoListOld) {
                if (!itemProductoListNew.contains(itemProductoListOldItemProducto)) {
                    itemProductoListOldItemProducto.getModeloProductoList().remove(modeloProducto);
                    itemProductoListOldItemProducto = em.merge(itemProductoListOldItemProducto);
                }
            }
            for (ItemProducto itemProductoListNewItemProducto : itemProductoListNew) {
                if (!itemProductoListOld.contains(itemProductoListNewItemProducto)) {
                    itemProductoListNewItemProducto.getModeloProductoList().add(modeloProducto);
                    itemProductoListNewItemProducto = em.merge(itemProductoListNewItemProducto);
                }
            }
            for (ItemProducto itemProductoList1OldItemProducto : itemProductoList1Old) {
                if (!itemProductoList1New.contains(itemProductoList1OldItemProducto)) {
                    itemProductoList1OldItemProducto.setModeloProductoid(null);
                    itemProductoList1OldItemProducto = em.merge(itemProductoList1OldItemProducto);
                }
            }
            for (ItemProducto itemProductoList1NewItemProducto : itemProductoList1New) {
                if (!itemProductoList1Old.contains(itemProductoList1NewItemProducto)) {
                    ModeloProducto oldModeloProductoidOfItemProductoList1NewItemProducto = itemProductoList1NewItemProducto.getModeloProductoid();
                    itemProductoList1NewItemProducto.setModeloProductoid(modeloProducto);
                    itemProductoList1NewItemProducto = em.merge(itemProductoList1NewItemProducto);
                    if (oldModeloProductoidOfItemProductoList1NewItemProducto != null && !oldModeloProductoidOfItemProductoList1NewItemProducto.equals(modeloProducto)) {
                        oldModeloProductoidOfItemProductoList1NewItemProducto.getItemProductoList1().remove(itemProductoList1NewItemProducto);
                        oldModeloProductoidOfItemProductoList1NewItemProducto = em.merge(oldModeloProductoidOfItemProductoList1NewItemProducto);
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
                Integer id = modeloProducto.getId();
                if (findModeloProducto(id) == null) {
                    throw new NonexistentEntityException("The modeloProducto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ModeloProducto modeloProducto;
            try {
                modeloProducto = em.getReference(ModeloProducto.class, id);
                modeloProducto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modeloProducto with id " + id + " no longer exists.", enfe);
            }
            LineaDeProducto lineaDeProductoid = modeloProducto.getLineaDeProductoid();
            if (lineaDeProductoid != null) {
                lineaDeProductoid.getModeloProductoList().remove(modeloProducto);
                lineaDeProductoid = em.merge(lineaDeProductoid);
            }
            List<ItemProducto> itemProductoList = modeloProducto.getItemProductoList();
            for (ItemProducto itemProductoListItemProducto : itemProductoList) {
                itemProductoListItemProducto.getModeloProductoList().remove(modeloProducto);
                itemProductoListItemProducto = em.merge(itemProductoListItemProducto);
            }
            List<ItemProducto> itemProductoList1 = modeloProducto.getItemProductoList1();
            for (ItemProducto itemProductoList1ItemProducto : itemProductoList1) {
                itemProductoList1ItemProducto.setModeloProductoid(null);
                itemProductoList1ItemProducto = em.merge(itemProductoList1ItemProducto);
            }
            em.remove(modeloProducto);
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

    public List<ModeloProducto> findModeloProductoEntities() {
        return findModeloProductoEntities(true, -1, -1);
    }

    public List<ModeloProducto> findModeloProductoEntities(int maxResults, int firstResult) {
        return findModeloProductoEntities(false, maxResults, firstResult);
    }

    private List<ModeloProducto> findModeloProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ModeloProducto.class));
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

    public ModeloProducto findModeloProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ModeloProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getModeloProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ModeloProducto> rt = cq.from(ModeloProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
