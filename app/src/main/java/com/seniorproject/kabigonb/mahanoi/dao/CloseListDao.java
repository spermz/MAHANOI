package com.seniorproject.kabigonb.mahanoi.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CloseListDao implements Parcelable{

    @SerializedName("token")    private String token;
    @SerializedName("Username") private String userName;
    @SerializedName("err")      private String errorMessage;
    @SerializedName("status")   private String statusMessage;
    @SerializedName("reason")   private String reason;
    @SerializedName("result")   private List<CloseListDataDao> result;

    public CloseListDao()
    {

    }


    protected CloseListDao(Parcel in) {
        token = in.readString();
        userName = in.readString();
        errorMessage = in.readString();
        statusMessage = in.readString();
        reason = in.readString();
        result = in.createTypedArrayList(CloseListDataDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(userName);
        dest.writeString(errorMessage);
        dest.writeString(statusMessage);
        dest.writeString(reason);
        dest.writeTypedList(result);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CloseListDao> CREATOR = new Creator<CloseListDao>() {
        @Override
        public CloseListDao createFromParcel(Parcel in) {
            return new CloseListDao(in);
        }

        @Override
        public CloseListDao[] newArray(int size) {
            return new CloseListDao[size];
        }
    };

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<CloseListDataDao> getResult() {
        return result;
    }

    public void setResult(List<CloseListDataDao> result) {
        this.result = result;
    }
}
