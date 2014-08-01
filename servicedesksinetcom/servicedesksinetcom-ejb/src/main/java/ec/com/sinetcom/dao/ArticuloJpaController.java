/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import ec.com.sinetcom.orm.Articulo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.orm.Ticket;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class ArticuloJpaController implements Serializable {

    public ArticuloJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articulo articulo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario para = articulo.getPara();
            if (para != null) {
                para = em.getReference(para.getClass(), para.getId());
                articulo.setPara(para);
            }
            Usuario de = articulo.getDe();
            if (de != null) {
                de = em.getReference(de.getClass(), de.getId());
                articulo.setDe(de);
            }
            Ticket ticketticketNumber = articulo.getTicketticketNumber();
            if (ticketticketNumber != null) {
                ticketticketNumber = em.getReference(ticketticketNumber.getClass(), ticketticketNumber.getTicketNumber());
                articulo.setTicketticketNumber(ticketticketNumber);
            }
            em.persist(articulo);
            if (para != null) {
                para.getArticuloList().add(articulo);
                para = em.merge(para);
            }
            if (de != null) {
                de.getArticuloList().add(articulo);
                de = em.merge(de);
            }
            if (ticketticketNumber != null) {
                ticketticketNumber.getArticuloList().add(articulo);
                ticketticketNumber = em.merge(ticketticketNumber);
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

    public void edit(Articulo articulo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Articulo persistentArticulo = em.find(Articulo.class, articulo.getId());
            Usuario paraOld = persistentArticulo.getPara();
            Usuario paraNew = articulo.getPara();
            Usuario deOld = persistentArticulo.getDe();
            Usuario deNew = articulo.getDe();
            Ticket ticketticketNumberOld = persistentArticulo.getTicketticketNumber();
            Ticket ticketticketNumberNew = articulo.getTicketticketNumber();
            if (paraNew != null) {
                paraNew = em.getReference(paraNew.getClass(), paraNew.getId());
                articulo.setPara(paraNew);
            }
            if (deNew != null) {
                deNew = em.getReference(deNew.getClass(), deNew.getId());
                articulo.setDe(deNew);
            }
            if (ticketticketNumberNew != null) {
                ticketticketNumberNew = em.getReference(ticketticketNumberNew.getClass(), ticketticketNumberNew.getTicketNumber());
                articulo.setTicketticketNumber(ticketticketNumberNew);
            }
            articulo = em.merge(articulo);
            if (paraOld != null && !paraOld.equals(paraNew)) {
                paraOld.getArticuloList().remove(articulo);
                paraOld = em.merge(paraOld);
            }
            if (paraNew != null && !paraNew.equals(paraOld)) {
                paraNew.getArticuloList().add(articulo);
                paraNew = em.merge(paraNew);
            }
            if (deOld != null && !deOld.equals(deNew)) {
                deOld.getArticuloList().remove(articulo);
                deOld = em.merge(deOld);
            }
            if (deNew != null && !deNew.equals(deOld)) {
                deNew.getArticuloList().add(articulo);
                deNew = em.merge(deNew);
            }
            if (ticketticketNumberOld != null && !ticketticketNumberOld.equals(ticketticketNumberNew)) {
                ticketticketNumberOld.getArticuloList().remove(articulo);
                ticketticketNumberOld = em.merge(ticketticketNumberOld);
            }
            if (ticketticketNumberNew != null && !ticketticketNumberNew.equals(ticketticketNumberOld)) {
                ticketticketNumberNew.getArticuloList().add(articulo);
                ticketticketNumberNew = em.merge(ticketticketNumberNew);
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
                Integer id = articulo.getId();
                if (findArticulo(id) == null) {
                    throw new NonexistentEntityException("The articulo with id " + id + " no longer exists.");
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
            Articulo articulo;
            try {
                articulo = em.getReference(Articulo.class, id);
                articulo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articulo with id " + id + " no longer exists.", enfe);
            }
            Usuario para = articulo.getPara();
            if (para != null) {
                para.getArticuloList().remove(articulo);
                para = em.merge(para);
            }
            Usuario de = articulo.getDe();
            if (de != null) {
                de.getArticuloList().remove(articulo);
                de = em.merge(de);
            }
            Ticket ticketticketNumber = articulo.getTicketticketNumber();
            if (ticketticketNumber != null) {
                ticketticketNumber.getArticuloList().remove(articulo);
                ticketticketNumber = em.merge(ticketticketNumber);
            }
            em.remove(articulo);
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

    public List<Articulo> findArticuloEntities() {
        return findArticuloEntities(true, -1, -1);
    }

    public List<Articulo> findArticuloEntities(int maxResults, int firstResult) {
        return findArticuloEntities(false, maxResults, firstResult);
    }

    private List<Articulo> findArticuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Articulo.class));
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

    public Articulo findArticulo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Articulo> rt = cq.from(Articulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
