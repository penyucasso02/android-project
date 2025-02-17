package com.example.partyeventapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Set;

public class Settings extends AppCompatActivity {

    Button btn1,btn2,btn3,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn1 = findViewById(R.id.btnFeedback);
        btn2 = findViewById(R.id.btnAbtUs);
        btn3 = findViewById(R.id.btnLogout);
        back = findViewById(R.id.goToMenu);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, Login.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, Feedback.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, AboutUs.class);
                startActivity(intent);
            }
        });

        OrientationEventListener orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {

                int epsilion = 10;
                int leftlandscape = 90;
                int rightlandcape = 270;

                if(epsilionCheck(orientation, leftlandscape, epsilion) || epsilionCheck(orientation, rightlandcape, epsilion)){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }
            private boolean epsilionCheck(int orientation, int landscapeMode, int epsilion){
                return orientation > landscapeMode - epsilion && orientation < landscapeMode + epsilion;
            }
        };

        orientationEventListener.enable();

    }
}