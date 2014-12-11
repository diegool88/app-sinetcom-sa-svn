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
    
    public List<Contacto> cragarContactos() {
        return contactoFacade.findAll();
    }
    
    public void crearContacto(Contacto contacto) {
        contactoFacade.create(contacto);
   }
    
    public List<ClienteEmpresa> cargarClientesEmpresas() {
        return clienteEmpresaFacade.findAll();
    }
    
    public ClienteEmpresa recuperarClienteEmpresa(String id) {
        return clienteEmpresaFacade.find(id);        
    }
    
     public void eliminarTipoContrato(Integer contacto) {
        try {
            Contacto aEliminar = contactoFacade.find(contacto);
            if (aEliminar != null) {
                contactoFacade.remove(aEliminar);
            }                         
        } catch (Exception e) {
        }
               
    }
}
