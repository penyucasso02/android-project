package com.example.partyeventapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Feedback extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText Feedback;
    Button button, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

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

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Feedback");

        button = findViewById(R.id.btnSubmit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inpFeedback = Feedback.getText().toString().trim();
                HashMap<Object, String> result = new HashMap<>();
                result.put("feedback", inpFeedback);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Feedback");
                reference.setValue(result);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back = findViewById(R.id.goToSettings);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feedback.this, Settings.class);
                startActivity(intent);
            }
        });

    }
}