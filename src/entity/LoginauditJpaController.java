/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author john
 */
public class LoginauditJpaController implements Serializable {

    public LoginauditJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Loginaudit loginaudit) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Login loginid = loginaudit.getLoginid();
            if (loginid != null) {
                loginid = em.getReference(loginid.getClass(), loginid.getLoginid());
                loginaudit.setLoginid(loginid);
            }
            em.persist(loginaudit);
            if (loginid != null) {
                loginid.getLoginauditCollection().add(loginaudit);
                loginid = em.merge(loginid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Loginaudit loginaudit) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Loginaudit persistentLoginaudit = em.find(Loginaudit.class, loginaudit.getLoginaid());
            Login loginidOld = persistentLoginaudit.getLoginid();
            Login loginidNew = loginaudit.getLoginid();
            if (loginidNew != null) {
                loginidNew = em.getReference(loginidNew.getClass(), loginidNew.getLoginid());
                loginaudit.setLoginid(loginidNew);
            }
            loginaudit = em.merge(loginaudit);
            if (loginidOld != null && !loginidOld.equals(loginidNew)) {
                loginidOld.getLoginauditCollection().remove(loginaudit);
                loginidOld = em.merge(loginidOld);
            }
            if (loginidNew != null && !loginidNew.equals(loginidOld)) {
                loginidNew.getLoginauditCollection().add(loginaudit);
                loginidNew = em.merge(loginidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = loginaudit.getLoginaid();
                if (findLoginaudit(id) == null) {
                    throw new NonexistentEntityException("The loginaudit with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Loginaudit loginaudit;
            try {
                loginaudit = em.getReference(Loginaudit.class, id);
                loginaudit.getLoginaid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The loginaudit with id " + id + " no longer exists.", enfe);
            }
            Login loginid = loginaudit.getLoginid();
            if (loginid != null) {
                loginid.getLoginauditCollection().remove(loginaudit);
                loginid = em.merge(loginid);
            }
            em.remove(loginaudit);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Loginaudit> findLoginauditEntities() {
        return findLoginauditEntities(true, -1, -1);
    }

    public List<Loginaudit> findLoginauditEntities(int maxResults, int firstResult) {
        return findLoginauditEntities(false, maxResults, firstResult);
    }

    private List<Loginaudit> findLoginauditEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Loginaudit.class));
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

    public Loginaudit findLoginaudit(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Loginaudit.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoginauditCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Loginaudit> rt = cq.from(Loginaudit.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
