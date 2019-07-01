package com.example.searchablespinnerapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.searchablespinnerapp.search_utils.DialogSearchable;

/**
 * Created by Anurag on 29/6/19.
 */
public class VendorsModel implements Parcelable, DialogSearchable {
    private String vendorName;
    private String vendorAddress;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }


    @Override
    public String toString() {
        return "VendorsModel{" +
                "vendorName='" + vendorName + '\'' +
                ", vendorAddress='" + vendorAddress + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.vendorName);
        dest.writeString(this.vendorAddress);
    }

    public VendorsModel() {
    }

    protected VendorsModel(Parcel in) {
        this.vendorName = in.readString();
        this.vendorAddress = in.readString();
    }

    public static final Parcelable.Creator<VendorsModel> CREATOR = new Parcelable.Creator<VendorsModel>() {
        @Override
        public VendorsModel createFromParcel(Parcel source) {
            return new VendorsModel(source);
        }

        @Override
        public VendorsModel[] newArray(int size) {
            return new VendorsModel[size];
        }
    };


    @Override
    public String getSearchableField() {
        if (this.getVendorName()!=null){
            if (this.getVendorAddress()!=null){
                return this.getVendorName()+" "+this.getVendorAddress();
            }
            return getVendorName();
        }
        return "";
    }
}
