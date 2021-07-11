package com.example.covidpatientmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter extends ArrayAdapter<PatientDetails> {

    public  Adapter(Context context, List<PatientDetails> patientDetails)
    {
        super(context,0,patientDetails);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Adpater help to show data in list
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false);
        }
        final PatientDetails currentPatient = getItem(position);
        assert currentPatient != null;
        // now setting the values of variables
        TextView name = listItemView.findViewById(R.id.nameData);
        TextView adhaar = listItemView.findViewById(R.id.adhaardata);
        TextView status = listItemView.findViewById(R.id.covidStatusData);
        name.setText(currentPatient.getName());
        adhaar.setText(currentPatient.getAdhaar());
        status.setText(currentPatient.getStatus());

        return listItemView;

    }
}
