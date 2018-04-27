package com.example.jm.buywithme.Model;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jm.buywithme.R;

import java.util.*;

/**
 * Created by jm on 18.1.1.
 */

public class ListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<Integer> imageName = new ArrayList<>();
    private final ArrayList<String> quantity = new ArrayList<>();
    private final ArrayList<String> productName = new ArrayList<>();
    private final Integer a = Integer.valueOf(2131230994);
    private final Integer b = Integer.valueOf(2131230995);

    public ListAdapter(Activity context, ArrayList<String> theList){
        super(context, R.layout.list_items, theList.subList(0, theList.size()/3));
        this.context = context;

        for (int i = 0; i<(theList.size()/3); i++){
            this.imageName.add(Integer.parseInt(theList.get(i)));
        }

        int size = (theList.size()/3);
        for (int i = size; i<theList.size() - size; i++){
            this.quantity.add(theList.get(i));
        }

        size = theList.size() - (theList.size()/3);
        for (int i = size; i<theList.size(); i++){
            this.productName.add(theList.get(i));

        }

    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewSingle = inflater.inflate(R.layout.list_items, null, true);
        ImageView listViewImage = (ImageView) listViewSingle.findViewById(R.id.imageView1);
        TextView textView = (TextView) listViewSingle.findViewById(R.id.quantity);

        if((!imageName.get(position).equals(a)) && !imageName.get(position).equals(b) && position < imageName.size()){


            if(!quantity.get(position).equals("0")){
                textView.setText(quantity.get(position));
            }

            listViewImage.setImageResource(imageName.get(position));
            return listViewSingle;

        }

        if(imageName.get(position).equals(a) || imageName.get(position).equals(b)){

            TextView character = listViewSingle.findViewById(R.id.character);
            TextView text = listViewSingle.findViewById(R.id.text);
            if(quantity.size() != 0) {
                Log.v("LA cantidad es:", "CANTIDAD:" + quantity);
                if(!quantity.get(position).equals("0")) {
                    textView.setText(quantity.get(position));
                }
            }
            text.setText(productName.get(position));
            character.setText(productName.get(position).substring(0, 1));

            listViewImage.setImageResource(imageName.get(position));
            return listViewSingle;

        }

        return listViewSingle;
    }
}
