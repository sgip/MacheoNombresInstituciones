/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.egt.commons.util.ThrowableUtils;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;


abstract class GenericDAO<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("SigmecWebInformePU");
    private EntityManager em;
    private Class<T> entityClass;    
    private UserTransaction transaction;
    //private FacesContext context = FacesContext.getCurrentInstance();   

    public void beginTransaction()throws Exception {
        
        try{
            transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            em = emf.createEntityManager();
            //em.getTransaction().begin();
        }catch(Exception e) {            
           throw new Exception (ThrowableUtils.getString(e));
        } 
    }

    public void commit() throws Exception{
        try{
            //em.getTransaction().commit();
            transaction.commit();            
        }catch( Exception e) {             
//            Throwable t = new IllegalStateException(e.getCause().getMessage());  
//            throw new FacesException(t);    
            throw new Exception (ThrowableUtils.getString(e));  
        } 
    }

    public void rollback() throws Exception {        
        try{
            //em.getTransaction().rollback();
            transaction.rollback();
        }catch(Exception e) {
            throw new Exception (ThrowableUtils.getString(e)); 
        } 
    }

    public void closeTransaction() throws Exception {
        try{
        em.close(); 
        }catch(Exception e) {
            throw new Exception (ThrowableUtils.getString(e));             
        } 
    }

    public void commitAndCloseTransaction() throws Exception {
        try{
            transaction.commit();            
            closeTransaction();            
         }catch(Exception e) {
            throw new Exception (ThrowableUtils.getString(e));             
        } 
    }

    public void flush() {
        em.flush();
    }

    public void joinTransaction() {
        em = emf.createEntityManager();
        em.joinTransaction();
    }

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        em.persist(entity);
    }

    public void delete(T entity) {
        T entityToBeRemoved = em.merge(entity);

        em.remove(entityToBeRemoved);
    }

    public T update(T entity) {
        return em.merge(entity);
    }

    public T find(long entityID) {
        return em.find(entityClass, entityID);
    }

    public T findReferenceOnly(long entityID) {
        return em.getReference(entityClass, entityID);
    }

    // Using the unchecked because JPA does not have a
    // em.getCriteriaBuilder().createQuery()<T> method
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    // Using the unchecked because JPA does not have a
    // query.getSingleResult()<T> method
    @SuppressWarnings("unchecked")
    protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
        T result = null;

        try {
            Query query = em.createNamedQuery(namedQuery);

            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }

            result = (T) query.getSingleResult();

        } catch (NoResultException e) {
            System.out.println("No result found for named query: " + namedQuery);
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    private void populateQueryParameters(Query query, Map<String, Object> parameters) {
        for (Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
}
