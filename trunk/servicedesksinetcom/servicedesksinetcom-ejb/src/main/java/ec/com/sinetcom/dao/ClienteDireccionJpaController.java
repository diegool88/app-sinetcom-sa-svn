/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.PreexistingEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.Ciudad;
import ec.com.sinetcom.orm.ClienteDireccion;
import ec.com.sinetcom.orm.ClienteDireccionPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class ClienteDireccionJpaController implements Serializable {

    public ClienteDireccionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClienteDireccion clienteDireccion) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (clienteDireccion.getClienteDireccionPK() == null) {
            clienteDireccion.setClienteDireccionPK(new ClienteDireccionPK());
        }
        clienteDireccion.getClienteDireccionPK().setClienteEmpresaruc(clienteDireccion.getClienteEmpresa().getRuc());
        clienteDireccion.getClienteDireccionPK().setCiudadid(clienteDireccion.getCiudad().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ClienteEmpresa clienteEmpresa = clienteDireccion.getClienteEmpresa();
            if (clienteEmpresa != null) {
                clienteEmpresa = em.getReference(clienteEmpresa.getClass(), clienteEmpresa.getRuc());
                clienteDireccion.setClienteEmpresa(clienteEmpresa);
            }
            Ciudad ciudad = clienteDireccion.getCiudad();
            if (ciudad != null) {
                ciudad = em.getReference(ciudad.getClass(), ciudad.getId());
                clienteDireccion.setCiudad(ciudad);
            }
            em.persist(clienteDireccion);
            if (clienteEmpresa != null) {
                clienteEmpresa.getClienteDireccionList().add(clienteDireccion);
                clienteEmpresa = em.merge(clienteEmpresa);
            }
            if (ciudad != null) {
                ciudad.getClienteDireccionList().add(clienteDireccion);
                ciudad = em.merge(ciudad);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findClienteDireccion(clienteDireccion.getClienteDireccionPK()) != null) {
                throw new PreexistingEntityException("ClienteDireccion " + clienteDireccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClienteDireccion clienteDireccion) throws NonexistentEntityException, RollbackFailureException, Exception {
        clienteDireccion.getClienteDireccionPK().setClienteEmpresaruc(clienteDireccion.getClienteEmpresa().getRuc());
        clienteDireccion.getClienteDireccionPK().setCiudadid(clienteDireccion.getCiudad().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ClienteDireccion persistentClienteDireccion = em.find(ClienteDireccion.class, clienteDireccion.getClienteDireccionPK());
            ClienteEmpresa clienteEmpresaOld = persistentClienteDireccion.getClienteEmpresa();
            ClienteEmpresa clienteEmpresaNew = clienteDireccion.getClienteEmpresa();
            Ciudad ciudadOld = persistentClienteDireccion.getCiudad();
            Ciudad ciudadNew = clienteDireccion.getCiudad();
            if (clienteEmpresaNew != null) {
                clienteEmpresaNew = em.getReference(clienteEmpresaNew.getClass(), clienteEmpresaNew.getRuc());
                clienteDireccion.setClienteEmpresa(clienteEmpresaNew);
            }
            if (ciudadNew != null) {
                ciudadNew = em.getReference(ciudadNew.getClass(), ciudadNew.getId());
                clienteDireccion.setCiudad(ciudadNew);
            }
            clienteDireccion = em.merge(clienteDireccion);
            if (clienteEmpresaOld != null && !clienteEmpresaOld.equals(clienteEmpresaNew)) {
                clienteEmpresaOld.getClienteDireccionList().remove(clienteDireccion);
                clienteEmpresaOld = em.merge(clienteEmpresaOld);
            }
            if (clienteEmpresaNew != null && !clienteEmpresaNew.equals(clienteEmpresaOld)) {
                clienteEmpresaNew.getClienteDireccionList().add(clienteDireccion);
                clienteEmpresaNew = em.merge(clienteEmpresaNew);
            }
            if (ciudadOld != null && !ciudadOld.equals(ciudadNew)) {
                ciudadOld.getClienteDireccionList().remove(clienteDireccion);
                ciudadOld = em.merge(ciudadOld);
            }
            if (ciudadNew != null && !ciudadNew.equals(ciudadOld)) {
                ciudadNew.getClienteDireccionList().add(clienteDireccion);
                ciudadNew = em.merge(ciudadNew);
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
                ClienteDireccionPK id = clienteDireccion.getClienteDireccionPK();
                if (findClienteDireccion(id) == null) {
                    throw new NonexistentEntityException("The clienteDireccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ClienteDireccionPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ClienteDireccion clienteDireccion;
            try {
                clienteDireccion = em.getReference(ClienteDireccion.class, id);
                clienteDireccion.getClienteDireccionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clienteDireccion with id " + id + " no longer exists.", enfe);
            }
            ClienteEmpresa clienteEmpresa = clienteDireccion.getClienteEmpresa();
            if (clienteEmpresa != null) {
                clienteEmpresa.getClienteDireccionList().remove(clienteDireccion);
                clienteEmpresa = em.merge(clienteEmpresa);
            }
            Ciudad ciudad = clienteDireccion.getCiudad();
            if (ciudad != null) {
                ciudad.getClienteDireccionList().remove(clienteDireccion);
                ciudad = em.merge(ciudad);
            }
            em.remove(clienteDireccion);
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

    public List<ClienteDireccion> findClienteDireccionEntities() {
        return findClienteDireccionEntities(true, -1, -1);
    }

    public List<ClienteDireccion> findClienteDireccionEntities(int maxResults, int firstResult) {
        return findClienteDireccionEntities(false, maxResults, firstResult);
    }

    private List<ClienteDireccion> findClienteDireccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClienteDireccion.class));
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

    public ClienteDireccion findClienteDireccion(ClienteDireccionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClienteDireccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteDireccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClienteDireccion> rt = cq.from(ClienteDireccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
