package com.example.goldapplenotice.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONArrayParser {

    JSONArray productArray;

    public JSONArrayParser(JSONArray productArray) {
        this.productArray = productArray;
    }

    //возвращает джейсон объект из массива джейсонов по его idView
    public JSONObject getJsonObject(int idView) {
        for (int i = 0; i < productArray.length(); i++) {
            try {
                JSONObject product = productArray.getJSONObject(i);
                Constants constProd = new Constants(product);
                if(constProd.getViewID() == idView){
                    return product;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
