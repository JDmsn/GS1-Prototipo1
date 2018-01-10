package com.example.jm.buywithme;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;

public class MyLists extends AppCompatActivity {
    private Intent intent;
    private GridView gv;
    private ArrayList<String> listNames = new ArrayList<>();
    private ArrayList<String> newNames = new ArrayList<>();
    private ListAdapter2 listAdapter;
    private Button newList;
    private Dialog custom;
    private ArrayList<String> resultList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lists);

        intent = getIntent();
        listNames = intent.getStringArrayListExtra("listNames");
        gv = (GridView) findViewById(R.id.mylists);
        addToMyList(listNames);

        custom = new Dialog(MyLists.this);
        custom.setContentView(R.layout.dialog_add_list);
        custom.setTitle("New Lista");

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("lock", true);
                intent.putExtra("listName", listNames.get(i));
                setResult(1, intent);
                finish();
            }
        });

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
        final EditText text = (EditText) custom.findViewById(R.id.nameOfMyNewList);
        Button add = (Button) custom.findViewById(R.id.add);
        Button cancel = (Button) custom.findViewById(R.id.cancel);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listNames.add(text.getText().toString());
                newNames.add(text.getText().toString());
                addToMyList(listNames);
                intent.putExtra("isNew", true);
                intent.putStringArrayListExtra("newName", newNames);
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

    private void addToMyList(ArrayList<String> listNames) {
        listAdapter = new ListAdapter2(MyLists.this, listNames);

        gv.setAdapter(listAdapter);
        ViewGroup.LayoutParams params = gv.getLayoutParams();
        params.height = listNames.size() * 160;
        gv.setLayoutParams(params);
        gv.requestLayout();

    }



}
