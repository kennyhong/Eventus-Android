package com.example.baronvonfaustiii.eventus_android.model;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import com.example.baronvonfaustiii.eventus_android.R;
import com.example.baronvonfaustiii.eventus_android.ui.CreateEventActivity;
import com.example.baronvonfaustiii.eventus_android.ui.SignedInLandingPage;
import com.example.baronvonfaustiii.eventus_android.ui.ViewEventActivity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ServerDataTestSet1
{
    private ServerData serverData;
    private ArrayList<Event> events;

    @Before
    public void setUp() throws Exception
    {
        serverData = new ServerData();
    }

    @After
    public void tearDown() throws Exception
    {

    }

    // add event from create event page. Then validate that it was added to the server
    @Rule
    public ActivityTestRule<CreateEventActivity> mActivityRule = new ActivityTestRule<>(
            CreateEventActivity.class);

    @Test
    public void addEvent() throws Exception
    {
        int preValue = serverData.getEvents().size();
        String name = "Event name";
        String description = "Event description";

        // Check to see if these exist and are displayed on render
        onView(withId(R.id.eventNameEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.eventDescriptionEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));
        onView(withId(R.id.saveButton)).check(matches(isDisplayed()));


        //mActivityRule.getActivity().setupListeners();

        // Now, populate name field
        // Type text and then press the button.
    //    onView(withId(R.id.eventNameEditText))
     //           .perform(typeTextIntoFocusedView(name), closeSoftKeyboard());

        // Now, populate description field
        // Type text and then press the button.
      //  onView(withId(R.id.eventDescriptionEditText))
      //          .perform(typeTextIntoFocusedView(description), closeSoftKeyboard());

        // This should save the freshly created event all the way to the server
    //    onView(withId(R.id.saveButton)).perform(click());

    //   int post = serverData.getEvents().size();

     //  Assert.assertTrue(post > preValue);

       // int id = mActivityRule.getActivity().getEvent().getID();


    }

    @Test
    public void getEvents() throws Exception
    {
        events = serverData.getEvents();
        Assert.assertNotNull(events);
    }


}// end tests

