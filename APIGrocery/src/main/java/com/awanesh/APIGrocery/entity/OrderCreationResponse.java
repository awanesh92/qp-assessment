package com.awanesh.APIGrocery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationResponse {

    private int itemsAdded;
    private int itemsNotAdded;
    private Order order;

}