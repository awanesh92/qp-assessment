package com.awanesh.APIGrocery.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awanesh.APIGrocery.entity.GroceryItem;
import com.awanesh.APIGrocery.entity.Order;
import com.awanesh.APIGrocery.entity.OrderCreationResponse;
import com.awanesh.APIGrocery.entity.OrderItem;
import com.awanesh.APIGrocery.entity.OrderRequestItem;
import com.awanesh.APIGrocery.entity.UserInfo;
import com.awanesh.APIGrocery.repository.GroceryItemRepository;
import com.awanesh.APIGrocery.repository.OrderItemRepository;
import com.awanesh.APIGrocery.repository.OrderTableRepository;
import com.awanesh.APIGrocery.repository.UserInfoRepository;


@Service
public class OrderService {
    
    @Autowired
    private OrderTableRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Autowired
    private UserInfoRepository userRepository;

    public OrderCreationResponse createOrder(String userName, List<OrderRequestItem> orderItems) throws Exception {
    	Integer userId = userRepository.findByName(userName).get().getId();
    	
        UserInfo user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not Found"));
        List<OrderItem> orderItemList = null;
        Order order = new Order();
        order.setUserId((long)user.getId());
        order.setOrderDate(java.sql.Date.valueOf(LocalDate.now()));
        order.setStatus("PENDING");
        order = orderRepository.save(order);

        int itemsAdded = 0;
        int itemsNotAdded = 0;

        for (OrderRequestItem itemRequest : orderItems) {
        	try {
                GroceryItem groceryItem = groceryItemRepository.findById(itemRequest.getGroceryItemId())
                        .orElseThrow(() -> new Exception("Item not found: " + itemRequest.getGroceryItemId()));

                if (groceryItem.getInventory() >= itemRequest.getQuantity()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setGroceryItemId(groceryItem);
                    orderItem.setQuantity(itemRequest.getQuantity());

                    orderItemRepository.save(orderItem);
                    orderItemList.add(orderItem);
                    groceryItem.setInventory(groceryItem.getInventory() - itemRequest.getQuantity());
                    groceryItemRepository.save(groceryItem);
                    itemsAdded++;
                } else {
                    itemsNotAdded++;
                }
            } catch (Exception e) {
            	System.out.println("Not added item");
                itemsNotAdded++;
            }
        }
        order.setOrderItems(orderItemList);

        return new OrderCreationResponse(itemsAdded, itemsNotAdded, order);
    }
}