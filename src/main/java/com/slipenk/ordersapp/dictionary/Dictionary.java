package com.slipenk.ordersapp.dictionary;

public class Dictionary {

    public static final String SLASH = "/";
    public static final String DOLLAR_SIGN = "$";

    public static final String INSERT_DATA_SQL_PATH = SLASH + "insertData.sql";

    //messages
    public static final String ERROR_PRODUCT_NOT_FOUND = "Product not found.";
    public static final String CANNOT_BUY = "You cannot buy more than we have.";
    public static final String BEFORE_METHOD = "=====>> Calling method: ";
    public static final String ARGUMENT = "=====>> Argument: ";
    public static final String RETURN_FROM_METHOD = "=====>> Return from method: ";
    public static final String RESULT = "=====>> Result: ";

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
    public static final String CREATED_DATE_TIME = "created_date_time";
    public static final String ORDERED_PRODUCT = "orderedProduct";

    // paths
    public static final String ORDER_APP_PATH = SLASH + "api";
    public static final String PRODUCTS_PATH = SLASH + PRODUCTS;
    public static final String PRODUCTS_FULL_PATH = ORDER_APP_PATH + PRODUCTS_PATH;
    public static final String ORDERS_PATH = SLASH + ORDERS;
    public static final String ORDERS_FULL_PATH = ORDER_APP_PATH + ORDERS_PATH;
    public static final String PAY_ORDER_PATH = SLASH + "pay";
    public static final String PAY_ORDER_FULL_PATH = ORDER_APP_PATH + PAY_ORDER_PATH;

    //for tests
    public static final String SLIPENK = "slipenk";
    public static final String SALAH = "salah";
    public static final String APPLE_IPHONE_14_128GB_MIDNIGHT = "Apple iPhone 14 128GB (Midnight)";
    public static final String APPLE_IPHONE_12_64GB_WHITE = "Apple iPhone 12 64GB (White)";
    public static final String IPHONE_13_PRO = "iPhone 13 Pro";
    public static final String IPHONE_11 = "iPhone 11";
    public static final String NUMBER_3200_99 = "32000.99";
    public static final String NUMBER_2200_99 = "22000.99";

    private Dictionary() {
    }
}
