package com.awanesh.APIGrocery.entity;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "orderid")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "groceryitemid")
    private GroceryItem groceryItemId;

    private int quantity;
}