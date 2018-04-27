package com.example.jm.buywithme;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.jm.buywithme.Model.ListAdapter;

import java.util.ArrayList;

/**
 * Created by jm on 18.4.26.
 */

public class OwnProduct extends Activity{

    private Intent in, intent;
    private ImageButton im;
    private ArrayAdapter<String> ad;
    private GridView gv;
    private ArrayList<String> allImages = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.own_products);
        gv = (GridView) findViewById(R.id.basket3);

        in = getIntent();



        allImages = in.getStringArrayListExtra("allImages");
        names = in.getStringArrayListExtra("names");

        int a = allImages.size();
        for(int i = 0; i<a; i++){
            allImages.add("0");

        }

        allImages.addAll(names);

        adapter = new ListAdapter(OwnProduct.this, allImages);
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String a = "2131230994";
                final String b = "2131230995";
                in = new Intent("eventOwnProducts");

                if(a.equals(allImages.get(i))){
                    allImages.remove(i);
                    allImages.add(i, "2131230995");

                    in.putExtra("name", names.get(i));
                    in.putExtra("img", "2131230995");
                    LocalBroadcastManager.getInstance(OwnProduct.this).sendBroadcast(in);
                }else{
                    allImages.remove(i);
                    allImages.add(i, "2131230994");

                    in.putExtra("name", names.get(i));
                    in.putExtra("img", "2131230994");
                    LocalBroadcastManager.getInstance(OwnProduct.this).sendBroadcast(in);
                    LocalBroadcastManager.getInstance(OwnProduct.this).sendBroadcast(in);
                }

                refresList();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width,(int) (height*.38));
        Window w = getWindow();
        WindowManager.LayoutParams wlp = w.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &=~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        w.setAttributes(wlp);

    }

    private void refresList() {
        adapter = new ListAdapter(OwnProduct.this, allImages);
        gv.setAdapter(adapter);

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


        }
    };

    @Override
    protected void onDestroy(){
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
