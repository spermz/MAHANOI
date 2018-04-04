package com.seniorproject.kabigonb.mahanoi.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LaFezTer on 26-Mar-18.
 */

public class MatchMakingDao {

    @SerializedName("token") private String token;
    @SerializedName("Username") private String userName;
    @SerializedName("offerId") private String offerId;
    @SerializedName("err")      private String errorMessage;
    @SerializedName("status")   private String statusMessage;
    @SerializedName("latitude") private Double latitude;
    @SerializedName("longitude") private Double longtitude;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }
}
