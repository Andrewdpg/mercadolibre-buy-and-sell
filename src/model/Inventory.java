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
            search = searchExistenProduct(name, desc);
            if (search == false) {
                Product nProduct = new Product(name, desc, price, quantity);
                this.products.add(nProduct);
                nProduct.chooseCategory(category);
            } else {
                System.out.println("Existent product");
            }
        }
    }

    private boolean searchExistenProduct(String name, String desc) {
        boolean exist = false;
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name) && p.getDesc().equalsIgnoreCase(desc)) {
                exist = true;
            }
        }
        return exist;
    }

    public void addOrder(String bName, ArrayList<Product> list, String date) throws ParseException {
        if (!list.isEmpty()) {
            double totalPrice = calcTotal(list);
            orders.add(new Order(bName, list, totalPrice, date));
            lessQuantityMorePurch(list);
        }
    }

    private void lessQuantityMorePurch(ArrayList<Product> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setQuantity(list.get(i).getQuantity() - 1);
            list.get(i).setPurchased(list.get(i).getPurchased() + 1);
        }
    }

    private double calcTotal(ArrayList<Product> list) {
        double result = 0.0;
        for (Product p : list) {
            result += p.getPrice();
        }
        return result;
    }

    private String increaseQuantityProduct(String name, int cant) {
        Product current = null;
        current = searchProduct(name);
        if (current != null) {
            int quantity = current.getQuantity() + cant;
            current.setQuantity(quantity);
        }
        return "the new quantity for product: " + name + " is: " + current.getQuantity();
    }

    public Product searchProduct(String name) {
        for (Product p : products) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public void searchProductForPrice(int top, int bot) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getPrice() >= bot && p.getPrice() <= top) {
                result.add(p);
            }
        }
        System.out.println("the products in the range between: " + top + bot + " are:");
        for (Product pp : result) {
            System.out.println(pp.getName() + "" + "" + pp.getPrice());
        }
    }

    public void searchProductbyCategory() {

    }

    public void searchProductbyTimesBought(int top, int bot){
        ArrayList<Product> result = new ArrayList<>(products);
        for(Product p:products){
            if(p.getPurchased() <=top && p.getPurchased()>= bot){
                result.add(p);
            }
        }

        System.out.println("the products in the range between: " + top + bot +" times bought are:" );
        for (Product pp : result) {
            System.out.println(pp.getName() + "" + "" + pp.getPrice());
        }
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