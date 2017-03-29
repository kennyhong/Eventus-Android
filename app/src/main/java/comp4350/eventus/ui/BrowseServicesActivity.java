package comp4350.eventus.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import comp4350.eventus.R;
import comp4350.eventus.model.Event;
import comp4350.eventus.model.ServerData;
import comp4350.eventus.model.Service;
import java.util.ArrayList;

public class BrowseServicesActivity extends AppCompatActivity {
private final String EXTRA_CANCEL = "cancel";
    public static final String EXTRA_BROWSE = "event";
    public static String EXTRA_SERVICE = "service";
private final int ADD_SERVICE_CODE = 10;
private final int CANCEL_CODE = 6;
    private int filterMode = 0;

public LinearLayout scrollLayout = null;
private EditText searchBar = null;

private ImageButton searchButton = null;

    private  Button nameButton = null;
    private  Button idButton = null;
    private  Button serviceTagButton = null;

    public Event event;
    public ArrayList<Service> eventServices;

  public ArrayList<Service> serviceList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_services);

        if (savedInstanceState == null) {
            event = getIntent().getParcelableExtra(EXTRA_BROWSE);

        } else {
            event = savedInstanceState.getParcelable(EXTRA_BROWSE);
        }


        if (event != null)
        {
            eventServices = event.getServices();
        }
        else
        {
            eventServices = new ArrayList<Service>();
        }

        initScrollLayout();
        setupListeners();

        gatherAvailableServices(true);

    }

    public void initScrollLayout()
    {
        scrollLayout = (LinearLayout) findViewById(R.id.addServicesLinLayout);
    }

    public void emptyAvailableServices()
    {
        while(scrollLayout.getChildCount() > 0)
        {
            scrollLayout.removeViewAt(0);
        }
    }

    public void gatherAvailableServices(boolean reset)
    {
        serviceList = new ArrayList<Service>();
        String text = searchBar.getText().toString();

        ServerData serverData = new ServerData();

        serviceList = serverData.getServices();

        for(int i = 0 ; i < serviceList.size(); i++)
        {
            Service curr = serviceList.get(i);
            boolean add = true;
            boolean serviceAdd = false;

            for(int j = 0 ; j < eventServices.size(); j++)
            {// check to see if this service, matches any existing service that you are already signed up for
                if(event.getServices().get(j).getID() == curr.getID())
                {// then the id of this service, already exists on this event, and we do not want to add it again
                    add = false;
                   break;
               }

            }

            if(!reset)
            {

            // filter out other results from the search bar
            if(add)
            {// you dont want more filtering
                    switch (filterMode)
                    {
                        case 0 :
                            if(!curr.getName().contains(text))
                            {//
                                add = false;
                            }
                            break;
                        case 1 :
                            String serviceID = Integer.toString(curr.getID());
                            if(!serviceID.equals(text))
                            {
                                add = false;
                            }
                            break;
                        case 2 :

                            for(int k = 0 ; k < curr.getServiceTags().size(); k++)
                            {
                                if(curr.getServiceTags().get(k).getName().toString().equals(text))
                                {
                                    // then this service, has the searched for text, as a service tag
                                    serviceAdd = true;
                                }
                            }

                            if(!serviceAdd)
                            {// then this means that this service, does not have a matching service tag
                                add = false;
                            }

                            break;

                    }// end switch
                }

            }// end if

            // Then you want to display it as an option
            if(add)
            {
                createNewServiceTextView(curr);
            }

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

                forceKeyboardClose();
                // return code, and include this clicked service
            Intent intent = getIntent();
            intent.putExtra(EXTRA_SERVICE, service);
            setResult(ADD_SERVICE_CODE, intent);
            finish();


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
                //Service event = new Service();

                intent.putExtra(EXTRA_CANCEL, event);
                setResult(CANCEL_CODE, intent);
                finish();

            }
        });

        searchBar = (EditText) findViewById(R.id.searchBar);

        searchBar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                if(searchBar.getText().toString().equals("Browse"))
                {
                    searchBar.setText("");
                }

            }
        });

        searchButton = (ImageButton) findViewById(R.id.SearchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(searchBar.getText().toString().equals("Browse"))
                {
                    searchBar.setText("");
                    rePopulate(true);
                }

                if(searchBar.getText().toString().equals(""))
                {
                    rePopulate(true);
                }
                else
                {
                    rePopulate(false);

                }


            }
        });

        initializeFilterButtons();


    }

    public void initializeFilterButtons()
    {
        // Filter ID 0 == Name filter
         nameButton = (Button)findViewById(R.id.byNameButton);
        //Filter ID 1 = By ID filter
         idButton = (Button)findViewById(R.id.byIDButton);
        //Filter ID = 2 == By Service tag button
         serviceTagButton = (Button)findViewById(R.id.byServiceTagButton);

        //apply listeners

        nameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                if(filterMode != 0)
                {// then we are turning filter by name on.
                    // need to turn off other button... IE change its background back to white
                    filterMode = 0 ;
                    resetSelectedFilter();
                   // populateServicesList();
                }

            }
        });

        idButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                if(filterMode != 1)
                {// then we are turning filter by name on.
                    // need to turn off other button... IE change its background back to white
                    filterMode = 1 ;
                    resetSelectedFilter();
                  //  populateServicesList();
                }

            }
        });

        serviceTagButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                if(filterMode != 2)
                {// then we are turning filter by name on.
                    // need to turn off other button... IE change its background back to white
                    filterMode = 2 ;
                    resetSelectedFilter();
                }

            }
        });


    }

    public void resetSelectedFilter()
    {
        switch(filterMode)
        {
            case 0:
                nameButton.setBackgroundColor(0xffff8800);

                idButton.setBackgroundColor(0xffffffff);
                serviceTagButton.setBackgroundColor(0xffffffff);
                break;
            case 1 :
                idButton.setBackgroundColor(0xffff8800);
                serviceTagButton.setBackgroundColor(0xffffffff);
                nameButton.setBackgroundColor(0xffffffff);

                break;

            case 2 :
                serviceTagButton.setBackgroundColor(0xffff8800);
                idButton.setBackgroundColor(0xffffffff);
                nameButton.setBackgroundColor(0xffffffff);
                break;
        }
    }

    public void rePopulate(boolean reset)
    {
        emptyAvailableServices();
        gatherAvailableServices(reset);
    }


    public void forceKeyboardClose() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    }// end activity
