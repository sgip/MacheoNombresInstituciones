/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marcos
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;


public class ReportFactory {
    
    private String mensaje;    

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }   

    /** Creates a new instance of reporte */
    public ReportFactory() {
    }

    private Connection conectarBD() throws ClassNotFoundException, SQLException {
        Connection conexion = null;
      
        try {
	      Class.forName("org.postgresql.Driver");
	    }
	    catch (ClassNotFoundException e) {
	    	mensaje = "PostgreSQL JDBC Driver not found.";
	      System.exit(1);
	    }

        //Connection conexion = null;
        try {
            try {
                //Dir Ip / Bdatos/usuario/clave                
                conexion = DriverManager.getConnection("jdbc:postgresql://192.168.23.20/MECDB100", "postgres", "mecdgii");
                //conexion al server 142
                //conexion = DriverManager.getConnection("jdbc:postgresql://192.168.16.110/MECDB100", "postgres", "mecdgii");
                if (conexion == null) {
                    System.out.println("NO CONECTA A LA BD");
                }

            } catch (SQLException ex) {
                Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }    
    
    
    private String reportName;
    private Map<?,?> params;
    private String tipoReporte;
    private List<?> list;
 
    public ReportFactory(String ReportName, Map<?,?> params, String tipoReporte, List<?>list) {
        this.reportName = ReportName;
        this.params = params;
        this.tipoReporte = tipoReporte;
        this.list = list;
    }
    
    public ReportFactory(String ReportName, Map<?,?> params, String tipoReporte) {
        this.reportName = ReportName;
        this.params = params;
        this.tipoReporte = tipoReporte;        
    }
	 
    public ReportFactory(String ReportName, String tipoReporte) {
        this.reportName = ReportName;
        this.tipoReporte = tipoReporte;
    }
	 
    public InputStream getReportStream() throws ClassNotFoundException, SQLException{
	 
    InputStream input = null;
	 
	 try {
             Connection datasource;
             datasource = conectarBD();
             datasource.setAutoCommit(true);
             ByteArrayOutputStream output = new ByteArrayOutputStream();
             JasperReport jasperReport = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("/reports/"+reportName));
             //jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
             //JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(list);

             JasperPrint print = JasperFillManager.fillReport(jasperReport, params, datasource);
             JRExporter exporter = null;

             if("PDF".equals(tipoReporte))
             exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();

             if("HTML".equals(tipoReporte)){
             exporter = new net.sf.jasperreports.engine.export.JRHtmlExporter();
             exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
             }

             if("XLS".equals(tipoReporte))
             exporter = new net.sf.jasperreports.engine.export.JRXlsExporter();

             if("CVS".equals(tipoReporte))
             exporter = new net.sf.jasperreports.engine.export.JRCsvExporter();

             if("TXT".equals(tipoReporte))
             exporter = new net.sf.jasperreports.engine.export.JRTextExporter();

             if("RTF".equals(tipoReporte))
             exporter = new net.sf.jasperreports.engine.export.JRRtfExporter();

             exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
             exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
             exporter.exportReport();
             input = new ByteArrayInputStream(output.toByteArray());
             } catch (JRException ex) {
             Logger.getLogger(ReportFactory.class.getName()).log(Level.SEVERE, null, ex);
             }

             return input;
	 
	 }
    
    
    
    
    
    
    
    
    
}
