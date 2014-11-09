/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.Usuario;
import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.application.PrimeResourceHandler;

/**
 *
 * @author diegoflores
 */

@WebFilter("/*")
public class FiltroDeAutorizacion implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        AdministracionUsuarioBean auth = (AdministracionUsuarioBean) req.getSession().getAttribute("login");
        //URL de Login
        String loginURL = req.getContextPath() + "/login.xhtml";
        //Error de Permisos Login
        String errorPermisos = req.getContextPath() + "/errorDePermisos.xhtml";
        //Validaciones de autorizacion y contenido
        boolean estaAutenticado = auth != null && auth.estaUsuarioAutenticado();
        boolean esPaginaDeLogin = req.getRequestURI().equals(loginURL);
        boolean esPaginaDeError = req.getRequestURI().equals(errorPermisos);
        //boolean aceptarRecursosDeSolicitud = req.getRequestURI().startsWith(req.getContextPath() + "/faces" + ResourceHandler.RESOURCE_IDENTIFIER);
        boolean aceptarRecursosDeSolicitud = req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER) || req.getRequestURI().startsWith(req.getContextPath() + PrimeResourceHandler.RESOURCE_IDENTIFIER);
        //throw new UnsupportedOperationException("Not supported yet.");
        if ( estaAutenticado || esPaginaDeLogin || aceptarRecursosDeSolicitud ) {
            //Prevenir que paginas restringidas sean puestas en cache
            if(!aceptarRecursosDeSolicitud){
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                res.setDateHeader("Expires", 0); // Proxies.
            }
            //Forzar a usar el Charset UTF-8
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            
            if(auth != null && auth.getUsuarioActual() != null && !esPaginaDeError && !verificarPermiso(auth.getUsuarioActual(), req) && !aceptarRecursosDeSolicitud){
                res.sendRedirect(req.getContextPath() + "/errorDePermisos.xhtml");
            }
            // User is logged in, so just continue request.
            chain.doFilter(request, response);
        } else {
            // User is not logged in, so redirect to index.
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
        }
    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private boolean verificarPermiso(Usuario usuario, HttpServletRequest req){
        if(usuario.getGrupoid().getNombre().toLowerCase().trim().equals("cliente")){
            return !(!req.getRequestURI().toLowerCase().contains("mistickets") && !req.getRequestURI().toLowerCase().contains("crearticket") 
                    && !req.getRequestURI().toLowerCase().contains("login")
                    && !req.getRequestURI().toLowerCase().contains("welcome"));
        }
        return true;
    }
    
}
