package comp4350.eventus.model;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class EventTest {

    private Event event;

    @Before
    public void setUp() throws Exception {
        event = new Event(0, "name", "description", new ArrayList<Service>());
    }

    @After
    public void tearDown() throws Exception {
        event = new Event();
        event.setName("name");
        event.setDescription("description");
    }

    @Test
    public void setDescription() throws Exception {
        event.setDescription("newDescription");
        Assert.assertEquals("newDescription", event.getDescription());
    }

    @Test
    public void setName() throws Exception {
        event.setName("newName");
        Assert.assertEquals("newName", event.getName());
    }

    @Test
    public void getDescription() throws Exception {
        String desc = event.getDescription();
        Assert.assertEquals("description", desc);
    }

    @Test
    public void getName() throws Exception {
        String name = event.getName();
        Assert.assertEquals("name", name);
    }

    @Test
    public void writeToParcel(){

        // Write the data.
        Event event = new  Event("name", "description", new ArrayList<Service>());
        Parcel parcel = Parcel.obtain();
        event.writeToParcel(parcel, event.describeContents());

        // After you're done with writing, you need to reset the parcel for reading.
        parcel.setDataPosition(0);

        // Read the data.
        Event createdFromParcel = Event.CREATOR.createFromParcel(parcel);

        // Verify that the received data is correct.
        assertThat(createdFromParcel.getName(), is("name"));
        assertThat(createdFromParcel.getDescription(), is("description"));
    }
}