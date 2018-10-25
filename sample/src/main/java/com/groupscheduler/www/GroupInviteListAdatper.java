package com.groupscheduler.www;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupInviteListAdatper extends BaseAdapter {
    FirebaseFirestore db;
    FirebaseUser user;
    private Context mContext;
    private ArrayList<GroupInviteList> data;
    private String gid;
    CompactCalendarView calendarView;
    boolean flag = true;
    GroupInviteListAdatper(Context context, ArrayList<GroupInviteList> data, String gid, CompactCalendarView calendarView){
        this.mContext = context;
        this.data = data;
        this.gid = gid;
        this.calendarView = calendarView;
    }

    public void add(String userId, String email, boolean isMember){
        data.add(new GroupInviteList(userId,email,isMember));
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
        GroupInviteListAdatper.GroupInviteListHolder holder;
        LayoutInflater inflater;

        if(v==null){
            inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.group_invite_list, parent,false);

            holder = new GroupInviteListHolder();

            holder.emailHolder = v.findViewById(R.id.invite_email_tv);
            holder.inviteStateHolder = v.findViewById(R.id.invite_toggle_btn);
            holder.layoutHolder = v.findViewById(R.id.invite_linearLayout);

            holder.inviteStateHolder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonView.setEnabled(false);
                        buttonView.setBackgroundColor(Color.parseColor("#aa00b248"));
                        int position = (Integer) buttonView.getTag();
                        data.get(position).setMember(true);
                        holder.emailHolder.setBackgroundColor(Color.parseColor("#2200f044"));

                        // TODO invite
                        db = FirebaseFirestore.getInstance();
                        Map<String, Object> member = new HashMap<>();
                        Map<String, Object> nestedData = new HashMap<>();
                        nestedData.put(data.get(position).getUserId(), true);
                        member.put("member", nestedData);
                        db.collection("group").document(gid).set(member, SetOptions.merge());

                        //GroupScheduleActivity activity = (GroupScheduleActivity) mContext;
                        //activity.recreate();
                        //activity.firebaseGroupEventLoad(gid);

                    }
                }
            });

            v.setTag(holder);
        }else {
            holder = (GroupInviteListAdatper.GroupInviteListHolder) v.getTag();
        }

        GroupInviteList list = data.get(position);

        holder.emailHolder.setText(list.getEmail());
        holder.inviteStateHolder.setTag(position);
        if(list.isMember()) {
            holder.inviteStateHolder.setChecked(true);
            holder.inviteStateHolder.setBackgroundColor(Color.parseColor("#aa00b248"));
            holder.inviteStateHolder.setEnabled(false);
            holder.emailHolder.setBackgroundColor(Color.parseColor("#2200f044"));
        }else{
            holder.inviteStateHolder.setChecked(false);
            holder.inviteStateHolder.setBackgroundColor(Color.parseColor("#aaf00f00"));
            holder.inviteStateHolder.setEnabled(true);
            holder.emailHolder.setBackgroundColor(Color.parseColor("#22ff0000"));
        }


        return v;
    }

    private class GroupInviteListHolder {
        TextView emailHolder;
        ToggleButton inviteStateHolder;
        LinearLayout layoutHolder;
    }
}
