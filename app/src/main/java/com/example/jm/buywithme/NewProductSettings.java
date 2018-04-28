package com.example.jm.buywithme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jm on 18.4.26.
 */

public class NewProductSettings extends AppCompatActivity{

    private Intent intent;
    private Toolbar tb;
    private TextView nameP;
    private EditText inputQ, inputD, inputA, inputN;
    private Button acept,cancel,delete;
    private Integer im;
    private String nameProduct, quantity, description, owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_products_settings);
        tb = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Edit my own product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nameP = (TextView) findViewById(R.id.nameText);

        inputN = (EditText) findViewById(R.id.inputNameProduct);
        inputQ = (EditText) findViewById(R.id.inputQuantity);
        inputD = (EditText) findViewById(R.id.inputDescription);
        inputA = (EditText) findViewById(R.id.inputAddedBy);
        acept = (Button) findViewById(R.id.acceptButton);
        cancel = (Button) findViewById(R.id.cancelButton);
        delete = (Button) findViewById(R.id.deleteButton);

        intent = getIntent();

        owner = intent.getStringExtra("owner");

        final int typeOfEntry = intent.getIntExtra("typeOfEntry", 0);

        inputN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(typeOfEntry == 0 || typeOfEntry == 2){
                    if(inputN.getText().toString().length() != 0){
                        String a = inputN.getText().toString();
                        nameP.setText(a.substring(0,1));
                    }else{
                        nameP.setText("");
                    }


                }


            }
        });

        if(typeOfEntry == 1 || typeOfEntry == 2){
            nameProduct = intent.getStringExtra("nameProduct");
            quantity = intent.getStringExtra("quantity");
            description = intent.getStringExtra("description");
            inputN.setText(nameProduct);
            nameP.setText(nameProduct.substring(0,1));
            inputN.setEnabled(false);

        }else if(typeOfEntry == 1){
            delete.setEnabled(true);
            delete.setActivated(true);

        }else{
            delete.setEnabled(false);
            delete.setActivated(false);
            delete.setClickable(false);
        }


        if(!"0".equals(quantity)){
            inputQ.setHint(quantity);
        }

        if(!"n".equals(description)){
            inputD.setHint(description);
        }

        inputA.setEnabled(false);
        inputA.setText(owner);


        acept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!inputN.getText().toString().equals("")){
                    intent.putExtra("nameProduct", inputN.getText().toString());

                }

                if(!inputQ.getText().toString().equals("")){
                    intent.putExtra("quantity", inputQ.getText().toString());
                }else{
                    if(description != null){
                        intent.putExtra("quantity", quantity);
                    }else {
                        intent.putExtra("quantity", "0");
                    }
                }

                if(!inputD.getText().toString().equals("")){

                    intent.putExtra("description", inputD.getText().toString());

                }else{
                    if(description != null){
                        intent.putExtra("description", description);
                    }else {
                        intent.putExtra("description", "n");
                    }

                }

                if(typeOfEntry != 0){
                    setResult(9, intent);
                }else{
                    setResult(8, intent);
                }

                finish();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete product")
                        .setMessage("Do you want to delete your this product?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                intent.putExtra("name", nameProduct);
                                setResult(7, intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data){

    }

}
