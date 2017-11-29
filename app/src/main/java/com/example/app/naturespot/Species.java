package com.example.app.naturespot;

/**
 * Created by joseppmoreira on 28/11/2017.
 */

public class Species {

    private String name;
    private String location;
    private String image;
    private String uid;

    public Species(String name, String location, String image, String uid){
        this.name = name;
        this.location = location;
        this.image = image;
        this.uid = uid;
    }

    public Species(){

    }

    public String getName(){
        return name;
    }

    public String getLocation(){
        return location;
    }

    public String getImage(){
        return image;
    }

    public String getUid() { return uid; }

}
