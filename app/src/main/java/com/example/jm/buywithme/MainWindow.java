package com.example.jm.buywithme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jm.buywithme.Model.Lista;
import com.example.jm.buywithme.Model.ListAdapter;
import com.example.jm.buywithme.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainWindow extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private ImageButton f_and_v, b_and_p, m_and_c, s_and_s, f_f, p_supplies, h_and_h, m_and_f, im;
    private FirebaseAuth frau;
    private Intent in;
    private GridView gv;
    private Lista list = new Lista();
    private Lista newList = new Lista();
    private ListAdapter listAdapter;
    private int totalHeight;
    private ArrayList<Integer> data = new ArrayList<Integer>();
    private ArrayList<Integer> data1 = new ArrayList<Integer>();
    private ArrayList<String> resultList = new ArrayList<String>();
    private LinkedHashMap<String,Object> newMap = new LinkedHashMap<String,Object>();
    private Intent intent, intentForLists, intentForSettings;
    private String section, nameList;
    private LinkedHashMap<String, Object> myLists = new LinkedHashMap<>();
    private Toolbar toolbar;
    private User person;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private DatabaseReference ref, reference;
    private Handler handler;
    private boolean start = true;
    private Map<String,Lista> allLists= new HashMap<>();
    private String actualList = "";
    private ChildEventListener listEvent;
    private Long realTime;
    private boolean hasChange = false;
    private Map<String, Object> m = new LinkedHashMap<>();
    private Map<String, Object> dabaseReferences = new LinkedHashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        intentForLists = new Intent("eventLists");
        intentForSettings = new Intent("eventSettings");
        frau = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = frau.getCurrentUser();
        ref = database.getReference();
        realTime = 00000000001L;
        person = new User(user.getEmail(), user.getUid());

        ref.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if( (!dataSnapshot.hasChild(person.getEmail())) && person.getMyLists().size() == 0){
                    Long time = System.nanoTime();
                    person.addToMyLists(time.toString(), "Home");
                    ref.child("Users").child(person.getEmail()).setValue(person);
                    Lista nueva = new Lista(person.getEmail(), "Home");
                    ArrayList<Integer> a = new ArrayList();
                    a.add(2131361856);
                    ArrayList<Integer> b = new ArrayList();
                    b.add(2131230844);
                    ArrayList<Integer> c = new ArrayList();
                    c.add(2131230845);
                    ArrayList<String> d = new ArrayList();
                    d.add("m_and_c");

                    ArrayList<String> q = new ArrayList();
                    q.add("0");
                    ArrayList<String> de = new ArrayList();
                    de.add("n");
                    ArrayList<String> o = new ArrayList();
                    o.add(person.getEmail() + ".com");

                    //An example:
                    nueva.setId(a);
                    nueva.setArray(b);
                    nueva.setArrayw(c);
                    nueva.setSection(d);

                    nueva.setQuantity(q);
                    nueva.setDescription(de);
                    nueva.setOwner(o);

                    myLists.put(time.toString(), nueva);
                    ref.child("Lists").child(time.toString()).setValue(nueva);
                }
                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ref.child("Users").child(person.getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.getKey().equals("myLists")){
                    for (DataSnapshot ds: dataSnapshot.getChildren()) {
                        person.addToMyLists(ds.getKey(), (String) ds.getValue());
                        if(start){
                            actualList = ds.getKey();
                            nameList = (String) ds.getValue();
                            start = false;
                            childEventListener();
                            reference.addChildEventListener(listEvent);
                            refreshMyList(list.getList());

                        }

                    }

                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("myLists")){
                    person.setMyLists((Map<String, String>) dataSnapshot.getValue());
                    //Cual me cambiaste?

                    if(!((Map<String, String>) dataSnapshot.getValue()).containsKey(actualList)){
                        Toast.makeText(MainWindow.this, "You've been removed from one of the lists", Toast.LENGTH_SHORT).show();
                        Map<String, String> a = person.getMyLists();
                        for (String key : a.keySet()) {
                            Log.v("TENEMOOS:", key+"++++actualList:"+a.get(key));
                            //list = (Lista) myLists.get(key);
                            actualList = key;
                            nameList = person.getMyLists().get(key);
                            childEventListener();
                            reference.addChildEventListener(listEvent);
                            break;
                        }

                        setTitleToolBar(nameList);
                    }

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        setMyLists();

        setTitleToolBar(nameList);

        handler = new Handler();
        handler.postDelayed(r, 10000);


        f_and_v = (ImageButton) findViewById(R.id.f_and_v);
        b_and_p = (ImageButton) findViewById(R.id.b_and_p);
        m_and_c = (ImageButton) findViewById(R.id.m_and_c);
        m_and_f = (ImageButton) findViewById(R.id.m_and_f);
        s_and_s = (ImageButton) findViewById(R.id.s_and_s);
        f_f = (ImageButton) findViewById(R.id.f_f);
        p_supplies = (ImageButton) findViewById(R.id.p_supplies);
        h_and_h = (ImageButton) findViewById(R.id.h_and_h);
        f_and_v.setOnClickListener(this);
        b_and_p.setOnClickListener(this);
        m_and_c.setOnClickListener(this);
        s_and_s.setOnClickListener(this);
        f_f.setOnClickListener(this);
        p_supplies.setOnClickListener(this);
        h_and_h.setOnClickListener(this);
        m_and_f.setOnClickListener(this);

        gv = (GridView) findViewById(R.id.basket);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteElement(i);
            }
        });

        ViewGroup.LayoutParams params = gv.getLayoutParams();
        params.height = 330;
        gv.setLayoutParams(params);
        gv.requestLayout();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("event"));

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver2, new IntentFilter("eventLists"));

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageSettings, new IntentFilter("eventSettings"));

        in = new Intent(MainWindow.this, SFV.class);

        intentForLists = new Intent(MainWindow.this, MyLists.class);
        intentForSettings = new Intent(MainWindow.this, Settings.class);

        //dr1.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }


    private void childEventListener() {

        reference = ref.child("Lists").child(actualList);
        list = new Lista();
        listEvent = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                String sg = dataSnapshot.getKey();

                if (sg.equals("id")) {
                    ArrayList<Long> a = (ArrayList<Long>) dataSnapshot.getValue();
                    ArrayList<Integer> b = new ArrayList();
                    int sz = a.size();

                    for(int i=0; i<sz; i++){
                        b.add((int) (long) a.get(i));

                    }
                    list.setId(b);
                } else if (sg.equals("list")) {
                    ArrayList<Long> a = (ArrayList<Long>) dataSnapshot.getValue();
                    ArrayList<Integer> b = new ArrayList();
                    int sz = a.size();

                    for(int i=0; i<sz; i++){
                        b.add((int) (long) a.get(i));

                    }
                    list.setArray(b);
                } else if (sg.equals("listw")) {
                    ArrayList<Long> a = (ArrayList<Long>) dataSnapshot.getValue();
                    ArrayList<Integer> b = new ArrayList();
                    int sz = a.size();

                    for(int i=0; i<sz; i++){
                        b.add((int) (long) a.get(i));

                    }
                    list.setArrayw(b);

                } else if (sg.equals("section")) {
                    list.setSection((ArrayList<String>) dataSnapshot.getValue());
                } else if (sg.equals("time")){
                    //fireCloud.setTime((Long) ds.getValue());
                    realTime = (Long) dataSnapshot.getValue();
                    list.setTime(realTime);
                    refreshMyList(list.getList());
                } else if (sg.equals("nameList")){
                    list.setNameList((String) dataSnapshot.getValue());
                    nameList = (String) dataSnapshot.getValue();

                }else if (sg.equals("admin")){
                    list.setAdmin((String) dataSnapshot.getValue());

                } else if(sg.equals("users")){
                    list.setUsers((ArrayList<String>) dataSnapshot.getValue());
                    myLists.put(dataSnapshot.getKey(), list);
                    setTitleToolBar(nameList);
                    refreshMyList(list.getList());
                } else if(sg.equals("description")){
                    list.setDescription((ArrayList<String>) dataSnapshot.getValue());

                }else if(sg.equals("owner")){
                    list.setOwner((ArrayList<String>) dataSnapshot.getValue());

                }else if(sg.equals("quantity")){
                    list.setQuantity((ArrayList<String>) dataSnapshot.getValue());
                }

            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String sg = dataSnapshot.getKey();

                Log.v("EYY", "EL VALOR DESPUES DE CLICAR ES:*******"+reference+ "," + dataSnapshot.getRef());

                if (sg.equals("id")) {
                    ArrayList<Long> a = (ArrayList<Long>) dataSnapshot.getValue();
                    ArrayList<Integer> b = new ArrayList();
                    int sz = a.size();

                    for (int i = 0; i < sz; i++) {
                        b.add((int) (long) a.get(i));

                    }
                    newList.setId(b);

                    list.setId(b);
                } else if (sg.equals("list")) {
                    ArrayList<Long> a = (ArrayList<Long>) dataSnapshot.getValue();
                    ArrayList<Integer> b = new ArrayList();
                    int sz = a.size();

                    for (int i = 0; i < sz; i++) {
                        b.add((int) (long) a.get(i));

                    }
                    newList.setArray(b);

                    list.setArrayw(b);
                } else if (sg.equals("listw")) {
                    ArrayList<Long> a = (ArrayList<Long>) dataSnapshot.getValue();
                    ArrayList<Integer> b = new ArrayList();
                    int sz = a.size();

                    for (int i = 0; i < sz; i++) {
                        b.add((int) (long) a.get(i));

                    }
                    newList.setArrayw(b);

                    list.setArrayw(b);
                } else if (sg.equals("section")) {
                    newList.setSection((ArrayList<String>) dataSnapshot.getValue());
                    list.setSection((ArrayList<String>) dataSnapshot.getValue());
                    hasChange = true;
                } else if (sg.equals("time")) {

                    list.setTime((Long) dataSnapshot.getValue());
                    //setTitleToolBar(list.getNameList());
                    //nameList = list.getNameList();

                    Log.v(actualList, "WHY CHANGE MY OTHER LIST??******+++" + nameList);
                    if (hasChange) {
                        hasChange = false;

                        list.setId(newList.getId());
                        list.setArray(newList.getList());
                        list.setArrayw(newList.getListw());
                        list.setSection(newList.getSection());

                        refreshMyList(newList.getList());
                    }

                    realTime = (Long) dataSnapshot.getValue();
                    Log.v(sg, "Time has changed+++++++++");
                    if ((Long) dataSnapshot.getValue() > list.getTime()) {
                        Log.v(sg, "***VALUE_sTRING*****");

                        //list = newList;

                        /**
                         if(newList.getId().size() != 0) {
                         list.setId(newList.getId());
                         list.setArray(newList.getList());
                         list.setArrayw(newList.getListw());
                         list.setSection(newList.getSection());
                         list.setNameList(newList.getNameList());

                         list.setTime((Long) dataSnapshot.getValue());
                         realTime = list.getTime();
                         refreshMyList(list.getList());
                         }

                         //setTitleToolBar(newList.getNameList());
                         **/
                    }

                } else if (sg.equals("admin")) {
                    newList.setAdmin((String) dataSnapshot.getValue());

                } else if (sg.equals("nameList")) {
                    newList.setNameList((String) dataSnapshot.getValue());
                    nameList = (String) dataSnapshot.getValue();
                    list.setNameList(nameList);
                    //myLists.put(actualList)
                    setTitleToolBar(nameList);

                } else if (sg.equals("users") && realTime > list.getTime()) {
                    Log.v(sg, "Users have changed");
                    ArrayList<String> u = (ArrayList<String>) dataSnapshot.getValue();
                    if (u.contains(person.getEmail())) {
                        list.setTime(realTime);
                        list.setUsers((ArrayList<String>) dataSnapshot.getValue());
                        //myLists.put(dataSnapshot.getKey(), list);
                        setTitleToolBar(nameList);
                    } else {
                        ArrayList<String> names = new ArrayList<>();
                        ArrayList<String> keyLists = new ArrayList<>();
                        Map<String, String> map = person.getMyLists();
                        for (String key : map.keySet()) {
                            names.add(map.get(key));
                            keyLists.add(key);
                        }

                        intentForLists.putStringArrayListExtra("listNames", names);
                        intentForLists.putStringArrayListExtra("keyLists", keyLists);

                        startActivityForResult(intentForLists, 1);
                    }

                } else if(sg.equals("description")){
                    list.setDescription((ArrayList<String>) dataSnapshot.getValue());

                }else if(sg.equals("owner")){
                    list.setOwner((ArrayList<String>) dataSnapshot.getValue());

                }else if(sg.equals("quantity")){
                    list.setQuantity((ArrayList<String>) dataSnapshot.getValue());
                    refreshMyList(list.getList());
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


    }


    private void whatIsInTheList(Lista list) {
        ArrayList<String> a= (ArrayList) list.getSection();
        for (int i=0;i<a.size();i++){
            Log.v("IT HAS:", a.get(i));
        }
    }


    final Runnable r = new Runnable() {
        public void run() {

            //UPDATING FIREBASE
            updateFirebase();
            handler.postDelayed(this, 10000);
        }
    };

    private void updateFirebase(){
        //String id = user.getUid();
        //ref.child("Lists").child(id).child(nameList).setValue(list);
        //revisar si la lista ha cambiado
        ref.child("Lists").child(actualList).setValue(list);


    }


    private BroadcastReceiver mMessageSettings = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    private BroadcastReceiver mMessageReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


        }
    };

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            data = intent.getIntegerArrayListExtra("data");
            Integer image = data.get(0);
            Integer imagew =  data.get(1);
            Integer id = data.get(2);
            int lock = data.get(3);
            section = in.getStringExtra("section");
            String q = "0";
            String d = "n";
            String o = person.getEmail() + ".com";

            if(lock == 1){
                addElement(image, imagew, id, section, q, d, o);
            }else{
                deleteElement(image, imagew, id, section, q, d, o);

            }

        }
    };

    @Override
    protected void onDestroy(){
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_window, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intentForSettings.putExtra("admin", list.getAdmin());
            intentForSettings.putExtra("nameList", nameList);
            intentForSettings.putStringArrayListExtra("users", new ArrayList<String>(list.getUsers()));
            intentForSettings.putExtra("lock",person.getEmail().equals(list.getAdmin()));
            startActivityForResult(intentForSettings, 5);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            //Here we show the list
            ArrayList<String> names = new ArrayList<>();
            ArrayList<String> keyLists = new ArrayList<>();
            Map<String, String> map = person.getMyLists();

            Log.v("PASANDO QUE ESTA ?", "PASANDO¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡");


            for(final String key: map.keySet()){
                names.add(map.get(key));
                keyLists.add(key);
                ArrayList<String> l = new ArrayList<>((ArrayList<String>) m.get(key));
                intentForLists.putStringArrayListExtra(key,l);

            }

            intentForLists.putStringArrayListExtra("listNames", names);
            intentForLists.putStringArrayListExtra("keyLists", keyLists);

            startActivityForResult(intentForLists, 1);
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.sign_out) {
            frau.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public ArrayList<String> getEmailsFromAllTheLists(String key) {
        final ArrayList<String> emailsInList = new ArrayList<>();

        ref.child("Lists").child(key).child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                emailsInList.add((String) dataSnapshot.getValue());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return emailsInList;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data){
        if(resultcode == 22){
            if(list.getAdmin().equals(person.getEmail())){
                ArrayList<String> m = new ArrayList<>(list.getUsers());
                for(int i = 0; i<m.size(); i++){
                    ref.child("Users").child(m.get(i)).child("myLists").child(actualList).removeValue();

                }
                ref.child("Lists").child(actualList).removeValue();

                person.deleteFromMyLists(Long.valueOf(actualList));
                myLists.remove(actualList);

                Map<String, String> a = person.getMyLists();

                for (String key : a.keySet()) {
                    Log.v("TENEMOOS:", key+"++++actualList:"+a.get(key));
                    //list = (Lista) myLists.get(key);
                    actualList = key;
                    nameList = person.getMyLists().get(key);
                    childEventListener();
                    reference.addChildEventListener(listEvent);
                    break;
                }

                setTitleToolBar(nameList);
                //refreshMyList(list.getList());

            }else{
                ref.child("Lists").child(actualList).child("users").child(person.getEmail()).removeValue();
                ref.child("Users").child(person.getEmail()).child("myLists").child(actualList).removeValue();

                for (String key : myLists.keySet()) {
                    list = (Lista) myLists.get(key);
                    actualList = key;
                    nameList = list.getNameList();
                    break;
                }
                setTitleToolBar(nameList);
                refreshMyList(list.getList());
            }


        }

        if(resultcode == 5){
            if(!data.getStringExtra("nameList").equals(list.getNameList())){
                list.setNameList(data.getStringExtra("nameList"));
                setTitleToolBar(data.getStringExtra("nameList"));
                person.getMyLists().put(actualList, data.getStringExtra("nameList"));
                nameList = data.getStringExtra("nameList");
                ArrayList<String> m = new ArrayList<>(list.getUsers());

                for(int i = 0; i<m.size(); i++){
                    ref.child("Users").child(m.get(i)).child("myLists").child(actualList).setValue(data.getStringExtra("nameList"));
                }

                list.setTime(System.nanoTime());
                updateFirebase();

            }
            //Who has been removed??
            ArrayList<String> p = new ArrayList<>(list.getUsers());
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
                }
            }

            check=false;
            p = data.getStringArrayListExtra("users");
            u = new ArrayList<>(list.getUsers());
            if(p.size() == u.size()){
                for (int i = 0; i<p.size(); i++){
                    if(!p.get(i).equals(u.get(i))){
                        list.setUsers(p);
                        list.setTime(System.nanoTime());
                        updateFirebase();

                        break;
                    }
                }
            }

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

                    ref.child("Users").child(p.get(i)).child("myLists").child(actualList).push();
                    ref.child("Users").child(p.get(i)).child("myLists").child(actualList).setValue(data.getStringExtra("nameList"));
                }
            }

            if(!false){
                list.setUsers(p);
                list.setTime(System.nanoTime());
                updateFirebase();
            }
            check = false;

        }

        if(resultcode == 3){
            if(data.getBooleanExtra("isNew", false)){
                int size = (data.getStringArrayListExtra("newName")).size();
                for(int i = 0; i<size; i++){
                    String nameLst = data.getStringArrayListExtra("newName").get(i);
                    String key = data.getStringArrayListExtra("newKeys").get(i);
                    Lista newList = new Lista(person.getEmail(), nameLst);
                    person.addToMyLists(key, nameLst);
                    myLists.put(key, newList);
                    ref.child("Lists").child(key).setValue(newList);
                    ref.child("Users").child(person.getEmail()).setValue(person);
                }

                data.putExtra("isNew",false);
            }

            reference.removeEventListener(listEvent);

            setTitleToolBar(data.getStringExtra("listName"));
            nameList = data.getStringExtra("listName");
            actualList = data.getStringExtra("keyList");
            childEventListener();
            reference.addChildEventListener(listEvent);
            resultList = data.getStringArrayListExtra("resultList");
            intentForLists.putExtra("lock", false);

        }
    }

    @Override
    public void onClick(View view) {
        in.putExtra("boolean",false);

        if (view == f_and_v){
            section = "f_and_v";
            in.putExtra("section", "f_and_v");
            in.putExtra("boolean", true);
            in.putIntegerArrayListExtra("data2", list.getList("f_and_v").getListw());
            in.putIntegerArrayListExtra("data3", list.getList("f_and_v").getId());

            startActivity(in);
        }else if (view == b_and_p){
            section = "b_and_p";
            in.putExtra("boolean", true);
            in.putIntegerArrayListExtra("data2", list.getList("b_and_p").getListw());
            in.putIntegerArrayListExtra("data3", list.getList("b_and_p").getId());
            in.putExtra("section", "b_and_p");

            startActivity(in);
        }else if (view == m_and_c){
            section = "m_and_c";
            in.putExtra("boolean", true);
            in.putExtra("section", section);
            in.putIntegerArrayListExtra("data2", list.getList(section).getListw());
            in.putIntegerArrayListExtra("data3", list.getList(section).getId());

            startActivity(in);
        }else if(view == m_and_f){
            section = "m_and_f";
            in.putExtra("boolean", true);
            in.putExtra("section", section);
            in.putIntegerArrayListExtra("data2", list.getList(section).getListw());
            in.putIntegerArrayListExtra("data3", list.getList(section).getId());

            startActivity(in);
        }else if(view == s_and_s){
            section = "s_and_s";
            in.putExtra("boolean", true);
            in.putExtra("section", section);
            in.putIntegerArrayListExtra("data2", list.getList(section).getListw());
            in.putIntegerArrayListExtra("data3", list.getList(section).getId());
            startActivity(in);

        }else if(view == f_f){
            section = "f_f";
            in.putExtra("section", section);
            in.putExtra("boolean", true);
            in.putIntegerArrayListExtra("data2", list.getList(section).getListw());
            in.putIntegerArrayListExtra("data3", list.getList(section).getId());
            startActivity(in);

        }else if(view == p_supplies){
            section = "p_supplies";
            in.putExtra("section", section);
            in.putExtra("boolean", true);
            in.putIntegerArrayListExtra("data2", list.getList(section).getListw());
            in.putIntegerArrayListExtra("data3", list.getList(section).getId());

            startActivity(in);

        }else if(view == h_and_h){
            section = "h_and_h";
            in.putExtra("section", section);
            in.putExtra("boolean", true);
            in.putIntegerArrayListExtra("data2", list.getList(section).getListw());
            in.putIntegerArrayListExtra("data3", list.getList(section).getId());

            startActivity(in);
        }

    }

    private void deleteElement(int i){
        broadAddDelete(list.getList().get(i), list.getId().get(i));

        list.delete(i);
        list.setTime(System.nanoTime());

        decreaseSizeBasket();

        //myList is an ArrayList of INTEGER, so we must parse it to ArrayList of String
        ArrayList<Integer> myList = list.getList();
        ArrayList<String> theList = new ArrayList<>();
        ArrayList<String> theQuantity = new ArrayList<>(list.getQuantity());

        for(int j = 0; j<myList.size(); j++){
            theList.add(myList.get(j).toString());
        }

        theList.addAll(theList.size() , theQuantity);

        listAdapter = new ListAdapter(MainWindow.this, theList);
        gv.setAdapter(listAdapter);
        updateFirebase();
    }

    private void getAllTheLists(final String key){

        ref.child("Lists").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (((String)dataSnapshot.getKey()).equals(key)){
                    myLists.put(key, dataSnapshot.getValue());

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setMyLists(){
        Map<String, String> map = person.getMyLists();

        for(String key: map.keySet()){
            getAllTheLists(key);
        }

    }

    private void refreshMyList(ArrayList myList){
        //In case that we need to delete from myLists
        if(resultList != null) {
            for (String key : myLists.keySet()) {
                for(int i = 0 ; i<resultList.size() ; i++){
                    if(key.equals(resultList.get(i))){
                        myLists.remove(key);
                    }
                }
            }
        }

        //myList is an ArrayList of INTEGER, so we must parse it to ArrayList of String
        ArrayList<String> theList = new ArrayList<>();
        ArrayList<String> theQuantity = new ArrayList<>(list.getQuantity());

        for(int i = 0; i<myList.size(); i++){
            theList.add(myList.get(i).toString());
        }

        theList.addAll(theList.size() , theQuantity);

        listAdapter = new ListAdapter(MainWindow.this, theList);
        gv.setAdapter(listAdapter);
        updateAllUsers();
    }

    public void updateAllUsers(){
        Map<String, String> map = person.getMyLists();
        m = new LinkedHashMap<>();

        for(String key: map.keySet()){
            m.put(key, getEmailsFromAllTheLists(key));

        }
    }

    private void deleteElement(Integer image, Integer imagew, Integer id, String section, String quantity, String description, String owner){
        list.delete(image, imagew, id, section, quantity, description, owner);
        list.setTime(System.nanoTime());

        decreaseSizeBasket();

        //myList is an ArrayList of INTEGER, so we must parse it to ArrayList of String
        ArrayList<Integer> myList = list.getList();
        ArrayList<String> theList = new ArrayList<>();
        ArrayList<String> theQuantity = new ArrayList<>(list.getQuantity());

        for(int i = 0; i<myList.size(); i++){
            theList.add(myList.get(i).toString());
        }

        theList.addAll(theList.size() , theQuantity);


        listAdapter = new ListAdapter(MainWindow.this, theList);
        gv.setAdapter(listAdapter);
        updateFirebase();
    }

    private void addElement(Integer element, Integer elementw, Integer id, String section, String quantity, String description, String owner){
        list.save(element, elementw, id, section, quantity, description, owner);
        list.setTime(System.nanoTime());

        broadAddDelete(elementw, id);

        increaseSizeBasket();

        //myList is an ArrayList of INTEGER, so we must parse it to ArrayList of String
        ArrayList<Integer> myList = list.getList();
        ArrayList<String> theList = new ArrayList<>();
        ArrayList<String> theQuantity = new ArrayList<>(list.getQuantity());

        for(int i = 0; i<myList.size(); i++){
            theList.add(myList.get(i).toString());
        }

        theList.addAll(theList.size() , theQuantity);

        listAdapter = new ListAdapter(MainWindow.this, theList);
        gv.setAdapter(listAdapter);
        updateFirebase();
    }

    public void broadAddDelete(Integer element, Integer id){
        intent = new Intent("event1");
        data1.clear();
        data1.add(element);
        data1.add(id);
        intent.putIntegerArrayListExtra("data1", data1);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void decreaseSizeBasket(){
        if(list.size() % 3 == 0 && list.size()!=0){
            totalHeight--;
            totalHeight = list.size()/3;
        }

        ViewGroup.LayoutParams params = gv.getLayoutParams();
        params.height = 330 * totalHeight;
        gv.setLayoutParams(params);
        gv.requestLayout();
    }

    private void increaseSizeBasket(){
        totalHeight = list.size()/3;

        if(list.size() % 3 !=0){
            totalHeight++;
        }

        ViewGroup.LayoutParams params = gv.getLayoutParams();
        params.height = 330 * totalHeight;
        gv.setLayoutParams(params);
        gv.requestLayout();
    }

    private void setTitleToolBar(String name) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);

    }


}
