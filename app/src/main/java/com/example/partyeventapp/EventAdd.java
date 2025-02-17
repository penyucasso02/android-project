package com.example.partyeventapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class EventAdd extends AppCompatActivity {

    Button saveButton,back;
    EditText getName, getDesc, getDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);
        getName = findViewById(R.id.getEventName);
        getDesc = findViewById(R.id.getEventDesc);
        getDate = findViewById(R.id.getEventDate);
        saveButton = findViewById(R.id.btnAdd);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        back = findViewById(R.id.goToEvent);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventAdd.this, EventList.class);
                startActivity(intent);
            }
        });
    }

    public void saveData(){

        String title = getName.getText().toString();
        String desc = getDesc.getText().toString();
        String datetime = getDate.getText().toString();

        DataClass dataClass = new DataClass(title, desc, datetime);

        FirebaseDatabase.getInstance().getReference("Android Tutorials").child(title).setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EventAdd.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EventAdd.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}