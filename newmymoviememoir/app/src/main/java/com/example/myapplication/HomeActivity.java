package com.example.myapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;

import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.MovieViewFragment;
import com.example.myapplication.fragment.MovieSearchFragment;
import com.example.myapplication.memoirpersoncinemacred.Cinema;
import com.example.myapplication.memoirpersoncinemacred.Person;
import com.example.myapplication.networkconnection.GeoLocation;
import com.example.myapplication.networkconnection.NetworkConnection;
import com.example.myapplication.save.PersonObject;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private PersonObject personObject;
    MapBlankFragment mfragment = new MapBlankFragment();
    private static final String TAG = "HomeActivity";

    NetworkConnection networkConnection = null;
    Double tempCinemalat, tempCinemaLon;
    ArrayList<Cinema> cinemaArrayList;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        personObject = (PersonObject) this.getApplicationContext();
        cinemaArrayList = new ArrayList<Cinema>();

        networkConnection = new NetworkConnection();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nv);


        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.Open,R.string.Close); drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //these two lines of code show the navicon drawer icon top left hand side
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); getSupportActionBar().setHomeButtonEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        getUserGeolocation();


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addMessage:
                replaceFragment(new MovieSearchFragment());
                break;
            case R.id.displayMessage:
                replaceFragment(new MovieViewFragment());
                break;
            case R.id.report:
                replaceFragment(new MovieViewFragment());
                break;
            case R.id.map_2:
                
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, mfragment);
                fragmentTransaction.commit();
//                getCinemaGeolocation();
                break;
        }
        //this code closes the drawer after you selected an item from the menu,otherwise stay open
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment);
        fragmentTransaction.commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
        return true;
        return super.onOptionsItemSelected(item);
    }



//    maps
    private void getUserGeolocation() {
//        Person person = new Person (1,"Mr Warda", "Mr Warda", "Caulfield", "VIC" , 3174);

        String address = personObject.getPerson().getAddress();
        GeoLocation geoLocation = new GeoLocation();
        geoLocation.getAddress(address, getApplicationContext(),new GeoHandler());


    }
    private void getCinemaGeolocation() {
        String[] methodName = {"getAllCinemaNameAndSuburb"};
        GetCinemaSuburbTask getCinemaSuburb = new GetCinemaSuburbTask();
        getCinemaSuburb.execute(methodName);

    }


    private class GetCinemaSuburbTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "task name: " + params[0];
            Log.d(TAG, "doInBackground: " + result);
            return networkConnection.getAllCinemaNameSuburb(params);
        }

        @Override
        protected void onPostExecute(String result) {

            ArrayList<Cinema> cinemas = new ArrayList<>();

            JsonArray jsonArray = (JsonArray) new JsonParser().parse(result);

            JsonObject jsonObject;

            int arraySize = jsonArray.size();
            for (int i = 0; i < arraySize; i++) {
                jsonObject = jsonArray.get(i).getAsJsonObject();
                Log.d(TAG, "onPostExecute: Json Objects" + jsonObject);
                String cinemaname = jsonObject.get("cinemaname").getAsString();
                String cinemasuburb = jsonObject.get("cinemasuburb").getAsString();
                cinemas.add(new Cinema(cinemaname, cinemasuburb));
            }

            for(Cinema cinema: cinemas){
                GeoLocation geoLocation = new GeoLocation();
                String tempCinemaName = cinema.getCinemaname();
                String tempCinemaLocation = cinema.getCinemasuburb();
                geoLocation.getAddress(tempCinemaLocation, getApplicationContext(),new GeoHandler());

                Log.d(TAG, "onPostExecute: " + tempCinemaLocation + tempCinemaName + tempCinemalat + tempCinemaLon);

                cinemaArrayList.add(new Cinema(tempCinemaName, tempCinemaLocation, tempCinemalat, tempCinemaLon));
                tempCinemaLon = 0.00;
                tempCinemalat = 0.00;





            }



            // starting new bundle for arraylist

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, mfragment);
            fragmentTransaction.commit();


        }

    }


    private class GeoHandler extends Handler {
        @Override
        public void handleMessage (Message msg) {
            Double latitude, longitude;
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    latitude = bundle.getDouble("latitude");
                    longitude = bundle.getDouble("longitude");


                    Bundle bundle2 = new Bundle();

                    bundle2.putDouble("lat", latitude);
                    bundle2.putDouble("lng", longitude);
                    mfragment.setArguments(bundle2);


                    tempCinemalat = latitude;
                    tempCinemaLon = longitude;



                    break;
                default:
                    latitude = null;
                    longitude = null;
            }

            Log.d(TAG, "handleMessage: " + latitude + longitude);


        }
    }
}
