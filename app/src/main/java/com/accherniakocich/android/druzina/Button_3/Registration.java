package com.accherniakocich.android.druzina.Button_3;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.accherniakocich.android.druzina.Kabinet;
import com.accherniakocich.android.druzina.MainActivity;
import com.accherniakocich.android.druzina.R;
import com.accherniakocich.android.druzina.classes.Druzinnik;
import com.accherniakocich.android.druzina.classes.GPS_Service;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class Registration extends Activity {

    private EditText et_1,et_2,et_3,et_4,et_5;
    private TextView na_patrule;
    private Button button_registration_zastup,button_registration_end,button_lich_kab;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private SharedPreferences sharedPreferences;
    public static final String SAVED_TEXT = "saved_druzinnik";
    SharedPreferences.Editor ed;
    private Druzinnik druzinnik;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabinet);
        init();
        if(!runtime_permissions())
            enable_buttons();


    }

    private void init() {

        sharedPreferences = getPreferences(MODE_PRIVATE);
        ed = sharedPreferences.edit();

        na_patrule = (TextView)findViewById(R.id.na_patrule);

        et_1 = (EditText)findViewById(R.id.FIODrizinnika);
        et_2 = (EditText)findViewById(R.id.rayon);
        et_3 = (EditText)findViewById(R.id.ulitsa_marshrut);
        et_4 = (EditText)findViewById(R.id.phone);
        et_5 = (EditText)findViewById(R.id.number_of_udostoverenie);
        button_lich_kab = (Button) findViewById(R.id.button_lich_kab);

        button_registration_zastup = (Button) findViewById(R.id.button_registration_zastup);

        button_registration_end = (Button) findViewById(R.id.button_registration_end);

        if (sharedPreferences.getAll().isEmpty()){
            Log.d(MainActivity.LOG_TAG,"sPref = " + sharedPreferences.getAll());
        }else{
            na_patrule.setText("На патрулировании " + sharedPreferences.getString(SAVED_TEXT,""));
        }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        if (sharedPreferences.getString(SAVED_TEXT,"").equals("")){
            button_registration_zastup.setEnabled(true);
            button_registration_end.setEnabled(false);
            button_lich_kab.setEnabled(false);
        }else{
            button_registration_zastup.setEnabled(false);
            button_registration_end.setEnabled(true);
            button_lich_kab.setEnabled(true);

        }

        button_lich_kab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Kabinet.class);
                intent.putExtra("druzinnik",sharedPreferences.getString(SAVED_TEXT,""));
                startActivity(intent);
            }
        });
    }

    private void deleteDruzinnikFromDatabase() {
        deleteDruzinnik();
    }

    private void clickButton() {



        druzinnik = new Druzinnik(et_1.getText().toString(),et_2.getText().toString(),et_3.getText().toString(),
                et_4.getText().toString(),et_5.getText().toString(),0,0);
        reference.child("Дружинники").child(et_1.getText().toString()).setValue(druzinnik);

        et_1.setText("");
        et_2.setText("");
        et_3.setText("");
        et_4.setText("");
        et_5.setText("");

        Toast.makeText(Registration.this,"Дружинник заступил на патрулирование",Toast.LENGTH_SHORT).show();

        na_patrule.setText("На патрулировании " + druzinnik.getFIO());
        saveDruzinnik(druzinnik);
        button_registration_zastup.setEnabled(false);
        button_registration_end.setEnabled(true);
        button_lich_kab.setEnabled(true);
    }

    private void saveDruzinnik(Druzinnik druzinnik){
        //ed.clear();
        ed.putString(SAVED_TEXT,druzinnik.getFIO());
        ed.commit();

        writeToFile(druzinnik.getFIO(),this);



        //setDefaults(SAVED_TEXT,druzinnik.getFIO(),this);
    }

    private void deleteDruzinnik(){

        reference.child("Дружинники").child(sharedPreferences.getString(SAVED_TEXT,"")).removeValue();

        sharedPreferences = getPreferences(MODE_PRIVATE);
        ed.clear();
        ed.commit();

        Toast.makeText(Registration.this,"Патрулирование окончено",Toast.LENGTH_SHORT).show();
        na_patrule.setText("Сейчас на патрулировании");



        button_registration_zastup.setEnabled(true);
        button_registration_end.setEnabled(false);
        button_lich_kab.setEnabled(false);
    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("name.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            //Log.d(MainActivity.LOG_TAG, "ok");
        }
        catch (IOException e) {
            Log.d(MainActivity.LOG_TAG, "File write failed: " + e.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    //na_patrule.append("\n"+intent.getExtras().get("coordinates"));
                    //Log.d(MainActivity.LOG_TAG,"coor = " + intent.getExtras().get("coordinates"));

                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    private void enable_buttons() {

        button_registration_zastup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButton();
                Intent i =new Intent(getApplicationContext(),GPS_Service.class);
                startService(i);
            }
        });

        button_registration_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDruzinnikFromDatabase();
                Intent i = new Intent(getApplicationContext(),GPS_Service.class);
                stopService(i);

            }
        });
    }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_buttons();
            }else {
                runtime_permissions();
            }
        }
    }
}
