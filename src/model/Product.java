package model;

import util.Compare;

public class Product implements Compare<Product> {

    public static final String PATH = "products.json";

    private String name;
    private String desc;
    private Double price;
    private Integer quantity;
    private Integer purchased;
    private CategoryProduct categories;

    // Constructor
    public Product(String name, String desc, double price, int quantity) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        purchased = 0;
    }

    public int compare(Product other, String attribute) {
        switch (attribute) {
            case "price":
                return this.price.compareTo(other.price);
            case "name":
                return this.name.compareTo(other.name);
            case "quantity":
                return this.quantity.compareTo(other.quantity);
            case "category":
                return this.categories.compareTo(other.categories);
            default:
                break;
        }
        return this.name.compareTo(other.name);
    }

    public int compareAttr(Object target, String attribute) {
        switch (attribute) {
            case "price":
                return this.price.compareTo(Double.valueOf(target.toString()));
            case "name":
                return this.name.compareTo((String) target);
            case "quantity":
                return this.quantity.compareTo((Integer) target);
            case "category":
                return this.categories.compareTo((CategoryProduct) target);
            default:
                break;
        }
        return 0;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }

    public CategoryProduct getCategories() {
        return categories;
    }

    public void setCategories(CategoryProduct categories) {
        this.categories = categories;
    }

}
