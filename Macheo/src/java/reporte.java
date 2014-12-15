/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
//import reports.ReportGenerator;

/**
 *
 * @author Marcos
 */
@ManagedBean
@ViewScoped
public class reporte {
    
    private String mensaje;    

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }   

    /** Creates a new instance of reporte */
    public reporte() {
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
    
    public String viewReportTrabajadorCertificadoTrabajoPDF(String codigoTrabajador) throws SQLException, IOException, NamingException, ClassNotFoundException {
        //Fill Map with params values
        HashMap hm = new HashMap();
        hm.put("codigoTrabajador", codigoTrabajador);
        
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SigmecWebInformePU", System.getProperties());
//        EntityManager em = emf.createEntityManager();
//
//        java.sql.Connection conexion = em.unwrap(java.sql.Connection.class);
        
        
        //Connect with local datasource
        Context ctx = new InitialContext();

        Connection conexion;
        try {
            conexion = conectarBD();
            conexion.setAutoCommit(true);

            String reportId = "trabajador_certificado_trabajo";

            File file = new File("c:\\syswk1\\ireport\\compilation\\");
            JasperPrint jasperPrint;
            try {
                /////////////////////////////////////////////////////
//                jasperPrint = JasperFillManager.fillReport(
//                        new FileInputStream(new File(file, reportId + ".jasper")),
//                        hm, conexion);
//                byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
//
//
//                FacesContext context = FacesContext.getCurrentInstance();
//                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
//                response.addHeader("Content-disposition",
//                        "filename=reporte.pdf");
//                response.setContentLength(bytes.length);
//                response.getOutputStream().write(bytes);
//                response.setContentType("application/pdf");
//                context.responseComplete();
                //////////////////////////////////////////////////////
                
                jasperPrint = JasperFillManager.fillReport(new FileInputStream(new File(file, reportId + ".jasper")),
                        hm, conexion);
                byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);


                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.addHeader("Content-disposition",
                        "filename=reporte.pdf");
                response.setContentLength(bytes.length);
                response.getOutputStream().write(bytes);
                response.setContentType("application/pdf");
                context.responseComplete();

//
                ////////////////////////////////////////////////////
//                String nombreReporte = "trabajador_certificado_reporte";
//                File f = new File("../reports/"+nombreReporte+".pdf"); //creo un File con el path y nombre correspondiente  del archivo
//                FileOutputStream s = new FileOutputStream(f);  // creo una nueva instancia de FileOutputStream
//                s.write(reporteBytes);  //creo el archivo
//                s.close();//cierro la instacia
//
//                FacesContext ctx = FacesContext.getCurrentInstance(); //Obtengo
//                instacia del contexto
//                //obtengo instancia de response
//                HttpServletResponse response = (HttpServletResponse)
//                ctx.getExternalContext().getResponse();
//                ServletOutputStream output=response.getOutputStream();
//                response.setContentType("application/pdf");
//                response.setContentLength(reporteBytes.length);
//                response.setHeader("Content-disposition","attachment; inline; filename=
//                \""+nombreReporte+"\"");
//                output.write(reporteBytes,0,reporteBytes.length);
//                output.flush();
//                output.close(); 
            } catch (JRException ex) {
                Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoResultException ex) {
            Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /***********************************************************************
         * Pour afficher une bo�te de dialogue pour enregistrer le fichier sous
         * le nom rapport.pdf
         **********************************************************************/
        return null;
    }
    
    
    private JasperPrint impressao;
    private HashMap<String, Serializable> parametroMap;
    private FacesContext context;
    private ServletContext servletContext;
    private String mensagem, caminhoRelatorio;
    private boolean exibeDialog;
    
    public boolean isExibeDialog() {
        return exibeDialog;
    }

    public void setExibeDialog(boolean exibeDialog) {
        this.exibeDialog = exibeDialog;
    }

	
    public void geraRelatorio(String codigoTrabajador) {
        context = FacesContext.getCurrentInstance();
        servletContext = (ServletContext) context.getExternalContext().getContext();

        caminhoRelatorio = servletContext.getRealPath("/report/trabajador_certificado_trabajo.jasper");
        mensagem += "Caminho do relatório: " + caminhoRelatorio + "\n ";

     //   mensagem += "Sistema Operacional: " + Funcoes.retornaSistemaOperacional().toLowerCase() + "\n ";

        parametroMap = new HashMap<String, Serializable>();
        parametroMap.put("codigoTrabajador", codigoTrabajador); 	               
        enviarPdf();
    }
	 
    public void enviarPdf() {
        // Carrega o xml de definição do relatório
        try {
            Connection conexion;
            conexion = conectarBD();
            conexion.setAutoCommit(true);

            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            // Configura o response para suportar o relatório
            response.setContentType("application/pdf");
            response.addHeader("Content-disposition", "inline; filename=\"certificado.pdf\"");
            //response.addHeader("Content-disposition", "attachment; filename=\"certificado.pdf\"");

            // Preenche o relatório com os parametros e o data source
            impressao = JasperFillManager.fillReport(caminhoRelatorio, parametroMap, conexion);
            // Exporta o relatório
            JasperExportManager.exportReportToPdfStream(impressao, response.getOutputStream());
            // Salva o estado da aplicação no contexto do JSF
            context.getApplication().getStateManager().saveView(context); 
            // Fecha o stream do response
            context.responseComplete();
            
        } catch (Exception e) {
            setExibeDialog(true);
            mensagem += e;
        }               

    }
    
    
    public StreamedContent getSampleReportPDF(String codigoTrabajador) throws ClassNotFoundException, SQLException{
	 
	InputStream relatorio = null;
	 
	try {
            
            Connection conexion;
            conexion = conectarBD();
            conexion.setAutoCommit(true);
	 
	     String pdfFile = "C:\\temporal\\sampleReport.pdf";
	 
	     ByteArrayOutputStream Teste = new ByteArrayOutputStream();
	 
	     JasperReport jasperReport = (JasperReport)JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("/report/trabajador_certificado_trabajo.jasper"));
	     //jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
	 
	     //HashMap<String, Integer> params = new HashMap<String, Integer>();
             Map params = new HashMap();
             params.put("codigoTrabajador", codigoTrabajador); 
	     JasperPrint print = JasperFillManager.fillReport(jasperReport, params, conexion);
             JasperPrintManager.printReport(print,true);
	 
	     JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
	     //JRExporter exporter = new net.sf.jasperreports.engine.export.JRHtmlExporter();
	     //JRExporter exporter = new net.sf.jasperreports.engine.export.JRXlsExporter();
	     //JRExporter exporter = new net.sf.jasperreports.engine.export.JRXmlExporter();
	     //JRExporter exporter = new net.sf.jasperreports.engine.export.JRCsvExporter();
	 
	     exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pdfFile);
	     exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, Teste);
	     exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
	     exporter.exportReport();
	 
	     relatorio = new ByteArrayInputStream(Teste.toByteArray());
	 
	 } catch (JRException ex) {
	     Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
	 }
	 
	 return new DefaultStreamedContent(relatorio, "", "trabajador_certificado_trabajo.pdf");
	 
	}
    
    public StreamedContent getArchivoPDF(String codigoTrabajador) throws JRException, ClassNotFoundException, SQLException {
	 
	Connection conexion;
        conexion = conectarBD();
        conexion.setAutoCommit(true);

        InputStream inputStream = null;

        Map parameters = new HashMap();

        parameters.put("codigo_trabajador", codigoTrabajador);

        try {

            ByteArrayOutputStream Teste = new ByteArrayOutputStream();

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("/report/trabajador_certificado_trabajo.jasper"));

            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conexion);

            JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, Teste);

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

            exporter.exportReport();

            inputStream = new ByteArrayInputStream(Teste.toByteArray());

        } catch (JRException ex) {
        }

        return new DefaultStreamedContent(inputStream, "application/pdf", "nombre_archivo");

    }
  

    
    // <editor-fold defaultstate="collapsed" desc="Método para imprimir certificado de trabajo">
    public StreamedContent getTrabajadorCertificadoTrabajoReportPDF(String codigoTrabajador) throws ClassNotFoundException, SQLException {
        HashMap<String, String> params = new HashMap<String, String>();
        // se definen los parametros utilizados en el reporte
        params.put("codigoTrabajador", codigoTrabajador);        
        // se establece el parametro logo del reporte con la imagen que queremos desplegar en el reporte
        context = FacesContext.getCurrentInstance();
        servletContext = (ServletContext) context.getExternalContext().getContext();
        String rutaImagen = servletContext.getRealPath("/WEB-INF/classes/reports/imagenes/dggth.jpg"); 
        params.put("logo", rutaImagen);
        // se instancia la clase que nos genera el reporte
        // se pasa: el nombre del reporte, los parametros del reporte y el tipo de salida
        ReportFactory rf = new ReportFactory("puesto_detalle_trabajador.jasper", params, "PDF");
        return new DefaultStreamedContent(rf.getReportStream(), "", "trabajador_certificado_trabajo_"+codigoTrabajador +".pdf");
    }        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Método para imprimir ubicación laboral">
    public StreamedContent getTrabajadorUbicacionLaboralReportPDF(String codigoTrabajador) throws ClassNotFoundException, SQLException {
        HashMap<String, String> params = new HashMap<String, String>();
        // se definen los parametros utilizados en el reporte
        params.put("codigoTrabajador", codigoTrabajador);
        // se establece el parametro logo del reporte con la imagen que queremos desplegar en el reporte
        context = FacesContext.getCurrentInstance();
        servletContext = (ServletContext) context.getExternalContext().getContext();
        String rutaImagen = servletContext.getRealPath("/WEB-INF/classes/reports/imagenes/dggth.jpg"); 
        params.put("logo", rutaImagen);
        // se instancia la clase que nos genera el reporte
        // se pasa: el nombre del reporte, los parametros del reporte y el tipo de salida
        ReportFactory rf = new ReportFactory("puesto_efectivo_detalle_trabajador.jasper", params, "PDF");
        return new DefaultStreamedContent(rf.getReportStream(), "", "trabajador_ubicacion_laboral.pdf");
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Método para imprimir constancia ingreso">
    public StreamedContent getTrabajadorConstanciaIngresoReportPDF(String codigoTrabajador, String codigoPeriodoPago, String username) throws ClassNotFoundException, SQLException {
        HashMap<String, String> params = new HashMap<String, String>();
        // se definen los parametros utilizados en el reporte
        params.put("codigoTrabajador", codigoTrabajador);
        params.put("codigoPeriodoPago", codigoPeriodoPago);
        params.put("END_USER_CODE", username);
        // se establece el parametro logo del reporte con la imagen que queremos desplegar en el reporte
        context = FacesContext.getCurrentInstance();
        servletContext = (ServletContext) context.getExternalContext().getContext();
        String rutaImagen = servletContext.getRealPath("/WEB-INF/classes/reports/imagenes/dggth.jpg"); 
        params.put("logo", rutaImagen);
        // se instancia la clase que nos genera el reporte
        // se pasa: el nombre del reporte, los parametros del reporte y el tipo de salida
        ReportFactory rf = new ReportFactory("concepto_trabajador_periodo_detalle_constancia_ingresos.jasper", params, "PDF");
        return new DefaultStreamedContent(rf.getReportStream(), "", "trabajador_constancia_ingreso.pdf");
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Método para imprimir constancia ingreso con ubicación laboral">
    public StreamedContent getTrabajadorConstanciaIngresoUbicacionLaboralReportPDF(String codigoTrabajador, String codigoPeriodoPago, String username) throws ClassNotFoundException, SQLException {
        HashMap<String, String> params = new HashMap<String, String>();
        // se definen los parametros utilizados en el reporte
        params.put("codigoTrabajador", codigoTrabajador);
        params.put("codigoPeriodoPago", codigoPeriodoPago);
        params.put("END_USER_CODE", username);
        // se establece el parametro logo del reporte con la imagen que queremos desplegar en el reporte
        context = FacesContext.getCurrentInstance();
        servletContext = (ServletContext) context.getExternalContext().getContext();
        String rutaImagen = servletContext.getRealPath("/WEB-INF/classes/reports/imagenes/dggth.jpg"); 
        params.put("logo", rutaImagen);
        // se instancia la clase que nos genera el reporte
        // se pasa: el nombre del reporte, los parametros del reporte y el tipo de salida
        ReportFactory rf = new ReportFactory("concepto_trabajador_periodo_detalle_constancia_ubicacion_laboral.jasper", params, "PDF");
        return new DefaultStreamedContent(rf.getReportStream(), "", "trabajador_constancia_ingreso_ubicacion_laboral_"+ codigoTrabajador + "_"+ codigoPeriodoPago+".pdf");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Método para imprimir asistencia del trabajador">
    public StreamedContent getAsistenciaTrabajadorReportPDF(String codigoTrabajador, Date fechaAsistenciaDesde, Date fechaAsistenciaHasta) throws ClassNotFoundException, SQLException {
        HashMap params = new HashMap();
        // se definen los parametros utilizados en el reporte
        params.put("codigoTrabajador", codigoTrabajador);   
        params.put("fecha_asistencia_desde", fechaAsistenciaDesde);
        params.put("fecha_asistencia_hasta", fechaAsistenciaHasta);
        // se establece el parametro logo del reporte con la imagen que queremos desplegar en el reporte
        context = FacesContext.getCurrentInstance();
        servletContext = (ServletContext) context.getExternalContext().getContext();
        String rutaImagen = servletContext.getRealPath("/WEB-INF/classes/reports/imagenes/dggth.jpg"); 
        params.put("logo", rutaImagen);
        // se instancia la clase que nos genera el reporte
        // se pasa: el nombre del reporte, los parametros del reporte y el tipo de salida
        ReportFactory rf = new ReportFactory("asistencia_trabajador_detalle.jasper", params, "PDF");
        return new DefaultStreamedContent(rf.getReportStream(), "", "asistencia_trabajador_detalle_"+codigoTrabajador +".pdf");
    }        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Método para imprimir certificación documental">
    public StreamedContent getCertificacionDocumentalReportPDF(Long idPersona) throws ClassNotFoundException, SQLException {
        HashMap params = new HashMap();
        // se definen los parametros utilizados en el reporte
        params.put("id_persona", idPersona);        
        params.put("END_USER_CODE", FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("END_USER_CODE")); 
        // se establece el parametro logo del reporte con la imagen que queremos desplegar en el reporte
        context = FacesContext.getCurrentInstance();
        servletContext = (ServletContext) context.getExternalContext().getContext();
        String rutaImagen = servletContext.getRealPath("/WEB-INF/classes/reports/imagenes/dggth.jpg"); 
        params.put("logo", rutaImagen);
        // se instancia la clase que nos genera el reporte
        // se pasa: el nombre del reporte, los parametros del reporte y el tipo de salida
        ReportFactory rf = new ReportFactory("persona_certificacion_documental.jasper", params, "PDF");
        return new DefaultStreamedContent(rf.getReportStream(), "", "certificacion_documental.pdf");
    }        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Método para imprimir certificado de trabajo">
    public StreamedContent getConstanciaMatriculacionDocenteReportPDF(Long idTrabajador) throws ClassNotFoundException, SQLException {
        HashMap params = new HashMap();
        // se definen los parametros utilizados en el reporte
        params.put("id_trabajador", idTrabajador);   
        params.put("END_USER_NAME", FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("END_USER_NAME")); 
        // se establece el parametro logo del reporte con la imagen que queremos desplegar en el reporte
        context = FacesContext.getCurrentInstance();
        servletContext = (ServletContext) context.getExternalContext().getContext();
        String rutaImagen = servletContext.getRealPath("/WEB-INF/classes/reports/imagenes/dggth.jpg"); 
        params.put("logo", rutaImagen);
        // se instancia la clase que nos genera el reporte
        // se pasa: el nombre del reporte, los parametros del reporte y el tipo de salida
        ReportFactory rf = new ReportFactory("trabajador_constancia_matriculacion.jasper", params, "PDF");
        return new DefaultStreamedContent(rf.getReportStream(), "", "trabajador_constancia_matriculacion.pdf");
    }        
    // </editor-fold>
    /* ------------------------------------------------------
     * procesos para llamar a reporte constancia de ingreso */
   

    public String viewTrabajadorConstanciaIngresoReportPDF(String codigoTrabajador, String codigoPeriodoPago, String username) throws SQLException, IOException, NamingException {
        //codIns = '0';
        //System.out.println("codIns"+parCodigo);   
        
        context = FacesContext.getCurrentInstance();
        servletContext = (ServletContext) context.getExternalContext().getContext();

        
        //Fill Map with params values
        HashMap hm = new HashMap();
        hm.put("codigoTrabajador", codigoTrabajador);
        hm.put("codigoPeriodoPago", codigoPeriodoPago);  
        hm.put("END_USER_CODE", username);
        
        // para pasar la ruta de la imagen por parametro
        // el parametro debe estar definido en el reporte, en el lugar donde estara la imagen
        String rutaImagen = servletContext.getRealPath("/WEB-INF/classes/reports/imagenes/dggth.jpg"); 
        hm.put("logo", rutaImagen);
        
        //Connect with local datasource
        Context ctx = new InitialContext();

        Connection conexion;
        try {
            conexion = conectarBD();
            conexion.setAutoCommit(true);

            String reportId = "concepto_trabajador_periodo_detalle_constancia_ingresos";
            
            String rutaReporte = servletContext.getRealPath("/WEB-INF/classes/reports");
            
            String rptName = reportId+"_"+codigoTrabajador+"_"+codigoPeriodoPago;

            File file = new File(rutaReporte);
            JasperPrint jasperPrint;
            try {
                jasperPrint = JasperFillManager.fillReport(
                        new FileInputStream(new File(file, reportId + ".jasper")),
                        hm, conexion);
                byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.addHeader("Content-disposition","filename="+rptName+".pdf"); 
                response.setContentLength(bytes.length);
                response.getOutputStream().write(bytes);
                response.setContentType("application/pdf");
                context.responseComplete();
            } catch (JRException ex) {
                Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
        }

        /***********************************************************************
         * Pour afficher une bo�te de dialogue pour enregistrer le fichier sous
         * le nom rapport.pdf
         **********************************************************************/
        return null;
    }

    public String viewTrabajadorConstanciaIngresoUbicacionLaboralReportPDF(String codigoTrabajador, String codigoPeriodoPago, String username) throws SQLException, IOException, NamingException {
        //codIns = '0';
        //System.out.println("codIns"+parCodigo);   
        
        context = FacesContext.getCurrentInstance();
        servletContext = (ServletContext) context.getExternalContext().getContext();
        
        //Fill Map with params values
        HashMap hm = new HashMap();
        hm.put("codigoTrabajador", codigoTrabajador);
        hm.put("codigoPeriodoPago", codigoPeriodoPago);
        hm.put("END_USER_CODE", username);        
        
        // para pasar la ruta de la imagen por parametro
        // el parametro debe estar definido en el reporte, en el lugar donde estara la imagen
        String rutaImagen = servletContext.getRealPath("/WEB-INF/classes/reports/imagenes/dggth.jpg"); 
        hm.put("logo", rutaImagen);
        
        //Connect with local datasource
        Context ctx = new InitialContext();

        Connection conexion;
        try {
            conexion = conectarBD();
            conexion.setAutoCommit(true);

            String reportId = "concepto_trabajador_periodo_detalle_constancia_ubicacion_laboral";
            
            String rutaReporte = servletContext.getRealPath("/WEB-INF/classes/reports");
            
            String rptName = reportId+"_"+codigoTrabajador+"_"+codigoPeriodoPago;

            File file = new File(rutaReporte);

            JasperPrint jasperPrint;
            try {
                jasperPrint = JasperFillManager.fillReport(
                        new FileInputStream(new File(file, reportId + ".jasper")),
                        hm, conexion);
                byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
//                response.addHeader("Content-disposition","filename=rptBeans.pdf");
                response.addHeader("Content-disposition","filename="+rptName+".pdf");                
                response.setContentLength(bytes.length);
                response.getOutputStream().write(bytes);
                response.setContentType("application/pdf");
                context.responseComplete();
            } catch (JRException ex) {
                Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
        }

        /***********************************************************************
         * Pour afficher une bo�te de dialogue pour enregistrer le fichier sous
         * le nom rapport.pdf
         **********************************************************************/
        return null;
    }
    
    public String viewTrabajadorCertificadoTrabajoReportPDF(String codigoTrabajador) throws SQLException, IOException, NamingException {
               
        context = FacesContext.getCurrentInstance();
        servletContext = (ServletContext) context.getExternalContext().getContext();

        
        //Fill Map with params values
        HashMap hm = new HashMap();
        hm.put("codigoTrabajador", codigoTrabajador);        
        
        // para pasar la ruta de la imagen por parametro
        // el parametro debe estar definido en el reporte, en el lugar donde estara la imagen
        String rutaImagen = servletContext.getRealPath("/WEB-INF/classes/reports/imagenes/dggth.jpg"); 
        hm.put("logo", rutaImagen);
        
        //Connect with local datasource
        Context ctx = new InitialContext();

        Connection conexion;
        try {
            conexion = conectarBD();
            conexion.setAutoCommit(true);

            String reportId = "puesto_detalle_trabajador";
            
            String rutaReporte = servletContext.getRealPath("/WEB-INF/classes/reports");

            File file = new File(rutaReporte);
            JasperPrint jasperPrint;
            try {
                jasperPrint = JasperFillManager.fillReport(
                        new FileInputStream(new File(file, reportId + ".jasper")),
                        hm, conexion);
                byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.addHeader("Content-disposition",
                        "filename=rptBeans.pdf");
                response.setContentLength(bytes.length);
                response.getOutputStream().write(bytes);
                response.setContentType("application/pdf");
                context.responseComplete();
            } catch (JRException ex) {
                Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
        }

        /***********************************************************************
         * Pour afficher une bo�te de dialogue pour enregistrer le fichier sous
         * le nom rapport.pdf
         **********************************************************************/
        return null;
    }
}
