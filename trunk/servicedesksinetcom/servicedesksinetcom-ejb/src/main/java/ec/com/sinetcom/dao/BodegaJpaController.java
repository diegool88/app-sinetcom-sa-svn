/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import ec.com.sinetcom.orm.Bodega;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.Ciudad;
import ec.com.sinetcom.orm.ItemProducto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class BodegaJpaController implements Serializable {

    public BodegaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bodega bodega) throws RollbackFailureException, Exception {
        if (bodega.getItemProductoList() == null) {
            bodega.setItemProductoList(new ArrayList<ItemProducto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ciudad ciudadid = bodega.getCiudadid();
            if (ciudadid != null) {
                ciudadid = em.getReference(ciudadid.getClass(), ciudadid.getId());
                bodega.setCiudadid(ciudadid);
            }
            List<ItemProducto> attachedItemProductoList = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoListItemProductoToAttach : bodega.getItemProductoList()) {
                itemProductoListItemProductoToAttach = em.getReference(itemProductoListItemProductoToAttach.getClass(), itemProductoListItemProductoToAttach.getNumeroSerial());
                attachedItemProductoList.add(itemProductoListItemProductoToAttach);
            }
            bodega.setItemProductoList(attachedItemProductoList);
            em.persist(bodega);
            if (ciudadid != null) {
                ciudadid.getBodegaList().add(bodega);
                ciudadid = em.merge(ciudadid);
            }
            for (ItemProducto itemProductoListItemProducto : bodega.getItemProductoList()) {
                Bodega oldBodegaidOfItemProductoListItemProducto = itemProductoListItemProducto.getBodegaid();
                itemProductoListItemProducto.setBodegaid(bodega);
                itemProductoListItemProducto = em.merge(itemProductoListItemProducto);
                if (oldBodegaidOfItemProductoListItemProducto != null) {
                    oldBodegaidOfItemProductoListItemProducto.getItemProductoList().remove(itemProductoListItemProducto);
                    oldBodegaidOfItemProductoListItemProducto = em.merge(oldBodegaidOfItemProductoListItemProducto);
                }
            }
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

    public void edit(Bodega bodega) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Bodega persistentBodega = em.find(Bodega.class, bodega.getId());
            Ciudad ciudadidOld = persistentBodega.getCiudadid();
            Ciudad ciudadidNew = bodega.getCiudadid();
            List<ItemProducto> itemProductoListOld = persistentBodega.getItemProductoList();
            List<ItemProducto> itemProductoListNew = bodega.getItemProductoList();
            if (ciudadidNew != null) {
                ciudadidNew = em.getReference(ciudadidNew.getClass(), ciudadidNew.getId());
                bodega.setCiudadid(ciudadidNew);
            }
            List<ItemProducto> attachedItemProductoListNew = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoListNewItemProductoToAttach : itemProductoListNew) {
                itemProductoListNewItemProductoToAttach = em.getReference(itemProductoListNewItemProductoToAttach.getClass(), itemProductoListNewItemProductoToAttach.getNumeroSerial());
                attachedItemProductoListNew.add(itemProductoListNewItemProductoToAttach);
            }
            itemProductoListNew = attachedItemProductoListNew;
            bodega.setItemProductoList(itemProductoListNew);
            bodega = em.merge(bodega);
            if (ciudadidOld != null && !ciudadidOld.equals(ciudadidNew)) {
                ciudadidOld.getBodegaList().remove(bodega);
                ciudadidOld = em.merge(ciudadidOld);
            }
            if (ciudadidNew != null && !ciudadidNew.equals(ciudadidOld)) {
                ciudadidNew.getBodegaList().add(bodega);
                ciudadidNew = em.merge(ciudadidNew);
            }
            for (ItemProducto itemProductoListOldItemProducto : itemProductoListOld) {
                if (!itemProductoListNew.contains(itemProductoListOldItemProducto)) {
                    itemProductoListOldItemProducto.setBodegaid(null);
                    itemProductoListOldItemProducto = em.merge(itemProductoListOldItemProducto);
                }
            }
            for (ItemProducto itemProductoListNewItemProducto : itemProductoListNew) {
                if (!itemProductoListOld.contains(itemProductoListNewItemProducto)) {
                    Bodega oldBodegaidOfItemProductoListNewItemProducto = itemProductoListNewItemProducto.getBodegaid();
                    itemProductoListNewItemProducto.setBodegaid(bodega);
                    itemProductoListNewItemProducto = em.merge(itemProductoListNewItemProducto);
                    if (oldBodegaidOfItemProductoListNewItemProducto != null && !oldBodegaidOfItemProductoListNewItemProducto.equals(bodega)) {
                        oldBodegaidOfItemProductoListNewItemProducto.getItemProductoList().remove(itemProductoListNewItemProducto);
                        oldBodegaidOfItemProductoListNewItemProducto = em.merge(oldBodegaidOfItemProductoListNewItemProducto);
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
                Integer id = bodega.getId();
                if (findBodega(id) == null) {
                    throw new NonexistentEntityException("The bodega with id " + id + " no longer exists.");
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
            Bodega bodega;
            try {
                bodega = em.getReference(Bodega.class, id);
                bodega.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bodega with id " + id + " no longer exists.", enfe);
            }
            Ciudad ciudadid = bodega.getCiudadid();
            if (ciudadid != null) {
                ciudadid.getBodegaList().remove(bodega);
                ciudadid = em.merge(ciudadid);
            }
            List<ItemProducto> itemProductoList = bodega.getItemProductoList();
            for (ItemProducto itemProductoListItemProducto : itemProductoList) {
                itemProductoListItemProducto.setBodegaid(null);
                itemProductoListItemProducto = em.merge(itemProductoListItemProducto);
            }
            em.remove(bodega);
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

    public List<Bodega> findBodegaEntities() {
        return findBodegaEntities(true, -1, -1);
    }

    public List<Bodega> findBodegaEntities(int maxResults, int firstResult) {
        return findBodegaEntities(false, maxResults, firstResult);
    }

    private List<Bodega> findBodegaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bodega.class));
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

    public Bodega findBodega(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bodega.class, id);
        } finally {
            em.close();
        }
    }

    public int getBodegaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bodega> rt = cq.from(Bodega.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
