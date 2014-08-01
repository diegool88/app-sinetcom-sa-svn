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
import ec.com.sinetcom.orm.CategoriaFaq;
import ec.com.sinetcom.orm.Faq;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class FaqJpaController implements Serializable {

    public FaqJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Faq faq) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuarioidmodificacion = faq.getUsuarioidmodificacion();
            if (usuarioidmodificacion != null) {
                usuarioidmodificacion = em.getReference(usuarioidmodificacion.getClass(), usuarioidmodificacion.getId());
                faq.setUsuarioidmodificacion(usuarioidmodificacion);
            }
            Usuario usuarioidcreacion = faq.getUsuarioidcreacion();
            if (usuarioidcreacion != null) {
                usuarioidcreacion = em.getReference(usuarioidcreacion.getClass(), usuarioidcreacion.getId());
                faq.setUsuarioidcreacion(usuarioidcreacion);
            }
            CategoriaFaq categoriaFaqcodigo = faq.getCategoriaFaqcodigo();
            if (categoriaFaqcodigo != null) {
                categoriaFaqcodigo = em.getReference(categoriaFaqcodigo.getClass(), categoriaFaqcodigo.getCodigo());
                faq.setCategoriaFaqcodigo(categoriaFaqcodigo);
            }
            em.persist(faq);
            if (usuarioidmodificacion != null) {
                usuarioidmodificacion.getFaqList().add(faq);
                usuarioidmodificacion = em.merge(usuarioidmodificacion);
            }
            if (usuarioidcreacion != null) {
                usuarioidcreacion.getFaqList().add(faq);
                usuarioidcreacion = em.merge(usuarioidcreacion);
            }
            if (categoriaFaqcodigo != null) {
                categoriaFaqcodigo.getFaqList().add(faq);
                categoriaFaqcodigo = em.merge(categoriaFaqcodigo);
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

    public void edit(Faq faq) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Faq persistentFaq = em.find(Faq.class, faq.getId());
            Usuario usuarioidmodificacionOld = persistentFaq.getUsuarioidmodificacion();
            Usuario usuarioidmodificacionNew = faq.getUsuarioidmodificacion();
            Usuario usuarioidcreacionOld = persistentFaq.getUsuarioidcreacion();
            Usuario usuarioidcreacionNew = faq.getUsuarioidcreacion();
            CategoriaFaq categoriaFaqcodigoOld = persistentFaq.getCategoriaFaqcodigo();
            CategoriaFaq categoriaFaqcodigoNew = faq.getCategoriaFaqcodigo();
            if (usuarioidmodificacionNew != null) {
                usuarioidmodificacionNew = em.getReference(usuarioidmodificacionNew.getClass(), usuarioidmodificacionNew.getId());
                faq.setUsuarioidmodificacion(usuarioidmodificacionNew);
            }
            if (usuarioidcreacionNew != null) {
                usuarioidcreacionNew = em.getReference(usuarioidcreacionNew.getClass(), usuarioidcreacionNew.getId());
                faq.setUsuarioidcreacion(usuarioidcreacionNew);
            }
            if (categoriaFaqcodigoNew != null) {
                categoriaFaqcodigoNew = em.getReference(categoriaFaqcodigoNew.getClass(), categoriaFaqcodigoNew.getCodigo());
                faq.setCategoriaFaqcodigo(categoriaFaqcodigoNew);
            }
            faq = em.merge(faq);
            if (usuarioidmodificacionOld != null && !usuarioidmodificacionOld.equals(usuarioidmodificacionNew)) {
                usuarioidmodificacionOld.getFaqList().remove(faq);
                usuarioidmodificacionOld = em.merge(usuarioidmodificacionOld);
            }
            if (usuarioidmodificacionNew != null && !usuarioidmodificacionNew.equals(usuarioidmodificacionOld)) {
                usuarioidmodificacionNew.getFaqList().add(faq);
                usuarioidmodificacionNew = em.merge(usuarioidmodificacionNew);
            }
            if (usuarioidcreacionOld != null && !usuarioidcreacionOld.equals(usuarioidcreacionNew)) {
                usuarioidcreacionOld.getFaqList().remove(faq);
                usuarioidcreacionOld = em.merge(usuarioidcreacionOld);
            }
            if (usuarioidcreacionNew != null && !usuarioidcreacionNew.equals(usuarioidcreacionOld)) {
                usuarioidcreacionNew.getFaqList().add(faq);
                usuarioidcreacionNew = em.merge(usuarioidcreacionNew);
            }
            if (categoriaFaqcodigoOld != null && !categoriaFaqcodigoOld.equals(categoriaFaqcodigoNew)) {
                categoriaFaqcodigoOld.getFaqList().remove(faq);
                categoriaFaqcodigoOld = em.merge(categoriaFaqcodigoOld);
            }
            if (categoriaFaqcodigoNew != null && !categoriaFaqcodigoNew.equals(categoriaFaqcodigoOld)) {
                categoriaFaqcodigoNew.getFaqList().add(faq);
                categoriaFaqcodigoNew = em.merge(categoriaFaqcodigoNew);
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
                Integer id = faq.getId();
                if (findFaq(id) == null) {
                    throw new NonexistentEntityException("The faq with id " + id + " no longer exists.");
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
            Faq faq;
            try {
                faq = em.getReference(Faq.class, id);
                faq.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The faq with id " + id + " no longer exists.", enfe);
            }
            Usuario usuarioidmodificacion = faq.getUsuarioidmodificacion();
            if (usuarioidmodificacion != null) {
                usuarioidmodificacion.getFaqList().remove(faq);
                usuarioidmodificacion = em.merge(usuarioidmodificacion);
            }
            Usuario usuarioidcreacion = faq.getUsuarioidcreacion();
            if (usuarioidcreacion != null) {
                usuarioidcreacion.getFaqList().remove(faq);
                usuarioidcreacion = em.merge(usuarioidcreacion);
            }
            CategoriaFaq categoriaFaqcodigo = faq.getCategoriaFaqcodigo();
            if (categoriaFaqcodigo != null) {
                categoriaFaqcodigo.getFaqList().remove(faq);
                categoriaFaqcodigo = em.merge(categoriaFaqcodigo);
            }
            em.remove(faq);
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

    public List<Faq> findFaqEntities() {
        return findFaqEntities(true, -1, -1);
    }

    public List<Faq> findFaqEntities(int maxResults, int firstResult) {
        return findFaqEntities(false, maxResults, firstResult);
    }

    private List<Faq> findFaqEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Faq.class));
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

    public Faq findFaq(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Faq.class, id);
        } finally {
            em.close();
        }
    }

    public int getFaqCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Faq> rt = cq.from(Faq.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
