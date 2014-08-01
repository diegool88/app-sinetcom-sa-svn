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
import ec.com.sinetcom.orm.CategoriaProducto;
import ec.com.sinetcom.orm.Fabricante;
import java.util.ArrayList;
import java.util.List;
import ec.com.sinetcom.orm.LineaDeProducto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class FabricanteJpaController implements Serializable {

    public FabricanteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fabricante fabricante) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (fabricante.getCategoriaProductoList() == null) {
            fabricante.setCategoriaProductoList(new ArrayList<CategoriaProducto>());
        }
        if (fabricante.getLineaDeProductoList() == null) {
            fabricante.setLineaDeProductoList(new ArrayList<LineaDeProducto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<CategoriaProducto> attachedCategoriaProductoList = new ArrayList<CategoriaProducto>();
            for (CategoriaProducto categoriaProductoListCategoriaProductoToAttach : fabricante.getCategoriaProductoList()) {
                categoriaProductoListCategoriaProductoToAttach = em.getReference(categoriaProductoListCategoriaProductoToAttach.getClass(), categoriaProductoListCategoriaProductoToAttach.getId());
                attachedCategoriaProductoList.add(categoriaProductoListCategoriaProductoToAttach);
            }
            fabricante.setCategoriaProductoList(attachedCategoriaProductoList);
            List<LineaDeProducto> attachedLineaDeProductoList = new ArrayList<LineaDeProducto>();
            for (LineaDeProducto lineaDeProductoListLineaDeProductoToAttach : fabricante.getLineaDeProductoList()) {
                lineaDeProductoListLineaDeProductoToAttach = em.getReference(lineaDeProductoListLineaDeProductoToAttach.getClass(), lineaDeProductoListLineaDeProductoToAttach.getId());
                attachedLineaDeProductoList.add(lineaDeProductoListLineaDeProductoToAttach);
            }
            fabricante.setLineaDeProductoList(attachedLineaDeProductoList);
            em.persist(fabricante);
            for (CategoriaProducto categoriaProductoListCategoriaProducto : fabricante.getCategoriaProductoList()) {
                categoriaProductoListCategoriaProducto.getFabricanteList().add(fabricante);
                categoriaProductoListCategoriaProducto = em.merge(categoriaProductoListCategoriaProducto);
            }
            for (LineaDeProducto lineaDeProductoListLineaDeProducto : fabricante.getLineaDeProductoList()) {
                Fabricante oldFabricanteidOfLineaDeProductoListLineaDeProducto = lineaDeProductoListLineaDeProducto.getFabricanteid();
                lineaDeProductoListLineaDeProducto.setFabricanteid(fabricante);
                lineaDeProductoListLineaDeProducto = em.merge(lineaDeProductoListLineaDeProducto);
                if (oldFabricanteidOfLineaDeProductoListLineaDeProducto != null) {
                    oldFabricanteidOfLineaDeProductoListLineaDeProducto.getLineaDeProductoList().remove(lineaDeProductoListLineaDeProducto);
                    oldFabricanteidOfLineaDeProductoListLineaDeProducto = em.merge(oldFabricanteidOfLineaDeProductoListLineaDeProducto);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findFabricante(fabricante.getId()) != null) {
                throw new PreexistingEntityException("Fabricante " + fabricante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fabricante fabricante) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Fabricante persistentFabricante = em.find(Fabricante.class, fabricante.getId());
            List<CategoriaProducto> categoriaProductoListOld = persistentFabricante.getCategoriaProductoList();
            List<CategoriaProducto> categoriaProductoListNew = fabricante.getCategoriaProductoList();
            List<LineaDeProducto> lineaDeProductoListOld = persistentFabricante.getLineaDeProductoList();
            List<LineaDeProducto> lineaDeProductoListNew = fabricante.getLineaDeProductoList();
            List<String> illegalOrphanMessages = null;
            for (LineaDeProducto lineaDeProductoListOldLineaDeProducto : lineaDeProductoListOld) {
                if (!lineaDeProductoListNew.contains(lineaDeProductoListOldLineaDeProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LineaDeProducto " + lineaDeProductoListOldLineaDeProducto + " since its fabricanteid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CategoriaProducto> attachedCategoriaProductoListNew = new ArrayList<CategoriaProducto>();
            for (CategoriaProducto categoriaProductoListNewCategoriaProductoToAttach : categoriaProductoListNew) {
                categoriaProductoListNewCategoriaProductoToAttach = em.getReference(categoriaProductoListNewCategoriaProductoToAttach.getClass(), categoriaProductoListNewCategoriaProductoToAttach.getId());
                attachedCategoriaProductoListNew.add(categoriaProductoListNewCategoriaProductoToAttach);
            }
            categoriaProductoListNew = attachedCategoriaProductoListNew;
            fabricante.setCategoriaProductoList(categoriaProductoListNew);
            List<LineaDeProducto> attachedLineaDeProductoListNew = new ArrayList<LineaDeProducto>();
            for (LineaDeProducto lineaDeProductoListNewLineaDeProductoToAttach : lineaDeProductoListNew) {
                lineaDeProductoListNewLineaDeProductoToAttach = em.getReference(lineaDeProductoListNewLineaDeProductoToAttach.getClass(), lineaDeProductoListNewLineaDeProductoToAttach.getId());
                attachedLineaDeProductoListNew.add(lineaDeProductoListNewLineaDeProductoToAttach);
            }
            lineaDeProductoListNew = attachedLineaDeProductoListNew;
            fabricante.setLineaDeProductoList(lineaDeProductoListNew);
            fabricante = em.merge(fabricante);
            for (CategoriaProducto categoriaProductoListOldCategoriaProducto : categoriaProductoListOld) {
                if (!categoriaProductoListNew.contains(categoriaProductoListOldCategoriaProducto)) {
                    categoriaProductoListOldCategoriaProducto.getFabricanteList().remove(fabricante);
                    categoriaProductoListOldCategoriaProducto = em.merge(categoriaProductoListOldCategoriaProducto);
                }
            }
            for (CategoriaProducto categoriaProductoListNewCategoriaProducto : categoriaProductoListNew) {
                if (!categoriaProductoListOld.contains(categoriaProductoListNewCategoriaProducto)) {
                    categoriaProductoListNewCategoriaProducto.getFabricanteList().add(fabricante);
                    categoriaProductoListNewCategoriaProducto = em.merge(categoriaProductoListNewCategoriaProducto);
                }
            }
            for (LineaDeProducto lineaDeProductoListNewLineaDeProducto : lineaDeProductoListNew) {
                if (!lineaDeProductoListOld.contains(lineaDeProductoListNewLineaDeProducto)) {
                    Fabricante oldFabricanteidOfLineaDeProductoListNewLineaDeProducto = lineaDeProductoListNewLineaDeProducto.getFabricanteid();
                    lineaDeProductoListNewLineaDeProducto.setFabricanteid(fabricante);
                    lineaDeProductoListNewLineaDeProducto = em.merge(lineaDeProductoListNewLineaDeProducto);
                    if (oldFabricanteidOfLineaDeProductoListNewLineaDeProducto != null && !oldFabricanteidOfLineaDeProductoListNewLineaDeProducto.equals(fabricante)) {
                        oldFabricanteidOfLineaDeProductoListNewLineaDeProducto.getLineaDeProductoList().remove(lineaDeProductoListNewLineaDeProducto);
                        oldFabricanteidOfLineaDeProductoListNewLineaDeProducto = em.merge(oldFabricanteidOfLineaDeProductoListNewLineaDeProducto);
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
                Integer id = fabricante.getId();
                if (findFabricante(id) == null) {
                    throw new NonexistentEntityException("The fabricante with id " + id + " no longer exists.");
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
            Fabricante fabricante;
            try {
                fabricante = em.getReference(Fabricante.class, id);
                fabricante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fabricante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<LineaDeProducto> lineaDeProductoListOrphanCheck = fabricante.getLineaDeProductoList();
            for (LineaDeProducto lineaDeProductoListOrphanCheckLineaDeProducto : lineaDeProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fabricante (" + fabricante + ") cannot be destroyed since the LineaDeProducto " + lineaDeProductoListOrphanCheckLineaDeProducto + " in its lineaDeProductoList field has a non-nullable fabricanteid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CategoriaProducto> categoriaProductoList = fabricante.getCategoriaProductoList();
            for (CategoriaProducto categoriaProductoListCategoriaProducto : categoriaProductoList) {
                categoriaProductoListCategoriaProducto.getFabricanteList().remove(fabricante);
                categoriaProductoListCategoriaProducto = em.merge(categoriaProductoListCategoriaProducto);
            }
            em.remove(fabricante);
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

    public List<Fabricante> findFabricanteEntities() {
        return findFabricanteEntities(true, -1, -1);
    }

    public List<Fabricante> findFabricanteEntities(int maxResults, int firstResult) {
        return findFabricanteEntities(false, maxResults, firstResult);
    }

    private List<Fabricante> findFabricanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fabricante.class));
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

    public Fabricante findFabricante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fabricante.class, id);
        } finally {
            em.close();
        }
    }

    public int getFabricanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fabricante> rt = cq.from(Fabricante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
