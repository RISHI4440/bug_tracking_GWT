/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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
@Table(name = "PROJECT", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.findByProjectid", query = "SELECT p FROM Project p WHERE p.projectid = :projectid"),
    @NamedQuery(name = "Project.findByProjectname", query = "SELECT p FROM Project p WHERE p.projectname = :projectname"),
    @NamedQuery(name = "Project.findByStartdate", query = "SELECT p FROM Project p WHERE p.startdate = :startdate"),
    @NamedQuery(name = "Project.findByEnddate", query = "SELECT p FROM Project p WHERE p.enddate = :enddate"),
    @NamedQuery(name = "Project.findByStatus", query = "SELECT p FROM Project p WHERE p.status = :status")})
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PROJECTID", nullable = false)
    private Integer projectid;
    @Basic(optional = false)
    @Column(name = "PROJECTNAME", nullable = false, length = 20)
    private String projectname;
    @Column(name = "STARTDATE")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Column(name = "ENDDATE")
    @Temporal(TemporalType.DATE)
    private Date enddate;
    @Column(name = "STATUS", length = 20)
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectid")
    private Collection<Bug> bugCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectid")
    private Collection<Projectmember> projectmemberCollection;
    @JoinColumn(name = "LOGINID", referencedColumnName = "LOGINID", nullable = false)
    @ManyToOne(optional = false)
    private Login loginid;

    public Project() {
    }

    public Project(Integer projectid) {
        this.projectid = projectid;
    }

    public Project(Integer projectid, String projectname) {
        this.projectid = projectid;
        this.projectname = projectname;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Bug> getBugCollection() {
        return bugCollection;
    }

    public void setBugCollection(Collection<Bug> bugCollection) {
        this.bugCollection = bugCollection;
    }

    @XmlTransient
    public Collection<Projectmember> getProjectmemberCollection() {
        return projectmemberCollection;
    }

    public void setProjectmemberCollection(Collection<Projectmember> projectmemberCollection) {
        this.projectmemberCollection = projectmemberCollection;
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
        hash += (projectid != null ? projectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.projectid == null && other.projectid != null) || (this.projectid != null && !this.projectid.equals(other.projectid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Project[ projectid=" + projectid + " ]";
    }
    
}
