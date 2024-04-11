package com.example.goldapplenotice.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldapplenotice.data.MyDbManager;

public class RemoveAllProductsDialog extends AlertDialog implements DialogInterface.OnClickListener {

    private Context context;
    private LinearLayout layoutWithProduct;
    private TextView emptyText;

    public RemoveAllProductsDialog(Context context,  LinearLayout layoutWithProduct, TextView emptyText) {
        super(context);
        this.context = context;
        this.layoutWithProduct = layoutWithProduct;
        this.emptyText = emptyText;
    }
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("удаление продуктов из базы");
        builder.setMessage("вы точно хотите удалить всё?");
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
                Toast.makeText(context, "удалено!", Toast.LENGTH_SHORT).show();
                MyDbManager dbManager = new MyDbManager(context);
                dbManager.openDb();
                dbManager.removeAllFromDb();
                dbManager.closeDb();
                layoutWithProduct.removeAllViews();
                layoutWithProduct.addView(emptyText);
            case Dialog.BUTTON_NEGATIVE:
                RemoveAllProductsDialog.this.cancel();
        }
    }
}

