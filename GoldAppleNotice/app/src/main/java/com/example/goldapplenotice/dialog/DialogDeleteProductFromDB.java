package com.example.goldapplenotice.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.LinkAddress;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldapplenotice.R;
import com.example.goldapplenotice.ShowProductList;
import com.example.goldapplenotice.dao.ProductDAO;
import com.example.goldapplenotice.data.MyDbManager;
import com.example.goldapplenotice.utils.AddLayoutWithProduct;

// dialog удаления продукта  из базы данных
public class DialogDeleteProductFromDB extends AlertDialog implements DialogInterface.OnClickListener {
    private ProductDAO product;
    private LinearLayout layoutWithProductFromDb;
    private TextView emptyText;

    public DialogDeleteProductFromDB(Context context, ProductDAO product, LinearLayout layoutWithProductFromDb, TextView emptyText) {
        super(context);
        this.product = product;
        this.layoutWithProductFromDb = layoutWithProductFromDb;
        this.emptyText = emptyText;
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("удаление продукта из базы");
        builder.setMessage(String.format("вы хотите удалить %s %s?", product.getBrand(), product.getName()));
        builder.setCancelable(true);
        builder.setPositiveButton("да", this);
        builder.setNegativeButton("нет", this);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // выбор в окне удаления  да   нет
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case Dialog.BUTTON_POSITIVE:
                Toast.makeText(getContext(), String.format("%s %s удалён!", product.getBrand(), product.getName()), Toast.LENGTH_SHORT).show();
                MyDbManager dbManager = new MyDbManager(getContext());
                dbManager.openDb();
                    dbManager.removeProduct(product);
                    layoutWithProductFromDb.getTouchables().forEach(view -> {
                        if(view.getId() == product.getDbID()){
                            layoutWithProductFromDb.removeView(view);
                        }
                    });
                    if (dbManager.getFromDb().isEmpty()) {
                        layoutWithProductFromDb.addView(emptyText);
                    }
                    dbManager.closeDb();

            case Dialog.BUTTON_NEGATIVE:
                DialogDeleteProductFromDB.this.cancel();
        }
    }
}
