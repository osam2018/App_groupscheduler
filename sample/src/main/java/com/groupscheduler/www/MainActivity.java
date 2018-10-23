package com.groupscheduler.www;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

class MainActivity extends Activity {

    Button my_schedule_btn;
    ListView group_schedule_list;
    HashMap<String,String> group_list_datas;
    ArrayList<String> group_schedule_arraylist;
    ArrayAdapter<String> group_schedule_list_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        my_schedule_btn = findViewById(R.id.my_schedule_btn);
        group_schedule_list = findViewById(R.id.group_schedule_list);


        group_list_datas = new HashMap<>();
        group_schedule_arraylist = new ArrayList<>(group_list_datas.keySet());

        group_schedule_list_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,group_schedule_arraylist);

        group_schedule_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_item = (String)adapterView.getItemAtPosition(i);

                Toast.makeText(getApplicationContext(),selected_item,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
