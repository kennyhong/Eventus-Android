package comp4350.eventus.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Event implements Parcelable {

    private int id;
    private String name;
    private String description;
    private String date;
    private String createdAt;
    private String updatedAt;
    private ArrayList<Service> services;

    public Event() {
        id = 0;
        name = "";
        description = "";
        date = "";
        createdAt = "";
        updatedAt = "";
        services = new ArrayList<>();
    }

    public Event(int id, String name, String description, String date, String createdAt, String updatedAt, ArrayList<Service> services) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.services = services;
    }

    protected Event(Parcel in) {
        this();
        readFromParcel(in);
    }

    public int getID() {
        return id;
    }


    private void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        in.readTypedList(services, Service.CREATOR);
    }

    public Event(String name, String description, ArrayList<Service> services) {
        this.name = name;
        this.description = description;
        this.services = services;
    }

    public Event(int id, String name, String description, ArrayList<Service> services) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.services = services;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeTypedList(services);
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
