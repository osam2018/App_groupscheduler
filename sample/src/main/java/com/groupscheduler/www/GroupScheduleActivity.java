package com.groupscheduler.www;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GroupScheduleActivity extends AppCompatActivity {

    private Toolbar toolbar;

    CompactCalendarView calendarView;

    ListView groupInviteListView;
    ArrayList<GroupInviteList> groupInviteArrayList;
    GroupInviteListAdatper groupInviteListAdatper;

    FirebaseAuth firebaseAuth;

    SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

    String gid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_schedule);

        firebaseAuth = FirebaseAuth.getInstance();
        // TODO 노짬
        Intent i = getIntent();
        gid = i.getStringExtra("gid");

        toolbar = findViewById(R.id.group_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        groupInviteListView = findViewById(R.id.group_invite_list);
        groupInviteArrayList = new ArrayList<>();
        groupInviteListAdatper = new GroupInviteListAdatper(this, groupInviteArrayList);
        groupInviteListView.setAdapter(groupInviteListAdatper);


        // TODO groupInviteListView.setOnItemClickListener 만들지 마셈. 내부에서 이벤트 받는게 있어서 어짜피 처리안됨.
        groupInviteListAdatper.add("uid","example@naver.com",false);
        groupInviteListAdatper.add("uid2","examples@naver.com",true);
        groupInviteListAdatper.add("uid3","exampless@naver.com",false);
        groupInviteListAdatper.notifyDataSetChanged();
        // TODO This is Samples. Please Delete it.

        calendarView = findViewById(R.id.group_calendar_view);
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        firebaseEventLoad();
        toolbar.setTitle(dateFormatForMonth.format(calendarView.getFirstDayOfCurrentMonth()));
    }

    private void firebaseEventLoad() {
        // TODO Group list db connect please NoJJAng.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        firebaseAuth.signOut();
        moveTaskToBack(true);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
