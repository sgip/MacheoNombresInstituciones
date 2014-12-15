/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.PreexistingEntityException;
import controllers.exceptions.RollbackFailureException;
//import entity.RastroInforme;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
//import entity.Funcion;
//import entity.CondicionEjecucionFuncion;
import javax.annotation.Resource;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

/**
 *
 * @author marcos
 */
public class RastroInformeJpaController implements Serializable {

    public RastroInformeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "SigmecWebInformePU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /*public void create(RastroInforme rastroInforme) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Funcion idFuncion = rastroInforme.getIdFuncion();
            if (idFuncion != null) {
                idFuncion = em.getReference(idFuncion.getClass(), idFuncion.getIdFuncion());
                rastroInforme.setIdFuncion(idFuncion);
            }
            CondicionEjecucionFuncion numeroCondicionEjecucionFuncion = rastroInforme.getNumeroCondicionEjecucionFuncion();
            if (numeroCondicionEjecucionFuncion != null) {
                numeroCondicionEjecucionFuncion = em.getReference(numeroCondicionEjecucionFuncion.getClass(), numeroCondicionEjecucionFuncion.getNumeroCondicionEjecucionFuncion());
                rastroInforme.setNumeroCondicionEjecucionFuncion(numeroCondicionEjecucionFuncion);
            }
            em.persist(rastroInforme);
            if (idFuncion != null) {
                idFuncion.getRastroInformeCollection().add(rastroInforme);
                idFuncion = em.merge(idFuncion);
            }
            if (numeroCondicionEjecucionFuncion != null) {
                numeroCondicionEjecucionFuncion.getRastroInformeCollection().add(rastroInforme);
                numeroCondicionEjecucionFuncion = em.merge(numeroCondicionEjecucionFuncion);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        
//    try {
//        em = getEntityManager();
//        em.getTransaction().begin();
//        em.persist(rastroInforme); 
//        em.getTransaction().commit();
//    } catch (Exception ex) {
//        if (findRastroInforme(rastroInforme.getIdRastroInforme()) != null) {
//            throw new PreexistingEntityException("Rastro informe " + rastroInforme + " already exists.", ex);
//        }
//        throw ex;
//    } finally {
//        if (em != null) {
//            em.close();
//        }
//    }
}
   

    public void edit(RastroInforme rastroInforme) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RastroInforme persistentRastroInforme = em.find(RastroInforme.class, rastroInforme.getIdRastroInforme());
            Funcion idFuncionOld = persistentRastroInforme.getIdFuncion();
            Funcion idFuncionNew = rastroInforme.getIdFuncion();
            CondicionEjecucionFuncion numeroCondicionEjecucionFuncionOld = persistentRastroInforme.getNumeroCondicionEjecucionFuncion();
            CondicionEjecucionFuncion numeroCondicionEjecucionFuncionNew = rastroInforme.getNumeroCondicionEjecucionFuncion();
            if (idFuncionNew != null) {
                idFuncionNew = em.getReference(idFuncionNew.getClass(), idFuncionNew.getIdFuncion());
                rastroInforme.setIdFuncion(idFuncionNew);
            }
            if (numeroCondicionEjecucionFuncionNew != null) {
                numeroCondicionEjecucionFuncionNew = em.getReference(numeroCondicionEjecucionFuncionNew.getClass(), numeroCondicionEjecucionFuncionNew.getNumeroCondicionEjecucionFuncion());
                rastroInforme.setNumeroCondicionEjecucionFuncion(numeroCondicionEjecucionFuncionNew);
            }
            rastroInforme = em.merge(rastroInforme);
            if (idFuncionOld != null && !idFuncionOld.equals(idFuncionNew)) {
                idFuncionOld.getRastroInformeCollection().remove(rastroInforme);
                idFuncionOld = em.merge(idFuncionOld);
            }
            if (idFuncionNew != null && !idFuncionNew.equals(idFuncionOld)) {
                idFuncionNew.getRastroInformeCollection().add(rastroInforme);
                idFuncionNew = em.merge(idFuncionNew);
            }
            if (numeroCondicionEjecucionFuncionOld != null && !numeroCondicionEjecucionFuncionOld.equals(numeroCondicionEjecucionFuncionNew)) {
                numeroCondicionEjecucionFuncionOld.getRastroInformeCollection().remove(rastroInforme);
                numeroCondicionEjecucionFuncionOld = em.merge(numeroCondicionEjecucionFuncionOld);
            }
            if (numeroCondicionEjecucionFuncionNew != null && !numeroCondicionEjecucionFuncionNew.equals(numeroCondicionEjecucionFuncionOld)) {
                numeroCondicionEjecucionFuncionNew.getRastroInformeCollection().add(rastroInforme);
                numeroCondicionEjecucionFuncionNew = em.merge(numeroCondicionEjecucionFuncionNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = rastroInforme.getIdRastroInforme();
                if (findRastroInforme(id) == null) {
                    throw new NonexistentEntityException("The rastroInforme with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RastroInforme rastroInforme;
            try {
                rastroInforme = em.getReference(RastroInforme.class, id);
                rastroInforme.getIdRastroInforme();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rastroInforme with id " + id + " no longer exists.", enfe);
            }
            Funcion idFuncion = rastroInforme.getIdFuncion();
            if (idFuncion != null) {
                idFuncion.getRastroInformeCollection().remove(rastroInforme);
                idFuncion = em.merge(idFuncion);
            }
            CondicionEjecucionFuncion numeroCondicionEjecucionFuncion = rastroInforme.getNumeroCondicionEjecucionFuncion();
            if (numeroCondicionEjecucionFuncion != null) {
                numeroCondicionEjecucionFuncion.getRastroInformeCollection().remove(rastroInforme);
                numeroCondicionEjecucionFuncion = em.merge(numeroCondicionEjecucionFuncion);
            }
            em.remove(rastroInforme);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RastroInforme> findRastroInformeEntities() {
        return findRastroInformeEntities(true, -1, -1);
    }

    public List<RastroInforme> findRastroInformeEntities(int maxResults, int firstResult) {
        return findRastroInformeEntities(false, maxResults, firstResult);
    }

    private List<RastroInforme> findRastroInformeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RastroInforme.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public RastroInforme findRastroInforme(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RastroInforme.class, id);
        } finally {
            em.close();
        }
    }

    public int getRastroInformeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RastroInforme> rt = cq.from(RastroInforme.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }*/
    
}
