package com.accherniakocich.android.druzina.classes;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.accherniakocich.android.druzina.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GPS_Service extends Service {

    private LocationListener listener;
    private LocationManager locationManager;

    private FirebaseDatabase database;
    private DatabaseReference reference_lat;
    private DatabaseReference reference_lon;

    private String name;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        readFile();
        database = FirebaseDatabase.getInstance();
        reference_lat = database.getReference().child("Дружинники").child(name).child("lat");
        reference_lon = database.getReference().child("Дружинники").child(name).child("lon");

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.d(MainActivity.LOG_TAG,"c = " + location);
                pdatedataOnServer(location.getLatitude(),location.getLongitude());
                Intent i = new Intent("location_update");
                i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,20000,0,listener);

    }

    private void pdatedataOnServer(double latitude, double longitude) {
        reference_lat.setValue(latitude);
        reference_lon.setValue(longitude);
    }

    private void readFile() {
        FileInputStream fin = null;
        try {
            fin = openFileInput("name.txt");
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            name = text;
        }
        catch(IOException ex) {
            //Log.d(MainActivity.LOG_TAG,"end");
        }
        finally{

            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        //Log.d(MainActivity.LOG_TAG,"name = "+name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }
}