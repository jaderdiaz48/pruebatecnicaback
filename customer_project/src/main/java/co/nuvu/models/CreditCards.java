/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.nuvu.models;

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
 * @author jader
 */
@Entity
@Table(name = "credit_cards")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CreditCards.findAll", query = "SELECT c FROM CreditCards c"),
    @NamedQuery(name = "CreditCards.findById", query = "SELECT c FROM CreditCards c WHERE c.id = :id"),
    @NamedQuery(name = "CreditCards.findByNumber", query = "SELECT c FROM CreditCards c WHERE c.number = :number"),
    @NamedQuery(name = "CreditCards.findByCreatedAt", query = "SELECT c FROM CreditCards c WHERE c.createdAt = :createdAt")})
public class CreditCards implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "number")
    private long number;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "card_types_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CardTypes cardTypesId;
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Customers customerId;

    public CreditCards() {
    }

    public CreditCards(Integer id) {
        this.id = id;
    }

    public CreditCards(Integer id, long number, Date createdAt) {
        this.id = id;
        this.number = number;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public CardTypes getCardTypesId() {
        return cardTypesId;
    }

    public void setCardTypesId(CardTypes cardTypesId) {
        this.cardTypesId = cardTypesId;
    }

    public Customers getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customers customerId) {
        this.customerId = customerId;
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
        if (!(object instanceof CreditCards)) {
            return false;
        }
        CreditCards other = (CreditCards) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.CreditCards[ id=" + id + " ]";
    }
    
}
