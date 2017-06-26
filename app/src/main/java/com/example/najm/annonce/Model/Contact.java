package com.example.najm.annonce.Model;

/**
 * Created by najm on 04/20/2016.
 */
public class Contact {
    int id;
    String userna;
    String name;
    String pass;
    String phone;
    String address;

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    String ville;
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getAddress() {        return address;    }
    public void setAddress(String address) {        this.address = address;    }
    public void setId(int n){
        this.id=n;
    }
    public int getId(){
        return this.id;
    }
    public void setUserna(String n){
        this.userna=n;
    }
    public String  getUserna(){
        return this.userna;
    }
    public void setName(String n){
        this.name=n;
    }
    public String  getName(){
        return this.name;
    }
    public void setPass(String n){
        pass=n;
    }
    public String  getPass(){
        return pass;
    }

}
