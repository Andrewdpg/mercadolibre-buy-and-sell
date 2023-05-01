package model.test;

import model.Inventory;
import model.Product;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import junit.framework.TestCase;

public class InventoryTest {

    public void setupStage1() {
        Inventory empty = new Inventory();

    }

    public void setupStage2(){
        Inventory util = new Inventory();
        util.addProduct("Smart Watch Essen", "Reloj inteligente negro", 250000, 10, 2);
        util.addProduct("Balon golty", "balon para futbol profesional blanco", 80000, 10, 6);
    }

    public void setupStage3(){
        Inventory util = new Inventory();
        util.addProduct("Smart Watch Essen", "Reloj inteligente negro", 250000, 10, 2);
        util.addProduct("Balon golty", "balon para futbol profesional blanco", 80000, 10, 6);
        util.addProduct("Alexa","Description3", 250000, 50, 2);
    }

    @Test
    public void deleteTest(){
        try{
            setupStage1();
            Inventory empty = new Inventory();
            empty.deleteProduct("mandarina", 10);
        }catch (NullPointerException  ae) {
            assertTrue(true);
        }


    }


}
