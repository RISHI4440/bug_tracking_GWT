/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.exceptions.IllegalOrphanException;
import entity.exceptions.NonexistentEntityException;
import entity.exceptions.PreexistingEntityException;
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
public class LoginJpaController implements Serializable {

    public LoginJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Login login) throws PreexistingEntityException, Exception {
        if (login.getBugassignedCollection() == null) {
            login.setBugassignedCollection(new ArrayList<Bugassigned>());
        }
        if (login.getBugCollection() == null) {
            login.setBugCollection(new ArrayList<Bug>());
        }
        if (login.getProjectmemberCollection() == null) {
            login.setProjectmemberCollection(new ArrayList<Projectmember>());
        }
        if (login.getLoginauditCollection() == null) {
            login.setLoginauditCollection(new ArrayList<Loginaudit>());
        }
        if (login.getProjectCollection() == null) {
            login.setProjectCollection(new ArrayList<Project>());
        }
        if (login.getSolutionCollection() == null) {
            login.setSolutionCollection(new ArrayList<Solution>());
        }
        if (login.getLoginprofileCollection() == null) {
            login.setLoginprofileCollection(new ArrayList<Loginprofile>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Questionbase questionid = login.getQuestionid();
            if (questionid != null) {
                questionid = em.getReference(questionid.getClass(), questionid.getQuestionid());
                login.setQuestionid(questionid);
            }
            Collection<Bugassigned> attachedBugassignedCollection = new ArrayList<Bugassigned>();
            for (Bugassigned bugassignedCollectionBugassignedToAttach : login.getBugassignedCollection()) {
                bugassignedCollectionBugassignedToAttach = em.getReference(bugassignedCollectionBugassignedToAttach.getClass(), bugassignedCollectionBugassignedToAttach.getBugaid());
                attachedBugassignedCollection.add(bugassignedCollectionBugassignedToAttach);
            }
            login.setBugassignedCollection(attachedBugassignedCollection);
            Collection<Bug> attachedBugCollection = new ArrayList<Bug>();
            for (Bug bugCollectionBugToAttach : login.getBugCollection()) {
                bugCollectionBugToAttach = em.getReference(bugCollectionBugToAttach.getClass(), bugCollectionBugToAttach.getBugid());
                attachedBugCollection.add(bugCollectionBugToAttach);
            }
            login.setBugCollection(attachedBugCollection);
            Collection<Projectmember> attachedProjectmemberCollection = new ArrayList<Projectmember>();
            for (Projectmember projectmemberCollectionProjectmemberToAttach : login.getProjectmemberCollection()) {
                projectmemberCollectionProjectmemberToAttach = em.getReference(projectmemberCollectionProjectmemberToAttach.getClass(), projectmemberCollectionProjectmemberToAttach.getProjectmid());
                attachedProjectmemberCollection.add(projectmemberCollectionProjectmemberToAttach);
            }
            login.setProjectmemberCollection(attachedProjectmemberCollection);
            Collection<Loginaudit> attachedLoginauditCollection = new ArrayList<Loginaudit>();
            for (Loginaudit loginauditCollectionLoginauditToAttach : login.getLoginauditCollection()) {
                loginauditCollectionLoginauditToAttach = em.getReference(loginauditCollectionLoginauditToAttach.getClass(), loginauditCollectionLoginauditToAttach.getLoginaid());
                attachedLoginauditCollection.add(loginauditCollectionLoginauditToAttach);
            }
            login.setLoginauditCollection(attachedLoginauditCollection);
            Collection<Project> attachedProjectCollection = new ArrayList<Project>();
            for (Project projectCollectionProjectToAttach : login.getProjectCollection()) {
                projectCollectionProjectToAttach = em.getReference(projectCollectionProjectToAttach.getClass(), projectCollectionProjectToAttach.getProjectid());
                attachedProjectCollection.add(projectCollectionProjectToAttach);
            }
            login.setProjectCollection(attachedProjectCollection);
            Collection<Solution> attachedSolutionCollection = new ArrayList<Solution>();
            for (Solution solutionCollectionSolutionToAttach : login.getSolutionCollection()) {
                solutionCollectionSolutionToAttach = em.getReference(solutionCollectionSolutionToAttach.getClass(), solutionCollectionSolutionToAttach.getSolutionid());
                attachedSolutionCollection.add(solutionCollectionSolutionToAttach);
            }
            login.setSolutionCollection(attachedSolutionCollection);
            Collection<Loginprofile> attachedLoginprofileCollection = new ArrayList<Loginprofile>();
            for (Loginprofile loginprofileCollectionLoginprofileToAttach : login.getLoginprofileCollection()) {
                loginprofileCollectionLoginprofileToAttach = em.getReference(loginprofileCollectionLoginprofileToAttach.getClass(), loginprofileCollectionLoginprofileToAttach.getLoginpid());
                attachedLoginprofileCollection.add(loginprofileCollectionLoginprofileToAttach);
            }
            login.setLoginprofileCollection(attachedLoginprofileCollection);
            em.persist(login);
            if (questionid != null) {
                questionid.getLoginCollection().add(login);
                questionid = em.merge(questionid);
            }
            for (Bugassigned bugassignedCollectionBugassigned : login.getBugassignedCollection()) {
                Login oldLoginidOfBugassignedCollectionBugassigned = bugassignedCollectionBugassigned.getLoginid();
                bugassignedCollectionBugassigned.setLoginid(login);
                bugassignedCollectionBugassigned = em.merge(bugassignedCollectionBugassigned);
                if (oldLoginidOfBugassignedCollectionBugassigned != null) {
                    oldLoginidOfBugassignedCollectionBugassigned.getBugassignedCollection().remove(bugassignedCollectionBugassigned);
                    oldLoginidOfBugassignedCollectionBugassigned = em.merge(oldLoginidOfBugassignedCollectionBugassigned);
                }
            }
            for (Bug bugCollectionBug : login.getBugCollection()) {
                Login oldLoginidOfBugCollectionBug = bugCollectionBug.getLoginid();
                bugCollectionBug.setLoginid(login);
                bugCollectionBug = em.merge(bugCollectionBug);
                if (oldLoginidOfBugCollectionBug != null) {
                    oldLoginidOfBugCollectionBug.getBugCollection().remove(bugCollectionBug);
                    oldLoginidOfBugCollectionBug = em.merge(oldLoginidOfBugCollectionBug);
                }
            }
            for (Projectmember projectmemberCollectionProjectmember : login.getProjectmemberCollection()) {
                Login oldLoginidOfProjectmemberCollectionProjectmember = projectmemberCollectionProjectmember.getLoginid();
                projectmemberCollectionProjectmember.setLoginid(login);
                projectmemberCollectionProjectmember = em.merge(projectmemberCollectionProjectmember);
                if (oldLoginidOfProjectmemberCollectionProjectmember != null) {
                    oldLoginidOfProjectmemberCollectionProjectmember.getProjectmemberCollection().remove(projectmemberCollectionProjectmember);
                    oldLoginidOfProjectmemberCollectionProjectmember = em.merge(oldLoginidOfProjectmemberCollectionProjectmember);
                }
            }
            for (Loginaudit loginauditCollectionLoginaudit : login.getLoginauditCollection()) {
                Login oldLoginidOfLoginauditCollectionLoginaudit = loginauditCollectionLoginaudit.getLoginid();
                loginauditCollectionLoginaudit.setLoginid(login);
                loginauditCollectionLoginaudit = em.merge(loginauditCollectionLoginaudit);
                if (oldLoginidOfLoginauditCollectionLoginaudit != null) {
                    oldLoginidOfLoginauditCollectionLoginaudit.getLoginauditCollection().remove(loginauditCollectionLoginaudit);
                    oldLoginidOfLoginauditCollectionLoginaudit = em.merge(oldLoginidOfLoginauditCollectionLoginaudit);
                }
            }
            for (Project projectCollectionProject : login.getProjectCollection()) {
                Login oldLoginidOfProjectCollectionProject = projectCollectionProject.getLoginid();
                projectCollectionProject.setLoginid(login);
                projectCollectionProject = em.merge(projectCollectionProject);
                if (oldLoginidOfProjectCollectionProject != null) {
                    oldLoginidOfProjectCollectionProject.getProjectCollection().remove(projectCollectionProject);
                    oldLoginidOfProjectCollectionProject = em.merge(oldLoginidOfProjectCollectionProject);
                }
            }
            for (Solution solutionCollectionSolution : login.getSolutionCollection()) {
                Login oldLoginidOfSolutionCollectionSolution = solutionCollectionSolution.getLoginid();
                solutionCollectionSolution.setLoginid(login);
                solutionCollectionSolution = em.merge(solutionCollectionSolution);
                if (oldLoginidOfSolutionCollectionSolution != null) {
                    oldLoginidOfSolutionCollectionSolution.getSolutionCollection().remove(solutionCollectionSolution);
                    oldLoginidOfSolutionCollectionSolution = em.merge(oldLoginidOfSolutionCollectionSolution);
                }
            }
            for (Loginprofile loginprofileCollectionLoginprofile : login.getLoginprofileCollection()) {
                Login oldLoginidOfLoginprofileCollectionLoginprofile = loginprofileCollectionLoginprofile.getLoginid();
                loginprofileCollectionLoginprofile.setLoginid(login);
                loginprofileCollectionLoginprofile = em.merge(loginprofileCollectionLoginprofile);
                if (oldLoginidOfLoginprofileCollectionLoginprofile != null) {
                    oldLoginidOfLoginprofileCollectionLoginprofile.getLoginprofileCollection().remove(loginprofileCollectionLoginprofile);
                    oldLoginidOfLoginprofileCollectionLoginprofile = em.merge(oldLoginidOfLoginprofileCollectionLoginprofile);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLogin(login.getLoginid()) != null) {
                throw new PreexistingEntityException("Login " + login + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Login login) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Login persistentLogin = em.find(Login.class, login.getLoginid());
            Questionbase questionidOld = persistentLogin.getQuestionid();
            Questionbase questionidNew = login.getQuestionid();
            Collection<Bugassigned> bugassignedCollectionOld = persistentLogin.getBugassignedCollection();
            Collection<Bugassigned> bugassignedCollectionNew = login.getBugassignedCollection();
            Collection<Bug> bugCollectionOld = persistentLogin.getBugCollection();
            Collection<Bug> bugCollectionNew = login.getBugCollection();
            Collection<Projectmember> projectmemberCollectionOld = persistentLogin.getProjectmemberCollection();
            Collection<Projectmember> projectmemberCollectionNew = login.getProjectmemberCollection();
            Collection<Loginaudit> loginauditCollectionOld = persistentLogin.getLoginauditCollection();
            Collection<Loginaudit> loginauditCollectionNew = login.getLoginauditCollection();
            Collection<Project> projectCollectionOld = persistentLogin.getProjectCollection();
            Collection<Project> projectCollectionNew = login.getProjectCollection();
            Collection<Solution> solutionCollectionOld = persistentLogin.getSolutionCollection();
            Collection<Solution> solutionCollectionNew = login.getSolutionCollection();
            Collection<Loginprofile> loginprofileCollectionOld = persistentLogin.getLoginprofileCollection();
            Collection<Loginprofile> loginprofileCollectionNew = login.getLoginprofileCollection();
            List<String> illegalOrphanMessages = null;
            for (Bugassigned bugassignedCollectionOldBugassigned : bugassignedCollectionOld) {
                if (!bugassignedCollectionNew.contains(bugassignedCollectionOldBugassigned)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bugassigned " + bugassignedCollectionOldBugassigned + " since its loginid field is not nullable.");
                }
            }
            for (Bug bugCollectionOldBug : bugCollectionOld) {
                if (!bugCollectionNew.contains(bugCollectionOldBug)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bug " + bugCollectionOldBug + " since its loginid field is not nullable.");
                }
            }
            for (Projectmember projectmemberCollectionOldProjectmember : projectmemberCollectionOld) {
                if (!projectmemberCollectionNew.contains(projectmemberCollectionOldProjectmember)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Projectmember " + projectmemberCollectionOldProjectmember + " since its loginid field is not nullable.");
                }
            }
            for (Loginaudit loginauditCollectionOldLoginaudit : loginauditCollectionOld) {
                if (!loginauditCollectionNew.contains(loginauditCollectionOldLoginaudit)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Loginaudit " + loginauditCollectionOldLoginaudit + " since its loginid field is not nullable.");
                }
            }
            for (Project projectCollectionOldProject : projectCollectionOld) {
                if (!projectCollectionNew.contains(projectCollectionOldProject)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Project " + projectCollectionOldProject + " since its loginid field is not nullable.");
                }
            }
            for (Solution solutionCollectionOldSolution : solutionCollectionOld) {
                if (!solutionCollectionNew.contains(solutionCollectionOldSolution)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solution " + solutionCollectionOldSolution + " since its loginid field is not nullable.");
                }
            }
            for (Loginprofile loginprofileCollectionOldLoginprofile : loginprofileCollectionOld) {
                if (!loginprofileCollectionNew.contains(loginprofileCollectionOldLoginprofile)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Loginprofile " + loginprofileCollectionOldLoginprofile + " since its loginid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (questionidNew != null) {
                questionidNew = em.getReference(questionidNew.getClass(), questionidNew.getQuestionid());
                login.setQuestionid(questionidNew);
            }
            Collection<Bugassigned> attachedBugassignedCollectionNew = new ArrayList<Bugassigned>();
            for (Bugassigned bugassignedCollectionNewBugassignedToAttach : bugassignedCollectionNew) {
                bugassignedCollectionNewBugassignedToAttach = em.getReference(bugassignedCollectionNewBugassignedToAttach.getClass(), bugassignedCollectionNewBugassignedToAttach.getBugaid());
                attachedBugassignedCollectionNew.add(bugassignedCollectionNewBugassignedToAttach);
            }
            bugassignedCollectionNew = attachedBugassignedCollectionNew;
            login.setBugassignedCollection(bugassignedCollectionNew);
            Collection<Bug> attachedBugCollectionNew = new ArrayList<Bug>();
            for (Bug bugCollectionNewBugToAttach : bugCollectionNew) {
                bugCollectionNewBugToAttach = em.getReference(bugCollectionNewBugToAttach.getClass(), bugCollectionNewBugToAttach.getBugid());
                attachedBugCollectionNew.add(bugCollectionNewBugToAttach);
            }
            bugCollectionNew = attachedBugCollectionNew;
            login.setBugCollection(bugCollectionNew);
            Collection<Projectmember> attachedProjectmemberCollectionNew = new ArrayList<Projectmember>();
            for (Projectmember projectmemberCollectionNewProjectmemberToAttach : projectmemberCollectionNew) {
                projectmemberCollectionNewProjectmemberToAttach = em.getReference(projectmemberCollectionNewProjectmemberToAttach.getClass(), projectmemberCollectionNewProjectmemberToAttach.getProjectmid());
                attachedProjectmemberCollectionNew.add(projectmemberCollectionNewProjectmemberToAttach);
            }
            projectmemberCollectionNew = attachedProjectmemberCollectionNew;
            login.setProjectmemberCollection(projectmemberCollectionNew);
            Collection<Loginaudit> attachedLoginauditCollectionNew = new ArrayList<Loginaudit>();
            for (Loginaudit loginauditCollectionNewLoginauditToAttach : loginauditCollectionNew) {
                loginauditCollectionNewLoginauditToAttach = em.getReference(loginauditCollectionNewLoginauditToAttach.getClass(), loginauditCollectionNewLoginauditToAttach.getLoginaid());
                attachedLoginauditCollectionNew.add(loginauditCollectionNewLoginauditToAttach);
            }
            loginauditCollectionNew = attachedLoginauditCollectionNew;
            login.setLoginauditCollection(loginauditCollectionNew);
            Collection<Project> attachedProjectCollectionNew = new ArrayList<Project>();
            for (Project projectCollectionNewProjectToAttach : projectCollectionNew) {
                projectCollectionNewProjectToAttach = em.getReference(projectCollectionNewProjectToAttach.getClass(), projectCollectionNewProjectToAttach.getProjectid());
                attachedProjectCollectionNew.add(projectCollectionNewProjectToAttach);
            }
            projectCollectionNew = attachedProjectCollectionNew;
            login.setProjectCollection(projectCollectionNew);
            Collection<Solution> attachedSolutionCollectionNew = new ArrayList<Solution>();
            for (Solution solutionCollectionNewSolutionToAttach : solutionCollectionNew) {
                solutionCollectionNewSolutionToAttach = em.getReference(solutionCollectionNewSolutionToAttach.getClass(), solutionCollectionNewSolutionToAttach.getSolutionid());
                attachedSolutionCollectionNew.add(solutionCollectionNewSolutionToAttach);
            }
            solutionCollectionNew = attachedSolutionCollectionNew;
            login.setSolutionCollection(solutionCollectionNew);
            Collection<Loginprofile> attachedLoginprofileCollectionNew = new ArrayList<Loginprofile>();
            for (Loginprofile loginprofileCollectionNewLoginprofileToAttach : loginprofileCollectionNew) {
                loginprofileCollectionNewLoginprofileToAttach = em.getReference(loginprofileCollectionNewLoginprofileToAttach.getClass(), loginprofileCollectionNewLoginprofileToAttach.getLoginpid());
                attachedLoginprofileCollectionNew.add(loginprofileCollectionNewLoginprofileToAttach);
            }
            loginprofileCollectionNew = attachedLoginprofileCollectionNew;
            login.setLoginprofileCollection(loginprofileCollectionNew);
            login = em.merge(login);
            if (questionidOld != null && !questionidOld.equals(questionidNew)) {
                questionidOld.getLoginCollection().remove(login);
                questionidOld = em.merge(questionidOld);
            }
            if (questionidNew != null && !questionidNew.equals(questionidOld)) {
                questionidNew.getLoginCollection().add(login);
                questionidNew = em.merge(questionidNew);
            }
            for (Bugassigned bugassignedCollectionNewBugassigned : bugassignedCollectionNew) {
                if (!bugassignedCollectionOld.contains(bugassignedCollectionNewBugassigned)) {
                    Login oldLoginidOfBugassignedCollectionNewBugassigned = bugassignedCollectionNewBugassigned.getLoginid();
                    bugassignedCollectionNewBugassigned.setLoginid(login);
                    bugassignedCollectionNewBugassigned = em.merge(bugassignedCollectionNewBugassigned);
                    if (oldLoginidOfBugassignedCollectionNewBugassigned != null && !oldLoginidOfBugassignedCollectionNewBugassigned.equals(login)) {
                        oldLoginidOfBugassignedCollectionNewBugassigned.getBugassignedCollection().remove(bugassignedCollectionNewBugassigned);
                        oldLoginidOfBugassignedCollectionNewBugassigned = em.merge(oldLoginidOfBugassignedCollectionNewBugassigned);
                    }
                }
            }
            for (Bug bugCollectionNewBug : bugCollectionNew) {
                if (!bugCollectionOld.contains(bugCollectionNewBug)) {
                    Login oldLoginidOfBugCollectionNewBug = bugCollectionNewBug.getLoginid();
                    bugCollectionNewBug.setLoginid(login);
                    bugCollectionNewBug = em.merge(bugCollectionNewBug);
                    if (oldLoginidOfBugCollectionNewBug != null && !oldLoginidOfBugCollectionNewBug.equals(login)) {
                        oldLoginidOfBugCollectionNewBug.getBugCollection().remove(bugCollectionNewBug);
                        oldLoginidOfBugCollectionNewBug = em.merge(oldLoginidOfBugCollectionNewBug);
                    }
                }
            }
            for (Projectmember projectmemberCollectionNewProjectmember : projectmemberCollectionNew) {
                if (!projectmemberCollectionOld.contains(projectmemberCollectionNewProjectmember)) {
                    Login oldLoginidOfProjectmemberCollectionNewProjectmember = projectmemberCollectionNewProjectmember.getLoginid();
                    projectmemberCollectionNewProjectmember.setLoginid(login);
                    projectmemberCollectionNewProjectmember = em.merge(projectmemberCollectionNewProjectmember);
                    if (oldLoginidOfProjectmemberCollectionNewProjectmember != null && !oldLoginidOfProjectmemberCollectionNewProjectmember.equals(login)) {
                        oldLoginidOfProjectmemberCollectionNewProjectmember.getProjectmemberCollection().remove(projectmemberCollectionNewProjectmember);
                        oldLoginidOfProjectmemberCollectionNewProjectmember = em.merge(oldLoginidOfProjectmemberCollectionNewProjectmember);
                    }
                }
            }
            for (Loginaudit loginauditCollectionNewLoginaudit : loginauditCollectionNew) {
                if (!loginauditCollectionOld.contains(loginauditCollectionNewLoginaudit)) {
                    Login oldLoginidOfLoginauditCollectionNewLoginaudit = loginauditCollectionNewLoginaudit.getLoginid();
                    loginauditCollectionNewLoginaudit.setLoginid(login);
                    loginauditCollectionNewLoginaudit = em.merge(loginauditCollectionNewLoginaudit);
                    if (oldLoginidOfLoginauditCollectionNewLoginaudit != null && !oldLoginidOfLoginauditCollectionNewLoginaudit.equals(login)) {
                        oldLoginidOfLoginauditCollectionNewLoginaudit.getLoginauditCollection().remove(loginauditCollectionNewLoginaudit);
                        oldLoginidOfLoginauditCollectionNewLoginaudit = em.merge(oldLoginidOfLoginauditCollectionNewLoginaudit);
                    }
                }
            }
            for (Project projectCollectionNewProject : projectCollectionNew) {
                if (!projectCollectionOld.contains(projectCollectionNewProject)) {
                    Login oldLoginidOfProjectCollectionNewProject = projectCollectionNewProject.getLoginid();
                    projectCollectionNewProject.setLoginid(login);
                    projectCollectionNewProject = em.merge(projectCollectionNewProject);
                    if (oldLoginidOfProjectCollectionNewProject != null && !oldLoginidOfProjectCollectionNewProject.equals(login)) {
                        oldLoginidOfProjectCollectionNewProject.getProjectCollection().remove(projectCollectionNewProject);
                        oldLoginidOfProjectCollectionNewProject = em.merge(oldLoginidOfProjectCollectionNewProject);
                    }
                }
            }
            for (Solution solutionCollectionNewSolution : solutionCollectionNew) {
                if (!solutionCollectionOld.contains(solutionCollectionNewSolution)) {
                    Login oldLoginidOfSolutionCollectionNewSolution = solutionCollectionNewSolution.getLoginid();
                    solutionCollectionNewSolution.setLoginid(login);
                    solutionCollectionNewSolution = em.merge(solutionCollectionNewSolution);
                    if (oldLoginidOfSolutionCollectionNewSolution != null && !oldLoginidOfSolutionCollectionNewSolution.equals(login)) {
                        oldLoginidOfSolutionCollectionNewSolution.getSolutionCollection().remove(solutionCollectionNewSolution);
                        oldLoginidOfSolutionCollectionNewSolution = em.merge(oldLoginidOfSolutionCollectionNewSolution);
                    }
                }
            }
            for (Loginprofile loginprofileCollectionNewLoginprofile : loginprofileCollectionNew) {
                if (!loginprofileCollectionOld.contains(loginprofileCollectionNewLoginprofile)) {
                    Login oldLoginidOfLoginprofileCollectionNewLoginprofile = loginprofileCollectionNewLoginprofile.getLoginid();
                    loginprofileCollectionNewLoginprofile.setLoginid(login);
                    loginprofileCollectionNewLoginprofile = em.merge(loginprofileCollectionNewLoginprofile);
                    if (oldLoginidOfLoginprofileCollectionNewLoginprofile != null && !oldLoginidOfLoginprofileCollectionNewLoginprofile.equals(login)) {
                        oldLoginidOfLoginprofileCollectionNewLoginprofile.getLoginprofileCollection().remove(loginprofileCollectionNewLoginprofile);
                        oldLoginidOfLoginprofileCollectionNewLoginprofile = em.merge(oldLoginidOfLoginprofileCollectionNewLoginprofile);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = login.getLoginid();
                if (findLogin(id) == null) {
                    throw new NonexistentEntityException("The login with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Login login;
            try {
                login = em.getReference(Login.class, id);
                login.getLoginid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The login with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Bugassigned> bugassignedCollectionOrphanCheck = login.getBugassignedCollection();
            for (Bugassigned bugassignedCollectionOrphanCheckBugassigned : bugassignedCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Login (" + login + ") cannot be destroyed since the Bugassigned " + bugassignedCollectionOrphanCheckBugassigned + " in its bugassignedCollection field has a non-nullable loginid field.");
            }
            Collection<Bug> bugCollectionOrphanCheck = login.getBugCollection();
            for (Bug bugCollectionOrphanCheckBug : bugCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Login (" + login + ") cannot be destroyed since the Bug " + bugCollectionOrphanCheckBug + " in its bugCollection field has a non-nullable loginid field.");
            }
            Collection<Projectmember> projectmemberCollectionOrphanCheck = login.getProjectmemberCollection();
            for (Projectmember projectmemberCollectionOrphanCheckProjectmember : projectmemberCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Login (" + login + ") cannot be destroyed since the Projectmember " + projectmemberCollectionOrphanCheckProjectmember + " in its projectmemberCollection field has a non-nullable loginid field.");
            }
            Collection<Loginaudit> loginauditCollectionOrphanCheck = login.getLoginauditCollection();
            for (Loginaudit loginauditCollectionOrphanCheckLoginaudit : loginauditCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Login (" + login + ") cannot be destroyed since the Loginaudit " + loginauditCollectionOrphanCheckLoginaudit + " in its loginauditCollection field has a non-nullable loginid field.");
            }
            Collection<Project> projectCollectionOrphanCheck = login.getProjectCollection();
            for (Project projectCollectionOrphanCheckProject : projectCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Login (" + login + ") cannot be destroyed since the Project " + projectCollectionOrphanCheckProject + " in its projectCollection field has a non-nullable loginid field.");
            }
            Collection<Solution> solutionCollectionOrphanCheck = login.getSolutionCollection();
            for (Solution solutionCollectionOrphanCheckSolution : solutionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Login (" + login + ") cannot be destroyed since the Solution " + solutionCollectionOrphanCheckSolution + " in its solutionCollection field has a non-nullable loginid field.");
            }
            Collection<Loginprofile> loginprofileCollectionOrphanCheck = login.getLoginprofileCollection();
            for (Loginprofile loginprofileCollectionOrphanCheckLoginprofile : loginprofileCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Login (" + login + ") cannot be destroyed since the Loginprofile " + loginprofileCollectionOrphanCheckLoginprofile + " in its loginprofileCollection field has a non-nullable loginid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Questionbase questionid = login.getQuestionid();
            if (questionid != null) {
                questionid.getLoginCollection().remove(login);
                questionid = em.merge(questionid);
            }
            em.remove(login);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Login> findLoginEntities() {
        return findLoginEntities(true, -1, -1);
    }

    public List<Login> findLoginEntities(int maxResults, int firstResult) {
        return findLoginEntities(false, maxResults, firstResult);
    }

    private List<Login> findLoginEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Login.class));
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

    public Login findLogin(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Login.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoginCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Login> rt = cq.from(Login.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
