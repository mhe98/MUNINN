package com.raven.muninnmatching.Data;

/**
 *
 * @author Lewi Ayun, Mark He, Daniel Frye
 * Last Updated: 12/10/2019
 */

public class UserInformation {

    private String username;
    private String email;
    private String city;
    private String state;
    private String tag1;
    private String tag2;
    private String tag3;

    //default constructor
    public UserInformation() {

    }

    public UserInformation(String username, String city, String state,
                           String tag1, String tag2, String tag3) {

        this.username = username;
        this.email = email;
        this.city = city;
        this.state = state;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
    }

    //========================GETTERS========================

    public String getUsername() { return username; }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getTag1() {
        return tag1;
    }

    public String getTag2(){ return tag2; }

    public String getTag3() { return  tag3; }
    //========================SETTERS========================

    public void setUsername(String _username) {
        this.username = _username;
    }

    public void setEmail(String _email) {
        this.email = _email;
    }

    public void setCity(String _city) {
        this.city = _city;
    }

    public void setState(String _state) {
        this.state = _state;
    }

    public void setTag1(String _tag1) { this.tag1 = _tag1;}

    public void setTag2(String _tag2) { this.tag2 = _tag2; }

    public void setTag3(String _tag3) {this.tag3 = _tag3; }

}
