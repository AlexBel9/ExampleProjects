package com.example.goldapplenotice.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class GenerateURL{

    private static final String ADDRESS = "https://goldapple.ru/catalogsearch/result";
    private static final String PARAMS = "q";


    public static URL generateURL(String nameProduct){
        Uri builtUri = Uri.parse(ADDRESS)
                .buildUpon()
                .appendQueryParameter(PARAMS, nameProduct)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
