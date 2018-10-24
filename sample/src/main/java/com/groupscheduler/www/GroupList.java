package com.groupscheduler.www;

public class GroupList {
    private String title;
    private String groupId;

    GroupList(String title,String groupId){
        this.title = title;
        this.groupId = groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
