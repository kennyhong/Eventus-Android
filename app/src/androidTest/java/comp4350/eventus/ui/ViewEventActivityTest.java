package comp4350.eventus.ui;

import android.support.test.rule.ActivityTestRule;

import comp4350.eventus.R;
import comp4350.eventus.model.Event;
import comp4350.eventus.model.Service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class ViewEventActivityTest {

    private final String EVENT_TITLE = "TitleHere";
    private final String EVENT_DESCRIPTION = "Place your short description here: ";
    private Event event;

    @Rule
    public ActivityTestRule<ViewEventActivity> mActivityRule = new ActivityTestRule<>(
            ViewEventActivity.class);

    @Before
    public void setUp() throws Exception {
        event = new Event(0, "name", "description", new ArrayList<Service>());
    }

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

    @Test
    public void deleteCodeTest() {
        onView(withId(R.id.editButton)).perform(click());
        onView(withId(R.id.deleteButton)).perform(click());

        Assert.assertEquals(5, mActivityRule.getActivity().getResultCode());
    }

    @Test
    public void updateCodeTest() {
        onView(withId(R.id.backButton)).perform(click());
        Assert.assertEquals(0, mActivityRule.getActivity().getResultCode());
    }


}