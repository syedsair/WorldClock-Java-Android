package com.example.clock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class CountryDbDAO implements ICountryDAO {

    private Context context;

    public CountryDbDAO(Context ctx){
        context = ctx;
    }

    @Override
    public void save(Hashtable<String, String> attributes) {
        CountryDbHelper dbHelper = new CountryDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        Enumeration<String> keys = attributes.keys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            content.put(key,attributes.get(key));
            // System.out.println(key + " : " + attributes.get(key));
        }

        db.insert("Countries",null,content);
    }
    @Override
    public void delete(Hashtable<String, String> attributes) {
        CountryDbHelper dbHelper = new CountryDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        Enumeration<String> keys = attributes.keys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            content.put(key,attributes.get(key));
            // System.out.println(key + " : " + attributes.get(key));
        }
        db.delete("Countries","name=?",new String[]{attributes.get("Name")});
        System.out.println(attributes.get("Name"));
    }

    @Override
    public void save(ArrayList<Hashtable<String, String>> objects) {
        for(Hashtable<String,String> obj : objects){
            save(obj);
        }
    }

    @Override
    public ArrayList<Hashtable<String, String>> load() {
        CountryDbHelper dbHelper = new CountryDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM Countries";
        Cursor cursor = db.rawQuery(query,null);

        ArrayList<Hashtable<String,String>> objects = new ArrayList<Hashtable<String, String>>();
        while(cursor.moveToNext()){
            Hashtable<String,String> obj = new Hashtable<String, String>();
            String [] columns = cursor.getColumnNames();
            for(String col : columns){
                obj.put(col.toLowerCase(),cursor.getString(cursor.getColumnIndex(col)));
            }
            objects.add(obj);
        }
        return objects;
    }

    @Override
    public Hashtable<String, String> load(String name) {
        ArrayList<Hashtable<String, String>> objects = load();
        for(int i = 0;i<objects.size();i++){
            if(objects.get(i).containsValue(name)){
                return objects.get(i);
            }
        }
        return null;
    }
}