package com.example.baronvonfaustiii.eventus_android;

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

                Button newEventButton = createNewEventButton();

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

               LinearLayout scrollLayout = (LinearLayout)findViewById(R.id.LinearScrollLayout);  // mainEventList.findViewById(R.id.LinearScrollLayout).ad .addView(newEventButton); // takes a new view as a parameter
               scrollLayout.addView(newEventButton,lp);

            }
        });


    }

    public Button createNewEventButton()
    {
        // set basic event button defaults here
        Button result = new Button(this);
        result.setText("Fresh Event");

        return result;
    }

    public void initializeList()
    {
        // this is where data is pulled, and used to populate the main list view of this activity

        // consider sorting items based on date, IE closest to happen appears near the top
    }


}
