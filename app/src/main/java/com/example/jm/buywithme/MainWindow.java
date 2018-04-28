package com.example.jm.buywithme;

import android.app.SearchManager;
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
import android.view.MenuInflater;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jm.buywithme.Model.Lista;
import com.example.jm.buywithme.Model.ListAdapter;
import com.example.jm.buywithme.Model.User;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MainWindow extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private ImageButton f_and_v, b_and_p, m_and_c, s_and_s, f_f, p_supplies, h_and_h, m_and_f, own_products, recently_used, im;
    private FirebaseAuth frau;
    private Intent in;
    private GridView gv;
    private Lista list = new Lista();
    private Lista newList = new Lista();
    private ListAdapter listAdapter, adapterSearch;
    private int totalHeight;
    private ArrayList<Integer> data = new ArrayList<Integer>();
    private ArrayList<Integer> data1 = new ArrayList<Integer>();
    private ArrayList<String> resultList = new ArrayList<String>();
    private ArrayList<String> allTheImages = new ArrayList<>();
    private ArrayList<String> allTheQuantities = new ArrayList<>();
    private ArrayList<String> allTheNames= new ArrayList<>();
    private LinkedHashMap<String,Object> newMap = new LinkedHashMap<String,Object>();
    private Intent intent, intentForLists, intentForSettings, intentEditProduct, intentCreateProduct, intentNewProductSettings, intentRecentProduct;
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
    private Map<String, String> ow = new HashMap<>();
    private BiMap<String,Integer> bi = HashBiMap.create();
    private BiMap<String,Integer> biId = HashBiMap.create();
    private Map<String,String> biSec = new LinkedHashMap<>();

    private ArrayList<String> rNames = new ArrayList<>();
    private ArrayList<Integer> rNumbers = new ArrayList<>();

    private SearchView sch;
    private GridView searchGV;
    private TableLayout tb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        fillBiMap();
        fillBiMapId();
        fillMapSec();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        intentForLists = new Intent("eventLists");
        intentForSettings = new Intent("eventSettings");
        intentEditProduct = new Intent("eventEditProduct");
        intentNewProductSettings = new Intent("eventEditOwnProduct");
        intentCreateProduct = new Intent("eventOwnProducts");
        intentRecentProduct = new Intent("eventRecentlyUsed");

        frau = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = frau.getCurrentUser();
        ref = database.getReference();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        realTime = 00000000001L;
        person = new User(user.getEmail(), user.getUid());
        tb1 = (TableLayout) findViewById(R.id.table1);
        gv = (GridView) findViewById(R.id.basket);
        searchGV = (GridView) findViewById(R.id.searchGrid);

        ArrayList<String> b = new ArrayList();
        b.add("2131230844");

        ArrayList<String> q = new ArrayList();
        q.add("0");

        ArrayList<String> pn = new ArrayList();
        pn.add(bi.inverse().get(2131230844));

        b.addAll(b.size(), q);
        b.addAll(b.size(), pn);

        adapterSearch = new ListAdapter(this, new ArrayList<String>());

        sch = (SearchView) findViewById(R.id.search);

        sch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.equals("")){
                    searchGV.setVisibility(View.GONE);
                    tb1.setVisibility(View.VISIBLE);
                    gv.setVisibility(View.VISIBLE);
                }else{
                    tb1.setVisibility(View.GONE);
                    gv.setVisibility(View.GONE);
                    searchGV.setVisibility(View.VISIBLE);
                }

                adapterSearch = new ListAdapter(MainWindow.this, getSearch(s));
                searchGV.setAdapter(adapterSearch);
                refreshSizeOfSearchBox();

                searchGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String name = allTheNames.get(i);
                        List<String> n = list.getProductName();
                        if(name.equals("new_product")){
                            sch.setQuery("", true);
                            searchGV.setVisibility(View.GONE);
                            tb1.setVisibility(View.VISIBLE);
                            gv.setVisibility(View.VISIBLE);
                            intentNewProductSettings.putExtra("typeOfEntry", 0);
                            intentNewProductSettings.putExtra("owner", person.getEmail() + ".com");
                            startActivityForResult(intentNewProductSettings,12);

                        }else if(n.contains(name)){
                            for(int j = 0; j<n.size(); j++) {
                                if(n.get(j).equals(name)){
                                    list.delete(j);
                                    break;
                                }
                            }
                            refreshMyList(list.getList());
                            updateFirebase();

                        }else if(!n.contains(name)){
                            String pName = bi.inverse().get(Integer.parseInt(allTheImages.get(i)));
                            Integer image = Integer.parseInt(allTheImages.get(i));
                            Integer imageW = new Integer(image + 1);
                            String sec = biSec.get(pName);
                            Integer pId = biId.get(pName);

                            //Integer element, Integer elementw, Integer id, String section, String quantity, String description, String owner, String productName
                            addElement(image, imageW, pId, sec, "0", "n", person.getEmail() + ".com", pName);

                        }

                        sch.setQuery("", true);
                        searchGV.setVisibility(View.GONE);
                        tb1.setVisibility(View.VISIBLE);
                        gv.setVisibility(View.VISIBLE);
                    }
                });

                searchGV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Integer a = Integer.parseInt(allTheImages.get(i));
                        Integer b = new Integer(a + 1);

                        Log.v("HE HECHO UN TOQUE LARGO", "AQUI ESTOYYY+**+*+**++*+**+*+*+*++*" + a + " ," + b);
                        sch.setQuery("", true);
                        searchGV.setVisibility(View.GONE);
                        tb1.setVisibility(View.VISIBLE);
                        gv.setVisibility(View.VISIBLE);
                        return true;
                    }
                });

                return false;
            }
        });



        ref.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if( (!dataSnapshot.hasChild(person.getEmail())) && person.getMyLists().size() == 0){
                    Long time = System.nanoTime();
                    person.addToMyLists(time.toString(), "Home");
                    ref.child("Users").child(person.getEmail()).setValue(person);
                    Lista nueva = new Lista(person.getEmail(), "Home");

                    /**
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

                    ArrayList<String> pn = new ArrayList();
                    pn.add(bi.inverse().get(2131230844));

                    //An example:
                    nueva.setId(a);
                    nueva.setArray(b);
                    nueva.setArrayw(c);
                    nueva.setSection(d);

                    nueva.setQuantity(q);
                    nueva.setDescription(de);
                    nueva.setOwner(o);
                    **/

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

                        reference.removeEventListener(listEvent);

                        Map<String, String> a = person.getMyLists();
                        for (String key : a.keySet()) {
                            actualList = key;
                            nameList = person.getMyLists().get(key);
                            childEventListener();
                            reference.addChildEventListener(listEvent);
                            break;
                        }

                        refreshMyList(list.getList());
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
        own_products = (ImageButton) findViewById(R.id.own_products);
        recently_used = (ImageButton) findViewById(R.id.recently_used);
        f_and_v.setOnClickListener(this);
        b_and_p.setOnClickListener(this);
        m_and_c.setOnClickListener(this);
        s_and_s.setOnClickListener(this);
        f_f.setOnClickListener(this);
        p_supplies.setOnClickListener(this);
        h_and_h.setOnClickListener(this);
        m_and_f.setOnClickListener(this);
        own_products.setOnClickListener(this);
        recently_used.setOnClickListener(this);

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
        //registerForContextMenu(gv);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("event"));

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver2, new IntentFilter("eventLists"));

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageSettings, new IntentFilter("eventSettings"));

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageOwnProducts, new IntentFilter("eventOwnProducts"));

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageRecentProducts, new IntentFilter("eventRecentlyUsed"));

        in = new Intent(MainWindow.this, SFV.class);

        intentNewProductSettings = new Intent(MainWindow.this, NewProductSettings.class);
        intentForLists = new Intent(MainWindow.this, MyLists.class);
        intentForSettings = new Intent(MainWindow.this, Settings.class);
        intentEditProduct = new Intent(MainWindow.this, EditElement.class);
        intentCreateProduct = new Intent(MainWindow.this, OwnProduct.class);
        intentRecentProduct = new Intent(MainWindow.this, RecentProduct.class);

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


        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
              @Override
              public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                  //enviar los datos al editar producto: imagen, nombre, cantidad, descripcion, propietario

                  if(!list.getSection().get(i).equals("own_products")) {
                      intentEditProduct.putExtra("image", Integer.toString(list.getList().get(i)));
                      intentEditProduct.putExtra("nameProduct", list.getProductName().get(i));
                      intentEditProduct.putExtra("quantity", list.getQuantity().get(i));
                      intentEditProduct.putExtra("description", list.getDescription().get(i));
                      intentEditProduct.putExtra("owner", list.getOwner().get(i));
                      intentEditProduct.putExtra("pos", i);

                      startActivityForResult(intentEditProduct, 9);

                      return true;
                  }else{
                      if(list.getOwner().get(i).equals(person.getEmail())){
                          intentNewProductSettings.putExtra("typeOfEntry", 1);
                      }else{
                          intentNewProductSettings.putExtra("typeOfEntry", 2);
                      }

                      intentNewProductSettings.putExtra("pos", i);
                      intentNewProductSettings.putExtra("owner", list.getOwner().get(i));
                      intentNewProductSettings.putExtra("nameProduct", list.getProductName().get(i));
                      intentNewProductSettings.putExtra("quantity", list.getQuantity().get(i));
                      intentNewProductSettings.putExtra("description", list.getDescription().get(i));
                      startActivityForResult(intentNewProductSettings, 12);

                      return true;
                  }

              }
        });
    }


    private ArrayList<String> getSearch(String s){
        //YA TIENES LA INFORMACIÓN DEL CUADRO DE BÚSQUEDA
        //ITERAMOS POR LAS CLAVES
        //GUARDAR: IMAGE, QUANTITY, NAME <+++++

        boolean door = true;
        String b = s.toLowerCase();
        ArrayList<String> nl = new ArrayList<>(list.getProductName());
        ArrayList<String> ql = new ArrayList<>(list.getQuantity());
        ArrayList<Integer> il = new ArrayList<>(list.getListw());
        allTheNames = new ArrayList<>();
        allTheQuantities = new ArrayList<>();
        allTheImages = new ArrayList<>();


        for(String key : bi.keySet()){

            if(key.contains(b)){

                for(int i = 0;i <nl.size(); i++){
                    //OBTENEMOS LA IMAGEN EN NEGATIVO

                    if(nl.get(i) != null && nl.get(i).contains(key)){
                        allTheNames.add(nl.get(i));
                        allTheQuantities.add(ql.get(i));
                        allTheImages.add(Integer.toString(il.get(i)));

                        door = false;
                    }
                }

                //OBTENEMOS LA IMAGEN EN POSITIVO
                if(door){
                    allTheImages.add(Integer.toString(bi.get(key)));
                    allTheQuantities.add("0");
                    allTheNames.add(key);
                }

                door = true;
            }
        }

        //Añadimos al final la imagen de crear un producto

        allTheImages.add("2131230997");
        allTheQuantities.add("0");
        allTheNames.add("new_product");

        //UNION DE TODOS LOS ARRAYS
        allTheQuantities.addAll(allTheNames);
        allTheImages.addAll(allTheQuantities);
        return allTheImages;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_window, menu);

        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //SearchView searchView = sch;

        // Assumes current activity is the searchable activity
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    private void fillBiMap() {
        bi.put("pear", 2131230961);
        bi.put("apple", 2131230810);
        bi.put("banana", 2131230816);
        bi.put("carrot", 2131230838);
        bi.put("garlic", 2131230901);
        bi.put("grapes", 2131230908);
        bi.put("peach", 2131230959);
        bi.put("peper", 2131230963);

        bi.put("bun", 2131230827);
        bi.put("croissant", 2131230873);
        bi.put("donut", 2131230887);
        bi.put("muffin", 2131230940);
        bi.put("pancake", 2131230957);
        bi.put("pie", 2131230966);
        bi.put("toast", 2131230982);
        bi.put("bread", 2131230824);
        bi.put("butter",2131230831);
        bi.put("cheese",  2131230844);
        bi.put("eggs", 2131230891);
        bi.put("milk", 2131230937);
        bi.put("yogurt", 2131230992);

        bi.put("bacon", 2131230814);
        bi.put("beef", 2131230820);
        bi.put("chiken", 2131230846);
        bi.put("fish", 2131230893);
        bi.put("ham", 2131230910);
        bi.put("hotdog", 2131230914);
        bi.put("lobster",2131230933);
        bi.put("oysters", 2131230955);
        bi.put("shrimp", 2131230976);

        bi.put("cake", 2131230834);
        bi.put("chips", 2131230848);
        bi.put("chocolate", 2131230850);
        bi.put("cola", 2131230852);
        bi.put("dessert", 2131230881);
        bi.put("honey", 2131230912);
        bi.put("jam", 2131230927);
        bi.put("jello", 2131230929);
        bi.put("popcorn", 2131230970);

        bi.put("burrito", 2131230829);
        bi.put("dumpling", 2131230889);
        bi.put("fries", 2131230897);
        bi.put("lasagna", 2131230931);
        bi.put("pizza", 2131230968);

        bi.put("battery", 2131230818);
        bi.put("candle", 2131230836);
        bi.put("diaper", 2131230883);
        bi.put("flower", 2131230895);
        bi.put("gift", 2131230903);
        bi.put("razor", 2131230972);
        bi.put("shampoo", 2131230974);
        bi.put("sponge", 2131230980);
        bi.put("vitamins", 2131230990);

        bi.put("birndfood", 2131230822);
        bi.put("catfood", 2131230840);
        bi.put("catlitter", 2131230842);
        bi.put("dogfood", 2131230885);
    }

    private void fillBiMapId() {
        biId.put("pear", 2131362000);
        biId.put("apple", 2131361831);
        biId.put("banana", 2131361836);
        biId.put("carrot", 2131361856);
        biId.put("garlic", 2131361920);
        biId.put("grapes", 2131361923);
        biId.put("peach", 2131361999);
        biId.put("peper", 2131362002);

        biId.put("bun", 2131361846);
        biId.put("croissant", 2131361877);
        biId.put("donut", 2131361894);
        biId.put("muffin", 2131361970);
        biId.put("pancake", 2131361991);
        biId.put("pie", 2131362003);
        biId.put("toast", 2131362078);
        biId.put("bread", 2131361845);

        biId.put("butter", 2131361848);
        biId.put("cheese", 2131361864);
        biId.put("eggs", 2131361900);
        biId.put("milk", 2131361968);
        biId.put("yogurt", 2131362099);

        biId.put("bacon", 2131361835);
        biId.put("beef", 2131361840);
        biId.put("chiken", 2131361865);
        biId.put("fish", 2131361915);
        biId.put("ham", 2131361925);
        biId.put("hotdog", 2131361930);
        biId.put("lobster",2131361959);
        biId.put("oysters", 2131361988);
        biId.put("shrimp", 2131362044);

        biId.put("cake", 2131361851);
        biId.put("chips", 2131361866);
        biId.put("chocolate", 2131361867);
        biId.put("cola", 2131361871);
        biId.put("dessert", 2131361890);
        biId.put("honey", 2131361929);
        biId.put("jam", 2131361947);
        biId.put("jello", 2131361948);
        biId.put("popcorn", 2131362006);

        biId.put("burrito", 2131361847);
        biId.put("dumpling", 2131361896);
        biId.put("fries", 2131361919);
        biId.put("lasagna", 2131361950);
        biId.put("pizza", 2131362005);

        biId.put("battery", 2131361839);
        biId.put("candle", 2131361855);
        biId.put("diaper", 2131361891);
        biId.put("flower", 2131361917);
        biId.put("gift", 2131361922);
        biId.put("razor", 2131362011);
        biId.put("shampoo", 2131362039);
        biId.put("sponge", 2131362052);
        biId.put("vitamins", 2131362094);

        biId.put("birndfood", 2131361842);
        biId.put("catfood", 2131361857);
        biId.put("catlitter", 2131361858);
        biId.put("dogfood", 2131361893);
    }

    private void fillMapSec() {
        biSec.put("pear", "f_and_v");
        biSec.put("apple", "f_and_v");
        biSec.put("banana", "f_and_v");
        biSec.put("carrot", "f_and_v");
        biSec.put("garlic", "f_and_v");
        biSec.put("grapes", "f_and_v");
        biSec.put("peach", "f_and_v");
        biSec.put("peper", "f_and_v");

        biSec.put("bun", "b_and_p");
        biSec.put("croissant", "b_and_p");
        biSec.put("donut", "b_and_p");
        biSec.put("muffin", "b_and_p");
        biSec.put("pancake", "b_and_p");
        biSec.put("pie", "b_and_p");
        biSec.put("toast", "b_and_p");
        biSec.put("bread", "b_and_p");

        biSec.put("butter", "m_and_c");
        biSec.put("cheese", "m_and_c");
        biSec.put("eggs", "m_and_c");
        biSec.put("milk", "m_and_c");
        biSec.put("yogurt", "m_and_c");

        biSec.put("bacon", "m_and_f");
        biSec.put("beef", "m_and_f");
        biSec.put("chiken", "m_and_f");
        biSec.put("fish", "m_and_f");
        biSec.put("ham", "m_and_f");
        biSec.put("hotdog", "m_and_f");
        biSec.put("lobster","m_and_f");
        biSec.put("oysters", "m_and_f");
        biSec.put("shrimp", "m_and_f");

        biSec.put("cake", "s_and_s");
        biSec.put("chips", "s_and_s");
        biSec.put("chocolate", "s_and_s");
        biSec.put("cola", "s_and_s");
        biSec.put("dessert", "s_and_s");
        biSec.put("honey", "s_and_s");
        biSec.put("jam", "s_and_s");
        biSec.put("jello", "s_and_s");
        biSec.put("popcorn", "s_and_s");

        biSec.put("burrito", "f_f");
        biSec.put("dumpling", "f_f");
        biSec.put("fries", "f_f");
        biSec.put("lasagna", "f_f");
        biSec.put("pizza", "f_f");

        biSec.put("battery", "h_and_h");
        biSec.put("candle", "h_and_h");
        biSec.put("diaper", "h_and_h");
        biSec.put("flower", "h_and_h");
        biSec.put("gift", "h_and_h");
        biSec.put("razor", "h_and_h");
        biSec.put("shampoo", "h_and_h");
        biSec.put("sponge", "h_and_h");
        biSec.put("vitamins", "h_and_h");

        biSec.put("birndfood", "p_supplies");
        biSec.put("catfood", "p_supplies");
        biSec.put("catlitter", "p_supplies");
        biSec.put("dogfood", "p_supplies");
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
                }else if(sg.equals("productName")){
                    list.setProductName((ArrayList<String>) dataSnapshot.getValue());
                }else if(sg.equals("owp")){
                    for (DataSnapshot key : dataSnapshot.getChildren()) {
                        ow.put(key.getKey(),(String)key.getValue());
                    }
                    list.setOwp(ow);

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String sg = dataSnapshot.getKey();

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
                    refreshMyList(list.getList());
                    hasChange = true;
                } else if (sg.equals("time")) {

                    list.setTime((Long) dataSnapshot.getValue());
                    //setTitleToolBar(list.getNameList());
                    //nameList = list.getNameList();

                    if (hasChange) {
                        hasChange = false;

                        list.setId(newList.getId());
                        list.setArray(newList.getList());
                        list.setArrayw(newList.getListw());
                        list.setSection(newList.getSection());

                        refreshMyList(newList.getList());
                    }

                    realTime = (Long) dataSnapshot.getValue();
                    if ((Long) dataSnapshot.getValue() > list.getTime()) {

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

                } else if (sg.equals("users")) {
                    Log.v(sg, "Users have changed");
                    list.setUsers((ArrayList<String>) dataSnapshot.getValue());

                    /**
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

                     **/
                } else if(sg.equals("description")){
                    list.setDescription((ArrayList<String>) dataSnapshot.getValue());

                }else if(sg.equals("owner")){
                    list.setOwner((ArrayList<String>) dataSnapshot.getValue());

                }else if(sg.equals("quantity")){
                    list.setQuantity((ArrayList<String>) dataSnapshot.getValue());
                    refreshMyList(list.getList());
                }else if(sg.equals("productName")){
                    list.setProductName((ArrayList<String>) dataSnapshot.getValue());

                }else if(sg.equals("owp")){
                    for (DataSnapshot key : dataSnapshot.getChildren()) {
                        ow.put(key.getKey(),(String)key.getValue());
                    }
                    list.setOwp(ow);
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
            String pn = bi.inverse().get(image);
            if(lock == 1){
                addElement(image, imagew, id, section, q, d, o, pn);
            }else{
                deleteElement(image, imagew, id, section, q, d, o, pn);

            }
        }
    };

    private BroadcastReceiver mMessageOwnProducts = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String img = intent.getStringExtra("img");
            String name = intent.getStringExtra("name");
            List<String> l = list.getProductName();

            if(img.equals("2131230995")){
                //addElement(image, imagew, id, section, q, d, o, pn)
                addElement(Integer.valueOf("2131230994"), Integer.valueOf("2131230995"), Integer.valueOf("0000000000"),
                        "own_products", "0", "n", person.getEmail() + ".com", name);

            }else{

                deleteElement(Integer.valueOf("2131230994"), Integer.valueOf("2131230995"),

                        Integer.valueOf("0000000000"), "own_products", "0", "n", person.getEmail() + ".com", name);

                /**
                for(int i = 0; i<l.size(); i++){
                    if(l.get(i).equals(name)){
                        list.delete(i);
                        updateFirebase();
                        refreshMyList(list.getList());
                        break;
                    }
                }
                 **/
            }
        }
    };

    private BroadcastReceiver mMessageRecentProducts = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String img = intent.getStringExtra("img");
            String name = intent.getStringExtra("name");
            String owner = ow.get(name);

            List l = list.getProductName();

            if(img.equals("2131230994")){

                //addElement(image, imagew, id, section, q, d, o, pn);
                addElement(Integer.valueOf("2131230994"), Integer.valueOf("2131230995"), Integer.valueOf("0000000000"),
                        "own_products", "0", "n", owner, name);

            }else{
                Integer i = bi.get(name);
                Integer iw = new Integer(i + 1);
                Integer id = biId.get(name);
                String sec = biSec.get(name);

                addElement(i, iw, id,sec, "0", "n", person.getEmail() + ".com", name);

                updateFirebase();
                refreshMyList(list.getList());

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
                String oldKey = actualList;

                for(int i = 0; i<m.size(); i++){
                    ref.child("Users").child(m.get(i)).child("myLists").child(actualList).removeValue();

                }
                ref.child("Lists").child(oldKey).removeValue();

                person.deleteFromMyLists(Long.valueOf(oldKey));
                myLists.remove(oldKey);

                /**
                Map<String, String> a = person.getMyLists();

                for (String key : a.keySet()) {
                    //list = (Lista) myLists.get(key);
                    actualList = key;
                    nameList = person.getMyLists().get(key);
                    childEventListener();
                    reference.addChildEventListener(listEvent);
                    break;
                }

                setTitleToolBar(nameList);
                **/
                //refreshMyList(list.getList());

            }else{
                List<String> u = list.getUsers();
                for(int j = 0; j<u.size(); j++){
                    if(u.get(j).equals(person.getEmail())){
                        u.remove(j);
                        break;
                    }
                }

                person.deleteFromMyLists(Long.valueOf(actualList));
                myLists.remove(actualList);

                ref.child("Lists").child(actualList).child("users").setValue(u);
                ref.child("Users").child(person.getEmail()).child("myLists").child(actualList).removeValue();
                /**
                ref.child("Lists").child(actualList).child("users").child(person.getEmail()).removeValue();
                myLists.remove(actualList);

                //String oldKey = actualList;

                //ref.child("Users").child(person.getEmail()).child("myLists").child(actualList).removeValue();

                for (String key : myLists.keySet()) {
                    list = (Lista) myLists.get(key);
                    actualList = key;
                    nameList = list.getNameList();
                    childEventListener(); //Cambiado
                    reference.addChildEventListener(listEvent);//Cambiado
                    break;
                }

                ref.child("Users").child(person.getEmail()).child("myLists").child(oldKey).removeValue();

                setTitleToolBar(nameList);
                refreshMyList(list.getList());
                **/
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

            ow.clear();
            rNames.clear();
            rNumbers.clear();

            setTitleToolBar(data.getStringExtra("listName"));
            nameList = data.getStringExtra("listName");
            actualList = data.getStringExtra("keyList");
            childEventListener();
            reference.addChildEventListener(listEvent);
            resultList = data.getStringArrayListExtra("resultList");
            intentForLists.putExtra("lock", false);

        }

        if(resultcode == 9){
            int pos = data.getIntExtra("pos", 0);
            list.getQuantity().remove(pos);
            list.getQuantity().add(pos, data.getStringExtra("quantity"));
            list.getDescription().remove(pos);
            list.getDescription().add(pos, data.getStringExtra("description"));
            updateFirebase();
            refreshMyList(list.getList());
        }

        if(resultcode == 8){
            //Añadimos nuevo objeto a la lista, Actualizamos Firebase y NUESTROS PROPIOS PRODUCTOS
            String nameProduct = data.getStringExtra("nameProduct");
            String quantity = data.getStringExtra("quantity");
            String description = data.getStringExtra("description");

            //element: 2131230994  elementw: 2131230995  section: own_products
            //element, elementw, id, section, quantity, description, owner, productName
            Integer a = Integer.valueOf(2131230994);
            Integer b = Integer.valueOf(2131230995);
            Log.v("ALGO PASA:", "ESTA PASANDO: " + b.toString());
            addElement(a,b, Integer.valueOf(0000000000), "own_products",

                    quantity, description,person.getEmail() + ".com", nameProduct);

            ow.put(nameProduct, person.getEmail() + ".com");

            list.setOwp(ow);

            updateFirebase();
            refreshMyList(list.getList());
        }

        if(resultcode == 7){
            String name = data.getStringExtra("name");
            ow.remove(name);
            list.setOwp(ow);
            updateFirebase();
            //Check if the product is in our list:
            if(list.getProductName().contains(name)){
                List<String> l = list.getProductName();
                for(int i = 0; i<l.size();i ++){
                    if(l.get(i).equals(name)){
                        list.delete(i);
                        updateFirebase();
                        refreshMyList(list.getList());
                        break;
                    }
                }
            }

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
        }else if(view == own_products){
            section = "own_products";
            List<String> listNames = list.getList("own_products").getProductName();
            ArrayList<String> allNames = new ArrayList<>();
            ArrayList<String> allImages = new ArrayList<>();

            for(int i = 0; i<listNames.size(); i++){
                if(ow.containsKey(listNames.get(i))){
                    allNames.add(listNames.get(i));
                    Log.v("EL NOMBRE ES:", "EL NOMBRE DEL PRODUCTO ES::::::::::::::::*****" + listNames.get(i));
                    allImages.add("2131230995");

                }

            }

            Log.v("TAMAñO", "TAMAño DE MIS PRODUCTOS CREADOS::::::::::::::::*****" + allNames.size());
            Log.v("TAMAñO", "TAMAño DE MIS PRODUCTOS CREADOS+++++++++++++++++++++" + listNames.size());
            for(String key : ow.keySet()){
                if(!allNames.contains(key)){
                    allNames.add(key);
                    allImages.add("2131230994");

                }

            }

            intentCreateProduct.putStringArrayListExtra("allImages", allImages);
            intentCreateProduct.putStringArrayListExtra("names", allNames);

            startActivity(intentCreateProduct);

        }else if(view == recently_used){

            //Algoritmo para obtener los productos utilizados recientemente (algoritmo de la burbuja)
            for(int x = 0; x<rNumbers.size(); x++){
                for(int j = 0; j<rNumbers.size() - x - 1; j++){
                    if(rNumbers.get(j) < rNumbers.get(j + 1)){
                        int tmp = rNumbers.get(j + 1);
                        String tmpa = rNames.get(j + 1);

                        //Borramos y añadimos
                        rNumbers.remove(j + 1);
                        rNumbers.add(j + 1, rNumbers.get(j));
                        rNames.remove(j + 1);
                        rNames.add(j + 1, rNames.get(j));

                        rNumbers.remove(j);
                        rNumbers.add(j, tmp);
                        rNames.remove(j);
                        rNames.add(j, tmpa);
                    }
                }
            }

            //Hay que poner los que no estan en la lista: programar algoritmo

            List<String> l = list.getProductName();

            ArrayList<String> allNames = new ArrayList<>();
            ArrayList<String> allImages = new ArrayList<>();

            for(int i = 0; i<rNames.size(); i++){
                if(!l.contains(rNames.get(i))){
                    allNames.add(rNames.get(i));
                    if(bi.containsKey(rNames.get(i))){
                        allImages.add((bi.get(rNames.get(i))).toString());
                    }else{
                        allImages.add("2131230994");
                    }
                }

            }

            intentRecentProduct.putStringArrayListExtra("allImages", allImages);
            intentRecentProduct.putStringArrayListExtra("names", allNames);

            startActivity(intentRecentProduct);
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
        ArrayList<String> productName = new ArrayList<>(list.getProductName());

        for(int j = 0; j<myList.size(); j++){
            theList.add(myList.get(j).toString());
        }

        theList.addAll(theList.size() , theQuantity);
        theList.addAll(theList.size() , productName);

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
        ArrayList<String> productName = new ArrayList<>(list.getProductName());

        for(int i = 0; i<myList.size(); i++){
            theList.add(myList.get(i).toString());
        }

        theList.addAll(theList.size() , theQuantity);
        theList.addAll(theList.size() , productName);

        listAdapter = new ListAdapter(MainWindow.this, theList);
        gv.setAdapter(listAdapter);
        updateSizeBasket();
        updateAllUsers();
    }


    public void updateAllUsers(){
        Map<String, String> map = person.getMyLists();
        m = new LinkedHashMap<>();

        for(String key: map.keySet()){
            m.put(key, getEmailsFromAllTheLists(key));

        }
    }

    private void deleteElement(Integer image, Integer imagew, Integer id, String section, String quantity, String description, String owner, String productName){
        list.delete(image, imagew, id, section, quantity, description, owner, productName);
        list.setTime(System.nanoTime());

        decreaseSizeBasket();

        //myList is an ArrayList of INTEGER, so we must parse it to ArrayList of String
        ArrayList<Integer> myList = list.getList();
        ArrayList<String> theList = new ArrayList<>();
        ArrayList<String> theQuantity = new ArrayList<>(list.getQuantity());
        ArrayList<String> pName = new ArrayList<>(list.getProductName());

        for(int i = 0; i<myList.size(); i++){
            theList.add(myList.get(i).toString());
        }

        theList.addAll(theList.size() , theQuantity);
        theList.addAll(theList.size() , pName);

        listAdapter = new ListAdapter(MainWindow.this, theList);
        gv.setAdapter(listAdapter);
        updateFirebase();
    }

    private void addElement(Integer element, Integer elementw, Integer id, String section, String quantity, String description, String owner, String productName){
        list.save(element, elementw, id, section, quantity, description, owner, productName);
        list.setTime(System.nanoTime());

        if(!rNames.contains(productName)){
            rNames.add(productName);
            rNumbers.add(1);
        }else{
            for(int i = 0; i<rNames.size(); i++){
                if(rNames.get(i).equals(productName)){
                    int n = rNumbers.get(i);
                    rNumbers.remove(i);
                    rNumbers.add(i, n + 1);
                    break;
                }

            }
        }

        broadAddDelete(elementw, id);

        increaseSizeBasket();

        //myList is an ArrayList of INTEGER, so we must parse it to ArrayList of String
        ArrayList<Integer> myList = list.getList();
        ArrayList<String> theList = new ArrayList<>();
        ArrayList<String> theQuantity = new ArrayList<>(list.getQuantity());
        ArrayList<String> pName = new ArrayList<>(list.getProductName());
        for(int i = 0; i<myList.size(); i++){
            theList.add(myList.get(i).toString());
        }

        theList.addAll(theList.size() , theQuantity);
        theList.addAll(theList.size() , pName);

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

        //Hacer diferenciacion
        if(element.equals(Integer.valueOf("2131230994")) || element.equals(Integer.valueOf("2131230995")) ){
            LocalBroadcastManager.getInstance(this).sendBroadcast(intentCreateProduct);

        }else {
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    private void updateSizeBasket() {
        if(list.size() % 3 == 0 && list.size()!=0){
            totalHeight--;
            totalHeight = list.size()/3;
        }else{
            totalHeight = list.size()/3;
            if(list.size() % 3 !=0){
                totalHeight++;
            }
        }

        ViewGroup.LayoutParams params = gv.getLayoutParams();
        params.height = 330 * totalHeight;
        gv.setLayoutParams(params);
        gv.requestLayout();
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

    private void refreshSizeOfSearchBox(){
        int totalHeight = allTheImages.size()/3;

        if(allTheImages.size() % 3 !=0){

            totalHeight++;

        }else if(allTheImages.size() % 3 == 0 && allTheImages.size()!=0){
            totalHeight--;
            totalHeight = allTheImages.size()/3;

        }

        ViewGroup.LayoutParams params = searchGV.getLayoutParams();

        if(totalHeight > 4){
            params.height = 330 * totalHeight;
        }else{
            params.height = 330;
        }

        searchGV.setLayoutParams(params);
        searchGV.requestLayout();

    }

    private void setTitleToolBar(String name) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);

    }


}
