package com.example.baronvonfaustiii.eventus_android.model;

import android.support.test.runner.AndroidJUnit4;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ServerDataTestSet1
{
    private ServerData serverData;
    private ArrayList<Event> events;

    @Before
    public void setUp() throws Exception
    {
        serverData = new ServerData();
    }

    @After
    public void tearDown() throws Exception
    {

    }

    @Test
    public void getEvents() throws Exception
    {
        events = serverData.getEvents();
        Assert.assertNotNull(events);
    }




}// end tests

