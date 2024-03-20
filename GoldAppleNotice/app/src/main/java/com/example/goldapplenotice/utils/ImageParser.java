package com.example.goldapplenotice.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/// класс отправляющий запрос для получения картинки из интернета
public class ImageParser implements Response.Listener<Bitmap>, Response.ErrorListener{

    private ImageView imageView;
    private Context context;

    public ImageParser(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageParser(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(Bitmap response) {
        imageView.setImageBitmap(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
    }
}
