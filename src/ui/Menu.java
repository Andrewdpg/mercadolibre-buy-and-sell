package ui;
import model.Inventory;
import model.Product;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.rules.TestRule;

import model.CategoryProduct;

public class Menu{
        private final String MAIN_MENU= "1. Agregar producto al inventario\n"
                +"2. Generar orden\n"
                +"3. Buscar producto u orden\n"
                +"4. Salir";
        private final String FIRSTSEARCH_MENU= "¿Sobre que deseas buscar?:\n"
                +"1. Productos\n"
                +"2. Órdenes";
        private final String SECONDSEARCHPRODUCT_MENU= "Buscar por:\n"
                +"1. Nombre\n"
                +"2. Precio\n"
                +"3. Categoría\n"
                +"4. Número de veces comprados";
        private final String SECONDSEARCHORDER_MENU= "Buscar por:\n"
                +"1. Nombre del comprador\n"
                +"2. Precio total\n"
                +"3. Fecha de compra\n"
                +"4. Veces compradas";
        private final String THIRDSEARCH_MENU="Buscar de manera:\n"
                +"1. Ascedente\n"
                +"2. Descendente";
        private final String FOURTHSEARCHPRODUCT_MENU= "Buscar producto por ésta característica:\n"
                +"1. Nombre (Alfabético)\n"
                +"2. Descripción (Alfabético)\n"
                +"3. Categoria\n"
                +"4. Precio\n"
                +"5. Cantidad disponible\n"
                +"6. Número de veces comprado";
        private final String FOURTHSEARCHORDER_MENU= "Buscar orden por ésta característica:\n"
                +"1. Nombre del comprador (Alfabético)\n"
                +"2. Número de productos comprados\n"
                +"3. Precio total de compra\n"
                +"4. Fecha de compra";
        private final String AFTERSEARCHPRODUCT_MENU= "Opciones:\n"
                +"1. Editar cantidad disponible\n"
                +"2. Eliminar de inventario\n"
                +"3. Regresar al menú principal";

        private int option;
        private Inventory mercado;
        private boolean isRunning;
        
        public Menu(){
                isRunning = true;
                mercado = new Inventory();
        }

        private void addProduct(){       
                String name, desc; 
                int cat;
                double price;
                int quantity; 
                System.out.println("Nombre del producto: ");
                name = Reader.readString();
                System.out.println("Descripción del producto: ");
                desc = Reader.readString();
                System.out.println("Precio del producto:");
                price = Reader.readDouble();
                System.out.println("Unidades disponibles: ");
                quantity = Reader.readInt(0);
                System.out.println("Elige la categoría del producto: ");
                System.out.println(CategoryProduct.LIST_OF_CATEGORIES);
                cat= Reader.readInt(0);
                mercado.addProduct(name, desc, price, quantity, cat);
        }

        private void addOrder(){
                String name, date;
                ArrayList<Product> list=null;
                System.out.println("Nombre del comprador: ");
                name = Reader.readString();
                System.out.println("Fecha de la compra (dd/MM/yyyy): ");
                date = Reader.readString();
                try {
                        mercado.addOrder(name, list, date);
                } catch (ParseException e) {
                        e.printStackTrace();
                }
        }

        private void searchThing(){
                System.out.println(FIRSTSEARCH_MENU);
                switch (option) {
                        case 1:
                                searchProduct();
                                break;
                        case 2:
                                searchOrder();
                                break;
                        default:
                                System.out.println("Opción no válida");
                                break;
                }

        }

        private void searchProduct(){
                Object top, bot;
                boolean asc = true;
                String attr;
                String variable;
                System.out.println(SECONDSEARCHPRODUCT_MENU);
                switch (option) {
                        case 1:
                                attr = "name";
                                break;
                        case 2:
                                attr= "price";
                                break;
                        case 3:
                                attr= "category";
                                break;
                        case 4:
                                attr= "quantity";
                                break;
                        default:
                                System.out.println("Opción no valida");
                                break;
                }
                wayToSearch(asc);
                System.out.println(FOURTHSEARCHPRODUCT_MENU);
                switch (option) {
                        case 1:
                                variable = "name";
                                break;
                        case 2:
                                variable= "price";
                                break;
                        case 3:
                                variable= "category";
                                break;
                        case 4:
                                variable= "quantity";
                                break;
                        default:
                                System.out.println("Opción no valida");
                                break;
                }
                //mercado.searchProductBy(top, bot, attr, variable, asc);
                
        }
        private void searchOrder(){
                Object top, bot;
                boolean asc = true;
                String attr;
                String variable;
                System.out.println(SECONDSEARCHPRODUCT_MENU);
                switch (option) {
                        case 1:
                                attr = "name";
                                break;
                        case 2:
                                attr= "price";
                                break;
                        case 3:
                                attr= "category";
                                break;
                        case 4:
                                attr= "quantity";
                                break;
                        default:
                                System.out.println("Opción no valida");
                                break;
                }
                wayToSearch(asc);
                System.out.println(FOURTHSEARCHPRODUCT_MENU);
                switch (option) {
                        case 1:
                                variable = "name";
                                break;
                        case 2:
                                variable= "price";
                                break;
                        case 3:
                                variable= "category";
                                break;
                        case 4:
                                variable= "quantity";
                                break;
                        default:
                                System.out.println("Opción no valida");
                                break;
                }
        }
        private void wayToSearch(boolean asc){
                System.out.println(THIRDSEARCH_MENU);
                switch (option) {
                        case 1:
                                asc=true;
                                break;
                        case 2:
                                asc=false;
                                break;
                        default:
                                asc=true;
                                break;
                }
        }

        public void printMenu(){
                System.out.println(MAIN_MENU);
        }

        public void executeInput(){
                executeMainMenu();
        }

        private void executeMainMenu(){
                switch(option){
                        case 1:
                                addProduct();
                                break;
                        case 2:
                                addOrder();
                                break;
                        case 3:
                                searchThing();
                                break;
                        case 4:
                                isRunning= false;
                                System.out.println("Cerrando...");
                                break;
                        default:
                                System.out.println("Opción no reconocida");
                                break;
                }
        }

        public boolean isRunning() {
                return isRunning;
        }
        public void readOption() {
                option = Reader.readInt(-1);
        }
}