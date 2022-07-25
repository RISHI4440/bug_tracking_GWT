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
public class BugJpaController implements Serializable {

    public BugJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bug bug) {
        if (bug.getBugassignedCollection() == null) {
            bug.setBugassignedCollection(new ArrayList<Bugassigned>());
        }
        if (bug.getSolutionCollection() == null) {
            bug.setSolutionCollection(new ArrayList<Solution>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project projectid = bug.getProjectid();
            if (projectid != null) {
                projectid = em.getReference(projectid.getClass(), projectid.getProjectid());
                bug.setProjectid(projectid);
            }
            Priority priorityid = bug.getPriorityid();
            if (priorityid != null) {
                priorityid = em.getReference(priorityid.getClass(), priorityid.getPriorityid());
                bug.setPriorityid(priorityid);
            }
            Login loginid = bug.getLoginid();
            if (loginid != null) {
                loginid = em.getReference(loginid.getClass(), loginid.getLoginid());
                bug.setLoginid(loginid);
            }
            Collection<Bugassigned> attachedBugassignedCollection = new ArrayList<Bugassigned>();
            for (Bugassigned bugassignedCollectionBugassignedToAttach : bug.getBugassignedCollection()) {
                bugassignedCollectionBugassignedToAttach = em.getReference(bugassignedCollectionBugassignedToAttach.getClass(), bugassignedCollectionBugassignedToAttach.getBugaid());
                attachedBugassignedCollection.add(bugassignedCollectionBugassignedToAttach);
            }
            bug.setBugassignedCollection(attachedBugassignedCollection);
            Collection<Solution> attachedSolutionCollection = new ArrayList<Solution>();
            for (Solution solutionCollectionSolutionToAttach : bug.getSolutionCollection()) {
                solutionCollectionSolutionToAttach = em.getReference(solutionCollectionSolutionToAttach.getClass(), solutionCollectionSolutionToAttach.getSolutionid());
                attachedSolutionCollection.add(solutionCollectionSolutionToAttach);
            }
            bug.setSolutionCollection(attachedSolutionCollection);
            em.persist(bug);
            if (projectid != null) {
                projectid.getBugCollection().add(bug);
                projectid = em.merge(projectid);
            }
            if (priorityid != null) {
                priorityid.getBugCollection().add(bug);
                priorityid = em.merge(priorityid);
            }
            if (loginid != null) {
                loginid.getBugCollection().add(bug);
                loginid = em.merge(loginid);
            }
            for (Bugassigned bugassignedCollectionBugassigned : bug.getBugassignedCollection()) {
                Bug oldBugidOfBugassignedCollectionBugassigned = bugassignedCollectionBugassigned.getBugid();
                bugassignedCollectionBugassigned.setBugid(bug);
                bugassignedCollectionBugassigned = em.merge(bugassignedCollectionBugassigned);
                if (oldBugidOfBugassignedCollectionBugassigned != null) {
                    oldBugidOfBugassignedCollectionBugassigned.getBugassignedCollection().remove(bugassignedCollectionBugassigned);
                    oldBugidOfBugassignedCollectionBugassigned = em.merge(oldBugidOfBugassignedCollectionBugassigned);
                }
            }
            for (Solution solutionCollectionSolution : bug.getSolutionCollection()) {
                Bug oldBugidOfSolutionCollectionSolution = solutionCollectionSolution.getBugid();
                solutionCollectionSolution.setBugid(bug);
                solutionCollectionSolution = em.merge(solutionCollectionSolution);
                if (oldBugidOfSolutionCollectionSolution != null) {
                    oldBugidOfSolutionCollectionSolution.getSolutionCollection().remove(solutionCollectionSolution);
                    oldBugidOfSolutionCollectionSolution = em.merge(oldBugidOfSolutionCollectionSolution);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bug bug) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bug persistentBug = em.find(Bug.class, bug.getBugid());
            Project projectidOld = persistentBug.getProjectid();
            Project projectidNew = bug.getProjectid();
            Priority priorityidOld = persistentBug.getPriorityid();
            Priority priorityidNew = bug.getPriorityid();
            Login loginidOld = persistentBug.getLoginid();
            Login loginidNew = bug.getLoginid();
            Collection<Bugassigned> bugassignedCollectionOld = persistentBug.getBugassignedCollection();
            Collection<Bugassigned> bugassignedCollectionNew = bug.getBugassignedCollection();
            Collection<Solution> solutionCollectionOld = persistentBug.getSolutionCollection();
            Collection<Solution> solutionCollectionNew = bug.getSolutionCollection();
            List<String> illegalOrphanMessages = null;
            for (Bugassigned bugassignedCollectionOldBugassigned : bugassignedCollectionOld) {
                if (!bugassignedCollectionNew.contains(bugassignedCollectionOldBugassigned)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bugassigned " + bugassignedCollectionOldBugassigned + " since its bugid field is not nullable.");
                }
            }
            for (Solution solutionCollectionOldSolution : solutionCollectionOld) {
                if (!solutionCollectionNew.contains(solutionCollectionOldSolution)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solution " + solutionCollectionOldSolution + " since its bugid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (projectidNew != null) {
                projectidNew = em.getReference(projectidNew.getClass(), projectidNew.getProjectid());
                bug.setProjectid(projectidNew);
            }
            if (priorityidNew != null) {
                priorityidNew = em.getReference(priorityidNew.getClass(), priorityidNew.getPriorityid());
                bug.setPriorityid(priorityidNew);
            }
            if (loginidNew != null) {
                loginidNew = em.getReference(loginidNew.getClass(), loginidNew.getLoginid());
                bug.setLoginid(loginidNew);
            }
            Collection<Bugassigned> attachedBugassignedCollectionNew = new ArrayList<Bugassigned>();
            for (Bugassigned bugassignedCollectionNewBugassignedToAttach : bugassignedCollectionNew) {
                bugassignedCollectionNewBugassignedToAttach = em.getReference(bugassignedCollectionNewBugassignedToAttach.getClass(), bugassignedCollectionNewBugassignedToAttach.getBugaid());
                attachedBugassignedCollectionNew.add(bugassignedCollectionNewBugassignedToAttach);
            }
            bugassignedCollectionNew = attachedBugassignedCollectionNew;
            bug.setBugassignedCollection(bugassignedCollectionNew);
            Collection<Solution> attachedSolutionCollectionNew = new ArrayList<Solution>();
            for (Solution solutionCollectionNewSolutionToAttach : solutionCollectionNew) {
                solutionCollectionNewSolutionToAttach = em.getReference(solutionCollectionNewSolutionToAttach.getClass(), solutionCollectionNewSolutionToAttach.getSolutionid());
                attachedSolutionCollectionNew.add(solutionCollectionNewSolutionToAttach);
            }
            solutionCollectionNew = attachedSolutionCollectionNew;
            bug.setSolutionCollection(solutionCollectionNew);
            bug = em.merge(bug);
            if (projectidOld != null && !projectidOld.equals(projectidNew)) {
                projectidOld.getBugCollection().remove(bug);
                projectidOld = em.merge(projectidOld);
            }
            if (projectidNew != null && !projectidNew.equals(projectidOld)) {
                projectidNew.getBugCollection().add(bug);
                projectidNew = em.merge(projectidNew);
            }
            if (priorityidOld != null && !priorityidOld.equals(priorityidNew)) {
                priorityidOld.getBugCollection().remove(bug);
                priorityidOld = em.merge(priorityidOld);
            }
            if (priorityidNew != null && !priorityidNew.equals(priorityidOld)) {
                priorityidNew.getBugCollection().add(bug);
                priorityidNew = em.merge(priorityidNew);
            }
            if (loginidOld != null && !loginidOld.equals(loginidNew)) {
                loginidOld.getBugCollection().remove(bug);
                loginidOld = em.merge(loginidOld);
            }
            if (loginidNew != null && !loginidNew.equals(loginidOld)) {
                loginidNew.getBugCollection().add(bug);
                loginidNew = em.merge(loginidNew);
            }
            for (Bugassigned bugassignedCollectionNewBugassigned : bugassignedCollectionNew) {
                if (!bugassignedCollectionOld.contains(bugassignedCollectionNewBugassigned)) {
                    Bug oldBugidOfBugassignedCollectionNewBugassigned = bugassignedCollectionNewBugassigned.getBugid();
                    bugassignedCollectionNewBugassigned.setBugid(bug);
                    bugassignedCollectionNewBugassigned = em.merge(bugassignedCollectionNewBugassigned);
                    if (oldBugidOfBugassignedCollectionNewBugassigned != null && !oldBugidOfBugassignedCollectionNewBugassigned.equals(bug)) {
                        oldBugidOfBugassignedCollectionNewBugassigned.getBugassignedCollection().remove(bugassignedCollectionNewBugassigned);
                        oldBugidOfBugassignedCollectionNewBugassigned = em.merge(oldBugidOfBugassignedCollectionNewBugassigned);
                    }
                }
            }
            for (Solution solutionCollectionNewSolution : solutionCollectionNew) {
                if (!solutionCollectionOld.contains(solutionCollectionNewSolution)) {
                    Bug oldBugidOfSolutionCollectionNewSolution = solutionCollectionNewSolution.getBugid();
                    solutionCollectionNewSolution.setBugid(bug);
                    solutionCollectionNewSolution = em.merge(solutionCollectionNewSolution);
                    if (oldBugidOfSolutionCollectionNewSolution != null && !oldBugidOfSolutionCollectionNewSolution.equals(bug)) {
                        oldBugidOfSolutionCollectionNewSolution.getSolutionCollection().remove(solutionCollectionNewSolution);
                        oldBugidOfSolutionCollectionNewSolution = em.merge(oldBugidOfSolutionCollectionNewSolution);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bug.getBugid();
                if (findBug(id) == null) {
                    throw new NonexistentEntityException("The bug with id " + id + " no longer exists.");
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
            Bug bug;
            try {
                bug = em.getReference(Bug.class, id);
                bug.getBugid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bug with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Bugassigned> bugassignedCollectionOrphanCheck = bug.getBugassignedCollection();
            for (Bugassigned bugassignedCollectionOrphanCheckBugassigned : bugassignedCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bug (" + bug + ") cannot be destroyed since the Bugassigned " + bugassignedCollectionOrphanCheckBugassigned + " in its bugassignedCollection field has a non-nullable bugid field.");
            }
            Collection<Solution> solutionCollectionOrphanCheck = bug.getSolutionCollection();
            for (Solution solutionCollectionOrphanCheckSolution : solutionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bug (" + bug + ") cannot be destroyed since the Solution " + solutionCollectionOrphanCheckSolution + " in its solutionCollection field has a non-nullable bugid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Project projectid = bug.getProjectid();
            if (projectid != null) {
                projectid.getBugCollection().remove(bug);
                projectid = em.merge(projectid);
            }
            Priority priorityid = bug.getPriorityid();
            if (priorityid != null) {
                priorityid.getBugCollection().remove(bug);
                priorityid = em.merge(priorityid);
            }
            Login loginid = bug.getLoginid();
            if (loginid != null) {
                loginid.getBugCollection().remove(bug);
                loginid = em.merge(loginid);
            }
            em.remove(bug);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bug> findBugEntities() {
        return findBugEntities(true, -1, -1);
    }

    public List<Bug> findBugEntities(int maxResults, int firstResult) {
        return findBugEntities(false, maxResults, firstResult);
    }

    private List<Bug> findBugEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bug.class));
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

    public Bug findBug(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bug.class, id);
        } finally {
            em.close();
        }
    }

    public int getBugCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bug> rt = cq.from(Bug.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
