package com.seniorproject.kabigonb.mahanoi.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LaFezTer on 05-Mar-18.
 */

public class RequestFormDao {

    @SerializedName("typeservice")  private int typeService;
    @SerializedName("type_info")      private String typeInfo;
    @SerializedName("moreDetail")     private String moreDetail;
    @SerializedName("toolsCheck")     private String toolsCheck;
    @SerializedName("problem")     private String problem;
    @SerializedName("placeType")     private String placeType;
    @SerializedName("amount")       private String amount;
    @SerializedName("Username")     private String userName;
    @SerializedName("err")       private String errorMessage;
    @SerializedName("status")    private String statusMessage;
    @SerializedName("token")     private String token;

    public int getTypeService() {
        return typeService;
    }

    public void setTypeService(int typeService) {
        this.typeService = typeService;
    }

    public String getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(String typeInfo) {
        this.typeInfo = typeInfo;
    }

    public String getMoreDetail() {
        return moreDetail;
    }

    public void setMoreDetail(String moreDetail) {
        this.moreDetail = moreDetail;
    }

    public String getToolsCheck() {
        return toolsCheck;
    }

    public void setToolsCheck(String toolsCheck) {
        this.toolsCheck = toolsCheck;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
