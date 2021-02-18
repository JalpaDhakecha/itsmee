package com.razy.itsmee.demo.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class ListResponse implements Serializable {
    boolean success;
    ArrayList<data> data;
    ArrayList<String> validationResults;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<com.razy.itsmee.demo.Models.data> getData() {
        return data;
    }

    public void setData(ArrayList<com.razy.itsmee.demo.Models.data> data) {
        this.data = data;
    }

    public ArrayList<String> getValidationResults() {
        return validationResults;
    }

    public void setValidationResults(ArrayList<String> validationResults) {
        this.validationResults = validationResults;
    }
}
