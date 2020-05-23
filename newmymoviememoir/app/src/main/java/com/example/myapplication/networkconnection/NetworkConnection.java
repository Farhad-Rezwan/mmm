package com.example.myapplication.networkconnection;

import android.util.Log;

import com.example.myapplication.memoirpersoncinemacred.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnection {
    private OkHttpClient client = null;
    private String results;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public NetworkConnection() {
        client = new OkHttpClient();
    }

    private static final String BASE_URL = "http://10.0.2.2:8080/M3/web/";


    public String getMaxID(){
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

    public String addPerson(String[] details) {

        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
        java.util.Date date = null;
        try {
            date = sdf1.parse(details[3]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());


        Person person = new Person(details[0], details[1], details[2].charAt(0), sqlStartDate, details[4], details[5]);

        String maxID = getMaxID();




        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

        String studentJson = gson.toJson(person);
        String strResponse = "";
        //this is for testing, you can check how the json looks like in Logcat
        Log.i("json " , studentJson);
        final String methodPath = "rest/person/";
        RequestBody body = RequestBody.create(studentJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + methodPath).post(body).build();
        try {
            Response response= client.newCall(request).execute();
            Log.i("json " , response.getClass().getTypeName());
            strResponse= response.body().string();

            Log.i("json " , strResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;


    }




}
