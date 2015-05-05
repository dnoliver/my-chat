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
    @NamedQuery(name = "Profiles.findAll", query = "SELECT p FROM Profiles p"),
    @NamedQuery(name = "Profiles.findById", query = "SELECT p FROM Profiles p WHERE p.id = :id"),
    @NamedQuery(name = "Profiles.findByLogin", query = "SELECT p FROM Profiles p WHERE p.login = :login"),
    @NamedQuery(name = "Profiles.findByPassword", query = "SELECT p FROM Profiles p WHERE p.password = :password"),
    @NamedQuery(name = "Profiles.findByType", query = "SELECT p FROM Profiles p WHERE p.type = :type")
})

//@NamedStoredProcedureQueries({
//    @NamedStoredProcedureQuery(
//        name = "Profiles.RegisterUserLogin",
//        procedureName = "RegisterUserLogin",
//        parameters = {
//            @StoredProcedureParameter(type=Integer.class,mode=IN,name="profile")
//        }
//    )
//})



public class Profiles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private Collection<Invitations> sendedInvitationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver")
    private Collection<Invitations> receivedInvitationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Collection<Messages> messagesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private Collection<UsersAccess> usersAccessCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private Collection<RoomsAccessPolicy> roomsAccessPolicyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private Collection<UsersLogins> usersLoginsCollection;
    @OneToMany(mappedBy = "owner")
    private Collection<Rooms> roomsCollection;

    public Profiles() {
    }

    public Profiles(Integer id) {
        this.id = id;
    }

    public Profiles(Integer id, String login, String password, String type) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<Invitations> getSendedInvitationsCollection() {
        return sendedInvitationsCollection;
    }

    public void setSendedInvitationsCollection(Collection<Invitations> invitationsCollection) {
        this.sendedInvitationsCollection = invitationsCollection;
    }

    @XmlTransient
    public Collection<Invitations> getReceivedInvitationsCollection1() {
        return receivedInvitationsCollection;
    }

    public void setReceivedInvitationsCollection1(Collection<Invitations> invitationsCollection1) {
        this.receivedInvitationsCollection = invitationsCollection1;
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

    @XmlTransient
    public Collection<UsersLogins> getUsersLoginsCollection() {
        return usersLoginsCollection;
    }

    public void setUsersLoginsCollection(Collection<UsersLogins> usersLoginsCollection) {
        this.usersLoginsCollection = usersLoginsCollection;
    }

    @XmlTransient
    public Collection<Rooms> getRoomsCollection() {
        return roomsCollection;
    }

    public void setRoomsCollection(Collection<Rooms> roomsCollection) {
        this.roomsCollection = roomsCollection;
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
        if (!(object instanceof Profiles)) {
            return false;
        }
        Profiles other = (Profiles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Profiles[ id=" + id + " ]";
    }
    
}
