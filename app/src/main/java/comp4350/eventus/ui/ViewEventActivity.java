package comp4350.eventus.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import comp4350.eventus.R;
import comp4350.eventus.model.Event;
import comp4350.eventus.model.ServerData;
import comp4350.eventus.model.Service;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewEventActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT = "event";
    private static final int REQUEST_ADD_SERVICE = 10;
    private final int CANCEL_CODE = 6;

    private TextView eventName;
    private TextView eventDescription;
    private ArrayList<Service> currEventServices;
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
        eventName.setMovementMethod(new ScrollingMovementMethod());

        eventDescription = (TextView) findViewById(R.id.descriptionTextView);
        eventDescription.setMovementMethod(new ScrollingMovementMethod());
        resultCode = 0;
        if (savedInstanceState == null) {
            event = getIntent().getParcelableExtra(EXTRA_EVENT);
        } else {
            event = savedInstanceState.getParcelable(EXTRA_EVENT);
        }

        if (event != null) {
            eventName.setText(event.getName());
            eventDescription.setText(event.getDescription());
            currEventServices = event.getServices();
        } else {
            eventName.setText("Empty Event");
            eventDescription.setText("Empty Event");
            event = new Event(0, "Empty Event", "Empty Event", new ArrayList<Service>());
            currEventServices = event.getServices();
        }

        scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);
        listLayout = new LinearLayout(this);
        listLayout.setOrientation(LinearLayout.HORIZONTAL);

        for(int i = 0; i < currEventServices.size(); i++) {
            createNewServiceTextView(currEventServices.get(i));
        }
        
        setupListeners();
    }

    public ServerData accessServerData() {
        return serverData;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setupListeners() {

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

        Button receiptButton = (Button) findViewById(R.id.receiptButton);

        receiptButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                Intent intent = new Intent(ViewEventActivity.this, ReceiptActivity.class);
                intent.putExtra(ReceiptActivity.EXTRA_RECEIPT, event);

                startActivity(intent);



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
                    serverData = new ServerData("http://eventus.us-west-2.elasticbeanstalk.com/api/events/" + Integer.toString(event.getID()), "DELETE", "");
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
                if (editOn)
                {
                    forceKeyboardClose();
                    editOn = false;
                    setFieldsToEditable(editOn);

                } else {
                    // Make deleting the event possible
                    editOn = true;
                    setFieldsToEditable(editOn);

                }

            }
        });


        ImageButton addButton = (ImageButton) findViewById((R.id.addNewServiceButton));

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                turnOffRemoveServiceMode();

                forceKeyboardClose();

                Intent intent = new Intent(ViewEventActivity.this, BrowseServicesActivity.class);

                intent.putExtra(BrowseServicesActivity.EXTRA_BROWSE, event);

                startActivityForResult(intent, REQUEST_ADD_SERVICE);

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

    public void setFieldsToEditable(boolean status)
    {
        final Button deleteButton = (Button) findViewById(R.id.deleteButton);
        ImageButton editButton = (ImageButton) findViewById(R.id.editButton);

        if(!status)
        {// then turn edit abilities off
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
                    title.setFocusable(false);
                    forceKeyboardClose();
                }
            });

            TextView desc = (TextView) findViewById(R.id.descriptionTextView);
            // allow for the title to be updated. @ TO DO , this needs to patching up
            //title.setTextIsSelectable(true);
            desc.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    TextView desc = (TextView) findViewById(R.id.descriptionTextView);
                    // Do something in response to button click
                    desc.setCursorVisible(false);
                    desc.setFocusableInTouchMode(false);
                    desc.setFocusable(false);
                    forceKeyboardClose();
                }
            });
        }
        else
        {


            deleteButton.setVisibility(View.VISIBLE);

            forceKeyboardClose();
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
                }
            });

            TextView desc = (TextView) findViewById(R.id.descriptionTextView);
            //title.setTextIsSelectable(true);
            desc.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    TextView desc = (TextView) findViewById(R.id.descriptionTextView);
                    // Do something in response to button click
                    desc.setCursorVisible(true);
                    desc.setFocusableInTouchMode(true);
                    desc.setInputType(InputType.TYPE_CLASS_TEXT);
                    desc.requestFocus(); //to trigger the soft input
                }
            });
        }


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


    public void createNewServiceTextView(final Service service) {// later this also may take parameter values from this field or elsewhere for creating the services stuff
        // later this can be used for actually assembling the service object maybe

        TextView result = new TextView(this);
        result.setText(service.getName());
        result.setBackgroundColor(-1);
        result.setTextSize(24f);
        result.setTextColor((0xff000000));
        result.setClickable(true);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        scrollLayout.addView(result, lp);

        result.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // make sure that there are elements to remove
                if (removeServiceMode) {// then remove this service,
                    // for now just delete the item, later, add a confirm dialog etc.
                    scrollLayout.removeView(v);

                    String serviceName =  ((TextView)v).getText().toString();

                    for(int i = 0 ; i < event.getServices().size(); i++)
                    {
                        if(event.getServices().get(i).getName().equals(serviceName))
                        {
                            event.getServices().remove(i);
                            ServerData serverData = new ServerData("http://eventus.us-west-2.elasticbeanstalk.com/api/events/"+event.getID()+"/services/"+service.getID(), "DELETE", "");
                            break;
                        }
                    }

                    turnOffRemoveServiceMode();
                } else {// turn it on
                    startActivity(new Intent(ViewEventActivity.this, ViewServiceActivity.class));
                }
            }
        });
    }

    public Service getServiceByID(int id)
    {
        Service result = null;
        for(int i = 0 ; i < currEventServices.size(); i++)
        {
            if(currEventServices.get(i).getID() == id)
            {
                result = currEventServices.get(i);
            }
        }
        return result;
    }

    public void save(View view) throws JSONException {
        JSONObject json = new JSONObject();
        ServerData eventServerData;
        String eventData;

        if (event == null) {
            event = new Event();
        }
        String saveEventName = eventName.getText().toString();
        String saveEventDescription = eventDescription.getText().toString();
        if (TextUtils.isEmpty(saveEventName)) {
            eventName.setError(getString(R.string.error_field_empty));
            eventName.requestFocus();
        } else if (TextUtils.isEmpty((saveEventDescription))) {
            eventDescription.setError(getString(R.string.error_field_empty));
            eventDescription.requestFocus();
        } else {
            eventName.setError(null);
            event.setName(saveEventName);
            event.setDescription(saveEventDescription);
            json.put("name", saveEventName);
            json.put("description", saveEventDescription);
            json.put("date", "1000-01-01 00:00:00");
            //If layout is empty, don't add anything to services, else, add services.

            eventData = json.toString();
            eventServerData = new ServerData("http://eventus.us-west-2.elasticbeanstalk.com/api/events/"+Integer.toString(event.getID()), "PUT", eventData);
            Intent intent = getIntent();
            intent.putExtra(EXTRA_EVENT, event);
            setResult(RESULT_OK, intent);
            finish();
        }
    }


//    serverData = new ServerData("http://eventus.us-west-2.elasticbeanstalk.com/api/events/"+id, "PUT", json.toString()); // update when implemented

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        boolean cancel = false;
        if (requestCode == CANCEL_CODE )
        { // do nothing
            cancel = true;
        }
        if (requestCode == REQUEST_ADD_SERVICE && !cancel && (data.getParcelableExtra(BrowseServicesActivity.EXTRA_SERVICE) != null  ))
        {// then it is returning from an add service, with a service, so extract it.
            Service service = data.getParcelableExtra(BrowseServicesActivity.EXTRA_SERVICE);

            event.getServices().add(service);

            // Then save the service to the event
            createNewServiceTextView(service);

            ServerData serverData = new ServerData("http://eventus.us-west-2.elasticbeanstalk.com/api/events/"+event.getID()+"/services/"+service.getID(), "POST", "");
            //events = serverData.getEvents();
            //eventListAdapter.refresh(events);

            setupListeners();
        }

    }

}
