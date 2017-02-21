package com.example.baronvonfaustiii.eventus_android;

import junit.framework.Test;
import junit.framework.TestSuite;

public class RunUnitTests extends TestSuite
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("All tests");
        testObjects();
        testBusiness();
        //testPersistence(); Not fully functional
        return suite;
    }

    @org.junit.Test
    private static void testObjects()
    {
        //suite.addTestSuite(GameTest.class);
        System.out.println("Starting Object tests");
        suite.addTestSuite(ExampleUnitTest.class);
        suite.addTestSuite(CountServicesTest.class);
        System.out.println("Finished Object tests");

    }

    private static void testBusiness()
    {
        //suite.addTestSuite(InteractionsTest.class);

    }

    private static void testPersistence()
    {
       // suite.addTestSuite(DBTest.class);
    }

}