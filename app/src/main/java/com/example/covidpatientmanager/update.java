package com.example.covidpatientmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class update extends AppCompatActivity {

    private EditText madhaar,mstatus;
    private Button mbtn;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        madhaar = findViewById(R.id.adhaarForSearch);
        mstatus= findViewById(R.id.updateCovidStatus);
        mbtn = findViewById(R.id.btn_update_final);

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = madhaar.getText().toString();
                if (TextUtils.isEmpty(madhaar.getText().toString()) && TextUtils.isEmpty(mstatus.getText().toString())) {

                    // displaying a toast message if the edit text is empty.
                    Toast.makeText(update.this, "Please enter your data..", Toast.LENGTH_SHORT).show();
                    return;
                }
                callPUTDataMethod(mstatus.getText().toString(),madhaar.getText().toString());

            }
        });
    }

    private void callPUTDataMethod(String status, String adhaar) {

        // below line is for displaying our progress bar.
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
        PatientDetails modal = new PatientDetails(status);

        // calling a method to create an update and passing our modal class.
        Call<PatientDetails> call = retrofitAPI.updateData(modal,adhaar);

        call.enqueue(new Callback<PatientDetails>() {
            @Override
            public void onResponse(Call<PatientDetails> call, Response<PatientDetails> response) {
                Toast.makeText(update.this, "Data updated to API", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PatientDetails> call, Throwable t) {

            }
        });

    }
}