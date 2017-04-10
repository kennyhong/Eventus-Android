package comp4350.eventus.ui;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import comp4350.eventus.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class ViewReceiptActivityTest {

    @Rule
    public ActivityTestRule<ReceiptActivity> mActivityRule = new ActivityTestRule<>(
            ReceiptActivity.class);

    @Test
    public void onCreate() throws Exception {
        // Check to see if we have all the components of activity_receipt.xml
        onView(withId(R.id.back_button)).check(matches(isDisplayed()));
        onView(withId(R.id.title_textView)).check(matches(isDisplayed()));
        onView(withId(R.id.CostLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.EstimatedCost)).check(matches(isDisplayed()));
        onView(withId(R.id.services_LIST)).check(matches(isDisplayed()));
    }

    @Test
    public void setupListeners() throws Exception {
        // Test to see if there are any items in the receiptActivity
        onView(withId(R.id.services_LIST)).check(matches(isDisplayed()));

        // First, check if backButton exists, and if so, click on it to go back to the activity_view_event.xml page
        onView(withId(R.id.back_button)).check(matches(isDisplayed()));
        onView(withId(R.id.back_button)).perform(click());
    }
}