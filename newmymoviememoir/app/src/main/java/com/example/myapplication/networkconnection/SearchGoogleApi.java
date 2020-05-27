package com.example.myapplication.networkconnection;

import android.util.Log;

import com.example.myapplication.memoirpersoncinemacred.MovieSearch;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchGoogleApi {
    private static final String TAG = "SearchGoogleApi";

    private static final String API_KEY = "AIzaSyBtHiGy5osty_oXxr1PDdVm2CdvQN0RIU0";
    private static final String SEARCH_ID_cx = "010591429130415407485:7ueotli3jon";

    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";
        if (params!=null && values!=null){
            for (int i =0; i < params.length; i ++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key="+ API_KEY+ "&cx="+
                    SEARCH_ID_cx + "&q="+ keyword + query_parameter);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }


    public static List<MovieSearch> getObjects(String res) {

        List<MovieSearch> result = new ArrayList<MovieSearch>();


        try{
            JSONObject jsonObject = new JSONObject(res);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                JSONObject pagemap = item.getJSONObject("pagemap");
                JSONArray cse_thumbnails  = pagemap.getJSONArray("cse_thumbnail");
                JSONObject cse_thumbnail = cse_thumbnails.getJSONObject(0);
                String url = cse_thumbnail.getString("src");

                JSONArray metatags = pagemap.getJSONArray("metatags");
                JSONObject metatag = metatags.getJSONObject(0);
                String title = metatag.getString("og:title");


                String year = title.split("[\\(\\)]")[1];
                String tit =  title.split("[\\(\\)]")[0];

                result.add(new MovieSearch(tit, year, url));

                Log.d(TAG, "getObjects: " + url + tit + year);



            }
            if(jsonArray != null && jsonArray.length() > 0) {
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return result;

    }









    public static String getSnippet(String result){
        String snippet = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                snippet =jsonArray.getJSONObject(0).getString("snippet");
            }
        }catch (Exception e){ e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet; }
}
