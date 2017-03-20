package com.example.baronvonfaustiii.eventus_android.ui;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class JSONFunctions extends AsyncTask<String, Void, String> {

    protected void onPreExecute() {
        //Do Nothing For Now...
        Log.e("Response", "Pre-response to our db call");
    }

    protected String doInBackground(String... params) {

//        int responseCode;
        String serverResponse = "";
        String requestCode;
        String data;
        int responseCode;

        //Attempt to access our db from url
        try {
            URL url = new URL(params[0]);
            requestCode = params[1];
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                if (requestCode.equals("GET")) {
                    urlConnection.setRequestMethod("GET");
                    serverResponse = readStream(urlConnection.getInputStream());
                } else if (requestCode.equals("DELETE")) {
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod("DELETE");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.connect();
                    responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            serverResponse += line;
                        }
                    } else {
                        serverResponse = "Error";
                    }
                } else {
                    if (requestCode.equals("POST")) {
                        urlConnection.setRequestMethod("POST");
                    } else if (requestCode.equals("PUT")) {
                        urlConnection.setRequestMethod("PUT");
                    }
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    data = params[2];
                    serverResponse = stream(urlConnection.getOutputStream(), data);
                    responseCode = urlConnection.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            serverResponse += line;
                        }
                    } else {
                        serverResponse = "Error";
                    }
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

    private String stream(OutputStream out, String data) {
        String result = "";
        try {
            System.out.println("DATA: " + data);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            out.close();
            result = "Success";
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