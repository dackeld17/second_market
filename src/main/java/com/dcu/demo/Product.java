package com.dcu.demo;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(columnDefinition = "Text")
    public String image;
    @Column(nullable = false, unique = true)
    public String title;


    @Column(length = 100)
    public String company;

    public Integer price;
    public LocalDate release_date;
}
