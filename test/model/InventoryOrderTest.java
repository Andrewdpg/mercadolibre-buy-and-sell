package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import util.Filter;

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

    public void setupStage3() throws ParseException {
        inventory = new Inventory();

        ArrayList<Product> orderA = new ArrayList<>();
        orderA.add(new Product("A", "Descrip", 250000, 1));

        ArrayList<Product> orderB = new ArrayList<>();
        orderB.add(new Product("B", "Descript", 15000, 1));
        orderB.add(new Product("C", "Descrip", 80000, 1));

        ArrayList<Product> orderC = new ArrayList<>();
        orderC.add(new Product("D", "Descript", 13000, 1));
        orderC.add(new Product("A", "Descrip", 250000, 1));

        ArrayList<Product> orderD = new ArrayList<>();
        orderD.add(new Product("F", "Descript", 500000, 1));
        orderD.add(new Product("C", "Descrip", 80000, 1));

        inventory.addOrder("Alfosno", orderA, "14/02/2022");
        inventory.addOrder("Enrique", orderB, "14/02/2022");
        inventory.addOrder("Pedro", orderC, "13/02/2022");
        inventory.addOrder("Francis", orderD, "17/02/2022");

    }

    @Test
    public void createOrder() throws ParseException {
        setupStage1();
        ArrayList<Product> orderOne = new ArrayList<>();
        orderOne.add(new Product("A", "Descrip", 50, 2));
        inventory.addOrder("Alfonso", orderOne, "13/05/2022");
        assertEquals(1, inventory.getOrders().size());
    }

    @Test
    public void addOrders() throws ParseException {
        setupStage2();
        ArrayList<Product> thirdOrder = new ArrayList<>();
        thirdOrder.add(new Product("A", "descrip", 15000, 2));
        inventory.addOrder("Alfonso", thirdOrder, "13/02/2022");
        assertEquals(3, inventory.getOrders().size());
    }

    @Test
    public void searchNotExistentOrder() {
        setupStage1();
        assertNull(inventory.searchOrderBy("name", true,
                new Filter(new SimpleDateFormat("12/02/2022"), new SimpleDateFormat("12/02/2022"), "date")));
    }

    @Test
    public void searchOrder() throws ParseException {
        setupStage2();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date purchasedDate = dateFormat.parse("14/02/2022");
        ArrayList<Order> result = inventory.searchOrderBy("name", true,
                new Filter(purchasedDate, purchasedDate, "date"));
        assertEquals("Enrique",result.get(0).getbName());
    }

    @Test
    public void searchTotalPurchased() throws ParseException {
        setupStage3();
        ArrayList<Order> result = inventory.searchOrderBy("name", false, new Filter(900000  ,10 , "total price"));
        assertEquals(4, result.size());
    }

    @Test
    public void searchNotTotalPurchased() throws ParseException {
        setupStage3();
        ArrayList<Order> result = inventory.searchOrderBy("name", false, new Filter(1, 1, "total price"));
        assertNull( result);
    }

    
    @Test
    public void searchOrderByInitialName() throws ParseException {
        setupStage3();
        ArrayList<Order> result = inventory.searchOrderBy("name", true, new Filter("D", "A", "name"));
        assertEquals(1, result.size());
    }

    @Test
    public void searchOrderByInitialName2() throws ParseException {
        setupStage3();
        ArrayList<Order> result = inventory.searchOrderBy("name", true, new Filter("z", "x", "name"));
        assertNull( result);
    }

    @Test
    public void ascendentPrice() throws ParseException {
        setupStage3();
        ArrayList<Order> result = inventory.searchOrderBy("total price", true,
                new Filter(251000   ,80000 , "total price"));
        assertEquals(2, result.size());
    }

    @Test
    public void disorderPrice() throws ParseException {
        setupStage3();
        ArrayList<Order> result = inventory.searchOrderBy("total price", false,
                new Filter ( 251000 , 80000, "total price"));
        assertEquals(2, result.size());
    }
    
}