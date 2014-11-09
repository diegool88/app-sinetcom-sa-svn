/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.configuracion.UtilidadDeEmail;
import ec.com.sinetcom.dao.GarantiaEconomicaFacade;
import ec.com.sinetcom.dao.PagoFacade;
import ec.com.sinetcom.dao.SlaFacade;
import ec.com.sinetcom.dao.TipoDisponibilidadFacade;
import ec.com.sinetcom.orm.GarantiaEconomica;
import ec.com.sinetcom.orm.Pago;
import ec.com.sinetcom.orm.Sla;
import ec.com.sinetcom.orm.TipoDisponibilidad;
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
public class ContratoServicio {

    @EJB
    private SlaFacade slaFacade;
    @EJB
    private TipoDisponibilidadFacade tipoDisponibilidadFacade;
    @EJB
    private GarantiaEconomicaFacade garantiaEconomicaFacade;
    @EJB
    private PagoFacade pagoFacade;
    
    
    public void verificarPagosPorVencerEnProx2Semanas(){
        List<Pago> pagos = this.pagoFacade.obtenerTodosLosPagosPorVencerEnProx2Semanas();
        UtilidadDeEmail utilidadDeEmail = new UtilidadDeEmail();
        if(pagos != null && !pagos.isEmpty()){
            for(Pago pago : pagos){
                utilidadDeEmail.enviarMensajeConAdjunto("noreply@sinetcom.com.ec", "diego.flores@sinetcom.com.ec" , "Notificación Pago por Vencer", crearCuerpoDeCorreoDeNotificacionDePagoPorVencer(pago), null, null, null);
            }
        }
    }
    
    public void verificarGarantiasEconomicasPorVencerEnProx2Semanas(){
        List<GarantiaEconomica> garantias = this.garantiaEconomicaFacade.obtenerTodasLasGarantiasEconomicasPorVencerEnProx2Semanas();
        UtilidadDeEmail utilidadDeEmail = new UtilidadDeEmail();
        if(garantias != null && !garantias.isEmpty()){
            for(GarantiaEconomica garantia : garantias){
                utilidadDeEmail.enviarMensajeConAdjunto("noreply@sinetcom.com.ec", "diego.flores@sinetcom.com.ec" , "Notificación Garantía E. por Vencer", crearCuerpoDeCorreoDeNotificacionDeGarantiaEconomicaPorVencer(garantia), null, null, null);
            }
        }
    }
    
    
    private String crearCuerpoDeCorreoDeNotificacionDePagoPorVencer(Pago pago){
        StringBuilder cuerpo = new StringBuilder();
        //Se arma el cuerpo del correo electrónico
        cuerpo.append("Estimad@").append("\n\n\n");
        cuerpo.append("Le informamos que un pago se encuentra por vencer, a continuación los detalles: ").append("\n\n");
        cuerpo.append("Contrato: ").append(pago.getContratonumero().getNumero()).append("\n");
        cuerpo.append("Monto: ").append(pago.getMonto()).append("\n");
        cuerpo.append("Plazo Máximo: ").append(pago.getPlazo()).append("\n");
        cuerpo.append("Pago Número: ").append(pago.getOrden()).append("\n");
        cuerpo.append("¿Es anticipo?: ").append(pago.getAnticipo() != null && pago.getAnticipo() ? "Si" : "No").append("\n\n\n");
        cuerpo.append("Saludos Cordiales.").append("\n");
        
        return cuerpo.toString();
    }
    
    private String crearCuerpoDeCorreoDeNotificacionDeGarantiaEconomicaPorVencer(GarantiaEconomica garantiaEconomica){
        StringBuilder cuerpo = new StringBuilder();
        //Se arma el cuerpo del correo electrónico
        cuerpo.append("Estimad@").append("\n\n\n");
        cuerpo.append("Le informamos que un pago se encuentra por vencer, a continuación los detalles: ").append("\n\n");
        cuerpo.append("Contrato: ").append(garantiaEconomica.getContratonumero().getNumero()).append("\n");
        cuerpo.append("Monto: ").append(garantiaEconomica.getValor()).append("\n");
        cuerpo.append("Fecha de finalización: ").append(garantiaEconomica.getFechaFin()).append("\n");
        cuerpo.append("Porcentaje: ").append(garantiaEconomica.getPorcentaje()).append("%\n");
        cuerpo.append("¿Es renovable?: ").append(garantiaEconomica.getRenovable() ? "Si" : "No").append("\n");
        cuerpo.append("Tipo de Garantía E.: ").append(garantiaEconomica.getTipoGarantiaid().getTipo()).append("\n\n\n");
        cuerpo.append("Saludos Cordiales.").append("\n");
        
        return cuerpo.toString();
    }
    
    /**
     * Crea un nuevo sla
     * @param sla
     * @return 
     */
    public boolean crearSla(Sla sla){
        try{
            this.slaFacade.create(sla);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Elimina un sla
     * @param sla
     * @return 
     */
    public boolean eliminarSla(Sla sla){
        try{
            this.slaFacade.remove(sla);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Modifica un SLA
     * @param sla
     * @return 
     */
    public boolean modificarSla(Sla sla){
        try{
            this.slaFacade.edit(sla);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Crea un tipo de disponibilidad
     * @param tipoDisponibilidad
     * @return 
     */
    public boolean crearTipoDeDisponibilidad(TipoDisponibilidad tipoDisponibilidad){
        try{
            this.tipoDisponibilidadFacade.create(tipoDisponibilidad);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Eliminar un tipo de disponibilidad
     * @param tipoDisponibilidad
     * @return 
     */
    public boolean eliminarTipoDeDisponibilidad(TipoDisponibilidad tipoDisponibilidad){
        try{
            this.tipoDisponibilidadFacade.remove(tipoDisponibilidad);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Modifica un tipo de disponibilidad
     * @param tipoDisponibilidad
     * @return 
     */
    public boolean modificarTipoDeDisponibilidad(TipoDisponibilidad tipoDisponibilidad){
        try{
            this.tipoDisponibilidadFacade.edit(tipoDisponibilidad);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
