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
@Table(name = "SOLUTION", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solution.findAll", query = "SELECT s FROM Solution s"),
    @NamedQuery(name = "Solution.findBySolutionid", query = "SELECT s FROM Solution s WHERE s.solutionid = :solutionid"),
    @NamedQuery(name = "Solution.findBySolveddate", query = "SELECT s FROM Solution s WHERE s.solveddate = :solveddate"),
    @NamedQuery(name = "Solution.findBySolution", query = "SELECT s FROM Solution s WHERE s.solution = :solution")})
public class Solution implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SOLUTIONID", nullable = false)
    private Integer solutionid;
    @Column(name = "SOLVEDDATE")
    @Temporal(TemporalType.DATE)
    private Date solveddate;
    @Column(name = "SOLUTION", length = 250)
    private String solution;
    @JoinColumn(name = "LOGINID", referencedColumnName = "LOGINID", nullable = false)
    @ManyToOne(optional = false)
    private Login loginid;
    @JoinColumn(name = "BUGID", referencedColumnName = "BUGID", nullable = false)
    @ManyToOne(optional = false)
    private Bug bugid;

    public Solution() {
    }

    public Solution(Integer solutionid) {
        this.solutionid = solutionid;
    }

    public Integer getSolutionid() {
        return solutionid;
    }

    public void setSolutionid(Integer solutionid) {
        this.solutionid = solutionid;
    }

    public Date getSolveddate() {
        return solveddate;
    }

    public void setSolveddate(Date solveddate) {
        this.solveddate = solveddate;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
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
        hash += (solutionid != null ? solutionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solution)) {
            return false;
        }
        Solution other = (Solution) object;
        if ((this.solutionid == null && other.solutionid != null) || (this.solutionid != null && !this.solutionid.equals(other.solutionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Solution[ solutionid=" + solutionid + " ]";
    }
    
}
