/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.InstitucionTemp;
import entity.InstitucionesMacheada;
import entity.util.JpaUtil;
import facade.InstitucionTempFacade;
import facade.InstitucionesMacheadasFacade;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import model.InstitucionesMacheadasLazyList;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author lilian
 */
@ManagedBean(name = "macheoNombresInstitucionesEducativasBean")
@ViewScoped
public class MacheoNombresInstitucionesEducativasBean extends AbstractMB implements Serializable {

    public MacheoNombresInstitucionesEducativasBean() {
        nombreDepartamento = "CONCEPCION";
        selectOneMenuDepartamento.setValue("CONCEPCION");
        archivo1Disabled = true;
        archivo2Disabled = true;
        archivo2AdjuntoDisabled = false;
        archivoPorDefecto = false;
    }
    private InstitucionesMacheada[] selectedInstitucionesMacheadaArray;

    public InstitucionesMacheada[] getSelectedInstitucionesMacheadaArray() {
        return selectedInstitucionesMacheadaArray;
    }

    public void setSelectedInstitucionesMacheadaArray(InstitucionesMacheada[] selectedInstitucionesMacheadaArray) {
        this.selectedInstitucionesMacheadaArray = selectedInstitucionesMacheadaArray;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    private Map<String, Integer> columnas1Map = new HashMap<String, Integer>();
    private List<String> columnasList1 = new ArrayList<String>();
    private List<String> columnasList2 = new ArrayList<String>();
    private List<String> columnasList3 = new ArrayList<String>();
    private List<String> columnasList4 = new ArrayList<String>();
    private Map<String, Integer> columnas2Map = new HashMap<String, Integer>();
    private List<String> columnas2List1 = new ArrayList<String>();
    private List<String> columnas2List2 = new ArrayList<String>();
    private List<String> columnas2List3 = new ArrayList<String>();
    private List<String> columnas2List4 = new ArrayList<String>();
    private String urlPrimerArchivo;
    private String urlSegundoArchivo;
    private String destinationFile;
    private String destinationFilePrimerArchivo;
    private String destinationFileSegundoArchivo;
    private EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
    private EntityManager em = emf.createEntityManager();
    private String nombreDepartamento;
    private Long idDepartamento;
    private Long idInstitucionSelected;
    private InstitucionTempFacade institucionTempFacade;
    private InstitucionesMacheadasFacade institucionesMacheadasFacade;
    private String nombre1;
    private String nombre2;
    private String departamento1;
    private String departamento2;
    private String distrito1;
    private String distrito2;
    private String codigo1;
    private String codigo2;
    private SelectOneMenu selectOneMenuDepartamento = new SelectOneMenu();
    private SelectOneMenu selectOneMenuNombre1 = new SelectOneMenu();
    private SelectOneMenu selectOneMenuNombre2 = new SelectOneMenu();
    private SelectOneMenu selectOneMenuDepartamento1 = new SelectOneMenu();
    private SelectOneMenu selectOneMenuDepartamento2 = new SelectOneMenu();
    private SelectOneMenu selectOneMenuDistrito1 = new SelectOneMenu();
    private SelectOneMenu selectOneMenuDistrito2 = new SelectOneMenu();
    private SelectOneMenu selectOneMenuCodigo1 = new SelectOneMenu();
    private SelectOneMenu selectOneMenuCodigo2 = new SelectOneMenu();
    Boolean archivo1Disabled;
    Boolean archivo2Disabled;
    Boolean archivo2AdjuntoDisabled;
    Boolean archivoPorDefecto;
    SelectBooleanCheckbox selectBooleanCheckbox;

    public SelectBooleanCheckbox getSelectBooleanCheckbox() {
        return selectBooleanCheckbox;
    }

    public void setSelectBooleanCheckbox(SelectBooleanCheckbox selectBooleanCheckbox) {
        this.selectBooleanCheckbox = selectBooleanCheckbox;
    }

    public Boolean getArchivoPorDefecto() {
        return archivoPorDefecto;
    }

    public void setArchivoPorDefecto(Boolean archivoPorDefecto) {
        this.archivoPorDefecto = archivoPorDefecto;
    }

    public Boolean getArchivo1Disabled() {
        return archivo1Disabled;
    }

    public void setArchivo1Disabled(Boolean archivo1Disabled) {
        this.archivo1Disabled = archivo1Disabled;
    }

    public Boolean getArchivo2Disabled() {
        return archivo2Disabled;
    }

    public void setArchivo2Disabled(Boolean archivo2Disabled) {
        this.archivo2Disabled = archivo2Disabled;
    }

    public Boolean getArchivo2AdjuntoDisabled() {
        return archivo2AdjuntoDisabled;
    }

    public void setArchivo2AdjuntoDisabled(Boolean archivo2AdjuntoDisabled) {
        this.archivo2AdjuntoDisabled = archivo2AdjuntoDisabled;
    }

    public List<String> getColumnasList1() {
        return columnasList1;
    }

    public void setColumnasList1(List<String> columnasList1) {
        this.columnasList1 = columnasList1;
    }

    public List<String> getColumnasList2() {
        return columnasList2;
    }

    public void setColumnasList2(List<String> columnasList2) {
        this.columnasList2 = columnasList2;
    }

    public List<String> getColumnasList3() {
        return columnasList3;
    }

    public void setColumnasList3(List<String> columnasList3) {
        this.columnasList3 = columnasList3;
    }

    public List<String> getColumnasList4() {
        return columnasList4;
    }

    public void setColumnasList4(List<String> columnasList4) {
        this.columnasList4 = columnasList4;
    }

    public List<String> getColumnas2List1() {
        return columnas2List1;
    }

    public void setColumnas2List(List<String> columnas2List1) {
        this.columnas2List1 = columnas2List1;
    }

    public List<String> getColumnas2List2() {
        return columnas2List2;
    }

    public void setColumnas2List2(List<String> columnas2List2) {
        this.columnas2List2 = columnas2List2;
    }

    public List<String> getColumnas2List3() {
        return columnas2List3;
    }

    public void setColumnas2List3(List<String> columnas2List3) {
        this.columnas2List3 = columnas2List3;
    }

    public List<String> getColumnas2List4() {
        return columnas2List4;
    }

    public void setColumnas2List4(List<String> columnas2List4) {
        this.columnas2List4 = columnas2List4;
    }

    public Map<String, Integer> getColumnas1Map() {
        return columnas1Map;
    }

    public void setColumnas1Map(Map<String, Integer> columnas1Map) {
        this.columnas1Map = columnas1Map;
    }

    public Map<String, Integer> getColumnas2Map() {
        return columnas2Map;
    }

    public void setColumnas2Map(Map<String, Integer> columnas2Map) {
        this.columnas2Map = columnas2Map;
    }

    public String getUrlPrimerArchivo() {
        return urlPrimerArchivo;
    }

    public void setUrlPrimerArchivo(String urlPrimerArchivo) {
        this.urlPrimerArchivo = urlPrimerArchivo;
    }

    public String getUrlSegundoArchivo() {
        return urlSegundoArchivo;
    }

    public void setUrlSegundoArchivo(String urlSegundoArchivo) {
        this.urlSegundoArchivo = urlSegundoArchivo;
    }

    public String getDestinationFile() {
        return destinationFile;
    }

    public void setDestinationFile(String destinationFile) {
        this.destinationFile = destinationFile;
    }

    public String getDestinationFilePrimerArchivo() {
        return destinationFilePrimerArchivo;
    }

    public void setDestinationFilePrimerArchivo(String destinationFilePrimerArchivo) {
        this.destinationFilePrimerArchivo = destinationFilePrimerArchivo;
    }

    public String getDestinationFileSegundoArchivo() {
        return destinationFileSegundoArchivo;
    }

    public void setDestinationFileSegundoArchivo(String destinationFileSegundoArchivo) {
        this.destinationFileSegundoArchivo = destinationFileSegundoArchivo;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getDepartamento1() {
        return departamento1;
    }

    public void setDepartamento1(String departamento1) {
        this.departamento1 = departamento1;
    }

    public String getDepartamento2() {
        return departamento2;
    }

    public void setDepartamento2(String departamento2) {
        this.departamento2 = departamento2;
    }

    public String getDistrito1() {
        return distrito1;
    }

    public void setDistrito1(String distrito1) {
        this.distrito1 = distrito1;
    }

    public String getDistrito2() {
        return distrito2;
    }

    public void setDistrito2(String distrito2) {
        this.distrito2 = distrito2;
    }

    public String getCodigo1() {
        return codigo1;
    }

    public void setCodigo1(String codigo1) {
        this.codigo1 = codigo1;
    }

    public String getCodigo2() {
        return codigo2;
    }

    public void setCodigo2(String codigo2) {
        this.codigo2 = codigo2;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public Long getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Long getIdInstitucionSelected() {
        return idInstitucionSelected;
    }

    public void setIdInstitucionSelected(Long idInstitucionSelected) {
        this.idInstitucionSelected = idInstitucionSelected;
    }

    public InstitucionTempFacade getInstitucionTempFacade() {
        if (institucionTempFacade == null) {
            institucionTempFacade = new InstitucionTempFacade();
        }
        return institucionTempFacade;
    }

    public InstitucionesMacheadasFacade getInstitucionesMacheadasFacade() {
        if (institucionesMacheadasFacade == null) {
            institucionesMacheadasFacade = new InstitucionesMacheadasFacade();
        }
        return institucionesMacheadasFacade;
    }

    public SelectOneMenu getSelectOneMenuDepartamento() {
        return selectOneMenuDepartamento;
    }

    public void setSelectOneMenuDepartamento(SelectOneMenu selectOneMenuDepartamento) {
        this.selectOneMenuDepartamento = selectOneMenuDepartamento;
    }

    public SelectOneMenu getSelectOneMenuNombre1() {
        return selectOneMenuNombre1;
    }

    public void setSelectOneMenuNombre1(SelectOneMenu selectOneMenuNombre1) {
        this.selectOneMenuNombre1 = selectOneMenuNombre1;
    }

    public SelectOneMenu getSelectOneMenuNombre2() {
        return selectOneMenuNombre2;
    }

    public void setSelectOneMenuNombre2(SelectOneMenu selectOneMenuNombre2) {
        this.selectOneMenuNombre2 = selectOneMenuNombre2;
    }

    public SelectOneMenu getSelectOneMenuDepartamento1() {
        return selectOneMenuDepartamento1;
    }

    public void setSelectOneMenuDepartamento1(SelectOneMenu selectOneMenuDepartamento1) {
        this.selectOneMenuDepartamento1 = selectOneMenuDepartamento1;
    }

    public SelectOneMenu getSelectOneMenuDepartamento2() {
        return selectOneMenuDepartamento2;
    }

    public void setSelectOneMenuDepartamento2(SelectOneMenu selectOneMenuDepartamento2) {
        this.selectOneMenuDepartamento2 = selectOneMenuDepartamento2;
    }

    public SelectOneMenu getSelectOneMenuDistrito1() {
        return selectOneMenuDistrito1;
    }

    public void setSelectOneMenuDistrito1(SelectOneMenu selectOneMenuDistrito1) {
        this.selectOneMenuDistrito1 = selectOneMenuDistrito1;
    }

    public SelectOneMenu getSelectOneMenuDistrito2() {
        return selectOneMenuDistrito2;
    }

    public void setSelectOneMenuDistrito2(SelectOneMenu selectOneMenuDistrito2) {
        this.selectOneMenuDistrito2 = selectOneMenuDistrito2;
    }

    public SelectOneMenu getSelectOneMenuCodigo1() {
        return selectOneMenuCodigo1;
    }

    public void setSelectOneMenuCodigo1(SelectOneMenu selectOneMenuCodigo1) {
        this.selectOneMenuCodigo1 = selectOneMenuCodigo1;
    }

    public SelectOneMenu getSelectOneMenuCodigo2() {
        return selectOneMenuCodigo2;
    }

    public void setSelectOneMenuCodigo2(SelectOneMenu selectOneMenuCodigo2) {
        this.selectOneMenuCodigo2 = selectOneMenuCodigo2;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="método para subir archivo al servidor: (update)">
    public void handleFileUploadUpdatePrimerArchivo(FileUploadEvent event) throws IOException, NamingException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException, javax.transaction.NotSupportedException, javax.transaction.RollbackException {
        try {
            copyFilePrimerArchivo(event.getFile().getFileName(), event.getFile().getInputstream());
            this.setUrlPrimerArchivo(event.getFile().getFileName());
            FacesMessage msg = new FacesMessage("Correcto", event.getFile().getFileName() + " se ha adjuntado correctamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            archivo1Disabled = false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El archivo no ha sido subido", "");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="método para subir archivo al servidor: (update)">
    public void handleFileUploadUpdateSegundoArchivo(FileUploadEvent event) throws IOException, NamingException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException, javax.transaction.NotSupportedException, javax.transaction.RollbackException {
        try {
            copyFileSegundoArchivo(event.getFile().getFileName(), event.getFile().getInputstream());
            this.setUrlSegundoArchivo(event.getFile().getFileName());
            FacesMessage msg = new FacesMessage("Correcto", event.getFile().getFileName() + " se ha adjuntado correctamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            archivo2Disabled = false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El archivo no ha sido subido", "");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }
    }
    // </editor-fold>

    public void utilizarArchivoPorDefecto() {
        if (((Boolean) selectBooleanCheckbox.getValue()) == false) {
            archivo2Disabled = false;
            archivo2AdjuntoDisabled = false;
        } else if (((Boolean) selectBooleanCheckbox.getValue()) == true) {
            archivo2Disabled = true;
            archivo2AdjuntoDisabled = true;
        } else {
            archivo2Disabled = false;
            archivo2AdjuntoDisabled = false;
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="método para copiar el archivo al servidor">

    public void copyFilePrimerArchivo(String fileName, InputStream in) {

        try {
            FacesContext context2 = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) context2.getExternalContext().getContext();

            //this.setDestinationFilePrimerArchivo(servletContext.getRealPath("/" + fileName));
            this.setDestinationFilePrimerArchivo(fileName);
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + getDestinationFilePrimerArchivo());
            
            /*File folder = new File(servletContext.getRealPath("/documentaciones/notas/"));
            if (!folder.exists()) { // si no existe la carpeta, la creamos
                folder.mkdir();
            }*/
            // verificacion si el archivo que vamos a subir ya existe
            File file = new File(destinationFilePrimerArchivo);
            /*if (file.exists()) { // si existe el archivo, modificamos el nombre antes de subir
             // separar nombre de archivo y extension
             String extension = "", fileSinExtension = "";

             int i = file.getName().lastIndexOf('.');
             if (i > 0) {
             extension = file.getName().substring(i);
             fileSinExtension = file.getName().substring(0, i);
             }
             String fileRenombrado = "";
             fileRenombrado = fileSinExtension + "_copia" + extension;

             file.renameTo(new File(servletContext.getRealPath("/documentaciones/notas/" + fileRenombrado)));
             }*/
            OutputStream out = new FileOutputStream(file);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();

            BufferedReader br = null;
            String line = "";
            String cvsSplitBy = ";";
            br = new BufferedReader(new FileReader(destinationFilePrimerArchivo));
            columnasList1 = new ArrayList<String>();
            columnasList2 = new ArrayList<String>();
            columnasList3 = new ArrayList<String>();
            columnasList4 = new ArrayList<String>();
            columnas1Map = new HashMap<String, Integer>();
            while ((line = br.readLine()) != null) {
                String[] resultado = line.split(cvsSplitBy);
                for (int i = 0; i < resultado.length; i++) {
                    columnasList1.add(resultado[i]);
                    columnasList2.add(resultado[i]);
                    columnasList3.add(resultado[i]);
                    columnasList4.add(resultado[i]);
                    columnas1Map.put(resultado[i], i);
                }
                break;
            }
            br.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="método para copiar el archivo al servidor">
    public void copyFileSegundoArchivo(String fileName, InputStream in) {

        try {
            // write the inputStream to a FileOutputStream
            FacesContext context2 = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) context2.getExternalContext().getContext();

            this.setDestinationFileSegundoArchivo(servletContext.getRealPath("/" + fileName));
            File folder = new File(servletContext.getRealPath("/"));
            if (!folder.exists()) { // si no existe la carpeta, la creamos
                folder.mkdir();
            }
            // verificacion si el archivo que vamos a subir ya existe
            File file = new File(destinationFileSegundoArchivo);
            /*if (file.exists()) { // si existe el archivo, modificamos el nombre antes de subir
             // separar nombre de archivo y extension
             String extension = "", fileSinExtension = "";

             int i = file.getName().lastIndexOf('.');
             if (i > 0) {
             extension = file.getName().substring(i);
             fileSinExtension = file.getName().substring(0, i);
             }
             String fileRenombrado = "";
             fileRenombrado = fileSinExtension + "_copia" + extension;

             file.renameTo(new File(servletContext.getRealPath("/documentaciones/notas/" + fileRenombrado)));
             }*/
            OutputStream out = new FileOutputStream(file);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();

            BufferedReader br = null;
            String line = "";
            String cvsSplitBy = ";";
            br = new BufferedReader(new FileReader(destinationFileSegundoArchivo));
            columnas2List1 = new ArrayList<String>();
            columnas2List2 = new ArrayList<String>();
            columnas2List3 = new ArrayList<String>();
            columnas2List4 = new ArrayList<String>();
            columnas2Map = new HashMap<String, Integer>();
            while ((line = br.readLine()) != null) {
                String[] resultado = line.split(cvsSplitBy);
                for (int i = 0; i < resultado.length; i++) {
                    columnas2List1.add(resultado[i]);
                    columnas2List2.add(resultado[i]);
                    columnas2List3.add(resultado[i]);
                    columnas2List4.add(resultado[i]);
                    columnas2Map.put(resultado[i], i);
                }
                break;
            }
            br.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Registrar Datos">
    Integer contadorMacheador;

    public void registrarDatos() {

        Boolean error = false;
        if (((Boolean) selectBooleanCheckbox.getValue()) == false) {
            if ((urlPrimerArchivo == null || urlPrimerArchivo.compareTo("") == 0)
                    || (urlSegundoArchivo == null || urlSegundoArchivo.compareTo("") == 0)) {
                error = true;
                displayErrorMessageToUser("Debe adjuntar ambos archivos", "");
            }
        } else if (((Boolean) selectBooleanCheckbox.getValue()) == true) {
            if ((urlPrimerArchivo == null || urlPrimerArchivo.compareTo("") == 0)) {
                error = true;
                displayErrorMessageToUser("Debe adjuntar un archivo ", "");
            }
        }

        if (error == false) {
            guardarDatosTablaTemporal();

            try {

                List<InstitucionTemp> institucionTempList;
                Query q = em.createQuery("SELECT i FROM InstitucionTemp i where i.tipo = 1 ");
                institucionTempList = q.getResultList();
                contadorMacheador = 0;

                System.out.println("******************************************************* EMPIEZA EL MACHEO " + new Date());
                for (InstitucionTemp item : institucionTempList) {

                    if (item.getNombreDepartamento() != null) {

                        q = em.createQuery("SELECT count(i) FROM InstitucionTemp i "
                                + "WHERE i.nombreDepartamento LIKE :nombreDepartamento ");
                        q.setParameter("nombreDepartamento", item.getNombreDepartamento());
                        Long countDepartamento = (Long) q.getSingleResult();

                        if (countDepartamento > 0) {
                            q = em.createQuery("SELECT i FROM InstitucionTemp i WHERE i.tipo = 2 "
                                    + "AND i.nombreDepartamento LIKE :nombreDepartamento ");
                            q.setParameter("nombreDepartamento", item.getNombreDepartamento());
                            List<InstitucionTemp> institucion2List = q.getResultList();
                            for (InstitucionTemp institucion2 : institucion2List) {
                                verificarMacheo(item, institucion2);
                            }
                        } else {
                            q = em.createQuery("SELECT i FROM InstitucionTemp i WHERE i.tipo = 2 ");
                            List<InstitucionTemp> institucion2List = q.getResultList();
                            for (InstitucionTemp institucion2 : institucion2List) {
                                verificarMacheo(item, institucion2);
                            }
                        }
                    } else {
                        q = em.createQuery("SELECT i FROM InstitucionTemp i WHERE i.tipo = 2 ");
                        List<InstitucionTemp> institucion2List = q.getResultList();
                        for (InstitucionTemp institucion2 : institucion2List) {
                            if ((institucion2.getNombreIntitucion()).compareTo(item.getNombreIntitucion()) == 0) {
                                contadorMacheador++;
                                InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                                institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                                institucionesMacheadas.setInstitucion1(item);
                                institucionesMacheadas.setInstitucion2(institucion2);
                                institucionesMacheadas.setEstado(true);
                                getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                            } else {
                                int coeficienteSimilaridad = StringMatching.calcularDistanciaDamerauLevenshtein(institucion2.getNombreIntitucion(), item.getNombreIntitucion());
                                int longitud;
                                if (institucion2.getNombreIntitucion().length() > item.getNombreIntitucion().length()) {
                                    longitud = institucion2.getNombreIntitucion().length();
                                } else {
                                    longitud = item.getNombreIntitucion().length();
                                }
                                if (coeficienteSimilaridad <= (longitud * 10) / 100) {
                                    contadorMacheador++;
                                    InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                                    institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                                    institucionesMacheadas.setInstitucion1(item);
                                    institucionesMacheadas.setInstitucion2(institucion2);
                                    institucionesMacheadas.setEstado(true);
                                    getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                                }
                            }
                        }
                    }
                }
                System.out.println("******************************************************* TERMINA EL MACHEO " + new Date());
            } catch (Exception e) {
                System.out.println("Error en el proceso de macheo: " + e.getMessage());
            }
        }

    }
    // </editor-fold>

    public void eliminarRegistrosSeleccionados(InstitucionesMacheada institucion) {
        try {
            getInstitucionesMacheadasFacade().deleteInstitucionesMacheadas(institucion);
            displayInfoMessageToUser("Eliminado con éxito", "La fila seleccionada se ha eliminado exitosamente");
        } catch (Exception e) {
            displayErrorMessageToUser("Ha ocurrido un error", "No se ha podido eliminar la fila seleccionada " + e.getMessage());
        }
    }

    public void verificarMacheo(InstitucionTemp institucion1, InstitucionTemp institucion2) {
        try {

            // <editor-fold defaultstate="collapsed" desc="ESCUELAS">
            if (((institucion1.getNombreIntitucion()).contains("ESCUELA")
                    && (institucion2.getNombreIntitucion()).contains("ESCUELA"))
                    || ((institucion1.getNombreIntitucion()).contains("ESCUELA")
                    && (!(institucion2.getNombreIntitucion()).contains("COLEGIO")))
                    || ((institucion1.getNombreIntitucion()).contains("ESCUELA")
                    && (!(institucion2.getNombreIntitucion()).contains("LICEO")))
                    || ((institucion1.getNombreIntitucion()).contains("ESCUELA")
                    && (!(institucion2.getNombreIntitucion()).contains("CENTRO")))
                    || ((institucion1.getNombreIntitucion()).contains("ESCUELA")
                    && (!(institucion2.getNombreIntitucion()).contains("SEDE")))) {

                String nombre1 = normalizarNombre(institucion1.getNombreIntitucion());
                String nombre2 = normalizarNombre(institucion2.getNombreIntitucion());
                int coeficienteSimilaridad = StringMatching.calcularDistanciaDamerauLevenshtein(nombre1, nombre2);
                int longitud;
                if (institucion2.getNombreIntitucion().length() > institucion1.getNombreIntitucion().length()) {
                    longitud = institucion2.getNombreIntitucion().length();
                } else {
                    longitud = institucion1.getNombreIntitucion().length();
                }
                //if(coeficienteSimilaridad <= (longitud*90)/100){
                StringTokenizer tokens = new StringTokenizer(institucion1.getNombreIntitucion(), " ");
                //int cant_tokens = tokens.countTokens();
                //int cant_tokens_coincidentes = 0;
                int i = 0;
                List<Integer> numerosEscuelas = new ArrayList();
                List<String> meses = new ArrayList();
                meses.add("ENERO");
                meses.add("FEBRERO");
                meses.add("MARZO");
                meses.add("ABRIL");
                meses.add("MAYO");
                meses.add("JUNIO");
                meses.add("JULIO");
                meses.add("AGOSTO");
                meses.add("SETIEMBRE");
                meses.add("OCTUBRE");
                meses.add("NOVIEMBRE");
                meses.add("DICIEMBRE");
                Boolean tieneMeses1 = false;
                while (tokens.hasMoreTokens()) {
                    String str = tokens.nextToken();
                    if (meses.contains(str)) {
                        tieneMeses1 = true;
                    }
                    try {
                        Integer.parseInt(str);
                        numerosEscuelas.add(Integer.parseInt(str));
                    } catch (Exception e) {
                    }
                }
                tokens = new StringTokenizer(institucion2.getNombreIntitucion(), " ");
                while (tokens.hasMoreTokens()) {
                    String str = tokens.nextToken();
                    try {
                        Integer.parseInt(str);
                        if (numerosEscuelas.contains(Integer.parseInt(str))) {
                            i++;
                        }
                    } catch (Exception e) {
                    }
                }
                int minimo = 0;
                if (tieneMeses1 == true) {
                    minimo = 1;
                }
                if (i > minimo) {
                    //ystem.out.println("entraaa");
                    contadorMacheador++;
                    InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                    institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                    institucionesMacheadas.setInstitucion1(institucion1);
                    institucionesMacheadas.setInstitucion2(institucion2);
                    institucionesMacheadas.setEstado(true);
                    getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                }
                //}
            }
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="LICEOS">
            if (
                    ((institucion1.getNombreIntitucion()).contains("LICEO")
                    && (institucion2.getNombreIntitucion()).contains("LICEO")) ||
                    ((institucion1.getNombreIntitucion()).contains("LICEO")
                    && !(institucion2.getNombreIntitucion()).contains("ESCUELA")) ||
                    ((institucion1.getNombreIntitucion()).contains("LICEO")
                    && !(institucion2.getNombreIntitucion()).contains("CENTRO")) ||
                    ((institucion1.getNombreIntitucion()).contains("LICEO")
                    && !(institucion2.getNombreIntitucion()).contains("SEDE"))) {

                Boolean macheado = false;
                String nombre1Normalizado = (normalizarNombre(institucion1.getNombreIntitucion())).trim();
                String nombre2Normalizado = (normalizarNombre(institucion2.getNombreIntitucion())).trim();
                if(nombre1Normalizado.compareTo(nombre2Normalizado)==0){
                    contadorMacheador++;
                    InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                    institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                    institucionesMacheadas.setInstitucion1(institucion1);
                    institucionesMacheadas.setInstitucion2(institucion2);
                    institucionesMacheadas.setEstado(true);
                    getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                    macheado=true;
                }
            }

            // <editor-fold defaultstate="collapsed" desc="COLEGIOS">
            if (
                    ((institucion1.getNombreIntitucion()).contains("COLEGIO")
                    && (institucion2.getNombreIntitucion()).contains("COLEGIO")) ||
                    ((institucion1.getNombreIntitucion()).contains("COLEGIO")
                    && !(institucion2.getNombreIntitucion()).contains("ESCUELA")) ||
                    ((institucion1.getNombreIntitucion()).contains("COLEGIO")
                    && !(institucion2.getNombreIntitucion()).contains("CENTRO")) ||
                    ((institucion1.getNombreIntitucion()).contains("COLEGIO")
                    && !(institucion2.getNombreIntitucion()).contains("SEDE"))) {

                Boolean macheado = false;
                String nombre1Normalizado = (normalizarNombre(institucion1.getNombreIntitucion())).trim();
                String nombre2Normalizado = (normalizarNombre(institucion2.getNombreIntitucion())).trim();
                if(nombre1Normalizado.compareTo(nombre2Normalizado)==0){
                    contadorMacheador++;
                    InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                    institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                    institucionesMacheadas.setInstitucion1(institucion1);
                    institucionesMacheadas.setInstitucion2(institucion2);
                    institucionesMacheadas.setEstado(true);
                    getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                    macheado=true;
                }
                if(macheado == false){
                //tokens de institucion 1
                StringTokenizer tokens = new StringTokenizer(institucion1.getNombreIntitucion(), " ");
                int cant_tokens = tokens.countTokens();
                int cant_tokens_coincidentes = 0;
                int i = 0;
                List<Integer> numeros1 = new ArrayList();
                while (tokens.hasMoreTokens()) {
                    String str = tokens.nextToken();
                    try {
                        Integer.parseInt(str);
                        numeros1.add(Integer.parseInt(str));
                    } catch (Exception e) {
                    }
                }
                //tokens de institucion 2
                List<Integer> numeros2 = new ArrayList();
                tokens = new StringTokenizer(institucion2.getNombreIntitucion(), " ");
                while (tokens.hasMoreTokens()) {
                    String str = tokens.nextToken();
                    try {
                        Integer.parseInt(str);
                        numeros2.add(Integer.parseInt(str));
                    } catch (Exception e) {
                    }
                }

                if (numeros1.isEmpty() && numeros2.isEmpty()) {
                    if ((institucion2.getNombreIntitucion()).compareTo(institucion1.getNombreIntitucion()) == 0) {
                        contadorMacheador++;
                        InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                        institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                        institucionesMacheadas.setInstitucion1(institucion1);
                        institucionesMacheadas.setInstitucion2(institucion2);
                        institucionesMacheadas.setEstado(true);
                        getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                    } else {

                        String nombre1 = normalizarNombre(institucion1.getNombreIntitucion());
                        String nombre2 = normalizarNombre(institucion2.getNombreIntitucion());
                        int coeficienteSimilaridad = StringMatching.calcularDistanciaDamerauLevenshtein(nombre1, nombre2);
                        int longitud;
                        if (nombre2.length() > nombre1.length()) {
                            longitud = nombre2.length();
                        } else {
                            longitud = nombre1.length();
                        }
                        //System.out.println("nombre1 " + nombre1);
                        //System.out.println("nombre2 " + nombre2);
                        //System.out.println("coeficienteSimilaridad " + coeficienteSimilaridad);
                        //System.out.println("longitud " + longitud);
                        //System.out.println("lllllllllllllllllllllllllll " + ((longitud * 20) / 100));
                        if (coeficienteSimilaridad <= (longitud * 30) / 100) {
                            if (institucion1.getNombreIntitucion().contains("NACIONAL") && institucion2.getNombreIntitucion().contains("PRIVADO")) {
                            } else if (institucion2.getNombreIntitucion().contains("NACIONAL") && institucion1.getNombreIntitucion().contains("PRIVADO")) {
                            } // <editor-fold defaultstate="collapsed" desc="Verificar si es CENTRO">
                            else if ((institucion1.getNombreIntitucion()).contains("CENTRO") == true) {
                                Pattern regPatt = Pattern.compile("\\s.*-.*\\s");
                                Matcher regMatch = regPatt.matcher(institucion1.getNombreIntitucion());
                                String patron = null;
                                while (regMatch.find()) {
                                    patron = regMatch.group();
                                    break;
                                }
                                if (patron != null) {
                                    if (institucion2.getNombreIntitucion().contains(patron)) {
                                        contadorMacheador++;
                                        InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                                        institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                                        institucionesMacheadas.setInstitucion1(institucion1);
                                        institucionesMacheadas.setInstitucion2(institucion2);
                                        institucionesMacheadas.setEstado(true);
                                        getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                                    }
                                }
                            } // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="Verificar no machear LICEO con COLEGIO">
                            else if (institucion1.getNombreIntitucion().contains("LICEO") && institucion2.getNombreIntitucion().contains("COLEGIO")) {
                            } else if (institucion1.getNombreIntitucion().contains("COLEGIO") && institucion2.getNombreIntitucion().contains("LICEO")) {
                            } // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="Verificar nombres de santos">
                            else if (institucion1.getNombreIntitucion().contains("SAN ")) {
                                tokens = new StringTokenizer(institucion1.getNombreIntitucion(), " ");
                                i = 0;
                                boolean bandera = false;
                                String str = null;
                                while (tokens.hasMoreTokens()) {
                                    str = tokens.nextToken();
                                    if (bandera == true) {
                                        break;
                                    }
                                    if (str.compareTo("SAN") == 0) {
                                        bandera = true;
                                    }
                                    i++;
                                }
                                if (str != null) {
                                    if (institucion2.getNombreIntitucion().contains(str)) {
                                        contadorMacheador++;
                                        InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                                        institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                                        institucionesMacheadas.setInstitucion1(institucion1);
                                        institucionesMacheadas.setInstitucion2(institucion2);
                                        institucionesMacheadas.setEstado(true);
                                        getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                                    }
                                }
                            } // </editor-fold>
                            else if (institucion1.getNombreIntitucion().contains("SANTA ")) {
                                tokens = new StringTokenizer(institucion1.getNombreIntitucion(), " ");
                                i = 0;
                                boolean bandera = false;
                                String str = null;
                                while (tokens.hasMoreTokens()) {
                                    str = tokens.nextToken();
                                    if (bandera == true) {
                                        break;
                                    }
                                    if (str.compareTo("SANTA") == 0) {
                                        bandera = true;
                                    }
                                    i++;
                                }
                                if (str != null) {
                                    if (institucion2.getNombreIntitucion().contains(str)) {
                                        contadorMacheador++;
                                        InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                                        institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                                        institucionesMacheadas.setInstitucion1(institucion1);
                                        institucionesMacheadas.setInstitucion2(institucion2);
                                        institucionesMacheadas.setEstado(true);
                                        getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                                    }
                                }
                            } else if (institucion1.getNombreIntitucion().contains("SANTO ")) {
                                tokens = new StringTokenizer(institucion1.getNombreIntitucion(), " ");
                                i = 0;
                                boolean bandera = false;
                                String str = null;
                                while (tokens.hasMoreTokens()) {
                                    str = tokens.nextToken();
                                    if (bandera == true) {
                                        break;
                                    }
                                    if (str.compareTo("SANTO") == 0) {
                                        bandera = true;
                                    }
                                    i++;
                                }
                                if (str != null) {
                                    if (institucion2.getNombreIntitucion().contains(str)) {
                                        contadorMacheador++;
                                        InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                                        institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                                        institucionesMacheadas.setInstitucion1(institucion1);
                                        institucionesMacheadas.setInstitucion2(institucion2);
                                        institucionesMacheadas.setEstado(true);
                                        getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                                    }
                                }
                            } else {
                                tokens = new StringTokenizer(institucion1.getNombreIntitucion(), " ");
                                cant_tokens = tokens.countTokens();
                                cant_tokens_coincidentes = 0;
                                i = 0;
                                while (tokens.hasMoreTokens()) {
                                    String str = tokens.nextToken();
                                    if (institucion2.getNombreIntitucion().contains(str)) {
                                        cant_tokens_coincidentes++;
                                    }
                                    i++;
                                }
                                //System.out.println("**** " + (double) cant_tokens_coincidentes / (double) cant_tokens);
                                if ((double) cant_tokens_coincidentes / (double) cant_tokens > 0.5) {
                                    contadorMacheador++;
                                    InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                                    institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                                    institucionesMacheadas.setInstitucion1(institucion1);
                                    institucionesMacheadas.setInstitucion2(institucion2);
                                    institucionesMacheadas.setEstado(true);
                                    getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                                }
                            }
                        } else {
                            tokens = new StringTokenizer(nombre1, " ");
                            cant_tokens = tokens.countTokens();
                            cant_tokens_coincidentes = 0;
                            i = 0;
                            while (tokens.hasMoreTokens()) {
                                String str = tokens.nextToken();
                                if (nombre2.contains(str)) {
                                    cant_tokens_coincidentes++;
                                }
                                i++;
                            }
                            //System.out.println("**** " + (double) cant_tokens_coincidentes / (double) cant_tokens);
                            if ((double) cant_tokens_coincidentes / (double) cant_tokens > 0.9) {
                                contadorMacheador++;
                                InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                                institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                                institucionesMacheadas.setInstitucion1(institucion1);
                                institucionesMacheadas.setInstitucion2(institucion2);
                                institucionesMacheadas.setEstado(true);
                                getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                            }
                        }
                    }
                }}
            } // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="CENTROS">
            else if (((institucion1.getNombreIntitucion()).contains("CENTRO")
                    && (!(institucion2.getNombreIntitucion()).contains("CENTRO")))
                    || ((institucion1.getNombreIntitucion()).contains("CENTRO")
                    && (!(institucion2.getNombreIntitucion()).contains("ESCUELA")))
                    || ((institucion1.getNombreIntitucion()).contains("CENTRO")
                    && (!(institucion2.getNombreIntitucion()).contains("COLEGIO")))
                    || ((institucion1.getNombreIntitucion()).contains("CENTRO")
                    && (!(institucion2.getNombreIntitucion()).contains("LICEO")))
                    || ((institucion1.getNombreIntitucion()).contains("CENTRO")
                    && (!(institucion2.getNombreIntitucion()).contains("SEDE")))) {
                
                Boolean macheado = false;
                
                Pattern regPatt = Pattern.compile("\\w+-[0-9]+");
                Matcher regMatch = regPatt.matcher(institucion1.getNombreIntitucion());
                List<String> tokenList = new ArrayList<String>();
                while (regMatch.find()) {
                    tokenList.add(regMatch.group());
                    break;
                }
                
                Pattern regPatt2 = Pattern.compile("\\w+-[0-9]+");
                Matcher regMatch2 = regPatt2.matcher(institucion2.getNombreIntitucion());
                List<String> tokenList2 = new ArrayList<String>();
                while (regMatch2.find()) {
                    tokenList2.add(regMatch2.group());
                }
                
                Boolean machea = false;
                for(String item : tokenList){
                    if(tokenList2.contains(item)){
                        machea = true;
                        break;
                    }
                }
                    
                if (machea == true) {
                    //if (institucion2.getNombreIntitucion().contains(patron.trim() + " ")) {
                        contadorMacheador++;
                        InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                        institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                        institucionesMacheadas.setInstitucion1(institucion1);
                        institucionesMacheadas.setInstitucion2(institucion2);
                        institucionesMacheadas.setEstado(true);
                        getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                        macheado = true;
                    //}
                }
                
            }
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="SEDE">
            else if (((institucion1.getNombreIntitucion()).contains("SEDE")
                    && (!(institucion2.getNombreIntitucion()).contains("SEDE")))
                    || ((institucion1.getNombreIntitucion()).contains("SEDE")
                    && (!(institucion2.getNombreIntitucion()).contains("ESCUELA")))
                    || ((institucion1.getNombreIntitucion()).contains("SEDE")
                    && (!(institucion2.getNombreIntitucion()).contains("COLEGIO")))
                    || ((institucion1.getNombreIntitucion()).contains("SEDE")
                    && (!(institucion2.getNombreIntitucion()).contains("LICEO"))) ) {
                
                Boolean macheado = false;
                
                Pattern regPatt = Pattern.compile("[0-9]+");
                Matcher regMatch = regPatt.matcher(institucion1.getNombreIntitucion());
                List<String> tokenList = new ArrayList<String>();
                while (regMatch.find()) {
                    tokenList.add(regMatch.group());
                    break;
                }
                
                Pattern regPatt2 = Pattern.compile("[0-9]+");
                Matcher regMatch2 = regPatt2.matcher(institucion2.getNombreIntitucion());
                List<String> tokenList2 = new ArrayList<String>();
                while (regMatch2.find()) {
                    tokenList2.add(regMatch2.group().trim());
                }
             
                Boolean machea = false;
                if(tokenList.size() == 1 && tokenList2.size()==1){
                    if((tokenList.get(0)).compareTo(tokenList2.get(0))==0){
                        machea=true;
                    }
                }
                    
                if (machea == true) {
                    //if (institucion2.getNombreIntitucion().contains(patron.trim() + " ")) {
                        contadorMacheador++;
                        InstitucionesMacheada institucionesMacheadas = new InstitucionesMacheada();
                        institucionesMacheadas.setIdInstitucionesMacheadas(contadorMacheador);
                        institucionesMacheadas.setInstitucion1(institucion1);
                        institucionesMacheadas.setInstitucion2(institucion2);
                        institucionesMacheadas.setEstado(true);
                        getInstitucionesMacheadasFacade().createInstitucionesMacheadas(institucionesMacheadas);
                        macheado = true;
                    //}
                }
                
            }
            // </editor-fold>

        } catch (Exception e) {
            System.out.println("Problema para guardar macheo " + e.getMessage() + institucion1.getNombreInstitucionOriginal() + "-" + institucion2.getNombreInstitucionOriginal());
        }
    }

    public void valueChangeListener1() {
        if (((String) selectOneMenuNombre1.getValue()) != null) {
            columnasList2.remove((String) selectOneMenuNombre1.getValue());
            columnasList3.remove((String) selectOneMenuNombre1.getValue());
            columnasList4.remove((String) selectOneMenuNombre1.getValue());
        }
        if (nombre1 != null) {
            columnasList2.add(nombre1);
            columnasList3.add(nombre1);
            columnasList4.add(nombre1);
        }
    }

    public void valueChangeListener2() {
        if (((String) selectOneMenuDepartamento1.getValue()) != null) {
            columnasList1.remove((String) selectOneMenuDepartamento1.getValue());
            columnasList3.remove((String) selectOneMenuDepartamento1.getValue());
            columnasList4.remove((String) selectOneMenuDepartamento1.getValue());
        }
        if (departamento1 != null) {
            columnasList1.add(departamento1);
            columnasList3.add(departamento1);
            columnasList4.add(departamento1);
        }
    }

    public void valueChangeListener3() {
        if (((String) selectOneMenuDistrito1.getValue()) != null) {
            columnasList1.remove((String) selectOneMenuDistrito1.getValue());
            columnasList2.remove((String) selectOneMenuDistrito1.getValue());
            columnasList4.remove((String) selectOneMenuDistrito1.getValue());
        }
        if (distrito1 != null) {
            columnasList1.add(distrito1);
            columnasList2.add(distrito1);
            columnasList4.add(distrito1);
        }
    }

    public void valueChangeListener4() {
        if (((String) selectOneMenuCodigo1.getValue()) != null) {
            columnasList1.remove((String) selectOneMenuCodigo1.getValue());
            columnasList2.remove((String) selectOneMenuCodigo1.getValue());
            columnasList3.remove((String) selectOneMenuCodigo1.getValue());
        }
        if (codigo1 != null) {
            columnasList1.add(codigo1);
            columnasList2.add(codigo1);
            columnasList3.add(codigo1);
        }
    }

    public void valueChangeListener5() {
        if (((String) selectOneMenuNombre2.getValue()) != null) {
            columnas2List2.remove((String) selectOneMenuNombre2.getValue());
            columnas2List3.remove((String) selectOneMenuNombre2.getValue());
            columnas2List4.remove((String) selectOneMenuNombre2.getValue());
        }
        if (nombre2 != null) {
            columnas2List2.add(nombre2);
            columnas2List3.add(nombre2);
            columnas2List4.add(nombre2);
        }
    }

    public void valueChangeListener6() {
        if (((String) selectOneMenuDepartamento2.getValue()) != null) {
            columnas2List1.remove((String) selectOneMenuDepartamento2.getValue());
            columnas2List3.remove((String) selectOneMenuDepartamento2.getValue());
            columnas2List4.remove((String) selectOneMenuDepartamento2.getValue());
        }
        if (departamento2 != null) {
            columnas2List1.add(departamento2);
            columnas2List3.add(departamento2);
            columnas2List4.add(departamento2);
        }
    }

    public void valueChangeListener7() {
        if (((String) selectOneMenuDistrito2.getValue()) != null) {
            columnas2List1.remove((String) selectOneMenuDistrito2.getValue());
            columnas2List2.remove((String) selectOneMenuDistrito2.getValue());
            columnas2List4.remove((String) selectOneMenuDistrito2.getValue());
        }
        if (distrito2 != null) {
            columnas2List1.add(distrito2);
            columnas2List2.add(distrito2);
            columnas2List4.add(distrito2);
        }
    }

    public void valueChangeListener8() {
        if (((String) selectOneMenuCodigo2.getValue()) != null) {
            columnas2List1.remove((String) selectOneMenuCodigo2.getValue());
            columnas2List2.remove((String) selectOneMenuCodigo2.getValue());
            columnas2List3.remove((String) selectOneMenuCodigo2.getValue());
        }
        if (codigo2 != null) {
            columnas2List1.add(codigo2);
            columnas2List2.add(codigo2);
            columnas2List3.add(codigo2);
        }
    }

    public void guardarDatosTablaTemporal() {
        try {

            //Eliminar todas las filas de la tabla temporal
            getInstitucionesMacheadasFacade().deleteAllInstitucionesMacheadas();

            //Eliminar todas las filas de la tabla temporal
            getInstitucionTempFacade().deleteAllInstitucionTemp();

            BufferedReader br = null;
            String line = "";
            String cvsSplitBy = ";";
            br = new BufferedReader(new FileReader(destinationFilePrimerArchivo));

            Integer count = 0;
            System.out.println("Empieza insituciones 1: " + new Date());
            while ((line = br.readLine()) != null) {
                if (count > 0) {
                    String[] resultado = line.split(cvsSplitBy);
                    //System.out.println("** " + count + " " + resultado[columnas1Map.get((String) selectOneMenuNombre1.getValue())]);
                    InstitucionTemp institucionTemp = new InstitucionTemp();
                    institucionTemp.setIdInstitucion(count);
                    /*institucionTemp.setNombreIntitucion(normalizarString(resultado[0]));
                     institucionTemp.setNombreInstitucionOriginal(resultado[0]);
                     institucionTemp.setCodigoDepartamento(normalizarString(resultado[1]));
                     institucionTemp.setNombreDepartamento(normalizarString(resultado[2]));
                     institucionTemp.setCodigoDistrito(normalizarString(resultado[3]));
                     institucionTemp.setNombreDistrito(normalizarString(resultado[4]));
                     institucionTemp.setCodigoPrincipal(normalizarString(resultado[5]));*/
                    institucionTemp.setNombreIntitucion(normalizarString(resultado[columnas1Map.get((String) selectOneMenuNombre1.getValue())]));
                    institucionTemp.setNombreInstitucionOriginal(resultado[columnas1Map.get((String) selectOneMenuNombre1.getValue())]);
                    //institucionTemp.setCodigoDepartamento(normalizarString(resultado[columnas1Map.get((String)selectOneMenuNombre1.getValue())]));
                    institucionTemp.setNombreDepartamento(normalizarString(normalizarString(resultado[columnas1Map.get((String) selectOneMenuDepartamento1.getValue())])));
                    //institucionTemp.setCodigoDistrito(normalizarString(normalizarString(resultado[columnas1Map.get((String)selectOneMenuNombre1.getValue())])));
                    if (columnas1Map.get((String) selectOneMenuDistrito1.getValue()) != null) {
                        institucionTemp.setNombreDistrito(normalizarString(normalizarString(resultado[columnas1Map.get((String) selectOneMenuDistrito1.getValue())])));
                    }
                    if (columnas1Map.get((String) selectOneMenuCodigo1.getValue()) != null) {
                        institucionTemp.setCodigoPrincipal(normalizarString(normalizarString(resultado[columnas1Map.get((String) selectOneMenuCodigo1.getValue())])));
                    }

                    if (resultado.length <= 6) {
                        institucionTemp.setCodigoSecundario(null);
                    } else {
                        institucionTemp.setCodigoSecundario(normalizarString(resultado[6]));
                    }

                    institucionTemp.setTipo(1);
                    try {
                        if (institucionTemp.getNombreDepartamento() != null) {
                            if (institucionTemp.getNombreDepartamento().compareTo((String) selectOneMenuDepartamento.getValue()) == 0) {
                                getInstitucionTempFacade().createInstitucionTemp(institucionTemp);
                            }
                        }
                    } catch (ConstraintViolationException cve) {
                        Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
                        System.out.println("*** Violación de restricciones de la base de datos: " + violations);
                    }

                }
                count++;
            }
            br.close();
            System.out.println("Termina insituciones 1: " + new Date());




            System.out.println("Empieza insituciones 2: " + new Date());

            if (((Boolean) selectBooleanCheckbox.getValue()) == true) {
                FacesContext context2 = FacesContext.getCurrentInstance();
                ServletContext servletContext = (ServletContext) context2.getExternalContext().getContext();
                this.setDestinationFileSegundoArchivo(servletContext.getRealPath("/" + "codiniv2014.csv"));

                br = new BufferedReader(new FileReader(destinationFileSegundoArchivo));
                while ((line = br.readLine()) != null) {
                    if (count > 0) {
                        String[] resultado = line.split(cvsSplitBy);

                        InstitucionTemp institucionTemp = new InstitucionTemp();
                        institucionTemp.setIdInstitucion(count);
                        institucionTemp.setNombreIntitucion(normalizarString(resultado[0]));
                        institucionTemp.setNombreInstitucionOriginal(resultado[0]);
                        institucionTemp.setCodigoDepartamento(normalizarString(resultado[1]));
                        institucionTemp.setNombreDepartamento(normalizarString(resultado[2]));
                        institucionTemp.setCodigoDistrito(normalizarString(resultado[3]));
                        institucionTemp.setNombreDistrito(normalizarString(resultado[4]));
                        institucionTemp.setCodigoPrincipal(normalizarString(resultado[5]));
                        if (resultado.length <= 6) {
                            institucionTemp.setCodigoSecundario(null);
                        } else {
                            institucionTemp.setCodigoSecundario(normalizarString(resultado[6]));
                        }

                        institucionTemp.setTipo(2);
                        try {
                            if (institucionTemp.getNombreDepartamento() != null) {
                                if (institucionTemp.getNombreDepartamento().compareTo((String) selectOneMenuDepartamento.getValue()) == 0) {
                                    getInstitucionTempFacade().createInstitucionTemp(institucionTemp);
                                }
                            }
                        } catch (ConstraintViolationException cve) {
                            Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
                            System.out.println("*** Violación de restricciones de la base de datos: " + violations);
                        }

                    }
                    count++;
                }

                System.out.println("Termina insituciones 2: " + new Date());
                br.close();
            } else {
                br = new BufferedReader(new FileReader(destinationFileSegundoArchivo));

                System.out.println("Empieza insituciones 2: " + new Date());
                while ((line = br.readLine()) != null) {
                    if (count > 0) {
                        String[] resultado = line.split(cvsSplitBy);
                        //System.out.println("** " + count + " " + resultado[columnas2Map.get((String) selectOneMenuNombre2.getValue())]);
                        InstitucionTemp institucionTemp = new InstitucionTemp();
                        institucionTemp.setIdInstitucion(count);
                        institucionTemp.setNombreIntitucion(normalizarString(resultado[columnas2Map.get((String) selectOneMenuNombre2.getValue())]));
                        institucionTemp.setNombreInstitucionOriginal(resultado[columnas2Map.get((String) selectOneMenuNombre2.getValue())]);
                        //institucionTemp.setCodigoDepartamento(normalizarString(resultado[columnas1Map.get((String)selectOneMenuNombre1.getValue())]));
                        institucionTemp.setNombreDepartamento(normalizarString(normalizarString(resultado[columnas2Map.get((String) selectOneMenuDepartamento2.getValue())])));
                        //institucionTemp.setCodigoDistrito(normalizarString(normalizarString(resultado[columnas1Map.get((String)selectOneMenuNombre1.getValue())])));
                        if (columnas2Map.get((String) selectOneMenuDistrito2.getValue()) != null) {
                            institucionTemp.setNombreDistrito(normalizarString(normalizarString(resultado[columnas2Map.get((String) selectOneMenuDistrito2.getValue())])));
                        }
                        if (columnas2Map.get((String) selectOneMenuCodigo2.getValue()) != null) {
                            institucionTemp.setCodigoPrincipal(normalizarString(normalizarString(resultado[columnas2Map.get((String) selectOneMenuCodigo2.getValue())])));
                        }

                        if (resultado.length <= 6) {
                            institucionTemp.setCodigoSecundario(null);
                        } else {
                            institucionTemp.setCodigoSecundario(normalizarString(resultado[6]));
                        }

                        institucionTemp.setTipo(2);
                        try {
                            if (institucionTemp.getNombreDepartamento() != null) {
                                if (institucionTemp.getNombreDepartamento().compareTo((String) selectOneMenuDepartamento.getValue()) == 0) {
                                    getInstitucionTempFacade().createInstitucionTemp(institucionTemp);
                                }
                            }
                        } catch (ConstraintViolationException cve) {
                            Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
                            System.out.println("*** Violación de restricciones de la base de datos: " + violations);
                        }

                    }
                    count++;
                }
                br.close();
                System.out.println("Termina insituciones 2: " + new Date());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error1 al leer archivo");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error1 al leer archivo");
            e.printStackTrace();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("Ha ocurrido un error", "No se ha podido realizar la actualización " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String normalizarNombre(String cadena) {
        cadena = cadena.replace("ESCUELA", "");
        cadena = cadena.replace("BASICA", "");
        cadena = cadena.replace("COLEGIO", "");
        cadena = cadena.replace("NACIONAL", "");
        cadena = cadena.replace("PRIVADO", "");
        cadena = cadena.replace("SUBVENCIONADO", "");
        cadena = cadena.replace("TECNICO", "");
        cadena = cadena.replace("PARROQUIAL", "");
        cadena = cadena.replace("LICEO", "");
        return cadena;
    }

    public String normalizarString(String cadena) {
        if (cadena != null) {
            cadena = cadena.trim();
            cadena = cadena.toUpperCase();
            cadena = cadena.replace("Á", "A");
            cadena = cadena.replace("É", "E");
            cadena = cadena.replace("Í", "I");
            cadena = cadena.replace("Ó", "O");
            cadena = cadena.replace("Ú", "U");
            cadena = cadena.replace("(", " ");
            cadena = cadena.replace(")", " ");
            cadena = cadena.replace("\"", "");
            cadena = cadena.replace(".", ". ");
            cadena = cadena.replace("    ", " ");
            cadena = cadena.replace("   ", " ");
            cadena = cadena.replace("  ", " ");
            cadena = cadena.replace("ESC.", "ESCUELA");
            cadena = cadena.replace("ESC ", "ESCUELA ");
            cadena = cadena.replace("BAS.", "BASICA");
            cadena = cadena.replace("BAS ", "BASICA ");
            cadena = cadena.replace("COL.", "COLEGIO");
            cadena = cadena.replace("COL ", "COLEGIO ");
            cadena = cadena.replace("NAC.", "NACIONAL");
            cadena = cadena.replace("NAC ", "NACIONAL ");
            cadena = cadena.replace("TEC.", "TECNICO");
            cadena = cadena.replace("TEC ", "TECNICO ");
            cadena = cadena.replace("PARROQ.", "PARROQUIAL");
            cadena = cadena.replace("PARROQ ", "PARROQUIAL ");
            cadena = cadena.replace("PRIV.", "PRIVADO");
            cadena = cadena.replace("PRIV ", "PRIVADO ");
            cadena = cadena.replace("SUBV.", "SUBVENCIONADO");
            cadena = cadena.replace("SUBV ", "SUBVENCIONADO ");
            cadena = cadena.replace("TEC ", "TECNICO ");
            cadena = cadena.replace("LIC.", "LICEO");
            cadena = cadena.replace("LIC ", "LICEO ");
            cadena = cadena.replace("I. F. D. ", "INSTITUTO FORMACION DOCENTE");
            cadena = cadena.replace("GRAL.", "GENERAL");
            cadena = cadena.replace("GRAL ", "GENERAL ");
            cadena = cadena.replace("MCAL.", "MARISCAL");
            cadena = cadena.replace("MCAL ", "MARISCAL ");
            cadena = cadena.replace("CPTAN.", "CAPITAN");
            cadena = cadena.replace("CPTAN ", "CAPITAN ");
            cadena = cadena.replace("DR.", "DOCTOR");
            cadena = cadena.replace("DR ", "DOCTOR ");
            cadena = cadena.replace("STA.", "SANTA");
            cadena = cadena.replace("STA ", "SANTA ");
            if (cadena.compareTo("") == 0) {
                cadena = null;
            }
        }
        return cadena;
    }

    float stringMatching(String name1, String name2) {
        int diffLen = name1.length() - name2.length();
        if (diffLen < 0) {
            diffLen = diffLen * (-1);
        }
        if (diffLen < 100) {
        }
        return 0;
    }
    // <editor-fold defaultstate="collapsed" desc="Implementación del data model">
    private LazyDataModel<InstitucionesMacheada> institucionesMacheadasLazy = null;

    public LazyDataModel<InstitucionesMacheada> getInstitucionesMacheadas() throws Exception {
        if (institucionesMacheadasLazy == null) {
            institucionesMacheadasLazy = new InstitucionesMacheadasLazyList();
        }
        return institucionesMacheadasLazy;
    }
    // </editor-fold>

    InstitucionesMacheada filaSeleccionada;

    public InstitucionesMacheada getFilaSeleccionada() {
        return filaSeleccionada;
    }

    public void setFilaSeleccionada(InstitucionesMacheada filaSeleccionada) {
        this.filaSeleccionada = filaSeleccionada;
    }

    // <editor-fold defaultstate="collapsed" desc="Método onclick para delete">
    public void deleteOnClick(InstitucionesMacheada im) {

        filaSeleccionada = im;
        RequestContext rcontext = RequestContext.getCurrentInstance();
        rcontext.execute("institucionMacheadaDeleteDialogWidget.show()");

    }
    // </editor-fold>

    public void eliminarInstitucionMacheada() {
        try {
            getInstitucionesMacheadasFacade().deleteInstitucionesMacheadas(filaSeleccionada);
            displayInfoMessageToUser("Eliminado con éxito", "La fila seleccionada se ha eliminado exitosamente");
        } catch (Exception e) {
            displayErrorMessageToUser("Ha ocurrido un error", "No se ha podido eliminar la fila seleccionada " + e.getMessage());
        }
    }
}
