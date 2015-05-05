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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dnoliver
 */
@Entity
@Table(catalog = "MyChat", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Messages.findAll", query = "SELECT m FROM Messages m"),
    @NamedQuery(name = "Messages.findById", query = "SELECT m FROM Messages m WHERE m.id = :id"),
    @NamedQuery(name = "Messages.findByDatetimeOfCreation", query = "SELECT m FROM Messages m WHERE m.datetimeOfCreation = :datetimeOfCreation"),
    @NamedQuery(name = "Messages.findByBody", query = "SELECT m FROM Messages m WHERE m.body = :body"),
    @NamedQuery(name = "Messages.findByRoom", query = "SELECT m FROM Messages m WHERE m.room = :room"),
    @NamedQuery(name = "Messages.findByState", query = "SELECT m FROM Messages m WHERE m.state = :state")})

public class Messages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datetime_of_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetimeOfCreation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String body;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String state;
    @JoinColumn(name = "owner", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profiles owner;
    @JoinColumn(name = "room", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rooms room;

    public Messages() {
        this.datetimeOfCreation = new Date();
        this.state = "active";
    }

    public Messages(Integer id) {
        this.id = id;
        this.datetimeOfCreation = new Date();
        this.state = "active";
    }

    public Messages(Integer id, Date datetimeOfCreation, String body, String state) {
        this.id = id;
        this.datetimeOfCreation = datetimeOfCreation;
        this.body = body;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatetimeOfCreation() {
        return datetimeOfCreation;
    }

    public void setDatetimeOfCreation(Date datetimeOfCreation) {
        this.datetimeOfCreation = datetimeOfCreation;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Profiles getOwner() {
        return owner;
    }

    public void setOwner(Profiles owner) {
        this.owner = owner;
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
        if (!(object instanceof Messages)) {
            return false;
        }
        Messages other = (Messages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Messages[ id=" + id + " ]";
    }
    
}
