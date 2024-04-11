package com.example.goldapplenotice.dao;

import androidx.annotation.NonNull;

import com.example.goldapplenotice.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;
/// простой объект "продукт"
public class ProductDAO {

    private String mainVariantID;
    private String name;
    private String brand;
    private String productType;
    private String oldPrice;
    private String actualPrice;
    private String imgUrl;
    private int viewID;
    private int dbID;
    private String differentPrice;
    private String date;

    public ProductDAO() {
    }

    public ProductDAO(JSONObject product) throws JSONException {
        Constants productConstants = new Constants(product);
        this.mainVariantID = productConstants.getMainVariantID();
        this.name = productConstants.getName();
        this.brand = productConstants.getBrand();
        this.productType = productConstants.getProductType();
        this.oldPrice = productConstants.getOldPrice();
        this.actualPrice = productConstants.getActualPrice();
        this.imgUrl = productConstants.getURLImage();
    }

    public String getMainVariantID() {
        return mainVariantID;
    }

    public void setMainVariantID(String mainVariantID) {
        this.mainVariantID = mainVariantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getViewID() {
        return viewID;
    }

    public void setViewID(int viewID) {
        this.viewID = viewID;
    }

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID = dbID;
    }

    public String getDifferentPrice() {
        return differentPrice;
    }

    public void setDifferentPrice(String differentPrice) {
        this.differentPrice = differentPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(brand).append(" ").append(name).append("\n")
                .append(productType).append("\n")
                .append("старая цена: ").append(oldPrice).append("\n")
                .append("новая цена: ").append(actualPrice);
        return builder.toString();
    }
}
