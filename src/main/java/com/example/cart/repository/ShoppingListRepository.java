package com.example.cart.repository;

import com.example.cart.domain.ShoppingList;

public interface ShoppingListRepository {
    public ShoppingList getShoppingList(String id);
}