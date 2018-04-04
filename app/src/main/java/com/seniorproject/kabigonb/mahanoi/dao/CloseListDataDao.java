package com.seniorproject.kabigonb.mahanoi.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CloseListDataDao implements Parcelable{

    @SerializedName("_id")  private String id;
    @SerializedName("offerId") private String offerId;
    @SerializedName("Providername") private String providerName;
    @SerializedName("Username")     private String userName;
    @SerializedName("longitude")    private Double longitude;
    @SerializedName("latitude")     private Double latitude;
    @SerializedName("typeservice")  private int typeService;

    public CloseListDataDao()
    {

    }

    protected CloseListDataDao(Parcel in) {
        id = in.readString();
        offerId = in.readString();
        providerName = in.readString();
        userName = in.readString();
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        typeService = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(offerId);
        dest.writeString(providerName);
        dest.writeString(userName);
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        dest.writeInt(typeService);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CloseListDataDao> CREATOR = new Creator<CloseListDataDao>() {
        @Override
        public CloseListDataDao createFromParcel(Parcel in) {
            return new CloseListDataDao(in);
        }

        @Override
        public CloseListDataDao[] newArray(int size) {
            return new CloseListDataDao[size];
        }
    };

    public int getTypeService() {
        return typeService;
    }

    public void setTypeService(int typeService) {
        this.typeService = typeService;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
