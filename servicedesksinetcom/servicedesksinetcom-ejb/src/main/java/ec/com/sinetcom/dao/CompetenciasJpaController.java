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
import ec.com.sinetcom.orm.UsuarioCompetencias;
import java.util.ArrayList;
import java.util.List;
import ec.com.sinetcom.orm.Cola;
import ec.com.sinetcom.orm.Competencias;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class CompetenciasJpaController implements Serializable {

    public CompetenciasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Competencias competencias) throws RollbackFailureException, Exception {
        if (competencias.getUsuarioCompetenciasList() == null) {
            competencias.setUsuarioCompetenciasList(new ArrayList<UsuarioCompetencias>());
        }
        if (competencias.getColaList() == null) {
            competencias.setColaList(new ArrayList<Cola>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<UsuarioCompetencias> attachedUsuarioCompetenciasList = new ArrayList<UsuarioCompetencias>();
            for (UsuarioCompetencias usuarioCompetenciasListUsuarioCompetenciasToAttach : competencias.getUsuarioCompetenciasList()) {
                usuarioCompetenciasListUsuarioCompetenciasToAttach = em.getReference(usuarioCompetenciasListUsuarioCompetenciasToAttach.getClass(), usuarioCompetenciasListUsuarioCompetenciasToAttach.getUsuarioid());
                attachedUsuarioCompetenciasList.add(usuarioCompetenciasListUsuarioCompetenciasToAttach);
            }
            competencias.setUsuarioCompetenciasList(attachedUsuarioCompetenciasList);
            List<Cola> attachedColaList = new ArrayList<Cola>();
            for (Cola colaListColaToAttach : competencias.getColaList()) {
                colaListColaToAttach = em.getReference(colaListColaToAttach.getClass(), colaListColaToAttach.getId());
                attachedColaList.add(colaListColaToAttach);
            }
            competencias.setColaList(attachedColaList);
            em.persist(competencias);
            for (UsuarioCompetencias usuarioCompetenciasListUsuarioCompetencias : competencias.getUsuarioCompetenciasList()) {
                Competencias oldCompetenciasidOfUsuarioCompetenciasListUsuarioCompetencias = usuarioCompetenciasListUsuarioCompetencias.getCompetenciasid();
                usuarioCompetenciasListUsuarioCompetencias.setCompetenciasid(competencias);
                usuarioCompetenciasListUsuarioCompetencias = em.merge(usuarioCompetenciasListUsuarioCompetencias);
                if (oldCompetenciasidOfUsuarioCompetenciasListUsuarioCompetencias != null) {
                    oldCompetenciasidOfUsuarioCompetenciasListUsuarioCompetencias.getUsuarioCompetenciasList().remove(usuarioCompetenciasListUsuarioCompetencias);
                    oldCompetenciasidOfUsuarioCompetenciasListUsuarioCompetencias = em.merge(oldCompetenciasidOfUsuarioCompetenciasListUsuarioCompetencias);
                }
            }
            for (Cola colaListCola : competencias.getColaList()) {
                Competencias oldCompetenciasidOfColaListCola = colaListCola.getCompetenciasid();
                colaListCola.setCompetenciasid(competencias);
                colaListCola = em.merge(colaListCola);
                if (oldCompetenciasidOfColaListCola != null) {
                    oldCompetenciasidOfColaListCola.getColaList().remove(colaListCola);
                    oldCompetenciasidOfColaListCola = em.merge(oldCompetenciasidOfColaListCola);
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

    public void edit(Competencias competencias) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Competencias persistentCompetencias = em.find(Competencias.class, competencias.getId());
            List<UsuarioCompetencias> usuarioCompetenciasListOld = persistentCompetencias.getUsuarioCompetenciasList();
            List<UsuarioCompetencias> usuarioCompetenciasListNew = competencias.getUsuarioCompetenciasList();
            List<Cola> colaListOld = persistentCompetencias.getColaList();
            List<Cola> colaListNew = competencias.getColaList();
            List<String> illegalOrphanMessages = null;
            for (UsuarioCompetencias usuarioCompetenciasListOldUsuarioCompetencias : usuarioCompetenciasListOld) {
                if (!usuarioCompetenciasListNew.contains(usuarioCompetenciasListOldUsuarioCompetencias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioCompetencias " + usuarioCompetenciasListOldUsuarioCompetencias + " since its competenciasid field is not nullable.");
                }
            }
            for (Cola colaListOldCola : colaListOld) {
                if (!colaListNew.contains(colaListOldCola)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cola " + colaListOldCola + " since its competenciasid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<UsuarioCompetencias> attachedUsuarioCompetenciasListNew = new ArrayList<UsuarioCompetencias>();
            for (UsuarioCompetencias usuarioCompetenciasListNewUsuarioCompetenciasToAttach : usuarioCompetenciasListNew) {
                usuarioCompetenciasListNewUsuarioCompetenciasToAttach = em.getReference(usuarioCompetenciasListNewUsuarioCompetenciasToAttach.getClass(), usuarioCompetenciasListNewUsuarioCompetenciasToAttach.getUsuarioid());
                attachedUsuarioCompetenciasListNew.add(usuarioCompetenciasListNewUsuarioCompetenciasToAttach);
            }
            usuarioCompetenciasListNew = attachedUsuarioCompetenciasListNew;
            competencias.setUsuarioCompetenciasList(usuarioCompetenciasListNew);
            List<Cola> attachedColaListNew = new ArrayList<Cola>();
            for (Cola colaListNewColaToAttach : colaListNew) {
                colaListNewColaToAttach = em.getReference(colaListNewColaToAttach.getClass(), colaListNewColaToAttach.getId());
                attachedColaListNew.add(colaListNewColaToAttach);
            }
            colaListNew = attachedColaListNew;
            competencias.setColaList(colaListNew);
            competencias = em.merge(competencias);
            for (UsuarioCompetencias usuarioCompetenciasListNewUsuarioCompetencias : usuarioCompetenciasListNew) {
                if (!usuarioCompetenciasListOld.contains(usuarioCompetenciasListNewUsuarioCompetencias)) {
                    Competencias oldCompetenciasidOfUsuarioCompetenciasListNewUsuarioCompetencias = usuarioCompetenciasListNewUsuarioCompetencias.getCompetenciasid();
                    usuarioCompetenciasListNewUsuarioCompetencias.setCompetenciasid(competencias);
                    usuarioCompetenciasListNewUsuarioCompetencias = em.merge(usuarioCompetenciasListNewUsuarioCompetencias);
                    if (oldCompetenciasidOfUsuarioCompetenciasListNewUsuarioCompetencias != null && !oldCompetenciasidOfUsuarioCompetenciasListNewUsuarioCompetencias.equals(competencias)) {
                        oldCompetenciasidOfUsuarioCompetenciasListNewUsuarioCompetencias.getUsuarioCompetenciasList().remove(usuarioCompetenciasListNewUsuarioCompetencias);
                        oldCompetenciasidOfUsuarioCompetenciasListNewUsuarioCompetencias = em.merge(oldCompetenciasidOfUsuarioCompetenciasListNewUsuarioCompetencias);
                    }
                }
            }
            for (Cola colaListNewCola : colaListNew) {
                if (!colaListOld.contains(colaListNewCola)) {
                    Competencias oldCompetenciasidOfColaListNewCola = colaListNewCola.getCompetenciasid();
                    colaListNewCola.setCompetenciasid(competencias);
                    colaListNewCola = em.merge(colaListNewCola);
                    if (oldCompetenciasidOfColaListNewCola != null && !oldCompetenciasidOfColaListNewCola.equals(competencias)) {
                        oldCompetenciasidOfColaListNewCola.getColaList().remove(colaListNewCola);
                        oldCompetenciasidOfColaListNewCola = em.merge(oldCompetenciasidOfColaListNewCola);
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
                Integer id = competencias.getId();
                if (findCompetencias(id) == null) {
                    throw new NonexistentEntityException("The competencias with id " + id + " no longer exists.");
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
            Competencias competencias;
            try {
                competencias = em.getReference(Competencias.class, id);
                competencias.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The competencias with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<UsuarioCompetencias> usuarioCompetenciasListOrphanCheck = competencias.getUsuarioCompetenciasList();
            for (UsuarioCompetencias usuarioCompetenciasListOrphanCheckUsuarioCompetencias : usuarioCompetenciasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Competencias (" + competencias + ") cannot be destroyed since the UsuarioCompetencias " + usuarioCompetenciasListOrphanCheckUsuarioCompetencias + " in its usuarioCompetenciasList field has a non-nullable competenciasid field.");
            }
            List<Cola> colaListOrphanCheck = competencias.getColaList();
            for (Cola colaListOrphanCheckCola : colaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Competencias (" + competencias + ") cannot be destroyed since the Cola " + colaListOrphanCheckCola + " in its colaList field has a non-nullable competenciasid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(competencias);
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

    public List<Competencias> findCompetenciasEntities() {
        return findCompetenciasEntities(true, -1, -1);
    }

    public List<Competencias> findCompetenciasEntities(int maxResults, int firstResult) {
        return findCompetenciasEntities(false, maxResults, firstResult);
    }

    private List<Competencias> findCompetenciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Competencias.class));
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

    public Competencias findCompetencias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Competencias.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompetenciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Competencias> rt = cq.from(Competencias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
