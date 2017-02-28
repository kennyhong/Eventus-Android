package com.example.baronvonfaustiii.eventus_android.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.baronvonfaustiii.eventus_android.R;
import com.example.baronvonfaustiii.eventus_android.model.Event;
import com.example.baronvonfaustiii.eventus_android.ui.adapter.EventListAdapter;

import java.util.ArrayList;

public class SignedInLandingPage extends Activity {

    private static final int REQUEST_ADD_EVENT = 1;
    public static final String EXTRA_EVENT = "event";

    private RecyclerView recyclerView;
    private EventListAdapter eventListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Context context;
    private ArrayList<Event> events;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in_landing_page);
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        events = new ArrayList<Event>();

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
        if (requestCode == REQUEST_ADD_EVENT && data != null) {
            if (resultCode == RESULT_OK) {
                Event event = data.getParcelableExtra(CreateEventActivity.EXTRA_EVENT);
                eventListAdapter.add(event);
            }
        }
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
                // Do something in response to button click
                // For now, we want to just start up a create event activity
                Intent intent = new Intent(context, CreateEventActivity.class);
                startActivityForResult(intent, REQUEST_ADD_EVENT);
            }
        });
    }
}
