package model;

public enum CategoryProduct {
    BOOKS,
    ELECTRONICS,
    APPAREL_AND_ACCESORIES,
    FOOD_AND_BEVERAGES,
    STATIONERY,
    SPORTS,
    BEAUTY_AND_PERSONAL_CARE_PRODUCTS,
    TOYS_AND_GAMES;

    
    public static final String LIST_OF_CATEGORIES = "\n"+
    "1. Books. \n"+
    "2. Electronics.\n"+ 
    "3. Apparel and accessories.\n"+
    "4. Food and beverages.\n"+ 
    "5. Stationery.\n"+
    "6. Sports.\n"+ 
    "7. Beauty and personal care products.\n"+
    "8. Toys and games.\n";

    public static CategoryProduct chooseCategory(int cat) {
        if (cat >= 1 && cat <= 8)
            return CategoryProduct.values()[cat - 1];
        return BOOKS;
    }
}
