package com.example.baronvonfaustiii.eventus_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Bailey on 2/26/2017.
 */

public class Event implements Parcelable{

    private ArrayList<Service> services;
    private String description;
    private String name;

    public Event(){
    }

    protected Event(Parcel in){
        this.name = in.readString();
        this.description = in.readString();
        this.services = in.readArrayList(ArrayList.class.getClassLoader());
    }

    public Event(ArrayList<Service> services, String description, String name) {
        this.services = services;
        this.description = description;
        this.name = name;
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
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeList(this.services);
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
