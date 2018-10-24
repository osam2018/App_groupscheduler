package com.groupscheduler.www;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;

public class ScheduleDialog {

    private Context context;
    Dialog dialog;

    RadioGroup psn_radio_color_group;
    RadioButton psn_radio_red, psn_radio_orange, psn_radio_yellow, psn_radio_green, psn_radio_blue, psn_radio_navy, psn_radio_purple;
    TimePicker psn_timePicker;
    EditText psn_title_et;
    Button psn_cancel_btn;
    Button psn_save_btn;
    LinearLayout psn_dlg_layout;
    ArrayList<RadioButton> radio_array;
    LinearLayout.LayoutParams params;

    CompactCalendarView calendarView;

    public ScheduleDialog(Context context, CompactCalendarView calendarView) {
        this.calendarView = calendarView;
        this.context = context;
        this.dialog = new Dialog(context);
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        dialog = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dialog.setContentView(R.layout.schedule_dlg);

        // 커스텀 다이얼로그를 노출한다.
        dialog.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        psn_radio_color_group = dialog.findViewById(R.id.psn_radio_color_group);
        psn_radio_red = dialog.findViewById(R.id.psn_radio_red);
        psn_radio_orange = dialog.findViewById(R.id.psn_radio_orange);
        psn_radio_yellow = dialog.findViewById(R.id.psn_radio_yellow);
        psn_radio_green = dialog.findViewById(R.id.psn_radio_green);
        psn_radio_blue = dialog.findViewById(R.id.psn_radio_blue);
        psn_radio_navy = dialog.findViewById(R.id.psn_radio_navy);
        psn_radio_purple = dialog.findViewById(R.id.psn_radio_purple);
        psn_timePicker = dialog.findViewById(R.id.psn_timePicker);
        psn_title_et = dialog.findViewById(R.id.psn_title_et);
        psn_cancel_btn = dialog.findViewById(R.id.psn_cancel_btn);
        psn_save_btn = dialog.findViewById(R.id.psn_save_btn);
        psn_dlg_layout = dialog.findViewById(R.id.psn_dlg_layout);
        radio_array = new ArrayList<>();
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        params.weight=0.0f;
        radio_array.add(psn_radio_red);
        radio_array.add(psn_radio_orange);
        radio_array.add(psn_radio_yellow);
        radio_array.add(psn_radio_green);
        radio_array.add(psn_radio_blue);
        radio_array.add(psn_radio_navy);
        radio_array.add(psn_radio_purple);

        psn_save_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(psn_title_et.getText().toString().replace(" ","").equals("")){

                }else {

                    // TODO 여기가 젤 중요해
                    //calendarView.addEvent(new Event(radio_array.));
                }
                dialog.dismiss();
            }
        });
        psn_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        psn_radio_color_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (RadioButton radio : radio_array) {
                    radio.setLayoutParams(params);
                    radio.setText("");
                }
                params.weight = 1.0f;

                switch (checkedId){
                    case R.id.psn_radio_red:
                        psn_radio_red.setText(R.string.color_selected);
                        psn_radio_red.setLayoutParams(params);
                        psn_dlg_layout.setBackgroundColor(Color.parseColor("#aaff0000"));
                        break;
                    case R.id.psn_radio_orange:
                        psn_radio_orange.setText(R.string.color_selected);
                        psn_radio_orange.setLayoutParams(params);
                        psn_dlg_layout.setBackgroundColor(Color.parseColor("#aaff8000"));
                        break;
                    case R.id.psn_radio_yellow:
                        psn_radio_yellow.setText(R.string.color_selected);
                        psn_radio_yellow.setLayoutParams(params);
                        psn_dlg_layout.setBackgroundColor(Color.parseColor("#aaffff00"));
                        break;
                    case R.id.psn_radio_green:
                        psn_radio_green.setText(R.string.color_selected);
                        psn_radio_green.setLayoutParams(params);
                        psn_dlg_layout.setBackgroundColor(Color.parseColor("#aa00ff00"));
                        break;
                    case R.id.psn_radio_blue:
                        psn_radio_blue.setText(R.string.color_selected);
                        psn_radio_blue.setLayoutParams(params);
                        psn_dlg_layout.setBackgroundColor(Color.parseColor("#aa0000ff"));
                        break;
                    case R.id.psn_radio_navy:
                        psn_radio_navy.setText(R.string.color_selected);
                        psn_radio_navy.setLayoutParams(params);
                        psn_dlg_layout.setBackgroundColor(Color.parseColor("#aa000080"));
                        break;
                    case R.id.psn_radio_purple:
                        psn_radio_purple.setText(R.string.color_selected);
                        psn_radio_purple.setLayoutParams(params);
                        psn_dlg_layout.setBackgroundColor(Color.parseColor("#aa800080"));
                        break;
                    default:
                        break;
                }
            }
        });
    }
}