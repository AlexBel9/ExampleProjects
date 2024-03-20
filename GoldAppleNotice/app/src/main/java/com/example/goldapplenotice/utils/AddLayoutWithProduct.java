package com.example.goldapplenotice.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.goldapplenotice.AddProduct;
import com.example.goldapplenotice.R;

public class AddLayoutWithProduct {

    @SuppressLint("ResourceAsColor")
    public static LinearLayout layoutWithProduct(int id, String desc, String imgUrl, Context context, View.OnClickListener listener){
        /////создание контейнера для продукта
        LinearLayout productLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        productLayout.setOrientation(LinearLayout.VERTICAL);
        productLayout.setPadding(10,10,10,10);
        productLayout.setOnClickListener(listener);
        productLayout.setId(id);
        /////создание текста для продукта
        TextView textView = new TextView(context);
        textView.append(desc);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        textView.setTextColor(R.color.blue);
        Typeface typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD);
        textView.setTypeface(typeface);

        //////Создание новой картинки /////////////////////////
        ImageView imageView = new ImageView(context);
        ImageRequest imageRequest = new ImageRequest(imgUrl, new ImageParser(imageView),
                0, 0, null, Bitmap.Config.ARGB_8888,
                new ImageParser(context));
        Volley.newRequestQueue(context).add(imageRequest);
        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //// добавление в контнейнер продукта текста и картинки
        productLayout.addView(imageView, imageViewLayoutParams);
        productLayout.addView(textView);
        productLayout.setLayoutParams(params);
        return productLayout;
    }
}
