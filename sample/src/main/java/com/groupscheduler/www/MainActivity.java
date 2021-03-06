package com.groupscheduler.www;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    protected void onResume() {
        super.onResume();
        /*firebase init*/
        groupScheduleArrayList.clear();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        /* firebase end */

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
    }

    void show()
    {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
                            Map<String, Object> groupData = new HashMap<>();
                            groupData.put("name", editText.getText().toString());
                            Map<String, Object> nestedData = new HashMap<>();
                            nestedData.put(user.getUid(), true);

                            groupData.put("member", nestedData);

                            db.collection("group").add(groupData)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            groupScheduleListAdapter.add(editText.getText().toString(), documentReference.getId());
                                            groupScheduleListAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(),"오류가 발생했습니다.",Toast.LENGTH_LONG).show();
                                            }
                                        });

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
