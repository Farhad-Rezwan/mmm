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


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public String addPerson(String[] details){

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
        try {
            Response response= client.newCall(request).execute();
            Log.i("json " , response.getClass().getTypeName());
            strResponse= response.body().string();

            Log.i("json " , strResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }


        person.setPersonid(personid);

        String plaintext = details[9];
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


        java.sql.Date curSignUpDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());



        Credential credential = new Credential(details[8], hashtext, curSignUpDate);
        credential.setPersonid(person);

        String credentialJson = gson.toJson(credential);
        //this is for testing, you can check how the json looks like in Logcat
        Log.i("json " , credentialJson);
        final String methodPathCred = "rest/credential/";
        RequestBody body2 = RequestBody.create(credentialJson, JSON);
        Request request2 = new Request.Builder().url(BASE_URL + methodPathCred).post(body2).build();
        try {
            Response response= client.newCall(request2).execute();
            Log.i("json " , response.getClass().getTypeName());
            strResponse= response.body().string();

            Log.i("json " , strResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return strResponse;


    }




}
