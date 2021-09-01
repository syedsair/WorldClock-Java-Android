package com.example.clock;

import java.util.ArrayList;
import java.util.Hashtable;

public interface   ICountryDAO {
    public void save(Hashtable<String,String> attributes);
    public void save(ArrayList<Hashtable<String,String>> objects);
    public ArrayList<Hashtable<String,String>> load();
    public Hashtable<String,String> load(String id);

    public void delete(Hashtable<String, String> data);
}