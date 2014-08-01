/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.IllegalOrphanException;
import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import ec.com.sinetcom.orm.EventoTicket;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.HistorialDeTicket;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class EventoTicketJpaController implements Serializable {

    public EventoTicketJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EventoTicket eventoTicket) throws RollbackFailureException, Exception {
        if (eventoTicket.getHistorialDeTicketList() == null) {
            eventoTicket.setHistorialDeTicketList(new ArrayList<HistorialDeTicket>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<HistorialDeTicket> attachedHistorialDeTicketList = new ArrayList<HistorialDeTicket>();
            for (HistorialDeTicket historialDeTicketListHistorialDeTicketToAttach : eventoTicket.getHistorialDeTicketList()) {
                historialDeTicketListHistorialDeTicketToAttach = em.getReference(historialDeTicketListHistorialDeTicketToAttach.getClass(), historialDeTicketListHistorialDeTicketToAttach.getId());
                attachedHistorialDeTicketList.add(historialDeTicketListHistorialDeTicketToAttach);
            }
            eventoTicket.setHistorialDeTicketList(attachedHistorialDeTicketList);
            em.persist(eventoTicket);
            for (HistorialDeTicket historialDeTicketListHistorialDeTicket : eventoTicket.getHistorialDeTicketList()) {
                EventoTicket oldEventoTicketcodigoOfHistorialDeTicketListHistorialDeTicket = historialDeTicketListHistorialDeTicket.getEventoTicketcodigo();
                historialDeTicketListHistorialDeTicket.setEventoTicketcodigo(eventoTicket);
                historialDeTicketListHistorialDeTicket = em.merge(historialDeTicketListHistorialDeTicket);
                if (oldEventoTicketcodigoOfHistorialDeTicketListHistorialDeTicket != null) {
                    oldEventoTicketcodigoOfHistorialDeTicketListHistorialDeTicket.getHistorialDeTicketList().remove(historialDeTicketListHistorialDeTicket);
                    oldEventoTicketcodigoOfHistorialDeTicketListHistorialDeTicket = em.merge(oldEventoTicketcodigoOfHistorialDeTicketListHistorialDeTicket);
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

    public void edit(EventoTicket eventoTicket) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EventoTicket persistentEventoTicket = em.find(EventoTicket.class, eventoTicket.getCodigo());
            List<HistorialDeTicket> historialDeTicketListOld = persistentEventoTicket.getHistorialDeTicketList();
            List<HistorialDeTicket> historialDeTicketListNew = eventoTicket.getHistorialDeTicketList();
            List<String> illegalOrphanMessages = null;
            for (HistorialDeTicket historialDeTicketListOldHistorialDeTicket : historialDeTicketListOld) {
                if (!historialDeTicketListNew.contains(historialDeTicketListOldHistorialDeTicket)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HistorialDeTicket " + historialDeTicketListOldHistorialDeTicket + " since its eventoTicketcodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<HistorialDeTicket> attachedHistorialDeTicketListNew = new ArrayList<HistorialDeTicket>();
            for (HistorialDeTicket historialDeTicketListNewHistorialDeTicketToAttach : historialDeTicketListNew) {
                historialDeTicketListNewHistorialDeTicketToAttach = em.getReference(historialDeTicketListNewHistorialDeTicketToAttach.getClass(), historialDeTicketListNewHistorialDeTicketToAttach.getId());
                attachedHistorialDeTicketListNew.add(historialDeTicketListNewHistorialDeTicketToAttach);
            }
            historialDeTicketListNew = attachedHistorialDeTicketListNew;
            eventoTicket.setHistorialDeTicketList(historialDeTicketListNew);
            eventoTicket = em.merge(eventoTicket);
            for (HistorialDeTicket historialDeTicketListNewHistorialDeTicket : historialDeTicketListNew) {
                if (!historialDeTicketListOld.contains(historialDeTicketListNewHistorialDeTicket)) {
                    EventoTicket oldEventoTicketcodigoOfHistorialDeTicketListNewHistorialDeTicket = historialDeTicketListNewHistorialDeTicket.getEventoTicketcodigo();
                    historialDeTicketListNewHistorialDeTicket.setEventoTicketcodigo(eventoTicket);
                    historialDeTicketListNewHistorialDeTicket = em.merge(historialDeTicketListNewHistorialDeTicket);
                    if (oldEventoTicketcodigoOfHistorialDeTicketListNewHistorialDeTicket != null && !oldEventoTicketcodigoOfHistorialDeTicketListNewHistorialDeTicket.equals(eventoTicket)) {
                        oldEventoTicketcodigoOfHistorialDeTicketListNewHistorialDeTicket.getHistorialDeTicketList().remove(historialDeTicketListNewHistorialDeTicket);
                        oldEventoTicketcodigoOfHistorialDeTicketListNewHistorialDeTicket = em.merge(oldEventoTicketcodigoOfHistorialDeTicketListNewHistorialDeTicket);
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
                Integer id = eventoTicket.getCodigo();
                if (findEventoTicket(id) == null) {
                    throw new NonexistentEntityException("The eventoTicket with id " + id + " no longer exists.");
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
            EventoTicket eventoTicket;
            try {
                eventoTicket = em.getReference(EventoTicket.class, id);
                eventoTicket.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eventoTicket with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<HistorialDeTicket> historialDeTicketListOrphanCheck = eventoTicket.getHistorialDeTicketList();
            for (HistorialDeTicket historialDeTicketListOrphanCheckHistorialDeTicket : historialDeTicketListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EventoTicket (" + eventoTicket + ") cannot be destroyed since the HistorialDeTicket " + historialDeTicketListOrphanCheckHistorialDeTicket + " in its historialDeTicketList field has a non-nullable eventoTicketcodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(eventoTicket);
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

    public List<EventoTicket> findEventoTicketEntities() {
        return findEventoTicketEntities(true, -1, -1);
    }

    public List<EventoTicket> findEventoTicketEntities(int maxResults, int firstResult) {
        return findEventoTicketEntities(false, maxResults, firstResult);
    }

    private List<EventoTicket> findEventoTicketEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EventoTicket.class));
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

    public EventoTicket findEventoTicket(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EventoTicket.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventoTicketCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EventoTicket> rt = cq.from(EventoTicket.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
