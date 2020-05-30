package com.example.myapplication.networkconnection;

import android.util.Log;

import com.example.myapplication.memoirpersoncinemacred.Credential;
import com.example.myapplication.memoirpersoncinemacred.Person;
import com.example.myapplication.save.PersonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnection {


    private static final String TAG = "Network Connection";

    private OkHttpClient client = null;
    private String results;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public NetworkConnection() {
        client = new OkHttpClient();
    }

    private static final String BASE_URL = "http://10.0.2.2:8080/M3/web/";


    public String getMaxID(){
        results = "";
        final String methodPath = "rest/person/maxID";
        Request.Builder builder = new Request.Builder(); builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();
        }catch (Exception e){ e.printStackTrace();
        }
        return results;
    }

    public String addPerson(String[] details){

        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
            Date date = null;
            java.sql.Date sqlStartDate = null;



            date = sdf1.parse(details[4]);
            sqlStartDate = new java.sql.Date(date.getTime());




            String maxID;
            maxID = getMaxID();
            Log.i("String maxid", "maxid: " + maxID);


            JsonElement jelement = new JsonParser().parse(maxID);
            JsonArray jarray = jelement.getAsJsonArray();
            JsonObject jobject = jarray.get(0).getAsJsonObject();
            maxID = jobject.get("ID").getAsString();


            Log.i("String maxid", "maxid: " + maxID);

            int postcode = Integer.parseInt(details[7]);
            int personid = Integer.parseInt(maxID) + 1;

            Person person = new Person(details[1], details[2], details[3].charAt(0), sqlStartDate, details[5], details[6], postcode);




            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

            String personJson = gson.toJson(person);
            String strResponse = "";
            //this is for testing, you can check how the json looks like in Logcat
            Log.i("json " , personJson);
            final String methodPath = "rest/person/";
            RequestBody body = RequestBody.create(personJson, JSON);
            Request request = new Request.Builder().url(BASE_URL + methodPath).post(body).build();



            Response response= client.newCall(request).execute();
            Log.i("json " , response.getClass().getTypeName());
            strResponse= response.body().string();

            Log.i("json " , strResponse);





            person.setPersonid(personid);

            String plaintext = details[9];
            MessageDigest m = null;
            m = MessageDigest.getInstance("MD5");




            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            String hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while(hashtext.length() < 32 ){
                hashtext = "0"+hashtext;
            }



            java.sql.Date curSignUpDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());



            Credential credential = new Credential(details[8], hashtext, curSignUpDate);
            credential.setPersonid(person);

            String credentialJson = gson.toJson(credential);
            //this is for testing, you can check how the json looks like in Logcat
            Log.i("json " , credentialJson);
            final String methodPathCred = "rest/credential/";
            RequestBody body2 = RequestBody.create(credentialJson, JSON);
            Request request2 = new Request.Builder().url(BASE_URL + methodPathCred).post(body2).build();


            Response response2= client.newCall(request2).execute();
            Log.i("json " , response2.getClass().getTypeName());
            strResponse= response2.body().string();

            Log.i("json " , strResponse);


//            public Person(int personid, String firstname, String surname, Character gender, Date dob, String address, String stateau, int postcode)
//            Person personToStore = new Person(personid, details[1], details[2], details[3].charAt(0), sqlStartDate, details[5], details[6], postcode);
//
//            PersonObject personObject = new PersonObject();
//            personObject.setPerson(personToStore);



            // checking username and password maches already or not.
            String[] params = {credential.getUsername(), details[9]};
            results = credentialCheck(params);


        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return results ;


    }



    public String getAllEmails(){
        final String methodPath = "rest/credential/credentials/";
        Request.Builder builder = new Request.Builder(); builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();
        }catch (Exception e){ e.printStackTrace();
        }
        return results;
    }


    public String moviesWatchPerSuburb(String[] details) {

        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
            Date date1, date2 = null;
            java.sql.Date sqlStartDate, sqlEndDate = null;
            date1 = sdf1.parse(details[0]);
            date2 = sdf1.parse(details[1]);
            sqlStartDate = new java.sql.Date(date1.getTime());
            sqlEndDate = new java.sql.Date(date2.getTime());

            final String methodPath = "rest/memoir/findCSuburbByPersonIDandDateRange/" + 1 + "/" + sqlStartDate + "/" + sqlEndDate;
            Log.d(TAG, "moviesWatchPerSuburb: " + methodPath);
            Request.Builder builder = new Request.Builder(); builder.url(BASE_URL + methodPath);
            Request request = builder.build();

            Response response = client.newCall(request).execute();
            results=response.body().string();











        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return results;
    }

    public String moviesWatchedPerMonth(String[] details) {
        try {
            final String methodPath = "rest/memoir/findMovieWachedPerMonthGivenPersonIDandYear/" + 1 + "/" + details[0];
            Log.d(TAG, "moviesWatchedPerMonth: " + methodPath);
            Request.Builder builder = new Request.Builder(); builder.url(BASE_URL + methodPath);
            Request request = builder.build();

            Response response = client.newCall(request).execute();
            results=response.body().string();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

    public String credentialCheck(String[] params) {

        try {
            String plaintext = params[1];
            MessageDigest m = null;
            try {
                m = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }


            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            String hashtext = bigInt.toString(16);
// Now we need to zero pad it if you actually want the full 32 chars.
            while(hashtext.length() < 32 ){
                hashtext = "0"+hashtext;
            }






            final String methodPath2 = "rest/credential/crede/" + params[0] + "/" + hashtext;
            Log.d(TAG, "validateUserName: " + methodPath2);
            Request.Builder builder = new Request.Builder(); builder.url(BASE_URL + methodPath2);
            Request request = builder.build();

            Response response = client.newCall(request).execute();
            Log.d(TAG, "credentialCheck: " + response);
            results=response.body().string();
            Log.d(TAG, "validateUserName: " + results);

            JsonElement jelement = new JsonParser().parse(results);
            JsonArray jarray = jelement.getAsJsonArray();
            int x = jarray.size();




//            x = 0 means not found
            if (x == 1){
                JsonObject jobject = jarray.get(0).getAsJsonObject();


                Log.d(TAG, "credentialCheck: " + x);


                String personidString = jobject.get("ID").getAsString();

                final String methodPath = "rest/person/" + personidString;
                Log.d(TAG, "validateUserName: " + personidString);
                Request.Builder builder2 = new Request.Builder(); builder.url(BASE_URL + methodPath);
                Request request2 = builder.build();

                Response response2 = client.newCall(request2).execute();
                Log.d(TAG, "credentialCheck: " + response2);
                results=response2.body().string();
                Log.d(TAG, "validateUserName: " + results);

            } else {
                results = null;
            }




        } catch (IOException e) {
            e.printStackTrace();
            results = null;
        }


        return results;
    }


    public String getAllCinemaNameSuburb(String[] params) {

        try {
            final String methodPath = "rest/cinema/" + params[0];
            Log.d(TAG, "getAllCinemaNameSuburb: " + methodPath);
            Request.Builder builder = new Request.Builder(); builder.url(BASE_URL + methodPath);
            Request request = builder.build();

            Response response = client.newCall(request).execute();
            results=response.body().string();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }




}
