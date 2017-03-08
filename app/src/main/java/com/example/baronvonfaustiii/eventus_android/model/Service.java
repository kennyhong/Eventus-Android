package com.example.baronvonfaustiii.eventus_android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bailey on 2/26/2017.
 */

public class Service implements Parcelable{

    private String description;
    private String name;

    public Service(){
        description = "";
        name = "";
    }

    public Service(String name, String description){
        this.name = name;
        this.description = description;
    }

    protected Service(Parcel in) {
        this();// Might not need this?
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
    }

    public void setDescription(String description){
        this.description = description;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }
    public String getName(){
        return name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {return 0;}

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel source) {
            return new Service(source);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };
}
