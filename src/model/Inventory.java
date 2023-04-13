package model;

import java.text.ParseException;
import java.util.ArrayList;

public class Inventory{
    private ArrayList<Order> orders;
    private ArrayList<Product> products;
    
    public Inventory(){
        orders = new ArrayList<>();
        products = new ArrayList<>();
    }

    public void addProduct(String name, String desc, double price, int quantity, int category,int purchased){
        products.add(new Product(name,desc,price,quantity,category,purchased));
    }
    public void addOrder(String bName, ArrayList<Product> list, String date) throws ParseException{
        if(!list.isEmpty()){
            double totalPrice = calcTotal(list);
            orders.add(new Order(bName, list, totalPrice, date));
            lessQuantityMorePurch(list);
        }
    }

    private void lessQuantityMorePurch(ArrayList<Product> list){
        for(int i=0;i<list.size();i++){
            list.get(i).setQuantity(list.get(i).getQuantity()-1);
            list.get(i).setPurchased(list.get(i).getPurchased()+1);
        }
    }
    private double calcTotal(ArrayList<Product> list){
        double result=0.0;
        for(Product p: list){
            result+=p.getPrice();
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