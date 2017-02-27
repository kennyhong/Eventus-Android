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
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SignedInLandingPage extends Activity {
//    ScrollView mainEventList = null;
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
//    ArrayList<String> listItems = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
//    ArrayAdapter<String> adapter;

    private static final int REQUEST_ADD_EVENT = 1;
    public static final String EXTRA_EVENT = "event";

    private RecyclerView recyclerView;
    private EventListAdapter eventListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Context context;
    private ArrayList<Event> events;
    private Event event;
    private String output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in_landing_page);
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // This is the call we're making to our back-end api. Just working with local data for now...
        try {
            output = new JSONFunctions().execute("http://eventus.us-west-2.elasticbeanstalk.com/api/events").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // Now we do something with output to format it into a List<Events>
        // output -> events
        // Either this way, OR on postExecute (in the JSONFunctions.java file) we can try using postExecute to format
        // our data into something we can use for events
        events = new ArrayList<Event>();

        if (savedInstanceState == null) {
            event = getIntent().getParcelableExtra(EXTRA_EVENT);
        } else {
            event = savedInstanceState.getParcelable(EXTRA_EVENT);
        }

        eventListAdapter = new EventListAdapter(this, events);
//        eventListAdapter.setOnItemClickListener(new EventListAdapter.OnItemClickedListener() {
//            @Override
//            public void onItemClick(Event event) {
//                // Set up so we:
//                // 1) Get the count of the items in our EventListAdapter
//                // 2) Set up onClickListeners for each item in our EventListAdapter
//            }
//        });
        recyclerView.setAdapter(eventListAdapter);

        setupListeners();
//        initializeList();

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

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
//    public void addItems(View v) {
//        listItems.add("Clicked : " + clickCounter++);
//        adapter.notifyDataSetChanged();
//    }

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

                // Begin new Dialog actions for adding a new event

                // For now, simply add another button to the view
//                generateNewEventDialog();

                //createNewEventButton();

                // For now, we want to just start up a create event activity
                Intent intent = new Intent(context, CreateEventActivity.class);
                startActivityForResult(intent, REQUEST_ADD_EVENT);

                //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                // LinearLayout scrollLayout = (LinearLayout)findViewById(R.id.LinearScrollLayout);  // mainEventList.findViewById(R.id.LinearScrollLayout).ad .addView(newEventButton); // takes a new view as a parameter
                //scrollLayout.addView(newEventButton,lp);

            }
        });
    }

//    public void generateNewEventDialog() {
//        boolean result = true;
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(SignedInLandingPage.this);
//
//        //builder.setMessage("Create an empty new event, or \n select a prefab for some suggested \n services to be a part of the new event");
//        builder.setTitle("Create a New Event");
//        final String[] eventPrefabList = new String[5];
//        // just some samples for now
//
//        eventPrefabList[0] = "Empty Event-Default";
//        eventPrefabList[1] = "BBQ";
//        eventPrefabList[2] = "After-hours warehouse party";
//        eventPrefabList[3] = "Wedding";
//        eventPrefabList[4] = "Cancel-None";
//
//
//        builder.setItems(eventPrefabList, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // create new event of this type
//                        if (which <= 3) {
//                            createNewEventButton(eventPrefabList[which], which);
//                        }
//                    }
//                }
//        );
//
//
//        // Add the buttons
//           /*
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id)
//            {
//                // User clicked OK button
//                //createNewEventButton();
//                //result = true;
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id)
//            {
//                // User cancelled the dialog
//            }
//        });
//
//             */
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//    }

//    public void createNewEventButton(String eventName, int prefabID) {
//
//        //use the prefabID for building a prefab event......
//        // feed information into the new event page, so that it has some services already in its list,
//        // that just need some confirmation and choices made etc.
//
//        // set basic event button defaults here
//        Button result = new Button(this);
//        result.setText(eventName);
//
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
////        LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.LinearScrollLayout);  // mainEventList.findViewById(R.id.LinearScrollLayout).ad .addView(newEventButton); // takes a new view as a parameter
//
//
//        result.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Link up events to event pages
//
//                // for now, links to an empty Event page.
//
//                startActivity(new Intent(SignedInLandingPage.this, CreateEventActivity.class));
//
//            }
//        });
//
////        scrollLayout.addView(result, lp);
//
//    }

//    public void initializeList() {
//        // this is where data is pulled, and used to populate the main list view of this activity
//
//        // consider sorting items based on date, IE closest to happen appears near the top
//
//        // Each event in the list needs to link to its associated event page, with all of its service information etc
//
//        LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.LinearScrollLayout);
//
//        for (int i = 0; i < scrollLayout.getChildCount(); i++) {
//            Button temp = (Button) scrollLayout.getChildAt(i);
//
//            temp.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    // Link up events to event pages
//
//                    // for now, links to an empty Event page.
//
//                    startActivity(new Intent(SignedInLandingPage.this, ViewEventActivity.class));
//
//                }
//            });
//
//        }
//
//
//    }


}
