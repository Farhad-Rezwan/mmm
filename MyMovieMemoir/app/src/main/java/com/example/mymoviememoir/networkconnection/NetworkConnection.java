package com.example.mymoviememoir.networkconnection;

import android.util.Log;

import com.example.mymoviememoir.mmmjava.Person;
import com.google.gson.Gson;

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
//    private String results;
    public static final MediaType JSON = MediaType.parse("application/json; charset = utf-8");

    public NetworkConnection() {
        client=new OkHttpClient ();
    }

    private static final String BASE_URL = "http://10.0.2.2:8080/M3/web/rest/";




    public String addPerson(String[] params) throws ParseException {

//        public Person(String firstname, String surname, Character gender, Date dob, String address, String stateau, int postcode);

        String sDate1="12/31/1998";
        Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);
        System.out.println(sDate1+"\t"+date1);

        Person person = new Person(params[1], params[2], params[4].charAt(0), date1, params[5], params[6], Integer.parseInt(params[7]));
        Gson gson = new Gson();
        String studentJson = gson.toJson(person);
        String strResponse = "";
        //this is for testing, you can check how the json looks like in Logcat
        Log.i("json " , studentJson);
        final String methodPath = "person/";
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
