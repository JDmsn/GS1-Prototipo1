package com.example.jm.buywithme;

/**
 * Created by jm on 18.4.15.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.jm.buywithme.Model.UsersAdapter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Settings extends AppCompatActivity {
    private Intent intent;
    private EditText editNameList, editAdmin, addParticipantsField;
    private ListView participants, addParticipants;
    private ArrayList<String> listUsers = new ArrayList<>();
    private String admin, nameList;
    private boolean lock = false;
    private ArrayList<String> newEmails= new ArrayList<>();
    private UsersAdapter usersAdapter;
    private Button addParticipantButton, saveNameList, deleteList;
    private Dialog custom;
    private Toolbar tb;
    private Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        intent = getIntent();

        listUsers = intent.getStringArrayListExtra("users");
        admin = intent.getStringExtra("admin");
        nameList = intent.getStringExtra("nameList");
        lock = intent.getBooleanExtra("lock", false);
        tb = (Toolbar) findViewById(R.id.toolbar2);
        editNameList = (EditText) findViewById(R.id.inputNameList);
        editAdmin = (EditText) findViewById(R.id.inputAdmin);
        participants = (ListView) findViewById(R.id.participantsView);
        //addParticipants = (ListView) findViewById(R.id.allParticipants);
        addParticipantsField = (EditText) findViewById(R.id.addParticipantsField);
        addParticipantButton = (Button) findViewById(R.id.addParticipantButton);
        saveNameList = (Button) findViewById(R.id.saveNameList);
        deleteList = (Button) findViewById(R.id.deleteList);
        intent.putExtra("emails", true);
        //tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {});
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editNameList.setHint(nameList);
        editNameList.setEnabled(lock);
        editNameList.setFocusable(lock);
        saveNameList.setEnabled(lock);
        editAdmin.setText(admin + ".com");
        editAdmin.setEnabled(false);
        editAdmin.setFocusable(false);
        addParticipantButton.setEnabled(lock);

        addParticipantsField.setEnabled(lock);
        editNameList.setFocusable(lock);


        Log.v("usuario:", listUsers.get(0));

        //Section for participants
        usersAdapter = new UsersAdapter(Settings.this, listUsers, participants, lock, false);

        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) participants.getLayoutParams();
        lp.height = 135 * listUsers.size();
        participants.setLayoutParams(lp);
        participants.setAdapter(usersAdapter);

        saveNameList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editNameList.getText().toString().equals("")){
                    nameList = editNameList.getText().toString();
                    intent.putExtra("nameList", nameList);
                    editNameList.setHint(nameList);
                    editNameList.setText("");
                }
            }
        });

        deleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete List")
                        .setMessage("Do you want to delete the list?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                setResult(22, intent);
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

            }
        });

        addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Matcher m = pattern.matcher(addParticipantsField.getText().toString());
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Add participant")
                        .setMessage("Do you want to add the participant to the list?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                if(m.matches() && !listUsers.contains(addParticipantsField.getText().toString().substring(0, addParticipantsField.getText().toString().length() - 4))){
                                    addToMyList(addParticipantsField.getText().toString().substring(0, addParticipantsField.getText().toString().length() - 4));
                                    addParticipantsField.setText("");
                                    setResult(5, intent);
                                }else{
                                    Log.v("usuario:", addParticipantsField.getText().toString());
                                    Toast.makeText(Settings.this, "Participant incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });


    }

    private void addToMyList(String email) {
        listUsers.add(email);
        Toast.makeText(this, "Participant added!", Toast.LENGTH_SHORT).show();
        usersAdapter = new UsersAdapter(Settings.this, listUsers, participants, lock, false);
        intent.putStringArrayListExtra("users", listUsers);
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) participants.getLayoutParams();
        lp.height = 135 * listUsers.size();
        participants.setLayoutParams(lp);
        participants.setAdapter(usersAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case android.R.id.home:
                setResult(5, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            setResult(5, intent);
            onBackPressed();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }

}
