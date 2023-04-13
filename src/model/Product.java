package model;

public class Product implements Comparable<Product>{
    private String name;
    private String desc;
    private double price;
    private int quantity;
    private int purchased;
    private CategoryProduct categories; 
    
    //Constructor
    public Product(String name, String desc, double price, int quantity, int category,int purchased) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        this.purchased = purchased;
        categories = chooseCategory(category);
    }

    //Method for choosing the category by a given int among 1 to 8.
    public CategoryProduct chooseCategory(int cat){
        switch(cat){
            case 1:
                categories = CategoryProduct.BOOKS;
                break;
            case 2:
                categories = CategoryProduct.ELECTRONICS;
                break;
            case 3:
                categories = CategoryProduct.APPAREL_AND_ACCESORIES;
                break;
            case 4:
                categories = CategoryProduct.FOOD_AND_BEVERAGES;
                break;
            case 5:
                categories = CategoryProduct.STATIONERY;
                break;
            case 6:
                categories = CategoryProduct.SPORTS;
                break;
            case 7:
                categories = CategoryProduct.BEAUTY_AND_PERSONAL_CARE_PRODUCTS;
                break;
            case 8:
                categories = CategoryProduct.TOYS_AND_GAMES;
                break;
            default:
                break;
            }
        return categories;
    }

    //Getters and setters
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

    @Override
    public int compareTo(Product o) {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
