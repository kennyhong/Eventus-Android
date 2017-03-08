package com.example.baronvonfaustiii.eventus_android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bailey on 2/26/2017.
 */

public class Event implements Parcelable{

    private String name;
    private String description;
    private ArrayList<Service> services;

    public Event(){
        name = "";
        description = "";
        services = new ArrayList<Service>();
    }

    protected Event(Parcel in){
        this();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        in.readTypedList(services, Service.CREATOR);
    }

    public Event(String name, String description, ArrayList<Service> services) {
        this.name = name;
        this.description = description;
        this.services = services;
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

    public ArrayList<Service> getServices() {
        return services;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeTypedList(this.services);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
