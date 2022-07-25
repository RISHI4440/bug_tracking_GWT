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
public class QuestionbaseJpaController implements Serializable {

    public QuestionbaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Questionbase questionbase) {
        if (questionbase.getLoginCollection() == null) {
            questionbase.setLoginCollection(new ArrayList<Login>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Login> attachedLoginCollection = new ArrayList<Login>();
            for (Login loginCollectionLoginToAttach : questionbase.getLoginCollection()) {
                loginCollectionLoginToAttach = em.getReference(loginCollectionLoginToAttach.getClass(), loginCollectionLoginToAttach.getLoginid());
                attachedLoginCollection.add(loginCollectionLoginToAttach);
            }
            questionbase.setLoginCollection(attachedLoginCollection);
            em.persist(questionbase);
            for (Login loginCollectionLogin : questionbase.getLoginCollection()) {
                Questionbase oldQuestionidOfLoginCollectionLogin = loginCollectionLogin.getQuestionid();
                loginCollectionLogin.setQuestionid(questionbase);
                loginCollectionLogin = em.merge(loginCollectionLogin);
                if (oldQuestionidOfLoginCollectionLogin != null) {
                    oldQuestionidOfLoginCollectionLogin.getLoginCollection().remove(loginCollectionLogin);
                    oldQuestionidOfLoginCollectionLogin = em.merge(oldQuestionidOfLoginCollectionLogin);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Questionbase questionbase) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Questionbase persistentQuestionbase = em.find(Questionbase.class, questionbase.getQuestionid());
            Collection<Login> loginCollectionOld = persistentQuestionbase.getLoginCollection();
            Collection<Login> loginCollectionNew = questionbase.getLoginCollection();
            List<String> illegalOrphanMessages = null;
            for (Login loginCollectionOldLogin : loginCollectionOld) {
                if (!loginCollectionNew.contains(loginCollectionOldLogin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Login " + loginCollectionOldLogin + " since its questionid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Login> attachedLoginCollectionNew = new ArrayList<Login>();
            for (Login loginCollectionNewLoginToAttach : loginCollectionNew) {
                loginCollectionNewLoginToAttach = em.getReference(loginCollectionNewLoginToAttach.getClass(), loginCollectionNewLoginToAttach.getLoginid());
                attachedLoginCollectionNew.add(loginCollectionNewLoginToAttach);
            }
            loginCollectionNew = attachedLoginCollectionNew;
            questionbase.setLoginCollection(loginCollectionNew);
            questionbase = em.merge(questionbase);
            for (Login loginCollectionNewLogin : loginCollectionNew) {
                if (!loginCollectionOld.contains(loginCollectionNewLogin)) {
                    Questionbase oldQuestionidOfLoginCollectionNewLogin = loginCollectionNewLogin.getQuestionid();
                    loginCollectionNewLogin.setQuestionid(questionbase);
                    loginCollectionNewLogin = em.merge(loginCollectionNewLogin);
                    if (oldQuestionidOfLoginCollectionNewLogin != null && !oldQuestionidOfLoginCollectionNewLogin.equals(questionbase)) {
                        oldQuestionidOfLoginCollectionNewLogin.getLoginCollection().remove(loginCollectionNewLogin);
                        oldQuestionidOfLoginCollectionNewLogin = em.merge(oldQuestionidOfLoginCollectionNewLogin);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = questionbase.getQuestionid();
                if (findQuestionbase(id) == null) {
                    throw new NonexistentEntityException("The questionbase with id " + id + " no longer exists.");
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
            Questionbase questionbase;
            try {
                questionbase = em.getReference(Questionbase.class, id);
                questionbase.getQuestionid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The questionbase with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Login> loginCollectionOrphanCheck = questionbase.getLoginCollection();
            for (Login loginCollectionOrphanCheckLogin : loginCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Questionbase (" + questionbase + ") cannot be destroyed since the Login " + loginCollectionOrphanCheckLogin + " in its loginCollection field has a non-nullable questionid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(questionbase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Questionbase> findQuestionbaseEntities() {
        return findQuestionbaseEntities(true, -1, -1);
    }

    public List<Questionbase> findQuestionbaseEntities(int maxResults, int firstResult) {
        return findQuestionbaseEntities(false, maxResults, firstResult);
    }

    private List<Questionbase> findQuestionbaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Questionbase.class));
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

    public Questionbase findQuestionbase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Questionbase.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuestionbaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Questionbase> rt = cq.from(Questionbase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
