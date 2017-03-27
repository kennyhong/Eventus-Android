package comp4350.eventus.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import comp4350.eventus.R;

public class MainActivity extends AppCompatActivity {
    //test
    private EditText userNameField;
    private String userNameFieldText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupListeners();

    }

    public void setupListeners() {
        Button signIn = (Button) findViewById(R.id.OK_Button);
        userNameField = (EditText) findViewById((R.id.UsernameField));
        userNameFieldText = userNameField.getText().toString();

        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                String userNameFieldText = userNameField.getText().toString();
                if (TextUtils.isEmpty(userNameFieldText)) {
                    userNameField.setError(getString(R.string.error_field_empty));
                    userNameField.requestFocus();
                } else {
                    userNameField.setError(null);
                    userNameField.setText("Signing you in");
                    startActivity(new Intent(MainActivity.this, SignedInLandingPage.class));
                }

            }
        });

        final EditText userNameField = (EditText) findViewById((R.id.UsernameField));

        userNameField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER) && !(TextUtils.isEmpty(userNameFieldText))) {
                    // Perform action on key press
                    userNameField.setText("Signing you in");

                    return true;
                }
                return false;
            }
        });
    }

}
