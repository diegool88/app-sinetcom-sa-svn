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
import ec.com.sinetcom.orm.Usuario;
import ec.com.sinetcom.orm.TipoEmpresa;
import ec.com.sinetcom.orm.Contacto;
import java.util.ArrayList;
import java.util.List;
import ec.com.sinetcom.orm.Contrato;
import ec.com.sinetcom.orm.ClienteDireccion;
import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.RegistroDeMovimientoDeInventario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author diegoflores
 */
public class ClienteEmpresaJpaController implements Serializable {

    public ClienteEmpresaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClienteEmpresa clienteEmpresa) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (clienteEmpresa.getContactoList() == null) {
            clienteEmpresa.setContactoList(new ArrayList<Contacto>());
        }
        if (clienteEmpresa.getContratoList() == null) {
            clienteEmpresa.setContratoList(new ArrayList<Contrato>());
        }
        if (clienteEmpresa.getClienteDireccionList() == null) {
            clienteEmpresa.setClienteDireccionList(new ArrayList<ClienteDireccion>());
        }
        if (clienteEmpresa.getRegistroDeMovimientoDeInventarioList() == null) {
            clienteEmpresa.setRegistroDeMovimientoDeInventarioList(new ArrayList<RegistroDeMovimientoDeInventario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuarioid = clienteEmpresa.getUsuarioid();
            if (usuarioid != null) {
                usuarioid = em.getReference(usuarioid.getClass(), usuarioid.getId());
                clienteEmpresa.setUsuarioid(usuarioid);
            }
            TipoEmpresa tipoEmpresaid = clienteEmpresa.getTipoEmpresaid();
            if (tipoEmpresaid != null) {
                tipoEmpresaid = em.getReference(tipoEmpresaid.getClass(), tipoEmpresaid.getId());
                clienteEmpresa.setTipoEmpresaid(tipoEmpresaid);
            }
            List<Contacto> attachedContactoList = new ArrayList<Contacto>();
            for (Contacto contactoListContactoToAttach : clienteEmpresa.getContactoList()) {
                contactoListContactoToAttach = em.getReference(contactoListContactoToAttach.getClass(), contactoListContactoToAttach.getId());
                attachedContactoList.add(contactoListContactoToAttach);
            }
            clienteEmpresa.setContactoList(attachedContactoList);
            List<Contrato> attachedContratoList = new ArrayList<Contrato>();
            for (Contrato contratoListContratoToAttach : clienteEmpresa.getContratoList()) {
                contratoListContratoToAttach = em.getReference(contratoListContratoToAttach.getClass(), contratoListContratoToAttach.getNumero());
                attachedContratoList.add(contratoListContratoToAttach);
            }
            clienteEmpresa.setContratoList(attachedContratoList);
            List<ClienteDireccion> attachedClienteDireccionList = new ArrayList<ClienteDireccion>();
            for (ClienteDireccion clienteDireccionListClienteDireccionToAttach : clienteEmpresa.getClienteDireccionList()) {
                clienteDireccionListClienteDireccionToAttach = em.getReference(clienteDireccionListClienteDireccionToAttach.getClass(), clienteDireccionListClienteDireccionToAttach.getClienteDireccionPK());
                attachedClienteDireccionList.add(clienteDireccionListClienteDireccionToAttach);
            }
            clienteEmpresa.setClienteDireccionList(attachedClienteDireccionList);
            List<RegistroDeMovimientoDeInventario> attachedRegistroDeMovimientoDeInventarioList = new ArrayList<RegistroDeMovimientoDeInventario>();
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach : clienteEmpresa.getRegistroDeMovimientoDeInventarioList()) {
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach = em.getReference(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach.getClass(), registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach.getCodigo());
                attachedRegistroDeMovimientoDeInventarioList.add(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventarioToAttach);
            }
            clienteEmpresa.setRegistroDeMovimientoDeInventarioList(attachedRegistroDeMovimientoDeInventarioList);
            em.persist(clienteEmpresa);
            if (usuarioid != null) {
                usuarioid.getClienteEmpresaList().add(clienteEmpresa);
                usuarioid = em.merge(usuarioid);
            }
            if (tipoEmpresaid != null) {
                tipoEmpresaid.getClienteEmpresaList().add(clienteEmpresa);
                tipoEmpresaid = em.merge(tipoEmpresaid);
            }
            for (Contacto contactoListContacto : clienteEmpresa.getContactoList()) {
                ClienteEmpresa oldClienteEmpresarucOfContactoListContacto = contactoListContacto.getClienteEmpresaruc();
                contactoListContacto.setClienteEmpresaruc(clienteEmpresa);
                contactoListContacto = em.merge(contactoListContacto);
                if (oldClienteEmpresarucOfContactoListContacto != null) {
                    oldClienteEmpresarucOfContactoListContacto.getContactoList().remove(contactoListContacto);
                    oldClienteEmpresarucOfContactoListContacto = em.merge(oldClienteEmpresarucOfContactoListContacto);
                }
            }
            for (Contrato contratoListContrato : clienteEmpresa.getContratoList()) {
                ClienteEmpresa oldClienteEmpresarucOfContratoListContrato = contratoListContrato.getClienteEmpresaruc();
                contratoListContrato.setClienteEmpresaruc(clienteEmpresa);
                contratoListContrato = em.merge(contratoListContrato);
                if (oldClienteEmpresarucOfContratoListContrato != null) {
                    oldClienteEmpresarucOfContratoListContrato.getContratoList().remove(contratoListContrato);
                    oldClienteEmpresarucOfContratoListContrato = em.merge(oldClienteEmpresarucOfContratoListContrato);
                }
            }
            for (ClienteDireccion clienteDireccionListClienteDireccion : clienteEmpresa.getClienteDireccionList()) {
                ClienteEmpresa oldClienteEmpresaOfClienteDireccionListClienteDireccion = clienteDireccionListClienteDireccion.getClienteEmpresa();
                clienteDireccionListClienteDireccion.setClienteEmpresa(clienteEmpresa);
                clienteDireccionListClienteDireccion = em.merge(clienteDireccionListClienteDireccion);
                if (oldClienteEmpresaOfClienteDireccionListClienteDireccion != null) {
                    oldClienteEmpresaOfClienteDireccionListClienteDireccion.getClienteDireccionList().remove(clienteDireccionListClienteDireccion);
                    oldClienteEmpresaOfClienteDireccionListClienteDireccion = em.merge(oldClienteEmpresaOfClienteDireccionListClienteDireccion);
                }
            }
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario : clienteEmpresa.getRegistroDeMovimientoDeInventarioList()) {
                ClienteEmpresa oldClienteEmpresarucOfRegistroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario = registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario.getClienteEmpresaruc();
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario.setClienteEmpresaruc(clienteEmpresa);
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario = em.merge(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario);
                if (oldClienteEmpresarucOfRegistroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario != null) {
                    oldClienteEmpresarucOfRegistroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario.getRegistroDeMovimientoDeInventarioList().remove(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario);
                    oldClienteEmpresarucOfRegistroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario = em.merge(oldClienteEmpresarucOfRegistroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findClienteEmpresa(clienteEmpresa.getRuc()) != null) {
                throw new PreexistingEntityException("ClienteEmpresa " + clienteEmpresa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClienteEmpresa clienteEmpresa) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ClienteEmpresa persistentClienteEmpresa = em.find(ClienteEmpresa.class, clienteEmpresa.getRuc());
            Usuario usuarioidOld = persistentClienteEmpresa.getUsuarioid();
            Usuario usuarioidNew = clienteEmpresa.getUsuarioid();
            TipoEmpresa tipoEmpresaidOld = persistentClienteEmpresa.getTipoEmpresaid();
            TipoEmpresa tipoEmpresaidNew = clienteEmpresa.getTipoEmpresaid();
            List<Contacto> contactoListOld = persistentClienteEmpresa.getContactoList();
            List<Contacto> contactoListNew = clienteEmpresa.getContactoList();
            List<Contrato> contratoListOld = persistentClienteEmpresa.getContratoList();
            List<Contrato> contratoListNew = clienteEmpresa.getContratoList();
            List<ClienteDireccion> clienteDireccionListOld = persistentClienteEmpresa.getClienteDireccionList();
            List<ClienteDireccion> clienteDireccionListNew = clienteEmpresa.getClienteDireccionList();
            List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioListOld = persistentClienteEmpresa.getRegistroDeMovimientoDeInventarioList();
            List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioListNew = clienteEmpresa.getRegistroDeMovimientoDeInventarioList();
            List<String> illegalOrphanMessages = null;
            for (Contacto contactoListOldContacto : contactoListOld) {
                if (!contactoListNew.contains(contactoListOldContacto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contacto " + contactoListOldContacto + " since its clienteEmpresaruc field is not nullable.");
                }
            }
            for (Contrato contratoListOldContrato : contratoListOld) {
                if (!contratoListNew.contains(contratoListOldContrato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contrato " + contratoListOldContrato + " since its clienteEmpresaruc field is not nullable.");
                }
            }
            for (ClienteDireccion clienteDireccionListOldClienteDireccion : clienteDireccionListOld) {
                if (!clienteDireccionListNew.contains(clienteDireccionListOldClienteDireccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClienteDireccion " + clienteDireccionListOldClienteDireccion + " since its clienteEmpresa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioidNew != null) {
                usuarioidNew = em.getReference(usuarioidNew.getClass(), usuarioidNew.getId());
                clienteEmpresa.setUsuarioid(usuarioidNew);
            }
            if (tipoEmpresaidNew != null) {
                tipoEmpresaidNew = em.getReference(tipoEmpresaidNew.getClass(), tipoEmpresaidNew.getId());
                clienteEmpresa.setTipoEmpresaid(tipoEmpresaidNew);
            }
            List<Contacto> attachedContactoListNew = new ArrayList<Contacto>();
            for (Contacto contactoListNewContactoToAttach : contactoListNew) {
                contactoListNewContactoToAttach = em.getReference(contactoListNewContactoToAttach.getClass(), contactoListNewContactoToAttach.getId());
                attachedContactoListNew.add(contactoListNewContactoToAttach);
            }
            contactoListNew = attachedContactoListNew;
            clienteEmpresa.setContactoList(contactoListNew);
            List<Contrato> attachedContratoListNew = new ArrayList<Contrato>();
            for (Contrato contratoListNewContratoToAttach : contratoListNew) {
                contratoListNewContratoToAttach = em.getReference(contratoListNewContratoToAttach.getClass(), contratoListNewContratoToAttach.getNumero());
                attachedContratoListNew.add(contratoListNewContratoToAttach);
            }
            contratoListNew = attachedContratoListNew;
            clienteEmpresa.setContratoList(contratoListNew);
            List<ClienteDireccion> attachedClienteDireccionListNew = new ArrayList<ClienteDireccion>();
            for (ClienteDireccion clienteDireccionListNewClienteDireccionToAttach : clienteDireccionListNew) {
                clienteDireccionListNewClienteDireccionToAttach = em.getReference(clienteDireccionListNewClienteDireccionToAttach.getClass(), clienteDireccionListNewClienteDireccionToAttach.getClienteDireccionPK());
                attachedClienteDireccionListNew.add(clienteDireccionListNewClienteDireccionToAttach);
            }
            clienteDireccionListNew = attachedClienteDireccionListNew;
            clienteEmpresa.setClienteDireccionList(clienteDireccionListNew);
            List<RegistroDeMovimientoDeInventario> attachedRegistroDeMovimientoDeInventarioListNew = new ArrayList<RegistroDeMovimientoDeInventario>();
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach : registroDeMovimientoDeInventarioListNew) {
                registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach = em.getReference(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach.getClass(), registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach.getCodigo());
                attachedRegistroDeMovimientoDeInventarioListNew.add(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventarioToAttach);
            }
            registroDeMovimientoDeInventarioListNew = attachedRegistroDeMovimientoDeInventarioListNew;
            clienteEmpresa.setRegistroDeMovimientoDeInventarioList(registroDeMovimientoDeInventarioListNew);
            clienteEmpresa = em.merge(clienteEmpresa);
            if (usuarioidOld != null && !usuarioidOld.equals(usuarioidNew)) {
                usuarioidOld.getClienteEmpresaList().remove(clienteEmpresa);
                usuarioidOld = em.merge(usuarioidOld);
            }
            if (usuarioidNew != null && !usuarioidNew.equals(usuarioidOld)) {
                usuarioidNew.getClienteEmpresaList().add(clienteEmpresa);
                usuarioidNew = em.merge(usuarioidNew);
            }
            if (tipoEmpresaidOld != null && !tipoEmpresaidOld.equals(tipoEmpresaidNew)) {
                tipoEmpresaidOld.getClienteEmpresaList().remove(clienteEmpresa);
                tipoEmpresaidOld = em.merge(tipoEmpresaidOld);
            }
            if (tipoEmpresaidNew != null && !tipoEmpresaidNew.equals(tipoEmpresaidOld)) {
                tipoEmpresaidNew.getClienteEmpresaList().add(clienteEmpresa);
                tipoEmpresaidNew = em.merge(tipoEmpresaidNew);
            }
            for (Contacto contactoListNewContacto : contactoListNew) {
                if (!contactoListOld.contains(contactoListNewContacto)) {
                    ClienteEmpresa oldClienteEmpresarucOfContactoListNewContacto = contactoListNewContacto.getClienteEmpresaruc();
                    contactoListNewContacto.setClienteEmpresaruc(clienteEmpresa);
                    contactoListNewContacto = em.merge(contactoListNewContacto);
                    if (oldClienteEmpresarucOfContactoListNewContacto != null && !oldClienteEmpresarucOfContactoListNewContacto.equals(clienteEmpresa)) {
                        oldClienteEmpresarucOfContactoListNewContacto.getContactoList().remove(contactoListNewContacto);
                        oldClienteEmpresarucOfContactoListNewContacto = em.merge(oldClienteEmpresarucOfContactoListNewContacto);
                    }
                }
            }
            for (Contrato contratoListNewContrato : contratoListNew) {
                if (!contratoListOld.contains(contratoListNewContrato)) {
                    ClienteEmpresa oldClienteEmpresarucOfContratoListNewContrato = contratoListNewContrato.getClienteEmpresaruc();
                    contratoListNewContrato.setClienteEmpresaruc(clienteEmpresa);
                    contratoListNewContrato = em.merge(contratoListNewContrato);
                    if (oldClienteEmpresarucOfContratoListNewContrato != null && !oldClienteEmpresarucOfContratoListNewContrato.equals(clienteEmpresa)) {
                        oldClienteEmpresarucOfContratoListNewContrato.getContratoList().remove(contratoListNewContrato);
                        oldClienteEmpresarucOfContratoListNewContrato = em.merge(oldClienteEmpresarucOfContratoListNewContrato);
                    }
                }
            }
            for (ClienteDireccion clienteDireccionListNewClienteDireccion : clienteDireccionListNew) {
                if (!clienteDireccionListOld.contains(clienteDireccionListNewClienteDireccion)) {
                    ClienteEmpresa oldClienteEmpresaOfClienteDireccionListNewClienteDireccion = clienteDireccionListNewClienteDireccion.getClienteEmpresa();
                    clienteDireccionListNewClienteDireccion.setClienteEmpresa(clienteEmpresa);
                    clienteDireccionListNewClienteDireccion = em.merge(clienteDireccionListNewClienteDireccion);
                    if (oldClienteEmpresaOfClienteDireccionListNewClienteDireccion != null && !oldClienteEmpresaOfClienteDireccionListNewClienteDireccion.equals(clienteEmpresa)) {
                        oldClienteEmpresaOfClienteDireccionListNewClienteDireccion.getClienteDireccionList().remove(clienteDireccionListNewClienteDireccion);
                        oldClienteEmpresaOfClienteDireccionListNewClienteDireccion = em.merge(oldClienteEmpresaOfClienteDireccionListNewClienteDireccion);
                    }
                }
            }
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario : registroDeMovimientoDeInventarioListOld) {
                if (!registroDeMovimientoDeInventarioListNew.contains(registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario)) {
                    registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario.setClienteEmpresaruc(null);
                    registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario = em.merge(registroDeMovimientoDeInventarioListOldRegistroDeMovimientoDeInventario);
                }
            }
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario : registroDeMovimientoDeInventarioListNew) {
                if (!registroDeMovimientoDeInventarioListOld.contains(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario)) {
                    ClienteEmpresa oldClienteEmpresarucOfRegistroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario = registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario.getClienteEmpresaruc();
                    registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario.setClienteEmpresaruc(clienteEmpresa);
                    registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario = em.merge(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario);
                    if (oldClienteEmpresarucOfRegistroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario != null && !oldClienteEmpresarucOfRegistroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario.equals(clienteEmpresa)) {
                        oldClienteEmpresarucOfRegistroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario.getRegistroDeMovimientoDeInventarioList().remove(registroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario);
                        oldClienteEmpresarucOfRegistroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario = em.merge(oldClienteEmpresarucOfRegistroDeMovimientoDeInventarioListNewRegistroDeMovimientoDeInventario);
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
                String id = clienteEmpresa.getRuc();
                if (findClienteEmpresa(id) == null) {
                    throw new NonexistentEntityException("The clienteEmpresa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ClienteEmpresa clienteEmpresa;
            try {
                clienteEmpresa = em.getReference(ClienteEmpresa.class, id);
                clienteEmpresa.getRuc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clienteEmpresa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Contacto> contactoListOrphanCheck = clienteEmpresa.getContactoList();
            for (Contacto contactoListOrphanCheckContacto : contactoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ClienteEmpresa (" + clienteEmpresa + ") cannot be destroyed since the Contacto " + contactoListOrphanCheckContacto + " in its contactoList field has a non-nullable clienteEmpresaruc field.");
            }
            List<Contrato> contratoListOrphanCheck = clienteEmpresa.getContratoList();
            for (Contrato contratoListOrphanCheckContrato : contratoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ClienteEmpresa (" + clienteEmpresa + ") cannot be destroyed since the Contrato " + contratoListOrphanCheckContrato + " in its contratoList field has a non-nullable clienteEmpresaruc field.");
            }
            List<ClienteDireccion> clienteDireccionListOrphanCheck = clienteEmpresa.getClienteDireccionList();
            for (ClienteDireccion clienteDireccionListOrphanCheckClienteDireccion : clienteDireccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ClienteEmpresa (" + clienteEmpresa + ") cannot be destroyed since the ClienteDireccion " + clienteDireccionListOrphanCheckClienteDireccion + " in its clienteDireccionList field has a non-nullable clienteEmpresa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuarioid = clienteEmpresa.getUsuarioid();
            if (usuarioid != null) {
                usuarioid.getClienteEmpresaList().remove(clienteEmpresa);
                usuarioid = em.merge(usuarioid);
            }
            TipoEmpresa tipoEmpresaid = clienteEmpresa.getTipoEmpresaid();
            if (tipoEmpresaid != null) {
                tipoEmpresaid.getClienteEmpresaList().remove(clienteEmpresa);
                tipoEmpresaid = em.merge(tipoEmpresaid);
            }
            List<RegistroDeMovimientoDeInventario> registroDeMovimientoDeInventarioList = clienteEmpresa.getRegistroDeMovimientoDeInventarioList();
            for (RegistroDeMovimientoDeInventario registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario : registroDeMovimientoDeInventarioList) {
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario.setClienteEmpresaruc(null);
                registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario = em.merge(registroDeMovimientoDeInventarioListRegistroDeMovimientoDeInventario);
            }
            em.remove(clienteEmpresa);
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

    public List<ClienteEmpresa> findClienteEmpresaEntities() {
        return findClienteEmpresaEntities(true, -1, -1);
    }

    public List<ClienteEmpresa> findClienteEmpresaEntities(int maxResults, int firstResult) {
        return findClienteEmpresaEntities(false, maxResults, firstResult);
    }

    private List<ClienteEmpresa> findClienteEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClienteEmpresa.class));
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

    public ClienteEmpresa findClienteEmpresa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClienteEmpresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClienteEmpresa> rt = cq.from(ClienteEmpresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
