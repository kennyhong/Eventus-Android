package comp4350.eventus.ui;

import android.app.Activity;
import android.content.Context;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import comp4350.eventus.R;
import comp4350.eventus.model.Event;
import comp4350.eventus.model.ServerData;
import comp4350.eventus.model.Service;

import java.util.Calendar;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;


public class CreateEventActivity extends AppCompatActivity {

    public static final String EXTRA_EVENT = "event";
    private static final int REQUEST_ADD_SERVICE = 10;
    private final int CANCEL_CODE = 6;


    boolean removeServiceMode = false;
    LinearLayout scrollLayout;
    private Event event;
    private Context context;
    private EditText inputEventName;
    private EditText inputEventDescription;
    private boolean keyboardAltOpen = false;
    private EditText inputEventDate;
    private EditText inputEventTime;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        context = this;
        event = new Event();

        scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);
        setupListeners();
    }

    public void forceKeyboardClose() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public Event getEvent() {
        return event;
    }


    public void setupListeners() {
        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                startActivity(new Intent(CreateEventActivity.this, SignedInLandingPage.class));

            }
        });

        Button saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something in response to button click
                try {
                    save(view);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        inputEventName = (EditText) findViewById(R.id.eventNameEditText);


        inputEventName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // close the keyboard
                    forceKeyboardClose();
                    return true;
                }
                return false;
            }
        });

        inputEventDescription = (EditText) findViewById(R.id.eventDescriptionEditText);
        inputEventDescription.setMovementMethod(new ScrollingMovementMethod());

// Should set up listeners so that the keyboard will close when the enter key is pressed.
        inputEventDescription.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    inputEventDescription.setText(inputEventDescription.getText().toString().trim());
                    // close the keyboard
                    forceKeyboardClose();
                    return true;
                }

                return false;
            }
        });


        // More close keyboard checks

        inputEventDescription.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                String userNameFieldText = inputEventDescription.getText().toString();
                if (TextUtils.isEmpty(userNameFieldText)) {
                    // do nothing, input still needed
                } else {
                    if (keyboardAltOpen) {
                        keyboardAltOpen = false;
                    } else {
                        forceKeyboardClose();
                        keyboardAltOpen = true;
                    }
                }

            }
        });

        ImageButton addServiceButton = (ImageButton) findViewById((R.id.addServiceButton));

        addServiceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Begin new Dialog actions for adding a new event
                turnOffRemoveServiceMode();
                forceKeyboardClose();


                Intent intent = new Intent(CreateEventActivity.this, BrowseServicesActivity.class);

                intent.putExtra(BrowseServicesActivity.EXTRA_BROWSE, event);

                startActivityForResult(intent, REQUEST_ADD_SERVICE);

            }
        });

        ImageButton removeServiceButton = (ImageButton) findViewById((R.id.removeServiceButton));

        removeServiceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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

        inputEventDate = (EditText) findViewById(R.id.eventDay);
        inputEventTime = (EditText) findViewById(R.id.eventTime);

        inputEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(CreateEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                inputEventDate.setText(String.format(Locale.getDefault(), "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth));
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        inputEventTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        inputEventTime.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
    }

    public void turnOffRemoveServiceMode() {
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
                            break;
                        }
                    }

                    turnOffRemoveServiceMode();
                } else {// turn it on
                    Intent intent = new Intent(CreateEventActivity.this, ViewServiceActivity.class);
                    intent.putExtra(ViewServiceActivity.EXTRA_SERVICE, service);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_EVENT, event);
    }


    public void save(View view) throws JSONException {
        JSONObject json = new JSONObject();
        ServerData eventServerData;
        ServerData serviceServerData;
        String eventData;
        int serviceId;
        int eventId;

        if (event == null) {
            event = new Event();
        }
        String eventName = inputEventName.getText().toString();
        String eventDescription = inputEventDescription.getText().toString();
        if (TextUtils.isEmpty(eventName)) {
            inputEventName.setError(getString(R.string.error_field_empty));
            inputEventName.requestFocus();
        } else if (TextUtils.isEmpty((eventDescription))) {
            inputEventDescription.setError(getString(R.string.error_field_empty));
            inputEventDescription.requestFocus();
        } else {
            inputEventName.setError(null);
            event.setName(eventName);
            event.setDescription(eventDescription);
            String fullDate = inputEventDate.getText().toString() + " " + inputEventTime.getText().toString() + ":00";
            event.setDate(fullDate);
            json.put("name", eventName);
            json.put("description", eventDescription);
            json.put("date", fullDate);
            //If layout is empty, don't add anything to services, else, add services.

            eventData = json.toString();
            eventServerData = new ServerData("http://eventus.us-west-2.elasticbeanstalk.com/api/events", "POST", eventData);
            eventId = eventServerData.getId();
            event.setId(eventId);
            for(int i = 0; i < event.getServices().size(); i++) {
                serviceId = event.getServices().get(i).getID();
                serviceServerData = new ServerData("http://eventus.us-west-2.elasticbeanstalk.com/api/events/"+eventId+"/services/"+serviceId/*replace i with serviceId*/, "POST", "");
            }
            Intent intent = getIntent();
            intent.putExtra(EXTRA_EVENT, event);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

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

            setupListeners();
        }

    }

}
