package com.example.baronvonfaustiii.eventus_android.ui;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Bailey on 2/25/2017.
 */

public class JSONFunctions extends AsyncTask<String, Void, String> {

    protected void onPreExecute() {
        //Do Nothing For Now...
        Log.e("Response", "Pre-response to our db call");
    }

    protected String doInBackground(String... params) {

        int responseCode;
        String serverResponse = "";

        //Attempt to access our db from url
        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    //Read in the stream
                    serverResponse = readStream(urlConnection.getInputStream());
                }
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
        return serverResponse;
    }

    protected void onPostExecute(String response) {
        // Check to see if we got some valid data back
        if (response == null) {
            response = "THERE WAS AN ERROR";
        }
        Log.e("Response: ", "" + response);
    }

    // This method is used for interpreting/reading string from input stream
    private String readStream(InputStream in) {

        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}