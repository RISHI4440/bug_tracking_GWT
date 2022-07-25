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
@Table(name = "BUGASSIGNED", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bugassigned.findAll", query = "SELECT b FROM Bugassigned b"),
    @NamedQuery(name = "Bugassigned.findByBugaid", query = "SELECT b FROM Bugassigned b WHERE b.bugaid = :bugaid"),
    @NamedQuery(name = "Bugassigned.findByAssdate", query = "SELECT b FROM Bugassigned b WHERE b.assdate = :assdate")})
public class Bugassigned implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BUGAID", nullable = false)
    private Integer bugaid;
    @Column(name = "ASSDATE")
    @Temporal(TemporalType.DATE)
    private Date assdate;
    @JoinColumn(name = "LOGINID", referencedColumnName = "LOGINID", nullable = false)
    @ManyToOne(optional = false)
    private Login loginid;
    @JoinColumn(name = "BUGID", referencedColumnName = "BUGID", nullable = false)
    @ManyToOne(optional = false)
    private Bug bugid;

    public Bugassigned() {
    }

    public Bugassigned(Integer bugaid) {
        this.bugaid = bugaid;
    }

    public Integer getBugaid() {
        return bugaid;
    }

    public void setBugaid(Integer bugaid) {
        this.bugaid = bugaid;
    }

    public Date getAssdate() {
        return assdate;
    }

    public void setAssdate(Date assdate) {
        this.assdate = assdate;
    }

    public Login getLoginid() {
        return loginid;
    }

    public void setLoginid(Login loginid) {
        this.loginid = loginid;
    }

    public Bug getBugid() {
        return bugid;
    }

    public void setBugid(Bug bugid) {
        this.bugid = bugid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bugaid != null ? bugaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bugassigned)) {
            return false;
        }
        Bugassigned other = (Bugassigned) object;
        if ((this.bugaid == null && other.bugaid != null) || (this.bugaid != null && !this.bugaid.equals(other.bugaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Bugassigned[ bugaid=" + bugaid + " ]";
    }
    
}
