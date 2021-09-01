package com.example.clock;
import android.content.Intent;
import java.io.IOException;

import android.os.Handler;
import android.view.View;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;


public class AllCountries extends AppCompatActivity {

    public ArrayList<Country> countries;
    public ArrayList<String> cToSend;
    public CountryListAdapter mAdapter;
    public CountryListAdapter mAdapterStorage;
    private Handler mainHandler = new Handler();
    ICountryDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dao = new CountryDbDAO(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_countries);
        ListView listView = (ListView) findViewById(R.id.listView1);
        MyContainer container = MyContainer.getInstance();
        if(container.allCountries.size() == 0){
            startThread();
            Toast.makeText(this, "Loading Data from Web Service!", Toast.LENGTH_LONG).show();
        }
        else{
            CountryListAdapter adapter = new CountryListAdapter (AllCountries.this, R.layout.country_adapter_layout, container.allCountries);
            mAdapter = adapter;
            mAdapterStorage = adapter;
            listView.setAdapter(adapter);
            Toast.makeText(this, "Using Already Downloaded TimeStamps!", Toast.LENGTH_LONG).show();
        }
    }
    public String parse(String line){
        String time = "";
        try {
            JSONObject object = new JSONObject(line);
            time = object.getString("timestamp");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Timestamp ts = new Timestamp(Integer.parseInt(time));

        Date date = new Date(ts.getTime());

        int h = date.getHours(), m = date.getMinutes(), s = date.getSeconds();
        String hs = Integer.toString(h), ms = Integer.toString(m), ss = Integer.toString(s);
        time = hs + " : " + ms + " : " + ss;

        return time;
    }

    public void startThread() {
        DownloadThread thread = new DownloadThread();
        thread.start();
    }

    class DownloadThread extends Thread {
        @Override
        public void run(){
            String afghanistan = getTimeFromTimezone("Asia/Kabul");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String algeria = getTimeFromTimezone("Africa/Algiers");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String argentina = getTimeFromTimezone("America/Argentina/Buenos_Aires");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String australia = getTimeFromTimezone("Australia/Brisbane");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String austria = getTimeFromTimezone("Europe/Vienna");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String azerbaijan = getTimeFromTimezone("Asia/Baku");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String bahrain = getTimeFromTimezone("Asia/Bahrain");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String bangladesh = getTimeFromTimezone("Asia/Dhaka");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String belgium = getTimeFromTimezone("Europe/Brussels");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String bhutan = getTimeFromTimezone("Asia/Thimphu");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String canada = getTimeFromTimezone("America/Cambridge_Bay");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String china = getTimeFromTimezone("Asia/Shanghai");

            Country c1 = new Country("Afghanistan", afghanistan, dao);
            Country c2 = new Country("Algeria", algeria, dao);
            Country c3 = new Country("Argentina", argentina, dao);
            Country c4 = new Country("Australia", australia, dao);
            Country c5 = new Country("Austria", austria, dao);
            Country c6 = new Country("Azerbaijan", azerbaijan, dao);
            Country c7 = new Country("Bahrain", bahrain, dao);
            Country c8 = new Country("Bangladesh", bangladesh, dao);
            Country c9 = new Country("Belgium", belgium, dao);
            Country c10 = new Country("Bhutan", bhutan, dao);
            Country c11 = new Country("Canada", canada, dao);
            Country c12 = new Country("China", china, dao);

            ArrayList<Country> countryArrayList = new ArrayList<>();
            countryArrayList.add(c1);
            countryArrayList.add(c2);
            countryArrayList.add(c3);
            countryArrayList.add(c4);
            countryArrayList.add(c5);
            countryArrayList.add(c6);
            countryArrayList.add(c7);
            countryArrayList.add(c8);
            countryArrayList.add(c9);
            countryArrayList.add(c10);
            countryArrayList.add(c11);
            countryArrayList.add(c12);

            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    countries = countryArrayList;
                    CountryListAdapter adapter = new CountryListAdapter (AllCountries.this, R.layout.country_adapter_layout, countryArrayList);
                    mAdapter = adapter;
                    mAdapterStorage = adapter;
                    ListView listView = (ListView) findViewById(R.id.listView1);
                    listView.setAdapter(adapter);
                    MyContainer container = MyContainer.getInstance();
                    container.setAllCountries(countries);

                }
            });
        }
        public String getTimeFromTimezone(String timezone){
            String time = "";
            ArrayList<Integer> times = new ArrayList<Integer>();
            try {
                String urlText = "https://api.timezonedb.com/v2.1/get-time-zone?key=TIRXM2YOFS3L&format=json&by=zone&zone=" + timezone;
                URL url = null;
                url = new URL(urlText);
                HttpURLConnection connection = null;
                connection = (HttpURLConnection) url.openConnection();
                int responseCode = connection.getResponseCode();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine = "";
                StringBuffer responseStream = new StringBuffer();
                while((inputLine = reader.readLine())!= null){
                    responseStream.append(inputLine);
                }
                reader.close();
                time = parse(responseStream.toString());
                //System.out.println(time);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return time;
        }
    }
    public void search(View view){
        EditText bar = (EditText) findViewById(R.id.search_bar);
        String name = bar.getText().toString();
        if (name.equals("")){
            mAdapter = mAdapterStorage;
        }
        else{
            Country obj = null;
            for(int i=0;i<countries.size();i++){
                if(countries.get(i).getName().equals(name)){
                    obj = countries.get(i);
                    break;
                }
            }
            if(obj!=null){
                mAdapter.clear();
                mAdapter.add(obj);
            }
            else{
                Toast.makeText(this, "Enter Exact Country Name!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void filter(View view) throws IOException {
        ListView parent = (ListView) findViewById(R.id.listView1);
        Integer c = parent.getChildCount();
        Integer count = 0;
        ArrayList<String> stringArrayList = new ArrayList<String>();
        for (Integer i = 0;i<c;i++){
            LinearLayout ll = (LinearLayout) parent.getChildAt(i);
            CheckBox checkbox = (CheckBox) ll.getChildAt(1);
            ll = (LinearLayout) ll.getChildAt(0);
            if (checkbox.isChecked()){
                TextView tvName = (TextView) ll.findViewById(R.id.textView1);
                String c_name = tvName.getText().toString();
                stringArrayList.add(c_name);
                count += 1;
            }
        }
        cToSend = stringArrayList;
        String names = "";

        for(int i = 0;i<stringArrayList.size();i++){
            names += stringArrayList.get(i) + ",";
        }

        Intent intent=new Intent(AllCountries.this,MainActivity2.class);
        intent.putExtra("test", names);
        startActivity(intent);

        //Toast.makeText(this, count.toString(), Toast.LENGTH_SHORT).show();

    }

}