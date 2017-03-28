package comp4350.eventus.model;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import comp4350.eventus.R;
import comp4350.eventus.ui.CreateEventActivity;
import comp4350.eventus.ui.SignedInLandingPage;
import comp4350.eventus.ui.adapter.EventListAdapter;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import java.util.ArrayList;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class ServerDataTestSet1 {
    private ServerData serverData;
    private ArrayList<Event> events;
    private String URL = "http://eventus.us-west-2.elasticbeanstalk.com/api/events/";

    @Before
    public void setUp() throws Exception {
        serverData = new ServerData();
    }

    @After
    public void tearDown() throws Exception {

    }

    // add event from create event page. Then validate that it was added to the server
    @Rule
    public ActivityTestRule<CreateEventActivity> mActivityRule = new ActivityTestRule<>(
            CreateEventActivity.class);


    @Test
    public void getEvents() throws Exception {
        events = serverData.getEvents();
        Assert.assertNotNull(events);
    }

    @Test
    public void addEvent() throws Exception {
        int preValue = serverData.getEvents().size();
        String name = "Event name";
        String description = "Event description";

        // Check to see if these exist and are displayed on render
        onView(withId(R.id.eventNameEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.eventDescriptionEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.eventDay)).check(matches(isDisplayed()));
        onView(withId(R.id.eventTime)).check(matches(isDisplayed()));
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));
        onView(withId(R.id.saveButton)).check(matches(isDisplayed()));


        //mActivityRule.getActivity().setupListeners();

        // Now, populate name field
        // Type text and then press the button.
        onView(withId(R.id.eventNameEditText))
                .perform(typeText(name), closeSoftKeyboard());

        // Now, populate description field
        // Type text and then press the button.
        onView(withId(R.id.eventDescriptionEditText))
                .perform(typeText(description), closeSoftKeyboard());


        onView(withId(R.id.eventDay)).perform(replaceText("2017-03-20"));
        onView(withId(R.id.eventTime)).perform(replaceText("15:00"));

        // This should save the freshly created event all the way to the server
        onView(withId(R.id.saveButton)).perform(click());


        serverData.getAllEventsRequest();
        int post = serverData.getEvents().size();

        Assert.assertTrue(post > preValue);

    }


    // add event from create event page. Then validate that it was added to the server

    @Rule
    public ActivityTestRule<SignedInLandingPage> vActivityRule = new ActivityTestRule<>(
            SignedInLandingPage.class);

    @Test
    public void testUpdateEvent() throws JSONException {
        ServerData data = vActivityRule.getActivity().accessServerData();
        data = new ServerData();
        int pre = data.getEvents().size();

        ArrayList<Event> events = serverData.getEvents();
        Assert.assertNotNull(events);

        EventListAdapter eventListAdapter;
        eventListAdapter = new EventListAdapter(vActivityRule.getActivity(), events);

        Event temp = eventListAdapter.getEventByTitle("Event name");
        Assert.assertNotNull(temp);
        temp.setName("Fancy Event");


        JSONObject json = new JSONObject();
        json = data.getJSONEventDetails(temp);// note this doesnt include services
        String id = Integer.toString(temp.getID());

        serverData = new ServerData(URL + id, "PUT", json.toString()); // update when implemented

        serverData.getAllEventsRequest();
        int post = serverData.getEvents().size();

        Assert.assertTrue(post == pre);

    }

    // we know that a new event was created by the last test, so lets go ahead and delete it. // we also know
    // what its updated title should be.
    // thus validating two other tests
    @Test
    public void deleteEvent() {
        // get access to the server data
        ServerData data = vActivityRule.getActivity().accessServerData();
        data = new ServerData();
        int pre = data.getEvents().size();

        ArrayList<Event> events = serverData.getEvents();
        Assert.assertNotNull(events);

        EventListAdapter eventListAdapter;
        eventListAdapter = new EventListAdapter(vActivityRule.getActivity(), events);

        Event temp = eventListAdapter.getEventByTitle("Fancy Event");
        Assert.assertNotNull(temp);

        String id = Integer.toString(temp.getID());

        serverData = new ServerData(URL + id, "DELETE", "");
        // great now get the fresh list, with the item remvoved

        serverData.getAllEventsRequest();
        int post = serverData.getEvents().size();

        Assert.assertTrue(post < pre);
    }

    @Test
    public void failFindEvent() {
        ServerData data = vActivityRule.getActivity().accessServerData();
        data = new ServerData();
        int pre = data.getEvents().size();

        ArrayList<Event> events = serverData.getEvents();
        Assert.assertNotNull(events);

        EventListAdapter eventListAdapter;
        eventListAdapter = new EventListAdapter(vActivityRule.getActivity(), events);

        Event temp = eventListAdapter.getEventByTitle("ImaginaryEventThatDoesntExist");
        Assert.assertNull(temp);
        // since the event doesnt exist, it cannot be deleted.

    }


}// end tests

