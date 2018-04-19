package com.example.jm.buywithme.Model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jm on 17.12.23.
 */

public class User {

    private String name = "";
    private String key;
    private String email;
    private Map<String, String> myLists = new LinkedHashMap<>();

    public User(String email, String key){
        this.email = email.substring(0, email.length() - 4);
        this.key = key;
        name = email.substring(0, email.length() - 10);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMyLists(Map<String, String> myLists){
        this.myLists = myLists;

    }

    public Map<String, String> getMyLists() {
        return myLists;
    }

    public void addToMyLists(String list, String name) {
        myLists.put(list, name);
    }

    public void deleteFromMyLists(Long list){

        myLists.remove(String.valueOf(list));
    }
}
