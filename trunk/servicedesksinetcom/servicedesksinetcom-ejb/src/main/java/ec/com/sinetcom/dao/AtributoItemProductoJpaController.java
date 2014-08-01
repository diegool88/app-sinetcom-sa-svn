/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.PreexistingEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import ec.com.sinetcom.orm.AtributoItemProducto;
import ec.com.sinetcom.orm.AtributoItemProductoPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.ParametrosDeProducto;
import ec.com.sinetcom.orm.ItemProducto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class AtributoItemProductoJpaController implements Serializable {

    public AtributoItemProductoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AtributoItemProducto atributoItemProducto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (atributoItemProducto.getAtributoItemProductoPK() == null) {
            atributoItemProducto.setAtributoItemProductoPK(new AtributoItemProductoPK());
        }
        atributoItemProducto.getAtributoItemProductoPK().setParametrosDeProductoid(atributoItemProducto.getParametrosDeProducto().getId());
        atributoItemProducto.getAtributoItemProductoPK().setItemProductonumeroSerial(atributoItemProducto.getItemProducto().getNumeroSerial());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ParametrosDeProducto parametrosDeProducto = atributoItemProducto.getParametrosDeProducto();
            if (parametrosDeProducto != null) {
                parametrosDeProducto = em.getReference(parametrosDeProducto.getClass(), parametrosDeProducto.getId());
                atributoItemProducto.setParametrosDeProducto(parametrosDeProducto);
            }
            ItemProducto itemProducto = atributoItemProducto.getItemProducto();
            if (itemProducto != null) {
                itemProducto = em.getReference(itemProducto.getClass(), itemProducto.getNumeroSerial());
                atributoItemProducto.setItemProducto(itemProducto);
            }
            em.persist(atributoItemProducto);
            if (parametrosDeProducto != null) {
                parametrosDeProducto.getAtributoItemProductoList().add(atributoItemProducto);
                parametrosDeProducto = em.merge(parametrosDeProducto);
            }
            if (itemProducto != null) {
                itemProducto.getAtributoItemProductoList().add(atributoItemProducto);
                itemProducto = em.merge(itemProducto);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAtributoItemProducto(atributoItemProducto.getAtributoItemProductoPK()) != null) {
                throw new PreexistingEntityException("AtributoItemProducto " + atributoItemProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AtributoItemProducto atributoItemProducto) throws NonexistentEntityException, RollbackFailureException, Exception {
        atributoItemProducto.getAtributoItemProductoPK().setParametrosDeProductoid(atributoItemProducto.getParametrosDeProducto().getId());
        atributoItemProducto.getAtributoItemProductoPK().setItemProductonumeroSerial(atributoItemProducto.getItemProducto().getNumeroSerial());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AtributoItemProducto persistentAtributoItemProducto = em.find(AtributoItemProducto.class, atributoItemProducto.getAtributoItemProductoPK());
            ParametrosDeProducto parametrosDeProductoOld = persistentAtributoItemProducto.getParametrosDeProducto();
            ParametrosDeProducto parametrosDeProductoNew = atributoItemProducto.getParametrosDeProducto();
            ItemProducto itemProductoOld = persistentAtributoItemProducto.getItemProducto();
            ItemProducto itemProductoNew = atributoItemProducto.getItemProducto();
            if (parametrosDeProductoNew != null) {
                parametrosDeProductoNew = em.getReference(parametrosDeProductoNew.getClass(), parametrosDeProductoNew.getId());
                atributoItemProducto.setParametrosDeProducto(parametrosDeProductoNew);
            }
            if (itemProductoNew != null) {
                itemProductoNew = em.getReference(itemProductoNew.getClass(), itemProductoNew.getNumeroSerial());
                atributoItemProducto.setItemProducto(itemProductoNew);
            }
            atributoItemProducto = em.merge(atributoItemProducto);
            if (parametrosDeProductoOld != null && !parametrosDeProductoOld.equals(parametrosDeProductoNew)) {
                parametrosDeProductoOld.getAtributoItemProductoList().remove(atributoItemProducto);
                parametrosDeProductoOld = em.merge(parametrosDeProductoOld);
            }
            if (parametrosDeProductoNew != null && !parametrosDeProductoNew.equals(parametrosDeProductoOld)) {
                parametrosDeProductoNew.getAtributoItemProductoList().add(atributoItemProducto);
                parametrosDeProductoNew = em.merge(parametrosDeProductoNew);
            }
            if (itemProductoOld != null && !itemProductoOld.equals(itemProductoNew)) {
                itemProductoOld.getAtributoItemProductoList().remove(atributoItemProducto);
                itemProductoOld = em.merge(itemProductoOld);
            }
            if (itemProductoNew != null && !itemProductoNew.equals(itemProductoOld)) {
                itemProductoNew.getAtributoItemProductoList().add(atributoItemProducto);
                itemProductoNew = em.merge(itemProductoNew);
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
                AtributoItemProductoPK id = atributoItemProducto.getAtributoItemProductoPK();
                if (findAtributoItemProducto(id) == null) {
                    throw new NonexistentEntityException("The atributoItemProducto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AtributoItemProductoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AtributoItemProducto atributoItemProducto;
            try {
                atributoItemProducto = em.getReference(AtributoItemProducto.class, id);
                atributoItemProducto.getAtributoItemProductoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atributoItemProducto with id " + id + " no longer exists.", enfe);
            }
            ParametrosDeProducto parametrosDeProducto = atributoItemProducto.getParametrosDeProducto();
            if (parametrosDeProducto != null) {
                parametrosDeProducto.getAtributoItemProductoList().remove(atributoItemProducto);
                parametrosDeProducto = em.merge(parametrosDeProducto);
            }
            ItemProducto itemProducto = atributoItemProducto.getItemProducto();
            if (itemProducto != null) {
                itemProducto.getAtributoItemProductoList().remove(atributoItemProducto);
                itemProducto = em.merge(itemProducto);
            }
            em.remove(atributoItemProducto);
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

    public List<AtributoItemProducto> findAtributoItemProductoEntities() {
        return findAtributoItemProductoEntities(true, -1, -1);
    }

    public List<AtributoItemProducto> findAtributoItemProductoEntities(int maxResults, int firstResult) {
        return findAtributoItemProductoEntities(false, maxResults, firstResult);
    }

    private List<AtributoItemProducto> findAtributoItemProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AtributoItemProducto.class));
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

    public AtributoItemProducto findAtributoItemProducto(AtributoItemProductoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AtributoItemProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtributoItemProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AtributoItemProducto> rt = cq.from(AtributoItemProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
