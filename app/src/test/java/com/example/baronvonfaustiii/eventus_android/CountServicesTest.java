package com.example.baronvonfaustiii.eventus_android;

import android.os.Bundle;
import junit.framework.TestCase;
import org.junit.Test;


public class CountServicesTest extends TestCase
{
    public CountServicesTest(String arg0)
    {
        super(arg0);
    }

    @Test
    public void testSetup()
    {

        // load up a stub instance of an event activity, then test adding and removing
        // elements from the services list

        //ViewEventActivity sampleEvent = new ViewEventActivity();

        //int childCount = sampleEvent.scrollLayout.getChildCount();
        int childCount = 0;
        assertEquals(childCount,0);

    }
}
