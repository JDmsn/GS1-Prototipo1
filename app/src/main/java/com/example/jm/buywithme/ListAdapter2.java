package com.example.jm.buywithme;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jm on 18.1.1.
 */

public class ListAdapter2 extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> imageName;

    public ListAdapter2(Activity context, ArrayList<String> imageName){
        super(context, R.layout.new_list_button, imageName);
        this.context = context;
        this.imageName = imageName;
    }

    public View getView(final int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewSingle = inflater.inflate(R.layout.new_list_button, null, true);

        TextView ListViewImage = (TextView) ListViewSingle.findViewById(R.id.lists);

        CharSequence text = imageName.get(position);

        ListViewImage.setText(text);
        return ListViewSingle;
    }
}
