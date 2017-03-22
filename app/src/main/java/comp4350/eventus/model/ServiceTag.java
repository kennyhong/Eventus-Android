package comp4350.eventus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiceTag implements Parcelable {

    private int id;
    private String name;
    private String createdAt;
    private String updatedAt;

    public ServiceTag() {
        id = 0;
        name = "";
        createdAt = "";
        updatedAt = "";
    }

    public ServiceTag(int id, String name, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    protected ServiceTag(Parcel in) {
        this();// Might not need this?
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ServiceTag> CREATOR = new Creator<ServiceTag>() {
        @Override
        public ServiceTag createFromParcel(Parcel source) {
            return new ServiceTag(source);
        }

        @Override
        public ServiceTag[] newArray(int size) {
            return new ServiceTag[size];
        }
    };
}
