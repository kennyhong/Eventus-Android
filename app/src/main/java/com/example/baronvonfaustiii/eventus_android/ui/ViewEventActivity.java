package com.example.baronvonfaustiii.eventus_android.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baronvonfaustiii.eventus_android.R;
import com.example.baronvonfaustiii.eventus_android.model.Event;
import com.example.baronvonfaustiii.eventus_android.model.ServerData;
import com.example.baronvonfaustiii.eventus_android.model.Service;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewEventActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT = "event";

    private TextView eventName;
    private TextView eventDescription;
    private ArrayList<Service> eventServices;
    private Event event;

    boolean removeServiceMode = false;
    boolean editOn = false;
    LinearLayout scrollLayout = null;
    LinearLayout listLayout = null;
    private ServerData serverData;
    private int resultCode;
    private final int DELETE_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        eventName = (TextView) findViewById(R.id.titleTextView);
        eventDescription = (TextView) findViewById(R.id.descriptionTextView);
        resultCode = 0;
        if (savedInstanceState == null) {
            event = getIntent().getParcelableExtra(EXTRA_EVENT);
        } else {
            event = savedInstanceState.getParcelable(EXTRA_EVENT);
        }

        if (event != null) {
            eventName.setText(event.getName());
            eventDescription.setText(event.getDescription());
            eventServices = event.getServices();
        }

        scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);
        listLayout = new LinearLayout(this);
        listLayout.setOrientation(LinearLayout.HORIZONTAL);
        setupListeners();
    }

    public ServerData accessServerData() {
        return serverData;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setupListeners() {

        if (event != null) {
            for (Service service : event.getServices()) {
                System.out.println("Services are happening!");

                createNewServiceTextView();
            }
        }

        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                try {
                    save(v);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        // Initialize delete button visibility etc.
        // Initially it is not visible, but will be once the edit button has been pressed
        final Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setVisibility(View.GONE);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editOn) {
                    // then you need to send the code back to delete this event
                    serverData = new ServerData("DELETE", Integer.toString(event.getID()));
                    Intent intent = getIntent();
                    intent.putExtra(EXTRA_EVENT, event);
                    setResult(DELETE_CODE, intent);
                    resultCode = DELETE_CODE;
                    finish();

                }

            }
        });

        ImageButton editButton = (ImageButton) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                if (editOn) {
                    forceKeyboardClose();
                    editOn = false;

                    // Remove the delete button from view
                    deleteButton.setVisibility(View.GONE);


                    TextView title = (TextView) findViewById(R.id.titleTextView);
                    // allow for the title to be updated. @ TO DO , this needs to patching up
                    //title.setTextIsSelectable(true);
                    title.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            TextView title = (TextView) findViewById(R.id.titleTextView);
                            // Do something in response to button click
                            title.setCursorVisible(false);
                            title.setFocusableInTouchMode(false);
                            //  title.setInputType(InputType.);
                            title.setFocusable(false);
                            forceKeyboardClose();
                            // title.requestFocus(); //to trigger the soft input
                            //title.setText("");
                        }
                    });
                } else {
                    // Make deleting the event possible
                    deleteButton.setVisibility(View.VISIBLE);

                    forceKeyboardClose();
                    editOn = true;
                    TextView title = (TextView) findViewById(R.id.titleTextView);
                    //title.setTextIsSelectable(true);
                    title.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            TextView title = (TextView) findViewById(R.id.titleTextView);
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


        ImageButton addButton = (ImageButton) findViewById((R.id.addNewServiceButton));

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                // Begin new Dialog actions for adding a new event

                // For now, simply add another button to the view
                turnOffRemoveServiceMode();
                createNewServiceTextView();

            }
        });

        ImageButton removeServiceButton = (ImageButton) findViewById((R.id.removeServiceButton));

        removeServiceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                //LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);

                if (removeServiceMode) {// then turn it off
                    turnOffRemoveServiceMode();
                } else {// turn it on

                    if (scrollLayout.getChildCount() > 0) {
                        removeServiceMode = true;
                        for (int i = 0; i < scrollLayout.getChildCount(); i++) {
                            TextView temp = (TextView) scrollLayout.getChildAt(i);
                            temp.setBackgroundColor(0xCCff0066);

                        }
                    }

                }


            }
        });


    }

    public void forceKeyboardClose() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }


    public void turnOffRemoveServiceMode() {
        //LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);

        removeServiceMode = false;

        for (int i = 0; i < scrollLayout.getChildCount(); i++) {
            TextView temp = (TextView) scrollLayout.getChildAt(i);
            temp.setBackgroundColor(-1);

        }
    }


    public void createNewServiceTextView() {// later this also may take parameter values from this field or elsewhere for creating the services stuff
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

        result.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                // make sure that there are elements to remove
                if (removeServiceMode) {// then remove this service,
                    // for now just delete the item, later, add a confirm dialog etc.
                    scrollLayout.removeView(v);
                    turnOffRemoveServiceMode();
                } else {// turn it on
                    // do nothing for now, later view details of that service
                    startActivity(new Intent(ViewEventActivity.this, ViewServiceActivity.class));
                }
            }
        });
        //return result;
    }

    public void save(View view) throws JSONException {
        JSONObject json = new JSONObject();
        String id = Integer.toString(event.getID());

        String eventName = this.eventName.getText().toString();
        String eventDescription = this.eventDescription.getText().toString();
        if (TextUtils.isEmpty(eventName)) {
            this.eventName.setError(getString(R.string.error_field_empty));
            this.eventName.requestFocus();
        } else if (TextUtils.isEmpty((eventDescription))) {
            this.eventDescription.setError(getString(R.string.error_field_empty));
            this.eventDescription.requestFocus();
        } else {
            this.eventName.setError(null);

            json.put("name", eventName);
            json.put("description", eventDescription);
            json.put("date", "1000-01-01 00:00:00");
            //If layout is empty, don't add anything to services, else, add services.
            if (scrollLayout.getChildCount() > 0) {
                saveServices(event, json);
            }
            serverData = new ServerData("PUT", json.toString(), id); // update when implemented
            Intent intent = getIntent();
            intent.putExtra(EXTRA_EVENT, event);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void saveServices(Event event, JSONObject json) {
        String tempText;
        Service tempService;
        for (int i = 0; i < scrollLayout.getChildCount(); i++) {
            TextView temp = (TextView) scrollLayout.getChildAt(i);
            tempText = temp.getText().toString();
            tempService = new Service(tempText, "description");
            event.getServices().add(tempService);
        }
    }


}
