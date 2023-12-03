package com.awanesh.APIGrocery;

import com.awanesh.APIGrocery.controller.AdminController;
import com.awanesh.APIGrocery.entity.GroceryItem;
import com.awanesh.APIGrocery.service.GroceryItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private GroceryItemService groceryItemService;

    @InjectMocks
    private AdminController adminController;

    @Test
    public void testAddGroceryItem() {
        GroceryItem mockedItem = new GroceryItem();
        when(groceryItemService.addGroceryItem(any())).thenReturn(mockedItem);

        ResponseEntity<GroceryItem> responseEntity = adminController.addGroceryItem(new GroceryItem());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockedItem, responseEntity.getBody());

        verify(groceryItemService, times(1)).addGroceryItem(any());
    }


    @Test
    public void testGetAllGroceryItems() {
        List<GroceryItem> mockedItems = new ArrayList<>();
        when(groceryItemService.getAllGroceryItems()).thenReturn(mockedItems);

        ResponseEntity<List<GroceryItem>> responseEntity = adminController.getAllGroceryItems();

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockedItems, responseEntity.getBody());

        verify(groceryItemService, times(1)).getAllGroceryItems();
    }

    @Test
    public void testRemoveGroceryItem() {
        when(groceryItemService.removeGroceryItem(any())).thenReturn(true);

        ResponseEntity<String> responseEntity = adminController.removeGroceryItem(1L);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals("Grocery item removed successfully", responseEntity.getBody());

        verify(groceryItemService, times(1)).removeGroceryItem(any());
    }

    @Test
    public void testUpdateGroceryItem() {
        GroceryItem updatedItem = new GroceryItem();
        when(groceryItemService.updateGroceryItem(any(), any())).thenReturn(updatedItem);

        ResponseEntity<GroceryItem> responseEntity = adminController.updateGroceryItem(1L, new GroceryItem());

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedItem, responseEntity.getBody());

        verify(groceryItemService, times(1)).updateGroceryItem(any(), any());
    }

    @Test
    public void testUpdateInventory() {
        doNothing().when(groceryItemService).updateInventory(any(), anyInt());

        ResponseEntity<Void> responseEntity = adminController.updateInventory(1L, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(groceryItemService, times(1)).updateInventory(any(), anyInt());
    }
}
