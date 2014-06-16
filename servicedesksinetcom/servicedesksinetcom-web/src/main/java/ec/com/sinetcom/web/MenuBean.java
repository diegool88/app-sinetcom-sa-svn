/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.BeforeCompletion;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

/**
 *
 * @author diegoflores
 */
@ManagedBean
@SessionScoped
public class MenuBean implements Serializable{
    
    private MenuModel menuPrincipal;
    private String rol;
    
    @ManagedProperty("#{login}")
    private AdministracionUsuarioBean login;
    
//    protected AdministracionUsuarioBean getLogin(){
//        return (AdministracionUsuarioBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login");
//    }
    
//    public MenuBean(){
//        
//    }

    public MenuModel getMenuPrincipal() {
        return menuPrincipal;
    }

    public void setMenuPrincipal(MenuModel menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
    }

//    public AdministracionUsuarioBean getLogin() {
//        return login;
//    }

    public void setLogin(AdministracionUsuarioBean login) {
        this.login = login;
    }

    
    @PostConstruct
    public void doInit(){
        menuPrincipal = new DefaultMenuModel();
//        rol = getLogin().getUsuarioActual().getNombreUsuario();
        rol = login.getUsuarioActual().getNombreUsuario();
        if(rol.equals("admin")){
            //menuPrincipal = new DefaultMenuModel();
            MenuItem productos = new MenuItem();
            productos.setValue("Productos");
            menuPrincipal.addMenuItem(productos);
        }else{
            //menuPrincipal = new DefaultMenuModel();
            MenuItem inventarios = new MenuItem();
            inventarios.setValue("Inventarios");
            menuPrincipal.addMenuItem(inventarios);
        }
    }
    
}
