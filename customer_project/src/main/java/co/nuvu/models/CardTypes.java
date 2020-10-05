/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.nuvu.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author jader
 */
@Entity
@Table(name = "card_types")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CardTypes.findAll", query = "SELECT c FROM CardTypes c"),
    @NamedQuery(name = "CardTypes.findById", query = "SELECT c FROM CardTypes c WHERE c.id = :id"),
    @NamedQuery(name = "CardTypes.findByName", query = "SELECT c FROM CardTypes c WHERE c.name = :name"),
    @NamedQuery(name = "CardTypes.findByCreatedAt", query = "SELECT c FROM CardTypes c WHERE c.createdAt = :createdAt")})
public class CardTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardTypesId")
    private Collection<CreditCards> creditCardsCollection;

    public CardTypes() {
    }

    public CardTypes(Integer id) {
        this.id = id;
    }

    public CardTypes(Integer id, String name, Date createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @XmlTransient
    public Collection<CreditCards> getCreditCardsCollection() {
        return creditCardsCollection;
    }

    public void setCreditCardsCollection(Collection<CreditCards> creditCardsCollection) {
        this.creditCardsCollection = creditCardsCollection;
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
        if (!(object instanceof CardTypes)) {
            return false;
        }
        CardTypes other = (CardTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.CardTypes[ id=" + id + " ]";
    }
    
}
