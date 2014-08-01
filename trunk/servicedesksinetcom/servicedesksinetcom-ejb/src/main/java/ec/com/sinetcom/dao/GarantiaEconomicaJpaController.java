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
import ec.com.sinetcom.orm.TipoGarantia;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.GarantiaEconomica;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class GarantiaEconomicaJpaController implements Serializable {

    public GarantiaEconomicaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GarantiaEconomica garantiaEconomica) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoGarantia tipoGarantiaid = garantiaEconomica.getTipoGarantiaid();
            if (tipoGarantiaid != null) {
                tipoGarantiaid = em.getReference(tipoGarantiaid.getClass(), tipoGarantiaid.getId());
                garantiaEconomica.setTipoGarantiaid(tipoGarantiaid);
            }
            Contrato contratonumero = garantiaEconomica.getContratonumero();
            if (contratonumero != null) {
                contratonumero = em.getReference(contratonumero.getClass(), contratonumero.getNumero());
                garantiaEconomica.setContratonumero(contratonumero);
            }
            em.persist(garantiaEconomica);
            if (tipoGarantiaid != null) {
                tipoGarantiaid.getGarantiaEconomicaList().add(garantiaEconomica);
                tipoGarantiaid = em.merge(tipoGarantiaid);
            }
            if (contratonumero != null) {
                contratonumero.getGarantiaEconomicaList().add(garantiaEconomica);
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

    public void edit(GarantiaEconomica garantiaEconomica) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            GarantiaEconomica persistentGarantiaEconomica = em.find(GarantiaEconomica.class, garantiaEconomica.getId());
            TipoGarantia tipoGarantiaidOld = persistentGarantiaEconomica.getTipoGarantiaid();
            TipoGarantia tipoGarantiaidNew = garantiaEconomica.getTipoGarantiaid();
            Contrato contratonumeroOld = persistentGarantiaEconomica.getContratonumero();
            Contrato contratonumeroNew = garantiaEconomica.getContratonumero();
            if (tipoGarantiaidNew != null) {
                tipoGarantiaidNew = em.getReference(tipoGarantiaidNew.getClass(), tipoGarantiaidNew.getId());
                garantiaEconomica.setTipoGarantiaid(tipoGarantiaidNew);
            }
            if (contratonumeroNew != null) {
                contratonumeroNew = em.getReference(contratonumeroNew.getClass(), contratonumeroNew.getNumero());
                garantiaEconomica.setContratonumero(contratonumeroNew);
            }
            garantiaEconomica = em.merge(garantiaEconomica);
            if (tipoGarantiaidOld != null && !tipoGarantiaidOld.equals(tipoGarantiaidNew)) {
                tipoGarantiaidOld.getGarantiaEconomicaList().remove(garantiaEconomica);
                tipoGarantiaidOld = em.merge(tipoGarantiaidOld);
            }
            if (tipoGarantiaidNew != null && !tipoGarantiaidNew.equals(tipoGarantiaidOld)) {
                tipoGarantiaidNew.getGarantiaEconomicaList().add(garantiaEconomica);
                tipoGarantiaidNew = em.merge(tipoGarantiaidNew);
            }
            if (contratonumeroOld != null && !contratonumeroOld.equals(contratonumeroNew)) {
                contratonumeroOld.getGarantiaEconomicaList().remove(garantiaEconomica);
                contratonumeroOld = em.merge(contratonumeroOld);
            }
            if (contratonumeroNew != null && !contratonumeroNew.equals(contratonumeroOld)) {
                contratonumeroNew.getGarantiaEconomicaList().add(garantiaEconomica);
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
                Integer id = garantiaEconomica.getId();
                if (findGarantiaEconomica(id) == null) {
                    throw new NonexistentEntityException("The garantiaEconomica with id " + id + " no longer exists.");
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
            GarantiaEconomica garantiaEconomica;
            try {
                garantiaEconomica = em.getReference(GarantiaEconomica.class, id);
                garantiaEconomica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The garantiaEconomica with id " + id + " no longer exists.", enfe);
            }
            TipoGarantia tipoGarantiaid = garantiaEconomica.getTipoGarantiaid();
            if (tipoGarantiaid != null) {
                tipoGarantiaid.getGarantiaEconomicaList().remove(garantiaEconomica);
                tipoGarantiaid = em.merge(tipoGarantiaid);
            }
            Contrato contratonumero = garantiaEconomica.getContratonumero();
            if (contratonumero != null) {
                contratonumero.getGarantiaEconomicaList().remove(garantiaEconomica);
                contratonumero = em.merge(contratonumero);
            }
            em.remove(garantiaEconomica);
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

    public List<GarantiaEconomica> findGarantiaEconomicaEntities() {
        return findGarantiaEconomicaEntities(true, -1, -1);
    }

    public List<GarantiaEconomica> findGarantiaEconomicaEntities(int maxResults, int firstResult) {
        return findGarantiaEconomicaEntities(false, maxResults, firstResult);
    }

    private List<GarantiaEconomica> findGarantiaEconomicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GarantiaEconomica.class));
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

    public GarantiaEconomica findGarantiaEconomica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GarantiaEconomica.class, id);
        } finally {
            em.close();
        }
    }

    public int getGarantiaEconomicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GarantiaEconomica> rt = cq.from(GarantiaEconomica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
