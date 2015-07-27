package com.example.cart.service;

import java.net.URL;

import com.example.cart.domain.ShoppingList;

public interface ShoppingListProcessorService {
    public URL processShoppingList(ShoppingList shoppingList) throws Exception;
}
