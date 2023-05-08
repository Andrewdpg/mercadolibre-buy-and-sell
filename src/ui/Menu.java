package ui;

import java.text.ParseException;
import java.util.ArrayList;

import exceptions.NotNumberNegative;
import model.CategoryProduct;
import model.Inventory;
import model.Order;
import model.Product;
import util.Filter;

public class Menu {
    private final String MAIN_MENU = "1. Agregar producto al inventario\n"
            + "2. Generar orden\n"
            + "3. Buscar producto u orden\n"
            + "4. Salir";
    private final String FIRSTSEARCH_MENU = "¿Sobre que deseas buscar?:\n"
            + "1. Productos\n"
            + "2. Órdenes";
    private final String SECONDSEARCHPRODUCT_MENU = "Ordenar por:\n"
            + "1. Nombre\n"
            + "2. Precio\n"
            + "3. Categoría\n"
            + "4. Número de veces comprados";
    private final String THIRDSEARCH_MENU = "Buscar de manera:\n"
            + "1. Ascedente\n"
            + "2. Descendente";
    private final String FOURTHSEARCHPRODUCT_MENU = "Buscar producto por ésta característica:\n"
            + "1. Nombre\n"
            + "2. Precio\n"
            + "3. Categoría\n"
            + "4. Número de veces comprados";
    private final String FOURTHSEARCHORDER_MENU = "Buscar orden por ésta característica:\n"
            + "1. Nombre del comprador (Alfabético)\n"
            + "2. Número de productos comprados\n"
            + "3. Precio total de compra\n"
            + "4. Fecha de compra";
    private final String AFTERSEARCHPRODUCT_MENU = "Opciones:\n"
            + "1. Editar cantidad disponible\n"
            + "2. Eliminar de inventario\n"
            + "3. Regresar al menú principal";

    private int option;
    private Inventory mercado;
    private boolean isRunning;

    public Menu() {
        isRunning = true;
        mercado = new Inventory();
    }

    private void addProduct() throws NotNumberNegative {
        String name, desc;
        int cat;
        double price;
        int quantity;
        System.out.println("Nombre del producto: ");
        name = Reader.readString();
        System.out.println("Descripción del producto: ");
        desc = Reader.readString();
        System.out.println("Precio del producto:");
        price = Reader.readDouble(5.0);
        System.out.println("Unidades disponibles: ");
        quantity = Reader.readInt(0);
        System.out.println("Elige la categoría del producto: ");
        System.out.println(CategoryProduct.LIST_OF_CATEGORIES);
        cat = Reader.readInt(0);
        mercado.addProduct(name, desc, price, quantity, cat);
    }

    private void addOrder() throws NotNumberNegative {
        String name, date;
        System.out.println("Nombre del comprador: ");
        name = Reader.readString();
        System.out.println("Fecha de la compra (dd/MM/yyyy): ");
        date = Reader.readString();
        ArrayList<Product> list = selectProducts();
        try {
            mercado.addOrder(name, list, date);
        } catch (ParseException e) {
            System.out.println("Fecha inválida, orden no agregada.");
        }
    }

    private ArrayList<Product> selectProducts() {
        ArrayList<Product> products = new ArrayList<>();
        do {
            System.out.println("¡Agreguemos un producto a la orden! ");
            Product product = selectProduct();
            if (product != null)
                products.add(product);
            if (!products.isEmpty()) {
                System.out.println("¿Quieres agregar un nuevo producto?\n"
                        + "   0. No\n"
                        + "   Any other. Si");
                readOption();
            }
        } while (option != 0 || products.isEmpty());
        return products;
    }

    private Product selectProduct() {
        System.out.print("Nombre del producto: ");
        String name = Reader.readString();
        ArrayList<Product> result = mercado.searchProductBy("name", true, new Filter(name, name, "name"));
        if (result != null) {
            Product selected = result.get(0);
            if (selected.getQuantity() > 0) {
                System.out.print("Cantidad del producto (" + selected.getQuantity() + " disponible): ");
                int cant = Reader.readInt(1, 1);
                if (cant < selected.getQuantity()) {
                    Product product = new Product(selected.getName(), selected.getDesc(), selected.getPrice(), cant);
                    return product;
                }
                System.out.println("Cantidad de producto no dispoible");
                return null;
            }
            System.out.println("Producto agotado");
            return null;
        }
        System.out.println("Producto no encontrado");
        return null;
    }

