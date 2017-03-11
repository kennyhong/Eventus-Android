package com.example.baronvonfaustiii.eventus_android.model;

import com.example.baronvonfaustiii.eventus_android.ui.JSONFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ServerData {

    ArrayList<Event> events;
    String serverInfo;
    String url;

    public ServerData() {
        url = "http://eventus.us-west-2.elasticbeanstalk.com/api/events";
        try {
            serverInfo = new JSONFunctions().execute(url).get();
            parseJSON(serverInfo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void parseJSON(String data) throws JSONException {
        // Create a new eventList to be added to adapter
        events = new ArrayList<>();
        JSONObject json = new JSONObject(data);
        JSONArray jsonEvents = json.getJSONArray("data");
        String meta = json.getString("meta");
        String error = json.getString("error");

        JSONObject jsonServices;
        JSONObject jsonServiceTags;
        JSONObject jsonEventParse;
        JSONArray jsonServicesArray;
        JSONArray jsonServiceTagsArray;

        // Data needed for Event object
        Event newEvent;
        int eventId;
        String eventName;
        String eventDescription;
        String eventDate;
        String eventCreatedAt;
        String eventUpdatedAt;
        ArrayList<Service> services;

        // Data needed for Service object
        Service service;
        int serviceId;
        String serviceName;
        int serviceCost;
        String serviceCreatedAt;
        String serviceUpdatedAt;
        ArrayList<ServiceTag> serviceTags;

        // Data needed for serviceTags
        ServiceTag serviceTag;
        int serviceTagId;
        String serviceTagName;
        String serviceTagCreatedAt;
        String serviceTagUpdatedAt;

        if (error != null) {
            for (int i = 0; i < jsonEvents.length(); i++) {
                // Grab the Event JSON object and parse it into separate Event parts
                jsonEventParse = jsonEvents.getJSONObject(i);
                eventId = jsonEventParse.getInt("id");
                eventName = jsonEventParse.getString("name");
                eventDescription = jsonEventParse.getString("name");
                eventDate = jsonEventParse.getString("date");
                eventCreatedAt = jsonEventParse.getString("created_at");
                eventUpdatedAt = jsonEventParse.getString("updated_at");
                jsonServicesArray = jsonEventParse.getJSONArray("services");
                services = new ArrayList<Service>();
                for (int j = 0; j < jsonServicesArray.length(); j++) {
                    jsonServices = jsonServicesArray.getJSONObject(j);
                    serviceId = jsonServices.getInt("id");
                    serviceName = jsonServices.getString("name");
                    serviceCost = jsonServices.getInt("cost");
                    serviceCreatedAt = jsonServices.getString("created_at");
                    serviceUpdatedAt = jsonServices.getString("updated_at");
                    jsonServiceTagsArray = jsonServices.getJSONArray("service_tags"); //Type mismatch, has to be a JSONArray
                    serviceTags = new ArrayList<ServiceTag>();
                    for (int k = 0; k < jsonServiceTagsArray.length(); k++) {
                        jsonServiceTags = jsonServiceTagsArray.getJSONObject(k);
                        serviceTagId = jsonServiceTags.getInt("id");
                        serviceTagName = jsonServiceTags.getString("name");
                        serviceTagCreatedAt = jsonServiceTags.getString("created_at");
                        serviceTagUpdatedAt = jsonServiceTags.getString("updated_at");
                        serviceTag = new ServiceTag(serviceTagId, serviceTagName, serviceTagCreatedAt, serviceTagUpdatedAt);
                        serviceTags.add(serviceTag);
                    }
                    service = new Service(serviceId, serviceName, serviceCost, serviceCreatedAt, serviceUpdatedAt, serviceTags);
                    services.add(service);
                }
                newEvent = new Event(eventId, eventName, eventDescription, eventDate, eventCreatedAt, eventUpdatedAt, services);
                events.add(newEvent);
            }
        }
    }
}