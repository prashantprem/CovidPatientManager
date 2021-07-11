package com.example.covidpatientmanager;

import com.google.gson.annotations.SerializedName;

public class PatientDetails {
    @SerializedName("patientname")
    private String patientname;
    @SerializedName("adhaar")
    private String adhaar;
    @SerializedName("covidstatus")
    private  String covidstatus;


    public PatientDetails( String covidstatus){
        this.covidstatus = covidstatus;
    }
    public PatientDetails(String patientname, String adhaar, String covidstatus){
         this.patientname = patientname;
         this.adhaar = adhaar;
         this.covidstatus = covidstatus;
    }
    public  String getName(){return patientname;}
    public  String getAdhaar(){return adhaar;}
    public  String getStatus(){return covidstatus;}
}