    private void searchThing() throws NotNumberNegative {
        System.out.println(FIRSTSEARCH_MENU);
        readOption();
        switch (option) {
            case 1:
                ArrayList<Product> products = searchProduct();
                if (products != null && !products.isEmpty()) {
                    System.out.println("Seleccione un producto: ");
                    printListResult(products);
                    actionAfterSearch(products);
                } else {
                    System.out.println("La busqueda no ha arrojado resultados.");
                }
                break;
            case 2:
                ArrayList<Order> orders = searchOrder();
                if (orders != null && !orders.isEmpty()) {
                    printListResult(orders);
                } else {
                    System.out.println("La busqueda no ha arrojado resultados.");
                }
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }

    private ArrayList<Product> searchProduct() {
        System.out.println("Creemos los filtros de búsqueda:");
        ArrayList<Filter> filters = new ArrayList<>();
        while (filters.size() == 0 || option != 0) {
            try {
                System.out.println("¡Agreguemos un filtro! ");
                Filter newFilter = createProductFilter();
                if (newFilter != null) {
                    filters.add(newFilter);
                }
                if (!filters.isEmpty()) {
                    System.out.println("¿Quieres agregar un nuevo filtro?\n"
                            + "   0. No\n"
                            + "   Any other. Si");
                    readOption();
                }
            } catch (ParseException e) {
                System.out.println("Formato de fecha inválido. Filtro no agregado.");
            }
        }
        System.out.println(SECONDSEARCHPRODUCT_MENU);
        readOption();
        String order = "name";
        switch (option) {
            case 1:
                order = "name";
                break;
            case 2:
                order = "price";
                break;
            case 3:
                order = "category";
                break;
            case 4:
                order = "quantity";
                break;
            default:
                System.out.println("Opción no valida. Seleccionado: Nombre");
                break;
        }
        boolean asc = wayToSearch();
        return mercado.searchProductBy(order, asc, filters.toArray(new Filter[] {}));
    }

    private Filter createProductFilter() throws ParseException {
        String variable = "name";
        Object value, init, end;
        System.out.println(FOURTHSEARCHPRODUCT_MENU);
        readOption();
        switch (option) {
            case 1:
                variable = "name";
                break;
            case 2:
                variable = "price";
                break;
            case 3:
                variable = "category";
                break;
            case 4:
                variable = "quantity";
                break;
            default:
                System.out.println("Opción no valida. Seleccionado: Nombre");
                break;
        }
        System.out.print("Desea buscar:\n"
                + "1. Valor fijo.\n"
                + "2. En rango.\n");
        readOption();
        Filter filter = null;
        switch (option) {
            case 1:
                System.out.println("Dame el valor a buscar: ");
                value = Reader.readValue(variable);
                filter = new Filter(value, value, variable);
                break;
            case 2:
                System.out.println("Dame el valor inicial del rango de búsqueda: ");
                init = Reader.readValue(variable);
                System.out.println("Dame el valor final del rango de búsqueda: ");
                end = Reader.readValue(variable);
                filter = new Filter(end, init, variable);
                break;
            default:
                System.out.println("Opción no valida. Seleccionado: Valor fijo");
                System.out.println("Dame el valor a buscar: ");
                value = Reader.readValue(variable);
                filter = new Filter(value, value, variable);
                break;
        }
        return filter;
    }

    private void actionAfterSearch(ArrayList<Product> result) throws NotNumberNegative {
        System.out.println("0. SALIR");
        int index = Reader.readInt(-1);
        if (index == -1 || index > result.size())
            System.out.println("Opción no válida, terminando proceso de búsqueda.");
        if (index != 0) {
            System.out.println(AFTERSEARCHPRODUCT_MENU);
            readOption();
            switch (option) {
                case 1:
                    int def = result.get(index - 1).getQuantity();
                    System.out.println("Cantidad anterior: " + def);
                    System.out.println("Nueva cantidad: ");
                    int cant = Reader.readInt(def);
                    if (cant >= 0) {
                        if (cant <= def) {
                            mercado.decreaseQuantityProduct(result.get(index - 1).getName(), def - cant);
                        } else {
                            mercado.increaseQuantityProduct(result.get(index - 1).getName(), cant - def);
                        }

                    } else {
                        System.out.println("Cantidad inválida. producto no actualizado");
                    }
                    break;
                case 2:
                    mercado.deleteProduct(result.get(index - 1).getName());
                    break;
                default:
                    System.out.println("Opción no válida, volviendo al menú principal.");
                    break;
            }
        }
    }

    private ArrayList<Order> searchOrder() {
        System.out.println("Creemos los filtros de búsqueda:");
        ArrayList<Filter> filters = new ArrayList<>();
        while (filters.size() == 0 || option != 0) {
            try {
                System.out.println("¡Agreguemos un filtro! ");
                Filter newFilter = createOrderFilter();
                if (newFilter != null) {
                    filters.add(newFilter);
                }
                if (!filters.isEmpty()) {
                    System.out.println("¿Quieres agregar un nuevo filtro?\n"
                            + "   0. No\n"
                            + "   Any other. Si");
                    readOption();
                }
            } catch (ParseException e) {
                System.out.println("Formato de fecha inválido. Filtro no agregado.");
            }
        }
        System.out.println(SECONDSEARCHPRODUCT_MENU);
        readOption();
        String order = "name";
        switch (option) {
            case 1:
                order = "name";
                break;
            case 2:
                order = "quantity";
                break;
            case 3:
                order = "total price";
                break;
            case 4:
                order = "date";
                break;
            default:
                System.out.println("Opción no valida. Seleccionado: Nombre");
                break;
        }
        boolean asc = wayToSearch();
        return mercado.searchOrderBy(order, asc, filters.toArray(new Filter[] {}));
    }

    private Filter createOrderFilter() throws ParseException {
        String variable = "name";
        Object value, init, end;
        System.out.println(FOURTHSEARCHORDER_MENU);
        readOption();
        switch (option) {
            case 1:
                variable = "name";
                break;
            case 2:
                variable = "quantity";
                break;
            case 3:
                variable = "total price";
                break;
            case 4:
                variable = "date";
                break;
            default:
                System.out.println("Opción no valida. Seleccionado: Nombre");
                break;
        }
        System.out.print("Desea buscar:\n"
                + "1. Valor fijo.\n"
                + "2. En rango.\n");
        readOption();
        Filter filter = null;
        switch (option) {
            case 1:
                System.out.println("Dame el valor a buscar: ");
                value = Reader.readValue(variable);
                filter = new Filter(value, value, variable);
                break;
            case 2:
                System.out.println("Dame el valor inicial del rango de búsqueda: ");
                init = Reader.readValue(variable);
                System.out.println("Dame el valor final del rango de búsqueda: ");
                end = Reader.readValue(variable);
                filter = new Filter(end, init, variable);
                break;
            default:
                System.out.println("Opción no valida. Seleccionado: Valor fijo");
                System.out.println("Dame el valor a buscar: ");
                value = Reader.readValue(variable);
                filter = new Filter(value, value, variable);
                break;
        }
        return filter;
    }

    private boolean wayToSearch() {
        System.out.println(THIRDSEARCH_MENU);
        readOption();
        switch (option) {
            case 1:
                return true;
            case 2:
                return false;
            default:
                return true;
        }
    }

    public void printMenu() {
        System.out.println(MAIN_MENU);
    }

    public void executeInput() {
        executeMainMenu();
    }

    private void executeMainMenu() {
        try {
            switch (option) {
                case 1:
                    addProduct();
                    option = 1;
                    break;
                case 2:
                    addOrder();
                    option = 2;
                    break;
                case 3:
                    searchThing();
                    option = 3;
                    break;
                case 4:
                    isRunning = false;
                    System.out.println("Cerrando...");
                    break;
                default:
                    System.out.println("Opción no reconocida");
                    break;
            }
        } catch (NotNumberNegative e) {
            System.out.println("Número inválido.");
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void readOption() {
        option = Reader.readInt(-1);
    }

    public void printListResult(ArrayList<?> list) {
        if (list == null)
            return;
        System.out.println("Resultados de la búsqueda: ");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).toString());
        }
    }
}