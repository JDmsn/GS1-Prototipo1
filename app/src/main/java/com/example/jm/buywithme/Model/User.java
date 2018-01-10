package com.example.jm.buywithme.Model;

/**
 * Created by jm on 17.12.23.
 */

public class User {
    private String email = "";
    private String age = "";
    private String country = "";

    public User(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
