package com.example.baronvonfaustiii.eventus_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
//test
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupListeners();

    }

    public void setupListeners()
    {
        Button signIn = (Button)findViewById( R.id.OK_Button);


        signIn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Do something in response to button click
                EditText userNameField = (EditText)findViewById((R.id.UsernameField));
                userNameField.setText("Signing you in");


            }
        });

        final EditText userNameField = (EditText)findViewById((R.id.UsernameField));

        userNameField.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                { 
                    // Perform action on key press
                   userNameField.setText("Signing you in");

                    return true;
                }
                return false;
            }
        });
    }

}
