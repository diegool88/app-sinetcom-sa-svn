/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.IllegalOrphanException;
import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import ec.com.sinetcom.orm.Cola;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.Competencias;
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
public class ColaJpaController implements Serializable {

    public ColaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cola cola) throws RollbackFailureException, Exception {
        if (cola.getTicketList() == null) {
            cola.setTicketList(new ArrayList<Ticket>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Competencias competenciasid = cola.getCompetenciasid();
            if (competenciasid != null) {
                competenciasid = em.getReference(competenciasid.getClass(), competenciasid.getId());
                cola.setCompetenciasid(competenciasid);
            }
            List<Ticket> attachedTicketList = new ArrayList<Ticket>();
            for (Ticket ticketListTicketToAttach : cola.getTicketList()) {
                ticketListTicketToAttach = em.getReference(ticketListTicketToAttach.getClass(), ticketListTicketToAttach.getTicketNumber());
                attachedTicketList.add(ticketListTicketToAttach);
            }
            cola.setTicketList(attachedTicketList);
            em.persist(cola);
            if (competenciasid != null) {
                competenciasid.getColaList().add(cola);
                competenciasid = em.merge(competenciasid);
            }
            for (Ticket ticketListTicket : cola.getTicketList()) {
                Cola oldColaidOfTicketListTicket = ticketListTicket.getColaid();
                ticketListTicket.setColaid(cola);
                ticketListTicket = em.merge(ticketListTicket);
                if (oldColaidOfTicketListTicket != null) {
                    oldColaidOfTicketListTicket.getTicketList().remove(ticketListTicket);
                    oldColaidOfTicketListTicket = em.merge(oldColaidOfTicketListTicket);
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

    public void edit(Cola cola) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cola persistentCola = em.find(Cola.class, cola.getId());
            Competencias competenciasidOld = persistentCola.getCompetenciasid();
            Competencias competenciasidNew = cola.getCompetenciasid();
            List<Ticket> ticketListOld = persistentCola.getTicketList();
            List<Ticket> ticketListNew = cola.getTicketList();
            List<String> illegalOrphanMessages = null;
            for (Ticket ticketListOldTicket : ticketListOld) {
                if (!ticketListNew.contains(ticketListOldTicket)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ticket " + ticketListOldTicket + " since its colaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (competenciasidNew != null) {
                competenciasidNew = em.getReference(competenciasidNew.getClass(), competenciasidNew.getId());
                cola.setCompetenciasid(competenciasidNew);
            }
            List<Ticket> attachedTicketListNew = new ArrayList<Ticket>();
            for (Ticket ticketListNewTicketToAttach : ticketListNew) {
                ticketListNewTicketToAttach = em.getReference(ticketListNewTicketToAttach.getClass(), ticketListNewTicketToAttach.getTicketNumber());
                attachedTicketListNew.add(ticketListNewTicketToAttach);
            }
            ticketListNew = attachedTicketListNew;
            cola.setTicketList(ticketListNew);
            cola = em.merge(cola);
            if (competenciasidOld != null && !competenciasidOld.equals(competenciasidNew)) {
                competenciasidOld.getColaList().remove(cola);
                competenciasidOld = em.merge(competenciasidOld);
            }
            if (competenciasidNew != null && !competenciasidNew.equals(competenciasidOld)) {
                competenciasidNew.getColaList().add(cola);
                competenciasidNew = em.merge(competenciasidNew);
            }
            for (Ticket ticketListNewTicket : ticketListNew) {
                if (!ticketListOld.contains(ticketListNewTicket)) {
                    Cola oldColaidOfTicketListNewTicket = ticketListNewTicket.getColaid();
                    ticketListNewTicket.setColaid(cola);
                    ticketListNewTicket = em.merge(ticketListNewTicket);
                    if (oldColaidOfTicketListNewTicket != null && !oldColaidOfTicketListNewTicket.equals(cola)) {
                        oldColaidOfTicketListNewTicket.getTicketList().remove(ticketListNewTicket);
                        oldColaidOfTicketListNewTicket = em.merge(oldColaidOfTicketListNewTicket);
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
                Integer id = cola.getId();
                if (findCola(id) == null) {
                    throw new NonexistentEntityException("The cola with id " + id + " no longer exists.");
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
            Cola cola;
            try {
                cola = em.getReference(Cola.class, id);
                cola.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cola with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ticket> ticketListOrphanCheck = cola.getTicketList();
            for (Ticket ticketListOrphanCheckTicket : ticketListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cola (" + cola + ") cannot be destroyed since the Ticket " + ticketListOrphanCheckTicket + " in its ticketList field has a non-nullable colaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Competencias competenciasid = cola.getCompetenciasid();
            if (competenciasid != null) {
                competenciasid.getColaList().remove(cola);
                competenciasid = em.merge(competenciasid);
            }
            em.remove(cola);
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

    public List<Cola> findColaEntities() {
        return findColaEntities(true, -1, -1);
    }

    public List<Cola> findColaEntities(int maxResults, int firstResult) {
        return findColaEntities(false, maxResults, firstResult);
    }

    private List<Cola> findColaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cola.class));
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

    public Cola findCola(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cola.class, id);
        } finally {
            em.close();
        }
    }

    public int getColaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cola> rt = cq.from(Cola.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
