/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.ClienteEmpresaFacade;
import ec.com.sinetcom.dao.ContratoFacade;
import ec.com.sinetcom.dao.ItemProductoFacade;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author diegoflores
 */
@Stateless
@LocalBean
public class CargaDeDatosServicio {
    
    @EJB
    private ClienteEmpresaFacade clienteEmpresaFacade;
    @EJB 
    private ContratoFacade contratoFacade;
    @EJB
    private ItemProductoFacade itemProductoFacade;
    
    
    public boolean cargarDatosClienteEmpresa(){
        return this.clienteEmpresaFacade.cargarDatosClienteEmpresa();
    }
    
    public boolean cargarDatosContrato(){
        return this.contratoFacade.cargarDatosContrato();
    }
    
    public boolean cargarDatosItemProducto(){
        return this.itemProductoFacade.cargarDatosItemProducto();
    }
    
}
