package model;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.reflect.TypeToken;

import exceptions.NotNumberNegative;
import util.Filter;
import util.Search;
import util.Storage;

public class Inventory {
    private ArrayList<Order> orders;
    private ArrayList<Product> products;

    private int savingErrors;

    public Inventory() {
        orders = new ArrayList<>();
        products = new ArrayList<>();
        try {
            // loadData(); Descomentar al final (no funcionan algunos tests porque siempre
            // tiene todos los productos cargados 💀)
        } catch (Exception e) {
            System.out.println("Archivo/s corruptos. Información no cargada");
            orders = new ArrayList<>();
            products = new ArrayList<>();
        }
    }

    private void loadData() throws IOException {
        orders = new ArrayList<>();
        products = new ArrayList<>();
        if (!Storage.fileExists(Order.PATH))
            Storage.saveJsonTo(Order.PATH, orders);
        if (!Storage.fileExists(Product.PATH))
            Storage.saveJsonTo(Product.PATH, products);
        Type orderType = new TypeToken<ArrayList<Order>>() {}.getType();
        Type productType = new TypeToken<ArrayList<Product>>() {}.getType();
        orders = Storage.loadJsonFrom(Order.PATH, orderType);
        products = Storage.loadJsonFrom(Product.PATH, productType);
    }

    private void saveData() {
        try {
            Storage.saveJsonTo(Order.PATH, orders);
            Storage.saveJsonTo(Product.PATH, products);
            savingErrors = 0;
        } catch (IOException e) {
            savingErrors++;
            if (savingErrors >= 2) {
                System.out.println(
                        "No se ha podido guardar la información, número de cambios no guardados: " + savingErrors);
            }
        }
    }

    public void addProduct(String name, String desc, double price, int quantity, int category) {
        boolean search;
        if (quantity < 0 || price < 0.0) {
            throw new NotNumberNegative();
        } else {
            search = productExists(name);
            if (search == false) {
                Product nProduct = new Product(name, desc, price, quantity);
                this.products.add(nProduct);
                nProduct.setCategories(CategoryProduct.chooseCategory(category));
                // saveData(); Descomentar al final (no funcionan algunos tests porque siempre tiene todos los productos cargados 💀)
            } else {
                System.out.println("Existent product");
            }
        }
    }

    public void addOrder(String bName, ArrayList<Product> list, String date) throws ParseException {
        if(list != null && !list.isEmpty()){
            Order order = new Order(bName, generateOrderID(), list, date);
            orders.add(order);
            System.out.println("orden generada con ID: " + order.getId());
            lessQuantityMorePurch(list);
            // saveData(); Descomentar al final (no funcionan algunos tests porque siempre
                       // tiene todos los productos cargados 💀)
        }
    }

    private void lessQuantityMorePurch(ArrayList<Product> list) {
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Product> result = Search.searchBy(products,
                    new Filter(list.get(i).getName(), list.get(i).getName(), "name"));
            if (result != null) {
                result.get(0).setQuantity(result.get(0).getQuantity() - list.get(i).getQuantity());
                result.get(0).setPurchased(result.get(0).getPurchased() + list.get(i).getQuantity());
            }
        }
    }

    public String increaseQuantityProduct(String name, int cant) throws NotNumberNegative {
        if(cant < 0) throw new NotNumberNegative("La cantidad introducida es negativa.");
        ArrayList<Product> result = Search.searchBy(products, new Filter(name, name, "name"));
        if (result != null) {
            result.get(0).setQuantity(result.get(0).getQuantity() + cant);
            // saveData(); Descomentar al final (no funcionan algunos tests porque siempre
            // tiene todos los productos cargados 💀)
            return "the new quantity for product: " + name + " is: " + result.get(0).getQuantity();
        }
        return "There was no product with that name";
    }

    public String decreaseQuantityProduct(String name, int cant) throws NotNumberNegative {
        if (cant < 0)
            throw new NotNumberNegative("La cantidad introducida es negativa.");
        ArrayList<Product> result = Search.searchBy(products, new Filter(name, name, "name"));
        if (result != null) {
            int newCant = result.get(0).getQuantity() - cant;
            if(newCant < 0) 
                throw new NotNumberNegative("La cantidad del preducto es menor a lo que se quiere quitar.");
            result.get(0).setQuantity(newCant);
            // saveData(); Descomentar al final (no funcionan algunos tests porque siempre
            // tiene todos los productos cargados 💀)
            return "the new quantity for product: " + name + " is: " + result.get(0).getQuantity();
        }
        return "There was no product with that name";
    }

    public String deleteProduct(String name) {
        ArrayList<Product> result = Search.searchBy(products, new Filter(name, name, "name"));
        if (result != null) {
            products.remove(result.get(0));
            // saveData(); Descomentar al final (no funcionan algunos tests porque siempre
            // tiene todos los productos cargados 💀)
            return "Product succsesfully deleted";
        }
        return "There was no product with that name";
    }

    public boolean productExists(String name) {
        return Search.searchBy(products, new Filter(name, name, "name")) != null;
    }

    public ArrayList<Product> searchProductBy(String orderedBy, boolean asc, Filter... filters) {
        ArrayList<Product> result = Search.searchBy(products, filters);
        if (result != null)
            Search.orderBy(result, orderedBy, asc);
        return result;
    }

    public ArrayList<Order> searchOrderBy(String orderedBy, boolean asc, Filter... filters) {
        ArrayList<Order> result = Search.searchBy(orders, filters);
        if (result != null)
            Search.orderBy(result, orderedBy, asc);
        return result;
    }

    public String generateOrderID(){
        String id = UUID.randomUUID().toString();
        while(searchOrderBy("id", true, new Filter(id,id,"id")) != null){
            id = UUID.randomUUID().toString();
        }
        return id;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}