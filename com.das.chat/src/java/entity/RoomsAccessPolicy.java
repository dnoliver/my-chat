/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dnoliver
 */
@Entity
@Table(name = "Rooms_Access_Policy", catalog = "MyChat", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RoomsAccessPolicy.findAll", query = "SELECT r FROM RoomsAccessPolicy r"),
    @NamedQuery(name = "RoomsAccessPolicy.findById", query = "SELECT r FROM RoomsAccessPolicy r WHERE r.id = :id"),
    @NamedQuery(name = "RoomsAccessPolicy.findByRoom", query = "SELECT r FROM RoomsAccessPolicy r WHERE r.room = :room"),
    @NamedQuery(name = "RoomsAccessPolicy.findByPolicy", query = "SELECT r FROM RoomsAccessPolicy r WHERE r.policy = :policy")})
public class RoomsAccessPolicy implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String policy;
    @JoinColumn(name = "profile", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profiles profile;
    @JoinColumn(name = "room", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rooms room;

    public RoomsAccessPolicy() {
    }

    public RoomsAccessPolicy(Integer id) {
        this.id = id;
    }

    public RoomsAccessPolicy(Integer id, String policy) {
        this.id = id;
        this.policy = policy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public Profiles getProfile() {
        return profile;
    }

    public void setProfile(Profiles profile) {
        this.profile = profile;
    }

    public Rooms getRoom() {
        return room;
    }

    public void setRoom(Rooms room) {
        this.room = room;
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
        if (!(object instanceof RoomsAccessPolicy)) {
            return false;
        }
        RoomsAccessPolicy other = (RoomsAccessPolicy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomsAccessPolicy[ id=" + id + " ]";
    }
    
}
