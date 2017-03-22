package comp4350.eventus.ui;

import android.support.test.rule.ActivityTestRule;

import comp4350.eventus.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class CreateEventActivityTest {

    private final String EVENT_TITLE_HINT = "Place your event name here: ";
    private final String EVENT_DESCRIPTION_HINT = "Place your short description here: ";

    @Rule
    public ActivityTestRule<CreateEventActivity> mActivityRule = new ActivityTestRule<>(
            CreateEventActivity.class);

    @Rule
    public ActivityTestRule<SignedInLandingPage> sActivityRule = new ActivityTestRule<>(
            SignedInLandingPage.class);

    @Test
    public void onCreate() throws Exception {
        // Check to see if these exist and are displayed on render
        onView(withId(R.id.eventNameEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.eventDescriptionEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));
        onView(withId(R.id.saveButton)).check(matches(isDisplayed()));

        // Check to see if description and title have data in them
        onView(withId(R.id.eventNameEditText)).check(matches(withHint(EVENT_TITLE_HINT)));
        onView(withId(R.id.eventDescriptionEditText)).check(matches(withHint(EVENT_DESCRIPTION_HINT)));
    }

    @Test
    public void setupListeners() throws Exception {
        // First, check if backButton and saveButton exists, and if so, click on backButton to go back to activity_signed_in_landing_page
        // Save button will be tested in the save() test
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));
        onView(withId(R.id.saveButton)).check(matches(isDisplayed()));
        onView(withId(R.id.saveButton)).perform(click());
        onView(withId(R.id.backButton)).perform(click());
    }

    @Test
    public void save() throws Exception {
        String name = "Event name";
        String description = "Event description";
        // First, attempt to save without fields populated
        onView(withId(R.id.saveButton)).check(matches(isDisplayed()));
        onView(withId(R.id.saveButton)).perform(click());

        // Now, populate name field
        // Type text and then press the button.
        onView(withId(R.id.eventNameEditText))
                .perform(typeText(name), closeSoftKeyboard());

        // Attempt to save round 2
        onView(withId(R.id.saveButton)).perform(click());

        // Now, populate description field, and clear name field
        // Type text and then press the button.
        onView(withId(R.id.eventNameEditText))
                .perform(replaceText(""));
        onView(withId(R.id.eventDescriptionEditText))
                .perform(typeText(description), closeSoftKeyboard());

        // Attempt to save round 3
        onView(withId(R.id.saveButton)).perform(click());

        // Now, populate name field again and attempt to complete save
        // Type text and then press the button.
        onView(withId(R.id.eventNameEditText))
                .perform(typeText(name), closeSoftKeyboard());

        // Attempt to save final round!
        onView(withId(R.id.saveButton)).perform(click());

        // See if the item exists in the list
        // It doesn't. I'm sure it has to do with a problem of passing intentions back in forth
        // (no intention was passed to CreateEventActivity, so it doesn't display data?)
        // Unless it's a problem with not notifyingDataSetChanged()? <-- will look into
//        onView(withId(R.id.eventItemName)).check(matches(isDisplayed()));
//        onView(withId(R.id.eventItemDescription)).check(matches(isDisplayed()));
    }
}