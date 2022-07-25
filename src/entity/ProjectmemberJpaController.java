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
public class ProjectmemberJpaController implements Serializable {

    public ProjectmemberJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Projectmember projectmember) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project projectid = projectmember.getProjectid();
            if (projectid != null) {
                projectid = em.getReference(projectid.getClass(), projectid.getProjectid());
                projectmember.setProjectid(projectid);
            }
            Login loginid = projectmember.getLoginid();
            if (loginid != null) {
                loginid = em.getReference(loginid.getClass(), loginid.getLoginid());
                projectmember.setLoginid(loginid);
            }
            em.persist(projectmember);
            if (projectid != null) {
                projectid.getProjectmemberCollection().add(projectmember);
                projectid = em.merge(projectid);
            }
            if (loginid != null) {
                loginid.getProjectmemberCollection().add(projectmember);
                loginid = em.merge(loginid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Projectmember projectmember) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Projectmember persistentProjectmember = em.find(Projectmember.class, projectmember.getProjectmid());
            Project projectidOld = persistentProjectmember.getProjectid();
            Project projectidNew = projectmember.getProjectid();
            Login loginidOld = persistentProjectmember.getLoginid();
            Login loginidNew = projectmember.getLoginid();
            if (projectidNew != null) {
                projectidNew = em.getReference(projectidNew.getClass(), projectidNew.getProjectid());
                projectmember.setProjectid(projectidNew);
            }
            if (loginidNew != null) {
                loginidNew = em.getReference(loginidNew.getClass(), loginidNew.getLoginid());
                projectmember.setLoginid(loginidNew);
            }
            projectmember = em.merge(projectmember);
            if (projectidOld != null && !projectidOld.equals(projectidNew)) {
                projectidOld.getProjectmemberCollection().remove(projectmember);
                projectidOld = em.merge(projectidOld);
            }
            if (projectidNew != null && !projectidNew.equals(projectidOld)) {
                projectidNew.getProjectmemberCollection().add(projectmember);
                projectidNew = em.merge(projectidNew);
            }
            if (loginidOld != null && !loginidOld.equals(loginidNew)) {
                loginidOld.getProjectmemberCollection().remove(projectmember);
                loginidOld = em.merge(loginidOld);
            }
            if (loginidNew != null && !loginidNew.equals(loginidOld)) {
                loginidNew.getProjectmemberCollection().add(projectmember);
                loginidNew = em.merge(loginidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = projectmember.getProjectmid();
                if (findProjectmember(id) == null) {
                    throw new NonexistentEntityException("The projectmember with id " + id + " no longer exists.");
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
            Projectmember projectmember;
            try {
                projectmember = em.getReference(Projectmember.class, id);
                projectmember.getProjectmid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The projectmember with id " + id + " no longer exists.", enfe);
            }
            Project projectid = projectmember.getProjectid();
            if (projectid != null) {
                projectid.getProjectmemberCollection().remove(projectmember);
                projectid = em.merge(projectid);
            }
            Login loginid = projectmember.getLoginid();
            if (loginid != null) {
                loginid.getProjectmemberCollection().remove(projectmember);
                loginid = em.merge(loginid);
            }
            em.remove(projectmember);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Projectmember> findProjectmemberEntities() {
        return findProjectmemberEntities(true, -1, -1);
    }

    public List<Projectmember> findProjectmemberEntities(int maxResults, int firstResult) {
        return findProjectmemberEntities(false, maxResults, firstResult);
    }

    private List<Projectmember> findProjectmemberEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Projectmember.class));
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

    public Projectmember findProjectmember(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Projectmember.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjectmemberCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Projectmember> rt = cq.from(Projectmember.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
