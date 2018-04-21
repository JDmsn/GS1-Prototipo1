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

    public ListAdapter(Activity context, ArrayList<String> theList){

        super(context, R.layout.list_items, theList.subList(0, theList.size()/2));
        this.context = context;

        //Dividir el array ->>>>>
        for (int i = 0; i<(theList.size()/2); i++){
            this.imageName.add(Integer.parseInt(theList.get(i)));
        }

        int size = (theList.size()/2);
        for (int i = size; i<theList.size(); i++){
            this.quantity.add(theList.get(i));
        }

    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewSingle = inflater.inflate(R.layout.list_items, null, true);

        if(position < imageName.size()){
            ImageView listViewImage = (ImageView) listViewSingle.findViewById(R.id.imageView1);
            TextView textView = (TextView) listViewSingle.findViewById(R.id.quantity);

            if(!quantity.get(position).equals("0")){
                textView.setText(quantity.get(position));
            }

            listViewImage.setImageResource(imageName.get(position));
            return listViewSingle;

        }

        return listViewSingle;
    }
}
