package com.example.baronvonfaustiii.eventus_android.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.baronvonfaustiii.eventus_android.R;
import com.example.baronvonfaustiii.eventus_android.model.Event;
import com.example.baronvonfaustiii.eventus_android.model.ServerData;
import com.example.baronvonfaustiii.eventus_android.ui.adapter.EventListAdapter;
import java.util.ArrayList;

public class SignedInLandingPage extends Activity {

    private static final int REQUEST_ADD_EVENT = 1;
    private static final int REQUEST_DELETE_EVENT = 5;
    public static final String EXTRA_EVENT = "event";

    private RecyclerView recyclerView;
    private EventListAdapter eventListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Context context;
    private ServerData serverData;
    private ArrayList<Event> events;
    private Event event;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in_landing_page);
        context = this;
        this.recyclerView = (RecyclerView) findViewById(R.id.eventList_Viewer);
        this.recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(linearLayoutManager);

        serverData = new ServerData();
        events = serverData.getEvents();

        if (savedInstanceState == null) {
            event = getIntent().getParcelableExtra(EXTRA_EVENT);
        } else {
            event = savedInstanceState.getParcelable(EXTRA_EVENT);
        }

        eventListAdapter = new EventListAdapter(this, events);
        recyclerView.setAdapter(eventListAdapter);

        setupListeners();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_EVENT, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_EVENT && data != null)
        {
            if (resultCode == RESULT_OK)
            {
                System.out.println("Adding new");
                Event event = data.getParcelableExtra(CreateEventActivity.EXTRA_EVENT);
                eventListAdapter.add(event);
            }
        }
        if(requestCode == 2 && resultCode == REQUEST_DELETE_EVENT)
        {// then it is returning from a view event , and the event needs to be deleted.
            System.out.println("Deleting Event from System");
            Event event = data.getParcelableExtra(ViewEventActivity.EXTRA_EVENT);
            eventListAdapter.remove(event);
            serverData = new ServerData();
            events = serverData.getEvents();
            eventListAdapter.refresh(events);

            setupListeners();
            // now update the server


        }
        if(requestCode == 2 && resultCode == RESULT_OK)
        {// then we just need to update the event
            System.out.println("Updating");



        }
    }

    public Event getEvent()
    {
        return event ;
    }


    public void setupListeners() {
        Button signOutButton = (Button) findViewById(R.id.SignoutButton);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                startActivity(new Intent(SignedInLandingPage.this, MainActivity.class));

            }
        });

        ImageButton addButton = (ImageButton) findViewById((R.id.addNewEventButton));

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Begin new Dialog actions for adding a new event

                // For now, simply add another button to the view
                generateNewEventDialog();

            }
        });
    }

    public AlertDialog getDialog()
    {
        return dialog;
    }

    public void generateNewEventDialog()
    {
        boolean result = true;

        builder = new AlertDialog.Builder(SignedInLandingPage.this);

        //builder.setMessage("Create an empty new event, or \n select a prefab for some suggested \n services to be a part of the new event");
        builder.setTitle("Create a New Event");
        final String[] eventPrefabList = new String[5];
       // just some samples for now

        eventPrefabList[0] = "Empty Event-Default";
        eventPrefabList[1] = "BBQ";
        eventPrefabList[2] = "After-hours warehouse party";
        eventPrefabList[3] = "Wedding";
        eventPrefabList[4] = "Cancel-None";


        builder.setItems(eventPrefabList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // create new event of this type
                        if (which <= 3)
                        {
                            System.out.println("---------------------------"+ which);
                            createNewEventButton(eventPrefabList[which], which);
                        }
                    }
                }
        );

        dialog = builder.create();
        dialog.show();

    }

    public void createNewEventButton(String eventName, int prefabID)
    {
        //use the prefabID for building a prefab event......
        // feed information into the new event page, so that it has some services already in its list,
        // that just need some confirmation and choices made etc.

                // for now, links to an empty Event page.

                Intent intent = new Intent(context, CreateEventActivity.class);
                startActivityForResult(intent, REQUEST_ADD_EVENT);

    }

}
