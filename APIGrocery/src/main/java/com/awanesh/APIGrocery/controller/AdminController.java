package com.awanesh.APIGrocery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.awanesh.APIGrocery.entity.GroceryItem;
import com.awanesh.APIGrocery.service.GroceryItemService;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final GroceryItemService groceryItemService;

    @Autowired
    public AdminController(GroceryItemService groceryItemService) {
        this.groceryItemService = groceryItemService;
    }

    @PostMapping("/addGroceryItem")
    public ResponseEntity<GroceryItem> addGroceryItem(@RequestBody GroceryItem groceryItem) {
        GroceryItem addedItem = groceryItemService.addGroceryItem(groceryItem);
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @GetMapping("/getAllGroceryItems")
    public ResponseEntity<List<GroceryItem>> getAllGroceryItems() {
        List<GroceryItem> groceryItems = groceryItemService.getAllGroceryItems();
        return new ResponseEntity<>(groceryItems, HttpStatus.OK);
    }

    @DeleteMapping("/removeGroceryItem/{id}")
    public ResponseEntity<String> removeGroceryItem(@PathVariable Long id) {
        boolean removed = (boolean) groceryItemService.removeGroceryItem(id);
        if (removed) {
            return new ResponseEntity<>("Grocery item removed successfully", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Grocery item not found", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/updateGroceryItem/{id}")
    public ResponseEntity<GroceryItem> updateGroceryItem(@PathVariable Long id, @RequestBody GroceryItem updatedItem) {
        GroceryItem updatedGroceryItem = groceryItemService.updateGroceryItem(id, updatedItem);
        return new ResponseEntity<>(updatedGroceryItem, HttpStatus.OK);
    }

    @PutMapping("/updateInventory/{id}")
    public ResponseEntity<Void> updateInventory(@PathVariable Long id, @RequestParam int newQuantity) {
        groceryItemService.updateInventory(id, newQuantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
