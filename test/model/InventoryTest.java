package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.IllegalFormatException;

import org.apiguardian.api.API;
import org.junit.Test;

import exceptions.NotNumberNegative;
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

    public void setupStage4() {
        inventory = new Inventory();
        inventory.addProduct("Tablet samsung S7", "tablet blanca", 600000, 0, 2);
    }

    /* revisar */
    @Test
    public void lessQuantityProduct() {
        setupStage3();
        ArrayList<Product> result = inventory.getProducts();
        inventory.decreaseQuantityProduct("Smart Watch Essen", 5);
        assertTrue(5, result.get(0).getQuantity);
    }

    @Test
    public void decreaseNegativaQuantity() {
        try {
            setupStage3();
            inventory.decreaseQuantityProduct("Smart Watch Essen", -5);

        } catch (NotNumberNegative ne) {
            assertTrue(true);
        }
    }

    public void decreseInNegative() {
        try {
            setupStage3();
            inventory.decreaseQuantityProduct("Smart Watch Essen", 15);

        } catch (NotNumberNegative ne) {
            assertTrue(true);
        }
    }

    @Test
    public void increaseQuantityProduct() {
        setupStage3();
        inventory.increaseQuantityProduct("Smart Watch Essen", 20);
        assertTrue(30, result.get(0).getQuantity);
    }

    @Test
    public void increaseNegativeNumber() {
        try {
            setupStage3();
            inventory.increaseQuantityProduct("Smart Watch Essen", -10);

        } catch (NotNumberNegative ne) {
            assertTrue(true);
        }
    }

    @Test
    public void addProductWhithoutCant() {
        setupStage4();
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
    public void addProductNegativaQuantity(){
        try{
            setupStage1();
            inventory.addProduct("Desodorante", "Descripción", 2, -10, 6); 
        }catch (NotNumberNegative ae){
            assertTrue(true);
        }
        
    }

    @Test
    public void addNotFormat(){
        try{
            setupStage1();
            inventory.addProduct("Desodorante", "Descripción", 2, "str", 6); 
        }catch (NumberFormatException nf){
            assertTrue(true);
        }
    }

    @Test
    public void addOutParameter(){
        try{
            setupStage1();
            inventory.addProduct("Desodorante", "Descripción", 2, "str", 20); 
        }catch (IllegalFormatException ife){
            assertTrue(true);
        }
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
        ArrayList<Product> result = inventory.searchProductBy("name", true, new Filter(2, 2, "category"));
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
