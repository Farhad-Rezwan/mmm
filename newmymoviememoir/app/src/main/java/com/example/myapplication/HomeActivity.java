package com.example.myapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;

import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.MovieViewFragment;
import com.example.myapplication.fragment.MovieSearchFragment;
import com.example.myapplication.memoirpersoncinemacred.Person;
import com.example.myapplication.networkconnection.GeoLocation;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
                geoLocation();
//                replaceFragment(new MapBlankFragment());
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

    private void replaceFragmentWithBundle(Fragment nextFragment) {
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
    private void geoLocation() {
        Person person = new Person (1,"Mr Warda", "Mr Warda", "Caulfield", "VIC" , 3174);

        String address = person.getAddress();
        GeoLocation geoLocation = new GeoLocation();
        geoLocation.getAddress(address, getApplicationContext(),new GeoHandler());


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
                    Bundle transferBund = new Bundle();
                    transferBund.putDouble("lat", latitude);
                    transferBund.putDouble("lng", longitude);


                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    MapBlankFragment mapFrag = new MapBlankFragment();
                    mapFrag.setArguments(transferBund);
                    fragmentTransaction.replace(R.id.content_frame, mapFrag);
                    fragmentTransaction.commit();


//                    fragmentTransaction.replace(R.id.content_frame, new MapBlankFragment());
//                    fragmentTransaction.commit();




                    break;
                default:
                    latitude = null;
                    longitude = null;
            }

            Log.d(TAG, "handleMessage: " + latitude + longitude);


        }
    }


}
