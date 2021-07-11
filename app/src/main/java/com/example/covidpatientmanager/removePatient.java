package com.example.covidpatientmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class removePatient extends AppCompatActivity {
    private EditText mEditText;
    private Button mbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_patient);
        mEditText = findViewById(R.id.removepatient);
        mbtn = findViewById(R.id.btn_remove_final);

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData(mEditText.getText().toString());
            }
        });
    }



    private void deleteData( String adhaar) {

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://whispering-oasis-53231.herokuapp.com/")

                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())

                // at last we are building our retrofit builder.
                .build();

        // below the line is to create an instance for our retrofit api class.
        jsonAPI retrofitAPI = retrofit.create(jsonAPI.class);

        // passing data from our text fields to our modal class.

        // calling a method to create an update and passing our modal class.
        Call<PatientDetails> call = retrofitAPI.deleteData(adhaar);

        call.enqueue(new Callback<PatientDetails>() {
            @Override
            public void onResponse(Call<PatientDetails> call, Response<PatientDetails> response) {
                Toast.makeText(removePatient.this, "Data Removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PatientDetails> call, Throwable t) {

            }
        });

    }

}