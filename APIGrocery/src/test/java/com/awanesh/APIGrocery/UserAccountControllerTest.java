package com.awanesh.APIGrocery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.awanesh.APIGrocery.controller.UserAccountController;
import com.awanesh.APIGrocery.entity.GroceryItem;
import com.awanesh.APIGrocery.entity.Order;
import com.awanesh.APIGrocery.entity.OrderCreationResponse;
import com.awanesh.APIGrocery.entity.OrderRequest;
import com.awanesh.APIGrocery.service.GroceryItemService;
import com.awanesh.APIGrocery.service.JwtService;
import com.awanesh.APIGrocery.service.OrderService;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class UserAccountControllerTest {

    @Mock
    private GroceryItemService groceryItemService;

    @Mock
    private OrderService orderService;

    @Mock
    private JwtService jwtService;

    @Autowired
    @InjectMocks
    private UserAccountController userAccountController;

	private Order order;

    @Test
    @WithMockUser(roles = "USER")
    public void testGetAllGroceryItems() {
        when(groceryItemService.getAllGroceryItems()).thenReturn(Collections.emptyList());

        ResponseEntity<List<GroceryItem>> responseEntity = userAccountController.getAllGroceryItems();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseEntity.getBody());

        verify(groceryItemService, times(1)).getAllGroceryItems();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testAddGroceryOrder() throws Exception {
    	order = null;
    	order.setOrderDate(java.sql.Date.valueOf(LocalDate.now()));
    	order.setOrderId(2L);
    	order.setOrderItems(null);
    	order.setStatus("PENDING");
    	order.setUserId(2L);
        when(orderService.createOrder(anyString(), any())).thenReturn(
        		new OrderCreationResponse(5, 0, order));

        when(jwtService.extractUsername(anyString())).thenReturn("user");

        ResponseEntity<String> responseEntity = userAccountController.addGroceryOrder("mockedToken", new OrderRequest());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        verify(orderService, times(1)).createOrder(anyString(), any());

        verify(jwtService, times(1)).extractUsername(anyString());
    }
}