package com.example.baronvonfaustiii.eventus_android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.baronvonfaustiii.eventus_android.R;
import com.example.baronvonfaustiii.eventus_android.model.Event;

public class ViewEventActivity extends AppCompatActivity
{
    public static final String EXTRA_EVENT = "event";

    private TextView eventName;
    private TextView eventDescription;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        eventName = (TextView)findViewById(R.id.titleTextView);
        eventDescription = (TextView)findViewById(R.id.descriptionTextView);

        if(savedInstanceState == null){
            event = getIntent().getParcelableExtra(EXTRA_EVENT);
        } else {
            event = savedInstanceState.getParcelable(EXTRA_EVENT);
        }

        if(event != null) {
            eventName.setText(event.getName());
            eventDescription.setText(event.getDescription());
        }
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
                setResult(RESULT_CANCELED);
                finish();

            }
        });
    }
}
