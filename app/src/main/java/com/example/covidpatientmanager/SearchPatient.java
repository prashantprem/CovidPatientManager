package com.example.covidpatientmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchPatient extends AppCompatActivity {
    //variables
    String patientURL = "https://whispering-oasis-53231.herokuapp.com/user/"+MainActivity.str;
    String jsonResponse = null;
    JSONObject detailedJsonObject = null;
    String mName,mAdhaar,mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_patient);

        //initialising variables

        final TextView adhaarTextview = findViewById(R.id.adhaardata);
        final TextView nameTextView = findViewById(R.id.nameData);
        final TextView covidStatusTextView = findViewById(R.id.covidStatusData);

        //Using execuotr,handler for a new thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                URL url = null;
                try {
                    url = QueryUtils.createUrl(patientURL);
                }
                catch (MalformedURLException e){
                    e.printStackTrace();
                }
                try {
                    jsonResponse = QueryUtils.makeHttpRequest(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    detailedJsonObject = new JSONObject(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject fullDetail = null; // in order to store jsonobject
                try {
                    fullDetail = detailedJsonObject.getJSONObject("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    //getting the required details from json
                    mName = fullDetail.getString("patientname");
                    mAdhaar = fullDetail.getString("adhaar");
                    mStatus = fullDetail.getString("covidstatus");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adhaarTextview.setText(mAdhaar);
                        nameTextView.setText(mName);
                        covidStatusTextView.setText(mStatus);

                    }
                });
            }
        });



    }
}