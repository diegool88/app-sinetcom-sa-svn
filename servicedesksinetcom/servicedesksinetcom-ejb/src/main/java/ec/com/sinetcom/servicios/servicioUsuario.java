/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.configuracion.ConfiguracionUsuario;
import ec.com.sinetcom.entidades.Usuario;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;

/**
 *
 * @author diegoflores
 */
@Stateful
@StatefulTimeout(unit = TimeUnit.MINUTES, value = 10)
public class servicioUsuario implements servicioUsuarioLocal {

    private Usuario usuario;
    
    @Override
    public void configuracionActual(ConfiguracionUsuario configuracionUsuario) {

        if (configuracionUsuario != null) {
        }
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostConstruct
    public void inicializarServicio() {
        usuario = new Usuario();
    }
    
    @Override
    public Usuario validarUsuario(String nombreUsuario, String password){
        if(nombreUsuario.equals("admin") && password.equals("admin")){
            this.usuario.setNombreUsuario("admin");
            this.usuario.setPassword("admin");
            return this.usuario;
        }else if(nombreUsuario.equals("inventory") && password.equals("inventory")){
            this.usuario.setNombreUsuario("inventory");
            this.usuario.setPassword("inventory");
            return this.usuario;
        }else{
            return null;
        }
    }
    
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
