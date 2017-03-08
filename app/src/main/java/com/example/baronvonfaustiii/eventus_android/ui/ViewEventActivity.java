package com.example.baronvonfaustiii.eventus_android.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baronvonfaustiii.eventus_android.R;
import com.example.baronvonfaustiii.eventus_android.model.Event;
import com.example.baronvonfaustiii.eventus_android.model.Service;

import java.util.ArrayList;

public class ViewEventActivity extends AppCompatActivity
{
    public static final String EXTRA_EVENT = "event";

    private TextView eventName;
    private TextView eventDescription;
    private ArrayList<Service> eventServices;
    private Event event;

    boolean removeServiceMode = false;
    boolean editOn = false;
    LinearLayout scrollLayout = null;
    LinearLayout listLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        eventName = (TextView)findViewById(R.id.titleTextView);
        eventDescription = (TextView)findViewById(R.id.descriptionTextView);

        if(savedInstanceState == null)
        {
            event = getIntent().getParcelableExtra(EXTRA_EVENT);
        } else {
            event = savedInstanceState.getParcelable(EXTRA_EVENT);
        }

        if(event != null) {
            eventName.setText(event.getName());
            eventDescription.setText(event.getDescription());
            eventServices = event.getServices();
        }

        scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);
        listLayout = new LinearLayout(this);
        listLayout.setOrientation(LinearLayout.HORIZONTAL);
        setupListeners();
    }

    public void setupListeners()
    {
        Button btn;
        for(Service service : event.getServices()) {
            btn = new Button(this);
            btn.setText(service.getName());
            btn.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {
                   startActivity(new Intent(ViewEventActivity.this, ViewServiceActivity.class));
               }
            });
            listLayout.addView(btn);
            int index = listLayout.indexOfChild(btn);
            btn.setTag(Integer.toString(index));
        }


        Button backButton = (Button)findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Do something in response to button click
                setResult(RESULT_CANCELED);
                finish();

            }
        });

//        Button saveButton = (Button)findViewById(R.id.saveButton);
//
//        saveButton.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                // Do something in response to button click
//
//            }
//        });

        ImageButton editButton = (ImageButton)findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                // Do something in response to button click
                if(editOn)
                {
                    forceKeyboardClose();
                    editOn = false;
                    TextView title = (TextView)findViewById(R.id.titleTextView);
                    //title.setTextIsSelectable(true);
                    title.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                            TextView title = (TextView)findViewById(R.id.titleTextView);
                            // Do something in response to button click
                            title.setCursorVisible(false);
                            title.setFocusableInTouchMode(false);
                          //  title.setInputType(InputType.);
                            title.setFocusable(false);
                           //RelativeLayout view = (RelativeLayout)findViewById(R.id.activity_view_event);
                            //view.
                            forceKeyboardClose();
                           // title.requestFocus(); //to trigger the soft input
                            //title.setText("");
                        }
                    });
                }
                else
                {
                    forceKeyboardClose();
                    editOn = true;
                    TextView title = (TextView)findViewById(R.id.titleTextView);
                    //title.setTextIsSelectable(true);
                    title.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                            TextView title = (TextView)findViewById(R.id.titleTextView);
                            // Do something in response to button click
                            title.setCursorVisible(true);
                            title.setFocusableInTouchMode(true);
                            title.setInputType(InputType.TYPE_CLASS_TEXT);
                            title.requestFocus(); //to trigger the soft input
                            title.setText("");
                        }
                    });


                }


            }
        });


        ImageButton addButton = (ImageButton)findViewById((R.id.addNewServiceButton));

        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // Do something in response to button click

                // Begin new Dialog actions for adding a new event

                // For now, simply add another button to the view
                turnOffRemoveServiceMode();
                createNewServiceTextView();
                // TextView newServiceButton =

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

    public void forceKeyboardClose()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
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
}
