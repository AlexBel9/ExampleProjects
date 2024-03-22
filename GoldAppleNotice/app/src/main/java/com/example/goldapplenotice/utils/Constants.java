package com.example.goldapplenotice.utils;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

///класс со всеми значениями из JsonObject
public class Constants  {

    private String name;
    private String brand;
    private String productType;
    private String oldPrice;
    private String actualPrice;
    private String mainVariantID;
    private String URLImage;
//    private String currency;
//    private int actualPriceLikeInt;
//    private int oldPriceLikeInt;
    private int viewID;
    private JSONObject product;
    private JSONObject price;

    public Constants(JSONObject product) throws JSONException{
        this.product = product;
        this.price = product.getJSONObject("price");
    }

    public String getName() throws JSONException {
        return product.get("name").toString();
    }

    public String getBrand() throws JSONException {
        return product.get("brand").toString();
    }

    public String getProductType()throws JSONException {
        return product.get("productType").toString();
    }

    public String getOldPrice()throws JSONException {
        return checkOldPrice(price);
    }

    public String getActualPrice()throws JSONException {
        JSONObject actualPrice = price.getJSONObject("actual");
        return actualPrice.get("amount") + " " + actualPrice.get("currency");
    }

    public String getMainVariantID()throws JSONException {
        return product.get("mainVariantItemId").toString();
    }

    public String getURLImage() throws JSONException{
        return getUrl(product);
    }

    public int getViewID()throws JSONException {
        return (Integer) product.get("idView");
    }

//    public String getCurrency() throws JSONException{
//        JSONObject actualPrice = price.getJSONObject("actual");
//        return actualPrice.get("currency").toString();
//    }
//
//    public int getActualPriceLikeInt()throws JSONException {
//        JSONObject actualPrice = price.getJSONObject("actual");
//        return Integer.valueOf(actualPrice.get("amount").toString());
//    }
//
//    public int getOldPriceLikeInt()throws JSONException {
//        if(product.isNull("old")){
//            return 0;
//        }
//        return oldPriceLikeInt;
//    }

    public JSONObject getProduct()throws JSONException {
        return product;
    }

    //проверка старой цены на null
    private String checkOldPrice(JSONObject object) throws JSONException {
        if (object.isNull("old")) {
            return "старой цены нет";
        } else {
            JSONObject oldPrice = object.getJSONObject("old");
            return oldPrice.get("amount") + " " + oldPrice.get("currency");
        }
    }

    //получение адреса где лежит картинка путём парсинга массива imageUrls
    private String getUrl(JSONObject product) throws JSONException {
        JSONArray imageUrls = product.getJSONArray("imageUrls");
        StringBuilder builder = new StringBuilder();
//        String needImageIrl = null;
        for (int i = 0; i < imageUrls.length(); i++) {
            JSONObject url = (JSONObject) imageUrls.get(i);
            JSONArray format = url.getJSONArray("format");
            JSONArray screen = url.getJSONArray("screen");
            builder.append(url.get("url").toString().replace("${screen}", screen.getString(2)).replace("${format}", format.getString(0)))
                    .append(" ");

        }
        return builder.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
