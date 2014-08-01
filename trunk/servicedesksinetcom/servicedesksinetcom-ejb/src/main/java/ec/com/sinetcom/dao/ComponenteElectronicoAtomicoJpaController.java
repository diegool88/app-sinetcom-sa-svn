/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import ec.com.sinetcom.orm.ComponenteElectronicoAtomico;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.ParametrosDeProducto;
import java.util.ArrayList;
import java.util.List;
import ec.com.sinetcom.orm.ItemProducto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class ComponenteElectronicoAtomicoJpaController implements Serializable {

    public ComponenteElectronicoAtomicoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComponenteElectronicoAtomico componenteElectronicoAtomico) throws RollbackFailureException, Exception {
        if (componenteElectronicoAtomico.getParametrosDeProductoList() == null) {
            componenteElectronicoAtomico.setParametrosDeProductoList(new ArrayList<ParametrosDeProducto>());
        }
        if (componenteElectronicoAtomico.getItemProductoList() == null) {
            componenteElectronicoAtomico.setItemProductoList(new ArrayList<ItemProducto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<ParametrosDeProducto> attachedParametrosDeProductoList = new ArrayList<ParametrosDeProducto>();
            for (ParametrosDeProducto parametrosDeProductoListParametrosDeProductoToAttach : componenteElectronicoAtomico.getParametrosDeProductoList()) {
                parametrosDeProductoListParametrosDeProductoToAttach = em.getReference(parametrosDeProductoListParametrosDeProductoToAttach.getClass(), parametrosDeProductoListParametrosDeProductoToAttach.getId());
                attachedParametrosDeProductoList.add(parametrosDeProductoListParametrosDeProductoToAttach);
            }
            componenteElectronicoAtomico.setParametrosDeProductoList(attachedParametrosDeProductoList);
            List<ItemProducto> attachedItemProductoList = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoListItemProductoToAttach : componenteElectronicoAtomico.getItemProductoList()) {
                itemProductoListItemProductoToAttach = em.getReference(itemProductoListItemProductoToAttach.getClass(), itemProductoListItemProductoToAttach.getNumeroSerial());
                attachedItemProductoList.add(itemProductoListItemProductoToAttach);
            }
            componenteElectronicoAtomico.setItemProductoList(attachedItemProductoList);
            em.persist(componenteElectronicoAtomico);
            for (ParametrosDeProducto parametrosDeProductoListParametrosDeProducto : componenteElectronicoAtomico.getParametrosDeProductoList()) {
                parametrosDeProductoListParametrosDeProducto.getComponenteElectronicoAtomicoList().add(componenteElectronicoAtomico);
                parametrosDeProductoListParametrosDeProducto = em.merge(parametrosDeProductoListParametrosDeProducto);
            }
            for (ItemProducto itemProductoListItemProducto : componenteElectronicoAtomico.getItemProductoList()) {
                ComponenteElectronicoAtomico oldComponenteElectronicoAtomicoidOfItemProductoListItemProducto = itemProductoListItemProducto.getComponenteElectronicoAtomicoid();
                itemProductoListItemProducto.setComponenteElectronicoAtomicoid(componenteElectronicoAtomico);
                itemProductoListItemProducto = em.merge(itemProductoListItemProducto);
                if (oldComponenteElectronicoAtomicoidOfItemProductoListItemProducto != null) {
                    oldComponenteElectronicoAtomicoidOfItemProductoListItemProducto.getItemProductoList().remove(itemProductoListItemProducto);
                    oldComponenteElectronicoAtomicoidOfItemProductoListItemProducto = em.merge(oldComponenteElectronicoAtomicoidOfItemProductoListItemProducto);
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

    public void edit(ComponenteElectronicoAtomico componenteElectronicoAtomico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ComponenteElectronicoAtomico persistentComponenteElectronicoAtomico = em.find(ComponenteElectronicoAtomico.class, componenteElectronicoAtomico.getId());
            List<ParametrosDeProducto> parametrosDeProductoListOld = persistentComponenteElectronicoAtomico.getParametrosDeProductoList();
            List<ParametrosDeProducto> parametrosDeProductoListNew = componenteElectronicoAtomico.getParametrosDeProductoList();
            List<ItemProducto> itemProductoListOld = persistentComponenteElectronicoAtomico.getItemProductoList();
            List<ItemProducto> itemProductoListNew = componenteElectronicoAtomico.getItemProductoList();
            List<ParametrosDeProducto> attachedParametrosDeProductoListNew = new ArrayList<ParametrosDeProducto>();
            for (ParametrosDeProducto parametrosDeProductoListNewParametrosDeProductoToAttach : parametrosDeProductoListNew) {
                parametrosDeProductoListNewParametrosDeProductoToAttach = em.getReference(parametrosDeProductoListNewParametrosDeProductoToAttach.getClass(), parametrosDeProductoListNewParametrosDeProductoToAttach.getId());
                attachedParametrosDeProductoListNew.add(parametrosDeProductoListNewParametrosDeProductoToAttach);
            }
            parametrosDeProductoListNew = attachedParametrosDeProductoListNew;
            componenteElectronicoAtomico.setParametrosDeProductoList(parametrosDeProductoListNew);
            List<ItemProducto> attachedItemProductoListNew = new ArrayList<ItemProducto>();
            for (ItemProducto itemProductoListNewItemProductoToAttach : itemProductoListNew) {
                itemProductoListNewItemProductoToAttach = em.getReference(itemProductoListNewItemProductoToAttach.getClass(), itemProductoListNewItemProductoToAttach.getNumeroSerial());
                attachedItemProductoListNew.add(itemProductoListNewItemProductoToAttach);
            }
            itemProductoListNew = attachedItemProductoListNew;
            componenteElectronicoAtomico.setItemProductoList(itemProductoListNew);
            componenteElectronicoAtomico = em.merge(componenteElectronicoAtomico);
            for (ParametrosDeProducto parametrosDeProductoListOldParametrosDeProducto : parametrosDeProductoListOld) {
                if (!parametrosDeProductoListNew.contains(parametrosDeProductoListOldParametrosDeProducto)) {
                    parametrosDeProductoListOldParametrosDeProducto.getComponenteElectronicoAtomicoList().remove(componenteElectronicoAtomico);
                    parametrosDeProductoListOldParametrosDeProducto = em.merge(parametrosDeProductoListOldParametrosDeProducto);
                }
            }
            for (ParametrosDeProducto parametrosDeProductoListNewParametrosDeProducto : parametrosDeProductoListNew) {
                if (!parametrosDeProductoListOld.contains(parametrosDeProductoListNewParametrosDeProducto)) {
                    parametrosDeProductoListNewParametrosDeProducto.getComponenteElectronicoAtomicoList().add(componenteElectronicoAtomico);
                    parametrosDeProductoListNewParametrosDeProducto = em.merge(parametrosDeProductoListNewParametrosDeProducto);
                }
            }
            for (ItemProducto itemProductoListOldItemProducto : itemProductoListOld) {
                if (!itemProductoListNew.contains(itemProductoListOldItemProducto)) {
                    itemProductoListOldItemProducto.setComponenteElectronicoAtomicoid(null);
                    itemProductoListOldItemProducto = em.merge(itemProductoListOldItemProducto);
                }
            }
            for (ItemProducto itemProductoListNewItemProducto : itemProductoListNew) {
                if (!itemProductoListOld.contains(itemProductoListNewItemProducto)) {
                    ComponenteElectronicoAtomico oldComponenteElectronicoAtomicoidOfItemProductoListNewItemProducto = itemProductoListNewItemProducto.getComponenteElectronicoAtomicoid();
                    itemProductoListNewItemProducto.setComponenteElectronicoAtomicoid(componenteElectronicoAtomico);
                    itemProductoListNewItemProducto = em.merge(itemProductoListNewItemProducto);
                    if (oldComponenteElectronicoAtomicoidOfItemProductoListNewItemProducto != null && !oldComponenteElectronicoAtomicoidOfItemProductoListNewItemProducto.equals(componenteElectronicoAtomico)) {
                        oldComponenteElectronicoAtomicoidOfItemProductoListNewItemProducto.getItemProductoList().remove(itemProductoListNewItemProducto);
                        oldComponenteElectronicoAtomicoidOfItemProductoListNewItemProducto = em.merge(oldComponenteElectronicoAtomicoidOfItemProductoListNewItemProducto);
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
                Integer id = componenteElectronicoAtomico.getId();
                if (findComponenteElectronicoAtomico(id) == null) {
                    throw new NonexistentEntityException("The componenteElectronicoAtomico with id " + id + " no longer exists.");
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
            ComponenteElectronicoAtomico componenteElectronicoAtomico;
            try {
                componenteElectronicoAtomico = em.getReference(ComponenteElectronicoAtomico.class, id);
                componenteElectronicoAtomico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The componenteElectronicoAtomico with id " + id + " no longer exists.", enfe);
            }
            List<ParametrosDeProducto> parametrosDeProductoList = componenteElectronicoAtomico.getParametrosDeProductoList();
            for (ParametrosDeProducto parametrosDeProductoListParametrosDeProducto : parametrosDeProductoList) {
                parametrosDeProductoListParametrosDeProducto.getComponenteElectronicoAtomicoList().remove(componenteElectronicoAtomico);
                parametrosDeProductoListParametrosDeProducto = em.merge(parametrosDeProductoListParametrosDeProducto);
            }
            List<ItemProducto> itemProductoList = componenteElectronicoAtomico.getItemProductoList();
            for (ItemProducto itemProductoListItemProducto : itemProductoList) {
                itemProductoListItemProducto.setComponenteElectronicoAtomicoid(null);
                itemProductoListItemProducto = em.merge(itemProductoListItemProducto);
            }
            em.remove(componenteElectronicoAtomico);
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

    public List<ComponenteElectronicoAtomico> findComponenteElectronicoAtomicoEntities() {
        return findComponenteElectronicoAtomicoEntities(true, -1, -1);
    }

    public List<ComponenteElectronicoAtomico> findComponenteElectronicoAtomicoEntities(int maxResults, int firstResult) {
        return findComponenteElectronicoAtomicoEntities(false, maxResults, firstResult);
    }

    private List<ComponenteElectronicoAtomico> findComponenteElectronicoAtomicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComponenteElectronicoAtomico.class));
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

    public ComponenteElectronicoAtomico findComponenteElectronicoAtomico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComponenteElectronicoAtomico.class, id);
        } finally {
            em.close();
        }
    }

    public int getComponenteElectronicoAtomicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComponenteElectronicoAtomico> rt = cq.from(ComponenteElectronicoAtomico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
