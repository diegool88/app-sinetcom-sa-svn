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
import org.primefaces.component.column.Column;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

/**
 *
 * @author diegoflores
 */
@ManagedBean
@ViewScoped
public class MenuBean implements Serializable{
    
    private DefaultMenuModel menuPrincipal;
    private String rol;
    
    @ManagedProperty("#{login}")
    private AdministracionUsuarioBean login;
    
//    protected AdministracionUsuarioBean getLogin(){
//        return (AdministracionUsuarioBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login");
//    }
    
//    public MenuBean(){
//        
//    }

    public DefaultMenuModel getMenuPrincipal() {
        return menuPrincipal;
    }

    public void setMenuPrincipal(DefaultMenuModel menuPrincipal) {
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
            
            //Primer Submenu
            Submenu submenuProductos = new Submenu();
            submenuProductos.setLabel("Productos");
            
            //Label
            Submenu productos = new Submenu();
            productos.setLabel("Productos");
            
            MenuItem crearProducto = new MenuItem();
            crearProducto.setValue("Crear Producto");
            crearProducto.setUrl("#");
            
            MenuItem modificarProducto = new MenuItem();
            modificarProducto.setValue("Modificar Producto");
            modificarProducto.setUrl("#");
            
            MenuItem gestionarModelosYRevisiones = new MenuItem();
            gestionarModelosYRevisiones.setValue("Gestionar Modelos y Revisiones");
            gestionarModelosYRevisiones.setUrl("#");
            
            productos.getChildren().add(crearProducto);
            productos.getChildren().add(modificarProducto);
            productos.getChildren().add(gestionarModelosYRevisiones);
            
            Column column = new Column();
            column.getChildren().add(productos);
            
            submenuProductos.getChildren().add(column);
            menuPrincipal.addSubmenu(productos);
            
            //Segundo Submenu
            Submenu submenuPerfilUsuario = new Submenu();
            submenuPerfilUsuario.setLabel("Perfiles de Usuario");
            
            //Label
            Submenu perfilesDeUsuario = new Submenu();
            perfilesDeUsuario.setLabel("Perfiles de Usuario");
            
            MenuItem crearUsuario = new MenuItem();
            crearUsuario.setValue("Crear Usuario");
            crearUsuario.setUrl("#");
            
            MenuItem modificarUsuario = new MenuItem();
            modificarUsuario.setValue("Modificar Usuario");
            modificarUsuario.setUrl("#");
            
            MenuItem eliminarUsuario = new MenuItem();
            eliminarUsuario.setValue("Eliminar Usuario");
            eliminarUsuario.setUrl("#");
            
            perfilesDeUsuario.getChildren().add(crearUsuario);
            perfilesDeUsuario.getChildren().add(modificarUsuario);
            perfilesDeUsuario.getChildren().add(eliminarUsuario);
            
            Column column1 = new Column();
            column1.getChildren().add(perfilesDeUsuario);
            
            perfilesDeUsuario.getChildren().add(column1);
            
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
