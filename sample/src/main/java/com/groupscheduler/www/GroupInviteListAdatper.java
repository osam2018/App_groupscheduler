package com.groupscheduler.www;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class GroupInviteListAdatper extends BaseAdapter {
    private Context mContext;
    private ArrayList<GroupInviteList> data;

    GroupInviteListAdatper(Context context, ArrayList<GroupInviteList> data){
        this.mContext = context;
        this.data = data;
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

            v.setTag(holder);
        }else {
            holder = (GroupInviteListAdatper.GroupInviteListHolder) v.getTag();
        }

        GroupInviteList list = data.get(position);

        holder.emailHolder.setText(list.getEmail());
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

        holder.inviteStateHolder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   holder.inviteStateHolder.setEnabled(false);
                   holder.inviteStateHolder.setBackgroundColor(Color.parseColor("#aa00b248"));
                   holder.emailHolder.setBackgroundColor(Color.parseColor("#2200f044"));
                }
            }
        });


        return v;
    }

    private class GroupInviteListHolder {
        TextView emailHolder;
        ToggleButton inviteStateHolder;
        LinearLayout layoutHolder;
    }
}
