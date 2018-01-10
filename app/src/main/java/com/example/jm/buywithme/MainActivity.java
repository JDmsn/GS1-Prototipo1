package com.example.jm.buywithme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button login;
    private EditText email;
    private EditText pass;

    private FirebaseAuth frau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frau = FirebaseAuth.getInstance();

        //In case that the user is already loged in
        if(frau.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainWindow.class));
        }

        login = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == login){
            //Check fields: email and password
            userLogin();
        }
    }

    private void userLogin() {
        String em = email.getText().toString().trim();
        String pas = pass.getText().toString().trim();

        frau.signInWithEmailAndPassword(em, pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainWindow.class));
                }else{
                    //User/password incorrect
                }
            }
        });
    }
}
