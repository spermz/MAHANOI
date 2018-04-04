package com.seniorproject.kabigonb.mahanoi.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LaFezTer on 22-Mar-18.
 */

public class OpenListDao implements Parcelable {

    @SerializedName("token")        private String token;
    @SerializedName("Username")     private String userName;
    @SerializedName("status")       private String statusMessage;
    @SerializedName("err")          private String errorMessage;
    @SerializedName("result")       private List<OpnListDataDao> result;

    public OpenListDao() {

    }

    protected OpenListDao(Parcel in) {
        token = in.readString();
        userName = in.readString();
        statusMessage = in.readString();
        errorMessage = in.readString();
        result = in.createTypedArrayList(OpnListDataDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(userName);
        dest.writeString(statusMessage);
        dest.writeString(errorMessage);
        dest.writeTypedList(result);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OpenListDao> CREATOR = new Creator<OpenListDao>() {
        @Override
        public OpenListDao createFromParcel(Parcel in) {
            return new OpenListDao(in);
        }

        @Override
        public OpenListDao[] newArray(int size) {
            return new OpenListDao[size];
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

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<OpnListDataDao> getResult() {
        return result;
    }

    public void setResult(List<OpnListDataDao> result) {
        this.result = result;
    }
}
