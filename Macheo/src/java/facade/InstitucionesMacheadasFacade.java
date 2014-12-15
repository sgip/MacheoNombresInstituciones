/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import beans.AbstractMB;
import dao.InstitucionesMacheadasDAO;
import entity.InstitucionesMacheada;
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
public class InstitucionesMacheadasFacade extends AbstractMB implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private InstitucionesMacheadasDAO institucionesMacheadasDAO = new InstitucionesMacheadasDAO();  
    
    @SuppressWarnings("unchecked")
    public static <T> T findBean(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
    }
    
    public void createInstitucionesMacheadas(InstitucionesMacheada institucionesMacheadas) throws Exception {
        try{
        institucionesMacheadasDAO.beginTransaction();
        institucionesMacheadasDAO.save(institucionesMacheadas);
        institucionesMacheadasDAO.commitAndCloseTransaction();
        }catch(Exception e){
            institucionesMacheadasDAO.commitAndCloseTransaction();
            System.out.println("mm");
        }
        
    }

    public void updateInstitucionesMacheadas(InstitucionesMacheada institucionesMacheadas) throws Exception {
        institucionesMacheadasDAO.beginTransaction();          
        institucionesMacheadasDAO.update(institucionesMacheadas);        
        institucionesMacheadasDAO.commitAndCloseTransaction();        
    }

    public void deleteInstitucionesMacheadas(InstitucionesMacheada institucionesMacheadas) throws Exception {
        institucionesMacheadasDAO.beginTransaction();
        institucionesMacheadasDAO.delete(institucionesMacheadas);
        institucionesMacheadasDAO.commitAndCloseTransaction();
    }
    
    public void deleteAllInstitucionesMacheadas() throws Exception {
        institucionesMacheadasDAO.beginTransaction();
        
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
    
        Query q;
        q = em.createQuery("DELETE FROM InstitucionesMacheada ");
        q.executeUpdate();
           
        institucionesMacheadasDAO.commitAndCloseTransaction();
    }
}
