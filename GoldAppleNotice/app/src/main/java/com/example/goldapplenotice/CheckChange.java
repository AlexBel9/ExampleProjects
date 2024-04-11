package com.example.goldapplenotice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldapplenotice.data.MyDbManager;
import com.example.goldapplenotice.dialog.DialogDeleteChangeList;
import com.example.goldapplenotice.utils.AddLayoutWithProduct;

import org.w3c.dom.Text;

public class CheckChange extends AppCompatActivity implements View.OnClickListener{

    LinearLayout linearLayout;
    MyDbManager dbManager;
    Button removeAll;
    TextView emptyText;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_change);
        init();
        linearLayout.addView(emptyText);
        dbManager.openDb();

        try {
            if(!dbManager.changeList().isEmpty()){

                linearLayout.removeView(emptyText);
                dbManager.changeList().forEach(productDAO -> {
                    String desc = String.format("%s\n%s %s",productDAO.toString(), productDAO.getDate(), productDAO.getDifferentPrice());
                    LinearLayout layout = AddLayoutWithProduct.layoutWithProduct(productDAO.getDbID(), desc, productDAO.getImgUrl(), this, this);
                    linearLayout.addView(layout);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ResourceAsColor")
    private void init(){
        linearLayout = findViewById(R.id.showProductFromChange);
        dbManager = new MyDbManager(this);
        removeAll = findViewById(R.id.dropChangList);
        removeAll.setOnClickListener(this);
        emptyText = new TextView(this);
        emptyText.setText("пока никаких изменений ¯\\_(ツ)_/¯");
        emptyText.setGravity(Gravity.CENTER);
        emptyText.setTextSize(15);
        emptyText.setTypeface(Typeface.SERIF);
        emptyText.setTextColor(R.color.black);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(removeAll)){
            dbManager.openDb();
            try {
                if(!dbManager.changeList().isEmpty()){
                    DialogDeleteChangeList dialogDeleteChangeList = new DialogDeleteChangeList(this, linearLayout, emptyText);
                    dialogDeleteChangeList.showDialog();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "список уже пуст!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}