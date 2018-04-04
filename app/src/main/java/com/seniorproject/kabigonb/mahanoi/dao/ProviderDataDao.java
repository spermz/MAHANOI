package com.seniorproject.kabigonb.mahanoi.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LaFezTer on 26-Mar-18.
 */

public class ProviderDataDao {
    @SerializedName("token") private String token;
    @SerializedName("Username") private String providerUserName;
    @SerializedName("err")  private String errorMessage;
    @SerializedName("status")   private String statusMessage;
    @SerializedName("name")     private String firstName;
    @SerializedName("lastname") private String lastName;
    @SerializedName("email")    private String email;
    @SerializedName("typeservice")  private int typeService;
    @SerializedName("telno")        private String telephoneNumber;
    @SerializedName("detail")       private String detail;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProviderUserName() {
        return providerUserName;
    }

    public void setProviderUserName(String providerUserName) {
        this.providerUserName = providerUserName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTypeService() {
        return typeService;
    }

    public void setTypeService(int typeService) {
        this.typeService = typeService;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
