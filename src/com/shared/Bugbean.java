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
@Table(name = "BUG", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bug.findAll", query = "SELECT b FROM Bug b"),
    @NamedQuery(name = "Bug.findByBugid", query = "SELECT b FROM Bug b WHERE b.bugid = :bugid"),
    @NamedQuery(name = "Bug.findByBugname", query = "SELECT b FROM Bug b WHERE b.bugname = :bugname"),
    @NamedQuery(name = "Bug.findByRaiseddate", query = "SELECT b FROM Bug b WHERE b.raiseddate = :raiseddate"),
    @NamedQuery(name = "Bug.findByDescription", query = "SELECT b FROM Bug b WHERE b.description = :description"),
    @NamedQuery(name = "Bug.findByStatus", query = "SELECT b FROM Bug b WHERE b.status = :status")})
public class Bugbean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BUGID", nullable = false)
    private Integer bugid;
    @Basic(optional = false)
    @Column(name = "BUGNAME", nullable = false, length = 20)
    private String bugname;
    @Column(name = "RAISEDDATE")
    @Temporal(TemporalType.DATE)
    private Date raiseddate;
    @Column(name = "DESCRIPTION", length = 250)
    private String description;
    @Column(name = "STATUS", length = 20)
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bugid")
    private Collection<Bugassignedbean> bugassignedCollection;
    @JoinColumn(name = "PROJECTID", referencedColumnName = "PROJECTID", nullable = false)
    @ManyToOne(optional = false)
    private Projectbean projectid;
    @JoinColumn(name = "PRIORITYID", referencedColumnName = "PRIORITYID", nullable = false)
    @ManyToOne(optional = false)
    private Prioritybean priorityid;
    @JoinColumn(name = "LOGINID", referencedColumnName = "LOGINID", nullable = false)
    @ManyToOne(optional = false)
    private Loginbean loginid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bugid")
    private Collection<Solutionbean> solutionCollection;

    public Bugbean() {
    }

    public Bugbean(Integer bugid) {
        this.bugid = bugid;
    }

    public Bugbean(Integer bugid, String bugname) {
        this.bugid = bugid;
        this.bugname = bugname;
    }

    public Integer getBugid() {
        return bugid;
    }

    public void setBugid(Integer bugid) {
        this.bugid = bugid;
    }

    public String getBugname() {
        return bugname;
    }

    public void setBugname(String bugname) {
        this.bugname = bugname;
    }

    public Date getRaiseddate() {
        return raiseddate;
    }

    public void setRaiseddate(Date raiseddate) {
        this.raiseddate = raiseddate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Bugassignedbean> getBugassignedCollection() {
        return bugassignedCollection;
    }

    public void setBugassignedCollection(Collection<Bugassignedbean> bugassignedCollection) {
        this.bugassignedCollection = bugassignedCollection;
    }

    public Projectbean getProjectid() {
        return projectid;
    }

    public void setProjectid(Projectbean projectid) {
        this.projectid = projectid;
    }

    public Prioritybean getPriorityid() {
        return priorityid;
    }

    public void setPriorityid(Prioritybean priorityid) {
        this.priorityid = priorityid;
    }

    public Loginbean getLoginid() {
        return loginid;
    }

    public void setLoginid(Loginbean loginid) {
        this.loginid = loginid;
    }

    @XmlTransient
    public Collection<Solutionbean> getSolutionCollection() {
        return solutionCollection;
    }

    public void setSolutionCollection(Collection<Solutionbean> solutionCollection) {
        this.solutionCollection = solutionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bugid != null ? bugid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bugbean)) {
            return false;
        }
        Bugbean other = (Bugbean) object;
        if ((this.bugid == null && other.bugid != null) || (this.bugid != null && !this.bugid.equals(other.bugid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Bug[ bugid=" + bugid + " ]";
    }
    
}
