/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.IllegalOrphanException;
import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import ec.com.sinetcom.orm.CondicionFisica;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class CondicionFisicaJpaController implements Serializable {

    public CondicionFisicaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CondicionFisica condicionFisica) throws RollbackFailureException, Exception {
        if (condicionFisica.getItemProductoList() == null) {
            condicionFisica.setItemProductoList(new ArrayList<ItemProducto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<ItemProducto> attachedItemProductoList = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoListItemProductoToAttach : condicionFisica.getItemProductoList()) {
                itemProductoListItemProductoToAttach = em.getReference(itemProductoListItemProductoToAttach.getClass(), itemProductoListItemProductoToAttach.getNumeroSerial());
                attachedItemProductoList.add(itemProductoListItemProductoToAttach);
            }
            condicionFisica.setItemProductoList(attachedItemProductoList);
            em.persist(condicionFisica);
            for (ItemProducto itemProductoListItemProducto : condicionFisica.getItemProductoList()) {
                CondicionFisica oldCondicionFisicaidOfItemProductoListItemProducto = itemProductoListItemProducto.getCondicionFisicaid();
                itemProductoListItemProducto.setCondicionFisicaid(condicionFisica);
                itemProductoListItemProducto = em.merge(itemProductoListItemProducto);
                if (oldCondicionFisicaidOfItemProductoListItemProducto != null) {
                    oldCondicionFisicaidOfItemProductoListItemProducto.getItemProductoList().remove(itemProductoListItemProducto);
                    oldCondicionFisicaidOfItemProductoListItemProducto = em.merge(oldCondicionFisicaidOfItemProductoListItemProducto);
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

    public void edit(CondicionFisica condicionFisica) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CondicionFisica persistentCondicionFisica = em.find(CondicionFisica.class, condicionFisica.getId());
            List<ItemProducto> itemProductoListOld = persistentCondicionFisica.getItemProductoList();
            List<ItemProducto> itemProductoListNew = condicionFisica.getItemProductoList();
            List<String> illegalOrphanMessages = null;
            for (ItemProducto itemProductoListOldItemProducto : itemProductoListOld) {
                if (!itemProductoListNew.contains(itemProductoListOldItemProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItemProducto " + itemProductoListOldItemProducto + " since its condicionFisicaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ItemProducto> attachedItemProductoListNew = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoListNewItemProductoToAttach : itemProductoListNew) {
                itemProductoListNewItemProductoToAttach = em.getReference(itemProductoListNewItemProductoToAttach.getClass(), itemProductoListNewItemProductoToAttach.getNumeroSerial());
                attachedItemProductoListNew.add(itemProductoListNewItemProductoToAttach);
            }
            itemProductoListNew = attachedItemProductoListNew;
            condicionFisica.setItemProductoList(itemProductoListNew);
            condicionFisica = em.merge(condicionFisica);
            for (ItemProducto itemProductoListNewItemProducto : itemProductoListNew) {
                if (!itemProductoListOld.contains(itemProductoListNewItemProducto)) {
                    CondicionFisica oldCondicionFisicaidOfItemProductoListNewItemProducto = itemProductoListNewItemProducto.getCondicionFisicaid();
                    itemProductoListNewItemProducto.setCondicionFisicaid(condicionFisica);
                    itemProductoListNewItemProducto = em.merge(itemProductoListNewItemProducto);
                    if (oldCondicionFisicaidOfItemProductoListNewItemProducto != null && !oldCondicionFisicaidOfItemProductoListNewItemProducto.equals(condicionFisica)) {
                        oldCondicionFisicaidOfItemProductoListNewItemProducto.getItemProductoList().remove(itemProductoListNewItemProducto);
                        oldCondicionFisicaidOfItemProductoListNewItemProducto = em.merge(oldCondicionFisicaidOfItemProductoListNewItemProducto);
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
                Integer id = condicionFisica.getId();
                if (findCondicionFisica(id) == null) {
                    throw new NonexistentEntityException("The condicionFisica with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CondicionFisica condicionFisica;
            try {
                condicionFisica = em.getReference(CondicionFisica.class, id);
                condicionFisica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The condicionFisica with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ItemProducto> itemProductoListOrphanCheck = condicionFisica.getItemProductoList();
            for (ItemProducto itemProductoListOrphanCheckItemProducto : itemProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CondicionFisica (" + condicionFisica + ") cannot be destroyed since the ItemProducto " + itemProductoListOrphanCheckItemProducto + " in its itemProductoList field has a non-nullable condicionFisicaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(condicionFisica);
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

    public List<CondicionFisica> findCondicionFisicaEntities() {
        return findCondicionFisicaEntities(true, -1, -1);
    }

    public List<CondicionFisica> findCondicionFisicaEntities(int maxResults, int firstResult) {
        return findCondicionFisicaEntities(false, maxResults, firstResult);
    }

    private List<CondicionFisica> findCondicionFisicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CondicionFisica.class));
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

    public CondicionFisica findCondicionFisica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CondicionFisica.class, id);
        } finally {
            em.close();
        }
    }

    public int getCondicionFisicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CondicionFisica> rt = cq.from(CondicionFisica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
