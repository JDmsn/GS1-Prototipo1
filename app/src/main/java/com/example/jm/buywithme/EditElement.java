


package com.example.jm.buywithme;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
/**
 * Created by jm on 18.4.19.
 */

public class EditElement extends AppCompatActivity {
    private Intent intent, intentForSettings;
    private GridView gv;
    private Toolbar tb;
    private ArrayList<String> listNames = new ArrayList<>();
    private ArrayList<String> listKeys = new ArrayList<>();
    private ArrayList<String> newNames = new ArrayList<>();
    private ArrayList<String> newKeys = new ArrayList<>();
    private ListAdapter2 listAdapter;
    private Button newList;
    private Dialog custom;
    private ArrayList<String> resultList = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseUser user;
    private FirebaseAuth frau;
    private DatabaseReference ref, reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_element);
        tb = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Edit product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        /**
         listNames = intent.getStringArrayListExtra("listNames");
         listKeys = intent.getStringArrayListExtra("keyLists");

         gv = (GridView) findViewById(R.id.mylists);

         custom.setContentView(R.layout.dialog_add_list);
         custom.setTitle("New Lista");

         registerForContextMenu(gv);


         gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        intent.putExtra("lock", true);
        intent.putExtra("listName", listNames.get(i));
        intent.putExtra("keyList", listKeys.get(i));

        setResult(3, intent);
        finish();
        }
        });

         **/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data){

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        return false;
    }


}

