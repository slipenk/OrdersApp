package com.slipenk.ordersapp.dictionary;

public class Dictionary {

    public static final String SLASH = "/";
    public static final String DOLLAR_SIGN = "$";

    // paths
    public static final String ORDER_APP_PATH = SLASH + "orders-app";
    public static final String GET_PRODUCTS_PATH = SLASH + "getProducts";
    public static final String ADD_PRODUCTS_PATH = SLASH + "addProducts";
    public static final String ADD_ORDER_PATH = SLASH + "addOrders";
    public static final String GET_PRODUCTS_FULL_PATH = ORDER_APP_PATH + GET_PRODUCTS_PATH;
    public static final String ADD_PRODUCTS_FULL_PATH = ORDER_APP_PATH + ADD_PRODUCTS_PATH;
    public static final String ADD_ORDER_FULL_PATH = ORDER_APP_PATH + ADD_ORDER_PATH;

    public static final String INSERT_DATA_SQL_PATH = SLASH + "insertData.sql";

    //messages
    public static final String PRODUCTS_WITH_SIZE = "Products with size ";
    public static final String WERE_ADDED_SUCCESSFULLY = " were added successfully.";
    public static final String ERROR_WITH_CONFIG_SECURITY = "Error with configuring security.";
    public static final String ERROR_DURING_CONVERSION_INTO_JSON = "Error during conversion into JSON.";
    public static final String ERROR_PRODUCT_NOT_FOUND = "Product not found.";
    public static final String CANNOT_BUY = "You cannot buy more than we have.";

    //table names
    public static final String AUTHORITIES = "authorities";
    public static final String ORDERS = "orders";
    public static final String ORDER_ITEMS = "order_items";
    public static final String PRODUCTS = "products";
    public static final String USERS = "users";

    //attributes
    public static final String ID = "id";
    public static final String AUTHORITY = "authority";
    public static final String USERNAME = "username";
    public static final String PAID = "paid";
    public static final String ORDER = "order";
    public static final String QUANTITY = "quantity";
    public static final String PRODUCT_ID = "product_id";
    public static final String ORDER_ID = "order_id";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String TOTAL_QUANTITY = "total_quantity";
    public static final String PASSWORD = "password";
    public static final String ENABLED = "enabled";
    public static final String OWN_USER = "ownUser";

    //for tests
    public static final String SLIPENK = "slipenk";
    public static final String SALAH = "salah";
    public static final String APPLE_IPHONE_14_128GB_MIDNIGHT = "Apple iPhone 14 128GB (Midnight)";
    public static final String APPLE_IPHONE_12_64GB_WHITE = "Apple iPhone 12 64GB (White)";
    public static final String IPHONE_13_PRO = "iPhone 13 Pro";
    public static final String IPHONE_11 = "iPhone 11";

    private Dictionary() {
    }
}
