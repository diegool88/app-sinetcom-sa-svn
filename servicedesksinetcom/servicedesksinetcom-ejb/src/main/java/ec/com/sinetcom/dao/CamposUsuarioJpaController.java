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
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.orm.AtributoGrupo;
import ec.com.sinetcom.orm.CamposUsuario;
import ec.com.sinetcom.orm.CamposUsuarioPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class CamposUsuarioJpaController implements Serializable {

    public CamposUsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CamposUsuario camposUsuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (camposUsuario.getCamposUsuarioPK() == null) {
            camposUsuario.setCamposUsuarioPK(new CamposUsuarioPK());
        }
        camposUsuario.getCamposUsuarioPK().setAtributoGrupoid(camposUsuario.getAtributoGrupo().getId());
        camposUsuario.getCamposUsuarioPK().setUsuarioid(camposUsuario.getUsuario().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuario = camposUsuario.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                camposUsuario.setUsuario(usuario);
            }
            AtributoGrupo atributoGrupo = camposUsuario.getAtributoGrupo();
            if (atributoGrupo != null) {
                atributoGrupo = em.getReference(atributoGrupo.getClass(), atributoGrupo.getId());
                camposUsuario.setAtributoGrupo(atributoGrupo);
            }
            em.persist(camposUsuario);
            if (usuario != null) {
                usuario.getCamposUsuarioList().add(camposUsuario);
                usuario = em.merge(usuario);
            }
            if (atributoGrupo != null) {
                atributoGrupo.getCamposUsuarioList().add(camposUsuario);
                atributoGrupo = em.merge(atributoGrupo);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCamposUsuario(camposUsuario.getCamposUsuarioPK()) != null) {
                throw new PreexistingEntityException("CamposUsuario " + camposUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CamposUsuario camposUsuario) throws NonexistentEntityException, RollbackFailureException, Exception {
        camposUsuario.getCamposUsuarioPK().setAtributoGrupoid(camposUsuario.getAtributoGrupo().getId());
        camposUsuario.getCamposUsuarioPK().setUsuarioid(camposUsuario.getUsuario().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CamposUsuario persistentCamposUsuario = em.find(CamposUsuario.class, camposUsuario.getCamposUsuarioPK());
            Usuario usuarioOld = persistentCamposUsuario.getUsuario();
            Usuario usuarioNew = camposUsuario.getUsuario();
            AtributoGrupo atributoGrupoOld = persistentCamposUsuario.getAtributoGrupo();
            AtributoGrupo atributoGrupoNew = camposUsuario.getAtributoGrupo();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                camposUsuario.setUsuario(usuarioNew);
            }
            if (atributoGrupoNew != null) {
                atributoGrupoNew = em.getReference(atributoGrupoNew.getClass(), atributoGrupoNew.getId());
                camposUsuario.setAtributoGrupo(atributoGrupoNew);
            }
            camposUsuario = em.merge(camposUsuario);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getCamposUsuarioList().remove(camposUsuario);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getCamposUsuarioList().add(camposUsuario);
                usuarioNew = em.merge(usuarioNew);
            }
            if (atributoGrupoOld != null && !atributoGrupoOld.equals(atributoGrupoNew)) {
                atributoGrupoOld.getCamposUsuarioList().remove(camposUsuario);
                atributoGrupoOld = em.merge(atributoGrupoOld);
            }
            if (atributoGrupoNew != null && !atributoGrupoNew.equals(atributoGrupoOld)) {
                atributoGrupoNew.getCamposUsuarioList().add(camposUsuario);
                atributoGrupoNew = em.merge(atributoGrupoNew);
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
                CamposUsuarioPK id = camposUsuario.getCamposUsuarioPK();
                if (findCamposUsuario(id) == null) {
                    throw new NonexistentEntityException("The camposUsuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CamposUsuarioPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CamposUsuario camposUsuario;
            try {
                camposUsuario = em.getReference(CamposUsuario.class, id);
                camposUsuario.getCamposUsuarioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The camposUsuario with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario = camposUsuario.getUsuario();
            if (usuario != null) {
                usuario.getCamposUsuarioList().remove(camposUsuario);
                usuario = em.merge(usuario);
            }
            AtributoGrupo atributoGrupo = camposUsuario.getAtributoGrupo();
            if (atributoGrupo != null) {
                atributoGrupo.getCamposUsuarioList().remove(camposUsuario);
                atributoGrupo = em.merge(atributoGrupo);
            }
            em.remove(camposUsuario);
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

    public List<CamposUsuario> findCamposUsuarioEntities() {
        return findCamposUsuarioEntities(true, -1, -1);
    }

    public List<CamposUsuario> findCamposUsuarioEntities(int maxResults, int firstResult) {
        return findCamposUsuarioEntities(false, maxResults, firstResult);
    }

    private List<CamposUsuario> findCamposUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CamposUsuario.class));
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

    public CamposUsuario findCamposUsuario(CamposUsuarioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CamposUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getCamposUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CamposUsuario> rt = cq.from(CamposUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
