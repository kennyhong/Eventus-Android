package com.example.baronvonfaustiii.eventus_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignedInLandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in_landing_page);

        Button signOutButton = (Button)findViewById(R.id.SignoutButton);

        signOutButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Do something in response to button click
                startActivity(new Intent(SignedInLandingPage.this, MainActivity.class));



            }
        });



    }
}
