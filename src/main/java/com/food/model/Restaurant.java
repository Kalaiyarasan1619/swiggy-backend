package com.food.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Data
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Column(length = 1000)
    private String image;
    
    private Double rating;
    private String deliveryTime;
    
    @ElementCollection
    private List<String> cuisine = new ArrayList<>();
    
    private Integer costForTwo;
    private String distance;
    private Boolean promoted = false;
    private String discount;
    
    @Column(length = 500)
    private String description;
    
    private String address;
    private String phone;
    
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuItem> menu = new ArrayList<>();
}
