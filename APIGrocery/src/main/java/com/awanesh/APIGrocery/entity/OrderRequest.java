package com.awanesh.APIGrocery.entity;

import java.util.List;

public class OrderRequest {

    private List<OrderRequestItem> orderItems;

    public List<OrderRequestItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderRequestItem> orderItems) {
        this.orderItems = orderItems;
    }
}