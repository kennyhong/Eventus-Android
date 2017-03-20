package com.example.baronvonfaustiii.eventus_android.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.baronvonfaustiii.eventus_android.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private String toBeTyped = "test-user";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void onCreate() throws Exception {
        // Check to see if we have all the components of activity_main
        onView(withId(R.id.EventusLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.UsernameField)).check(matches(isDisplayed()));
        onView(withId(R.id.OK_Button)).check(matches(isDisplayed()));
    }

    @Test
    public void setupListeners() throws Exception {
        // Attempt to go to the next Activity with an empty userName
        onView(withId(R.id.OK_Button)).perform(click());

        // Type text and then press the button.
        onView(withId(R.id.UsernameField))
                .perform(typeText(toBeTyped), closeSoftKeyboard());

        // Check that the text was changed.
        onView(withId(R.id.UsernameField))
                .check(matches(withText(toBeTyped)));

        // Try to move to the next Activity
        onView(withId(R.id.OK_Button)).perform(click());
    }

}