/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.ClienteEmpresaFacade;
import ec.com.sinetcom.dao.TipoEmpresaFacade;
import ec.com.sinetcom.dao.UsuarioFacade;
import ec.com.sinetcom.orm.ClienteEmpresa;
import ec.com.sinetcom.orm.TipoEmpresa;
import ec.com.sinetcom.orm.Usuario;
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
public class ClienteEmpresaServicio {
    
    @EJB
    private ClienteEmpresaFacade clienteEmpresaFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private TipoEmpresaFacade tipoEmpresaFacade;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public List<ClienteEmpresa> cargarClientesEmpresa(){
        return clienteEmpresaFacade.findAll();
    }
    
    public void crearClienteEmpresa(ClienteEmpresa empresa) {
        clienteEmpresaFacade.create(empresa);
    }
    
    public List<Usuario> cargarUsuarios() {
        return usuarioFacade.findAll();
    }
    
    public Usuario recuperarUsuario(Integer id) {
        return usuarioFacade.find(id);
    }
    
    public List<TipoEmpresa> cargarTiposEmpresa() {
        return tipoEmpresaFacade.findAll();
    }
    
    public TipoEmpresa recuperarTipoEmpresa(Integer id) {
        return tipoEmpresaFacade.find(id);
    }
    
     public void eliminarClienteEmpresa(String clienteEmpresa) {
        try {
            ClienteEmpresa aEliminar = clienteEmpresaFacade.find(clienteEmpresa);
            if (aEliminar != null) {
                clienteEmpresaFacade.remove(aEliminar);
            }                         
        } catch (Exception e) {
        }
               
    }
}
