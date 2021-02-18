package com.razy.itsmee.demo.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class data implements Serializable {
    String id,
            firstName,
            lastName,
            userName,
            birthday,
            mailAddress,
            password,
            verificationCode,
            isVerified,
            uid,
            token;

    String authType,
            dateCreated;

    boolean success;
    ArrayList<String> validationResults;
    ArrayList<picmee> picmees;


    String
            title,
            text,
            file,
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<String> getValidationResults() {
        return validationResults;
    }

    public void setValidationResults(ArrayList<String> validationResults) {
        this.validationResults = validationResults;
    }

    public ArrayList<picmee> getPicmees() {
        return picmees;
    }

    public void setPicmees(ArrayList<picmee> picmees) {
        this.picmees = picmees;
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
