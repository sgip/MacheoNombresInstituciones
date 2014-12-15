/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author marcos
 */
public class JpaUtil {
    private static final EntityManagerFactory emf;
    static{
        try{
            emf=Persistence.createEntityManagerFactory("SigmecWebInformePU", System.getProperties());
        }catch(Throwable t){
            System.out.println("Error al inicializar el Entity Manager Factory" + t);
            t.printStackTrace();
            throw new ExceptionInInitializerError();
            
        }        
    }
    
    public static EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }
}
