package com.raven.muninnmatching.Data;

/**
 * @author Lewi Ayun, Mark He, Daniel Frye
 * Last Updated: 12/10/2019
 */
public class User {

    private String uid;
    private String name;
    private String email;

    public User(){
        //default constructor
    }

    public User(String _uid, String _name, String _email){
        this.uid = _uid;
        this.name = _name;
        this.email = _email;
    }

    //========================GETTERS========================

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    //========================SETTERS========================

    public void setUid(String _uid) {
        this.uid = _uid;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public void setEmail(String _email) {
        this.email = _email;
    }
}
