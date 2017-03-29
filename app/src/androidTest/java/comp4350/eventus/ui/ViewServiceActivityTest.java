package comp4350.eventus.ui;

import android.support.test.rule.ActivityTestRule;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import comp4350.eventus.R;
import comp4350.eventus.model.Event;
import comp4350.eventus.model.Service;
import comp4350.eventus.model.ServiceTag;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class ViewServiceActivityTest {

    Service testService;

    @Rule
    public ActivityTestRule<ViewServiceActivity> mActivityRule = new ActivityTestRule<>(
            ViewServiceActivity.class);

    @Before
    public void setUp() throws Exception {
        testService = new Service(99999, "Tester", 0, "0", "0", new ArrayList<ServiceTag>());
    }

    @Test
    public void onCreate() throws Exception {
        mActivityRule.getActivity().service = testService;
        mActivityRule.getActivity().initializeFields();
        // Check to see if we have all the components of activity_view_event
        onView(withId(R.id.titleTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.priceTag)).check(matches(isDisplayed()));
        onView(withId(R.id.dateBookedTag)).check(matches(isDisplayed()));
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));

        // Check to see if description and title have data in them
        onView(withId(R.id.titleTextView)).check(matches(withText("Tester")));
        onView(withId(R.id.priceTag)).check(matches(withText("Price: $0")));
        onView(withId(R.id.dateBookedTag)).check(matches(withText("Date Booked: 0")));
    }

    @Test
    public void setupListeners() throws Exception {
        // First, check if backButton exists, and if so, click on it to go back to activity_signed_in_landing_page
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));
        onView(withId(R.id.backButton)).perform(click());
    }
}