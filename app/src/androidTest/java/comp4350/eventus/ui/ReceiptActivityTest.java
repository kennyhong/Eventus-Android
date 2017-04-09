package comp4350.eventus.ui;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;
import comp4350.eventus.R;
import comp4350.eventus.model.Event;
import comp4350.eventus.model.Service;
import comp4350.eventus.model.ServiceTag;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Rule;
import org.junit.Test;

public class ReceiptActivityTest
{
    ArrayList<Service> testServiceList = new ArrayList<Service>();
    Event testerEvent = new Event(99999,"Tester","Describe This","0","0","0",testServiceList);


    @Rule
    public ActivityTestRule<ReceiptActivity> sActivityRule = new ActivityTestRule<>(
            ReceiptActivity.class);

    @Test
    public void onCreate() throws Exception
    {
        // Check to see if these exist and are displayed on render
        onView(withId(R.id.EstimatedCost)).check(matches(isDisplayed()));
        onView(withId(R.id.CostLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.title_textView)).check(matches(isDisplayed()));

    }

    @Test
    public void testSetup()
    {
        onView(withId(R.id.EstimatedCost)).check(matches(isDisplayed()));

        sActivityRule.getActivity().initScrollLayout();

        sActivityRule.getActivity().setupListeners();

        ArrayList< ServiceTag > serviceTags = new ArrayList<ServiceTag>();
        Service testService = new Service(99999,"Service1",50,"yesterday","today",serviceTags);
        testerEvent.getServices().add(testService);// add services

        sActivityRule.getActivity().setEvent(testerEvent);

    }



    @Test
    public void testCost()
    {
       // after init.. there should be one test service with a cost of 50
        onView(withId(R.id.EstimatedCost)).check(matches(isDisplayed()));
        TextView estimatedCost = (TextView) sActivityRule.getActivity().findViewById(R.id.EstimatedCost);

        String orig = estimatedCost.getText().toString();

        ArrayList< ServiceTag > serviceTags = new ArrayList<ServiceTag>();
        Service testService = new Service(-1,"Service1",50,"yesterday","today",serviceTags);
        Service testService2 = new Service(-1,"Service2",150,"yesterday","today",serviceTags);
        testerEvent.getServices().add(testService);
        testerEvent.getServices().add(testService2);
        sActivityRule.getActivity().setEvent(testerEvent);

        int expected = 200;
        int result = sActivityRule.getActivity().calculateExpectedCost();

        Assert.assertTrue(expected == result);

    }


}// end test class
