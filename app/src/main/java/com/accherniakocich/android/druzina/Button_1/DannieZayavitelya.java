package com.accherniakocich.android.druzina.Button_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.accherniakocich.android.druzina.MainActivity;
import com.accherniakocich.android.druzina.R;
import com.accherniakocich.android.druzina.classes.Zaloba;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DannieZayavitelya extends AppCompatActivity {

    private String rayon,harakter,adress,FIOzayavitelya,gotovPisat,opisanie,kontakti,obrabotana;
    private EditText ed1,ed2,ed3,ed_sit;
    private RadioButton cb1,cb2;
    private Button b1,b2;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dannie_zayavitelya);

        init();
    }

    private void init() {
        ed1 = (EditText)findViewById(R.id.et1);
        ed2 = (EditText)findViewById(R.id.et2);
        ed3 = (EditText)findViewById(R.id.et3);
        ed_sit = (EditText)findViewById(R.id.et_sit);

        cb1 = (RadioButton) findViewById(R.id.cb1);
        cb2 = (RadioButton) findViewById(R.id.cb2);

        b1 = (Button)findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v);
            }
        });
        b2 = (Button)findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        rayon = intent.getStringExtra("rayon");
        harakter = intent.getStringExtra("harakter");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        Log.d(MainActivity.LOG_TAG,"database = " + database);
        Log.d(MainActivity.LOG_TAG,"reference = " + reference.getRef());
    }

    public void click(View v) {

        adress = ed1.getText().toString();
        FIOzayavitelya = ed3.getText().toString();

        if (cb1.isChecked()){
            gotovPisat = "да";
        }else{
            gotovPisat = "нет";
        }

        opisanie = ed_sit.getText().toString();
        kontakti = ed2.getText().toString();
        obrabotana = "не обработана";

        Zaloba zaloba = new Zaloba(harakter,adress,FIOzayavitelya,gotovPisat,opisanie,kontakti,obrabotana);

        reference.child("Заявки").child(FIOzayavitelya).setValue(zaloba);

        ed1.setText("");
        ed2.setText("");
        ed3.setText("");
        ed_sit.setText("");

        if (cb1.isChecked()){
            cb1.setChecked(false);
        }
        if (cb2.isChecked()){
            cb2.setChecked(false);
        }

        Toast.makeText(DannieZayavitelya.this,"Заявка отправлена!",Toast.LENGTH_LONG).show();

    }
}