package com.example.goldapplenotice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldapplenotice.dao.ProductDAO;
import com.example.goldapplenotice.data.MyDbManager;
import com.example.goldapplenotice.dialog.DialogDeleteProductFromDB;
import com.example.goldapplenotice.utils.AddLayoutWithProduct;

import java.util.ArrayList;
import java.util.List;

public class ShowProductList extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mainLayout;
    private Button dropDB;
    private MyDbManager dbManager;
    private List<ProductDAO> productDAOList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_list);
        init();

        try {
            dbManager.openDb();
            if (!dbManager.getFromDb().isEmpty()) {
                mainLayout.removeAllViews();
                productDAOList =  dbManager.getFromDb();
               productDAOList.forEach(productDAO -> {
                    LinearLayout productLayout = AddLayoutWithProduct.layoutWithProduct(productDAO.getDbID(), productDAO.toString(), productDAO.getImgUrl(), this, this);
                    productLayout.setOnClickListener(this);
                    mainLayout.addView(productLayout);
                });
            }
            dbManager.closeDb();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }

    private void init() {
        dbManager = new MyDbManager(ShowProductList.this);
        productDAOList = new ArrayList<>();
        mainLayout = findViewById(R.id.showProductInDB);
        mainLayout.addView(emptyText());
        dropDB = findViewById(R.id.dropDbButton);
        dropDB.setOnClickListener(this);
    }

    //обработчик нажатий на продукты в БД
    @Override
    public void onClick(View view) {
        if (view.equals(dropDB)) {
            dbManager.openDb();
            dbManager.removeAllFromDb();
            dbManager.closeDb();
            mainLayout.removeAllViews();
            mainLayout.addView(emptyText());
        }
        if(!productDAOList.isEmpty()){
            productDAOList.forEach(product -> {
                if(view.getId() == product.getDbID()){
                    DialogDeleteProductFromDB dialog = new DialogDeleteProductFromDB(ShowProductList.this, product, mainLayout, emptyText());
                    dialog.showDialog();
                }
            });
        }

    }

    // просто текст с надписью "список пуст"
    @SuppressLint("ResourceAsColor")
    private TextView emptyText() {
        TextView empty = new TextView(this);
        empty.setText("список пуст");
        empty.setGravity(Gravity.CENTER);
        empty.setTextSize(15);
        empty.setTypeface(Typeface.SERIF);
        empty.setTextColor(R.color.black);
        return empty;
    }
}