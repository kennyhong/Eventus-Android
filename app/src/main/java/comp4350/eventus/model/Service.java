package comp4350.eventus.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Service implements Parcelable {

    private int id;
    private String name;
    private int cost;
    private String createdAt;
    private String updatedAt;
    private ArrayList<ServiceTag> serviceTags;

    public Service() {
        id = 0;
        name = "";
        cost = 0;
        createdAt = "";
        updatedAt = "";
        serviceTags = new ArrayList<ServiceTag>();
    }

    public Service(int id, String name, int cost, String createdAt, String updatedAt, ArrayList<ServiceTag> serviceTags) { //remove part this later
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.serviceTags = serviceTags;
    }

    protected Service(Parcel in) {
        this();// Might not need this?
        readFromParcel(in);
    }

   public ArrayList<ServiceTag> getServiceTags()
   {
       return serviceTags;
   }

    private void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.cost = in.readInt();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        in.readTypedList(serviceTags, ServiceTag.CREATOR);
    }

    public int getCost()
    {
        return cost;
    }
    public String getCreatedAt()
    {
        return createdAt;
    }

    public String getUpdatedAt()
    {return  updatedAt; }

    public int getID(){return id; }

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
        dest.writeInt(cost);
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
