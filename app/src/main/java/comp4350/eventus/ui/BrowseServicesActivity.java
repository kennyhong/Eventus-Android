package comp4350.eventus.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import comp4350.eventus.R;
import comp4350.eventus.model.Event;
import comp4350.eventus.model.ServerData;
import comp4350.eventus.model.Service;
import java.util.ArrayList;

public class BrowseServicesActivity extends AppCompatActivity {
private final String EXTRA_CANCEL = "cancel";
private final String EXTRA_SERVICE = "service";
private LinearLayout scrollLayout = null;

  public ArrayList<Service> serviceList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_services);

        scrollLayout = (LinearLayout) findViewById(R.id.addServicesLinLayout);


        gatherAvailableServices();

        setupListeners();
    }

    public void gatherAvailableServices()
    {
        serviceList = new ArrayList<Service>();

        ServerData serverData = new ServerData();

        serviceList = serverData.getServices();

        for(int i = 0 ; i < serviceList.size(); i++)
        {
            createNewServiceTextView(serviceList.get(i));
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
            public void onClick(View v)
            {
                // Do something in response to button click
                    //Intent intent = new Intent(ViewEventActivity.this, ViewServiceActivity.class);
                    //intent.putExtra(ViewServiceActivity.EXTRA_SERVICE, getServiceByID(service.getID()));

                    //startActivity(intent);


                // return code, and include this clicked service


            }
        });
    }


    public void setupListeners()
    {
        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = getIntent();
                forceKeyboardClose();
                Service event = new Service();

                intent.putExtra(EXTRA_CANCEL, event);
                setResult(RESULT_OK, intent);
                finish();

            }
        });





    }
    public void forceKeyboardClose() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
    /*
             Intent intent = getIntent();
            intent.putExtra(EXTRA_EVENT, event);
            setResult(RESULT_OK, intent);
            finish();
    */




    }// end activity
