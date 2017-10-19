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
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Дружинники");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        readFile();

        Log.d(MainActivity.LOG_TAG,"ssssstart 1");

        downloadDataList(googleMap);


        /*LatLng ll = new LatLng(55,37);
        googleMap.addMarker(new MarkerOptions().position(ll).title(name));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll,13f));*/

        /*LatLng sydney = new LatLng(50, 50);
        mMap.addMarker(new MarkerOptions().position(sydney).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,17f));*/

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

    private void downloadDataList(final GoogleMap map){
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

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Log.d(MainActivity.LOG_TAG,"countListData = "+ list.size());

                                        map.clear();


                                        for (int i = 0; i<list.size();i++){

                                            LatLng ll = new LatLng(list.get(i).getLat(),list.get(i).getLon());
                                            map.addMarker(new MarkerOptions().position(ll).title(list.get(i).getFIO() + "\n" + list.get(i).getLat() + " - " + list.get(i).getLon()));
                                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll,14f));
                                            Log.d(MainActivity.LOG_TAG,"item = " + list.get(i).getFIO());

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
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
