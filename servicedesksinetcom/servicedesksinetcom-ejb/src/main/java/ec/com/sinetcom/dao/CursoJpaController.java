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
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.Curso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class CursoJpaController implements Serializable {

    public CursoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curso curso) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario intructorSinetcom = curso.getIntructorSinetcom();
            if (intructorSinetcom != null) {
                intructorSinetcom = em.getReference(intructorSinetcom.getClass(), intructorSinetcom.getId());
                curso.setIntructorSinetcom(intructorSinetcom);
            }
            Contrato contratonumero = curso.getContratonumero();
            if (contratonumero != null) {
                contratonumero = em.getReference(contratonumero.getClass(), contratonumero.getNumero());
                curso.setContratonumero(contratonumero);
            }
            em.persist(curso);
            if (intructorSinetcom != null) {
                intructorSinetcom.getCursoList().add(curso);
                intructorSinetcom = em.merge(intructorSinetcom);
            }
            if (contratonumero != null) {
                contratonumero.getCursoList().add(curso);
                contratonumero = em.merge(contratonumero);
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

    public void edit(Curso curso) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Curso persistentCurso = em.find(Curso.class, curso.getCodigo());
            Usuario intructorSinetcomOld = persistentCurso.getIntructorSinetcom();
            Usuario intructorSinetcomNew = curso.getIntructorSinetcom();
            Contrato contratonumeroOld = persistentCurso.getContratonumero();
            Contrato contratonumeroNew = curso.getContratonumero();
            if (intructorSinetcomNew != null) {
                intructorSinetcomNew = em.getReference(intructorSinetcomNew.getClass(), intructorSinetcomNew.getId());
                curso.setIntructorSinetcom(intructorSinetcomNew);
            }
            if (contratonumeroNew != null) {
                contratonumeroNew = em.getReference(contratonumeroNew.getClass(), contratonumeroNew.getNumero());
                curso.setContratonumero(contratonumeroNew);
            }
            curso = em.merge(curso);
            if (intructorSinetcomOld != null && !intructorSinetcomOld.equals(intructorSinetcomNew)) {
                intructorSinetcomOld.getCursoList().remove(curso);
                intructorSinetcomOld = em.merge(intructorSinetcomOld);
            }
            if (intructorSinetcomNew != null && !intructorSinetcomNew.equals(intructorSinetcomOld)) {
                intructorSinetcomNew.getCursoList().add(curso);
                intructorSinetcomNew = em.merge(intructorSinetcomNew);
            }
            if (contratonumeroOld != null && !contratonumeroOld.equals(contratonumeroNew)) {
                contratonumeroOld.getCursoList().remove(curso);
                contratonumeroOld = em.merge(contratonumeroOld);
            }
            if (contratonumeroNew != null && !contratonumeroNew.equals(contratonumeroOld)) {
                contratonumeroNew.getCursoList().add(curso);
                contratonumeroNew = em.merge(contratonumeroNew);
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
                Integer id = curso.getCodigo();
                if (findCurso(id) == null) {
                    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
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
            Curso curso;
            try {
                curso = em.getReference(Curso.class, id);
                curso.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
            }
            Usuario intructorSinetcom = curso.getIntructorSinetcom();
            if (intructorSinetcom != null) {
                intructorSinetcom.getCursoList().remove(curso);
                intructorSinetcom = em.merge(intructorSinetcom);
            }
            Contrato contratonumero = curso.getContratonumero();
            if (contratonumero != null) {
                contratonumero.getCursoList().remove(curso);
                contratonumero = em.merge(contratonumero);
            }
            em.remove(curso);
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

    public List<Curso> findCursoEntities() {
        return findCursoEntities(true, -1, -1);
    }

    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
        return findCursoEntities(false, maxResults, firstResult);
    }

    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curso.class));
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

    public Curso findCurso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curso> rt = cq.from(Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
