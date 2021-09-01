package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {
    public ArrayList<Country> selected_countries = new ArrayList<Country>();
    ICountryDAO dao;
    private Handler mainHandler = new Handler();
    private ListView listview;
    public boolean threadCheck;
    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dao = new CountryDbDAO(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String names = intent.getStringExtra("test");
        String[] array = names.split(",");
        Integer x = array.length;
        ArrayList<String> arr = new ArrayList<String>();
        for(int i=0;i<x;i++){
            arr.add(array[i]);
        }

        ListView listView = (ListView) findViewById(R.id.listView1);


        MyContainer container = MyContainer.getInstance();
        ArrayList<Country> countryArrayList = container.allCountries;

        ArrayList<Country> c_list = new ArrayList<Country>();
        for(int i=0;i<arr.size();i++){
            for(int j=0;j<countryArrayList.size();j++){
                if(countryArrayList.get(j).getName().equals(arr.get(i))){
                    c_list.add(countryArrayList.get(j));
                    break;
                }
            }
        }

        for(int i=0;i<c_list.size();i++){
            if(!container.is_present(c_list.get(i).getName())){
                container.countries.add(c_list.get(i));
            }
        }

        container.save_all(dao);
        container.load_all(dao);

        System.out.println(container.countries.size());

        Main2_Adapter adapter = new Main2_Adapter (this, R.layout.main2_layout, container.countries);
        listView.setAdapter(adapter);
        listview = listView;
        startThread();
    }

    @Override
    protected void onStop() {
        super.onStop();
        threadCheck = false;
    }

    public void goToAllCountries(View view) {
        Intent intent = new Intent(MainActivity2.this, AllCountries.class);
        startActivity(intent);
    }
    public void remove(View view) {
        ListView parent = (ListView) findViewById(R.id.listView1);
        Integer c = parent.getChildCount();
        Integer count = 0;
        ArrayList<String> stringArrayList = new ArrayList<String>();
        for (Integer i = 0;i<c;i++){
            LinearLayout ll = (LinearLayout) parent.getChildAt(i);
            CheckBox checkbox = (CheckBox) ll.getChildAt(1);
            ll = (LinearLayout) ll.getChildAt(0);
            if (checkbox.isChecked()){
                TextView tvName = (TextView) ll.findViewById(R.id.textView);
                String c_name = tvName.getText().toString();
                stringArrayList.add(c_name);
                count += 1;
            }
        }
        MyContainer container = MyContainer.getInstance();
        container.remove_all(stringArrayList, dao);
        container.Remove(stringArrayList);
        finish();
        startActivity(getIntent());
    }
    public void startThread() {
        threadCheck = true;
        UpdateTimeThread thread = new UpdateTimeThread();
        thread.start();
    }

    class UpdateTimeThread extends Thread {
        @Override
        public void run() {
            while (threadCheck){
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(listview != null){
                            for(int i=0;i<listview.getChildCount();i++){
                                TextView tv = listview.getChildAt(i).findViewById(R.id.textView3);
                                CharSequence Time = tv.getText();
                                String time = Time.toString();
                                String[] times = time.split(":");
                                times[0] = times[0].substring(0, times[0].length()-1);
                                times[1] = times[1].substring(1, times[1].length()-1);
                                times[2] = times[2].substring(1, times[2].length());


                                Integer hours = Integer.parseInt(times[0]);
                                Integer minutes = Integer.parseInt(times[1]);
                                Integer seconds = Integer.parseInt(times[2]);

                                if(seconds < 60){
                                    seconds += 1;
                                }
                                else if (seconds == 60) {
                                    seconds = 0;

                                    if (minutes < 60) {
                                        minutes += 1;
                                    }
                                    else if (minutes == 60) {
                                        minutes = 0;
                                        if (hours == 24){
                                            hours = 0;
                                        }
                                        else {
                                            hours += 1;
                                        }
                                    }
                                }

                                String updatedTime = hours.toString() + " : " + minutes.toString() + " : " + seconds.toString();
                                tv.setText(updatedTime);
                                //System.out.println(hours);
                            }
                        }
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

class MyContainer
{
    private static MyContainer single_instance = null;
    public ArrayList<Country> countries;
    public ArrayList<Country> allCountries;

    public MyContainer()
    {
        countries = new ArrayList<Country>();
        allCountries = new ArrayList<Country>();
    }
    public static MyContainer getInstance() {
        if (single_instance == null)
            single_instance = new MyContainer();
        return single_instance;
    }
    public void setAllCountries(ArrayList<Country> allcountries){
        allCountries = allcountries;
    }
    public void save_all(ICountryDAO dao){
        for(int i=0;i<countries.size();i++){
            if(dao.load(countries.get(i).getName()) == null){
                countries.get(i).save(dao);
            }
        }
    }
    public void remove_all(ArrayList<String> arr, ICountryDAO dao){
        for(int i=0;i<countries.size();i++){
            if(arr.contains(countries.get(i).getName())){
                countries.get(i).delete(dao);
            }
        }
        // load_all(dao);
    }
    public void Remove(ArrayList<String> arr){
        ArrayList<Country> temp = new ArrayList<Country>();
        for(int j=0;j<countries.size();j++){
            if(arr.contains(countries.get(j).getName())){

            }
            else{
                temp.add(countries.get(j));
            }
        }
        countries.clear();
        countries = temp;
    }
    public void load_all(ICountryDAO dao){
        ArrayList<Hashtable<String, String>> objects = dao.load();
        System.out.println(objects);
        countries.clear();
        for (Hashtable<String, String> obj : objects) {
            Country c = new Country();
            c.setName(obj.get("name"));
            c.setTime(obj.get("time"));

            countries.add(c);
        }
    }
    public boolean is_present(String name){
        for(int i=0;i<countries.size();i++){
            if(countries.get(i).getName() == name){
                return true;
            }
        }
        return false;
    }
}

