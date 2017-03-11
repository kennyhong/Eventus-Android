package com.example.baronvonfaustiii.eventus_android.ui;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONFunctions extends AsyncTask<String, Void, String> {

    protected void onPreExecute() {
        //Do Nothing For Now...
        Log.e("Response", "Pre-response to our db call");
    }

    protected String doInBackground(String... params) {

        int responseCode;
        String serverResponse = "";
        String requestCode;

        //Attempt to access our db from url
        try {
            URL url = new URL(params[0]);
            requestCode = params[1];
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // If we're doing a GET request, read in the stream
                    // Else, do a POST request
                    if(requestCode == "GET") {
                        serverResponse = readStream(urlConnection.getInputStream());
                    } else if(requestCode == "POST") {
                        JSONObject json = new JSONObject(params[2]);
                        serverResponse = postStream(urlConnection.getOutputStream(), json);
                    }
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

    private String postStream(OutputStream out, JSONObject json) {
        String result = "";
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));



            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // This method is used for interpreting/reading string from input stream
    private String readStream(InputStream in) {

        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
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