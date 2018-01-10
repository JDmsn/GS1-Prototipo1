package com.example.jm.buywithme.Model;

/**
 * Created by jm on 17.12.23.
 */

public class Product {
    private int id;
    private String name;
    private String section;
    private boolean intoBasket;

    public Product(int id, String name, String section, boolean intoBasket) {
        this.id = id;
        this.name = name;
        this.section = section;
        this.intoBasket = intoBasket;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public boolean getIntoBasket() {
        return intoBasket;
    }

    public void setIntoBasket(boolean intoBasket) {
        this.intoBasket = intoBasket;
    }
}
