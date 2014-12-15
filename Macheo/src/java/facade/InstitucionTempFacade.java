/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import beans.AbstractMB;
import dao.InstitucionTempDAO;
import entity.InstitucionTemp;
import entity.util.JpaUtil;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author lilian
 */
public class InstitucionTempFacade extends AbstractMB implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private InstitucionTempDAO institucionTempDAO = new InstitucionTempDAO();  
    
    @SuppressWarnings("unchecked")
    public static <T> T findBean(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
    }
    
    public void createInstitucionTemp(InstitucionTemp institucionTemp) throws Exception {
        institucionTempDAO.beginTransaction();
        institucionTempDAO.save(institucionTemp);
        institucionTempDAO.commitAndCloseTransaction();
        
    }

    public void updateInstitucionTemp(InstitucionTemp institucionTemp) throws Exception {
        institucionTempDAO.beginTransaction();          
        institucionTempDAO.update(institucionTemp);        
        institucionTempDAO.commitAndCloseTransaction();        
    }

    public void deleteInstitucionTemp(InstitucionTemp institucionTemp) throws Exception {
        institucionTempDAO.beginTransaction();
        institucionTempDAO.delete(institucionTemp);
        institucionTempDAO.commitAndCloseTransaction();
    }
    
    public void deleteAllInstitucionTemp() throws Exception {
        institucionTempDAO.beginTransaction();
        
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
    
        Query q;
        q = em.createQuery("DELETE FROM InstitucionTemp ");
        q.executeUpdate();
           
        institucionTempDAO.commitAndCloseTransaction();
    }
}
