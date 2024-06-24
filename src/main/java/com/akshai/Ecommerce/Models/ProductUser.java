package com.akshai.Ecommerce.Models;


import jakarta.persistence.*;

@Entity
@Table(name="product_user")
public class ProductUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;

    @Column(name="user",unique = true,nullable = false)
    private String username;

    @Column(name="password",nullable = false,length = 100)
    private String password;

    @Column(name="email",nullable = false,unique=true,length = 320)
    private String email;

    @Column(name="firstname",nullable = false)
    private String firstname;

    @Column(name="lastname",nullable = false)
    private String lastname;

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
