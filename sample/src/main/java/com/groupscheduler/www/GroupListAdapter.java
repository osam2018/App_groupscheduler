package com.groupscheduler.www;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<GroupList> data;
    private int layout;

    GroupListAdapter(Context context, int layout, ArrayList<GroupList> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout=layout;
        this.data=data;
    }

    public void add(String title, String groupId){
        data.add(new GroupList(title,groupId));
    }
    public void remove(int positiion){
        data.remove(positiion);
        dataChange();
    }

    private void dataChange() {
        this.notifyDataSetChanged();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        GroupListHolder holder;

        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);

            holder = new GroupListHolder();

            holder.titleHolder = v.findViewById(R.id.group_list_title);

            v.setTag(holder);
        }else {
            holder = (GroupListHolder) v.getTag();
        }

        GroupList list = data.get(position);

        holder.titleHolder.setText(list.getTitle());



        return convertView;
    }

    private class GroupListHolder {
        TextView titleHolder;
    }
}