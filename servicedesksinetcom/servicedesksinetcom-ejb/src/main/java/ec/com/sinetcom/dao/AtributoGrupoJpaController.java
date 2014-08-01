/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.IllegalOrphanException;
import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import ec.com.sinetcom.orm.AtributoGrupo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.Grupo;
import ec.com.sinetcom.orm.CamposUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class AtributoGrupoJpaController implements Serializable {

    public AtributoGrupoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AtributoGrupo atributoGrupo) throws RollbackFailureException, Exception {
        if (atributoGrupo.getCamposUsuarioList() == null) {
            atributoGrupo.setCamposUsuarioList(new ArrayList<CamposUsuario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Grupo grupoid = atributoGrupo.getGrupoid();
            if (grupoid != null) {
                grupoid = em.getReference(grupoid.getClass(), grupoid.getId());
                atributoGrupo.setGrupoid(grupoid);
            }
            List<CamposUsuario> attachedCamposUsuarioList = new ArrayList<CamposUsuario>();
            for (CamposUsuario camposUsuarioListCamposUsuarioToAttach : atributoGrupo.getCamposUsuarioList()) {
                camposUsuarioListCamposUsuarioToAttach = em.getReference(camposUsuarioListCamposUsuarioToAttach.getClass(), camposUsuarioListCamposUsuarioToAttach.getCamposUsuarioPK());
                attachedCamposUsuarioList.add(camposUsuarioListCamposUsuarioToAttach);
            }
            atributoGrupo.setCamposUsuarioList(attachedCamposUsuarioList);
            em.persist(atributoGrupo);
            if (grupoid != null) {
                grupoid.getAtributoGrupoList().add(atributoGrupo);
                grupoid = em.merge(grupoid);
            }
            for (CamposUsuario camposUsuarioListCamposUsuario : atributoGrupo.getCamposUsuarioList()) {
                AtributoGrupo oldAtributoGrupoOfCamposUsuarioListCamposUsuario = camposUsuarioListCamposUsuario.getAtributoGrupo();
                camposUsuarioListCamposUsuario.setAtributoGrupo(atributoGrupo);
                camposUsuarioListCamposUsuario = em.merge(camposUsuarioListCamposUsuario);
                if (oldAtributoGrupoOfCamposUsuarioListCamposUsuario != null) {
                    oldAtributoGrupoOfCamposUsuarioListCamposUsuario.getCamposUsuarioList().remove(camposUsuarioListCamposUsuario);
                    oldAtributoGrupoOfCamposUsuarioListCamposUsuario = em.merge(oldAtributoGrupoOfCamposUsuarioListCamposUsuario);
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

    public void edit(AtributoGrupo atributoGrupo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AtributoGrupo persistentAtributoGrupo = em.find(AtributoGrupo.class, atributoGrupo.getId());
            Grupo grupoidOld = persistentAtributoGrupo.getGrupoid();
            Grupo grupoidNew = atributoGrupo.getGrupoid();
            List<CamposUsuario> camposUsuarioListOld = persistentAtributoGrupo.getCamposUsuarioList();
            List<CamposUsuario> camposUsuarioListNew = atributoGrupo.getCamposUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (CamposUsuario camposUsuarioListOldCamposUsuario : camposUsuarioListOld) {
                if (!camposUsuarioListNew.contains(camposUsuarioListOldCamposUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CamposUsuario " + camposUsuarioListOldCamposUsuario + " since its atributoGrupo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (grupoidNew != null) {
                grupoidNew = em.getReference(grupoidNew.getClass(), grupoidNew.getId());
                atributoGrupo.setGrupoid(grupoidNew);
            }
            List<CamposUsuario> attachedCamposUsuarioListNew = new ArrayList<CamposUsuario>();
            for (CamposUsuario camposUsuarioListNewCamposUsuarioToAttach : camposUsuarioListNew) {
                camposUsuarioListNewCamposUsuarioToAttach = em.getReference(camposUsuarioListNewCamposUsuarioToAttach.getClass(), camposUsuarioListNewCamposUsuarioToAttach.getCamposUsuarioPK());
                attachedCamposUsuarioListNew.add(camposUsuarioListNewCamposUsuarioToAttach);
            }
            camposUsuarioListNew = attachedCamposUsuarioListNew;
            atributoGrupo.setCamposUsuarioList(camposUsuarioListNew);
            atributoGrupo = em.merge(atributoGrupo);
            if (grupoidOld != null && !grupoidOld.equals(grupoidNew)) {
                grupoidOld.getAtributoGrupoList().remove(atributoGrupo);
                grupoidOld = em.merge(grupoidOld);
            }
            if (grupoidNew != null && !grupoidNew.equals(grupoidOld)) {
                grupoidNew.getAtributoGrupoList().add(atributoGrupo);
                grupoidNew = em.merge(grupoidNew);
            }
            for (CamposUsuario camposUsuarioListNewCamposUsuario : camposUsuarioListNew) {
                if (!camposUsuarioListOld.contains(camposUsuarioListNewCamposUsuario)) {
                    AtributoGrupo oldAtributoGrupoOfCamposUsuarioListNewCamposUsuario = camposUsuarioListNewCamposUsuario.getAtributoGrupo();
                    camposUsuarioListNewCamposUsuario.setAtributoGrupo(atributoGrupo);
                    camposUsuarioListNewCamposUsuario = em.merge(camposUsuarioListNewCamposUsuario);
                    if (oldAtributoGrupoOfCamposUsuarioListNewCamposUsuario != null && !oldAtributoGrupoOfCamposUsuarioListNewCamposUsuario.equals(atributoGrupo)) {
                        oldAtributoGrupoOfCamposUsuarioListNewCamposUsuario.getCamposUsuarioList().remove(camposUsuarioListNewCamposUsuario);
                        oldAtributoGrupoOfCamposUsuarioListNewCamposUsuario = em.merge(oldAtributoGrupoOfCamposUsuarioListNewCamposUsuario);
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
                Integer id = atributoGrupo.getId();
                if (findAtributoGrupo(id) == null) {
                    throw new NonexistentEntityException("The atributoGrupo with id " + id + " no longer exists.");
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
            AtributoGrupo atributoGrupo;
            try {
                atributoGrupo = em.getReference(AtributoGrupo.class, id);
                atributoGrupo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atributoGrupo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CamposUsuario> camposUsuarioListOrphanCheck = atributoGrupo.getCamposUsuarioList();
            for (CamposUsuario camposUsuarioListOrphanCheckCamposUsuario : camposUsuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AtributoGrupo (" + atributoGrupo + ") cannot be destroyed since the CamposUsuario " + camposUsuarioListOrphanCheckCamposUsuario + " in its camposUsuarioList field has a non-nullable atributoGrupo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Grupo grupoid = atributoGrupo.getGrupoid();
            if (grupoid != null) {
                grupoid.getAtributoGrupoList().remove(atributoGrupo);
                grupoid = em.merge(grupoid);
            }
            em.remove(atributoGrupo);
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

    public List<AtributoGrupo> findAtributoGrupoEntities() {
        return findAtributoGrupoEntities(true, -1, -1);
    }

    public List<AtributoGrupo> findAtributoGrupoEntities(int maxResults, int firstResult) {
        return findAtributoGrupoEntities(false, maxResults, firstResult);
    }

    private List<AtributoGrupo> findAtributoGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AtributoGrupo.class));
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

    public AtributoGrupo findAtributoGrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AtributoGrupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtributoGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AtributoGrupo> rt = cq.from(AtributoGrupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
