package com.example.baronvonfaustiii.eventus_android.ui;

import android.app.AlertDialog;
import android.support.test.rule.ActivityTestRule;

import com.example.baronvonfaustiii.eventus_android.R;

import android.view.View;
import android.widget.Button;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;


public class SignedInLandingPageTest {

    @Rule
    public ActivityTestRule<SignedInLandingPage> mActivityRule = new ActivityTestRule<>(
            SignedInLandingPage.class);

    @Test
    public void onCreate() throws Exception {
        // Check to see if these exist and are displayed on render
        onView(withId(R.id.YourEventsLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.SignoutButton)).check(matches(isDisplayed()));
        onView(withId(R.id.addNewEventButton)).check(matches(isDisplayed()));
        onView(withId(R.id.eventList_Viewer)).check(matches(isDisplayed()));
    }

    @Test
    public void setupListeners() throws Exception {
        // First, check if logoutButton and addNewEventButton exists, and if so, click on logoutButton to go back to activity_main
        // addNewEventButton will be tested in a different test
        onView(withId(R.id.SignoutButton)).check(matches(isDisplayed()));
        onView(withId(R.id.addNewEventButton)).check(matches(isDisplayed()));
        onView(withId(R.id.SignoutButton)).perform(click());
    }

    @Test
    public void addNewEvent() throws Exception {
        String name = "Event name";
        String description = "Event description";
        // First, check to see if addNewEventButton exists, if so, click it
        onView(withId(R.id.addNewEventButton)).check(matches(isDisplayed()));
        onView(withId(R.id.addNewEventButton)).perform(click());


        //onView(withId(builder)).perform(click());
        if (mActivityRule.getActivity().getDialog().isShowing()) {
            AlertDialog dialog = mActivityRule.getActivity().getDialog();

            if (!mActivityRule.getActivity().getDialog().isShowing())

            {// then the dialog has closed, and the new event should be launched.

                if (dialog != null) {
                    // check to see if the createEventActivity fields pop up, and if so, populate and save.
                    // Now, populate name field
                    onView(withId(R.id.eventNameEditText))
                            .perform(typeText(name), closeSoftKeyboard());

                    // Now, populate description field
                    onView(withId(R.id.eventDescriptionEditText))
                            .perform(typeText(description), closeSoftKeyboard());

                    // Attempt to save
                    onView(withId(R.id.saveButton)).perform(click());

                    // Check to see if an item exists
                    onView(withId(R.id.eventItemName)).check(matches(isDisplayed()));
                    onView(withId(R.id.eventItemDescription)).check(matches(isDisplayed()));

                    // Check to see what the item contains
                    onView(withId(R.id.eventItemName)).perform(click());
                    onView(withId(R.id.titleTextView)).check(matches(withText(name)));
                    onView(withId(R.id.descriptionTextView)).check(matches(withText(description)));
                }

            }

        }


    }
}