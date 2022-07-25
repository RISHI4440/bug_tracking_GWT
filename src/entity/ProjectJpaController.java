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
public class ProjectJpaController implements Serializable {

    public ProjectJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Project project) {
        if (project.getBugCollection() == null) {
            project.setBugCollection(new ArrayList<Bug>());
        }
        if (project.getProjectmemberCollection() == null) {
            project.setProjectmemberCollection(new ArrayList<Projectmember>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Login loginid = project.getLoginid();
            if (loginid != null) {
                loginid = em.getReference(loginid.getClass(), loginid.getLoginid());
                project.setLoginid(loginid);
            }
            Collection<Bug> attachedBugCollection = new ArrayList<Bug>();
            for (Bug bugCollectionBugToAttach : project.getBugCollection()) {
                bugCollectionBugToAttach = em.getReference(bugCollectionBugToAttach.getClass(), bugCollectionBugToAttach.getBugid());
                attachedBugCollection.add(bugCollectionBugToAttach);
            }
            project.setBugCollection(attachedBugCollection);
            Collection<Projectmember> attachedProjectmemberCollection = new ArrayList<Projectmember>();
            for (Projectmember projectmemberCollectionProjectmemberToAttach : project.getProjectmemberCollection()) {
                projectmemberCollectionProjectmemberToAttach = em.getReference(projectmemberCollectionProjectmemberToAttach.getClass(), projectmemberCollectionProjectmemberToAttach.getProjectmid());
                attachedProjectmemberCollection.add(projectmemberCollectionProjectmemberToAttach);
            }
            project.setProjectmemberCollection(attachedProjectmemberCollection);
            em.persist(project);
            if (loginid != null) {
                loginid.getProjectCollection().add(project);
                loginid = em.merge(loginid);
            }
            for (Bug bugCollectionBug : project.getBugCollection()) {
                Project oldProjectidOfBugCollectionBug = bugCollectionBug.getProjectid();
                bugCollectionBug.setProjectid(project);
                bugCollectionBug = em.merge(bugCollectionBug);
                if (oldProjectidOfBugCollectionBug != null) {
                    oldProjectidOfBugCollectionBug.getBugCollection().remove(bugCollectionBug);
                    oldProjectidOfBugCollectionBug = em.merge(oldProjectidOfBugCollectionBug);
                }
            }
            for (Projectmember projectmemberCollectionProjectmember : project.getProjectmemberCollection()) {
                Project oldProjectidOfProjectmemberCollectionProjectmember = projectmemberCollectionProjectmember.getProjectid();
                projectmemberCollectionProjectmember.setProjectid(project);
                projectmemberCollectionProjectmember = em.merge(projectmemberCollectionProjectmember);
                if (oldProjectidOfProjectmemberCollectionProjectmember != null) {
                    oldProjectidOfProjectmemberCollectionProjectmember.getProjectmemberCollection().remove(projectmemberCollectionProjectmember);
                    oldProjectidOfProjectmemberCollectionProjectmember = em.merge(oldProjectidOfProjectmemberCollectionProjectmember);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Project project) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project persistentProject = em.find(Project.class, project.getProjectid());
            Login loginidOld = persistentProject.getLoginid();
            Login loginidNew = project.getLoginid();
            Collection<Bug> bugCollectionOld = persistentProject.getBugCollection();
            Collection<Bug> bugCollectionNew = project.getBugCollection();
            Collection<Projectmember> projectmemberCollectionOld = persistentProject.getProjectmemberCollection();
            Collection<Projectmember> projectmemberCollectionNew = project.getProjectmemberCollection();
            List<String> illegalOrphanMessages = null;
            for (Bug bugCollectionOldBug : bugCollectionOld) {
                if (!bugCollectionNew.contains(bugCollectionOldBug)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bug " + bugCollectionOldBug + " since its projectid field is not nullable.");
                }
            }
            for (Projectmember projectmemberCollectionOldProjectmember : projectmemberCollectionOld) {
                if (!projectmemberCollectionNew.contains(projectmemberCollectionOldProjectmember)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Projectmember " + projectmemberCollectionOldProjectmember + " since its projectid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (loginidNew != null) {
                loginidNew = em.getReference(loginidNew.getClass(), loginidNew.getLoginid());
                project.setLoginid(loginidNew);
            }
            Collection<Bug> attachedBugCollectionNew = new ArrayList<Bug>();
            for (Bug bugCollectionNewBugToAttach : bugCollectionNew) {
                bugCollectionNewBugToAttach = em.getReference(bugCollectionNewBugToAttach.getClass(), bugCollectionNewBugToAttach.getBugid());
                attachedBugCollectionNew.add(bugCollectionNewBugToAttach);
            }
            bugCollectionNew = attachedBugCollectionNew;
            project.setBugCollection(bugCollectionNew);
            Collection<Projectmember> attachedProjectmemberCollectionNew = new ArrayList<Projectmember>();
            for (Projectmember projectmemberCollectionNewProjectmemberToAttach : projectmemberCollectionNew) {
                projectmemberCollectionNewProjectmemberToAttach = em.getReference(projectmemberCollectionNewProjectmemberToAttach.getClass(), projectmemberCollectionNewProjectmemberToAttach.getProjectmid());
                attachedProjectmemberCollectionNew.add(projectmemberCollectionNewProjectmemberToAttach);
            }
            projectmemberCollectionNew = attachedProjectmemberCollectionNew;
            project.setProjectmemberCollection(projectmemberCollectionNew);
            project = em.merge(project);
            if (loginidOld != null && !loginidOld.equals(loginidNew)) {
                loginidOld.getProjectCollection().remove(project);
                loginidOld = em.merge(loginidOld);
            }
            if (loginidNew != null && !loginidNew.equals(loginidOld)) {
                loginidNew.getProjectCollection().add(project);
                loginidNew = em.merge(loginidNew);
            }
            for (Bug bugCollectionNewBug : bugCollectionNew) {
                if (!bugCollectionOld.contains(bugCollectionNewBug)) {
                    Project oldProjectidOfBugCollectionNewBug = bugCollectionNewBug.getProjectid();
                    bugCollectionNewBug.setProjectid(project);
                    bugCollectionNewBug = em.merge(bugCollectionNewBug);
                    if (oldProjectidOfBugCollectionNewBug != null && !oldProjectidOfBugCollectionNewBug.equals(project)) {
                        oldProjectidOfBugCollectionNewBug.getBugCollection().remove(bugCollectionNewBug);
                        oldProjectidOfBugCollectionNewBug = em.merge(oldProjectidOfBugCollectionNewBug);
                    }
                }
            }
            for (Projectmember projectmemberCollectionNewProjectmember : projectmemberCollectionNew) {
                if (!projectmemberCollectionOld.contains(projectmemberCollectionNewProjectmember)) {
                    Project oldProjectidOfProjectmemberCollectionNewProjectmember = projectmemberCollectionNewProjectmember.getProjectid();
                    projectmemberCollectionNewProjectmember.setProjectid(project);
                    projectmemberCollectionNewProjectmember = em.merge(projectmemberCollectionNewProjectmember);
                    if (oldProjectidOfProjectmemberCollectionNewProjectmember != null && !oldProjectidOfProjectmemberCollectionNewProjectmember.equals(project)) {
                        oldProjectidOfProjectmemberCollectionNewProjectmember.getProjectmemberCollection().remove(projectmemberCollectionNewProjectmember);
                        oldProjectidOfProjectmemberCollectionNewProjectmember = em.merge(oldProjectidOfProjectmemberCollectionNewProjectmember);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = project.getProjectid();
                if (findProject(id) == null) {
                    throw new NonexistentEntityException("The project with id " + id + " no longer exists.");
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
            Project project;
            try {
                project = em.getReference(Project.class, id);
                project.getProjectid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The project with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Bug> bugCollectionOrphanCheck = project.getBugCollection();
            for (Bug bugCollectionOrphanCheckBug : bugCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the Bug " + bugCollectionOrphanCheckBug + " in its bugCollection field has a non-nullable projectid field.");
            }
            Collection<Projectmember> projectmemberCollectionOrphanCheck = project.getProjectmemberCollection();
            for (Projectmember projectmemberCollectionOrphanCheckProjectmember : projectmemberCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the Projectmember " + projectmemberCollectionOrphanCheckProjectmember + " in its projectmemberCollection field has a non-nullable projectid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Login loginid = project.getLoginid();
            if (loginid != null) {
                loginid.getProjectCollection().remove(project);
                loginid = em.merge(loginid);
            }
            em.remove(project);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Project> findProjectEntities() {
        return findProjectEntities(true, -1, -1);
    }

    public List<Project> findProjectEntities(int maxResults, int firstResult) {
        return findProjectEntities(false, maxResults, firstResult);
    }

    private List<Project> findProjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Project.class));
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

    public Project findProject(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Project.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Project> rt = cq.from(Project.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
