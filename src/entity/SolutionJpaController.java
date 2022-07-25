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
public class SolutionJpaController implements Serializable {

    public SolutionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Solution solution) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Login loginid = solution.getLoginid();
            if (loginid != null) {
                loginid = em.getReference(loginid.getClass(), loginid.getLoginid());
                solution.setLoginid(loginid);
            }
            Bug bugid = solution.getBugid();
            if (bugid != null) {
                bugid = em.getReference(bugid.getClass(), bugid.getBugid());
                solution.setBugid(bugid);
            }
            em.persist(solution);
            if (loginid != null) {
                loginid.getSolutionCollection().add(solution);
                loginid = em.merge(loginid);
            }
            if (bugid != null) {
                bugid.getSolutionCollection().add(solution);
                bugid = em.merge(bugid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Solution solution) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solution persistentSolution = em.find(Solution.class, solution.getSolutionid());
            Login loginidOld = persistentSolution.getLoginid();
            Login loginidNew = solution.getLoginid();
            Bug bugidOld = persistentSolution.getBugid();
            Bug bugidNew = solution.getBugid();
            if (loginidNew != null) {
                loginidNew = em.getReference(loginidNew.getClass(), loginidNew.getLoginid());
                solution.setLoginid(loginidNew);
            }
            if (bugidNew != null) {
                bugidNew = em.getReference(bugidNew.getClass(), bugidNew.getBugid());
                solution.setBugid(bugidNew);
            }
            solution = em.merge(solution);
            if (loginidOld != null && !loginidOld.equals(loginidNew)) {
                loginidOld.getSolutionCollection().remove(solution);
                loginidOld = em.merge(loginidOld);
            }
            if (loginidNew != null && !loginidNew.equals(loginidOld)) {
                loginidNew.getSolutionCollection().add(solution);
                loginidNew = em.merge(loginidNew);
            }
            if (bugidOld != null && !bugidOld.equals(bugidNew)) {
                bugidOld.getSolutionCollection().remove(solution);
                bugidOld = em.merge(bugidOld);
            }
            if (bugidNew != null && !bugidNew.equals(bugidOld)) {
                bugidNew.getSolutionCollection().add(solution);
                bugidNew = em.merge(bugidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = solution.getSolutionid();
                if (findSolution(id) == null) {
                    throw new NonexistentEntityException("The solution with id " + id + " no longer exists.");
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
            Solution solution;
            try {
                solution = em.getReference(Solution.class, id);
                solution.getSolutionid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solution with id " + id + " no longer exists.", enfe);
            }
            Login loginid = solution.getLoginid();
            if (loginid != null) {
                loginid.getSolutionCollection().remove(solution);
                loginid = em.merge(loginid);
            }
            Bug bugid = solution.getBugid();
            if (bugid != null) {
                bugid.getSolutionCollection().remove(solution);
                bugid = em.merge(bugid);
            }
            em.remove(solution);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Solution> findSolutionEntities() {
        return findSolutionEntities(true, -1, -1);
    }

    public List<Solution> findSolutionEntities(int maxResults, int firstResult) {
        return findSolutionEntities(false, maxResults, firstResult);
    }

    private List<Solution> findSolutionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Solution.class));
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

    public Solution findSolution(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Solution.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolutionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solution> rt = cq.from(Solution.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
