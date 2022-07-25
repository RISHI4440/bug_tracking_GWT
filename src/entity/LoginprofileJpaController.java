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
public class LoginprofileJpaController implements Serializable {

    public LoginprofileJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Loginprofile loginprofile) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Login loginid = loginprofile.getLoginid();
            if (loginid != null) {
                loginid = em.getReference(loginid.getClass(), loginid.getLoginid());
                loginprofile.setLoginid(loginid);
            }
            em.persist(loginprofile);
            if (loginid != null) {
                loginid.getLoginprofileCollection().add(loginprofile);
                loginid = em.merge(loginid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Loginprofile loginprofile) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Loginprofile persistentLoginprofile = em.find(Loginprofile.class, loginprofile.getLoginpid());
            Login loginidOld = persistentLoginprofile.getLoginid();
            Login loginidNew = loginprofile.getLoginid();
            if (loginidNew != null) {
                loginidNew = em.getReference(loginidNew.getClass(), loginidNew.getLoginid());
                loginprofile.setLoginid(loginidNew);
            }
            loginprofile = em.merge(loginprofile);
            if (loginidOld != null && !loginidOld.equals(loginidNew)) {
                loginidOld.getLoginprofileCollection().remove(loginprofile);
                loginidOld = em.merge(loginidOld);
            }
            if (loginidNew != null && !loginidNew.equals(loginidOld)) {
                loginidNew.getLoginprofileCollection().add(loginprofile);
                loginidNew = em.merge(loginidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = loginprofile.getLoginpid();
                if (findLoginprofile(id) == null) {
                    throw new NonexistentEntityException("The loginprofile with id " + id + " no longer exists.");
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
            Loginprofile loginprofile;
            try {
                loginprofile = em.getReference(Loginprofile.class, id);
                loginprofile.getLoginpid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The loginprofile with id " + id + " no longer exists.", enfe);
            }
            Login loginid = loginprofile.getLoginid();
            if (loginid != null) {
                loginid.getLoginprofileCollection().remove(loginprofile);
                loginid = em.merge(loginid);
            }
            em.remove(loginprofile);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Loginprofile> findLoginprofileEntities() {
        return findLoginprofileEntities(true, -1, -1);
    }

    public List<Loginprofile> findLoginprofileEntities(int maxResults, int firstResult) {
        return findLoginprofileEntities(false, maxResults, firstResult);
    }

    private List<Loginprofile> findLoginprofileEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Loginprofile.class));
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

    public Loginprofile findLoginprofile(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Loginprofile.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoginprofileCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Loginprofile> rt = cq.from(Loginprofile.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
