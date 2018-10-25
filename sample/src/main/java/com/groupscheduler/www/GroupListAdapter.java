package com.groupscheduler.www;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<GroupList> data;

    GroupListAdapter(Context context, ArrayList<GroupList> data){
        this.mContext = context;
        this.data = data;
    }

    public void add(String title, String groupId){
        data.add(new GroupList(title,groupId));
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
        GroupListHolder holder;
        LayoutInflater inflater;

        if(v==null){
            inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.group_list, parent,false);

            holder = new GroupListHolder();

            holder.titleHolder = v.findViewById(R.id.group_list_title);

            v.setTag(holder);
        }else {
            holder = (GroupListHolder) v.getTag();
        }

        GroupList list = data.get(position);

        holder.titleHolder.setText(list.getTitle());



        return v;
    }

    private class GroupListHolder {
        TextView titleHolder;
    }
}