package com.example.baronvonfaustiii.eventus_android;

import com.example.baronvonfaustiii.eventus_android.model.EventTest;
import com.example.baronvonfaustiii.eventus_android.ui.CreateEventActivityTest;
import com.example.baronvonfaustiii.eventus_android.ui.MainActivityTest;
import com.example.baronvonfaustiii.eventus_android.ui.SignedInLandingPageTest;
import com.example.baronvonfaustiii.eventus_android.ui.ViewEventActivityTest;
import com.example.baronvonfaustiii.eventus_android.ui.adapter.EventListAdapterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Bailey on 2/27/2017.
 */

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventTest.class,
        MainActivityTest.class,
        ViewEventActivityTest.class,
        CreateEventActivityTest.class,
        SignedInLandingPageTest.class,
        EventListAdapterTest.class})
public class RunAndroidUnitTests {}