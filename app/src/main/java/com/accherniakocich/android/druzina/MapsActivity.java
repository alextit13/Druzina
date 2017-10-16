package com.accherniakocich.android.druzina;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.accherniakocich.android.druzina.Button_3.Registration;
import com.accherniakocich.android.druzina.classes.Druzinnik;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.R.id.list;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    private final static String LOG_TAG = "MyLogs";

    private double lat;
    private double lon;

    private String ret = "";
    private ArrayList<Druzinnik> listDruzinniki;

    private String name;
    private Location location;

    private double [] coordinates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listDruzinniki = new ArrayList<>();
        name = readFromFile(this);
        Intent intent = getIntent();
        listDruzinniki = (ArrayList<Druzinnik>) intent.getSerializableExtra("list");
        startLocated();
    }

    private double[] startLocated() {
        coordinates = new double[2];

        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)){
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }else{
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
        }else{
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            try {
                //Log.d(LOG_TAG,"We are here =" + hereLocate(location.getLatitude(),location.getLongitude()));
                //Log.d(LOG_TAG,"latit =" + location.getLatitude() + ", long = " + location.getLongitude());

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (;;){
                                Thread.sleep(2000);
                                coordinates[0] = location.getLatitude();
                                coordinates[1] = location.getLongitude();

                                lat = location.getLatitude();
                                lon = location.getLongitude();

                                FirebaseDatabase database;
                                DatabaseReference reference_lat;
                                DatabaseReference reference_lon;

                                database = FirebaseDatabase.getInstance();
                                reference_lat = database.getReference().child("Дружинники").child("Иванов Иван Васильевич").child("lat");
                                reference_lon = database.getReference().child("Дружинники").child("Иванов Иван Васильевич").child("lon");

                                reference_lat.setValue(5);
                                reference_lon.setValue(lon);

                                Log.d(LOG_TAG,"lat =" + lat + " , lon = " + lon);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();


            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(MapsActivity.this,"Not found!",Toast.LENGTH_SHORT).show();
            }
        }
        return coordinates;
    }

    public String hereLocate(double lat, double lon){
        String curCity = "";

        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocation(lat,lon,1);
            if (addressList.size()>0){
                curCity = addressList.get(0).getLocality();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return curCity;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST_LOCATION:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(MapsActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        try {
                            Log.d(LOG_TAG,"We are here =" + hereLocate(location.getLatitude(),location.getLongitude()));
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(MapsActivity.this,"Not found!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d(LOG_TAG,"name = " + ret);
        DatabaseReference reference_lat = database.getReference().child("Дружинники").child("Иванов Иван Васильевич").child("lat");

        LatLng sydney = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        /*MarkerOptions a = new MarkerOptions()
                .position(new LatLng(50,6));
        Marker m = mMap.addMarker(a);
        m.setPosition(new LatLng(50,5));*/
    }


    private String readFromFile(Context context) {

        try {
            InputStream inputStream = context.openFileInput("name.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
