package com.akshai.Ecommerce;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestDbConnection {
    @Id
    private String id;

    @Column
    private String username;
}
