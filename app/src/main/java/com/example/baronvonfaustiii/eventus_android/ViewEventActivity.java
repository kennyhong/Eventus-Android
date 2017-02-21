package com.example.baronvonfaustiii.eventus_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewEventActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        setupListeners();
        populateEventFields();
    }

    public void setupListeners()
    {
        Button backButton = (Button)findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Do something in response to button click
                startActivity(new Intent(ViewEventActivity.this, SignedInLandingPage.class));

            }
        });


        ImageButton addButton = (ImageButton)findViewById((R.id.addNewEventButton));

        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // Do something in response to button click

                // Begin new Dialog actions for adding a new event

                // For now, simply add another button to the view

                TextView newServiceButton = createNewServiceTextView();

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);  // mainEventList.findViewById(R.id.LinearScrollLayout).ad .addView(newEventButton); // takes a new view as a parameter
                scrollLayout.addView(newServiceButton, lp);
            }
            });


    }

    public TextView createNewServiceTextView()
    {
        // later this can be used for actually assembling the service object maybe
        TextView result = new TextView(this);
        result.setText("New Service Added");
        return result;
    }

    public void populateEventFields()
    {
        // Fill in the objects of this activity with corresponding event data
        // dynamic non stub
    }

}
