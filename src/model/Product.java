package model;

public class Product implements Comparable<Product>{
    private String name;
    private String desc;
    private Double price;
    private Integer quantity;
    private Integer purchased;
    private CategoryProduct categories; 
    
    //Constructor
    public Product(String name, String desc, double price, int quantity) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        purchased=0;
    }

    //Method for choosing the category by a given int among 1 to 8.
    public CategoryProduct chooseCategory(int cat){
        switch(cat){
            case 1:
                this.categories = CategoryProduct.BOOKS;
                break;
            case 2:
                this.categories = CategoryProduct.ELECTRONICS;
                break;
            case 3:
                this.categories = CategoryProduct.APPAREL_AND_ACCESORIES;
                break;
            case 4:
                this.categories = CategoryProduct.FOOD_AND_BEVERAGES;
                break;
            case 5:
                this.categories = CategoryProduct.STATIONERY;
                break;
            case 6:
                this.categories = CategoryProduct.SPORTS;
                break;
            case 7:
                this.categories = CategoryProduct.BEAUTY_AND_PERSONAL_CARE_PRODUCTS;
                break;
            case 8:
                this.categories = CategoryProduct.TOYS_AND_GAMES;
                break;
            default:
                break;
            }
        return categories;
    }


    public int compare(Product other, String attribute) {
        switch (attribute) {
            case "price":
                return this.price.compareTo(other.price);
            case "name":
                return this.name.compareTo(other.name);
            case "quantity":
                return this.quantity.compareTo(other.quantity);
            default:
                break;
        }
        return this.name.compareTo(other.name);
    }

    public int compareAttr(Object target, String attribute) {
        switch (attribute) {
            case "price":
                return this.price.compareTo((Double) target);
            case "name":
                return this.name.compareTo((String) target);
            case "quantity":
                return this.quantity.compareTo((Integer) target);
            default:
                break;
        }
        return 0;
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
        int criteria = this.name.compareTo(o.name);
        if(criteria==0){
            criteria= this.desc.compareTo(o.desc);
        }
        if(criteria==0){
            criteria= this.categories.compareTo(o.categories);
        }
        if(criteria==0){
            criteria= (int)(this.price-o.price);
        }
        if(criteria==0){
            criteria= this.quantity-o.quantity;
        }
        if(criteria==0){
            criteria= this.purchased-o.purchased;
        }
        return criteria;
    }

    public CategoryProduct getCategories() {
        return categories;
    }

    public void setCategories(CategoryProduct categories) {
        this.categories = categories;
    }
    
}
