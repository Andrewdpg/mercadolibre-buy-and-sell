package model;

import java.text.ParseException;
import java.util.ArrayList;

public class InventoryOrderTest {
    Inventory inventory;

    public void setupStage1() {
        inventory = new Inventory();
    }

    public void setupStage2() throws ParseException {
        inventory = new Inventory();

        ArrayList<Product> productsA = new ArrayList<>();
        productsA.add(new Product("A", "desc", 5, 10));

        ArrayList<Product> productsB = new ArrayList<>();
        productsA.add(new Product("B", "desc", 5, 10));
        productsB.add(new Product("C", "desc", 5, 10));

        inventory.addOrder("Alfonso", productsA, "07/02/2022");
        inventory.addOrder("Enrique", productsA, "14/02/2022");
    }
}
