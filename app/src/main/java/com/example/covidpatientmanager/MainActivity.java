package com.example.covidpatientmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String str; //to store the searched patient adhaar


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialising variables
        ImageButton mSearchBtn = findViewById(R.id.btn_serachPateint);
        Button mAddPatientBtn = (Button) findViewById(R.id.btn_add);
        Button mUpdateBtn = (Button) findViewById(R.id.btn_update);
        Button mRemoveBtn = (Button) findViewById(R.id.btn_remove);
        Button mListAllBtn = (Button) findViewById(R.id.btn_listAll);

        //To search a specific patient
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText adhaar = findViewById(R.id.searchpateintText);
                str = adhaar.getText().toString();
                if (str.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter channel name!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent searchPatientIntent = new Intent(MainActivity.this, SearchPatient.class);
                    startActivity(searchPatientIntent);
                }
            }
        });
        //Add a new patient
        mAddPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPatientIntent = new Intent(MainActivity.this, AddPatient.class);
                startActivity(addPatientIntent);

            }
        });
        //Update patient details
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPatientIntent = new Intent(MainActivity.this, update.class);
                startActivity(addPatientIntent);

            }
        });
        //Remove a patient
        mRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPatientIntent = new Intent(MainActivity.this, removePatient.class);
                startActivity(addPatientIntent);

            }
        });
        //To get aal the patient details at once
        mListAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPatientIntent = new Intent(MainActivity.this, listAll.class);
                startActivity(addPatientIntent);
            }
        });


    }
}