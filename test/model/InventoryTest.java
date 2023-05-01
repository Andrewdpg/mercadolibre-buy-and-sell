package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InventoryTest {
    Inventory inventory;

    public void setupStage1() {
        inventory = new Inventory();
    }

    public void setupStage2() {
        Inventory util = new Inventory();
        util.addProduct("Smart Watch Essen", "Reloj inteligente negro", 250000, 10, 2);
        util.addProduct("Balon golty", "balon para futbol profesional blanco", 80000, 10, 6);
    }

    public void setupStage3() {
        Inventory util = new Inventory();
        util.addProduct("Smart Watch Essen", "Reloj inteligente negro", 250000, 10, 2);
        util.addProduct("Balon golty", "balon para futbol profesional blanco", 80000, 10, 6);
        util.addProduct("Alexa", "Description3", 250000, 50, 2);
    }

    @Test
    public void deleteTest() {
        setupStage1();
        String result = inventory.deleteProduct("mandarina");
        assertEquals("There was no product with that name",result);
    }

}
