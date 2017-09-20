package com.example.abhishek.zomatowebservice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null) {

            getSupportFragmentManager().beginTransaction().add(R.id.container1,
                    new MyFragment()).addToBackStack(null).commit();
        }


    }
}
