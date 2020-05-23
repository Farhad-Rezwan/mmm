package com.example.myapplication.networkconnection;

import android.util.Log;

import com.example.myapplication.memoirpersoncinemacred.Credential;
import com.example.myapplication.memoirpersoncinemacred.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        Date date = null;
        try {
            date = sdf1.parse(details[4]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
        String maxID;
        maxID = getMaxID();
        Log.i("String maxid", "maxid: " + maxID);

        JsonElement jelement = new JsonParser().parse(maxID);

        JsonArray jarray = jelement.getAsJsonArray();
        JsonObject jobject = jarray.get(0).getAsJsonObject();
        maxID = jobject.get("ID").getAsString();

        Log.i("String maxid", "maxid: " + maxID);


//        json.getAsString();




//        int x = Integer.parseInt(maxID) + 1;

        int postcode = Integer.parseInt(details[7]);
        int personid = Integer.parseInt(maxID) + 1;

        Person person = new Person(personid, details[1], details[2], details[3].charAt(0), sqlStartDate, details[5], details[6], postcode);
        Credential credential = new Credential(details[8], )






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
