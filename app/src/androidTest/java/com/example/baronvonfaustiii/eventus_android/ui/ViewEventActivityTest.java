package com.example.baronvonfaustiii.eventus_android.ui;

import android.support.test.rule.ActivityTestRule;

import com.example.baronvonfaustiii.eventus_android.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by Bailey on 2/27/2017.
 */
public class ViewEventActivityTest {

    private final String EVENT_TITLE = "TitleHere";
    private final String EVENT_DESCRIPTION = "Place your short description here: ";

    @Rule
    public ActivityTestRule<ViewEventActivity> mActivityRule = new ActivityTestRule<>(
            ViewEventActivity.class);

    @Test
    public void onCreate() throws Exception {
        // Check to see if we have all the components of activity_view_event
        onView(withId(R.id.titleTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.descriptionTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));

        // Check to see if description and title have data in them
        onView(withId(R.id.titleTextView)).check(matches(withText(EVENT_TITLE)));
        onView(withId(R.id.descriptionTextView)).check(matches(withText(EVENT_DESCRIPTION)));
    }

    @Test
    public void setupListeners() throws Exception {
        // First, check if backButton exists, and if so, click on it to go back to activity_signed_in_landing_page
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));
        onView(withId(R.id.backButton)).perform(click());
    }

}