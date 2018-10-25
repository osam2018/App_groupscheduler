package com.groupscheduler.www;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ScheduleListDialogListAdatper extends BaseAdapter {
    FirebaseFirestore db;
    FirebaseUser user;

    private Context mContext;
    private ArrayList<ScheduleListDialogList> data;

    ScheduleListDialogListAdatper(Context context, ArrayList<ScheduleListDialogList> data){
        this.mContext = context;
        this.data = data;
    }

    public void add(long timestamp, String description, String color_code){
        data.add(new ScheduleListDialogList(timestamp,description,color_code));
    }

    public void remove(int positiion){
        data.remove(positiion);
        dataChange();
    }

    private void dataChange() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ScheduleListDialogListAdatper.ScheduleListDialogListHolder holder;
        LayoutInflater inflater;

        if(v==null){
            inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.schedule_list_dlg_listview, parent,false);

            holder = new ScheduleListDialogListHolder();

            holder.delBtnHolder = v.findViewById(R.id.schedule_listView_del_btn);
            holder.descriptionHolder = v.findViewById(R.id.schedule_listView_description_tv);
            holder.timestampHolder = v.findViewById(R.id.schedule_listView_timestamp_tv);
            holder.scheduleListHolder = v.findViewById(R.id.schedule_listView_layout);

            v.setTag(holder);
        }else {
            holder = (ScheduleListDialogListHolder) v.getTag();
        }

        ScheduleListDialogList list = data.get(position);

        Log.d("SLDA", list.getColor_code()+"");
        holder.descriptionHolder.setText(list.getDescription());
        SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
        holder.timestampHolder.setText(dateFormatForDisplaying.format(list.getTimestamp()));
        Log.d("SLDA", Color.parseColor(list.getColor_code())+"");

        holder.scheduleListHolder.setBackgroundColor(Color.parseColor(list.getColor_code()));

        holder.delBtnHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 여기에 리스트 삭제 구문~~
                createfEventToFirebase(list.getColor_code(), list.getTimestamp(), list.getDescription());
                remove(position);
                notifyDataSetChanged();
            }
        });



        return v;
    }

    private class ScheduleListDialogListHolder {
        TextView descriptionHolder;
        TextView timestampHolder;
        Button delBtnHolder;
        LinearLayout scheduleListHolder;
    }

    private void createfEventToFirebase(String colorcode, long timestamp, String spec){

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("events").whereEqualTo("spec",spec).whereEqualTo("uid",user.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("events").document(document.getId());
                            }
                        } else {
                            //Toast.makeText(getApplicationContext(),"오류가 발생했습니다.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void removeEventById(String id){
        db.collection("events").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //fail
            }
        });
    }
}
