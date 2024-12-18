package com.haitao.entity;

public class UserInfo {
    private int id;
    private String userName;
    private String blogUrl;
    private String blogRemark;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public String getBlogRemark() {
        return blogRemark;
    }

    public void setBlogRemark(String blogRemark) {
        this.blogRemark = blogRemark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
