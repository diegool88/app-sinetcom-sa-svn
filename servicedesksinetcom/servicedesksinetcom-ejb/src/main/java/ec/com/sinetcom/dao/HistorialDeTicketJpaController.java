/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.orm.Ticket;
import ec.com.sinetcom.orm.EventoTicket;
import ec.com.sinetcom.orm.HistorialDeTicket;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class HistorialDeTicketJpaController implements Serializable {

    public HistorialDeTicketJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistorialDeTicket historialDeTicket) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuarioid = historialDeTicket.getUsuarioid();
            if (usuarioid != null) {
                usuarioid = em.getReference(usuarioid.getClass(), usuarioid.getId());
                historialDeTicket.setUsuarioid(usuarioid);
            }
            Ticket ticketticketNumber = historialDeTicket.getTicketticketNumber();
            if (ticketticketNumber != null) {
                ticketticketNumber = em.getReference(ticketticketNumber.getClass(), ticketticketNumber.getTicketNumber());
                historialDeTicket.setTicketticketNumber(ticketticketNumber);
            }
            EventoTicket eventoTicketcodigo = historialDeTicket.getEventoTicketcodigo();
            if (eventoTicketcodigo != null) {
                eventoTicketcodigo = em.getReference(eventoTicketcodigo.getClass(), eventoTicketcodigo.getCodigo());
                historialDeTicket.setEventoTicketcodigo(eventoTicketcodigo);
            }
            em.persist(historialDeTicket);
            if (usuarioid != null) {
                usuarioid.getHistorialDeTicketList().add(historialDeTicket);
                usuarioid = em.merge(usuarioid);
            }
            if (ticketticketNumber != null) {
                ticketticketNumber.getHistorialDeTicketList().add(historialDeTicket);
                ticketticketNumber = em.merge(ticketticketNumber);
            }
            if (eventoTicketcodigo != null) {
                eventoTicketcodigo.getHistorialDeTicketList().add(historialDeTicket);
                eventoTicketcodigo = em.merge(eventoTicketcodigo);
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

    public void edit(HistorialDeTicket historialDeTicket) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HistorialDeTicket persistentHistorialDeTicket = em.find(HistorialDeTicket.class, historialDeTicket.getId());
            Usuario usuarioidOld = persistentHistorialDeTicket.getUsuarioid();
            Usuario usuarioidNew = historialDeTicket.getUsuarioid();
            Ticket ticketticketNumberOld = persistentHistorialDeTicket.getTicketticketNumber();
            Ticket ticketticketNumberNew = historialDeTicket.getTicketticketNumber();
            EventoTicket eventoTicketcodigoOld = persistentHistorialDeTicket.getEventoTicketcodigo();
            EventoTicket eventoTicketcodigoNew = historialDeTicket.getEventoTicketcodigo();
            if (usuarioidNew != null) {
                usuarioidNew = em.getReference(usuarioidNew.getClass(), usuarioidNew.getId());
                historialDeTicket.setUsuarioid(usuarioidNew);
            }
            if (ticketticketNumberNew != null) {
                ticketticketNumberNew = em.getReference(ticketticketNumberNew.getClass(), ticketticketNumberNew.getTicketNumber());
                historialDeTicket.setTicketticketNumber(ticketticketNumberNew);
            }
            if (eventoTicketcodigoNew != null) {
                eventoTicketcodigoNew = em.getReference(eventoTicketcodigoNew.getClass(), eventoTicketcodigoNew.getCodigo());
                historialDeTicket.setEventoTicketcodigo(eventoTicketcodigoNew);
            }
            historialDeTicket = em.merge(historialDeTicket);
            if (usuarioidOld != null && !usuarioidOld.equals(usuarioidNew)) {
                usuarioidOld.getHistorialDeTicketList().remove(historialDeTicket);
                usuarioidOld = em.merge(usuarioidOld);
            }
            if (usuarioidNew != null && !usuarioidNew.equals(usuarioidOld)) {
                usuarioidNew.getHistorialDeTicketList().add(historialDeTicket);
                usuarioidNew = em.merge(usuarioidNew);
            }
            if (ticketticketNumberOld != null && !ticketticketNumberOld.equals(ticketticketNumberNew)) {
                ticketticketNumberOld.getHistorialDeTicketList().remove(historialDeTicket);
                ticketticketNumberOld = em.merge(ticketticketNumberOld);
            }
            if (ticketticketNumberNew != null && !ticketticketNumberNew.equals(ticketticketNumberOld)) {
                ticketticketNumberNew.getHistorialDeTicketList().add(historialDeTicket);
                ticketticketNumberNew = em.merge(ticketticketNumberNew);
            }
            if (eventoTicketcodigoOld != null && !eventoTicketcodigoOld.equals(eventoTicketcodigoNew)) {
                eventoTicketcodigoOld.getHistorialDeTicketList().remove(historialDeTicket);
                eventoTicketcodigoOld = em.merge(eventoTicketcodigoOld);
            }
            if (eventoTicketcodigoNew != null && !eventoTicketcodigoNew.equals(eventoTicketcodigoOld)) {
                eventoTicketcodigoNew.getHistorialDeTicketList().add(historialDeTicket);
                eventoTicketcodigoNew = em.merge(eventoTicketcodigoNew);
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
                Integer id = historialDeTicket.getId();
                if (findHistorialDeTicket(id) == null) {
                    throw new NonexistentEntityException("The historialDeTicket with id " + id + " no longer exists.");
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
            HistorialDeTicket historialDeTicket;
            try {
                historialDeTicket = em.getReference(HistorialDeTicket.class, id);
                historialDeTicket.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialDeTicket with id " + id + " no longer exists.", enfe);
            }
            Usuario usuarioid = historialDeTicket.getUsuarioid();
            if (usuarioid != null) {
                usuarioid.getHistorialDeTicketList().remove(historialDeTicket);
                usuarioid = em.merge(usuarioid);
            }
            Ticket ticketticketNumber = historialDeTicket.getTicketticketNumber();
            if (ticketticketNumber != null) {
                ticketticketNumber.getHistorialDeTicketList().remove(historialDeTicket);
                ticketticketNumber = em.merge(ticketticketNumber);
            }
            EventoTicket eventoTicketcodigo = historialDeTicket.getEventoTicketcodigo();
            if (eventoTicketcodigo != null) {
                eventoTicketcodigo.getHistorialDeTicketList().remove(historialDeTicket);
                eventoTicketcodigo = em.merge(eventoTicketcodigo);
            }
            em.remove(historialDeTicket);
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

    public List<HistorialDeTicket> findHistorialDeTicketEntities() {
        return findHistorialDeTicketEntities(true, -1, -1);
    }

    public List<HistorialDeTicket> findHistorialDeTicketEntities(int maxResults, int firstResult) {
        return findHistorialDeTicketEntities(false, maxResults, firstResult);
    }

    private List<HistorialDeTicket> findHistorialDeTicketEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistorialDeTicket.class));
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

    public HistorialDeTicket findHistorialDeTicket(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistorialDeTicket.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialDeTicketCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistorialDeTicket> rt = cq.from(HistorialDeTicket.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
