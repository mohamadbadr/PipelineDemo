package com.dlc.piplinedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView motherRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> items = new ArrayList<>();
        for(int i = 0 ; i < 20; i++)
        {
            items.add("item " + (i + 1));
        }

        ArrayList<String> items1 = new ArrayList<>();
        for(int i = 0 ; i < 20; i++)
        {
            items1.add("bitch " + (i + 1));
        }

        ArrayList<ArrayList<String>> lists = new ArrayList<>();

        lists.add(items);
        lists.add(items1);


        motherRV = findViewById(R.id.mother_list);
        motherRV.hasFixedSize();
        motherRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        MotherListAdapter adapter = new MotherListAdapter(this,lists);
        motherRV.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(motherRV);
    }
}
