/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dnoliver
 */
@Entity
@Table(name = "Users_Logins", catalog = "MyChat", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersLogins.findAll", query = "SELECT u FROM UsersLogins u"),
    @NamedQuery(name = "UsersLogins.findById", query = "SELECT u FROM UsersLogins u WHERE u.id = :id"),
    @NamedQuery(name = "UsersLogins.findByDatetimeOfAccessStart", query = "SELECT u FROM UsersLogins u WHERE u.datetimeOfAccessStart = :datetimeOfAccessStart"),
    @NamedQuery(name = "UsersLogins.findByDatetimeOfAccessEnd", query = "SELECT u FROM UsersLogins u WHERE u.datetimeOfAccessEnd = :datetimeOfAccessEnd")})
public class UsersLogins implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datetime_of_access_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetimeOfAccessStart;
    @Column(name = "datetime_of_access_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetimeOfAccessEnd;
    @JoinColumn(name = "profile", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profiles profile;

    public UsersLogins() {
        this.datetimeOfAccessStart = new Date();
    }

    public UsersLogins(Integer id) {
        this.id = id;
        this.datetimeOfAccessStart = new Date();
    }

    public UsersLogins(Integer id, Date datetimeOfAccessStart) {
        this.id = id;
        this.datetimeOfAccessStart = datetimeOfAccessStart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatetimeOfAccessStart() {
        return datetimeOfAccessStart;
    }

    public void setDatetimeOfAccessStart(Date datetimeOfAccessStart) {
        this.datetimeOfAccessStart = datetimeOfAccessStart;
    }

    public Date getDatetimeOfAccessEnd() {
        return datetimeOfAccessEnd;
    }

    public void setDatetimeOfAccessEnd(Date datetimeOfAccessEnd) {
        this.datetimeOfAccessEnd = datetimeOfAccessEnd;
    }

    public Profiles getProfile() {
        return profile;
    }

    public void setProfile(Profiles profile) {
        this.profile = profile;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersLogins)) {
            return false;
        }
        UsersLogins other = (UsersLogins) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.UsersLogins[ id=" + id + " ]";
    }
    
}
