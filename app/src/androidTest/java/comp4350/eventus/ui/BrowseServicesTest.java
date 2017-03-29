package comp4350.eventus.ui;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.support.test.rule.ActivityTestRule;
import android.widget.LinearLayout;
import android.widget.TextView;
import comp4350.eventus.R;
import comp4350.eventus.model.Event;
import comp4350.eventus.model.Service;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Text;

public class BrowseServicesTest
{
    ArrayList<Service> testServiceList = new ArrayList<Service>();
    Event testerEvent = new Event(99999,"Tester","Describe This","0","0","0",testServiceList);



    @Rule
    public ActivityTestRule<BrowseServicesActivity> sActivityRule = new ActivityTestRule<>(
            BrowseServicesActivity.class);

    @Test
    public void testSetup()
    {
        sActivityRule.getActivity().event = testerEvent;
        sActivityRule.getActivity().eventServices = testServiceList;

        sActivityRule.getActivity().initScrollLayout();

        sActivityRule.getActivity().setupListeners();

        // now basic initialization of the activity should be complete
    }

    @Test
    public void onCreate() throws Exception
    {
        // Check to see if these exist and are displayed on render
        onView(withId(R.id.byNameButton)).check(matches(isDisplayed()));
        onView(withId(R.id.byIDButton)).check(matches(isDisplayed()));
        onView(withId(R.id.byServiceTagButton)).check(matches(isDisplayed()));

    }

    @Test
    public void testIDSearch()
    {
        onView(withId(R.id.byIDButton)).check(matches(isDisplayed()));
        onView(withId(R.id.byIDButton)).perform(click());

        // initially, the list will have all available services
        int pre = sActivityRule.getActivity().scrollLayout.getChildCount();

        onView(withId(R.id.searchBar)).perform(replaceText("4"));

        onView(withId(R.id.SearchButton)).perform(click());

        int post = sActivityRule.getActivity().scrollLayout.getChildCount();

        Assert.assertTrue(pre > post);

        TextView element = (TextView) sActivityRule.getActivity().scrollLayout.getChildAt(0);

        Assert.assertTrue(element.getText().toString().equals("A good time"));

    }

    @Test
    public void testReset()
    {
        onView(withId(R.id.byNameButton)).check(matches(isDisplayed()));

        onView(withId(R.id.byIDButton)).check(matches(isDisplayed()));
        onView(withId(R.id.byIDButton)).perform(click());

        // initially, the list will have all available services
        int pre = sActivityRule.getActivity().scrollLayout.getChildCount();

        onView(withId(R.id.searchBar)).perform(replaceText("4"));

        onView(withId(R.id.SearchButton)).perform(click());

        int post = sActivityRule.getActivity().scrollLayout.getChildCount();

        Assert.assertTrue(pre > post);


        // the list will have depleted available services
        pre = sActivityRule.getActivity().scrollLayout.getChildCount();

        onView(withId(R.id.searchBar)).perform(replaceText(""));

        onView(withId(R.id.SearchButton)).perform(click());

        post = sActivityRule.getActivity().scrollLayout.getChildCount();

        Assert.assertTrue(pre < post);

    }

    @Test
    public void testNameFilter()
    {
        onView(withId(R.id.byNameButton)).check(matches(isDisplayed()));
        onView(withId(R.id.byNameButton)).perform(click());

        // initially, the list will have all available services
        int pre = sActivityRule.getActivity().scrollLayout.getChildCount();

        onView(withId(R.id.searchBar)).perform(replaceText("Magic"));

        onView(withId(R.id.SearchButton)).perform(click());

        int post = sActivityRule.getActivity().scrollLayout.getChildCount();

        Assert.assertTrue(pre > post);

        TextView tester = (TextView) sActivityRule.getActivity().scrollLayout.getChildAt(0);
        Assert.assertTrue(tester.getText().toString().contains("Magic"));
    }

    @Test
    public void testServiceTagFilter()
    {
        onView(withId(R.id.byServiceTagButton)).check(matches(isDisplayed()));
        onView(withId(R.id.byServiceTagButton)).perform(click());

        // initially, the list will have all available services
        int pre = sActivityRule.getActivity().scrollLayout.getChildCount();

        onView(withId(R.id.searchBar)).perform(replaceText("Cameras"));

        onView(withId(R.id.SearchButton)).perform(click());

        int post = sActivityRule.getActivity().scrollLayout.getChildCount();

        Assert.assertTrue(pre > post);

        TextView tester = (TextView) sActivityRule.getActivity().scrollLayout.getChildAt(0);
        Assert.assertTrue(tester.getText().toString().equals("Movie Magic"));
    }

}
