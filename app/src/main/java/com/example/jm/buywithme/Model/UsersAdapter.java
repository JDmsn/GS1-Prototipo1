package com.example.jm.buywithme.Model;

/**
 * Created by jm on 18.4.15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jm.buywithme.R;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jm.buywithme.R;
import com.example.jm.buywithme.Settings;

import java.util.*;

public class UsersAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> emails;
    private final ListView listview;
    private final boolean lock, section;
    private EditText edt;
    private ArrayList<String> a = new ArrayList<String>();

    public UsersAdapter(Activity context, ArrayList<String> emails, ListView listview, boolean lock, boolean section){
        super(context, R.layout.users, emails);
        this.context = context;
        this.emails = emails;
        this.listview = listview;
        this.lock = lock;
        this.section = section;
        if(lock && section && (emails.size() == 0)){
            Log.v("*****:", "ESTOY");
            this.emails.add(" ");
        }
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View ListViewSingle = inflater.inflate(R.layout.users, null, true);

        Data d = new Data();
        d.emailsArray = emails;

        d.textEmail = (EditText) ListViewSingle.findViewById(R.id.textUser);
        d.buttonUser = (Button) ListViewSingle.findViewById(R.id.buttonUser);

        this.edt = d.textEmail;

        if(!lock){
            d.textEmail.setClickable(false);
            d.buttonUser.setClickable(false);
            d.buttonUser.setEnabled(false);
            d.textEmail.setEnabled(false);

        }else if(lock && section){
            //d.textEmail.setEnabled(true);
            this.edt.setEnabled(true);

            for (int i = 0;i<emails.size();i++){
                Log.v("Empiezo***:", Integer.toString(emails.size()));

            }
            if(emails.size() > 0){
                edt.setText(emails.get(emails.size() - 1));
                emails.add(" ");
            }
            d.buttonUser.setText("INVITE");
            //d.buttonUser.setEnabled(true);

        }else if((!lock) && section){
            d.buttonUser.setText("INVITE");
            d.textEmail.setEnabled(false);
        }

        if(!section && (!emails.get(position).equals(" "))) {
            d.textEmail.setText(emails.get(position) + ".com");
        }else{
            d.textEmail.setText(emails.get(position));
        }

        if(position == 0 && (!section)){
            d.textEmail.setClickable(false);
            d.buttonUser.setClickable(false);
            d.buttonUser.setEnabled(false);
            d.textEmail.setEnabled(false);

        }

        //Añadimos a los usuarios, y añadimos un campo mas


        d.buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(section) {
                    //Log.v("eyyyyyyy:", edt.getText().toString());
                    //emails.add("pepepe");
                    //edt.setText(edt.getText().toString());
                    //emails.add(" ");
                    //emails.add("EEEYYY");
                    ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) listview.getLayoutParams();
                    lp.height = 135 * emails.size();
                    listview.setLayoutParams(lp);
                    //listview.setAdapter(new UsersAdapter(context, emails, listview, lock, section));
                }else if(!section){
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Remove participant")
                            .setMessage("Do you want to remove the participant from the list?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String email = (edt.getText().toString()).substring(0, edt.getText().toString().length() - 4);
                                    emails.remove(email);
                                    ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) listview.getLayoutParams();
                                    lp.height = 135 * emails.size();
                                    listview.setLayoutParams(lp);
                                    listview.setAdapter(new UsersAdapter(context, emails, listview, lock, section));

                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
        });

        return ListViewSingle;
    }


    public boolean getBool(){
        return true;
    }
    static class Data {
        EditText textEmail;
        Button buttonUser;
        ArrayList<String> emailsArray;
    }
}
