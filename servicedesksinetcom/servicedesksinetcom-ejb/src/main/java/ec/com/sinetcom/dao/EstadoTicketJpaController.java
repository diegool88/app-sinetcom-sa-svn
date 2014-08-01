/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.IllegalOrphanException;
import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import ec.com.sinetcom.orm.EstadoTicket;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.Ticket;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class EstadoTicketJpaController implements Serializable {

    public EstadoTicketJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoTicket estadoTicket) throws RollbackFailureException, Exception {
        if (estadoTicket.getTicketList() == null) {
            estadoTicket.setTicketList(new ArrayList<Ticket>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Ticket> attachedTicketList = new ArrayList<Ticket>();
            for (Ticket ticketListTicketToAttach : estadoTicket.getTicketList()) {
                ticketListTicketToAttach = em.getReference(ticketListTicketToAttach.getClass(), ticketListTicketToAttach.getTicketNumber());
                attachedTicketList.add(ticketListTicketToAttach);
            }
            estadoTicket.setTicketList(attachedTicketList);
            em.persist(estadoTicket);
            for (Ticket ticketListTicket : estadoTicket.getTicketList()) {
                EstadoTicket oldEstadoTicketcodigoOfTicketListTicket = ticketListTicket.getEstadoTicketcodigo();
                ticketListTicket.setEstadoTicketcodigo(estadoTicket);
                ticketListTicket = em.merge(ticketListTicket);
                if (oldEstadoTicketcodigoOfTicketListTicket != null) {
                    oldEstadoTicketcodigoOfTicketListTicket.getTicketList().remove(ticketListTicket);
                    oldEstadoTicketcodigoOfTicketListTicket = em.merge(oldEstadoTicketcodigoOfTicketListTicket);
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

    public void edit(EstadoTicket estadoTicket) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstadoTicket persistentEstadoTicket = em.find(EstadoTicket.class, estadoTicket.getCodigo());
            List<Ticket> ticketListOld = persistentEstadoTicket.getTicketList();
            List<Ticket> ticketListNew = estadoTicket.getTicketList();
            List<String> illegalOrphanMessages = null;
            for (Ticket ticketListOldTicket : ticketListOld) {
                if (!ticketListNew.contains(ticketListOldTicket)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ticket " + ticketListOldTicket + " since its estadoTicketcodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ticket> attachedTicketListNew = new ArrayList<Ticket>();
            for (Ticket ticketListNewTicketToAttach : ticketListNew) {
                ticketListNewTicketToAttach = em.getReference(ticketListNewTicketToAttach.getClass(), ticketListNewTicketToAttach.getTicketNumber());
                attachedTicketListNew.add(ticketListNewTicketToAttach);
            }
            ticketListNew = attachedTicketListNew;
            estadoTicket.setTicketList(ticketListNew);
            estadoTicket = em.merge(estadoTicket);
            for (Ticket ticketListNewTicket : ticketListNew) {
                if (!ticketListOld.contains(ticketListNewTicket)) {
                    EstadoTicket oldEstadoTicketcodigoOfTicketListNewTicket = ticketListNewTicket.getEstadoTicketcodigo();
                    ticketListNewTicket.setEstadoTicketcodigo(estadoTicket);
                    ticketListNewTicket = em.merge(ticketListNewTicket);
                    if (oldEstadoTicketcodigoOfTicketListNewTicket != null && !oldEstadoTicketcodigoOfTicketListNewTicket.equals(estadoTicket)) {
                        oldEstadoTicketcodigoOfTicketListNewTicket.getTicketList().remove(ticketListNewTicket);
                        oldEstadoTicketcodigoOfTicketListNewTicket = em.merge(oldEstadoTicketcodigoOfTicketListNewTicket);
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
                Integer id = estadoTicket.getCodigo();
                if (findEstadoTicket(id) == null) {
                    throw new NonexistentEntityException("The estadoTicket with id " + id + " no longer exists.");
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
            EstadoTicket estadoTicket;
            try {
                estadoTicket = em.getReference(EstadoTicket.class, id);
                estadoTicket.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoTicket with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ticket> ticketListOrphanCheck = estadoTicket.getTicketList();
            for (Ticket ticketListOrphanCheckTicket : ticketListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EstadoTicket (" + estadoTicket + ") cannot be destroyed since the Ticket " + ticketListOrphanCheckTicket + " in its ticketList field has a non-nullable estadoTicketcodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadoTicket);
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

    public List<EstadoTicket> findEstadoTicketEntities() {
        return findEstadoTicketEntities(true, -1, -1);
    }

    public List<EstadoTicket> findEstadoTicketEntities(int maxResults, int firstResult) {
        return findEstadoTicketEntities(false, maxResults, firstResult);
    }

    private List<EstadoTicket> findEstadoTicketEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoTicket.class));
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

    public EstadoTicket findEstadoTicket(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoTicket.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoTicketCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoTicket> rt = cq.from(EstadoTicket.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
