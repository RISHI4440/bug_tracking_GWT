/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.exceptions.IllegalOrphanException;
import entity.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author john
 */
public class PriorityJpaController implements Serializable {

    public PriorityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Priority priority) {
        if (priority.getBugCollection() == null) {
            priority.setBugCollection(new ArrayList<Bug>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Bug> attachedBugCollection = new ArrayList<Bug>();
            for (Bug bugCollectionBugToAttach : priority.getBugCollection()) {
                bugCollectionBugToAttach = em.getReference(bugCollectionBugToAttach.getClass(), bugCollectionBugToAttach.getBugid());
                attachedBugCollection.add(bugCollectionBugToAttach);
            }
            priority.setBugCollection(attachedBugCollection);
            em.persist(priority);
            for (Bug bugCollectionBug : priority.getBugCollection()) {
                Priority oldPriorityidOfBugCollectionBug = bugCollectionBug.getPriorityid();
                bugCollectionBug.setPriorityid(priority);
                bugCollectionBug = em.merge(bugCollectionBug);
                if (oldPriorityidOfBugCollectionBug != null) {
                    oldPriorityidOfBugCollectionBug.getBugCollection().remove(bugCollectionBug);
                    oldPriorityidOfBugCollectionBug = em.merge(oldPriorityidOfBugCollectionBug);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Priority priority) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Priority persistentPriority = em.find(Priority.class, priority.getPriorityid());
            Collection<Bug> bugCollectionOld = persistentPriority.getBugCollection();
            Collection<Bug> bugCollectionNew = priority.getBugCollection();
            List<String> illegalOrphanMessages = null;
            for (Bug bugCollectionOldBug : bugCollectionOld) {
                if (!bugCollectionNew.contains(bugCollectionOldBug)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bug " + bugCollectionOldBug + " since its priorityid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Bug> attachedBugCollectionNew = new ArrayList<Bug>();
            for (Bug bugCollectionNewBugToAttach : bugCollectionNew) {
                bugCollectionNewBugToAttach = em.getReference(bugCollectionNewBugToAttach.getClass(), bugCollectionNewBugToAttach.getBugid());
                attachedBugCollectionNew.add(bugCollectionNewBugToAttach);
            }
            bugCollectionNew = attachedBugCollectionNew;
            priority.setBugCollection(bugCollectionNew);
            priority = em.merge(priority);
            for (Bug bugCollectionNewBug : bugCollectionNew) {
                if (!bugCollectionOld.contains(bugCollectionNewBug)) {
                    Priority oldPriorityidOfBugCollectionNewBug = bugCollectionNewBug.getPriorityid();
                    bugCollectionNewBug.setPriorityid(priority);
                    bugCollectionNewBug = em.merge(bugCollectionNewBug);
                    if (oldPriorityidOfBugCollectionNewBug != null && !oldPriorityidOfBugCollectionNewBug.equals(priority)) {
                        oldPriorityidOfBugCollectionNewBug.getBugCollection().remove(bugCollectionNewBug);
                        oldPriorityidOfBugCollectionNewBug = em.merge(oldPriorityidOfBugCollectionNewBug);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = priority.getPriorityid();
                if (findPriority(id) == null) {
                    throw new NonexistentEntityException("The priority with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Priority priority;
            try {
                priority = em.getReference(Priority.class, id);
                priority.getPriorityid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The priority with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Bug> bugCollectionOrphanCheck = priority.getBugCollection();
            for (Bug bugCollectionOrphanCheckBug : bugCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Priority (" + priority + ") cannot be destroyed since the Bug " + bugCollectionOrphanCheckBug + " in its bugCollection field has a non-nullable priorityid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(priority);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Priority> findPriorityEntities() {
        return findPriorityEntities(true, -1, -1);
    }

    public List<Priority> findPriorityEntities(int maxResults, int firstResult) {
        return findPriorityEntities(false, maxResults, firstResult);
    }

    private List<Priority> findPriorityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Priority.class));
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

    public Priority findPriority(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Priority.class, id);
        } finally {
            em.close();
        }
    }

    public int getPriorityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Priority> rt = cq.from(Priority.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
