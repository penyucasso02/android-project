package com.example.partyeventapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView profilePic, coverPic;
    TextView names, emails, phones;
    AlertDialog dialog, dialog1;
    FloatingActionButton updateProfile;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_REQUEST_CODE = 400;

    String cameraPermissions[];
    String storagePermissions[];

    Button back, btnEditProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        back = findViewById(R.id.goToMenu);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        profilePic = findViewById(R.id.iconProfile);
        names = findViewById(R.id.name);
        phones = findViewById(R.id.email);
        emails = findViewById(R.id.phone);
        //pd = new ProgressDialog(getApplication());

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for (DataSnapshot ds : datasnapshot.getChildren()) {
                    String name = "" + ds.child("name").getValue();
                    String email = "" + ds.child("email").getValue();
                    String phone = "" + ds.child("phone").getValue();
                    String image = "" + ds.child("image").getValue();
                    String cover = "" + ds.child("cover").getValue();

                    names.setText(name);
                    emails.setText(email);
                    phones.setText(phone);
                    try {
                        Picasso.get().load(image).into(profilePic);
                    } catch (Exception e) {

                    }

                    try {
                        Picasso.get().load(cover).into(coverPic);
                    } catch (Exception e) {

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Data");
        View view = getLayoutInflater().inflate(R.layout.fragment_update, null);
        builder.setView(view);
        dialog = builder.create();

        Button profileUpload = view.findViewById(R.id.buttonUpload);
        Button saveButton = view.findViewById(R.id.buttonSave);
        Button cancelButton = view.findViewById(R.id.buttonBack);
        EditText nameUpdate = view.findViewById(R.id.editTextTextNameUpdate);
        EditText phoneUpdate = view.findViewById(R.id.editTextTextPhoneNumber);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Select Method");
        View view1 = getLayoutInflater().inflate(R.layout.fragment_media, null);
        builder1.setView(view1);
        dialog1 = builder1.create();
        Button useCamera = view1.findViewById(R.id.camera);
        Button useStorage = view1.findViewById(R.id.storage);

        profileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.show();
                useCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameUpdate.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("name", name);

                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplication(), "Update...", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplication(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }

                String phone = phoneUpdate.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    HashMap<String, Object> result1 = new HashMap<>();
                    result1.put("phone", phone);

                    databaseReference.child(user.getUid()).updateChildren(result1)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    //Toast.makeText(getApplication(), "Updated...", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Toast.makeText(getApplication(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }


                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


    }

}