package com.example.cart.domain;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
    private String id;
    private List<Item> items;

    public ShoppingList() {
        this(new ArrayList<Item>());
    }

    public ShoppingList(List<Item> items) {
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
}
    
    
    
