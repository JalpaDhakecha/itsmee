package com.razy.itsmee.demo.Models;

import java.io.Serializable;

public class picmee implements Serializable {

    String id,
            title,
            text,
            file,
            dateCreated,
            dateCreatedDisplay,
            timeOut,
            remainTime;

    user user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreatedDisplay() {
        return dateCreatedDisplay;
    }

    public void setDateCreatedDisplay(String dateCreatedDisplay) {
        this.dateCreatedDisplay = dateCreatedDisplay;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(String remainTime) {
        this.remainTime = remainTime;
    }

    public com.razy.itsmee.demo.Models.user getUser() {
        return user;
    }

    public void setUser(com.razy.itsmee.demo.Models.user user) {
        this.user = user;
    }
}
