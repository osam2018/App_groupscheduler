package com.groupscheduler.www;

public class ScheduleListDialogList {
    private long timestamp;
    private String description;
    private String color_code;

    ScheduleListDialogList(long timestamp, String description, String color_code){
        this.description = description;
        this.timestamp = timestamp;
        this.color_code = color_code;
    }

    public String getDescription() {
        return description;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }
}
