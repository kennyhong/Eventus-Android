package comp4350.eventus.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Service implements Parcelable {

    private int id;
    private String name;
    private int cost;
    private String description; //remove this later
    private String createdAt;
    private String updatedAt;
    private ArrayList<ServiceTag> serviceTags;

    public Service() {
        id = 0;
        name = "";
        cost = 0;
        description = ""; //remove this later
        createdAt = "";
        updatedAt = "";
        serviceTags = new ArrayList<ServiceTag>();
    }

    public Service(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Service(int id, String name, int cost, String createdAt, String updatedAt, ArrayList<ServiceTag> serviceTags) { //remove part this later
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = "Empty"; //remove this later
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.serviceTags = serviceTags;
    }

    protected Service(Parcel in) {
        this();// Might not need this?
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.cost = in.readInt();
        this.description = in.readString(); //remove this later
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        in.readTypedList(serviceTags, ServiceTag.CREATOR);
    }

    public void setDescription(String description) {
        this.description = description;
    } //remove this later

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    } //remove this later

    public String getName() {
        return name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(cost);
        dest.writeString(description); //remove this later
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeTypedList(serviceTags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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
