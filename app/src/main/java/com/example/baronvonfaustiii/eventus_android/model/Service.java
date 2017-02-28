package com.example.baronvonfaustiii.eventus_android.model;

/**
 * Created by Bailey on 2/26/2017.
 */

public class Service {

    private String description;
    private String name;

    public Service(){
    }

    public Service(String description, String name){
        this.description = description;
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }
    public String getName(){
        return name;
    }
}
