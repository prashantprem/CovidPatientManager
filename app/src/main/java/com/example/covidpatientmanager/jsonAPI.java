package com.example.covidpatientmanager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface  jsonAPI {

    //These handel the diffrent requests for REST API

    @POST("user/register")
     Call<PatientDetails> createPost(@Body PatientDetails patientDetails);

    @PUT("user/update/{adhaar}")
    Call<PatientDetails> updateData(@Body PatientDetails patientDetails, @Path("adhaar") String adhaar);

    @DELETE("user/delete/{adhaar}")
    Call<PatientDetails> deleteData(@Path("adhaar")String adhaar);


}

