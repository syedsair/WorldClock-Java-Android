package com.example.clock;

import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;



class Country implements Serializable {
    String Name;
    String Time;
    private transient ICountryDAO dao = null;

    public Country() {

    }
    public void setTime(String time){
        Time = time;
    }

    public Country(String name,String time, ICountryDAO dao) {
        Name = name;
        this.Time = time;
        this.dao = dao;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDAO(ICountryDAO d) {
        dao = d;
    }



    public Country(String Name ,ICountryDAO dao){
        this.dao = dao;
    }


    public void save(ICountryDAO dao){
        if (dao != null){
            Hashtable<String,String> data = new Hashtable<String, String>();
            data.put("Name",getName());
            data.put("Time",this.Time);

            dao.save(data);
        }
        else{
            System.out.println("_______________________________________________________________________________");
        }
    }
    public void delete(ICountryDAO dao){
        if (dao != null){
            Hashtable<String,String> data = new Hashtable<String, String>();
            data.put("Name",getName());
            data.put("Time",this.Time);

            dao.delete(data);
        }
        else{
            System.out.println("_______________________________________________________________________________");
        }
    }
    public void load(Hashtable<String,String> data){
        Name = data.get("Name");
        Time = data.get("Time");
    }
    public static ArrayList<Country> load(ICountryDAO dao){

        ArrayList<Country> countriess = new ArrayList<Country>();
        if(dao != null) {
            ArrayList<Hashtable<String, String>> objects = dao.load();
            for (Hashtable<String, String> obj : objects) {
                Country c = new Country("", dao);
                c.load(obj);
                countriess.add(c);
            }
        }

        return countriess;
    }
}