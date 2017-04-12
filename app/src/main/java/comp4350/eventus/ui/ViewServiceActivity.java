package comp4350.eventus.ui;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comp4350.eventus.R;
import comp4350.eventus.model.Service;

public class ViewServiceActivity extends AppCompatActivity {
    public static final String EXTRA_SERVICE = "service";

    private TextView serviceName;
    private TextView servicePrice;
    private TextView serviceCreatedDate;
    public Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service);
        serviceName = (TextView) findViewById(R.id.titleTextView);
        servicePrice = (TextView) findViewById(R.id.priceTag);
        serviceCreatedDate = (TextView)findViewById(R.id.dateBookedTag);

        if (savedInstanceState == null) {
            service = getIntent().getParcelableExtra(EXTRA_SERVICE);
        } else {
            service = savedInstanceState.getParcelable(EXTRA_SERVICE);
        }

        if (service != null)
        {
            initializeFields();
        } else {
            service = new Service();
        }
        setupListeners();
    }

    public void initializeFields()
    {
        setText(serviceName, service.getName());
        setText(serviceCreatedDate, "Date Booked: "+ service.getCreatedAt());
        setText(servicePrice, "Price: $"+ service.getCost());
    }

    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    public void setupListeners() {
        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                setResult(RESULT_CANCELED);
                finish();

            }
        });

        TextView title = (TextView) findViewById(R.id.titleTextView);
        title.setMovementMethod(new ScrollingMovementMethod());


    }
}
