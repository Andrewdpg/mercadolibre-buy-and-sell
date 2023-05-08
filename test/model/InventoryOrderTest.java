package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import exceptions.NotNumberNegative;
import util.Filter;

public class InventoryOrderTest {
    Inventory inventory;

    public void setupStage1() {
        inventory = new Inventory();
        inventory.addProduct("A", "desc", 50, 100, 3);
    }

    public void setupStage2() throws ParseException {
        inventory = new Inventory();

        inventory.addProduct("A", "desc", 5, 100, 3);
        inventory.addProduct("B", "desc", 5, 100, 3);
        inventory.addProduct("C", "desc", 5, 100, 3);

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
        inventory.addProduct("A", "desc", 250000, 100, 3);
        inventory.addProduct("B", "desc", 15000, 100, 3);
        inventory.addProduct("C", "desc", 80000, 100, 3);
        inventory.addProduct("D", "desc", 13000, 100, 3);
        inventory.addProduct("F", "desc", 500000, 100, 3);

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
    public void createOrderWithBadFormatDate() throws ParseException {
        setupStage1();
        ArrayList<Product> orderOne = new ArrayList<>();
        orderOne.add(new Product("A", "Descrip", 50, 2));
        assertThrows(ParseException.class,
                () -> inventory.addOrder("Alfonso", orderOne, "2022/01/67"));
    }

    @Test
    public void createOrderWithBadFormatDate2() throws ParseException {
        setupStage1();
        ArrayList<Product> orderOne = new ArrayList<>();
        orderOne.add(new Product("A", "Descrip", 50, 2));
        assertThrows(ParseException.class,
                () -> inventory.addOrder("Alfonso", orderOne, "01/12/AA67"));
    }

    @Test
    public void createOrderWithBadFormatDate3() throws ParseException {
        setupStage1();
        ArrayList<Product> orderOne = new ArrayList<>();
        orderOne.add(new Product("A", "Descrip", 50, 2));
        assertThrows(ParseException.class,
                () -> inventory.addOrder("Alfonso", orderOne, "2/2/xx2"));
    }

    @Test
    public void createOrderWithBadFormatDate4() throws ParseException {
        setupStage1();
        ArrayList<Product> orderOne = new ArrayList<>();
        orderOne.add(new Product("A", "Descrip", 50, 2));
        assertThrows(ParseException.class,
                () -> inventory.addOrder("Alfonso", orderOne, "2/y2x/2034"));
    }

    @Test
    public void addProductWithWrongQuantity() {
        setupStage1();
        inventory.addProduct("chocokrispys", "Cereal de choco", 18000, 5, 4);
        ArrayList<Product> order = new ArrayList<>();
        order.add(new Product("chocokrispys", "Cereal de choco", 18000, 15));
        assertThrows(NotNumberNegative.class,
                () -> inventory.addOrder("Mads", order, "12/05/2022"));
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
        assertEquals("Enrique", result.get(0).getbName());
    }

    @Test
    public void searchTotalPurchased() throws ParseException {
        setupStage3();
        ArrayList<Order> result = inventory.searchOrderBy("name", false, new Filter(900000, 10, "total price"));
        assertEquals(4, result.size());
    }

    @Test
    public void searchNotTotalPurchased() throws ParseException {
        setupStage3();
        ArrayList<Order> result = inventory.searchOrderBy("name", false, new Filter(1, 1, "total price"));
        assertNull(result);
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
        assertNull(result);
    }

    @Test
    public void ascendentPrice() throws ParseException {
        setupStage3();
        ArrayList<Order> result = inventory.searchOrderBy("total price", true,
                new Filter(251000, 80000, "total price"));
        assertEquals(2, result.size());
    }

    @Test
    public void disorderPrice() throws ParseException {
        setupStage3();
        ArrayList<Order> result = inventory.searchOrderBy("total price", false,
                new Filter(251000, 80000, "total price"));
        assertEquals(2, result.size());
    }

    @Test
    public void ascendentInRangeDate() throws ParseException {
        setupStage3();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fin = dateFormat.parse("15/02/2022");
        Date ini = dateFormat.parse("13/02/2022");
        ArrayList<Order> result = inventory.searchOrderBy("name", true,
                new Filter(fin, ini, "date"));
        assertEquals(3, result.size());
        assertEquals("Alfosno", result.get(0).getbName());
        assertEquals("Enrique", result.get(1).getbName());
        assertEquals("Pedro", result.get(2).getbName());
    }

    @Test
    public void descendentInRangeDate() throws ParseException {
        setupStage3();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fin = dateFormat.parse("15/02/2022");
        Date ini = dateFormat.parse("13/02/2022");
        ArrayList<Order> result = inventory.searchOrderBy("name", false,
                new Filter(fin, ini, "date"));
        assertEquals(3, result.size());
        assertEquals("Pedro", result.get(0).getbName());
        assertEquals("Enrique", result.get(1).getbName());
        assertEquals("Alfosno", result.get(2).getbName());
    }

    @Test
    public void ascendentInRangeDateAndName() throws ParseException {
        setupStage3();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<Order> result = inventory.searchOrderBy("date", true,
                new Filter("F", "A", "name"));
        assertEquals(2, result.size());
        assertEquals("Enrique", result.get(1).getbName());
        assertEquals("Alfosno", result.get(0).getbName());
    }

    @Test
    public void descendentInRangeDateAndName() throws ParseException {
        setupStage3();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<Order> result = inventory.searchOrderBy("date", false,
                new Filter("F", "A", "name"));
        assertEquals(2, result.size());
        assertEquals("Alfosno", result.get(0).getbName());
        assertEquals("Enrique", result.get(1).getbName());
    }

    @Test
    public void compareOrders() {
        setupStage1();
        ArrayList<Product> order1 = new ArrayList<>();
        order1.add(new Product("product", "Descript", 80000, 5));

        ArrayList<Product> order2 = new ArrayList<>();
        order2.add(new Product("product2", "Descript", 20000, 2));

        assertTrue(order1.get(0).compare(order2.get(0), "price") > 0);

    }

    @Test
    public void compareOrders2() {
        setupStage1();
        ArrayList<Product> order1 = new ArrayList<>();
        order1.add(new Product("product", "Descript", 80000, 5));

        ArrayList<Product> order2 = new ArrayList<>();
        order2.add(new Product("product2", "Descript", 20000, 2));

        assertFalse(order1.get(0).compare(order2.get(0), "price") < 0);

    }

    @Test
    public void compareOrders3() {
        setupStage1();
        ArrayList<Product> order1 = new ArrayList<>();
        order1.add(new Product("product", "Descript", 80000, 5));

        ArrayList<Product> order2 = new ArrayList<>();
        order2.add(new Product("product2", "Descript", 20000, 2));

        assertTrue(order1.get(0).compare(order2.get(0), "name") < 0);

    }
}