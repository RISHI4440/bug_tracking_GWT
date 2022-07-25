/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shared;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author john
 */
@Entity
@Table(name = "PROJECTMEMBER", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projectmember.findAll", query = "SELECT p FROM Projectmember p"),
    @NamedQuery(name = "Projectmember.findByProjectmid", query = "SELECT p FROM Projectmember p WHERE p.projectmid = :projectmid"),
    @NamedQuery(name = "Projectmember.findByStatus", query = "SELECT p FROM Projectmember p WHERE p.status = :status")})
public class Projectmemberbean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PROJECTMID", nullable = false)
    private Integer projectmid;
    @Column(name = "STATUS", length = 20)
    private String status;
    @JoinColumn(name = "PROJECTID", referencedColumnName = "PROJECTID", nullable = false)
    @ManyToOne(optional = false)
    private Projectbean projectid;
    @JoinColumn(name = "LOGINID", referencedColumnName = "LOGINID", nullable = false)
    @ManyToOne(optional = false)
    private Loginbean loginid;

    public Projectmemberbean() {
    }

    public Projectmemberbean(Integer projectmid) {
        this.projectmid = projectmid;
    }

    public Integer getProjectmid() {
        return projectmid;
    }

    public void setProjectmid(Integer projectmid) {
        this.projectmid = projectmid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Projectbean getProjectid() {
        return projectid;
    }

    public void setProjectid(Projectbean projectid) {
        this.projectid = projectid;
    }

    public Loginbean getLoginid() {
        return loginid;
    }

    public void setLoginid(Loginbean loginid) {
        this.loginid = loginid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectmid != null ? projectmid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projectmemberbean)) {
            return false;
        }
        Projectmemberbean other = (Projectmemberbean) object;
        if ((this.projectmid == null && other.projectmid != null) || (this.projectmid != null && !this.projectmid.equals(other.projectmid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Projectmember[ projectmid=" + projectmid + " ]";
    }
    
}
