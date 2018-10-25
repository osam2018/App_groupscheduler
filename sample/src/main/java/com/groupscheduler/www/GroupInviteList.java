package com.groupscheduler.www;

public class GroupInviteList {
    private String userId;
    private String email;
    private boolean isMember;

    public GroupInviteList(String userId, String email, boolean isMember) {
        this.userId = userId;
        this.email = email;
        this.isMember = isMember;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMember(boolean member) {
        isMember = member;
    }
}
