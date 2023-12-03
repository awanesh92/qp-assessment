package com.awanesh.APIGrocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.awanesh.APIGrocery.entity.GroceryItem;
import com.awanesh.APIGrocery.repository.GroceryItemRepository;

@Service
public class GroceryItemService {

    private final GroceryItemRepository groceryItemRepository;

    @Autowired
    public GroceryItemService(GroceryItemRepository groceryItemRepository) {
        this.groceryItemRepository = groceryItemRepository;
    }

    public GroceryItem addGroceryItem(GroceryItem groceryItem) {
        return groceryItemRepository.save(groceryItem);
    }

    public List<GroceryItem> getAllGroceryItems() {
        return groceryItemRepository.findAll();
    }

    public boolean removeGroceryItem(Long groceryItemId) {
    	Optional<GroceryItem> optionalItem = groceryItemRepository.findById(groceryItemId);
        if (optionalItem.isPresent()) {
            groceryItemRepository.deleteById(groceryItemId);
            return true; 
        } else {
            return false;
        }
    }

    public GroceryItem updateGroceryItem(Long groceryItemId, GroceryItem updatedItem) {
        GroceryItem existingItem = groceryItemRepository.findById(groceryItemId)
                .orElseThrow(() -> new RuntimeException("Grocery item not found"));

        existingItem.setName(updatedItem.getName());
        existingItem.setPrice(updatedItem.getPrice());
        existingItem.setInventory(updatedItem.getInventory());

        return groceryItemRepository.save(existingItem);
    }

    public void updateInventory(Long groceryItemId, int newQuantity) {
        GroceryItem existingItem = groceryItemRepository.findById(groceryItemId)
                .orElseThrow(() -> new RuntimeException("Grocery item not found"));

        existingItem.setInventory(newQuantity);

        groceryItemRepository.save(existingItem);
    }
}

