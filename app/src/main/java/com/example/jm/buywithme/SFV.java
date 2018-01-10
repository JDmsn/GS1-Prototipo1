package com.example.jm.buywithme;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.jm.buywithme.Model.Lista;

import java.util.ArrayList;

/**
 * Created by jm on 17.12.25.
 */

public class SFV extends Activity{
    private Intent in, intent;
    private ImageButton im;
    private Lista list = new Lista();
    private ArrayAdapter<String> ad;
    private GridView gv;
    private ArrayList<Integer> elements = new ArrayList<>();
    private ArrayList<Integer> data1 = new ArrayList<>();
    private ArrayList<Integer> data2 = new ArrayList<>();
    private ArrayList<Integer> data3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gv = (GridView) findViewById(R.id.basket);

        in = getIntent();
        String sectn = in.getStringExtra("section");
        //
        if("f_and_v".equals(sectn)){
            setContentView(R.layout.fruitvegetable);
            buttons(sectn);

        }else if("b_and_p".equals(sectn)){
            setContentView(R.layout.breadpastries);
            buttons(sectn);

        }else if("m_and_c".equals(sectn)){
            setContentView(R.layout.milkcheese);
            buttons(sectn);

        }else if("m_and_f".equals(sectn)){
            setContentView(R.layout.meatfish);
            buttons(sectn);

        }if("s_and_s".equals(sectn)){
            setContentView(R.layout.snacksweet);
            buttons(sectn);

        }else if("h_and_h".equals(sectn)){
            setContentView(R.layout.householdhealth);
            buttons(sectn);

        }else if("f_f".equals(sectn)){
            setContentView(R.layout.frozenfood);
            buttons(sectn);

        }else if("p_supplies".equals(sectn)){
            setContentView(R.layout.petsupply);
            buttons(sectn);
        }
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

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("event1"));

        if(in.getBooleanExtra("boolean", false)){

            data2 = in.getIntegerArrayListExtra("data2");
            data3 = in.getIntegerArrayListExtra("data3");
            in.putExtra("boolean", false);
            if(!data3.isEmpty()){
                refreshButtons(data2, data3);
            }
        }

    }

    private void setParameters(ImageButton img, Integer in, Integer inw, Integer id){

        if(img.getTag().equals("1")){
            img.setImageResource(inw);
            img.setTag("0");
            tocall(in, inw, id, true);
        }else{
            img.setImageResource(in);
            img.setTag("1");
            tocall(in, inw, id, false);
        }

    }

    private void buttons(String section){
        if(section.equals("f_and_v")) {
            final ImageButton img1 = (ImageButton) findViewById(R.id.pear);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                //ImageButton img, Integer in,  String number;

                public void onClick(View view) {
                    setParameters(img1, R.drawable.pear, R.drawable.pearw, R.id.pear);

                }
            });

            final ImageButton img2 = (ImageButton) findViewById(R.id.apple);
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img2, R.drawable.apple, R.drawable.applew, R.id.apple);

                }
            });

            final ImageButton img3 = (ImageButton) findViewById(R.id.banana);
            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img3, R.drawable.banana, R.drawable.bananaw, R.id.banana);

                }
            });

            final ImageButton img4 = (ImageButton) findViewById(R.id.carrot);
            img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img4, R.drawable.carrot, R.drawable.carrotw, R.id.carrot);

                }
            });

            final ImageButton img5 = (ImageButton) findViewById(R.id.garlic);
            img5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img5, R.drawable.garlic, R.drawable.garlicw, R.id.garlic);

                }
            });

            final ImageButton img6 = (ImageButton) findViewById(R.id.grapes);
            img6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img6, R.drawable.grapes, R.drawable.grapesw, R.id.grapes);

                }
            });

            final ImageButton img7 = (ImageButton) findViewById(R.id.peach);
            img7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img7, R.drawable.peach, R.drawable.peachw, R.id.peach);

                }
            });

            final ImageButton img8 = (ImageButton) findViewById(R.id.peper);
            img8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img8, R.drawable.peper, R.drawable.peperw, R.id.peper);

                }
            });
        }else if(section.equals("b_and_p")){
            final ImageButton img1 = (ImageButton) findViewById(R.id.bun);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                //ImageButton img, Integer in,  String number;

                public void onClick(View view) {
                    setParameters(img1, R.drawable.bun, R.drawable.bunw, R.id.bun);

                }
            });

            final ImageButton img2 = (ImageButton) findViewById(R.id.croissant);
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img2, R.drawable.croissant, R.drawable.croissantw, R.id.croissant);

                }
            });

            final ImageButton img3 = (ImageButton) findViewById(R.id.donut);
            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img3, R.drawable.donut, R.drawable.donutw, R.id.donut);

                }
            });

            final ImageButton img4 = (ImageButton) findViewById(R.id.muffin);
            img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img4, R.drawable.muffin, R.drawable.muffinw, R.id.muffin);

                }
            });

            final ImageButton img5 = (ImageButton) findViewById(R.id.pancake);
            img5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img5, R.drawable.pancake, R.drawable.pancakew, R.id.pancake);

                }
            });

            final ImageButton img6 = (ImageButton) findViewById(R.id.pie);
            img6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img6, R.drawable.pie, R.drawable.piew, R.id.pie);

                }
            });

            final ImageButton img7 = (ImageButton) findViewById(R.id.toast);
            img7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img7, R.drawable.toast, R.drawable.toastw, R.id.toast);

                }
            });

            final ImageButton img8 = (ImageButton) findViewById(R.id.bread);
            img8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img8, R.drawable.bread, R.drawable.breadw, R.id.bread);

                }
            });
        }else if("m_and_f".equals(section)){
            final ImageButton img1 = (ImageButton) findViewById(R.id.bacon);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                //ImageButton img, Integer in,  String number;

                public void onClick(View view) {
                    setParameters(img1, R.drawable.bacon, R.drawable.baconw, R.id.bacon);

                }
            });

            final ImageButton img2 = (ImageButton) findViewById(R.id.beef);
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img2, R.drawable.beef, R.drawable.beefw, R.id.beef);

                }
            });

            final ImageButton img3 = (ImageButton) findViewById(R.id.chiken);
            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img3, R.drawable.chiken, R.drawable.chikenw, R.id.chiken);

                }
            });

            final ImageButton img4 = (ImageButton) findViewById(R.id.fish);
            img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img4, R.drawable.fish, R.drawable.fishw, R.id.fish);

                }
            });

            final ImageButton img5 = (ImageButton) findViewById(R.id.ham);
            img5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img5, R.drawable.ham, R.drawable.hamw, R.id.ham);

                }
            });

            final ImageButton img6 = (ImageButton) findViewById(R.id.hotdog);
            img6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img6, R.drawable.hotdog, R.drawable.hotdogw, R.id.hotdog);

                }
            });

            final ImageButton img7 = (ImageButton) findViewById(R.id.lobster);
            img7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img7, R.drawable.lobster, R.drawable.lobsterw, R.id.lobster);

                }
            });

            final ImageButton img8 = (ImageButton) findViewById(R.id.oyster);
            img8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img8, R.drawable.oyster, R.drawable.oysterw, R.id.oyster);

                }
            });

            final ImageButton img9 = (ImageButton) findViewById(R.id.shrimp);
            img9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img9, R.drawable.shrimp, R.drawable.shrimpw, R.id.shrimp);

                }
            });
        }else if("m_and_c".equals(section)){
            final ImageButton img1 = (ImageButton) findViewById(R.id.butter);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                //ImageButton img, Integer in,  String number;

                public void onClick(View view) {
                    setParameters(img1, R.drawable.butter, R.drawable.butterw, R.id.butter);

                }
            });

            final ImageButton img2 = (ImageButton) findViewById(R.id.cheese);
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img2, R.drawable.cheese, R.drawable.cheesew, R.id.cheese);

                }
            });

            final ImageButton img3 = (ImageButton) findViewById(R.id.eggs);
            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img3, R.drawable.eggs, R.drawable.eggsw, R.id.eggs);

                }
            });

            final ImageButton img4 = (ImageButton) findViewById(R.id.milk);
            img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img4, R.drawable.milk, R.drawable.milkw, R.id.milk);

                }
            });

            final ImageButton img5 = (ImageButton) findViewById(R.id.yogurt);
            img5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img5, R.drawable.yogurt, R.drawable.yogurtw, R.id.yogurt);

                }
            });

        }else if("s_and_s".equals(section)){
            final ImageButton img1 = (ImageButton) findViewById(R.id.cake);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                //ImageButton img, Integer in,  String number;

                public void onClick(View view) {
                    setParameters(img1, R.drawable.cake, R.drawable.cakew, R.id.cake);

                }
            });

            final ImageButton img2 = (ImageButton) findViewById(R.id.chips);
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img2, R.drawable.chips, R.drawable.chipsw, R.id.chips);

                }
            });

            final ImageButton img3 = (ImageButton) findViewById(R.id.chocolate);
            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img3, R.drawable.chocolate, R.drawable.chocolatew, R.id.chocolate);

                }
            });

            final ImageButton img4 = (ImageButton) findViewById(R.id.cola);
            img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img4, R.drawable.cola, R.drawable.colaw, R.id.cola);

                }
            });

            final ImageButton img5 = (ImageButton) findViewById(R.id.dessert);
            img5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img5, R.drawable.dessert, R.drawable.dessertw, R.id.dessert);

                }
            });

            final ImageButton img6 = (ImageButton) findViewById(R.id.honey);
            img6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img6, R.drawable.honey, R.drawable.honeyw, R.id.honey);

                }
            });

            final ImageButton img7 = (ImageButton) findViewById(R.id.jam);
            img7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img7, R.drawable.jam, R.drawable.jamw, R.id.jam);

                }
            });

            final ImageButton img8 = (ImageButton) findViewById(R.id.jello);
            img8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img8, R.drawable.jello, R.drawable.jellow, R.id.jello);

                }
            });

            final ImageButton img9 = (ImageButton) findViewById(R.id.popcorn);
            img9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img9, R.drawable.popcorn, R.drawable.popcornw, R.id.popcorn);

                }
            });
        }else if("h_and_h".equals(section)){
            final ImageButton img1 = (ImageButton) findViewById(R.id.battery);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                //ImageButton img, Integer in,  String number;

                public void onClick(View view) {
                    setParameters(img1, R.drawable.battery, R.drawable.batteryw, R.id.battery);

                }
            });

            final ImageButton img2 = (ImageButton) findViewById(R.id.candle);
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img2, R.drawable.candle, R.drawable.candlew, R.id.candle);

                }
            });

            final ImageButton img3 = (ImageButton) findViewById(R.id.diaper);
            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img3, R.drawable.diaper, R.drawable.diaperw, R.id.diaper);

                }
            });

            final ImageButton img4 = (ImageButton) findViewById(R.id.flower);
            img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img4, R.drawable.flower, R.drawable.flowerw, R.id.flower);

                }
            });

            final ImageButton img5 = (ImageButton) findViewById(R.id.gift);
            img5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img5, R.drawable.gift, R.drawable.giftw, R.id.gift);

                }
            });

            final ImageButton img6 = (ImageButton) findViewById(R.id.razor);
            img6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img6, R.drawable.razor, R.drawable.razorw, R.id.razor);

                }
            });

            final ImageButton img7 = (ImageButton) findViewById(R.id.shampoo);
            img7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img7, R.drawable.shampoo, R.drawable.shampoow, R.id.shampoo);

                }
            });

            final ImageButton img8 = (ImageButton) findViewById(R.id.sponge);
            img8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img8, R.drawable.sponge, R.drawable.spongew, R.id.sponge);

                }
            });

            final ImageButton img9 = (ImageButton) findViewById(R.id.vitamin);
            img9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img9, R.drawable.vitamin, R.drawable.vitaminw, R.id.vitamin);

                }
            });
        }else if("f_f".equals(section)){
            final ImageButton img1 = (ImageButton) findViewById(R.id.burrito);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                //ImageButton img, Integer in,  String number;

                public void onClick(View view) {
                    setParameters(img1, R.drawable.burrito, R.drawable.burritow, R.id.burrito);

                }
            });

            final ImageButton img2 = (ImageButton) findViewById(R.id.dumpling);
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img2, R.drawable.dumpling, R.drawable.dumplingw, R.id.dumpling);

                }
            });

            final ImageButton img3 = (ImageButton) findViewById(R.id.fries);
            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img3, R.drawable.fries, R.drawable.friesw, R.id.fries);

                }
            });

            final ImageButton img4 = (ImageButton) findViewById(R.id.lasagna);
            img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img4, R.drawable.lasagna, R.drawable.lasagnaw, R.id.lasagna);

                }
            });

            final ImageButton img5 = (ImageButton) findViewById(R.id.pizza);
            img5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img5, R.drawable.pizza, R.drawable.pizzaw, R.id.pizza);

                }
            });

        }else if("p_supplies".equals(section)){
            final ImageButton img1 = (ImageButton) findViewById(R.id.birdfood);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img1, R.drawable.birdfood, R.drawable.birdfoodw, R.id.birdfood);

                }
            });

            final ImageButton img2 = (ImageButton) findViewById(R.id.catfood);
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img2, R.drawable.catfood, R.drawable.catfoodw, R.id.catfood);

                }
            });

            final ImageButton img3 = (ImageButton) findViewById(R.id.catlliter);
            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img3, R.drawable.catlitter, R.drawable.catlitterw, R.id.catlliter);

                }
            });

            final ImageButton img4 = (ImageButton) findViewById(R.id.dogfood);
            img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setParameters(img4, R.drawable.dogfood, R.drawable.dogfoodw, R.id.dogfood);

                }
            });

        }
    }

    public void tocall(Integer image, Integer imagew, Integer id, boolean lock){
        intent = new Intent("event");

        if(lock){
            elements.clear();
            elements.add(image);
            elements.add(imagew);
            elements.add(id);
            elements.add(1);
            intent.putIntegerArrayListExtra("data", elements);
            //intent.putExtra("data", new int[]{image, imagew, id, 1});
            //intent.putExtra("data", image);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }else{
            elements.clear();
            elements.add(image);
            elements.add(imagew);
            elements.add(id);
            elements.add(0);
            intent.putIntegerArrayListExtra("data", elements);
            //intent.putExtra("data", new int[]{image, imagew, id, 0});
            //intent.putExtra("data", image);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            data1 = intent.getIntegerArrayListExtra("data1");
            ImageButton im1 = (ImageButton) findViewById(data1.get(1));
            im1.setImageResource(data1.get(0));
            im1.setTag("0");
        }
    };

    private void refreshButtons(ArrayList<Integer> imagesToRefresh, ArrayList<Integer> ids){
        for(int i=0; i<imagesToRefresh.size(); i++){
            im = (ImageButton) findViewById(ids.get(i));
            im.setImageResource(imagesToRefresh.get(i));
            im.setTag("0");
        }
        in.putExtra("boolean", false);
    }

    @Override
    protected void onDestroy(){
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
