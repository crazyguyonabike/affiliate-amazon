package com.example.cart.domain;

public class Item {
    private String id;
    private String name;
    private long quantity;

    public Item() {
        this(null, null, 0);
    }

    public Item(String id, String name, long quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}


