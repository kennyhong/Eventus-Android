package com.example.baronvonfaustiii.eventus_android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baronvonfaustiii.eventus_android.R;
import com.example.baronvonfaustiii.eventus_android.model.Event;

/**
 * Created by Bailey on 2/26/2017.
 */


public class CreateEventActivity extends AppCompatActivity {

    public static final String EXTRA_EVENT = "event";

    boolean removeServiceMode = false;
    LinearLayout scrollLayout;
    private Event event;
    private EditText inputEventName;
    private EditText inputEventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);
        setupListeners();
    }

    public void setupListeners()
    {
        Button backButton = (Button)findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Do something in response to button click
                startActivity(new Intent(CreateEventActivity.this, SignedInLandingPage.class));

            }
        });

        Button saveButton = (Button)findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something in response to button click
                save(view);
            }
        });

        inputEventName = (EditText)findViewById(R.id.eventNameEditText);
        inputEventDescription = (EditText)findViewById(R.id.eventDescriptionEditText);

        ImageButton addServiceButton = (ImageButton)findViewById((R.id.addServiceButton));

        addServiceButton.setOnClickListener(new View.OnClickListener(){
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_EVENT, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Add more to this if we have to.
    }

    public void save(View view) {
        if (event == null) {
            event = new Event();
        }
        String eventName = inputEventName.getText().toString();
        String eventDescription = inputEventDescription.getText().toString();
        if (TextUtils.isEmpty(eventName)) {
            inputEventName.setError(getString(R.string.error_field_empty));
            inputEventName.requestFocus();
        } else if(TextUtils.isEmpty((eventDescription))) {
            inputEventDescription.setError(getString(R.string.error_field_empty));
            inputEventDescription.requestFocus();
        } else {
            inputEventName.setError(null);
            event.setName(eventName);
            event.setDescription(eventDescription);

//            // Write this in for later
//            saveServices();
            Intent intent = getIntent();
            intent.putExtra(EXTRA_EVENT, event);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    // Possibly use this instead of going back to the SignedInLandingPage Activity?
    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

}
