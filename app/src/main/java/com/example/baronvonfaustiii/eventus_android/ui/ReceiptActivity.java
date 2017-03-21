package com.example.baronvonfaustiii.eventus_android.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import org.json.JSONException;

import com.example.baronvonfaustiii.eventus_android.R;
import com.example.baronvonfaustiii.eventus_android.model.Event;
import com.example.baronvonfaustiii.eventus_android.model.Service;

public class ReceiptActivity extends AppCompatActivity
{
    public static final String EXTRA_EVENT = "event";
    private ArrayList<Service> eventServices;
    LinearLayout scrollLayout = null;


    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        if (savedInstanceState == null) {
            event = getIntent().getParcelableExtra(EXTRA_EVENT);
        } else {
            event = savedInstanceState.getParcelable(EXTRA_EVENT);
        }

        if (event != null) {
            eventServices = event.getServices();
        }

        scrollLayout = (LinearLayout) findViewById(R.id.ServiceScrollLinearLayout);

        setupListeners();
    }

    public void setupListeners()
    {

        //setup the services list , with associated costs
        if (event != null) {
            for (Service service : event.getServices())
            {
                createNewServiceTextView(service);
            }
        }


        // Initialize the back button, for navigating back to the event

        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                    setResult(RESULT_CANCELED);
                    finish();


            }
        });
    }


    public void createNewServiceTextView(Service service)
    {


        TextView result = new TextView(this);
        result.setText("New Service Added"); // switch this to the service title + "---------------"+ cost
        result.setBackgroundColor(-1);
        result.setTextSize(24f);
        result.setTextColor((0xff000000));
        result.setClickable(true);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        scrollLayout.addView(result, lp);

    }

    }
