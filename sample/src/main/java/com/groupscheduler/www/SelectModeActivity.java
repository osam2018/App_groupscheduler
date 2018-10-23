package com.groupscheduler.www;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public class SelectModeActivity extends Activity implements View.OnClickListener {

    Button my_schedule_btn, group_schedule_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        my_schedule_btn = findViewById(R.id.my_schedule_btn);
        group_schedule_btn = findViewById(R.id.group_schedule_btn);

        my_schedule_btn.setOnClickListener(this);
        group_schedule_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.my_schedule_btn:
                startActivity(new Intent(SelectModeActivity.this,PersonalScheduleActivity.class));
                break;
            case R.id.group_schedule_btn:
                break;
                default:
                    break;
        }
    }
}
