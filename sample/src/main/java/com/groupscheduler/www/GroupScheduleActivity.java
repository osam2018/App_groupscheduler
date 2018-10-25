package com.groupscheduler.www;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class GroupScheduleActivity extends AppCompatActivity {

    private Toolbar toolbar;

    CompactCalendarView calendarView;

    ListView groupInviteListView;
    ArrayList<GroupInviteList> groupInviteArrayList;
    GroupInviteListAdatper groupInviteListAdatper;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    FirebaseUser user;

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
        groupInviteListAdatper.add("uid4","exampless@naver.com",false);
        groupInviteListAdatper.add("uid35","exampless@naver.com",false);
        groupInviteListAdatper.add("uid36","exampless@naver.com",false);
        groupInviteListAdatper.add("uid355","exampless@naver.com",false);
        groupInviteListAdatper.add("uid63","exampless@naver.com",false);
        groupInviteListAdatper.add("uid33","exampless@naver.com",true);
        groupInviteListAdatper.add("uid13","exampless@naver.com",false);
        groupInviteListAdatper.add("uid343","exampless@naver.com",false);
        groupInviteListAdatper.add("uid365","exampless@naver.com",false);
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

        firebaseGroupEventLoad(gid);
        toolbar.setTitle(dateFormatForMonth.format(calendarView.getFirstDayOfCurrentMonth()));
    }
    private void firebaseEventLoad(String userid){

        db = FirebaseFirestore.getInstance();
        /* firebase end */

        List<Event> events = new ArrayList<>();
        db.collection("events").whereEqualTo("uid", userid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                events.add(new Event(Color.parseColor(document.getString("color")), document.getDate("eventtime").getTime(),document.getString("spec")));
                            }
                            calendarView.addEvents(events);
                        } else {
                            Toast.makeText(getApplicationContext(),"오류가 발생했습니다.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void firebaseGroupEventLoad(String groupid){
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("group").document(groupid);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Map<String, Object> userData = (Map<String,Object>)document.get("uids");
                        Map<String, Boolean> td = (HashMap<String,Boolean>)document.get("member");
                        Set<String> userIds = td.keySet();
                        for (String userid : userIds) {
                            firebaseEventLoad(userid);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"그룹이 존재하지 않습니다..",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"오류가 발생했습니다.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_group_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        firebaseAuth.signOut();
        finishAffinity();
    }
}
