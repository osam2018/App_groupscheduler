package com.groupscheduler.www;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ScheduleListDialog {

    private Context context;
    Dialog dialog;

    Calendar date;

    SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());

    FloatingActionButton addBtn;
    ListView scheduleListView;
    ArrayList<ScheduleListDialogList> scheduleListDialogListArrayList;
    ScheduleListDialogListAdatper scheduleListDialogListAdatper;

    CompactCalendarView calendarView;

    public ScheduleListDialog(Context context, CompactCalendarView calendarView, Calendar date) {
        this.calendarView = calendarView;
        this.context = context;
        this.dialog = new Dialog(context);
        this.date = date;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void execute() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        dialog = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dialog.setContentView(R.layout.schedule_list_dlg);

        // 커스텀 다이얼로그를 노출한다.
        dialog.show();

        addBtn = dialog.findViewById(R.id.psn_schedule_list_addFloat_btn);

        scheduleListView = dialog.findViewById(R.id.psn_schedule_listView);

        scheduleListDialogListArrayList = new ArrayList<>();
        scheduleListDialogListAdatper = new ScheduleListDialogListAdatper(context, scheduleListDialogListArrayList, dialog);
        scheduleListView.setAdapter(scheduleListDialogListAdatper);


        // TODO 둥록되어 있는 이벤트 get해서 add

        String strColor;

        for (Event e : calendarView.getEvents(date.getTime())) {
            strColor = String.format("#%06X", 0xFFFFFF & e.getColor());
            Log.d("SLD", e.getColor()+"");
            scheduleListDialogListAdatper.add(e.getTimeInMillis(),(String)e.getData(),strColor);
        }

        scheduleListDialogListAdatper.notifyDataSetChanged();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleDialog dlg = new ScheduleDialog(context, calendarView, date);
                dlg.execute();
                dialog.dismiss();
            }
        });
    }

}