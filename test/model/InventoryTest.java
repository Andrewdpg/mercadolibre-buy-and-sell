package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import util.Filter;

public class InventoryTest {
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
    public void addFirstProductTest() {
        setupStage1();
        inventory.addProduct("Desodorante", "Descripción", 2, 10, 6);
        assertEquals(1, inventory.getProducts().size());
        assertEquals("Desodorante", inventory.getProducts().get(0).getName());
        assertEquals(10,inventory.getProducts().get(0).getQuantity());
    }

    @Test
    public void searchNonExistentTest() {
        setupStage1();
        boolean result = inventory.productExists("Alexa");
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
        ArrayList<Product> result = inventory.searchProductBy("name", true, new Filter(2, 2, "category"));
        assertEquals(2, result.size());
        assertEquals("Alexa", result.get(0).getName());
        assertEquals("Smart Watch Essen", result.get(1).getName());
    }

    @Test
    public void addProductToRepeatedCategoryTest() {
        setupStage2();
        inventory.addProduct("Alexa", "Description3", 250000, 50, 2);
        ArrayList<Product> result =  inventory.searchProductBy("name",true, new Filter(2, 2, "category"));
        assertEquals(3, inventory.getProducts().size());
        assertEquals(2, result.size());
        assertEquals("Alexa", result.get(0).getName());
        assertEquals(10, result.get(0).getQuantity());
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
}
