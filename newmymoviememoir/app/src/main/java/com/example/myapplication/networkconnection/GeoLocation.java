package com.example.myapplication.networkconnection;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GeoLocation {
    public static void getAddress(final String locatiopnAddress, final Context context, final Handler handler){
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                ArrayList<Double> result = new ArrayList<>();
                try {
                    List addressList = geocoder.getFromLocationName(locatiopnAddress, 1);
                    if (addressList != null && addressList.size() > 0){
                        Address address = (Address) addressList.get(0);
                        StringBuilder stringBuilder = new StringBuilder();


//                        Bundle locationLL =new Bundle();
//                        stringBuilder.append(address.getLatitude()).append("\n");

//                        stringBuilder.append(address.getLongitude()).append("\n");

                        result.add(address.getLatitude());
                        result.add(address.getLongitude());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null){
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putDouble("latitude", result.get(0));
                        bundle.putDouble("longitude", result.get(1));
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}
