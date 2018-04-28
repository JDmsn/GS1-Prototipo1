package com.example.jm.buywithme;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by jm on 18.4.19.
 */

public class EditElement extends AppCompatActivity {
    private Intent intent;
    private Toolbar tb;
    private ImageView pic;
    private EditText inputQ, inputD, inputA;
    private Button acept,cancel;
    private Integer im;
    private String nameProduct, quantity, description, owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_element);
        tb = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Edit product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pic = (ImageView) findViewById(R.id.image);
        inputQ = (EditText) findViewById(R.id.inputQuantity);
        inputD = (EditText) findViewById(R.id.inputDescription);
        inputA = (EditText) findViewById(R.id.inputAddedBy);
        acept = (Button) findViewById(R.id.acceptButton);
        cancel = (Button) findViewById(R.id.cancelButton);

        intent = getIntent();
        im = Integer.valueOf(intent.getStringExtra("image"));

        nameProduct = intent.getStringExtra("nameProduct");
        quantity = intent.getStringExtra("quantity");
        description = intent.getStringExtra("description");
        owner = intent.getStringExtra("owner");

        pic.setImageResource(im);


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

                setResult(9, intent);
                finish();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data){

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        return false;
    }


}

