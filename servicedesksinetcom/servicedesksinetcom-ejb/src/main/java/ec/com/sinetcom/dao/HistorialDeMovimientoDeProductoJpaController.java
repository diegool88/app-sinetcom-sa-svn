/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import ec.com.sinetcom.orm.HistorialDeMovimientoDeProducto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.orm.ItemProducto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class HistorialDeMovimientoDeProductoJpaController implements Serializable {

    public HistorialDeMovimientoDeProductoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistorialDeMovimientoDeProducto historialDeMovimientoDeProducto) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuarioid = historialDeMovimientoDeProducto.getUsuarioid();
            if (usuarioid != null) {
                usuarioid = em.getReference(usuarioid.getClass(), usuarioid.getId());
                historialDeMovimientoDeProducto.setUsuarioid(usuarioid);
            }
            ItemProducto itemProductonumeroSerial = historialDeMovimientoDeProducto.getItemProductonumeroSerial();
            if (itemProductonumeroSerial != null) {
                itemProductonumeroSerial = em.getReference(itemProductonumeroSerial.getClass(), itemProductonumeroSerial.getNumeroSerial());
                historialDeMovimientoDeProducto.setItemProductonumeroSerial(itemProductonumeroSerial);
            }
            em.persist(historialDeMovimientoDeProducto);
            if (usuarioid != null) {
                usuarioid.getHistorialDeMovimientoDeProductoList().add(historialDeMovimientoDeProducto);
                usuarioid = em.merge(usuarioid);
            }
            if (itemProductonumeroSerial != null) {
                itemProductonumeroSerial.getHistorialDeMovimientoDeProductoList().add(historialDeMovimientoDeProducto);
                itemProductonumeroSerial = em.merge(itemProductonumeroSerial);
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

    public void edit(HistorialDeMovimientoDeProducto historialDeMovimientoDeProducto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HistorialDeMovimientoDeProducto persistentHistorialDeMovimientoDeProducto = em.find(HistorialDeMovimientoDeProducto.class, historialDeMovimientoDeProducto.getId());
            Usuario usuarioidOld = persistentHistorialDeMovimientoDeProducto.getUsuarioid();
            Usuario usuarioidNew = historialDeMovimientoDeProducto.getUsuarioid();
            ItemProducto itemProductonumeroSerialOld = persistentHistorialDeMovimientoDeProducto.getItemProductonumeroSerial();
            ItemProducto itemProductonumeroSerialNew = historialDeMovimientoDeProducto.getItemProductonumeroSerial();
            if (usuarioidNew != null) {
                usuarioidNew = em.getReference(usuarioidNew.getClass(), usuarioidNew.getId());
                historialDeMovimientoDeProducto.setUsuarioid(usuarioidNew);
            }
            if (itemProductonumeroSerialNew != null) {
                itemProductonumeroSerialNew = em.getReference(itemProductonumeroSerialNew.getClass(), itemProductonumeroSerialNew.getNumeroSerial());
                historialDeMovimientoDeProducto.setItemProductonumeroSerial(itemProductonumeroSerialNew);
            }
            historialDeMovimientoDeProducto = em.merge(historialDeMovimientoDeProducto);
            if (usuarioidOld != null && !usuarioidOld.equals(usuarioidNew)) {
                usuarioidOld.getHistorialDeMovimientoDeProductoList().remove(historialDeMovimientoDeProducto);
                usuarioidOld = em.merge(usuarioidOld);
            }
            if (usuarioidNew != null && !usuarioidNew.equals(usuarioidOld)) {
                usuarioidNew.getHistorialDeMovimientoDeProductoList().add(historialDeMovimientoDeProducto);
                usuarioidNew = em.merge(usuarioidNew);
            }
            if (itemProductonumeroSerialOld != null && !itemProductonumeroSerialOld.equals(itemProductonumeroSerialNew)) {
                itemProductonumeroSerialOld.getHistorialDeMovimientoDeProductoList().remove(historialDeMovimientoDeProducto);
                itemProductonumeroSerialOld = em.merge(itemProductonumeroSerialOld);
            }
            if (itemProductonumeroSerialNew != null && !itemProductonumeroSerialNew.equals(itemProductonumeroSerialOld)) {
                itemProductonumeroSerialNew.getHistorialDeMovimientoDeProductoList().add(historialDeMovimientoDeProducto);
                itemProductonumeroSerialNew = em.merge(itemProductonumeroSerialNew);
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
                Integer id = historialDeMovimientoDeProducto.getId();
                if (findHistorialDeMovimientoDeProducto(id) == null) {
                    throw new NonexistentEntityException("The historialDeMovimientoDeProducto with id " + id + " no longer exists.");
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
            HistorialDeMovimientoDeProducto historialDeMovimientoDeProducto;
            try {
                historialDeMovimientoDeProducto = em.getReference(HistorialDeMovimientoDeProducto.class, id);
                historialDeMovimientoDeProducto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialDeMovimientoDeProducto with id " + id + " no longer exists.", enfe);
            }
            Usuario usuarioid = historialDeMovimientoDeProducto.getUsuarioid();
            if (usuarioid != null) {
                usuarioid.getHistorialDeMovimientoDeProductoList().remove(historialDeMovimientoDeProducto);
                usuarioid = em.merge(usuarioid);
            }
            ItemProducto itemProductonumeroSerial = historialDeMovimientoDeProducto.getItemProductonumeroSerial();
            if (itemProductonumeroSerial != null) {
                itemProductonumeroSerial.getHistorialDeMovimientoDeProductoList().remove(historialDeMovimientoDeProducto);
                itemProductonumeroSerial = em.merge(itemProductonumeroSerial);
            }
            em.remove(historialDeMovimientoDeProducto);
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

    public List<HistorialDeMovimientoDeProducto> findHistorialDeMovimientoDeProductoEntities() {
        return findHistorialDeMovimientoDeProductoEntities(true, -1, -1);
    }

    public List<HistorialDeMovimientoDeProducto> findHistorialDeMovimientoDeProductoEntities(int maxResults, int firstResult) {
        return findHistorialDeMovimientoDeProductoEntities(false, maxResults, firstResult);
    }

    private List<HistorialDeMovimientoDeProducto> findHistorialDeMovimientoDeProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistorialDeMovimientoDeProducto.class));
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

    public HistorialDeMovimientoDeProducto findHistorialDeMovimientoDeProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistorialDeMovimientoDeProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialDeMovimientoDeProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistorialDeMovimientoDeProducto> rt = cq.from(HistorialDeMovimientoDeProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
