package comp4350.eventus.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp4350.eventus.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateEventActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createEventActivityTest2() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.UsernameField),
                        withParent(allOf(withId(R.id.activity_main),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.UsernameField),
                        withParent(allOf(withId(R.id.activity_main),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.OK_Button),
                        withParent(allOf(withId(R.id.activity_main),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.YourEventsLabel), withText("Your Events"),
                        childAtPosition(
                                allOf(withId(R.id.activity_signed_in_landing_page),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Your Events")));

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.addNewEventButton),
                        withParent(allOf(withId(R.id.activity_signed_in_landing_page),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        imageButton2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text1), withText("Empty Event-Default"),
                        childAtPosition(
                                allOf(IsInstanceOf.<View>instanceOf(android.widget.ListView.class),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Empty Event-Default")));

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("BBQ"),
                        childAtPosition(
                                allOf(IsInstanceOf.<View>instanceOf(android.widget.ListView.class),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("BBQ")));

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("After-hours warehouse party"),
                        childAtPosition(
                                allOf(IsInstanceOf.<View>instanceOf(android.widget.ListView.class),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                2),
                        isDisplayed()));
        textView4.check(matches(withText("After-hours warehouse party")));

        ViewInteraction textView5 = onView(
                allOf(withId(android.R.id.text1), withText("Wedding"),
                        childAtPosition(
                                allOf(IsInstanceOf.<View>instanceOf(android.widget.ListView.class),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                3),
                        isDisplayed()));
        textView5.check(matches(withText("Wedding")));

        ViewInteraction textView6 = onView(
                allOf(withId(android.R.id.text1), withText("Cancel-None"),
                        childAtPosition(
                                allOf(IsInstanceOf.<View>instanceOf(android.widget.ListView.class),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                4),
                        isDisplayed()));
        textView6.check(matches(withText("Cancel-None")));

        ViewInteraction textView8 = onView(
                allOf(withId(android.R.id.text1), withText("Empty Event-Default"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                0),
                        isDisplayed()));
        textView8.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.eventNameEditText), isDisplayed()));
        appCompatEditText3.perform(replaceText("test ev"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.eventDescriptionEditText), isDisplayed()));
        appCompatEditText4.perform(replaceText("yyyyyyyyy"), closeSoftKeyboard());

        ViewInteraction editText = onView(
                allOf(withId(R.id.eventNameEditText), withText("test ev"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText.check(matches(withText("test ev")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.eventDescriptionEditText), withText("yyyyyyyyy"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        editText2.check(matches(withText("yyyyyyyyy")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.eventNameEditText), withText("test ev"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText3.check(matches(isDisplayed()));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.eventDescriptionEditText), withText("yyyyyyyyy"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        editText4.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.eventDay), isDisplayed()));
        appCompatEditText5.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.eventTime), isDisplayed()));
        appCompatEditText6.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.addServiceButton), isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textView9 = onView(
                allOf(withText("A good time"),
                        withParent(allOf(withId(R.id.addServicesLinLayout),
                                withParent(withId(R.id.addServicesScrollView))))));
        textView9.perform(scrollTo(), click());

        ViewInteraction textView10 = onView(
                allOf(withText("A good time"),
                        childAtPosition(
                                allOf(withId(R.id.ServiceScrollLinearLayout),
                                        childAtPosition(
                                                withId(R.id.ServiceScrollView),
                                                0)),
                                0),
                        isDisplayed()));
        textView10.check(matches(isDisplayed()));

        ViewInteraction textView11 = onView(
                allOf(withText("A good time"),
                        childAtPosition(
                                allOf(withId(R.id.ServiceScrollLinearLayout),
                                        childAtPosition(
                                                withId(R.id.ServiceScrollView),
                                                0)),
                                0),
                        isDisplayed()));
        textView11.check(matches(withText("A good time")));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.removeServiceButton), isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction textView12 = onView(
                allOf(withText("A good time"),
                        withParent(allOf(withId(R.id.ServiceScrollLinearLayout),
                                withParent(withId(R.id.ServiceScrollView))))));
        textView12.perform(scrollTo(), click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.saveButton), withText("Save"), isDisplayed()));
        appCompatButton4.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
