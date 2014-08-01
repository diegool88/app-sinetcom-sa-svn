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
import ec.com.sinetcom.orm.Fabricante;
import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.LineaDeProducto;
import ec.com.sinetcom.orm.ModeloProducto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class LineaDeProductoJpaController implements Serializable {

    public LineaDeProductoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LineaDeProducto lineaDeProducto) throws RollbackFailureException, Exception {
        if (lineaDeProducto.getModeloProductoList() == null) {
            lineaDeProducto.setModeloProductoList(new ArrayList<ModeloProducto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Fabricante fabricanteid = lineaDeProducto.getFabricanteid();
            if (fabricanteid != null) {
                fabricanteid = em.getReference(fabricanteid.getClass(), fabricanteid.getId());
                lineaDeProducto.setFabricanteid(fabricanteid);
            }
            CategoriaProducto categoriaid = lineaDeProducto.getCategoriaid();
            if (categoriaid != null) {
                categoriaid = em.getReference(categoriaid.getClass(), categoriaid.getId());
                lineaDeProducto.setCategoriaid(categoriaid);
            }
            List<ModeloProducto> attachedModeloProductoList = new ArrayList<ModeloProducto>();
            for (ModeloProducto modeloProductoListModeloProductoToAttach : lineaDeProducto.getModeloProductoList()) {
                modeloProductoListModeloProductoToAttach = em.getReference(modeloProductoListModeloProductoToAttach.getClass(), modeloProductoListModeloProductoToAttach.getId());
                attachedModeloProductoList.add(modeloProductoListModeloProductoToAttach);
            }
            lineaDeProducto.setModeloProductoList(attachedModeloProductoList);
            em.persist(lineaDeProducto);
            if (fabricanteid != null) {
                fabricanteid.getLineaDeProductoList().add(lineaDeProducto);
                fabricanteid = em.merge(fabricanteid);
            }
            if (categoriaid != null) {
                categoriaid.getLineaDeProductoList().add(lineaDeProducto);
                categoriaid = em.merge(categoriaid);
            }
            for (ModeloProducto modeloProductoListModeloProducto : lineaDeProducto.getModeloProductoList()) {
                LineaDeProducto oldLineaDeProductoidOfModeloProductoListModeloProducto = modeloProductoListModeloProducto.getLineaDeProductoid();
                modeloProductoListModeloProducto.setLineaDeProductoid(lineaDeProducto);
                modeloProductoListModeloProducto = em.merge(modeloProductoListModeloProducto);
                if (oldLineaDeProductoidOfModeloProductoListModeloProducto != null) {
                    oldLineaDeProductoidOfModeloProductoListModeloProducto.getModeloProductoList().remove(modeloProductoListModeloProducto);
                    oldLineaDeProductoidOfModeloProductoListModeloProducto = em.merge(oldLineaDeProductoidOfModeloProductoListModeloProducto);
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

    public void edit(LineaDeProducto lineaDeProducto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            LineaDeProducto persistentLineaDeProducto = em.find(LineaDeProducto.class, lineaDeProducto.getId());
            Fabricante fabricanteidOld = persistentLineaDeProducto.getFabricanteid();
            Fabricante fabricanteidNew = lineaDeProducto.getFabricanteid();
            CategoriaProducto categoriaidOld = persistentLineaDeProducto.getCategoriaid();
            CategoriaProducto categoriaidNew = lineaDeProducto.getCategoriaid();
            List<ModeloProducto> modeloProductoListOld = persistentLineaDeProducto.getModeloProductoList();
            List<ModeloProducto> modeloProductoListNew = lineaDeProducto.getModeloProductoList();
            List<String> illegalOrphanMessages = null;
            for (ModeloProducto modeloProductoListOldModeloProducto : modeloProductoListOld) {
                if (!modeloProductoListNew.contains(modeloProductoListOldModeloProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ModeloProducto " + modeloProductoListOldModeloProducto + " since its lineaDeProductoid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fabricanteidNew != null) {
                fabricanteidNew = em.getReference(fabricanteidNew.getClass(), fabricanteidNew.getId());
                lineaDeProducto.setFabricanteid(fabricanteidNew);
            }
            if (categoriaidNew != null) {
                categoriaidNew = em.getReference(categoriaidNew.getClass(), categoriaidNew.getId());
                lineaDeProducto.setCategoriaid(categoriaidNew);
            }
            List<ModeloProducto> attachedModeloProductoListNew = new ArrayList<ModeloProducto>();
            for (ModeloProducto modeloProductoListNewModeloProductoToAttach : modeloProductoListNew) {
                modeloProductoListNewModeloProductoToAttach = em.getReference(modeloProductoListNewModeloProductoToAttach.getClass(), modeloProductoListNewModeloProductoToAttach.getId());
                attachedModeloProductoListNew.add(modeloProductoListNewModeloProductoToAttach);
            }
            modeloProductoListNew = attachedModeloProductoListNew;
            lineaDeProducto.setModeloProductoList(modeloProductoListNew);
            lineaDeProducto = em.merge(lineaDeProducto);
            if (fabricanteidOld != null && !fabricanteidOld.equals(fabricanteidNew)) {
                fabricanteidOld.getLineaDeProductoList().remove(lineaDeProducto);
                fabricanteidOld = em.merge(fabricanteidOld);
            }
            if (fabricanteidNew != null && !fabricanteidNew.equals(fabricanteidOld)) {
                fabricanteidNew.getLineaDeProductoList().add(lineaDeProducto);
                fabricanteidNew = em.merge(fabricanteidNew);
            }
            if (categoriaidOld != null && !categoriaidOld.equals(categoriaidNew)) {
                categoriaidOld.getLineaDeProductoList().remove(lineaDeProducto);
                categoriaidOld = em.merge(categoriaidOld);
            }
            if (categoriaidNew != null && !categoriaidNew.equals(categoriaidOld)) {
                categoriaidNew.getLineaDeProductoList().add(lineaDeProducto);
                categoriaidNew = em.merge(categoriaidNew);
            }
            for (ModeloProducto modeloProductoListNewModeloProducto : modeloProductoListNew) {
                if (!modeloProductoListOld.contains(modeloProductoListNewModeloProducto)) {
                    LineaDeProducto oldLineaDeProductoidOfModeloProductoListNewModeloProducto = modeloProductoListNewModeloProducto.getLineaDeProductoid();
                    modeloProductoListNewModeloProducto.setLineaDeProductoid(lineaDeProducto);
                    modeloProductoListNewModeloProducto = em.merge(modeloProductoListNewModeloProducto);
                    if (oldLineaDeProductoidOfModeloProductoListNewModeloProducto != null && !oldLineaDeProductoidOfModeloProductoListNewModeloProducto.equals(lineaDeProducto)) {
                        oldLineaDeProductoidOfModeloProductoListNewModeloProducto.getModeloProductoList().remove(modeloProductoListNewModeloProducto);
                        oldLineaDeProductoidOfModeloProductoListNewModeloProducto = em.merge(oldLineaDeProductoidOfModeloProductoListNewModeloProducto);
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
                Integer id = lineaDeProducto.getId();
                if (findLineaDeProducto(id) == null) {
                    throw new NonexistentEntityException("The lineaDeProducto with id " + id + " no longer exists.");
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
            LineaDeProducto lineaDeProducto;
            try {
                lineaDeProducto = em.getReference(LineaDeProducto.class, id);
                lineaDeProducto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lineaDeProducto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ModeloProducto> modeloProductoListOrphanCheck = lineaDeProducto.getModeloProductoList();
            for (ModeloProducto modeloProductoListOrphanCheckModeloProducto : modeloProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LineaDeProducto (" + lineaDeProducto + ") cannot be destroyed since the ModeloProducto " + modeloProductoListOrphanCheckModeloProducto + " in its modeloProductoList field has a non-nullable lineaDeProductoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Fabricante fabricanteid = lineaDeProducto.getFabricanteid();
            if (fabricanteid != null) {
                fabricanteid.getLineaDeProductoList().remove(lineaDeProducto);
                fabricanteid = em.merge(fabricanteid);
            }
            CategoriaProducto categoriaid = lineaDeProducto.getCategoriaid();
            if (categoriaid != null) {
                categoriaid.getLineaDeProductoList().remove(lineaDeProducto);
                categoriaid = em.merge(categoriaid);
            }
            em.remove(lineaDeProducto);
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

    public List<LineaDeProducto> findLineaDeProductoEntities() {
        return findLineaDeProductoEntities(true, -1, -1);
    }

    public List<LineaDeProducto> findLineaDeProductoEntities(int maxResults, int firstResult) {
        return findLineaDeProductoEntities(false, maxResults, firstResult);
    }

    private List<LineaDeProducto> findLineaDeProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LineaDeProducto.class));
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

    public LineaDeProducto findLineaDeProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LineaDeProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getLineaDeProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LineaDeProducto> rt = cq.from(LineaDeProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
