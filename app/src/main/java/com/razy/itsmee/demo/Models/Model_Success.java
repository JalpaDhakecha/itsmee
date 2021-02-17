package com.razy.itsmee.demo.Models;

import com.razy.itsmee.demo.Models.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Model_Success implements Serializable {

    Boolean success;
    String  errorMessage, Status, Message/*, Data*/;
    data data;
    ArrayList<String> validationResults;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
/*
    public String getData() {
        return Data;
    }*/

    public com.razy.itsmee.demo.Models.data getData() {
        return data;
    }

    public void setData(com.razy.itsmee.demo.Models.data data) {
        this.data = data;
    }
/*
    public void setData(String data) {
        Data = data;
    }*/

    public ArrayList<String> getValidationResults() {
        return validationResults;
    }

    public void setValidationResults(ArrayList<String> validationResults) {
        this.validationResults = validationResults;
    }
}
