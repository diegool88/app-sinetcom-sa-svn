/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.sinetcom.web;

import ec.com.sinetcom.orm.ItemProducto;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author diegoflores
 */
@ManagedBean(name = "reportesBean")
@SessionScoped
public class ReportesBean implements Serializable {

    private DataSource dataSource;
    private Connection connection;
    private static final String pathReportes = "resources/reportes/";
    private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

    private List<ItemProducto> itemProductos;
    private JasperPrint jasperPrint;

    @PostConstruct
    public void doInit() {
        InitialContext initialContext;
        try {
            initialContext = new InitialContext();
            this.dataSource = (DataSource) initialContext.lookup("jdbc/appsinetcomlocal");
            this.connection = this.dataSource.getConnection();
        } catch (NamingException ex) {
            Logger.getLogger(ReportesBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarReporteDeInventariosDisponiblesAgrupadoPorSitio(ActionEvent event) throws JRException, IOException {
        this.jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes + "inventario.jasper"), new HashMap(), this.connection);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String tipo = (String) event.getComponent().getAttributes().get("tipo");
        String archivo = (String) event.getComponent().getAttributes().get("archivo");
        //Se valida la cabecera
        if (archivo.equals("pdf")) {
            if (tipo != null && !tipo.isEmpty() && tipo.equals("d")) {
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteInventariosDisponibles" + formatoFecha.format(Calendar.getInstance().getTime()) + ".pdf");
            }
        } else if (archivo.equals("word")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteInventariosDisponibles" + formatoFecha.format(Calendar.getInstance().getTime()) + ".docx");
        } else if (archivo.equals("excel")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteInventariosDisponibles" + formatoFecha.format(Calendar.getInstance().getTime()) + ".xlsx");
        }
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        //Se define el Jasper Export
        if (archivo.equals("pdf")) {
            JasperExportManager.exportReportToPdfStream(this.jasperPrint, servletOutputStream);
        } else if (archivo.equals("word")) {
            JRDocxExporter docxExporter = new JRDocxExporter();
            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            docxExporter.exportReport();
        } else if (archivo.equals("excel")) {
            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            xlsxExporter.exportReport();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarRegistroDeMovimientoDeInventario(int idRegistro) throws IOException, JRException{
        Map parametros = new HashMap();
        parametros.put("registroMov_id", idRegistro);
        this.jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes + "registroDeMovimiento.jasper"), parametros, this.connection);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(this.jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

}
