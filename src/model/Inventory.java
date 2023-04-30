package model;

import java.text.ParseException;
import java.util.ArrayList;

import exceptions.NotNumberNegative;

public class Inventory {
    private ArrayList<Order> orders;
    private ArrayList<Product> products;

    public Inventory() {
        orders = new ArrayList<>();
        products = new ArrayList<>();
    }

    public void addProduct(String name, String desc, double price, int quantity, int category) {
        boolean search;
        if (quantity < 0 || price < 0.0) {
            throw new NotNumberNegative();
        } else {
            search = searchExistenProduct(name);
            if (search == false) {
                Product nProduct = new Product(name, desc, price, quantity);
                this.products.add(nProduct);
                nProduct.setCategories(CategoryProduct.chooseCategory(category));
            } else {
                System.out.println("Existent product");
            }
        }
    }

    private boolean searchExistenProduct(String name) {
        return searchProductBy(products, name, name, "name") != null;
    }

    public void addOrder(String bName, ArrayList<Product> list, String date) throws ParseException {
        if (!list.isEmpty()) {
            orders.add(new Order(bName, list, date));
            lessQuantityMorePurch(list);
        }
    }

    private void lessQuantityMorePurch(ArrayList<Product> list) {
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Product> result = searchProductBy(products, list.get(i).getName(), list.get(i).getName(), "name");
            if (result != null) {
                result.get(0).setQuantity(result.get(0).getQuantity() - list.get(i).getQuantity());
                result.get(0).setQuantity(result.get(0).getPurchased() + list.get(i).getQuantity());
            }
        }
    }

    public String increaseQuantityProduct(String name, int cant) {
        ArrayList<Product> result = searchProductBy(products, name, name, "name");
        if (result != null) {
            result.get(0).setQuantity(result.get(0).getQuantity() + cant);
            return "the new quantity for product: " + name + " is: " + result.get(0).getQuantity();
        }
        return "There was no product with that name";
    }

    public String decreaseQuantityProduct(String name, int cant) {
        ArrayList<Product> result = searchProductBy(products, name, name, "name");
        if (result != null) {
            result.get(0).setQuantity(result.get(0).getQuantity() - cant);
            return "the new quantity for product: " + name + " is: " + result.get(0).getQuantity();
        }
        return "There was no product with that name";
    }

    public String deleteProduct(String name, int cant) {
        ArrayList<Product> result = searchProductBy(products, name, name, "name");
        if (result != null) {
            result.remove(0);
        }
        return "There was no product with that name";
    }

    public ArrayList<Product> searchProductBy(Object top, Object bot, String attribute,
            String orderedBy, boolean asc) {
        ArrayList<Product> result = searchProductBy(products, top, bot, attribute);
        if (result != null)
            Search.orderBy(result, orderedBy, asc);
        return result;
    }

    public ArrayList<Product> searchProductBy(ArrayList<Product> array, Object top, Object bot, String attribute,
            String orderedBy, boolean asc) {
        ArrayList<Product> result = searchProductBy(array, top, bot, attribute);
        if (result != null)
            Search.orderBy(result, orderedBy, asc);
        return result;
    }

    public ArrayList<Product> searchProductBy(ArrayList<Product> array, Object top, Object bot, String attribute) {
        ArrayList<Product> elements = new ArrayList<>(array);
        ArrayList<Product> result = null;
        Search.orderBy(elements, attribute, true);
        int start = Search.findStartIndex(elements, top, attribute);
        int finish = Search.findEndIndex(elements, bot, attribute);

        if (start != -1 && finish != -1) {
            result = new ArrayList<>();
            for (int i = start; i <= finish; i++) {
                result.add(elements.get(i));
            }
        }
        return result;
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