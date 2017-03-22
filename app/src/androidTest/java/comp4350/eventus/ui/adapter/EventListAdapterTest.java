package comp4350.eventus.ui.adapter;

import comp4350.eventus.model.Event;
import comp4350.eventus.model.Service;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

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
        Event event2 = new Event("BBQ", "Just a chill time with friends!", services);
        events.remove(0);
        assertEquals(events.size() == 2, true);
        assertEquals(!events.get(0).equals(event1), true);
    }

}