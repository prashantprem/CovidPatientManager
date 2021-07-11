package com.example.covidpatientmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class AddPatient extends AppCompatActivity {
    //variables
    private  jsonAPI apiJson;
    private EditText nameEdt,adhaarEdt,statusEdt;
    private Button sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        //inititalising variables
        nameEdt = findViewById(R.id.nameFill);
        adhaarEdt = findViewById(R.id.adhaarFill);
        statusEdt = findViewById(R.id.covidStatusFill);
        sendBtn = findViewById(R.id.btn_sendPatient);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameEdt.getText().toString().isEmpty() && adhaarEdt.getText().toString().isEmpty() && statusEdt.getText().toString().isEmpty()) {
                    Toast.makeText(AddPatient.this, "Please enter all the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                postData(nameEdt.getText().toString(),
                        adhaarEdt.getText().toString(),
                        statusEdt.getText().toString());
                //calling postdata method to send the user details to database using REST API
            }

        });



    }

    private void postData(String name, String adhaar, String status){

        //retrofit library is used to easily work with REST API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://whispering-oasis-53231.herokuapp.com/") //base url
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonAPI apiJson = retrofit.create(jsonAPI.class); //initialising apijson to use createpost method

        PatientDetails modal = new PatientDetails(name,adhaar,status);
        Call<PatientDetails> call = apiJson.createPost(modal);
        call.enqueue(new Callback<PatientDetails>() {
            @Override
            public void onResponse(Call<PatientDetails> call, Response<PatientDetails> response) {
                Toast.makeText(AddPatient.this, "Data added to API", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PatientDetails> call, Throwable t) {
                //currently doing nothing on failre
            }
        });
    }

}