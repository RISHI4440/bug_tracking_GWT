/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author john
 */
@Entity
@Table(name = "QUESTIONBASE", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questionbase.findAll", query = "SELECT q FROM Questionbase q"),
    @NamedQuery(name = "Questionbase.findByQuestionid", query = "SELECT q FROM Questionbase q WHERE q.questionid = :questionid"),
    @NamedQuery(name = "Questionbase.findByQuestiondetail", query = "SELECT q FROM Questionbase q WHERE q.questiondetail = :questiondetail")})
public class Questionbase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "QUESTIONID", nullable = false)
    private Integer questionid;
    @Basic(optional = false)
    @Column(name = "QUESTIONDETAIL", nullable = false, length = 30)
    private String questiondetail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionid")
    private Collection<Login> loginCollection;

    public Questionbase() {
    }

    public Questionbase(Integer questionid) {
        this.questionid = questionid;
    }

    public Questionbase(Integer questionid, String questiondetail) {
        this.questionid = questionid;
        this.questiondetail = questiondetail;
    }

    public Integer getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Integer questionid) {
        this.questionid = questionid;
    }

    public String getQuestiondetail() {
        return questiondetail;
    }

    public void setQuestiondetail(String questiondetail) {
        this.questiondetail = questiondetail;
    }

    @XmlTransient
    public Collection<Login> getLoginCollection() {
        return loginCollection;
    }

    public void setLoginCollection(Collection<Login> loginCollection) {
        this.loginCollection = loginCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionid != null ? questionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Questionbase)) {
            return false;
        }
        Questionbase other = (Questionbase) object;
        if ((this.questionid == null && other.questionid != null) || (this.questionid != null && !this.questionid.equals(other.questionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Questionbase[ questionid=" + questionid + " ]";
    }
    
}
