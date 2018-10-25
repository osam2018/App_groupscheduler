package com.groupscheduler.www;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button myScheduleBtn, createGroupBtn;
    ListView groupScheduleList;
    ArrayList<GroupList> groupScheduleArrayList;
    GroupListAdapter groupScheduleListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        myScheduleBtn = findViewById(R.id.my_schedule_btn);
        createGroupBtn = findViewById(R.id.create_group_btn);
        groupScheduleList = findViewById(R.id.group_schedule_list);

        groupScheduleArrayList = new ArrayList<>();

        groupScheduleListAdapter = new GroupListAdapter(this, groupScheduleArrayList);
        groupScheduleList.setAdapter(groupScheduleListAdapter);

        groupScheduleList.setOnItemClickListener((parent, v, position, id) -> {

            Intent i = new Intent(MainActivity.this,GroupScheduleActivity.class);
            i.putExtra("gid",groupScheduleArrayList.get(position).getGroupId());
            startActivity(i);
            // TODO 노짬
        });

        myScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PersonalScheduleActivity.class));
            }
        });
        createGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

    }
    void show()
    {
        final EditText editText = new EditText(this);
        editText.setSingleLine(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create Group");
        builder.setMessage("AlertDialog Content");
        builder.setView(editText);
        builder.setPositiveButton("Create",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.getText().toString().replace(" ","").equals("")) {
                            Toast.makeText(MainActivity.this,"Please Insert Group Title",Toast.LENGTH_SHORT).show();
                        }else {
                            groupScheduleListAdapter.add(editText.getText().toString(), "aaaaaa");
                            groupScheduleListAdapter.notifyDataSetChanged();
                        }
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}
