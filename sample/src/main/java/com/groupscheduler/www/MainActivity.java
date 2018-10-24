package com.groupscheduler.www;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button myScheduleBtn, createGroupBtn;
    ListView groupScheduleList;
    ArrayList<GroupList> groupScheduleArrayList;
    GroupListAdapter groupScheduleListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /*firebase init*/
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        /* firebase end */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        myScheduleBtn = findViewById(R.id.my_schedule_btn);
        createGroupBtn = findViewById(R.id.create_group_btn);
        groupScheduleList = findViewById(R.id.group_schedule_list);

        groupScheduleArrayList = new ArrayList<>();

        groupScheduleListAdapter = new GroupListAdapter(this, R.layout.grouplist, groupScheduleArrayList);
        /*group list retrieve*/
        String uid;
        if (user != null) {
            uid =user.getUid();
        } else {
            //back to login page or kill app
        }
        db.collection("group").whereEqualTo("member."+user.getUid(), true)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        groupScheduleListAdapter.add(document.getString("name"), document.getId());
                                    }
                            groupScheduleListAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(),"오류가 발생했습니다.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        /*retrieve end*/
        groupScheduleList.setAdapter(groupScheduleListAdapter);

        groupScheduleList.setOnItemClickListener((parent, v, position, id) -> {
            Toast.makeText(getApplicationContext(),position + " - After Service... - " + id, Toast.LENGTH_SHORT).show();
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create Group");
        builder.setMessage("AlertDialog Content");
        builder.setView(editText);
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        groupScheduleListAdapter.add(editText.getText().toString(),"aaaaaa");
                        Toast.makeText(getApplicationContext(),editText.getText() + "예를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),editText.getText() + "아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }
}
