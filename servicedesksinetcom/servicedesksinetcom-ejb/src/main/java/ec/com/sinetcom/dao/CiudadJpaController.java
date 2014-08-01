/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.IllegalOrphanException;
import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.Provincia;
import ec.com.sinetcom.orm.ClienteDireccion;
import java.util.ArrayList;
import java.util.List;
import ec.com.sinetcom.orm.Bodega;
import ec.com.sinetcom.orm.Ciudad;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class CiudadJpaController implements Serializable {

    public CiudadJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudad ciudad) throws RollbackFailureException, Exception {
        if (ciudad.getClienteDireccionList() == null) {
            ciudad.setClienteDireccionList(new ArrayList<ClienteDireccion>());
        }
        if (ciudad.getBodegaList() == null) {
            ciudad.setBodegaList(new ArrayList<Bodega>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Provincia provinciaid = ciudad.getProvinciaid();
            if (provinciaid != null) {
                provinciaid = em.getReference(provinciaid.getClass(), provinciaid.getId());
                ciudad.setProvinciaid(provinciaid);
            }
            List<ClienteDireccion> attachedClienteDireccionList = new ArrayList<ClienteDireccion>();
            for (ClienteDireccion clienteDireccionListClienteDireccionToAttach : ciudad.getClienteDireccionList()) {
                clienteDireccionListClienteDireccionToAttach = em.getReference(clienteDireccionListClienteDireccionToAttach.getClass(), clienteDireccionListClienteDireccionToAttach.getClienteDireccionPK());
                attachedClienteDireccionList.add(clienteDireccionListClienteDireccionToAttach);
            }
            ciudad.setClienteDireccionList(attachedClienteDireccionList);
            List<Bodega> attachedBodegaList = new ArrayList<Bodega>();
            for (Bodega bodegaListBodegaToAttach : ciudad.getBodegaList()) {
                bodegaListBodegaToAttach = em.getReference(bodegaListBodegaToAttach.getClass(), bodegaListBodegaToAttach.getId());
                attachedBodegaList.add(bodegaListBodegaToAttach);
            }
            ciudad.setBodegaList(attachedBodegaList);
            em.persist(ciudad);
            if (provinciaid != null) {
                provinciaid.getCiudadList().add(ciudad);
                provinciaid = em.merge(provinciaid);
            }
            for (ClienteDireccion clienteDireccionListClienteDireccion : ciudad.getClienteDireccionList()) {
                Ciudad oldCiudadOfClienteDireccionListClienteDireccion = clienteDireccionListClienteDireccion.getCiudad();
                clienteDireccionListClienteDireccion.setCiudad(ciudad);
                clienteDireccionListClienteDireccion = em.merge(clienteDireccionListClienteDireccion);
                if (oldCiudadOfClienteDireccionListClienteDireccion != null) {
                    oldCiudadOfClienteDireccionListClienteDireccion.getClienteDireccionList().remove(clienteDireccionListClienteDireccion);
                    oldCiudadOfClienteDireccionListClienteDireccion = em.merge(oldCiudadOfClienteDireccionListClienteDireccion);
                }
            }
            for (Bodega bodegaListBodega : ciudad.getBodegaList()) {
                Ciudad oldCiudadidOfBodegaListBodega = bodegaListBodega.getCiudadid();
                bodegaListBodega.setCiudadid(ciudad);
                bodegaListBodega = em.merge(bodegaListBodega);
                if (oldCiudadidOfBodegaListBodega != null) {
                    oldCiudadidOfBodegaListBodega.getBodegaList().remove(bodegaListBodega);
                    oldCiudadidOfBodegaListBodega = em.merge(oldCiudadidOfBodegaListBodega);
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

    public void edit(Ciudad ciudad) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ciudad persistentCiudad = em.find(Ciudad.class, ciudad.getId());
            Provincia provinciaidOld = persistentCiudad.getProvinciaid();
            Provincia provinciaidNew = ciudad.getProvinciaid();
            List<ClienteDireccion> clienteDireccionListOld = persistentCiudad.getClienteDireccionList();
            List<ClienteDireccion> clienteDireccionListNew = ciudad.getClienteDireccionList();
            List<Bodega> bodegaListOld = persistentCiudad.getBodegaList();
            List<Bodega> bodegaListNew = ciudad.getBodegaList();
            List<String> illegalOrphanMessages = null;
            for (ClienteDireccion clienteDireccionListOldClienteDireccion : clienteDireccionListOld) {
                if (!clienteDireccionListNew.contains(clienteDireccionListOldClienteDireccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClienteDireccion " + clienteDireccionListOldClienteDireccion + " since its ciudad field is not nullable.");
                }
            }
            for (Bodega bodegaListOldBodega : bodegaListOld) {
                if (!bodegaListNew.contains(bodegaListOldBodega)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bodega " + bodegaListOldBodega + " since its ciudadid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (provinciaidNew != null) {
                provinciaidNew = em.getReference(provinciaidNew.getClass(), provinciaidNew.getId());
                ciudad.setProvinciaid(provinciaidNew);
            }
            List<ClienteDireccion> attachedClienteDireccionListNew = new ArrayList<ClienteDireccion>();
            for (ClienteDireccion clienteDireccionListNewClienteDireccionToAttach : clienteDireccionListNew) {
                clienteDireccionListNewClienteDireccionToAttach = em.getReference(clienteDireccionListNewClienteDireccionToAttach.getClass(), clienteDireccionListNewClienteDireccionToAttach.getClienteDireccionPK());
                attachedClienteDireccionListNew.add(clienteDireccionListNewClienteDireccionToAttach);
            }
            clienteDireccionListNew = attachedClienteDireccionListNew;
            ciudad.setClienteDireccionList(clienteDireccionListNew);
            List<Bodega> attachedBodegaListNew = new ArrayList<Bodega>();
            for (Bodega bodegaListNewBodegaToAttach : bodegaListNew) {
                bodegaListNewBodegaToAttach = em.getReference(bodegaListNewBodegaToAttach.getClass(), bodegaListNewBodegaToAttach.getId());
                attachedBodegaListNew.add(bodegaListNewBodegaToAttach);
            }
            bodegaListNew = attachedBodegaListNew;
            ciudad.setBodegaList(bodegaListNew);
            ciudad = em.merge(ciudad);
            if (provinciaidOld != null && !provinciaidOld.equals(provinciaidNew)) {
                provinciaidOld.getCiudadList().remove(ciudad);
                provinciaidOld = em.merge(provinciaidOld);
            }
            if (provinciaidNew != null && !provinciaidNew.equals(provinciaidOld)) {
                provinciaidNew.getCiudadList().add(ciudad);
                provinciaidNew = em.merge(provinciaidNew);
            }
            for (ClienteDireccion clienteDireccionListNewClienteDireccion : clienteDireccionListNew) {
                if (!clienteDireccionListOld.contains(clienteDireccionListNewClienteDireccion)) {
                    Ciudad oldCiudadOfClienteDireccionListNewClienteDireccion = clienteDireccionListNewClienteDireccion.getCiudad();
                    clienteDireccionListNewClienteDireccion.setCiudad(ciudad);
                    clienteDireccionListNewClienteDireccion = em.merge(clienteDireccionListNewClienteDireccion);
                    if (oldCiudadOfClienteDireccionListNewClienteDireccion != null && !oldCiudadOfClienteDireccionListNewClienteDireccion.equals(ciudad)) {
                        oldCiudadOfClienteDireccionListNewClienteDireccion.getClienteDireccionList().remove(clienteDireccionListNewClienteDireccion);
                        oldCiudadOfClienteDireccionListNewClienteDireccion = em.merge(oldCiudadOfClienteDireccionListNewClienteDireccion);
                    }
                }
            }
            for (Bodega bodegaListNewBodega : bodegaListNew) {
                if (!bodegaListOld.contains(bodegaListNewBodega)) {
                    Ciudad oldCiudadidOfBodegaListNewBodega = bodegaListNewBodega.getCiudadid();
                    bodegaListNewBodega.setCiudadid(ciudad);
                    bodegaListNewBodega = em.merge(bodegaListNewBodega);
                    if (oldCiudadidOfBodegaListNewBodega != null && !oldCiudadidOfBodegaListNewBodega.equals(ciudad)) {
                        oldCiudadidOfBodegaListNewBodega.getBodegaList().remove(bodegaListNewBodega);
                        oldCiudadidOfBodegaListNewBodega = em.merge(oldCiudadidOfBodegaListNewBodega);
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
                Integer id = ciudad.getId();
                if (findCiudad(id) == null) {
                    throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.");
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
            Ciudad ciudad;
            try {
                ciudad = em.getReference(Ciudad.class, id);
                ciudad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ClienteDireccion> clienteDireccionListOrphanCheck = ciudad.getClienteDireccionList();
            for (ClienteDireccion clienteDireccionListOrphanCheckClienteDireccion : clienteDireccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the ClienteDireccion " + clienteDireccionListOrphanCheckClienteDireccion + " in its clienteDireccionList field has a non-nullable ciudad field.");
            }
            List<Bodega> bodegaListOrphanCheck = ciudad.getBodegaList();
            for (Bodega bodegaListOrphanCheckBodega : bodegaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Bodega " + bodegaListOrphanCheckBodega + " in its bodegaList field has a non-nullable ciudadid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Provincia provinciaid = ciudad.getProvinciaid();
            if (provinciaid != null) {
                provinciaid.getCiudadList().remove(ciudad);
                provinciaid = em.merge(provinciaid);
            }
            em.remove(ciudad);
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

    public List<Ciudad> findCiudadEntities() {
        return findCiudadEntities(true, -1, -1);
    }

    public List<Ciudad> findCiudadEntities(int maxResults, int firstResult) {
        return findCiudadEntities(false, maxResults, firstResult);
    }

    private List<Ciudad> findCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudad.class));
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

    public Ciudad findCiudad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudad.class, id);
        } finally {
            em.close();
        }
    }

    public int getCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudad> rt = cq.from(Ciudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
