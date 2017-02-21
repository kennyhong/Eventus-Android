package com.example.baronvonfaustiii.eventus_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.w3c.dom.Text;

public class ViewEventActivity extends AppCompatActivity
{
    boolean editMode = false;
    boolean removeServiceMode = false;
    LinearLayout scrollLayout = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);
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


        ImageButton addButton = (ImageButton)findViewById((R.id.addNewServiceButton));

        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // Do something in response to button click

                // Begin new Dialog actions for adding a new event

                // For now, simply add another button to the view
                turnOffRemoveServiceMode();
               // TextView newServiceButton =
                createNewServiceTextView();

                //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                //LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);  // mainEventList.findViewById(R.id.LinearScrollLayout).ad .addView(newEventButton); // takes a new view as a parameter
                //scrollLayout.addView(newServiceButton, lp);
            }
            });

        ImageButton removeServiceButton = (ImageButton)findViewById((R.id.removeServiceButton));

        removeServiceButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // Do something in response to button click

                //LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);

                if(removeServiceMode)
                {// then turn it off
                    turnOffRemoveServiceMode();
                }
                else
                {// turn it on

                    if(scrollLayout.getChildCount() > 0)
                    {
                        removeServiceMode = true;
                        for(int i = 0 ; i < scrollLayout.getChildCount(); i++)
                        {
                            TextView temp = (TextView) scrollLayout.getChildAt(i);
                            temp.setBackgroundColor(0xCCff0066);

                        }
                    }

                }


            }
        });


    }

    public void turnOffRemoveServiceMode()
    {
        //LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);

        removeServiceMode = false;

        for(int i = 0 ; i < scrollLayout.getChildCount(); i++)
        {
            TextView temp = (TextView) scrollLayout.getChildAt(i);
            temp.setBackgroundColor(-1);

        }
    }


    public void createNewServiceTextView()
    {// later this also may take parameter values from this field or elsewhere for creating the services stuff
        // later this can be used for actually assembling the service object maybe

        //LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);

        TextView result = new TextView(this);
        result.setText("New Service Added");
        result.setBackgroundColor(-1);
        result.setTextSize(24f);
        result.setTextColor((0xff000000));
        result.setClickable(true);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        scrollLayout.addView(result, lp);

        result.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // Do something in response to button click


                // make sure that there are elements to remove

                if(removeServiceMode)
                {// then remove this service,
                    // for now just delete the item, later, add a confirm dialog etc.
                    scrollLayout.removeView(v);
                    turnOffRemoveServiceMode();
                }
                else
                {// turn it on
                    // do nothing for now, later view details of that service

                }


            }
        });

        //return result;
    }


    public void populateEventFields()
    {
        // Fill in the objects of this activity with corresponding event data
        // dynamic non stub
    }

}
