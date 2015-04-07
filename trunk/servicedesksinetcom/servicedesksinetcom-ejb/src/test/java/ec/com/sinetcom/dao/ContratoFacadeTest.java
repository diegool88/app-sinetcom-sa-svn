/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.dao;

import ec.com.sinetcom.orm.Contrato;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
public class ContratoFacadeTest {

//    private static EJBContainer container;
//    private static Context ctx;
//
//    public ContratoFacadeTest() {
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
//    /**
//     * Test of create method, of class ContratoFacade.
//     */
//    @Test
//    public void testCreate() throws Exception {
//        try {
//            System.out.println("create");
//            TipoContratoFacade tipoContratoFacade = (TipoContratoFacade) ctx.lookup("java:global/classes/TipoContratoFacade");
//            ClienteEmpresaFacade clienteEmpresaFacade = (ClienteEmpresaFacade) ctx.lookup("java:global/classes/ClienteEmpresaFacade");
//            SlaFacade slaFacade = (SlaFacade) ctx.lookup("java:global/classes/SlaFacade");
//            UsuarioFacade usuarioFacade = (UsuarioFacade) ctx.lookup("java:global/classes/UsuarioFacade");
//            ContactoFacade contactoFacade = (ContactoFacade) ctx.lookup("java:global/classes/ContactoFacade");
//            Contrato entity = new Contrato();
//            entity.setNumero("AQWSDE123");
//            entity.setTipoContratoid(tipoContratoFacade.find(1));
//            entity.setClienteEmpresaruc(clienteEmpresaFacade.find("1768152560001"));
//            entity.setSlaid(slaFacade.find(1));
//            entity.setAccountManagerAsignado(usuarioFacade.find(3));
//            entity.setAdministradorDeContrato(contactoFacade.find(1));
//            entity.setFechaDeSuscripcion(new Date());
//            entity.setObjeto("Prueba");
//            entity.setPrecioTotal(BigDecimal.valueOf(100000));
//            entity.setTiempoDeValidez(2);
//            entity.setContratoDigital(new byte[2]);
//            entity.setServicioSoporteMantenimiento(2);
//            entity.setIncluyeRepuestos(true);
//            entity.setHorasDeSoporteAnual(20);
//            entity.setHorasDeSoporteUtilizadas(0);
//            entity.setInicioPorAnticipo(true);
//            ContratoFacade instance = (ContratoFacade) ctx.lookup("java:global/classes/ContratoFacade");
//            instance.create(entity);
//            System.out.println("Insert Done OK!");
//        } catch (Exception e) {
//            System.out.println("Insert Error! " + e.getMessage());
//        }
//
//    }
//
//    /**
//     * Test of find method, of class ContratoFacade.
//     */
//    @Test
//    public void testFind() throws Exception {
//        System.out.println("find");
//        Object id = "059-2013-RE-CNT";
//        ContratoFacade instance = (ContratoFacade) ctx.lookup("java:global/classes/ContratoFacade");
//        Contrato expResult = null;
//        Contrato result = instance.find(id);
//        //assertEquals(expResult, result);
//        assertNotSame(expResult, result);
//    }

}
