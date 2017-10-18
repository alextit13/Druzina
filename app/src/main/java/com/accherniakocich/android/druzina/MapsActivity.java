package com.accherniakocich.android.druzina;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.accherniakocich.android.druzina.classes.Druzinnik;
import com.accherniakocich.android.druzina.classes.Zaloba;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String name;
    private long countListData;
    private ChildEventListener mChildEventListeber;
    private ArrayList<Druzinnik> list;
    private Druzinnik druzinnik;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Дружинники");



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        readFile();
        downloadDataList();
        mMap = googleMap;

        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        final MarkerOptions mo = new MarkerOptions().title(name);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(MainActivity.LOG_TAG,"change cancelled");
                downloadDataList();

                for (int i = 0;i<list.size();i++){
                    Log.d(MainActivity.LOG_TAG,"item: lat = " + list.get(i).getLat() + ", lon = " + list.get(i).getLon());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(MainActivity.LOG_TAG,"change cancelled");
            }
        });
        */

        LatLng sydney = new LatLng(list.get(0).getLat(), list.get(0).getLon());
        mMap.addMarker(new MarkerOptions().position(sydney).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Add a marker in Sydney and move the camera

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

    private void downloadDataList(){
        list = new ArrayList<>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                countListData = dataSnapshot.getChildrenCount();

                if (mChildEventListeber == null){
                    mChildEventListeber = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            druzinnik = dataSnapshot.getValue(Druzinnik.class);
                            list.add(druzinnik);

                            if (list.size()==countListData){
                                //confirmList();

                                Log.d(MainActivity.LOG_TAG,"countListData = "+ list.size());

                                for (int i = 0; i<list.size();i++){
                                    Log.d(MainActivity.LOG_TAG,"item = " + list.get(i).getFIO());
                                }
                                /*progress_bar.setVisibility(View.GONE);
                                container.setAlpha(1f);
                                find_button.setEnabled(true);*/
                                // если лист пустой то тут все кодим
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    reference.addChildEventListener(mChildEventListeber);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
