package com.example.goldapplenotice.data;

import com.example.goldapplenotice.dao.ProductDAO;

public class DbConstants {

    // название стоблцов в базе данных
    public static final String DB_NAME = "my_basket.db";
    public static final int VERSION_ID = 2;
    public static final String TABLE_NAME = "product";
    public static final String ID = "_id";
    public static final String MAIN_VARIANT_ID = "main_variant_id";
    public static final String NAME = "name";
    public static final String BRAND = "brand";
    public static final String PRODUCT_TYPE = "product_type";
    public static final String OLD_PRICE = "old_price";
    public static final String ACTUAL_PRICE = "actual_price";
    public static final String URL_IMAGE = "url_image";


    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + " (" + ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT, "
            + BRAND + " TEXT, "
            + PRODUCT_TYPE + " TEXT, "
            + ACTUAL_PRICE + " TEXT, "
            + OLD_PRICE + " TEXT, "
            + URL_IMAGE + " TEXT, "
            + MAIN_VARIANT_ID + " TEXT UNIQUE)";
//            + "UNIQUE(" + MAIN_VARIANT_ID + ") ON CONFLICT REPLACE)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String REMOVE_ALL = "DELETE FROM " + TABLE_NAME;

}
