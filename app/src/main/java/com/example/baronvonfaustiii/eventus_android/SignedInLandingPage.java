package com.example.baronvonfaustiii.eventus_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class SignedInLandingPage extends AppCompatActivity
{
    ScrollView mainEventList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in_landing_page);

        mainEventList = (ScrollView)findViewById(R.id.MainEventList);

        setupListeners();
        initializeList();

    }

    public void setupListeners()
    {
        Button signOutButton = (Button)findViewById(R.id.SignoutButton);

        signOutButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Do something in response to button click
                startActivity(new Intent(SignedInLandingPage.this, MainActivity.class));

            }
        });

        ImageButton addButton = (ImageButton)findViewById((R.id.addNewEventButton));

        addButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Do something in response to button click

                // Begin new Dialog actions for adding a new event

                // For now, simply add another button to the view
                 generateNewEventDialog();

                //createNewEventButton();

                //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

              // LinearLayout scrollLayout = (LinearLayout)findViewById(R.id.LinearScrollLayout);  // mainEventList.findViewById(R.id.LinearScrollLayout).ad .addView(newEventButton); // takes a new view as a parameter
               //scrollLayout.addView(newEventButton,lp);

            }
        });


    }

    public void generateNewEventDialog()
    {
        boolean result = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(SignedInLandingPage.this);

        //builder.setMessage("Create an empty new event, or \n select a prefab for some suggested \n services to be a part of the new event");
        builder.setTitle("Create a New Event");
        final String[] eventPrefabList = new String[5];
        // just some samples for now

        eventPrefabList[0] = "Empty Event-Defaulte";
        eventPrefabList[1] = "BBQ";
        eventPrefabList[2] = "After-hours warehouse party";
        eventPrefabList[3] = "Wedding";
        eventPrefabList[4] = "Cancel-None";


        builder.setItems(eventPrefabList,new DialogInterface.OnClickListener()
                         {
                             @Override
                             public void onClick(DialogInterface dialog, int which)
                             {
                                     // create new event of this type
                                     if(which <=3)
                                     {
                                         createNewEventButton(eventPrefabList[which], which);
                                     }
                             }
                         }
                    );


        // Add the buttons
           /*
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                // User clicked OK button
                //createNewEventButton();
                //result = true;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });

             */

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void createNewEventButton(String eventName, int prefabID)
    {

      //use the prefabID for building a prefab event......
        // feed information into the new event page, so that it has some services already in its list,
        // that just need some confirmation and choices made etc.

            // set basic event button defaults here
            Button result = new Button(this);
            result.setText(eventName);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout scrollLayout = (LinearLayout)findViewById(R.id.LinearScrollLayout);  // mainEventList.findViewById(R.id.LinearScrollLayout).ad .addView(newEventButton); // takes a new view as a parameter


            result.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    // Link up events to event pages

                    // for now, links to an empty Event page.

                    startActivity(new Intent(SignedInLandingPage.this, ViewEventActivity.class));

                }
            });

            scrollLayout.addView(result,lp);

    }

    public void initializeList()
    {
        // this is where data is pulled, and used to populate the main list view of this activity

        // consider sorting items based on date, IE closest to happen appears near the top

        // Each event in the list needs to link to its associated event page, with all of its service information etc

        LinearLayout scrollLayout = (LinearLayout)findViewById(R.id.LinearScrollLayout);

        for(int i = 0; i < scrollLayout.getChildCount(); i++)
        {
            Button temp = (Button)scrollLayout.getChildAt(i);

            temp.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    // Link up events to event pages

                    // for now, links to an empty Event page.

                    startActivity(new Intent(SignedInLandingPage.this, ViewEventActivity.class));

                }
            });

        }


    }


}
