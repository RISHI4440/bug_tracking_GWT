/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shared;

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
@Table(name = "PRIORITY", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Priority.findAll", query = "SELECT p FROM Priority p"),
    @NamedQuery(name = "Priority.findByPriorityid", query = "SELECT p FROM Priority p WHERE p.priorityid = :priorityid"),
    @NamedQuery(name = "Priority.findByPrioritytype", query = "SELECT p FROM Priority p WHERE p.prioritytype = :prioritytype")})
public class Prioritybean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRIORITYID", nullable = false)
    private Integer priorityid;
    @Basic(optional = false)
    @Column(name = "PRIORITYTYPE", nullable = false, length = 30)
    private String prioritytype;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priorityid")
    private Collection<Bugbean> bugCollection;

    public Prioritybean() {
    }

    public Prioritybean(Integer priorityid) {
        this.priorityid = priorityid;
    }

    public Prioritybean(Integer priorityid, String prioritytype) {
        this.priorityid = priorityid;
        this.prioritytype = prioritytype;
    }

    public Integer getPriorityid() {
        return priorityid;
    }

    public void setPriorityid(Integer priorityid) {
        this.priorityid = priorityid;
    }

    public String getPrioritytype() {
        return prioritytype;
    }

    public void setPrioritytype(String prioritytype) {
        this.prioritytype = prioritytype;
    }

    @XmlTransient
    public Collection<Bugbean> getBugCollection() {
        return bugCollection;
    }

    public void setBugCollection(Collection<Bugbean> bugCollection) {
        this.bugCollection = bugCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (priorityid != null ? priorityid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prioritybean)) {
            return false;
        }
        Prioritybean other = (Prioritybean) object;
        if ((this.priorityid == null && other.priorityid != null) || (this.priorityid != null && !this.priorityid.equals(other.priorityid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Priority[ priorityid=" + priorityid + " ]";
    }
    
}
