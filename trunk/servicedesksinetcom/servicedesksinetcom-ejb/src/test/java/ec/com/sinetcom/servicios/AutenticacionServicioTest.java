/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.servicios;

import ec.com.sinetcom.orm.Usuario;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author diegoflores
 */
public class AutenticacionServicioTest {

//    private static EJBContainer container;
//    private static Context ctx;
//
//    public AutenticacionServicioTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//        Map<String, Object> properties = new HashMap<String, Object>();
//        properties.put(EJBContainer.MODULES, new File("target/classes"));
//        properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "/Applications/NetBeans/glassfish-3.1.2.2/glassfish");
//        properties.put("org.glassfish.ejb.embedded.glassfish.configuration.file", "/Applications/NetBeans/glassfish-3.1.2.2/glassfish/domains/domain1/config/domain.xml");
//        container = EJBContainer.createEJBContainer(properties);
//        ctx = container.getContext();
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//        container.close();
//    }
//
//    @Before
//    public void setUp() {
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//
//    /**
//     * Test of validarUsuario method, of class AutenticacionServicio.
//     */
//    @Test
//    public void testValidarUsuarioNoExiste() throws Exception {
//        System.out.println("validarUsuario");
//        String nombreUsuario = "diego.flores@sinetcom.com.ec";
//        String password = "Sinetcom2014";
//        AutenticacionServicio instance = (AutenticacionServicio) ctx.lookup("java:global/classes/AutenticacionServicio");
//        Usuario expResult = null;
//        Usuario result = instance.validarUsuario(nombreUsuario, password);
//        assertEquals(expResult, result);
//    }
//    
//    /**
//     * Test of validarUsuario method, of class AutenticacionServicio.
//     */
//    @Test
//    public void testValidarUsuarioSiExiste() throws Exception {
//        System.out.println("validarUsuario");
//        String nombreUsuario = "diego.flores@sinetcom.com.ec";
//        String password = "Sinetcom2013";
//        AutenticacionServicio instance = (AutenticacionServicio) ctx.lookup("java:global/classes/AutenticacionServicio");
//        UsuarioServicio instance2 = (UsuarioServicio) ctx.lookup("java:global/classes/UsuarioServicio");
//        Usuario expResult = instance2.obtenerUsuarioPorId(1);
//        Usuario result = instance.validarUsuario(nombreUsuario, password);
//        assertEquals(expResult, result);
//    }

}
