package com.example.jm.buywithme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.ListView;

import com.example.jm.buywithme.Model.Lista;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MyLists extends AppCompatActivity {
    private Intent intent, intentForSettings;
    private GridView gv;
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
        setContentView(R.layout.activity_my_lists);

        intentForSettings = new Intent("eventSettings");
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageSettings, new IntentFilter("eventSettings"));
        intentForSettings = new Intent(MyLists.this, Settings2.class);

        frau = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = frau.getCurrentUser();
        ref = database.getReference();

        intent = getIntent();
        listNames = intent.getStringArrayListExtra("listNames");
        listKeys = intent.getStringArrayListExtra("keyLists");

        gv = (GridView) findViewById(R.id.mylists);
        addToMyList(listNames);

        custom = new Dialog(MyLists.this);
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


        /**
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = listNames.remove(i);
                newNames.remove(name);

                resultList.add(name);
                intent.putStringArrayListExtra("resultList", resultList );
                addToMyList(listNames);

                return true;
            }
        });
         **/
        final EditText text = (EditText) custom.findViewById(R.id.nameOfMyNewList);
        Button add = (Button) custom.findViewById(R.id.add);
        Button cancel = (Button) custom.findViewById(R.id.cancel);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = Long.toString(System.nanoTime());

                listNames.add(text.getText().toString());
                listKeys.add(key);

                newNames.add(text.getText().toString());
                newKeys.add(key);

                addToMyList(listNames);
                intent.putExtra("isNew", true);
                intent.putStringArrayListExtra("newName", newNames);
                intent.putStringArrayListExtra("newKeys", newKeys);

                ArrayList<String> l = new ArrayList<>();
                l.add(user.getEmail().substring(0, user.getEmail().length() - 4));
                intent.putStringArrayListExtra(key, new ArrayList<String>(l));

                //NEW CODE
                Lista newList = new Lista(user.getEmail().substring(0, user.getEmail().length() - 4),  text.getText().toString());
                //person.addToMyLists(key, nameLst);
                //myLists.put(key, newList);


                Log.v("WHEN IM NEW", "There is a new element");
                ref.child("Lists").child(key).setValue(newList);
                ref.child("Users").child(user.getEmail().substring(0, user.getEmail().length() - 4)).child("myLists").child(key).push();
                ref.child("Users").child(user.getEmail().substring(0, user.getEmail().length() - 4)).child("myLists").child(key).setValue(text.getText().toString());

                text.setText("");
                custom.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom.dismiss();
            }
        });

        newList = (Button) findViewById(R.id.newlist);
        newList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                custom.show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(5,1,5,"Edit list");
        menu.add(6,2,6,"Delete list");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case 1:

                int pos = info.position;
                boolean lock = false;
                String actualList = listKeys.get(pos);
                String person = user.getEmail().substring(0, user.getEmail().length() - 4);

                ArrayList<String> allEmails = intent.getStringArrayListExtra(actualList);

                lock = allEmails.get(0).equals(person);


                intentForSettings.putExtra("admin", allEmails.get(0));
                intentForSettings.putExtra("nameList", listNames.get(pos));
                intentForSettings.putStringArrayListExtra("users", new ArrayList<String>(allEmails));
                intentForSettings.putExtra("lock",lock);
                intentForSettings.putExtra("numberOfRow", pos);
                startActivityForResult(intentForSettings, 5);



                return true;
            case 2:
                Log.v("El caso 2","´´´´´´¡¡¡¡¡¡¡¡");

                new AlertDialog.Builder(MyLists.this)
                        .setTitle("Delete List")
                        .setMessage("Do you want to delete the list?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                int pos = info.position;
                                String actualList = listKeys.get(pos);
                                String person = user.getEmail().substring(0, user.getEmail().length() - 4);

                                ArrayList<String> allEmails = intent.getStringArrayListExtra(actualList);
                                //Log.v("lo QUE ESTA",allEmails+",LO QUE ESTA EN LA LISTA AHORA:" + actualList);
                                listNames.get(pos);

                                //
                                if(allEmails.get(0).equals(person)){
                                    ArrayList<String> m = allEmails;
                                    for(int i = 0; i<m.size(); i++){
                                        ref.child("Users").child(m.get(i)).child("myLists").child(actualList).removeValue();

                                    }
                                    ref.child("Lists").child(actualList).removeValue();
                                    String l = listNames.remove(pos);
                                    String k = listKeys.remove(pos);
                                    newNames.remove(l);
                                    newKeys.remove(k);

                                    addToMyList(listNames);
                                }
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

                return true;

            default:
                return super.onContextItemSelected(item);

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data){

        if(resultcode == 22){
            int pos = intentForSettings.getIntExtra("numberOfRow", 0);

            String actualList = listKeys.get(pos);
            String person = user.getEmail().substring(0, user.getEmail().length() - 4);

            ArrayList<String> allEmails = intent.getStringArrayListExtra(actualList);

            if(allEmails.get(0).equals(person)) {
                ArrayList<String> m = allEmails;
                for (int i = 0; i < m.size(); i++) {
                    ref.child("Users").child(m.get(i)).child("myLists").child(actualList).removeValue();

                }
                ref.child("Lists").child(actualList).removeValue();
                String l = listNames.remove(pos);
                String k = listKeys.remove(pos);
                newNames.remove(l);
                newKeys.remove(k);

                addToMyList(listNames);
            }

        }


        if(resultcode == 5){
            int pos = intentForSettings.getIntExtra("numberOfRow", 0);
            Log.v("NUMERO DE LA FILA:", pos + "FILAAAA");
            String nl = listNames.get(pos);
            String actualList = listKeys.get(pos);

            String person = user.getEmail().substring(0, user.getEmail().length() - 4);

            ArrayList<String> allEmails = intent.getStringArrayListExtra(actualList);

            if(!data.getStringExtra("nameList").equals(nl)){

                listNames.remove(pos);
                listNames.add(pos, data.getStringExtra("nameList"));
                intent.putStringArrayListExtra("listNames", listNames);

                for(int i = 0; i<allEmails.size(); i++){
                    ref.child("Users").child(allEmails.get(i)).child("myLists").child(actualList).setValue(data.getStringExtra("nameList"));
                }
                ref.child("Lists").child(actualList).child("nameList").setValue(data.getStringExtra("nameList"));

                addToMyList(listNames);
            }

            //Who has been removed??
            ArrayList<String> p = new ArrayList<>(allEmails);
            ArrayList<String> u = data.getStringArrayListExtra("users");

            boolean check = false;

            for (int i = 0; i<p.size(); i++){
                for (int j = 0; j<u.size(); j++){
                    if(p.get(i).equals(u.get(j))){
                        check = true;
                        break;
                    }else{
                        check = false;
                    }
                }
                if(!check){
                    Log.v("IOO", "IIIIIIIIIIIIIIIIIIIIIIIII*******");
                    ref.child("Users").child(p.get(i)).child("myLists").child(actualList).removeValue();
                    allEmails.remove(p.get(i));
                    ref.child("Lists").child(actualList).child("users").setValue(allEmails);
                }
            }

            check=false;
            p = data.getStringArrayListExtra("users");
            u = new ArrayList<>(allEmails);
            if(p.size() == u.size()){
                for (int i = 0; i<p.size(); i++){
                    if(!p.get(i).equals(u.get(i))){
                        intent.putStringArrayListExtra(actualList, new ArrayList<String>(p));
                        ref.child("Lists").child(actualList).child("users").setValue(allEmails);
                        break;
                    }
                }

            }


            Log.v("IOO", "IIIIIIIIIIIIIIIIIIIIIIIII*******"+p.size());
            for (int i = 0; i<p.size(); i++){
                for (int j = 0; j<u.size(); j++){
                    if(p.get(i).equals(u.get(j))){
                        check = true;
                        break;
                    }else{
                        check = false;
                    }
                }
                if(!check){
                    Log.v("IOO", "IIIIIIIIIIIIIIIIIIIIIIIII*******");
                    ref.child("Users").child(p.get(i)).child("myLists").child(actualList).push();
                    ref.child("Users").child(p.get(i)).child("myLists").child(actualList).setValue(data.getStringExtra("nameList"));
                    allEmails.add(p.get(i));
                    ref.child("Lists").child(actualList).child("users").setValue(allEmails);
                }
            }

            if(!false){

                intent.putStringArrayListExtra(actualList, new ArrayList<String>(p));
                //list.setTime(System.nanoTime());
                //updateFirebase();
            }



        }
    }


    private void addToMyList(ArrayList<String> listNames) {
        listAdapter = new ListAdapter2(MyLists.this, listNames);

        gv.setAdapter(listAdapter);
        ViewGroup.LayoutParams params = gv.getLayoutParams();
        params.height = listNames.size() * 160;
        gv.setLayoutParams(params);
        gv.requestLayout();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        return false;
    }

    private BroadcastReceiver mMessageSettings = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

}
