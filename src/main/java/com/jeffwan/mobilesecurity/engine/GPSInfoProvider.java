package com.jeffwan.mobilesecurity.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jeffwan on 9/1/13.
 */

// to make sure this is one instance, we don't like more listener .
public class GPSInfoProvider {

    private static GPSInfoProvider mGPSInfoProvider;
    private static LocationManager locationManager;
    private static MyListener listener;
    private static SharedPreferences sp;

    private  GPSInfoProvider() {

    }

    //here we use single instance
    public synchronized static GPSInfoProvider getInstance(Context context) {
        if (mGPSInfoProvider == null) {
            mGPSInfoProvider = new GPSInfoProvider();
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);

            //get phone location
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            List<String> names = locationManager.getAllProviders();
            for (String name :names) {
                System.out.println(name);
            }

            // define GPS search condition
            Criteria criteria = new Criteria();
            // set Accuracy
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            // set Cost which may generate
            criteria.setCostAllowed(true);
            // set Power
            criteria.setPowerRequirement(Criteria.POWER_HIGH);

            String provider = locationManager.getBestProvider(criteria,true);
            System.out.println("Best Provider" + provider);

            if (!TextUtils.isEmpty(provider)) {
                listener = mGPSInfoProvider.new MyListener();
                locationManager.requestLocationUpdates(provider,0,0,listener);
            } else {
                //TODO: ask user to open location provider
                Toast.makeText(context.getApplicationContext(),"No location provider avaliable",1).show();
            }

        }

        return  mGPSInfoProvider;
    };


    // stop listen on location
    public void unRegistListener() {
        if (listener!=null) {
            locationManager.removeUpdates(listener);
        }
    }


    // get last location
    public String getLastLocation(){
        return sp.getString("lastLocation","");
    }


    private class MyListener implements LocationListener {

        // when location changes use
        @Override
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double accruracy = location.getAccuracy();
            String loaction = "longitude: " + longitude + ", latitude: " + latitude + ", accruracy: " + accruracy;
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("lastLocation", loaction);
            editor.commit();
        }


        // when status provider changes use
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        // when some provider be enabled
        @Override
        public void onProviderEnabled(String s) {

        }

        // when some provider be disabled
        @Override
        public void onProviderDisabled(String s) {

        }
    }

}
