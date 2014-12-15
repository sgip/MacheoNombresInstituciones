/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.InstitucionesMacheada;
import entity.util.JpaUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author lilian
 */
@ManagedBean(name = "institucionesMacheadasLazyList")
@ViewScoped
public class InstitucionesMacheadasLazyList extends LazyDataModel<InstitucionesMacheada> implements Serializable{
    
    
    
    private static final long serialVersionUID = 1L;    
    private List<InstitucionesMacheada> institucionesMacheadas;    
    
    private int rowCount = 0;
    
    EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
        
    @SuppressWarnings("unchecked")
    public static <T> T findBean(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
    }
    
    
    // el load del Lazy es el método principal y el que se sobreescribe con cada update en la interfaz
    @Override
    public List<InstitucionesMacheada> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        
        // <editor-fold defaultstate="collapsed" desc="Filtros">
        // definicion de filtros especificos menos filtro de fecha
        String queryFilter = null;
        String querySort = null;
        if ( !filters.isEmpty() ) {
            if (filters.containsKey("institucion1.nombreInstitucionOriginal")){
                if (queryFilter != null){
                    queryFilter = queryFilter + "AND ";
                    queryFilter = queryFilter + "lower(im.institucion1.nombreInstitucionOriginal) LIKE lower('%" + filters.get("institucion1.nombreInstitucionOriginal") + "%') ";
                } else{
                    queryFilter = "lower(im.institucion1.nombreInstitucionOriginal) LIKE lower('%" + filters.get("institucion1.nombreInstitucionOriginal") + "%') ";
                }
            }
            if (filters.containsKey("institucion2.nombreInstitucionOriginal")){
                if (queryFilter != null){
                    queryFilter = queryFilter + "AND ";
                    queryFilter = queryFilter + "lower(im.institucion2.nombreInstitucionOriginal) LIKE lower('%" + filters.get("institucion2.nombreInstitucionOriginal") + "%') ";
                } else{
                    queryFilter = "lower(im.institucion2.nombreInstitucionOriginal) LIKE lower('%" + filters.get("institucion2.nombreInstitucionOriginal") + "%') ";
                }
            }
            if (filters.containsKey("institucion1.nombreDistrito")){
                if (queryFilter != null){
                    queryFilter = queryFilter + "AND ";
                    queryFilter = queryFilter + "lower(im.institucion1.nombreDistrito) LIKE lower('%" + filters.get("institucion1.nombreDistrito") + "%') ";
                } else{
                    queryFilter = "lower(im.institucion1.nombreDistrito) LIKE lower('%" + filters.get("institucion1.nombreDistrito") + "%') ";
                }
            }
            if (filters.containsKey("institucion2.nombreDistrito")){
                if (queryFilter != null){
                    queryFilter = queryFilter + "AND ";
                    queryFilter = queryFilter + "lower(im.institucion2.nombreDistrito) LIKE lower('%" + filters.get("institucion2.nombreDistrito") + "%') ";
                } else{
                    queryFilter = "lower(im.institucion2.nombreDistrito) LIKE lower('%" + filters.get("institucion2.nombreDistrito") + "%') ";
                }
            }

        }      
        // definicion de order
        if(sortField != null) {
            querySort = "ORDER BY im." + sortField;
            querySort = querySort + (SortOrder.ASCENDING.equals(sortOrder) ? " ASC " : " DESC ");
        }
        // </editor-fold>

        String query;
        String queryCount;
        Query q;
            
        queryCount = "SELECT count(im) FROM InstitucionesMacheada im ";
        query = "SELECT im FROM InstitucionesMacheada im ";    
        
        if (queryFilter != null){
            queryCount = queryCount + "WHERE " + queryFilter;
            query = query + "WHERE " + queryFilter;
        }
        if(querySort != null){
            query = query + querySort;
        }
        
        try {
            // query de cantidad de registros
            q = em.createQuery(queryCount);
            rowCount = ((Long) q.getSingleResult()).intValue(); 
            setRowCount(rowCount);
            
            // query de datos a desplegar
            q = em.createQuery(query);
            q.setFirstResult(startingAt);
            q.setMaxResults(maxPerPage);
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            institucionesMacheadas = q.getResultList();  
            //System.out.println("InstitucionesMacheadasLazyList: theQuery: " + query + ", rowCount: " + rowCount);
        } catch(Exception ex) {
            System.out.println("InstitucionesMacheadasLazyList:load: " + ex.getLocalizedMessage());
            setRowCount(0);
        }
        return institucionesMacheadas;
    }
    
    @Override
    public Object getRowKey(InstitucionesMacheada institucionMacheada) {
        return institucionMacheada.getIdInstitucionesMacheadas();
    }
    
    @Override
    public InstitucionesMacheada getRowData(String institucionMacheadaId) {
        try {
            Integer id = Integer.valueOf(institucionMacheadaId);
            for (InstitucionesMacheada institucionMacheada : institucionesMacheadas) {
                if(id.equals(institucionMacheada.getIdInstitucionesMacheadas())){
                    return institucionMacheada;
                }
            }
            return null;
        }catch(Exception ex) {
            System.out.println("InstitucionesMacheadasLazyList:getRowData: " + ex.getLocalizedMessage());            
            return null;
        }
    }
}
