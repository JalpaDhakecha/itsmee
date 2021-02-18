package com.razy.itsmee.demo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.razy.itsmee.demo.Models.data;

public class MainActivity extends AppCompatActivity {

    Utils utils;
    data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = utils.getUser();

        ((TextView) findViewById(R.id.tvNameId)).setText("Hello " + data.getUserName());

    }
}