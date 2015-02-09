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
            this.dataSource = (DataSource) initialContext.lookup("jdbc/appsinetcomprod");
            this.connection = this.dataSource.getConnection();
        } catch (NamingException ex) {
            Logger.getLogger(ReportesBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarReporteDeInventariosDisponiblesAgrupadoPorSitio(ActionEvent event) throws JRException, IOException {
        Map parametros = new HashMap();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes);
        parametros.put("realPath", realPath);
        this.jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes + "inventario.jasper"), parametros, this.connection);
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
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            xlsxExporter.exportReport();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarReporteDeCambiosRealizadosPorCliente(ActionEvent event) throws JRException, IOException {
        Map parametros = new HashMap();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes);
        parametros.put("realPath", realPath);
        this.jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes + "cambiosRealizadosPorCliente.jasper"), parametros, this.connection);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String tipo = (String) event.getComponent().getAttributes().get("tipo");
        String archivo = (String) event.getComponent().getAttributes().get("archivo");
        //Se valida la cabecera
        if (archivo.equals("pdf")) {
            if (tipo != null && !tipo.isEmpty() && tipo.equals("d")) {
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteCambiosRealizadosPorCLiente" + formatoFecha.format(Calendar.getInstance().getTime()) + ".pdf");
            }
        } else if (archivo.equals("word")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteCambiosRealizadosPorCLiente" + formatoFecha.format(Calendar.getInstance().getTime()) + ".docx");
        } else if (archivo.equals("excel")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteCambiosRealizadosPorCLiente" + formatoFecha.format(Calendar.getInstance().getTime()) + ".xlsx");
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
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            xlsxExporter.exportReport();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarReporteDePartesPorAbastecer(ActionEvent event) throws JRException, IOException {
        Map parametros = new HashMap();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes);
        parametros.put("realPath", realPath);
        this.jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes + "reportePorAbastecerConInfo.jasper"), parametros, this.connection);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String tipo = (String) event.getComponent().getAttributes().get("tipo");
        String archivo = (String) event.getComponent().getAttributes().get("archivo");
        //Se valida la cabecera
        if (archivo.equals("pdf")) {
            if (tipo != null && !tipo.isEmpty() && tipo.equals("d")) {
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReportePartesPorAbastecer" + formatoFecha.format(Calendar.getInstance().getTime()) + ".pdf");
            }
        } else if (archivo.equals("word")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReportePartesPorAbastecer" + formatoFecha.format(Calendar.getInstance().getTime()) + ".docx");
        } else if (archivo.equals("excel")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReportePartesPorAbastecer" + formatoFecha.format(Calendar.getInstance().getTime()) + ".xlsx");
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
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            xlsxExporter.exportReport();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarRegistroDeMovimientoDeInventario(int idRegistro) throws IOException, JRException{
        Map parametros = new HashMap();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes);
        parametros.put("realPath", realPath);
        parametros.put("registroMov_id", idRegistro);
        this.jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes + "registroDeMovimiento.jasper"), parametros, this.connection);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(this.jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarReporteDeAtencionesMesActual(ActionEvent event) throws JRException, IOException{
        Map parametros = new HashMap();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes);
        parametros.put("realPath", realPath);
        this.jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes + "atencionesMesActual.jasper"), parametros, this.connection);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String tipo = (String) event.getComponent().getAttributes().get("tipo");
        String archivo = (String) event.getComponent().getAttributes().get("archivo");
        //Se valida la cabecera
        if (archivo.equals("pdf")) {
            if (tipo != null && !tipo.isEmpty() && tipo.equals("d")) {
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteAtencionesMesActual" + formatoFecha.format(Calendar.getInstance().getTime()) + ".pdf");
            }
        } else if (archivo.equals("word")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteAtencionesMesActual" + formatoFecha.format(Calendar.getInstance().getTime()) + ".docx");
        } else if (archivo.equals("excel")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteAtencionesMesActual" + formatoFecha.format(Calendar.getInstance().getTime()) + ".xlsx");
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
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            xlsxExporter.exportReport();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarReporteDeAtencionesPorIngeniero(ActionEvent event) throws JRException, IOException{
        Map parametros = new HashMap();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes);
        parametros.put("realPath", realPath);
        this.jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes + "atencionesPorIngeniero.jasper"), parametros, this.connection);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String tipo = (String) event.getComponent().getAttributes().get("tipo");
        String archivo = (String) event.getComponent().getAttributes().get("archivo");
        //Se valida la cabecera
        if (archivo.equals("pdf")) {
            if (tipo != null && !tipo.isEmpty() && tipo.equals("d")) {
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteAtencionesPorIngeniero" + formatoFecha.format(Calendar.getInstance().getTime()) + ".pdf");
            }
        } else if (archivo.equals("word")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteAtencionesPorIngeniero" + formatoFecha.format(Calendar.getInstance().getTime()) + ".docx");
        } else if (archivo.equals("excel")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteAtencionesPorIngeniero" + formatoFecha.format(Calendar.getInstance().getTime()) + ".xlsx");
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
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            xlsxExporter.exportReport();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarReporteDeIncumplimientoDeSLA(ActionEvent event) throws JRException, IOException{
        Map parametros = new HashMap();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes);
        parametros.put("realPath", realPath);
        this.jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes + "incumplimientoDeSLA.jasper"), parametros, this.connection);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String tipo = (String) event.getComponent().getAttributes().get("tipo");
        String archivo = (String) event.getComponent().getAttributes().get("archivo");
        //Se valida la cabecera
        if (archivo.equals("pdf")) {
            if (tipo != null && !tipo.isEmpty() && tipo.equals("d")) {
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteIncumplimientoDeSLAs" + formatoFecha.format(Calendar.getInstance().getTime()) + ".pdf");
            }
        } else if (archivo.equals("word")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteIncumplimientoDeSLAs" + formatoFecha.format(Calendar.getInstance().getTime()) + ".docx");
        } else if (archivo.equals("excel")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteIncumplimientoDeSLAs" + formatoFecha.format(Calendar.getInstance().getTime()) + ".xlsx");
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
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            xlsxExporter.exportReport();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarReporteDeGarantiasPorVencerEnProx2Semanas(ActionEvent event) throws JRException, IOException{
        Map parametros = new HashMap();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes);
        parametros.put("realPath", realPath);
        this.jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes + "garantiasPorVencerEnProx2Semanas.jasper"), parametros, this.connection);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String tipo = (String) event.getComponent().getAttributes().get("tipo");
        String archivo = (String) event.getComponent().getAttributes().get("archivo");
        //Se valida la cabecera
        if (archivo.equals("pdf")) {
            if (tipo != null && !tipo.isEmpty() && tipo.equals("d")) {
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteGarantiasPorVencerEnProx2Semanas" + formatoFecha.format(Calendar.getInstance().getTime()) + ".pdf");
            }
        } else if (archivo.equals("word")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteGarantiasPorVencerEnProx2Semanas" + formatoFecha.format(Calendar.getInstance().getTime()) + ".docx");
        } else if (archivo.equals("excel")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteGarantiasPorVencerEnProx2Semanas" + formatoFecha.format(Calendar.getInstance().getTime()) + ".xlsx");
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
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            xlsxExporter.exportReport();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarReporteDePagosPorVencerEnProx2Semanas(ActionEvent event) throws JRException, IOException{
        Map parametros = new HashMap();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes);
        parametros.put("realPath", realPath);
        this.jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes + "pagosPorVencerEnProx2Semanas.jasper"), parametros, this.connection);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String tipo = (String) event.getComponent().getAttributes().get("tipo");
        String archivo = (String) event.getComponent().getAttributes().get("archivo");
        //Se valida la cabecera
        if (archivo.equals("pdf")) {
            if (tipo != null && !tipo.isEmpty() && tipo.equals("d")) {
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReportePagosPorVencerEnProx2Semanas" + formatoFecha.format(Calendar.getInstance().getTime()) + ".pdf");
            }
        } else if (archivo.equals("word")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReportePagosPorVencerEnProx2Semanas" + formatoFecha.format(Calendar.getInstance().getTime()) + ".docx");
        } else if (archivo.equals("excel")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReportePagosPorVencerEnProx2Semanas" + formatoFecha.format(Calendar.getInstance().getTime()) + ".xlsx");
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
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            xlsxExporter.exportReport();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarActaDeEntregaRecepcion(ActionEvent event) throws JRException, IOException{
        Map parametros = new HashMap();
        String numeroContrato = (String) event.getComponent().getAttributes().get("numeroContrato");
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes);
        parametros.put("realPath", realPath);
        parametros.put("numeroContrato", numeroContrato);
        this.jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes + "actaDeEntregaRecepcion.jasper"), parametros, this.connection);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String tipo = (String) event.getComponent().getAttributes().get("tipo");
        String archivo = (String) event.getComponent().getAttributes().get("archivo");
        
        //Se valida la cabecera
        if (archivo.equals("pdf")) {
            if (tipo != null && !tipo.isEmpty() && tipo.equals("d")) {
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=ActaDeEntregaRecepcion" + formatoFecha.format(Calendar.getInstance().getTime()) + ".pdf");
            }
        } else if (archivo.equals("word")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ActaDeEntregaRecepcion" + formatoFecha.format(Calendar.getInstance().getTime()) + ".docx");
        } else if (archivo.equals("excel")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ActaDeEntregaRecepcion" + formatoFecha.format(Calendar.getInstance().getTime()) + ".xlsx");
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
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            xlsxExporter.exportReport();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarActaDeEntregaRecepcion(String numeroContrato, String tipo, String archivo) throws JRException, IOException{
        Map parametros = new HashMap();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes);
        parametros.put("realPath", realPath);
        parametros.put("numeroContrato", numeroContrato);
        this.jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReportes + "actaDeEntregaRecepcion.jasper"), parametros, this.connection);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        
        //Se valida la cabecera
        if (archivo.equals("pdf")) {
            if (tipo != null && !tipo.isEmpty() && tipo.equals("d")) {
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=ActaDeEntregaRecepcion" + formatoFecha.format(Calendar.getInstance().getTime()) + ".pdf");
            }
        } else if (archivo.equals("word")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ActaDeEntregaRecepcion" + formatoFecha.format(Calendar.getInstance().getTime()) + ".docx");
        } else if (archivo.equals("excel")) {
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ActaDeEntregaRecepcion" + formatoFecha.format(Calendar.getInstance().getTime()) + ".xlsx");
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
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            xlsxExporter.exportReport();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

}
