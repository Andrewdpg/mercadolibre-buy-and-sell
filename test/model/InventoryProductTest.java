package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import exceptions.NotNumberNegative;
import util.Filter;

public class InventoryProductTest {
    Inventory inventory;

    public void setupStage1() {
        inventory = new Inventory();
    }

    public void setupStage2() {
        inventory = new Inventory();
        inventory.addProduct("Smart Watch Essen", "Reloj inteligente negro", 250000, 10, 2);
        inventory.addProduct("Balon golty", "balon para futbol profesional blanco", 80000, 10, 6);
    }

    public void setupStage3() {
        inventory = new Inventory();
        inventory.addProduct("Smart Watch Essen", "Reloj inteligente negro", 250000, 10, 2);
        inventory.addProduct("Balon golty", "balon para futbol profesional blanco", 80000, 10, 6);
        inventory.addProduct("Alexa", "Description3", 250000, 50, 2);
    }

    @Test
    public void lessQuantityProduct() {
        setupStage3();
        inventory.decreaseQuantityProduct("Smart Watch Essen", 5);
        int result = inventory.getProducts().get(0).getQuantity();
        assertEquals(5, result);
    }

    @Test
    public void decreaseNegativeQuantity() {
        setupStage3();
        assertThrows(NotNumberNegative.class,
                () -> inventory.decreaseQuantityProduct("Smart Watch Essen", -5));
    }

    @Test
    public void decreseInNegative() {
        setupStage3();
        assertThrows(NotNumberNegative.class,
                () -> inventory.decreaseQuantityProduct("Smart Watch Essen", 15));
    }

    @Test
    public void decreseNotAfect() {
        setupStage3();
        assertEquals("the new quantity for product: Balon golty is: 10",
                inventory.decreaseQuantityProduct("Balon golty", 0));
    }

    @Test
    public void increaseQuantityProduct() {
        setupStage3();
        inventory.increaseQuantityProduct("Smart Watch Essen", 10);
        int result = inventory.getProducts().get(0).getQuantity();
        assertEquals(20, result);
    }

    @Test
    public void increaseNotFound() {
        setupStage1();
        String result = inventory.increaseQuantityProduct("NONE", 24);
        assertEquals("There was no product with that name", result);
    }

    @Test
    public void increaseNegativeNumber() {
        setupStage3();
        assertThrows(NotNumberNegative.class,
                () -> inventory.increaseQuantityProduct("Smart Watch Essen", -10));
    }

    @Test
    public void addProductWhithoutCant() {
        setupStage1();
        inventory.addProduct("Tablet samsung S7", "tablet blanca", 600000, 0, 2);
        boolean result = inventory.productExists("Tablet samsung S7");
        assertTrue(result);
    }

    @Test
    public void addFirstProductTest() {
        setupStage1();
        inventory.addProduct("Desodorante", "Descripción", 2, 10, 6);
        assertEquals(1, inventory.getProducts().size());
        assertEquals("Desodorante", inventory.getProducts().get(0).getName());
        assertEquals(10, inventory.getProducts().get(0).getQuantity());
    }

    @Test
    public void addProductNegativaQuantity() {
        setupStage1();
        assertThrows(NotNumberNegative.class,
                () -> inventory.addProduct("Desodorante", "Descripción", 2, -10, 6));
    }

    @Test
    public void searchNonExistentTest() {
        setupStage1();
        boolean result = inventory.productExists("Mandarina");
        assertFalse(result);
    }

    @Test
    public void searchProductTest() {
        setupStage3();
        boolean result = inventory.productExists("Alexa");
        assertTrue(result);
    }

    @Test
    public void searchProductByCategoryTest() {
        setupStage3();
        ArrayList<Product> result = inventory.searchProductBy("name", true, new Filter(
                CategoryProduct.chooseCategory(2), CategoryProduct.chooseCategory(2), "category"));
        assertEquals(2, result.size());
        assertEquals("Alexa", result.get(0).getName());
        assertEquals("Smart Watch Essen", result.get(1).getName());
    }

    @Test
    public void searchProductByCategoryNonUsedTest() {
        setupStage3();
        ArrayList<Product> result = inventory.searchProductBy("name", true, new Filter(
                CategoryProduct.chooseCategory(1), CategoryProduct.chooseCategory(1), "category"));
        assertEquals(null, result);
    }

    @Test
    public void searchProductByCategoryTest2() {
        setupStage2();
        ArrayList<Product> result = inventory.searchProductBy("name", true, new Filter(
                CategoryProduct.chooseCategory(6), CategoryProduct.chooseCategory(6), "category"));
        assertEquals(1, result.size());
        assertEquals("Balon golty", result.get(0).getName());
    }

