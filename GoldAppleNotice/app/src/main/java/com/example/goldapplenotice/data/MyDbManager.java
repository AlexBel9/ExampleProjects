package com.example.goldapplenotice.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.goldapplenotice.dao.ProductDAO;

import java.util.ArrayList;
import java.util.List;

public class MyDbManager {

    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private boolean check = true;

    public MyDbManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb (){
        db = dbHelper.getWritableDatabase();
    }

    public void insertToDb (ProductDAO product){
        ContentValues cv = new ContentValues();
        cv.put(DbConstants.NAME, product.getName());
        cv.put(DbConstants.BRAND, product.getBrand());
        cv.put(DbConstants.PRODUCT_TYPE, product.getProductType());
        cv.put(DbConstants.OLD_PRICE, product.getOldPrice());
        cv.put(DbConstants.ACTUAL_PRICE, product.getActualPrice());
        cv.put(DbConstants.MAIN_VARIANT_ID, product.getMainVariantID());
        cv.put(DbConstants.URL_IMAGE, product.getImgUrl());

        getFromDb().forEach(productDAO -> {
            if(productDAO.getMainVariantID().equals(product.getMainVariantID())){
                check = false;
            }
        });
        if(check){
                    db.insert(DbConstants.TABLE_NAME, null, cv);
        }
//        db.insertWithOnConflict(DbConstants.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//        db.replaceOrThrow(DbConstants.TABLE_NAME, null, cv);
    }

    @SuppressLint("Range")
    public List<ProductDAO> getFromDb (){
        List<ProductDAO> tempList = new ArrayList<>();
        Cursor cursor = db.query(DbConstants.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()){
            ProductDAO product = new ProductDAO();
            product.setName(cursor.getString(cursor.getColumnIndex(DbConstants.NAME)));
            product.setBrand(cursor.getString(cursor.getColumnIndex(DbConstants.BRAND)));
            product.setProductType(cursor.getString(cursor.getColumnIndex(DbConstants.PRODUCT_TYPE)));
            product.setOldPrice(cursor.getString(cursor.getColumnIndex(DbConstants.OLD_PRICE)));
            product.setActualPrice(cursor.getString(cursor.getColumnIndex(DbConstants.ACTUAL_PRICE)));
            product.setImgUrl(cursor.getString(cursor.getColumnIndex(DbConstants.URL_IMAGE)));
            product.setMainVariantID(cursor.getString(cursor.getColumnIndex(DbConstants.MAIN_VARIANT_ID)));
            product.setDbID(cursor.getInt(cursor.getColumnIndex(DbConstants.ID)));
            tempList.add(product);
        }
        cursor.close();
        return tempList;
    }
    public void closeDb(){
        dbHelper.close();
    }

    public void removeAllFromDb(){
       db.execSQL(DbConstants.REMOVE_ALL);
    }

    public void removeProduct(ProductDAO product){
        db.delete(DbConstants.TABLE_NAME, DbConstants.MAIN_VARIANT_ID+"=?", new String[]{product.getMainVariantID()});
    }
}
