package com.example.covidpatientmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class listAll extends AppCompatActivity {
    String URL = "https://whispering-oasis-53231.herokuapp.com/user/all";

    private Adapter mAdapter;
    List<PatientDetails> result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);

        //using listview with adpater to present all users in a list
        ListView patientListView = findViewById(R.id.list);
        mAdapter = new Adapter(this, new ArrayList<PatientDetails>());

        patientListView.setAdapter(mAdapter);

        //for new thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    result = QueryUtils.fetchPatientData(URL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.clear();
                        if(result != null && !result.isEmpty())
                        {
                            mAdapter.addAll(result);
                        }
                    }
                });
            }
        });
    }
}