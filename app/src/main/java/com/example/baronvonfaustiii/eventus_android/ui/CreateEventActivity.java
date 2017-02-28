package com.example.baronvonfaustiii.eventus_android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.baronvonfaustiii.eventus_android.R;
import com.example.baronvonfaustiii.eventus_android.model.Event;

/**
 * Created by Bailey on 2/26/2017.
 */


public class CreateEventActivity extends AppCompatActivity {

    public static final String EXTRA_EVENT = "event";

    private Event event;
    private EditText inputEventName;
    private EditText inputEventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
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
}
