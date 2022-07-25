/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author john
 */
@Entity
@Table(name = "LOGINAUDIT", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loginaudit.findAll", query = "SELECT l FROM Loginaudit l"),
    @NamedQuery(name = "Loginaudit.findByLoginaid", query = "SELECT l FROM Loginaudit l WHERE l.loginaid = :loginaid"),
    @NamedQuery(name = "Loginaudit.findByLogindate", query = "SELECT l FROM Loginaudit l WHERE l.logindate = :logindate"),
    @NamedQuery(name = "Loginaudit.findByLogindesc", query = "SELECT l FROM Loginaudit l WHERE l.logindesc = :logindesc")})
public class Loginaudit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LOGINAID", nullable = false)
    private Integer loginaid;
    @Column(name = "LOGINDATE")
    @Temporal(TemporalType.DATE)
    private Date logindate;
    @Column(name = "LOGINDESC", length = 20)
    private String logindesc;
    @JoinColumn(name = "LOGINID", referencedColumnName = "LOGINID", nullable = false)
    @ManyToOne(optional = false)
    private Login loginid;

    public Loginaudit() {
    }

    public Loginaudit(Integer loginaid) {
        this.loginaid = loginaid;
    }

    public Integer getLoginaid() {
        return loginaid;
    }

    public void setLoginaid(Integer loginaid) {
        this.loginaid = loginaid;
    }

    public Date getLogindate() {
        return logindate;
    }

    public void setLogindate(Date logindate) {
        this.logindate = logindate;
    }

    public String getLogindesc() {
        return logindesc;
    }

    public void setLogindesc(String logindesc) {
        this.logindesc = logindesc;
    }

    public Login getLoginid() {
        return loginid;
    }

    public void setLoginid(Login loginid) {
        this.loginid = loginid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginaid != null ? loginaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loginaudit)) {
            return false;
        }
        Loginaudit other = (Loginaudit) object;
        if ((this.loginaid == null && other.loginaid != null) || (this.loginaid != null && !this.loginaid.equals(other.loginaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Loginaudit[ loginaid=" + loginaid + " ]";
    }
    
}
