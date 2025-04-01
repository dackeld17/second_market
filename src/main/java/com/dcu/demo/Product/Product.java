package com.dcu.demo.Product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, columnDefinition = "Text")
    public String image;

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    public Integer price;


    @Column(length = 100,nullable = false)
    public String company;


    @Column(nullable = false)
    public LocalDate manufactureDate; //제조일

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    public LocalDateTime createdAt;

}
