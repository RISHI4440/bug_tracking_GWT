/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shared;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author john
 */
@Entity
@Table(name = "LOGIN", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Login.findAll", query = "SELECT l FROM Login l"),
    @NamedQuery(name = "Login.findByLoginid", query = "SELECT l FROM Login l WHERE l.loginid = :loginid"),
    @NamedQuery(name = "Login.findByPassword", query = "SELECT l FROM Login l WHERE l.password = :password"),
    @NamedQuery(name = "Login.findByFirstname", query = "SELECT l FROM Login l WHERE l.firstname = :firstname"),
    @NamedQuery(name = "Login.findByLastname", query = "SELECT l FROM Login l WHERE l.lastname = :lastname"),
    @NamedQuery(name = "Login.findByLogintype", query = "SELECT l FROM Login l WHERE l.logintype = :logintype"),
    @NamedQuery(name = "Login.findByLoginstatus", query = "SELECT l FROM Login l WHERE l.loginstatus = :loginstatus"),
    @NamedQuery(name = "Login.findByRegdate", query = "SELECT l FROM Login l WHERE l.regdate = :regdate"),
    @NamedQuery(name = "Login.findBySanswer", query = "SELECT l FROM Login l WHERE l.sanswer = :sanswer"),
    @NamedQuery(name = "Login.findByFirstlogin", query = "SELECT l FROM Login l WHERE l.firstlogin = :firstlogin"),
    @NamedQuery(name = "Login.findByPassmodifieddate", query = "SELECT l FROM Login l WHERE l.passmodifieddate = :passmodifieddate")})
public class Loginbean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "LOGINID", nullable = false, length = 50)
    private String loginid;
    @Basic(optional = false)
    @Column(name = "PASSWORD", nullable = false, length = 250)
    private String password;
    @Column(name = "FIRSTNAME", length = 20)
    private String firstname;
    @Column(name = "LASTNAME", length = 20)
    private String lastname;
    @Basic(optional = false)
    @Column(name = "LOGINTYPE", nullable = false, length = 20)
    private String logintype;
    @Column(name = "LOGINSTATUS", length = 20)
    private String loginstatus;
    @Column(name = "REGDATE")
    @Temporal(TemporalType.DATE)
    private Date regdate;
    @Column(name = "SANSWER", length = 20)
    private String sanswer;
    @Column(name = "FIRSTLOGIN")
    @Temporal(TemporalType.DATE)
    private Date firstlogin;
    @Column(name = "PASSMODIFIEDDATE")
    @Temporal(TemporalType.DATE)
    private Date passmodifieddate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loginid")
    private Collection<Bugassignedbean> bugassignedCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loginid")
    private Collection<Bugbean> bugCollection;
    @JoinColumn(name = "QUESTIONID", referencedColumnName = "QUESTIONID", nullable = false)
    @ManyToOne(optional = false)
    private Questionbasebean questionid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loginid")
    private Collection<Projectmemberbean> projectmemberCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loginid")
    private Collection<Loginauditbean> loginauditCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loginid")
    private Collection<Projectbean> projectCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loginid")
    private Collection<Solutionbean> solutionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loginid")
    private Collection<Loginprofilebean> loginprofileCollection;

    public Loginbean() {
    }

    public Loginbean(String loginid) {
        this.loginid = loginid;
    }

    public Loginbean(String loginid, String password, String logintype) {
        this.loginid = loginid;
        this.password = password;
        this.logintype = logintype;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLogintype() {
        return logintype;
    }

    public void setLogintype(String logintype) {
        this.logintype = logintype;
    }

    public String getLoginstatus() {
        return loginstatus;
    }

    public void setLoginstatus(String loginstatus) {
        this.loginstatus = loginstatus;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public String getSanswer() {
        return sanswer;
    }

    public void setSanswer(String sanswer) {
        this.sanswer = sanswer;
    }

    public Date getFirstlogin() {
        return firstlogin;
    }

    public void setFirstlogin(Date firstlogin) {
        this.firstlogin = firstlogin;
    }

    public Date getPassmodifieddate() {
        return passmodifieddate;
    }

    public void setPassmodifieddate(Date passmodifieddate) {
        this.passmodifieddate = passmodifieddate;
    }

    @XmlTransient
    public Collection<Bugassignedbean> getBugassignedCollection() {
        return bugassignedCollection;
    }

    public void setBugassignedCollection(Collection<Bugassignedbean> bugassignedCollection) {
        this.bugassignedCollection = bugassignedCollection;
    }

    @XmlTransient
    public Collection<Bugbean> getBugCollection() {
        return bugCollection;
    }

    public void setBugCollection(Collection<Bugbean> bugCollection) {
        this.bugCollection = bugCollection;
    }

    public Questionbasebean getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Questionbasebean questionid) {
        this.questionid = questionid;
    }

    @XmlTransient
    public Collection<Projectmemberbean> getProjectmemberCollection() {
        return projectmemberCollection;
    }

    public void setProjectmemberCollection(Collection<Projectmemberbean> projectmemberCollection) {
        this.projectmemberCollection = projectmemberCollection;
    }

    @XmlTransient
    public Collection<Loginauditbean> getLoginauditCollection() {
        return loginauditCollection;
    }

    public void setLoginauditCollection(Collection<Loginauditbean> loginauditCollection) {
        this.loginauditCollection = loginauditCollection;
    }

    @XmlTransient
    public Collection<Projectbean> getProjectCollection() {
        return projectCollection;
    }

    public void setProjectCollection(Collection<Projectbean> projectCollection) {
        this.projectCollection = projectCollection;
    }

    @XmlTransient
    public Collection<Solutionbean> getSolutionCollection() {
        return solutionCollection;
    }

    public void setSolutionCollection(Collection<Solutionbean> solutionCollection) {
        this.solutionCollection = solutionCollection;
    }

    @XmlTransient
    public Collection<Loginprofilebean> getLoginprofileCollection() {
        return loginprofileCollection;
    }

    public void setLoginprofileCollection(Collection<Loginprofilebean> loginprofileCollection) {
        this.loginprofileCollection = loginprofileCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginid != null ? loginid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loginbean)) {
            return false;
        }
        Loginbean other = (Loginbean) object;
        if ((this.loginid == null && other.loginid != null) || (this.loginid != null && !this.loginid.equals(other.loginid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Login[ loginid=" + loginid + " ]";
    }
    
}
