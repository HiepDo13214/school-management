/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.school.school_management.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name = "refresh_tokens")
@NamedQueries({
    @NamedQuery(name = "RefreshTokens.findAll", query = "SELECT r FROM RefreshTokens r"),
    @NamedQuery(name = "RefreshTokens.findById", query = "SELECT r FROM RefreshTokens r WHERE r.id = :id"),
    @NamedQuery(name = "RefreshTokens.findByToken", query = "SELECT r FROM RefreshTokens r WHERE r.token = :token"),
    @NamedQuery(name = "RefreshTokens.findByExpiresAt", query = "SELECT r FROM RefreshTokens r WHERE r.expiresAt = :expiresAt"),
    @NamedQuery(name = "RefreshTokens.findByRevoked", query = "SELECT r FROM RefreshTokens r WHERE r.revoked = :revoked"),
    @NamedQuery(name = "RefreshTokens.findByCreatedAt", query = "SELECT r FROM RefreshTokens r WHERE r.createdAt = :createdAt"),
    @NamedQuery(name = "RefreshTokens.findByRevokedAt", query = "SELECT r FROM RefreshTokens r WHERE r.revokedAt = :revokedAt")})
public class RefreshTokens implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "token")
    private String token;
    @Column(name = "expires_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;
    @Column(name = "revoked")
    private Boolean revoked;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "revoked_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date revokedAt;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users userId;

    public RefreshTokens() {
    }

    public RefreshTokens(Integer id) {
        this.id = id;
    }

    public RefreshTokens(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public void setRevoked(Boolean revoked) {
        this.revoked = revoked;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(Date revokedAt) {
        this.revokedAt = revokedAt;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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
        if (!(object instanceof RefreshTokens)) {
            return false;
        }
        RefreshTokens other = (RefreshTokens) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.school.school_management.entity.RefreshTokens[ id=" + id + " ]";
    }
    
}
