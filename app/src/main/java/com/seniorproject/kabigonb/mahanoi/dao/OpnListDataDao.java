package com.seniorproject.kabigonb.mahanoi.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LaFezTer on 22-Mar-18.
 */

public class OpnListDataDao implements Parcelable {

    @SerializedName("_id")      private String responseId;
    @SerializedName("providername") private String providerUsername;
    @SerializedName("offerId")      private String offerId;
    @SerializedName("typeservice")  private int typeService;
    @SerializedName("status")       private String status;

    public OpnListDataDao(){

    }

    protected OpnListDataDao(Parcel in) {
        responseId = in.readString();
        providerUsername = in.readString();
        offerId = in.readString();
        typeService = in.readInt();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(responseId);
        dest.writeString(providerUsername);
        dest.writeString(offerId);
        dest.writeInt(typeService);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OpnListDataDao> CREATOR = new Creator<OpnListDataDao>() {
        @Override
        public OpnListDataDao createFromParcel(Parcel in) {
            return new OpnListDataDao(in);
        }

        @Override
        public OpnListDataDao[] newArray(int size) {
            return new OpnListDataDao[size];
        }
    };

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }



    public String getProviderUsername() {
        return providerUsername;
    }

    public void setProviderUsername(String providerUsername) {
        this.providerUsername = providerUsername;
    }

    public int getTypeService() {
        return typeService;
    }

    public void setTypeService(int typeService) {
        this.typeService = typeService;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
