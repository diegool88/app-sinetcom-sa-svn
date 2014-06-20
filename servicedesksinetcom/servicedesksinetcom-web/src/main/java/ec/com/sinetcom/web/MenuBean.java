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
import org.primefaces.component.submenu.Submenu;
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
            //Primer Submenu Productos
            Submenu productos = new Submenu();
            productos.setLabel("Productos");
            MenuItem crearProducto = new MenuItem();
            crearProducto.setValue("Crear Producto");
            MenuItem modificarProducto = new MenuItem();
            modificarProducto.setValue("Modificar Producto");
            MenuItem gestionarModelosYRevisiones = new MenuItem();
            gestionarModelosYRevisiones.setValue("Gestionar Modelos y Revisiones");
            productos.getChildren().add(crearProducto);
            productos.getChildren().add(modificarProducto);
            productos.getChildren().add(gestionarModelosYRevisiones);
            menuPrincipal.addSubmenu(productos);
            
            //Segundo Submenu Perfiles de Usuarios
            Submenu perfilesDeUsuario = new Submenu();
            perfilesDeUsuario.setLabel("Perfiles de Usuario");
            MenuItem crearUsuario = new MenuItem();
            crearUsuario.setValue("Crear Usuario");
            MenuItem modificarUsuario = new MenuItem();
            modificarUsuario.setValue("Modificar Usuario");
            MenuItem eliminarUsuario = new MenuItem();
            eliminarUsuario.setValue("Eliminar Usuario");
            perfilesDeUsuario.getChildren().add(crearUsuario);
            perfilesDeUsuario.getChildren().add(modificarUsuario);
            perfilesDeUsuario.getChildren().add(eliminarUsuario);
            menuPrincipal.addSubmenu(perfilesDeUsuario);
            //Tercero Submenu Fabricantes
            Submenu fabricantes = new Submenu();
            fabricantes.setLabel("Fabricantes");
            menuPrincipal.addSubmenu(fabricantes);
            
            
        }else{
            //menuPrincipal = new DefaultMenuModel();
            MenuItem inventarios = new MenuItem();
            inventarios.setValue("Inventarios");
            menuPrincipal.addMenuItem(inventarios);
        }
    }
    
}
