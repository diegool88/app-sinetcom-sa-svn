/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.IllegalOrphanException;
import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import ec.com.sinetcom.orm.PrioridadTicket;
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
public class PrioridadTicketJpaController implements Serializable {

    public PrioridadTicketJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PrioridadTicket prioridadTicket) throws RollbackFailureException, Exception {
        if (prioridadTicket.getTicketList() == null) {
            prioridadTicket.setTicketList(new ArrayList<Ticket>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Ticket> attachedTicketList = new ArrayList<Ticket>();
            for (Ticket ticketListTicketToAttach : prioridadTicket.getTicketList()) {
                ticketListTicketToAttach = em.getReference(ticketListTicketToAttach.getClass(), ticketListTicketToAttach.getTicketNumber());
                attachedTicketList.add(ticketListTicketToAttach);
            }
            prioridadTicket.setTicketList(attachedTicketList);
            em.persist(prioridadTicket);
            for (Ticket ticketListTicket : prioridadTicket.getTicketList()) {
                PrioridadTicket oldPrioridadTicketcodigoOfTicketListTicket = ticketListTicket.getPrioridadTicketcodigo();
                ticketListTicket.setPrioridadTicketcodigo(prioridadTicket);
                ticketListTicket = em.merge(ticketListTicket);
                if (oldPrioridadTicketcodigoOfTicketListTicket != null) {
                    oldPrioridadTicketcodigoOfTicketListTicket.getTicketList().remove(ticketListTicket);
                    oldPrioridadTicketcodigoOfTicketListTicket = em.merge(oldPrioridadTicketcodigoOfTicketListTicket);
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

    public void edit(PrioridadTicket prioridadTicket) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PrioridadTicket persistentPrioridadTicket = em.find(PrioridadTicket.class, prioridadTicket.getCodigo());
            List<Ticket> ticketListOld = persistentPrioridadTicket.getTicketList();
            List<Ticket> ticketListNew = prioridadTicket.getTicketList();
            List<String> illegalOrphanMessages = null;
            for (Ticket ticketListOldTicket : ticketListOld) {
                if (!ticketListNew.contains(ticketListOldTicket)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ticket " + ticketListOldTicket + " since its prioridadTicketcodigo field is not nullable.");
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
            prioridadTicket.setTicketList(ticketListNew);
            prioridadTicket = em.merge(prioridadTicket);
            for (Ticket ticketListNewTicket : ticketListNew) {
                if (!ticketListOld.contains(ticketListNewTicket)) {
                    PrioridadTicket oldPrioridadTicketcodigoOfTicketListNewTicket = ticketListNewTicket.getPrioridadTicketcodigo();
                    ticketListNewTicket.setPrioridadTicketcodigo(prioridadTicket);
                    ticketListNewTicket = em.merge(ticketListNewTicket);
                    if (oldPrioridadTicketcodigoOfTicketListNewTicket != null && !oldPrioridadTicketcodigoOfTicketListNewTicket.equals(prioridadTicket)) {
                        oldPrioridadTicketcodigoOfTicketListNewTicket.getTicketList().remove(ticketListNewTicket);
                        oldPrioridadTicketcodigoOfTicketListNewTicket = em.merge(oldPrioridadTicketcodigoOfTicketListNewTicket);
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
                Integer id = prioridadTicket.getCodigo();
                if (findPrioridadTicket(id) == null) {
                    throw new NonexistentEntityException("The prioridadTicket with id " + id + " no longer exists.");
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
            PrioridadTicket prioridadTicket;
            try {
                prioridadTicket = em.getReference(PrioridadTicket.class, id);
                prioridadTicket.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prioridadTicket with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ticket> ticketListOrphanCheck = prioridadTicket.getTicketList();
            for (Ticket ticketListOrphanCheckTicket : ticketListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PrioridadTicket (" + prioridadTicket + ") cannot be destroyed since the Ticket " + ticketListOrphanCheckTicket + " in its ticketList field has a non-nullable prioridadTicketcodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(prioridadTicket);
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

    public List<PrioridadTicket> findPrioridadTicketEntities() {
        return findPrioridadTicketEntities(true, -1, -1);
    }

    public List<PrioridadTicket> findPrioridadTicketEntities(int maxResults, int firstResult) {
        return findPrioridadTicketEntities(false, maxResults, firstResult);
    }

    private List<PrioridadTicket> findPrioridadTicketEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrioridadTicket.class));
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

    public PrioridadTicket findPrioridadTicket(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrioridadTicket.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrioridadTicketCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrioridadTicket> rt = cq.from(PrioridadTicket.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
