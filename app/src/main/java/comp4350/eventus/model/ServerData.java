package comp4350.eventus.model;

import comp4350.eventus.ui.JSONFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ServerData {

    ArrayList<Event> events;
    ArrayList<Service> services;
    ArrayList<ServiceTag> serviceTags;
    String requestCode;
    String serverInfo;
    String data;
    String url;
    int JSONObjId;

    public ServerData() {
        requestCode = "GET";
        getAllEventsRequest();
        getAllServicesRequest();
        getAllServiceTagsRequest();
    }

    public ServerData(String url, String requestCode, String data) {
        this.requestCode = requestCode;
        this.data = data;
        this.url = url;
        if (requestCode.equals("POST")) {
            postRequest(url, requestCode, data);
        } else if (requestCode.equals("DELETE")) {
            deleteRequest(url, requestCode);
        } else if(requestCode.equals("PUT")) {
            putRequest(url, requestCode, data);
        }
    }

    public int getId() {
        return JSONObjId;
    }

    public int getJSONId(String jsonData) {
        try {
            System.out.println("JSONData: "+jsonData);
            JSONObject json = new JSONObject(jsonData);
            Object type = json.get("data");
                if (type instanceof JSONObject) {
                    JSONObject jsonEvents = (JSONObject) type;
                    return jsonEvents.getInt("id");
                } else if (type instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) type;
                    return jsonArray.getJSONObject(0).getInt("id");
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void putRequest(String url, String requestCode, String data) {
        try {
            serverInfo = new JSONFunctions().execute(url, requestCode, data).get();
            JSONObjId = getJSONId(serverInfo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void deleteRequest(String url, String requestCode) {
        try {
            serverInfo = new JSONFunctions().execute(url, requestCode).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void postRequest(String url, String requestCode, String data) {
        try {
            serverInfo = new JSONFunctions().execute(url, requestCode, data).get();
            JSONObjId = getJSONId(serverInfo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void getAllEventsRequest() {
        try {
            serverInfo = new JSONFunctions().execute("http://eventus.us-west-2.elasticbeanstalk.com/api/events", "GET").get();
            parseJSONEvents(serverInfo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJSONEventDetails(Event event) throws JSONException {
        JSONObject json = new JSONObject();
        String id = Integer.toString(event.getID());

        String eventName = event.getName();
        String eventDescription = event.getDescription();

        json.put("name", eventName);
        json.put("description", eventDescription);
        json.put("date", "1000-01-01 00:00:00");
        //If layout is empty, don't add anything to services, else, add services.

        return json;
    }

    public void getAllServicesRequest() {
        try {
            serverInfo = new JSONFunctions().execute("http://eventus.us-west-2.elasticbeanstalk.com/api/services", "GET").get();
            parseJSONServices(serverInfo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getAllServiceTagsRequest() {
        try {
            serverInfo = new JSONFunctions().execute("http://eventus.us-west-2.elasticbeanstalk.com/api/service_tags", "GET").get();
            parseJSONServiceTags(serverInfo);
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

    public ArrayList<Service> getServices() {
        return services;
    }

    public ArrayList<ServiceTag> getServiceTags() {
        return serviceTags;
    }

    public void parseJSONEvents(String data) throws JSONException {
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

        if (!error.equals(null)) {
            for (int i = 0; i < jsonEvents.length(); i++) {
                // Grab the Event JSON object and parse it into separate Event parts
                jsonEventParse = jsonEvents.getJSONObject(i);
                eventId = jsonEventParse.getInt("id");
                eventName = jsonEventParse.getString("name");
                eventDescription = jsonEventParse.getString("description");
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
                    jsonServiceTagsArray = jsonServices.getJSONArray("service_tags");
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

    public void parseJSONServices(String data) throws JSONException {
        services = new ArrayList<Service>();
        JSONObject json = new JSONObject(data);
        JSONArray jsonServicesArray = json.getJSONArray("data");
        JSONObject jsonServices;
        JSONArray jsonServiceTagsArray;
        JSONObject jsonServiceTags;
        String meta = json.getString("meta");
        String error = json.getString("error");

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

        if (!error.equals(null)) {
            for (int i = 0; i < jsonServicesArray.length(); i++) {
                jsonServices = jsonServicesArray.getJSONObject(i);
                serviceId = jsonServices.getInt("id");
                serviceName = jsonServices.getString("name");
                serviceCost = jsonServices.getInt("cost");
                serviceCreatedAt = jsonServices.getString("created_at");
                serviceUpdatedAt = jsonServices.getString("updated_at");
                jsonServiceTagsArray = jsonServices.getJSONArray("service_tags");
                serviceTags = new ArrayList<ServiceTag>();
                for (int j = 0; j < jsonServiceTagsArray.length(); j++) {
                    jsonServiceTags = jsonServiceTagsArray.getJSONObject(j);
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
        }
    }

    public void parseJSONServiceTags(String data) throws JSONException {
        serviceTags = new ArrayList<ServiceTag>();
        JSONObject json = new JSONObject(data);
        JSONArray jsonServiceTagsArray = json.getJSONArray("data");
        JSONObject jsonServiceTags;
        String meta = json.getString("meta");
        String error = json.getString("error");

        // Data needed for serviceTags
        ServiceTag serviceTag;
        int serviceTagId;
        String serviceTagName;
        String serviceTagCreatedAt;
        String serviceTagUpdatedAt;

        if (!error.equals(null)) {
            for (int i = 0; i < jsonServiceTagsArray.length(); i++) {
                jsonServiceTags = jsonServiceTagsArray.getJSONObject(i);
                serviceTagId = jsonServiceTags.getInt("id");
                serviceTagName = jsonServiceTags.getString("name");
                serviceTagCreatedAt = jsonServiceTags.getString("created_at");
                serviceTagUpdatedAt = jsonServiceTags.getString("updated_at");
                serviceTag = new ServiceTag(serviceTagId, serviceTagName, serviceTagCreatedAt, serviceTagUpdatedAt);
                serviceTags.add(serviceTag);
            }
        }
    }
}