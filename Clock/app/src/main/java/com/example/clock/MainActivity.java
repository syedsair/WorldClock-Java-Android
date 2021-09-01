package com.example.clock;

import android.annotation.SuppressLint;
import android.view.View;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    public ArrayList<Country> countries;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goToAllCountries(View view) {
        Intent intent = new Intent(MainActivity.this, AllCountries.class);
        startActivity(intent);
    }
}