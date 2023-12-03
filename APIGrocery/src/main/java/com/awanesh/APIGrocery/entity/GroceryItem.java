package com.awanesh.APIGrocery.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GroceryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groceryItemId;

    private String name;
    private double price;
    private int inventory;
}