/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shared;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author john
 */
@Entity
@Table(name = "LOGINPROFILE", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loginprofile.findAll", query = "SELECT l FROM Loginprofile l"),
    @NamedQuery(name = "Loginprofile.findByLoginpid", query = "SELECT l FROM Loginprofile l WHERE l.loginpid = :loginpid"),
    @NamedQuery(name = "Loginprofile.findByBirthdate", query = "SELECT l FROM Loginprofile l WHERE l.birthdate = :birthdate"),
    @NamedQuery(name = "Loginprofile.findByHno", query = "SELECT l FROM Loginprofile l WHERE l.hno = :hno"),
    @NamedQuery(name = "Loginprofile.findByStreet", query = "SELECT l FROM Loginprofile l WHERE l.street = :street"),
    @NamedQuery(name = "Loginprofile.findByCity", query = "SELECT l FROM Loginprofile l WHERE l.city = :city"),
    @NamedQuery(name = "Loginprofile.findByState", query = "SELECT l FROM Loginprofile l WHERE l.state = :state"),
    @NamedQuery(name = "Loginprofile.findByCountry", query = "SELECT l FROM Loginprofile l WHERE l.country = :country"),
    @NamedQuery(name = "Loginprofile.findByPincode", query = "SELECT l FROM Loginprofile l WHERE l.pincode = :pincode"),
    @NamedQuery(name = "Loginprofile.findByContactno", query = "SELECT l FROM Loginprofile l WHERE l.contactno = :contactno"),
    @NamedQuery(name = "Loginprofile.findByEmail", query = "SELECT l FROM Loginprofile l WHERE l.email = :email"),
    @NamedQuery(name = "Loginprofile.findByProfilemodifieddate", query = "SELECT l FROM Loginprofile l WHERE l.profilemodifieddate = :profilemodifieddate")})
public class Loginprofilebean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LOGINPID", nullable = false)
    private Integer loginpid;
    @Column(name = "BIRTHDATE")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Column(name = "HNO", length = 20)
    private String hno;
    @Column(name = "STREET", length = 20)
    private String street;
    @Column(name = "CITY", length = 20)
    private String city;
    @Column(name = "STATE", length = 20)
    private String state;
    @Column(name = "COUNTRY", length = 20)
    private String country;
    @Column(name = "PINCODE", length = 10)
    private String pincode;
    @Column(name = "CONTACTNO", length = 10)
    private String contactno;
    @Column(name = "EMAIL", length = 30)
    private String email;
    @Column(name = "PROFILEMODIFIEDDATE")
    @Temporal(TemporalType.DATE)
    private Date profilemodifieddate;
    @JoinColumn(name = "LOGINID", referencedColumnName = "LOGINID", nullable = false)
    @ManyToOne(optional = false)
    private Loginbean loginid;

    public Loginprofilebean() {
    }

    public Loginprofilebean(Integer loginpid) {
        this.loginpid = loginpid;
    }

    public Integer getLoginpid() {
        return loginpid;
    }

    public void setLoginpid(Integer loginpid) {
        this.loginpid = loginpid;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getHno() {
        return hno;
    }

    public void setHno(String hno) {
        this.hno = hno;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getProfilemodifieddate() {
        return profilemodifieddate;
    }

    public void setProfilemodifieddate(Date profilemodifieddate) {
        this.profilemodifieddate = profilemodifieddate;
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
        hash += (loginpid != null ? loginpid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loginprofilebean)) {
            return false;
        }
        Loginprofilebean other = (Loginprofilebean) object;
        if ((this.loginpid == null && other.loginpid != null) || (this.loginpid != null && !this.loginpid.equals(other.loginpid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Loginprofile[ loginpid=" + loginpid + " ]";
    }
    
}
