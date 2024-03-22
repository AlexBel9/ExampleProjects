package com.example.goldapplenotice.receivers;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.goldapplenotice.AddProduct;
import com.example.goldapplenotice.MainActivity;
import com.example.goldapplenotice.R;
import com.example.goldapplenotice.dao.ProductDAO;
import com.example.goldapplenotice.data.MyDbManager;
import com.example.goldapplenotice.utils.GenerateURL;
import com.example.goldapplenotice.utils.ImageParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

public class MainReceiver extends BroadcastReceiver {

    private final static String ID_CHANNEL = "TEST_CHANNEL";
    private JSONArray productArray = null;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        MyDbManager dbManager = new MyDbManager(context);
        dbManager.openDb();

        dbManager.getFromDb().forEach(productDAO -> {
            URL url = GenerateURL.generateURL(String.format("%s %s", productDAO.getBrand(), productDAO.getName()));
            new ForegroundTask().execute(url);
        });

        dbManager.closeDb();
//        Toast.makeText(context, date, Toast.LENGTH_LONG).show();
        Log.e("myLog", "message");

        Intent intent1 = new Intent(context, MainReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis()+ AlarmManager.INTERVAL_HALF_DAY, AlarmManager.INTERVAL_HALF_DAY,
                pendingIntent);
    }

    private void showNotification(Context context, String title, String message, int id) {

        NotificationChannel channel = new NotificationChannel(ID_CHANNEL,
                "TEST_DESCRIPTION",
                NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ID_CHANNEL)
                .setSmallIcon(R.drawable.gold_apple)
                .setContentTitle(title)
                .setContentText(message);
//                .setLargeIcon(bitmap);


        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }

    public class ForegroundTask extends AsyncTask<URL, Void, JSONArray> {
        public ForegroundTask() {
            super();
        }

        @Override
        protected void onPostExecute(JSONArray response) {
            productArray = response;
            try {
                if (!response.equals(null)) {
                    for (int i = 0; i < response.length(); i++) {
                        ProductDAO productFromRequest = new ProductDAO(response.getJSONObject(i));
                        MyDbManager dbManager = new MyDbManager(context);
                        dbManager.openDb();
                        dbManager.getFromDb().forEach(productFromDB -> {
                            if (productFromDB.getMainVariantID().equals(productFromRequest.getMainVariantID()) && !productFromDB.getActualPrice().equals(productFromRequest.getActualPrice())) {
                               // if (productFromDB.getActualPrice().equals(productFromRequest.getActualPrice())) {
                                    int priceFromDb = amountValue(productFromDB.getActualPrice());
                                    int priceFromRequest = amountValue(productFromRequest.getActualPrice());
                                    dbManager.removeProduct(productFromDB);
                                    dbManager.insertToDb(productFromDB);
                                    showNotification(context, String.format("%s %s", productFromDB.getBrand(), productFromDB.getName()), messageDifferentPrice(priceFromDb, priceFromRequest),  productFromDB.getDbID());
                               // }

                            }
                        });
                        dbManager.closeDb();
                    }
                } else {
                    Log.e("неверный запрос", "неверный запрос или такой товар отсутствует");
                }

            } catch (JSONException | NullPointerException e) {
                e.printStackTrace();
                Log.e("неверный запрос", "неверный запрос или такой товар отсутствует");
            }
        }

        @Override
        protected JSONArray doInBackground(URL... urls) {
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

    private int amountValue(String price) throws NumberFormatException {
        String[] array = price.split("\\s");
        return Integer.parseInt(array[0]);
    }

    private String messageDifferentPrice(int priceFromDb, int priceFromRequest) {
        if (priceFromDb > priceFromRequest) {
            return String.format("цена снизилась %d руб.", priceFromDb - priceFromRequest);
        } else {
            return String.format("цена стала дороже на %d руб.", priceFromRequest - priceFromDb);
        }
    }

}