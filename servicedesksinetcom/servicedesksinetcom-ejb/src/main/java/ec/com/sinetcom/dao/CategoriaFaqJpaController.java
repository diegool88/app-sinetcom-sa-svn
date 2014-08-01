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
import ec.com.sinetcom.orm.CategoriaFaq;
import java.util.ArrayList;
import java.util.List;
import ec.com.sinetcom.orm.Faq;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class CategoriaFaqJpaController implements Serializable {

    public CategoriaFaqJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaFaq categoriaFaq) throws RollbackFailureException, Exception {
        if (categoriaFaq.getCategoriaFaqList() == null) {
            categoriaFaq.setCategoriaFaqList(new ArrayList<CategoriaFaq>());
        }
        if (categoriaFaq.getFaqList() == null) {
            categoriaFaq.setFaqList(new ArrayList<Faq>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoriaFaq codigoPadre = categoriaFaq.getCodigoPadre();
            if (codigoPadre != null) {
                codigoPadre = em.getReference(codigoPadre.getClass(), codigoPadre.getCodigo());
                categoriaFaq.setCodigoPadre(codigoPadre);
            }
            List<CategoriaFaq> attachedCategoriaFaqList = new ArrayList<CategoriaFaq>();
            for (CategoriaFaq categoriaFaqListCategoriaFaqToAttach : categoriaFaq.getCategoriaFaqList()) {
                categoriaFaqListCategoriaFaqToAttach = em.getReference(categoriaFaqListCategoriaFaqToAttach.getClass(), categoriaFaqListCategoriaFaqToAttach.getCodigo());
                attachedCategoriaFaqList.add(categoriaFaqListCategoriaFaqToAttach);
            }
            categoriaFaq.setCategoriaFaqList(attachedCategoriaFaqList);
            List<Faq> attachedFaqList = new ArrayList<Faq>();
            for (Faq faqListFaqToAttach : categoriaFaq.getFaqList()) {
                faqListFaqToAttach = em.getReference(faqListFaqToAttach.getClass(), faqListFaqToAttach.getId());
                attachedFaqList.add(faqListFaqToAttach);
            }
            categoriaFaq.setFaqList(attachedFaqList);
            em.persist(categoriaFaq);
            if (codigoPadre != null) {
                codigoPadre.getCategoriaFaqList().add(categoriaFaq);
                codigoPadre = em.merge(codigoPadre);
            }
            for (CategoriaFaq categoriaFaqListCategoriaFaq : categoriaFaq.getCategoriaFaqList()) {
                CategoriaFaq oldCodigoPadreOfCategoriaFaqListCategoriaFaq = categoriaFaqListCategoriaFaq.getCodigoPadre();
                categoriaFaqListCategoriaFaq.setCodigoPadre(categoriaFaq);
                categoriaFaqListCategoriaFaq = em.merge(categoriaFaqListCategoriaFaq);
                if (oldCodigoPadreOfCategoriaFaqListCategoriaFaq != null) {
                    oldCodigoPadreOfCategoriaFaqListCategoriaFaq.getCategoriaFaqList().remove(categoriaFaqListCategoriaFaq);
                    oldCodigoPadreOfCategoriaFaqListCategoriaFaq = em.merge(oldCodigoPadreOfCategoriaFaqListCategoriaFaq);
                }
            }
            for (Faq faqListFaq : categoriaFaq.getFaqList()) {
                CategoriaFaq oldCategoriaFaqcodigoOfFaqListFaq = faqListFaq.getCategoriaFaqcodigo();
                faqListFaq.setCategoriaFaqcodigo(categoriaFaq);
                faqListFaq = em.merge(faqListFaq);
                if (oldCategoriaFaqcodigoOfFaqListFaq != null) {
                    oldCategoriaFaqcodigoOfFaqListFaq.getFaqList().remove(faqListFaq);
                    oldCategoriaFaqcodigoOfFaqListFaq = em.merge(oldCategoriaFaqcodigoOfFaqListFaq);
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

    public void edit(CategoriaFaq categoriaFaq) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoriaFaq persistentCategoriaFaq = em.find(CategoriaFaq.class, categoriaFaq.getCodigo());
            CategoriaFaq codigoPadreOld = persistentCategoriaFaq.getCodigoPadre();
            CategoriaFaq codigoPadreNew = categoriaFaq.getCodigoPadre();
            List<CategoriaFaq> categoriaFaqListOld = persistentCategoriaFaq.getCategoriaFaqList();
            List<CategoriaFaq> categoriaFaqListNew = categoriaFaq.getCategoriaFaqList();
            List<Faq> faqListOld = persistentCategoriaFaq.getFaqList();
            List<Faq> faqListNew = categoriaFaq.getFaqList();
            List<String> illegalOrphanMessages = null;
            for (Faq faqListOldFaq : faqListOld) {
                if (!faqListNew.contains(faqListOldFaq)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Faq " + faqListOldFaq + " since its categoriaFaqcodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoPadreNew != null) {
                codigoPadreNew = em.getReference(codigoPadreNew.getClass(), codigoPadreNew.getCodigo());
                categoriaFaq.setCodigoPadre(codigoPadreNew);
            }
            List<CategoriaFaq> attachedCategoriaFaqListNew = new ArrayList<CategoriaFaq>();
            for (CategoriaFaq categoriaFaqListNewCategoriaFaqToAttach : categoriaFaqListNew) {
                categoriaFaqListNewCategoriaFaqToAttach = em.getReference(categoriaFaqListNewCategoriaFaqToAttach.getClass(), categoriaFaqListNewCategoriaFaqToAttach.getCodigo());
                attachedCategoriaFaqListNew.add(categoriaFaqListNewCategoriaFaqToAttach);
            }
            categoriaFaqListNew = attachedCategoriaFaqListNew;
            categoriaFaq.setCategoriaFaqList(categoriaFaqListNew);
            List<Faq> attachedFaqListNew = new ArrayList<Faq>();
            for (Faq faqListNewFaqToAttach : faqListNew) {
                faqListNewFaqToAttach = em.getReference(faqListNewFaqToAttach.getClass(), faqListNewFaqToAttach.getId());
                attachedFaqListNew.add(faqListNewFaqToAttach);
            }
            faqListNew = attachedFaqListNew;
            categoriaFaq.setFaqList(faqListNew);
            categoriaFaq = em.merge(categoriaFaq);
            if (codigoPadreOld != null && !codigoPadreOld.equals(codigoPadreNew)) {
                codigoPadreOld.getCategoriaFaqList().remove(categoriaFaq);
                codigoPadreOld = em.merge(codigoPadreOld);
            }
            if (codigoPadreNew != null && !codigoPadreNew.equals(codigoPadreOld)) {
                codigoPadreNew.getCategoriaFaqList().add(categoriaFaq);
                codigoPadreNew = em.merge(codigoPadreNew);
            }
            for (CategoriaFaq categoriaFaqListOldCategoriaFaq : categoriaFaqListOld) {
                if (!categoriaFaqListNew.contains(categoriaFaqListOldCategoriaFaq)) {
                    categoriaFaqListOldCategoriaFaq.setCodigoPadre(null);
                    categoriaFaqListOldCategoriaFaq = em.merge(categoriaFaqListOldCategoriaFaq);
                }
            }
            for (CategoriaFaq categoriaFaqListNewCategoriaFaq : categoriaFaqListNew) {
                if (!categoriaFaqListOld.contains(categoriaFaqListNewCategoriaFaq)) {
                    CategoriaFaq oldCodigoPadreOfCategoriaFaqListNewCategoriaFaq = categoriaFaqListNewCategoriaFaq.getCodigoPadre();
                    categoriaFaqListNewCategoriaFaq.setCodigoPadre(categoriaFaq);
                    categoriaFaqListNewCategoriaFaq = em.merge(categoriaFaqListNewCategoriaFaq);
                    if (oldCodigoPadreOfCategoriaFaqListNewCategoriaFaq != null && !oldCodigoPadreOfCategoriaFaqListNewCategoriaFaq.equals(categoriaFaq)) {
                        oldCodigoPadreOfCategoriaFaqListNewCategoriaFaq.getCategoriaFaqList().remove(categoriaFaqListNewCategoriaFaq);
                        oldCodigoPadreOfCategoriaFaqListNewCategoriaFaq = em.merge(oldCodigoPadreOfCategoriaFaqListNewCategoriaFaq);
                    }
                }
            }
            for (Faq faqListNewFaq : faqListNew) {
                if (!faqListOld.contains(faqListNewFaq)) {
                    CategoriaFaq oldCategoriaFaqcodigoOfFaqListNewFaq = faqListNewFaq.getCategoriaFaqcodigo();
                    faqListNewFaq.setCategoriaFaqcodigo(categoriaFaq);
                    faqListNewFaq = em.merge(faqListNewFaq);
                    if (oldCategoriaFaqcodigoOfFaqListNewFaq != null && !oldCategoriaFaqcodigoOfFaqListNewFaq.equals(categoriaFaq)) {
                        oldCategoriaFaqcodigoOfFaqListNewFaq.getFaqList().remove(faqListNewFaq);
                        oldCategoriaFaqcodigoOfFaqListNewFaq = em.merge(oldCategoriaFaqcodigoOfFaqListNewFaq);
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
                Integer id = categoriaFaq.getCodigo();
                if (findCategoriaFaq(id) == null) {
                    throw new NonexistentEntityException("The categoriaFaq with id " + id + " no longer exists.");
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
            CategoriaFaq categoriaFaq;
            try {
                categoriaFaq = em.getReference(CategoriaFaq.class, id);
                categoriaFaq.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaFaq with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Faq> faqListOrphanCheck = categoriaFaq.getFaqList();
            for (Faq faqListOrphanCheckFaq : faqListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CategoriaFaq (" + categoriaFaq + ") cannot be destroyed since the Faq " + faqListOrphanCheckFaq + " in its faqList field has a non-nullable categoriaFaqcodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CategoriaFaq codigoPadre = categoriaFaq.getCodigoPadre();
            if (codigoPadre != null) {
                codigoPadre.getCategoriaFaqList().remove(categoriaFaq);
                codigoPadre = em.merge(codigoPadre);
            }
            List<CategoriaFaq> categoriaFaqList = categoriaFaq.getCategoriaFaqList();
            for (CategoriaFaq categoriaFaqListCategoriaFaq : categoriaFaqList) {
                categoriaFaqListCategoriaFaq.setCodigoPadre(null);
                categoriaFaqListCategoriaFaq = em.merge(categoriaFaqListCategoriaFaq);
            }
            em.remove(categoriaFaq);
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

    public List<CategoriaFaq> findCategoriaFaqEntities() {
        return findCategoriaFaqEntities(true, -1, -1);
    }

    public List<CategoriaFaq> findCategoriaFaqEntities(int maxResults, int firstResult) {
        return findCategoriaFaqEntities(false, maxResults, firstResult);
    }

    private List<CategoriaFaq> findCategoriaFaqEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaFaq.class));
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

    public CategoriaFaq findCategoriaFaq(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaFaq.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaFaqCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaFaq> rt = cq.from(CategoriaFaq.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
