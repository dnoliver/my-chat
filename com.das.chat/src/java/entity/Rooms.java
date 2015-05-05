/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dnoliver
 */
@Entity
@Table(catalog = "MyChat", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rooms.findAll", query = "SELECT r FROM Rooms r"),
    @NamedQuery(name = "Rooms.findById", query = "SELECT r FROM Rooms r WHERE r.id = :id"),
    @NamedQuery(name = "Rooms.findByName", query = "SELECT r FROM Rooms r WHERE r.name = :name"),
    @NamedQuery(name = "Rooms.findByOwner", query = "SELECT r FROM Rooms r WHERE r.owner = :owner"),
    @NamedQuery(name = "Rooms.findByType", query = "SELECT r FROM Rooms r WHERE r.type = :type")})

public class Rooms implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private Collection<Invitations> invitationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private Collection<Messages> messagesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private Collection<UsersAccess> usersAccessCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private Collection<RoomsAccessPolicy> roomsAccessPolicyCollection;
    @JoinColumn(name = "owner", referencedColumnName = "id")
    @ManyToOne
    private Profiles owner;

    public Rooms() {
    }

    public Rooms(Integer id) {
        this.id = id;
    }

    public Rooms(Integer id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<Invitations> getInvitationsCollection() {
        return invitationsCollection;
    }

    public void setInvitationsCollection(Collection<Invitations> invitationsCollection) {
        this.invitationsCollection = invitationsCollection;
    }

    @XmlTransient
    public Collection<Messages> getMessagesCollection() {
        return messagesCollection;
    }

    public void setMessagesCollection(Collection<Messages> messagesCollection) {
        this.messagesCollection = messagesCollection;
    }

    @XmlTransient
    public Collection<UsersAccess> getUsersAccessCollection() {
        return usersAccessCollection;
    }

    public void setUsersAccessCollection(Collection<UsersAccess> usersAccessCollection) {
        this.usersAccessCollection = usersAccessCollection;
    }

    @XmlTransient
    public Collection<RoomsAccessPolicy> getRoomsAccessPolicyCollection() {
        return roomsAccessPolicyCollection;
    }

    public void setRoomsAccessPolicyCollection(Collection<RoomsAccessPolicy> roomsAccessPolicyCollection) {
        this.roomsAccessPolicyCollection = roomsAccessPolicyCollection;
    }

    public Profiles getOwner() {
        return owner;
    }

    public void setOwner(Profiles owner) {
        this.owner = owner;
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
        if (!(object instanceof Rooms)) {
            return false;
        }
        Rooms other = (Rooms) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rooms[ id=" + id + " ]";
    }
    
}
