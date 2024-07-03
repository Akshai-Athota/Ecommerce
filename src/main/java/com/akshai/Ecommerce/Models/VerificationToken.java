package com.akshai.Ecommerce.Models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="verfication_token")
public class VerificationToken {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id",nullable = false)
    private Long id;

    @Lob
    @Column(name="token",nullable = false,unique = true)
    private String token;

    @Column(name = "created_timestamp", nullable = false)
    private Timestamp createdtime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUser user;

    public Timestamp getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Timestamp createdtime) {
        this.createdtime = createdtime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public LocalUser getUser() {
        return user;
    }

    public void setUser(LocalUser user) {
        this.user = user;
    }
}
