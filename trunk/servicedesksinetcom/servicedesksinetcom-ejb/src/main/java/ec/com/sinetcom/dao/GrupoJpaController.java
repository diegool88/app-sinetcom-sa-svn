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
import ec.com.sinetcom.orm.Permisos;
import ec.com.sinetcom.orm.AtributoGrupo;
import ec.com.sinetcom.orm.Grupo;
import java.util.ArrayList;
import java.util.List;
import ec.com.sinetcom.orm.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class GrupoJpaController implements Serializable {

    public GrupoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) throws RollbackFailureException, Exception {
        if (grupo.getAtributoGrupoList() == null) {
            grupo.setAtributoGrupoList(new ArrayList<AtributoGrupo>());
        }
        if (grupo.getUsuarioList() == null) {
            grupo.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Permisos permisos = grupo.getPermisos();
            if (permisos != null) {
                permisos = em.getReference(permisos.getClass(), permisos.getGrupoid());
                grupo.setPermisos(permisos);
            }
            List<AtributoGrupo> attachedAtributoGrupoList = new ArrayList<AtributoGrupo>();
            for (AtributoGrupo atributoGrupoListAtributoGrupoToAttach : grupo.getAtributoGrupoList()) {
                atributoGrupoListAtributoGrupoToAttach = em.getReference(atributoGrupoListAtributoGrupoToAttach.getClass(), atributoGrupoListAtributoGrupoToAttach.getId());
                attachedAtributoGrupoList.add(atributoGrupoListAtributoGrupoToAttach);
            }
            grupo.setAtributoGrupoList(attachedAtributoGrupoList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : grupo.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getId());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            grupo.setUsuarioList(attachedUsuarioList);
            em.persist(grupo);
            if (permisos != null) {
                Grupo oldGrupoOfPermisos = permisos.getGrupo();
                if (oldGrupoOfPermisos != null) {
                    oldGrupoOfPermisos.setPermisos(null);
                    oldGrupoOfPermisos = em.merge(oldGrupoOfPermisos);
                }
                permisos.setGrupo(grupo);
                permisos = em.merge(permisos);
            }
            for (AtributoGrupo atributoGrupoListAtributoGrupo : grupo.getAtributoGrupoList()) {
                Grupo oldGrupoidOfAtributoGrupoListAtributoGrupo = atributoGrupoListAtributoGrupo.getGrupoid();
                atributoGrupoListAtributoGrupo.setGrupoid(grupo);
                atributoGrupoListAtributoGrupo = em.merge(atributoGrupoListAtributoGrupo);
                if (oldGrupoidOfAtributoGrupoListAtributoGrupo != null) {
                    oldGrupoidOfAtributoGrupoListAtributoGrupo.getAtributoGrupoList().remove(atributoGrupoListAtributoGrupo);
                    oldGrupoidOfAtributoGrupoListAtributoGrupo = em.merge(oldGrupoidOfAtributoGrupoListAtributoGrupo);
                }
            }
            for (Usuario usuarioListUsuario : grupo.getUsuarioList()) {
                Grupo oldGrupoidOfUsuarioListUsuario = usuarioListUsuario.getGrupoid();
                usuarioListUsuario.setGrupoid(grupo);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldGrupoidOfUsuarioListUsuario != null) {
                    oldGrupoidOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldGrupoidOfUsuarioListUsuario = em.merge(oldGrupoidOfUsuarioListUsuario);
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

    public void edit(Grupo grupo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getId());
            Permisos permisosOld = persistentGrupo.getPermisos();
            Permisos permisosNew = grupo.getPermisos();
            List<AtributoGrupo> atributoGrupoListOld = persistentGrupo.getAtributoGrupoList();
            List<AtributoGrupo> atributoGrupoListNew = grupo.getAtributoGrupoList();
            List<Usuario> usuarioListOld = persistentGrupo.getUsuarioList();
            List<Usuario> usuarioListNew = grupo.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            if (permisosOld != null && !permisosOld.equals(permisosNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Permisos " + permisosOld + " since its grupo field is not nullable.");
            }
            for (AtributoGrupo atributoGrupoListOldAtributoGrupo : atributoGrupoListOld) {
                if (!atributoGrupoListNew.contains(atributoGrupoListOldAtributoGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AtributoGrupo " + atributoGrupoListOldAtributoGrupo + " since its grupoid field is not nullable.");
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its grupoid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (permisosNew != null) {
                permisosNew = em.getReference(permisosNew.getClass(), permisosNew.getGrupoid());
                grupo.setPermisos(permisosNew);
            }
            List<AtributoGrupo> attachedAtributoGrupoListNew = new ArrayList<AtributoGrupo>();
            for (AtributoGrupo atributoGrupoListNewAtributoGrupoToAttach : atributoGrupoListNew) {
                atributoGrupoListNewAtributoGrupoToAttach = em.getReference(atributoGrupoListNewAtributoGrupoToAttach.getClass(), atributoGrupoListNewAtributoGrupoToAttach.getId());
                attachedAtributoGrupoListNew.add(atributoGrupoListNewAtributoGrupoToAttach);
            }
            atributoGrupoListNew = attachedAtributoGrupoListNew;
            grupo.setAtributoGrupoList(atributoGrupoListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getId());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            grupo.setUsuarioList(usuarioListNew);
            grupo = em.merge(grupo);
            if (permisosNew != null && !permisosNew.equals(permisosOld)) {
                Grupo oldGrupoOfPermisos = permisosNew.getGrupo();
                if (oldGrupoOfPermisos != null) {
                    oldGrupoOfPermisos.setPermisos(null);
                    oldGrupoOfPermisos = em.merge(oldGrupoOfPermisos);
                }
                permisosNew.setGrupo(grupo);
                permisosNew = em.merge(permisosNew);
            }
            for (AtributoGrupo atributoGrupoListNewAtributoGrupo : atributoGrupoListNew) {
                if (!atributoGrupoListOld.contains(atributoGrupoListNewAtributoGrupo)) {
                    Grupo oldGrupoidOfAtributoGrupoListNewAtributoGrupo = atributoGrupoListNewAtributoGrupo.getGrupoid();
                    atributoGrupoListNewAtributoGrupo.setGrupoid(grupo);
                    atributoGrupoListNewAtributoGrupo = em.merge(atributoGrupoListNewAtributoGrupo);
                    if (oldGrupoidOfAtributoGrupoListNewAtributoGrupo != null && !oldGrupoidOfAtributoGrupoListNewAtributoGrupo.equals(grupo)) {
                        oldGrupoidOfAtributoGrupoListNewAtributoGrupo.getAtributoGrupoList().remove(atributoGrupoListNewAtributoGrupo);
                        oldGrupoidOfAtributoGrupoListNewAtributoGrupo = em.merge(oldGrupoidOfAtributoGrupoListNewAtributoGrupo);
                    }
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Grupo oldGrupoidOfUsuarioListNewUsuario = usuarioListNewUsuario.getGrupoid();
                    usuarioListNewUsuario.setGrupoid(grupo);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldGrupoidOfUsuarioListNewUsuario != null && !oldGrupoidOfUsuarioListNewUsuario.equals(grupo)) {
                        oldGrupoidOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldGrupoidOfUsuarioListNewUsuario = em.merge(oldGrupoidOfUsuarioListNewUsuario);
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
                Integer id = grupo.getId();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
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
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Permisos permisosOrphanCheck = grupo.getPermisos();
            if (permisosOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the Permisos " + permisosOrphanCheck + " in its permisos field has a non-nullable grupo field.");
            }
            List<AtributoGrupo> atributoGrupoListOrphanCheck = grupo.getAtributoGrupoList();
            for (AtributoGrupo atributoGrupoListOrphanCheckAtributoGrupo : atributoGrupoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the AtributoGrupo " + atributoGrupoListOrphanCheckAtributoGrupo + " in its atributoGrupoList field has a non-nullable grupoid field.");
            }
            List<Usuario> usuarioListOrphanCheck = grupo.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable grupoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(grupo);
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

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