    @Test
    public void searchProductInRangePrice() {
        setupStage3();
        ArrayList<Product> result = inventory.searchProductBy("name", true, new Filter(
                90000, 70000, "price"));
        assertEquals(1, result.size());
        assertEquals("Balon golty", result.get(0).getName());
    }

    @Test
    public void searchProductInRangeNonExistentPrice() {
        setupStage3();
        ArrayList<Product> result = inventory.searchProductBy("name", true, new Filter(
                0, 100, "price"));
        assertNull(null, result);
    }

    @Test
    public void searchProductInRangeNonExistentName() {
        setupStage2();
        String ini = "x";
        String fin = "y";
        ArrayList<Product> result = inventory.searchProductBy("name", true, new Filter(
                ini, fin, "name"));
        assertNull(null, result);
    }

    @Test
    public void searchProductInRangeName() {
        setupStage2();
        ArrayList<Product> result = inventory.searchProductBy("name", true, new Filter(
                "t", "n", "name"));
        assertEquals(1, result.size());
        assertEquals("Smart Watch Essen", result.get(0).getName());
    }

    @Test
    public void searchProductInAscendingWay() {
        setupStage3();
        int top = 50;
        int bot = 0;
        ArrayList<Product> result = inventory.searchProductBy("name", true, new Filter(
                top, bot, "quantity"));
        assertEquals(3, result.size());
        assertEquals("Alexa", result.get(0).getName());
        assertEquals("Balon golty", result.get(1).getName());
        assertEquals("Smart Watch Essen", result.get(2).getName());
    }

    @Test
    public void searchProductInDescendingWay() {
        setupStage3();
        int top = 50;
        int bot = 0;
        ArrayList<Product> result = inventory.searchProductBy("name", false, new Filter(
                top, bot, "quantity"));
        assertEquals(3, result.size());
        assertEquals("Smart Watch Essen", result.get(0).getName());
        assertEquals("Balon golty", result.get(1).getName());
        assertEquals("Alexa", result.get(2).getName());
    }

    @Test
    public void searchProductInRangeNegative() {
        setupStage3();
        ArrayList<Product> result = inventory.searchProductBy("name", false, new Filter(
                -5, -10000, "quantity"));
        assertNull(null, result);
    }

    @Test
    public void searchProductInPriceCategoryDescending() {
        setupStage2();
        ArrayList<Product> result = inventory.searchProductBy("category", false, new Filter(
                251000, 80000, "price"));
        assertEquals(2, result.size());
        assertEquals("Balon golty", result.get(0).getName());
        assertEquals("Smart Watch Essen", result.get(1).getName());
    }

    @Test
    public void searchProductInPriceCategoryAscending() {
        setupStage2();
        ArrayList<Product> result = inventory.searchProductBy("category", true, new Filter(
                251000, 80000, "price"));
        assertEquals(2, result.size());
        assertEquals("Smart Watch Essen", result.get(0).getName());
        assertEquals("Balon golty", result.get(1).getName());
    }

    @Test
    public void addProductToRepeatedCategoryTest() {
        setupStage2();
        inventory.addProduct("Alexa", "Description3", 250000, 50, 2);
        ArrayList<Product> result = inventory.searchProductBy("name", true,
                new Filter(CategoryProduct.chooseCategory(2),
                        CategoryProduct.chooseCategory(2), "category"));
        assertEquals(3, inventory.getProducts().size());
        assertEquals(2, result.size());
        assertEquals("Alexa", result.get(0).getName());
        assertEquals(50, result.get(0).getQuantity());
    }

    @Test
    public void deleteNonExistentTest() {
        setupStage1();
        String result = inventory.deleteProduct("Alexa");
        assertEquals("There was no product with that name", result);
    }

    @Test
    public void deleteTest() {
        setupStage3();
        String result = inventory.deleteProduct("Alexa");
        assertEquals("Product succsesfully deleted", result);
        assertEquals(2, inventory.getProducts().size());
    }

    @Test
    public void deleteAllElements() {
        setupStage3();
        String result = inventory.deleteProduct("Alexa");
        String result2 = inventory.deleteProduct("Smart Watch Essen");
        String result3 = inventory.deleteProduct("Balon golty");
        assertEquals("Product succsesfully deleted", result);
        assertEquals("Product succsesfully deleted", result2);
        assertEquals("Product succsesfully deleted", result3);
        assertEquals(0, inventory.getProducts().size());
    }
    

    
}
