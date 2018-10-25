package com.groupscheduler.www;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ScheduleListDialogListAdatper extends BaseAdapter {
    private Context mContext;
    private ArrayList<ScheduleListDialogList> data;

    ScheduleListDialogListAdatper(Context context, ArrayList<ScheduleListDialogList> data){
        this.mContext = context;
        this.data = data;
    }

    public void add(String timestamp, String description, String color_code){
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
        holder.timestampHolder.setText(list.getTimestamp());
        Log.d("SLDA", Color.parseColor(list.getColor_code())+"");

        holder.scheduleListHolder.setBackgroundColor(Color.parseColor(list.getColor_code()));

        holder.delBtnHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 여기에 리스트 삭제 구문~~
                // notifyDataSetChanged();
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
}
