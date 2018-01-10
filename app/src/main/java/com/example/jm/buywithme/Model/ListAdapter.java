package com.example.jm.buywithme.Model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.jm.buywithme.R;

import java.util.*;

/**
 * Created by jm on 18.1.1.
 */

public class ListAdapter extends ArrayAdapter<Integer> {
    private final Activity context;
    private final ArrayList<Integer> imageName;

    public ListAdapter(Activity context, ArrayList<Integer> imageName){
        super(context, R.layout.list_items, imageName);
        this.context = context;
        this.imageName = imageName;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewSingle = inflater.inflate(R.layout.list_items, null, true);

        ImageView ListViewImage = (ImageView) ListViewSingle.findViewById(R.id.imageView1);

        ListViewImage.setImageResource(imageName.get(position));
        return ListViewSingle;
    }
}
