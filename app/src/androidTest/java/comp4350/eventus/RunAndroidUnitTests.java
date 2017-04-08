package comp4350.eventus;

import comp4350.eventus.model.EventTest;
import comp4350.eventus.model.ServerDataTestSet1;
import comp4350.eventus.ui.BrowseServicesTest;
import comp4350.eventus.ui.CreateEventActivityTest;
import comp4350.eventus.ui.MainActivityTest;
import comp4350.eventus.ui.PrefabTest1;
import comp4350.eventus.ui.SignedInLandingPageTest;
import comp4350.eventus.ui.ViewEventActivityTest;
import comp4350.eventus.ui.ViewServiceActivity;
import comp4350.eventus.ui.ViewServiceActivityTest;
import comp4350.eventus.ui.adapter.EventListAdapterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Runs all tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventTest.class,
        MainActivityTest.class,
        ViewEventActivityTest.class,
        CreateEventActivityTest.class,
        SignedInLandingPageTest.class,
        BrowseServicesTest.class,
        ViewServiceActivityTest.class,
        ServerDataTestSet1.class,
        PrefabTest1.class,
        EventListAdapterTest.class})
public class RunAndroidUnitTests {}