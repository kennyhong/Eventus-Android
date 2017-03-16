package com.example.baronvonfaustiii.eventus_android.ui.adapter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.view.Display;

import com.example.baronvonfaustiii.eventus_android.model.Event;
import com.example.baronvonfaustiii.eventus_android.model.Service;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Bailey on 2/27/2017.
 */
public class EventListAdapterTest {

    private ArrayList<Event> events;
    private ArrayList<Service> services;

    @Before
    public void setUp() throws Exception {
        events = new ArrayList<>();
        services = new ArrayList<>();
        Event event1 = new Event("Party", "Parteh Tiem!", services);
        Event event2 = new Event("BBQ", "Just a chill time with friends!", services);
        Event event3 = new Event("Wedding", "Come celebrate the good times, with Mr. and Mrs. NewlyWed", services);
        events.add(event1);
        events.add(event2);
        events.add(event3);
    }

    @Test
    public void refresh() throws Exception {
        ArrayList<Event> newEvents = new ArrayList<>();
        Event event1 = new Event("Better Party", "Come watch us have more fun than that other guy!", services);
        Event event2 = new Event("Better BBQ", "We're more chill!!", services);
        Event event3 = new Event("Better Wedding", "We're fancier than Mr. and Mrs. NewlyWed, come join the NewlyWedz!", services);
        newEvents.add(event1);
        newEvents.add(event2);
        newEvents.add(event3);
        events.clear();
        events.addAll(newEvents);
        assertEquals(events.get(0).equals(event1), true);
        assertEquals(events.get(1).equals(event2), true);
        assertEquals(events.get(2).equals(event3), true);
    }

    @Test
    public void add() throws Exception {
        Event newEvent = new Event("Fundraiser", "Join us in raising money for the people!", services);
        events.add(newEvent);
        assertEquals(events.get(3).equals(newEvent), true);
        assertEquals(events.size(), 4);
    }

    @Test
    public void remove() throws Exception {
        Event event1 = new Event("Party", "Parteh Tiem!", services);
        events.remove(0);
        assertEquals(events.size() == 2, true);
        assertEquals(!events.get(0).equals(event1), true);
    }

}