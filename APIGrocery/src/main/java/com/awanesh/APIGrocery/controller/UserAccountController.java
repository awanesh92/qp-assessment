package com.awanesh.APIGrocery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awanesh.APIGrocery.entity.GroceryItem;
import com.awanesh.APIGrocery.entity.OrderCreationResponse;
import com.awanesh.APIGrocery.entity.OrderRequest;
import com.awanesh.APIGrocery.service.GroceryItemService;
import com.awanesh.APIGrocery.service.JwtService;
import com.awanesh.APIGrocery.service.OrderService;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class UserAccountController {
    @Autowired
    private GroceryItemService groceryItemService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private JwtService jwtService;

    @GetMapping("/getAllGroceryItems")
    public ResponseEntity<List<GroceryItem>> getAllGroceryItems() {
        List<GroceryItem> groceryItems = groceryItemService.getAllGroceryItems();
        return new ResponseEntity<>(groceryItems, HttpStatus.OK);
    }
    
    @PostMapping("/addGroceryOrder")
    public ResponseEntity<String> addGroceryOrder(@RequestHeader("Authorization") String token,@RequestBody OrderRequest orderRequest) {
        String userName = jwtService.extractUsername(token.substring(7));
        OrderCreationResponse order;
		try {
			order = orderService.createOrder(userName, orderRequest.getOrderItems());
		} catch (Exception e) {
			order = null;
		}

        return new ResponseEntity<>(order == null? "UserID not found": "Items Added to the cart" + 
        order.getOrder().getOrderItems(), HttpStatus.CREATED);
    }
    
}