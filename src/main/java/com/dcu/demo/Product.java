package com.dcu.demo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString

public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Text")
    private String image;
    @Column(nullable = false, unique = true)
    private String title;


    @Column(length = 100)
    private String company;

    private Integer price;
    private LocalDate release_date;

}
