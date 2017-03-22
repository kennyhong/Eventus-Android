package comp4350.eventus.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comp4350.eventus.R;
import comp4350.eventus.model.Service;

public class ViewServiceActivity extends AppCompatActivity {
    public static final String EXTRA_SERVICE = "service";

    private TextView serviceName;
    private TextView serviceDescription;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service);
        serviceName = (TextView) findViewById(R.id.titleTextView);
        serviceDescription = (TextView) findViewById(R.id.descriptionTag);

        if (savedInstanceState == null) {
            service = getIntent().getParcelableExtra(EXTRA_SERVICE);
        } else {
            service = savedInstanceState.getParcelable(EXTRA_SERVICE);
        }

        if (service != null) {
            serviceName.setText(service.getName());
            serviceDescription.setText(service.getDescription());
        }
        setupListeners();
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
    }
}
