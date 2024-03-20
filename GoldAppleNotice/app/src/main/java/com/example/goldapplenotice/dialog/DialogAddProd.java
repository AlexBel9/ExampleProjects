package com.example.goldapplenotice.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.goldapplenotice.AddProduct;
import com.example.goldapplenotice.dao.ProductDAO;
import com.example.goldapplenotice.data.MyDbManager;

public class DialogAddProd extends AlertDialog implements DialogInterface.OnClickListener {

    private Context context;
    private ProductDAO product;

    public DialogAddProd(Context context, ProductDAO product) {
        super(context);
        this.context = context;
        this.product = product;

    }
    public void showDialog (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("добавление продукта в базу");
        builder.setMessage(String.format("вы хотите отслеживать %s %s?",product.getBrand(), product.getName()));
        builder.setCancelable(true);
        builder.setPositiveButton("да", this);
        builder.setNegativeButton("нет", this);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i){
            case Dialog.BUTTON_POSITIVE:
                Toast.makeText(context, String.format("%s %s успешно добавлен!", product.getBrand(), product.getName()), Toast.LENGTH_SHORT).show();
                MyDbManager dbManager = new MyDbManager(context);
                dbManager.openDb();
                dbManager.insertToDb(product);
                dbManager.closeDb();
            case Dialog.BUTTON_NEGATIVE:
                DialogAddProd.this.cancel();
        }
    }
}
