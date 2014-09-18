/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.dao.ComponenteElectronicoAtomicoFacade;
import ec.com.sinetcom.dao.ParametrosDeProductoFacade;
import ec.com.sinetcom.dao.UnidadMedidaFacade;
import ec.com.sinetcom.orm.ComponenteElectronicoAtomico;
import ec.com.sinetcom.orm.ParametrosDeProducto;
import ec.com.sinetcom.orm.UnidadMedida;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author diegoflores
 */
@Stateless
@LocalBean
public class ComponenteAtomicoServicio {

    @EJB
    private ComponenteElectronicoAtomicoFacade componenteElectronicoAtomicoFacade;
    @EJB
    private ParametrosDeProductoFacade parametrosDeProductoFacade;
    @EJB
    private UnidadMedidaFacade unidadMedidaFacade;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    /**
     * Servicio que permite crear un nuevo componente electrónico
     * @param atomico 
     */
    public void crearComponenteElectronico(ComponenteElectronicoAtomico atomico){
        this.componenteElectronicoAtomicoFacade.create(atomico);
    }
    
    /**
     * Servicio que obtiene todos los parametros declarados
     * @return 
     */
    public List<ParametrosDeProducto> obtenerTodosLosParametrosDeProducto(){
        return this.parametrosDeProductoFacade.findAll();
    }
    
    /**
     * Servicio que obtiene todas la unidades de medida
     * @return 
     */
    public List<UnidadMedida> obtenerTodasLasUnidadesDeMedida(){
        return this.unidadMedidaFacade.findAll();
    }
    
    /**
     * Obtiene un parametro producto
     * @param id
     * @return 
     */
    public ParametrosDeProducto obtenerParametroProducto(int id){
        return this.parametrosDeProductoFacade.find(id);
    }
    
    /**
     * Obtiene una unidad de medida
     * @param id
     * @return 
     */
    public UnidadMedida obtenerUnidadMedida(int id){
        return this.unidadMedidaFacade.find(id);
    }
    
    /**
     * Crea un parámetro producto
     * @param parametrosDeProducto 
     */
    public void crearParametroProducto(ParametrosDeProducto parametrosDeProducto){
        this.parametrosDeProductoFacade.create(parametrosDeProducto);
    }
    
    /**
     * Obtiene todos los componentes electronicos atómicos
     * @return 
     */
    public List<ComponenteElectronicoAtomico> obtenerTodosLosComponentesElectronicos(){
        return this.componenteElectronicoAtomicoFacade.findAll();
    }
    
    /**
     * Elimina un componente electrónico
     * @param electronicoAtomico
     * @return 
     */
    public boolean eliminarComponenteElectronico(int electronicoAtomico){
        try{
            ComponenteElectronicoAtomico componenteElectronicoAtomico = this.componenteElectronicoAtomicoFacade.find(electronicoAtomico);
            this.componenteElectronicoAtomicoFacade.remove(componenteElectronicoAtomico);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
}
