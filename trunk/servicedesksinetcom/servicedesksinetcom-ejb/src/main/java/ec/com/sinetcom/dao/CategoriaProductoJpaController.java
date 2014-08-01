/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.dao.exceptions.IllegalOrphanException;
import ec.com.sinetcom.dao.exceptions.NonexistentEntityException;
import ec.com.sinetcom.dao.exceptions.PreexistingEntityException;
import ec.com.sinetcom.dao.exceptions.RollbackFailureException;
import ec.com.sinetcom.orm.CategoriaProducto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class CategoriaProductoJpaController implements Serializable {

    public CategoriaProductoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaProducto categoriaProducto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (categoriaProducto.getFabricanteList() == null) {
            categoriaProducto.setFabricanteList(new ArrayList<Fabricante>());
        }
        if (categoriaProducto.getLineaDeProductoList() == null) {
            categoriaProducto.setLineaDeProductoList(new ArrayList<LineaDeProducto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Fabricante> attachedFabricanteList = new ArrayList<Fabricante>();
            for (Fabricante fabricanteListFabricanteToAttach : categoriaProducto.getFabricanteList()) {
                fabricanteListFabricanteToAttach = em.getReference(fabricanteListFabricanteToAttach.getClass(), fabricanteListFabricanteToAttach.getId());
                attachedFabricanteList.add(fabricanteListFabricanteToAttach);
            }
            categoriaProducto.setFabricanteList(attachedFabricanteList);
            List<LineaDeProducto> attachedLineaDeProductoList = new ArrayList<LineaDeProducto>();
            for (LineaDeProducto lineaDeProductoListLineaDeProductoToAttach : categoriaProducto.getLineaDeProductoList()) {
                lineaDeProductoListLineaDeProductoToAttach = em.getReference(lineaDeProductoListLineaDeProductoToAttach.getClass(), lineaDeProductoListLineaDeProductoToAttach.getId());
                attachedLineaDeProductoList.add(lineaDeProductoListLineaDeProductoToAttach);
            }
            categoriaProducto.setLineaDeProductoList(attachedLineaDeProductoList);
            em.persist(categoriaProducto);
            for (Fabricante fabricanteListFabricante : categoriaProducto.getFabricanteList()) {
                fabricanteListFabricante.getCategoriaProductoList().add(categoriaProducto);
                fabricanteListFabricante = em.merge(fabricanteListFabricante);
            }
            for (LineaDeProducto lineaDeProductoListLineaDeProducto : categoriaProducto.getLineaDeProductoList()) {
                CategoriaProducto oldCategoriaidOfLineaDeProductoListLineaDeProducto = lineaDeProductoListLineaDeProducto.getCategoriaid();
                lineaDeProductoListLineaDeProducto.setCategoriaid(categoriaProducto);
                lineaDeProductoListLineaDeProducto = em.merge(lineaDeProductoListLineaDeProducto);
                if (oldCategoriaidOfLineaDeProductoListLineaDeProducto != null) {
                    oldCategoriaidOfLineaDeProductoListLineaDeProducto.getLineaDeProductoList().remove(lineaDeProductoListLineaDeProducto);
                    oldCategoriaidOfLineaDeProductoListLineaDeProducto = em.merge(oldCategoriaidOfLineaDeProductoListLineaDeProducto);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCategoriaProducto(categoriaProducto.getId()) != null) {
                throw new PreexistingEntityException("CategoriaProducto " + categoriaProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoriaProducto categoriaProducto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoriaProducto persistentCategoriaProducto = em.find(CategoriaProducto.class, categoriaProducto.getId());
            List<Fabricante> fabricanteListOld = persistentCategoriaProducto.getFabricanteList();
            List<Fabricante> fabricanteListNew = categoriaProducto.getFabricanteList();
            List<LineaDeProducto> lineaDeProductoListOld = persistentCategoriaProducto.getLineaDeProductoList();
            List<LineaDeProducto> lineaDeProductoListNew = categoriaProducto.getLineaDeProductoList();
            List<String> illegalOrphanMessages = null;
            for (LineaDeProducto lineaDeProductoListOldLineaDeProducto : lineaDeProductoListOld) {
                if (!lineaDeProductoListNew.contains(lineaDeProductoListOldLineaDeProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LineaDeProducto " + lineaDeProductoListOldLineaDeProducto + " since its categoriaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Fabricante> attachedFabricanteListNew = new ArrayList<Fabricante>();
            for (Fabricante fabricanteListNewFabricanteToAttach : fabricanteListNew) {
                fabricanteListNewFabricanteToAttach = em.getReference(fabricanteListNewFabricanteToAttach.getClass(), fabricanteListNewFabricanteToAttach.getId());
                attachedFabricanteListNew.add(fabricanteListNewFabricanteToAttach);
            }
            fabricanteListNew = attachedFabricanteListNew;
            categoriaProducto.setFabricanteList(fabricanteListNew);
            List<LineaDeProducto> attachedLineaDeProductoListNew = new ArrayList<LineaDeProducto>();
            for (LineaDeProducto lineaDeProductoListNewLineaDeProductoToAttach : lineaDeProductoListNew) {
                lineaDeProductoListNewLineaDeProductoToAttach = em.getReference(lineaDeProductoListNewLineaDeProductoToAttach.getClass(), lineaDeProductoListNewLineaDeProductoToAttach.getId());
                attachedLineaDeProductoListNew.add(lineaDeProductoListNewLineaDeProductoToAttach);
            }
            lineaDeProductoListNew = attachedLineaDeProductoListNew;
            categoriaProducto.setLineaDeProductoList(lineaDeProductoListNew);
            categoriaProducto = em.merge(categoriaProducto);
            for (Fabricante fabricanteListOldFabricante : fabricanteListOld) {
                if (!fabricanteListNew.contains(fabricanteListOldFabricante)) {
                    fabricanteListOldFabricante.getCategoriaProductoList().remove(categoriaProducto);
                    fabricanteListOldFabricante = em.merge(fabricanteListOldFabricante);
                }
            }
            for (Fabricante fabricanteListNewFabricante : fabricanteListNew) {
                if (!fabricanteListOld.contains(fabricanteListNewFabricante)) {
                    fabricanteListNewFabricante.getCategoriaProductoList().add(categoriaProducto);
                    fabricanteListNewFabricante = em.merge(fabricanteListNewFabricante);
                }
            }
            for (LineaDeProducto lineaDeProductoListNewLineaDeProducto : lineaDeProductoListNew) {
                if (!lineaDeProductoListOld.contains(lineaDeProductoListNewLineaDeProducto)) {
                    CategoriaProducto oldCategoriaidOfLineaDeProductoListNewLineaDeProducto = lineaDeProductoListNewLineaDeProducto.getCategoriaid();
                    lineaDeProductoListNewLineaDeProducto.setCategoriaid(categoriaProducto);
                    lineaDeProductoListNewLineaDeProducto = em.merge(lineaDeProductoListNewLineaDeProducto);
                    if (oldCategoriaidOfLineaDeProductoListNewLineaDeProducto != null && !oldCategoriaidOfLineaDeProductoListNewLineaDeProducto.equals(categoriaProducto)) {
                        oldCategoriaidOfLineaDeProductoListNewLineaDeProducto.getLineaDeProductoList().remove(lineaDeProductoListNewLineaDeProducto);
                        oldCategoriaidOfLineaDeProductoListNewLineaDeProducto = em.merge(oldCategoriaidOfLineaDeProductoListNewLineaDeProducto);
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
                Integer id = categoriaProducto.getId();
                if (findCategoriaProducto(id) == null) {
                    throw new NonexistentEntityException("The categoriaProducto with id " + id + " no longer exists.");
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
            CategoriaProducto categoriaProducto;
            try {
                categoriaProducto = em.getReference(CategoriaProducto.class, id);
                categoriaProducto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaProducto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<LineaDeProducto> lineaDeProductoListOrphanCheck = categoriaProducto.getLineaDeProductoList();
            for (LineaDeProducto lineaDeProductoListOrphanCheckLineaDeProducto : lineaDeProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CategoriaProducto (" + categoriaProducto + ") cannot be destroyed since the LineaDeProducto " + lineaDeProductoListOrphanCheckLineaDeProducto + " in its lineaDeProductoList field has a non-nullable categoriaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Fabricante> fabricanteList = categoriaProducto.getFabricanteList();
            for (Fabricante fabricanteListFabricante : fabricanteList) {
                fabricanteListFabricante.getCategoriaProductoList().remove(categoriaProducto);
                fabricanteListFabricante = em.merge(fabricanteListFabricante);
            }
            em.remove(categoriaProducto);
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

    public List<CategoriaProducto> findCategoriaProductoEntities() {
        return findCategoriaProductoEntities(true, -1, -1);
    }

    public List<CategoriaProducto> findCategoriaProductoEntities(int maxResults, int firstResult) {
        return findCategoriaProductoEntities(false, maxResults, firstResult);
    }

    private List<CategoriaProducto> findCategoriaProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaProducto.class));
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

    public CategoriaProducto findCategoriaProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaProducto> rt = cq.from(CategoriaProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
