package comp4350.eventus;

import comp4350.eventus.model.EventTest;
import comp4350.eventus.model.ServerDataTestSet1;
import comp4350.eventus.ui.BrowseServicesTest;
import comp4350.eventus.ui.CreateEventActivityTest;
import comp4350.eventus.ui.MainActivityTest;
import comp4350.eventus.ui.SignedInLandingPageTest;
import comp4350.eventus.ui.ViewEventActivityTest;
import comp4350.eventus.ui.adapter.EventListAdapterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventTest.class,
        ServerDataTestSet1.class,
        MainActivityTest.class,
        ViewEventActivityTest.class,
        CreateEventActivityTest.class,
        SignedInLandingPageTest.class,
        BrowseServicesTest.class,
        EventListAdapterTest.class})
public class RunAndroidUnitTests {}