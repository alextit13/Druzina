package com.accherniakocich.android.druzina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Kabinet extends AppCompatActivity {

    private Button sos,btn_zafiksirovali;
    private ListView listView;

    private ArrayAdapter<String> adapter;
    private ArrayList <String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabinet2);

        init();
    }

    private void init() {
        sos = (Button) findViewById(R.id.btn_sos);
        btn_zafiksirovali = (Button) findViewById(R.id.narushenie_zafiksirovano);
        listView = (ListView) findViewById(R.id.list_view);

        list = new ArrayList<>();

        for (int i = 0; i<50;i++){
            String nar = "Нарушение_" + i;
            list.add(nar);
        }

        adapter = new ArrayAdapter<>(Kabinet.this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
    }
}
