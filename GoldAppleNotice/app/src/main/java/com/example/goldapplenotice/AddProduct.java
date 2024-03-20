package com.example.goldapplenotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.gesture.OrientedBoundingBox;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.goldapplenotice.dao.ProductDAO;
import com.example.goldapplenotice.dialog.DialogAddProd;
import com.example.goldapplenotice.utils.AddLayoutWithProduct;
import com.example.goldapplenotice.utils.Constants;
import com.example.goldapplenotice.utils.GenerateURL;
import com.example.goldapplenotice.utils.HtmlDocument;
import com.example.goldapplenotice.utils.ImageParser;
import com.example.goldapplenotice.utils.JSONArrayParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.regex.Pattern;


public class AddProduct extends AppCompatActivity  implements View.OnClickListener{

    private EditText productName;
    private Button searchProduct;
    private LinearLayout scrollProduct;
    private JSONArray productArray = null;
    private ImageView loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        init();
    }

    private void init() {
        productName = findViewById(R.id.setTextProductName);
        searchProduct = findViewById(R.id.searchProductName);
        searchProduct.setOnClickListener(this);
        scrollProduct = findViewById(R.id.scrollProduct);
        loading = findViewById(R.id.loadingPick);
    }


    @Override
    public void onClick(View view) {

        try {
            if (view.equals(searchProduct)) {
                String textProductName = productName.getText().toString();
                if (textProductName.equals("")) {
                    Toast.makeText(this, "введите название продукта", Toast.LENGTH_SHORT).show();
                } else {
                    scrollProduct.removeAllViews();//удаление всех вьюшек из layout, чтобы добавить новые вьюшки
                    URL generateURL = GenerateURL.generateURL(textProductName);//генерация URL адреса
                    new QueryTask().execute(generateURL);//создание новой задачи и передача в него сгенерированного url
                }
            }
            if(productArray != null){
                JSONArrayParser parser = new JSONArrayParser(productArray);
                JSONObject jsonProduct = parser.getJsonObject(view.getId());
                try {
                    ProductDAO productDAO = new ProductDAO(jsonProduct);
                    DialogAddProd dialogAddProd = new DialogAddProd(AddProduct.this, productDAO);
                    dialogAddProd.showDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // поток обрабатывающий запрос
    class QueryTask extends AsyncTask<URL, ImageView, JSONArray> {

        @Override
        protected void onProgressUpdate(ImageView... values) {
            values[0].setImageResource(R.drawable.loading);
            Animation anim = AnimationUtils.loadAnimation(AddProduct.this, R.anim.rotate);
            values[0].startAnimation(anim);
            searchProduct.setEnabled(false);

                        super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(JSONArray response) {
            loading.setImageDrawable(null); //после заверешения загрузки значок загрузки пропадает
            searchProduct.setEnabled(true); //кнопка запрса опять доступна
            productArray = response;
                try {
                    if (!response.equals(null)) {
                        for (int i = 0; i < response.length(); i++) {
                            addViewProduct(response.getJSONObject(i), i);// динамическое добавление продуктов в layout
                        }
                    } else {
                        Toast.makeText(AddProduct.this, "неверный запрос или такой товар отсутствует", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(AddProduct.this, "неверный запрос или такой товар отсутствует", Toast.LENGTH_SHORT).show();
                }
        }
        @Override
        protected JSONArray doInBackground(URL... urls) {
            publishProgress(loading);//передача значка загрузки
            Connection.Response response = null;
            try {
                response = Jsoup.connect(urls[0].toString())
                        .header("authority", "goldapple.ru")
                        .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                        .header("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                        .header("cache-control", "max-age=0")
                        .cookie("PHPSESSID", "7c68a5112ba95f7bc0f77e26d30f08eb")
                        .cookie("ga-lang", "ru")
                        .cookie("client-store-code", "default")
                        .cookie("mage-cache-sessid", "true")
                        .cookie("advcake_track_id", "48ce8fd2-4f13-5d6d-669a-890435aedaba")
                        .cookie("advcake_session_id", "bb4533f0-8ee2-f58f-9b2e-fcae847a80f1")
                        .cookie("mindboxDeviceUUID", "6d7b4ebf-300f-4d9b-9b96-d11ca96fe9da")
                        .cookie("directCrm-session", "%7B%22deviceGuid%22%3A%226d7b4ebf-300f-4d9b-9b96-d11ca96fe9da%22%7D")
                        .cookie("tmr_lvid", "5f32dfb09c3e35389c9228b58f3ae120")
                        .cookie("tmr_lvidTS", "1708401474388")
                        .cookie("_gid", "GA1.2.901421418.1708401474")
                        .cookie("_ym_uid", "1708401474553641667")
                        .cookie("_ym_d", "1708401474")
                        .cookie("_ym_isad", "2")
                        .cookie("adrdel", "1")
                        .cookie("adrcid", "AfNrX96d8o0I6aPco79d5iQ")
                        .cookie("ngenix_jscv_2198e54375cc", "bot_profile_check")
                        .cookie("digi-analytics-sessionId", "WLzVSldB_Tf5ouUfXoDbO")
                        .cookie("_ga", "GA1.2.58928592.1708401474")
                        .cookie("_spx", "eyJpZCI6ImUzNGJmNGJlLTk2MWMtNGI1Zi1hMWY3LTMyN2Q3NDZlYTIzYyIsImZpeGVkIjp7InN0YWNrIjpbLTEyMDY4MjM1MjMsLTIwMDg5NjQzMjksLTIwMDg5NjQzMjksMCwwXX19")
                        .cookie("section_data_ids", "%7B%22geolocation%22%3A1708422894%2C%22adult_goods%22%3A1708422896%2C%22cart%22%3A1708422898%7D")
                        .cookie("tmr_detect", "0%7C1708422901573")
                        .cookie("_ga_QE5MQ8XJJK", "GS1.1.1708419233.3.1.1708423428.0.0.0")
                        .header("sec-ch-ua", "\"Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121\"")
                        .header("sec-ch-ua-mobile", "?0")
                        .header("sec-ch-ua-platform", "\"Windows\"")
                        .header("sec-fetch-dest", "document")
                        .header("sec-fetch-mode", "navigate")
                        .header("sec-fetch-site", "same-origin")
                        .header("sec-fetch-user", "?1")
                        .header("upgrade-insecure-requests", "1")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36")
                        .method(Connection.Method.GET)
                        .ignoreContentType(true)
                        .execute();

                //получаем документ из нашего запроса
                Document document = response.parse();
                String needAttr = document.head().toString();

                Pattern pattern = Pattern.compile("window.serverCache\\['plp'\\]=");
                String[] array = needAttr.split(pattern.pattern(), 2);
                String jsonObjectString = array[1].replaceAll(";", "");
                //после редактирования конвертируем документ в JSON;


                JSONObject jsonObject = new JSONObject(jsonObjectString);
                JSONObject productsObject = jsonObject.getJSONObject("data").getJSONObject("products");
                JSONArray productsArray = productsObject.getJSONArray("products");

                return productsArray;
            } catch (IOException | JSONException | NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    //динамическое добавление view элементов на экран
    @SuppressLint({"ResourceAsColor", "ResourceType"})
    private void addViewProduct(JSONObject product, int id) throws JSONException{
        product.put("idView", id);
        Log.e("idview", String.valueOf(id));
        ProductDAO productDAO = new ProductDAO(product);
        LinearLayout productLayout = AddLayoutWithProduct.layoutWithProduct(id,productDAO.toString(), productDAO.getImgUrl(), this, this);

        scrollProduct.addView(productLayout);
    }
}