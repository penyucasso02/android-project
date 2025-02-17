package com.example.partyeventapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class EventCheckLocation extends AppCompatActivity {

    Button back, checklocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_check_location);

        back = findViewById(R.id.goToEvent);
        checklocation = findViewById(R.id.goToEvent);
    }
}