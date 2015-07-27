/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.ClienteEmpresaFacade;
import ec.com.sinetcom.dao.ContactoFacade;
import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.Contacto;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Andres
 */
@Stateless
@LocalBean
public class ContactoServicio {

    @EJB
    private ContactoFacade contactoFacade;
    @EJB
    private ClienteEmpresaFacade clienteEmpresaFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public List<Contacto> cargarContactos() {
        return contactoFacade.findAll();
    }

    public boolean crearContacto(Contacto contacto) {
        try {
            contactoFacade.create(contacto);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean editarContacto(Contacto contacto) {
        try {
            contactoFacade.edit(contacto);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<ClienteEmpresa> cargarClientesEmpresas() {
        return clienteEmpresaFacade.findAll();
    }

    public ClienteEmpresa recuperarClienteEmpresa(String id) {
        return clienteEmpresaFacade.find(id);
    }
    
    public boolean verificarActualizacionCedulaCiudadania(Contacto contacto, String cedulaNueva){
        Contacto contactoQ = this.contactoFacade.obtenerContactoPorCedulaCiudadania(cedulaNueva);
        return contactoQ != null ? contactoQ.equals(contacto) : true;
    }
    
    public boolean verificarActualizacionEmail(Contacto contacto, String emailNuevo){
        Contacto contactoQ = this.contactoFacade.obtenerContactoPorCorreoElectronico(emailNuevo);
        return contactoQ != null ? contactoQ.equals(contacto) : true;
    }

    public boolean eliminarTipoContrato(Integer contacto) {
        try {
            Contacto aEliminar = contactoFacade.find(contacto);
            if(contactoFacade.tieneContratosACargo(contacto)){
                throw new Exception("No se puede eliminar un contacto que administra un contrato");
            }
            if (aEliminar != null) {
                contactoFacade.remove(aEliminar);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
}
