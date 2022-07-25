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
public class BugassignedJpaController implements Serializable {

    public BugassignedJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bugassigned bugassigned) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Login loginid = bugassigned.getLoginid();
            if (loginid != null) {
                loginid = em.getReference(loginid.getClass(), loginid.getLoginid());
                bugassigned.setLoginid(loginid);
            }
            Bug bugid = bugassigned.getBugid();
            if (bugid != null) {
                bugid = em.getReference(bugid.getClass(), bugid.getBugid());
                bugassigned.setBugid(bugid);
            }
            em.persist(bugassigned);
            if (loginid != null) {
                loginid.getBugassignedCollection().add(bugassigned);
                loginid = em.merge(loginid);
            }
            if (bugid != null) {
                bugid.getBugassignedCollection().add(bugassigned);
                bugid = em.merge(bugid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bugassigned bugassigned) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bugassigned persistentBugassigned = em.find(Bugassigned.class, bugassigned.getBugaid());
            Login loginidOld = persistentBugassigned.getLoginid();
            Login loginidNew = bugassigned.getLoginid();
            Bug bugidOld = persistentBugassigned.getBugid();
            Bug bugidNew = bugassigned.getBugid();
            if (loginidNew != null) {
                loginidNew = em.getReference(loginidNew.getClass(), loginidNew.getLoginid());
                bugassigned.setLoginid(loginidNew);
            }
            if (bugidNew != null) {
                bugidNew = em.getReference(bugidNew.getClass(), bugidNew.getBugid());
                bugassigned.setBugid(bugidNew);
            }
            bugassigned = em.merge(bugassigned);
            if (loginidOld != null && !loginidOld.equals(loginidNew)) {
                loginidOld.getBugassignedCollection().remove(bugassigned);
                loginidOld = em.merge(loginidOld);
            }
            if (loginidNew != null && !loginidNew.equals(loginidOld)) {
                loginidNew.getBugassignedCollection().add(bugassigned);
                loginidNew = em.merge(loginidNew);
            }
            if (bugidOld != null && !bugidOld.equals(bugidNew)) {
                bugidOld.getBugassignedCollection().remove(bugassigned);
                bugidOld = em.merge(bugidOld);
            }
            if (bugidNew != null && !bugidNew.equals(bugidOld)) {
                bugidNew.getBugassignedCollection().add(bugassigned);
                bugidNew = em.merge(bugidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bugassigned.getBugaid();
                if (findBugassigned(id) == null) {
                    throw new NonexistentEntityException("The bugassigned with id " + id + " no longer exists.");
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
            Bugassigned bugassigned;
            try {
                bugassigned = em.getReference(Bugassigned.class, id);
                bugassigned.getBugaid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bugassigned with id " + id + " no longer exists.", enfe);
            }
            Login loginid = bugassigned.getLoginid();
            if (loginid != null) {
                loginid.getBugassignedCollection().remove(bugassigned);
                loginid = em.merge(loginid);
            }
            Bug bugid = bugassigned.getBugid();
            if (bugid != null) {
                bugid.getBugassignedCollection().remove(bugassigned);
                bugid = em.merge(bugid);
            }
            em.remove(bugassigned);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bugassigned> findBugassignedEntities() {
        return findBugassignedEntities(true, -1, -1);
    }

    public List<Bugassigned> findBugassignedEntities(int maxResults, int firstResult) {
        return findBugassignedEntities(false, maxResults, firstResult);
    }

    private List<Bugassigned> findBugassignedEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bugassigned.class));
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

    public Bugassigned findBugassigned(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bugassigned.class, id);
        } finally {
            em.close();
        }
    }

    public int getBugassignedCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bugassigned> rt = cq.from(Bugassigned.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
