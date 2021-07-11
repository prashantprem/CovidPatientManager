package com.example.covidpatientmanager;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public final class QueryUtils {


    private QueryUtils() {
    }



    /**
     * Returns new URL object from the given string URL.
     */
    public static URL createUrl(String stringUrl) throws MalformedURLException {
        URL url =null;
        url= new URL(stringUrl);
        return url;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //making http connection
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse ="";
        if(url == null){
            return jsonResponse;
        }
        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(null, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(null, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
   //     Query the API dataset and return a list of Patientdetails objects.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List<PatientDetails> fetchPatientData(String requestUrl) throws MalformedURLException {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(null, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of patients
        List<PatientDetails> patients = extractFeatureFromJson(jsonResponse);

        // Return the list of patients
        return patients;
    }


    /**
     * Return a list of patient objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<PatientDetails> extractFeatureFromJson(String patientJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(patientJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding patients to
        List<PatientDetails> Patients = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(patientJSON);

            // Extract the JSONArray associated with the key called "data",
            // which represents a list of features
            JSONArray patientArray = baseJsonResponse.getJSONArray("data");

            // For each patient in the patientArray, create an patient object
            for (int i = 0; i < patientArray.length(); i++) {

                // Get a single patient at position i within the list of patients
                JSONObject currentPatient = patientArray.getJSONObject(i);

                // For a given patient, extract the JSONObject associated with the
                // key called "properties", which represents a list of all properties
                String adhaar = currentPatient.getString("adhaar");
                String patientName = currentPatient.getString("patientname");
                String covidStatus = currentPatient.getString("covidstatus");
                PatientDetails details = new PatientDetails(patientName,adhaar,covidStatus);
                Patients.add(details);


            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the API JSON results", e);
        }
        return Patients;
    }


}