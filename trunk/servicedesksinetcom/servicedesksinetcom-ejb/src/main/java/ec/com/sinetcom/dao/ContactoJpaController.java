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
import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.Contacto;
import ec.com.sinetcom.orm.Contrato;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class ContactoJpaController implements Serializable {

    public ContactoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contacto contacto) throws RollbackFailureException, Exception {
        if (contacto.getContratoList() == null) {
            contacto.setContratoList(new ArrayList<Contrato>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ClienteEmpresa clienteEmpresaruc = contacto.getClienteEmpresaruc();
            if (clienteEmpresaruc != null) {
                clienteEmpresaruc = em.getReference(clienteEmpresaruc.getClass(), clienteEmpresaruc.getRuc());
                contacto.setClienteEmpresaruc(clienteEmpresaruc);
            }
            List<Contrato> attachedContratoList = new ArrayList<Contrato>();
            for (Contrato contratoListContratoToAttach : contacto.getContratoList()) {
                contratoListContratoToAttach = em.getReference(contratoListContratoToAttach.getClass(), contratoListContratoToAttach.getNumero());
                attachedContratoList.add(contratoListContratoToAttach);
            }
            contacto.setContratoList(attachedContratoList);
            em.persist(contacto);
            if (clienteEmpresaruc != null) {
                clienteEmpresaruc.getContactoList().add(contacto);
                clienteEmpresaruc = em.merge(clienteEmpresaruc);
            }
            for (Contrato contratoListContrato : contacto.getContratoList()) {
                Contacto oldAdministradorDeContratoOfContratoListContrato = contratoListContrato.getAdministradorDeContrato();
                contratoListContrato.setAdministradorDeContrato(contacto);
                contratoListContrato = em.merge(contratoListContrato);
                if (oldAdministradorDeContratoOfContratoListContrato != null) {
                    oldAdministradorDeContratoOfContratoListContrato.getContratoList().remove(contratoListContrato);
                    oldAdministradorDeContratoOfContratoListContrato = em.merge(oldAdministradorDeContratoOfContratoListContrato);
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

    public void edit(Contacto contacto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contacto persistentContacto = em.find(Contacto.class, contacto.getId());
            ClienteEmpresa clienteEmpresarucOld = persistentContacto.getClienteEmpresaruc();
            ClienteEmpresa clienteEmpresarucNew = contacto.getClienteEmpresaruc();
            List<Contrato> contratoListOld = persistentContacto.getContratoList();
            List<Contrato> contratoListNew = contacto.getContratoList();
            List<String> illegalOrphanMessages = null;
            for (Contrato contratoListOldContrato : contratoListOld) {
                if (!contratoListNew.contains(contratoListOldContrato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contrato " + contratoListOldContrato + " since its administradorDeContrato field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteEmpresarucNew != null) {
                clienteEmpresarucNew = em.getReference(clienteEmpresarucNew.getClass(), clienteEmpresarucNew.getRuc());
                contacto.setClienteEmpresaruc(clienteEmpresarucNew);
            }
            List<Contrato> attachedContratoListNew = new ArrayList<Contrato>();
            for (Contrato contratoListNewContratoToAttach : contratoListNew) {
                contratoListNewContratoToAttach = em.getReference(contratoListNewContratoToAttach.getClass(), contratoListNewContratoToAttach.getNumero());
                attachedContratoListNew.add(contratoListNewContratoToAttach);
            }
            contratoListNew = attachedContratoListNew;
            contacto.setContratoList(contratoListNew);
            contacto = em.merge(contacto);
            if (clienteEmpresarucOld != null && !clienteEmpresarucOld.equals(clienteEmpresarucNew)) {
                clienteEmpresarucOld.getContactoList().remove(contacto);
                clienteEmpresarucOld = em.merge(clienteEmpresarucOld);
            }
            if (clienteEmpresarucNew != null && !clienteEmpresarucNew.equals(clienteEmpresarucOld)) {
                clienteEmpresarucNew.getContactoList().add(contacto);
                clienteEmpresarucNew = em.merge(clienteEmpresarucNew);
            }
            for (Contrato contratoListNewContrato : contratoListNew) {
                if (!contratoListOld.contains(contratoListNewContrato)) {
                    Contacto oldAdministradorDeContratoOfContratoListNewContrato = contratoListNewContrato.getAdministradorDeContrato();
                    contratoListNewContrato.setAdministradorDeContrato(contacto);
                    contratoListNewContrato = em.merge(contratoListNewContrato);
                    if (oldAdministradorDeContratoOfContratoListNewContrato != null && !oldAdministradorDeContratoOfContratoListNewContrato.equals(contacto)) {
                        oldAdministradorDeContratoOfContratoListNewContrato.getContratoList().remove(contratoListNewContrato);
                        oldAdministradorDeContratoOfContratoListNewContrato = em.merge(oldAdministradorDeContratoOfContratoListNewContrato);
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
                Integer id = contacto.getId();
                if (findContacto(id) == null) {
                    throw new NonexistentEntityException("The contacto with id " + id + " no longer exists.");
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
            Contacto contacto;
            try {
                contacto = em.getReference(Contacto.class, id);
                contacto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contacto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Contrato> contratoListOrphanCheck = contacto.getContratoList();
            for (Contrato contratoListOrphanCheckContrato : contratoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contacto (" + contacto + ") cannot be destroyed since the Contrato " + contratoListOrphanCheckContrato + " in its contratoList field has a non-nullable administradorDeContrato field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ClienteEmpresa clienteEmpresaruc = contacto.getClienteEmpresaruc();
            if (clienteEmpresaruc != null) {
                clienteEmpresaruc.getContactoList().remove(contacto);
                clienteEmpresaruc = em.merge(clienteEmpresaruc);
            }
            em.remove(contacto);
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

    public List<Contacto> findContactoEntities() {
        return findContactoEntities(true, -1, -1);
    }

    public List<Contacto> findContactoEntities(int maxResults, int firstResult) {
        return findContactoEntities(false, maxResults, firstResult);
    }

    private List<Contacto> findContactoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contacto.class));
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

    public Contacto findContacto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contacto.class, id);
        } finally {
            em.close();
        }
    }

    public int getContactoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contacto> rt = cq.from(Contacto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
