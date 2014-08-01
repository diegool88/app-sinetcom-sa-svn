/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.IllegalOrphanException;
import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.PreexistingEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ec.com.sinetcom.orm.UnidadMedida;
import ec.com.sinetcom.orm.ComponenteElectronicoAtomico;
import java.util.ArrayList;
import java.util.List;
import ec.com.sinetcom.orm.AtributoItemProducto;
import ec.com.sinetcom.orm.ParametrosDeProducto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class ParametrosDeProductoJpaController implements Serializable {

    public ParametrosDeProductoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParametrosDeProducto parametrosDeProducto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (parametrosDeProducto.getComponenteElectronicoAtomicoList() == null) {
            parametrosDeProducto.setComponenteElectronicoAtomicoList(new ArrayList<ComponenteElectronicoAtomico>());
        }
        if (parametrosDeProducto.getAtributoItemProductoList() == null) {
            parametrosDeProducto.setAtributoItemProductoList(new ArrayList<AtributoItemProducto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UnidadMedida unidadMedidaid = parametrosDeProducto.getUnidadMedidaid();
            if (unidadMedidaid != null) {
                unidadMedidaid = em.getReference(unidadMedidaid.getClass(), unidadMedidaid.getId());
                parametrosDeProducto.setUnidadMedidaid(unidadMedidaid);
            }
            List<ComponenteElectronicoAtomico> attachedComponenteElectronicoAtomicoList = new ArrayList<ComponenteElectronicoAtomico>();
            for (ComponenteElectronicoAtomico componenteElectronicoAtomicoListComponenteElectronicoAtomicoToAttach : parametrosDeProducto.getComponenteElectronicoAtomicoList()) {
                componenteElectronicoAtomicoListComponenteElectronicoAtomicoToAttach = em.getReference(componenteElectronicoAtomicoListComponenteElectronicoAtomicoToAttach.getClass(), componenteElectronicoAtomicoListComponenteElectronicoAtomicoToAttach.getId());
                attachedComponenteElectronicoAtomicoList.add(componenteElectronicoAtomicoListComponenteElectronicoAtomicoToAttach);
            }
            parametrosDeProducto.setComponenteElectronicoAtomicoList(attachedComponenteElectronicoAtomicoList);
            List<AtributoItemProducto> attachedAtributoItemProductoList = new ArrayList<AtributoItemProducto>();
            for (AtributoItemProducto atributoItemProductoListAtributoItemProductoToAttach : parametrosDeProducto.getAtributoItemProductoList()) {
                atributoItemProductoListAtributoItemProductoToAttach = em.getReference(atributoItemProductoListAtributoItemProductoToAttach.getClass(), atributoItemProductoListAtributoItemProductoToAttach.getAtributoItemProductoPK());
                attachedAtributoItemProductoList.add(atributoItemProductoListAtributoItemProductoToAttach);
            }
            parametrosDeProducto.setAtributoItemProductoList(attachedAtributoItemProductoList);
            em.persist(parametrosDeProducto);
            if (unidadMedidaid != null) {
                unidadMedidaid.getParametrosDeProductoList().add(parametrosDeProducto);
                unidadMedidaid = em.merge(unidadMedidaid);
            }
            for (ComponenteElectronicoAtomico componenteElectronicoAtomicoListComponenteElectronicoAtomico : parametrosDeProducto.getComponenteElectronicoAtomicoList()) {
                componenteElectronicoAtomicoListComponenteElectronicoAtomico.getParametrosDeProductoList().add(parametrosDeProducto);
                componenteElectronicoAtomicoListComponenteElectronicoAtomico = em.merge(componenteElectronicoAtomicoListComponenteElectronicoAtomico);
            }
            for (AtributoItemProducto atributoItemProductoListAtributoItemProducto : parametrosDeProducto.getAtributoItemProductoList()) {
                ParametrosDeProducto oldParametrosDeProductoOfAtributoItemProductoListAtributoItemProducto = atributoItemProductoListAtributoItemProducto.getParametrosDeProducto();
                atributoItemProductoListAtributoItemProducto.setParametrosDeProducto(parametrosDeProducto);
                atributoItemProductoListAtributoItemProducto = em.merge(atributoItemProductoListAtributoItemProducto);
                if (oldParametrosDeProductoOfAtributoItemProductoListAtributoItemProducto != null) {
                    oldParametrosDeProductoOfAtributoItemProductoListAtributoItemProducto.getAtributoItemProductoList().remove(atributoItemProductoListAtributoItemProducto);
                    oldParametrosDeProductoOfAtributoItemProductoListAtributoItemProducto = em.merge(oldParametrosDeProductoOfAtributoItemProductoListAtributoItemProducto);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findParametrosDeProducto(parametrosDeProducto.getId()) != null) {
                throw new PreexistingEntityException("ParametrosDeProducto " + parametrosDeProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParametrosDeProducto parametrosDeProducto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ParametrosDeProducto persistentParametrosDeProducto = em.find(ParametrosDeProducto.class, parametrosDeProducto.getId());
            UnidadMedida unidadMedidaidOld = persistentParametrosDeProducto.getUnidadMedidaid();
            UnidadMedida unidadMedidaidNew = parametrosDeProducto.getUnidadMedidaid();
            List<ComponenteElectronicoAtomico> componenteElectronicoAtomicoListOld = persistentParametrosDeProducto.getComponenteElectronicoAtomicoList();
            List<ComponenteElectronicoAtomico> componenteElectronicoAtomicoListNew = parametrosDeProducto.getComponenteElectronicoAtomicoList();
            List<AtributoItemProducto> atributoItemProductoListOld = persistentParametrosDeProducto.getAtributoItemProductoList();
            List<AtributoItemProducto> atributoItemProductoListNew = parametrosDeProducto.getAtributoItemProductoList();
            List<String> illegalOrphanMessages = null;
            for (AtributoItemProducto atributoItemProductoListOldAtributoItemProducto : atributoItemProductoListOld) {
                if (!atributoItemProductoListNew.contains(atributoItemProductoListOldAtributoItemProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AtributoItemProducto " + atributoItemProductoListOldAtributoItemProducto + " since its parametrosDeProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (unidadMedidaidNew != null) {
                unidadMedidaidNew = em.getReference(unidadMedidaidNew.getClass(), unidadMedidaidNew.getId());
                parametrosDeProducto.setUnidadMedidaid(unidadMedidaidNew);
            }
            List<ComponenteElectronicoAtomico> attachedComponenteElectronicoAtomicoListNew = new ArrayList<ComponenteElectronicoAtomico>();
            for (ComponenteElectronicoAtomico componenteElectronicoAtomicoListNewComponenteElectronicoAtomicoToAttach : componenteElectronicoAtomicoListNew) {
                componenteElectronicoAtomicoListNewComponenteElectronicoAtomicoToAttach = em.getReference(componenteElectronicoAtomicoListNewComponenteElectronicoAtomicoToAttach.getClass(), componenteElectronicoAtomicoListNewComponenteElectronicoAtomicoToAttach.getId());
                attachedComponenteElectronicoAtomicoListNew.add(componenteElectronicoAtomicoListNewComponenteElectronicoAtomicoToAttach);
            }
            componenteElectronicoAtomicoListNew = attachedComponenteElectronicoAtomicoListNew;
            parametrosDeProducto.setComponenteElectronicoAtomicoList(componenteElectronicoAtomicoListNew);
            List<AtributoItemProducto> attachedAtributoItemProductoListNew = new ArrayList<AtributoItemProducto>();
            for (AtributoItemProducto atributoItemProductoListNewAtributoItemProductoToAttach : atributoItemProductoListNew) {
                atributoItemProductoListNewAtributoItemProductoToAttach = em.getReference(atributoItemProductoListNewAtributoItemProductoToAttach.getClass(), atributoItemProductoListNewAtributoItemProductoToAttach.getAtributoItemProductoPK());
                attachedAtributoItemProductoListNew.add(atributoItemProductoListNewAtributoItemProductoToAttach);
            }
            atributoItemProductoListNew = attachedAtributoItemProductoListNew;
            parametrosDeProducto.setAtributoItemProductoList(atributoItemProductoListNew);
            parametrosDeProducto = em.merge(parametrosDeProducto);
            if (unidadMedidaidOld != null && !unidadMedidaidOld.equals(unidadMedidaidNew)) {
                unidadMedidaidOld.getParametrosDeProductoList().remove(parametrosDeProducto);
                unidadMedidaidOld = em.merge(unidadMedidaidOld);
            }
            if (unidadMedidaidNew != null && !unidadMedidaidNew.equals(unidadMedidaidOld)) {
                unidadMedidaidNew.getParametrosDeProductoList().add(parametrosDeProducto);
                unidadMedidaidNew = em.merge(unidadMedidaidNew);
            }
            for (ComponenteElectronicoAtomico componenteElectronicoAtomicoListOldComponenteElectronicoAtomico : componenteElectronicoAtomicoListOld) {
                if (!componenteElectronicoAtomicoListNew.contains(componenteElectronicoAtomicoListOldComponenteElectronicoAtomico)) {
                    componenteElectronicoAtomicoListOldComponenteElectronicoAtomico.getParametrosDeProductoList().remove(parametrosDeProducto);
                    componenteElectronicoAtomicoListOldComponenteElectronicoAtomico = em.merge(componenteElectronicoAtomicoListOldComponenteElectronicoAtomico);
                }
            }
            for (ComponenteElectronicoAtomico componenteElectronicoAtomicoListNewComponenteElectronicoAtomico : componenteElectronicoAtomicoListNew) {
                if (!componenteElectronicoAtomicoListOld.contains(componenteElectronicoAtomicoListNewComponenteElectronicoAtomico)) {
                    componenteElectronicoAtomicoListNewComponenteElectronicoAtomico.getParametrosDeProductoList().add(parametrosDeProducto);
                    componenteElectronicoAtomicoListNewComponenteElectronicoAtomico = em.merge(componenteElectronicoAtomicoListNewComponenteElectronicoAtomico);
                }
            }
            for (AtributoItemProducto atributoItemProductoListNewAtributoItemProducto : atributoItemProductoListNew) {
                if (!atributoItemProductoListOld.contains(atributoItemProductoListNewAtributoItemProducto)) {
                    ParametrosDeProducto oldParametrosDeProductoOfAtributoItemProductoListNewAtributoItemProducto = atributoItemProductoListNewAtributoItemProducto.getParametrosDeProducto();
                    atributoItemProductoListNewAtributoItemProducto.setParametrosDeProducto(parametrosDeProducto);
                    atributoItemProductoListNewAtributoItemProducto = em.merge(atributoItemProductoListNewAtributoItemProducto);
                    if (oldParametrosDeProductoOfAtributoItemProductoListNewAtributoItemProducto != null && !oldParametrosDeProductoOfAtributoItemProductoListNewAtributoItemProducto.equals(parametrosDeProducto)) {
                        oldParametrosDeProductoOfAtributoItemProductoListNewAtributoItemProducto.getAtributoItemProductoList().remove(atributoItemProductoListNewAtributoItemProducto);
                        oldParametrosDeProductoOfAtributoItemProductoListNewAtributoItemProducto = em.merge(oldParametrosDeProductoOfAtributoItemProductoListNewAtributoItemProducto);
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
                Integer id = parametrosDeProducto.getId();
                if (findParametrosDeProducto(id) == null) {
                    throw new NonexistentEntityException("The parametrosDeProducto with id " + id + " no longer exists.");
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
            ParametrosDeProducto parametrosDeProducto;
            try {
                parametrosDeProducto = em.getReference(ParametrosDeProducto.class, id);
                parametrosDeProducto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametrosDeProducto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AtributoItemProducto> atributoItemProductoListOrphanCheck = parametrosDeProducto.getAtributoItemProductoList();
            for (AtributoItemProducto atributoItemProductoListOrphanCheckAtributoItemProducto : atributoItemProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ParametrosDeProducto (" + parametrosDeProducto + ") cannot be destroyed since the AtributoItemProducto " + atributoItemProductoListOrphanCheckAtributoItemProducto + " in its atributoItemProductoList field has a non-nullable parametrosDeProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            UnidadMedida unidadMedidaid = parametrosDeProducto.getUnidadMedidaid();
            if (unidadMedidaid != null) {
                unidadMedidaid.getParametrosDeProductoList().remove(parametrosDeProducto);
                unidadMedidaid = em.merge(unidadMedidaid);
            }
            List<ComponenteElectronicoAtomico> componenteElectronicoAtomicoList = parametrosDeProducto.getComponenteElectronicoAtomicoList();
            for (ComponenteElectronicoAtomico componenteElectronicoAtomicoListComponenteElectronicoAtomico : componenteElectronicoAtomicoList) {
                componenteElectronicoAtomicoListComponenteElectronicoAtomico.getParametrosDeProductoList().remove(parametrosDeProducto);
                componenteElectronicoAtomicoListComponenteElectronicoAtomico = em.merge(componenteElectronicoAtomicoListComponenteElectronicoAtomico);
            }
            em.remove(parametrosDeProducto);
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

    public List<ParametrosDeProducto> findParametrosDeProductoEntities() {
        return findParametrosDeProductoEntities(true, -1, -1);
    }

    public List<ParametrosDeProducto> findParametrosDeProductoEntities(int maxResults, int firstResult) {
        return findParametrosDeProductoEntities(false, maxResults, firstResult);
    }

    private List<ParametrosDeProducto> findParametrosDeProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParametrosDeProducto.class));
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

    public ParametrosDeProducto findParametrosDeProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParametrosDeProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametrosDeProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParametrosDeProducto> rt = cq.from(ParametrosDeProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
