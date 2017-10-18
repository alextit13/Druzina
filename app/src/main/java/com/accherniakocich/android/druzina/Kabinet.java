package com.accherniakocich.android.druzina;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.accherniakocich.android.druzina.Button_3.Registration;
import com.accherniakocich.android.druzina.classes.Druzinnik;
import com.accherniakocich.android.druzina.classes.Zaloba;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Kabinet extends AppCompatActivity {

    private Button sos,btn_zafiksirovali;
    private ListView listView;
    private TextView na_p;

    private SimpleAdapter simpleAdapter;
    private SharedPreferences preferences;
    private ChildEventListener mChildEventListeber;

    private ArrayList<Zaloba>listNarusheniya; // тут уже загруженные все жалобы
    private Zaloba zaloba;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private long countListData = 0;

    final String ATTRIBUTE_NAME_TEXT_1 = "text_1";
    final String ATTRIBUTE_NAME_TEXT_2 = "text_2";
    final String ATTRIBUTE_NAME_TEXT_3 = "text_3";
    final String ATTRIBUTE_NAME_TEXT_4 = "text_4";
    final String ATTRIBUTE_NAME_TEXT_5 = "text_5";
    final String ATTRIBUTE_NAME_TEXT_6 = "text_6";
    final String ATTRIBUTE_NAME_TEXT_7 = "text_7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabinet2);

        init();
    }

    private void init() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Заявки");
        listNarusheniya = new ArrayList<>();
        preferences = getPreferences(MODE_PRIVATE);
        sos = (Button) findViewById(R.id.btn_sos);
        btn_zafiksirovali = (Button) findViewById(R.id.narushenie_zafiksirovano);
        listView = (ListView) findViewById(R.id.list_view);

        na_p = (TextView)findViewById(R.id.na_p);

        downloadDataList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickItem(parent,view,position,id);
            }
        });

        btn_zafiksirovali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fiksatsia();
            }
        });


        Intent intent = getIntent();
        String druzinnikFiO = intent.getStringExtra("druzinnik");
        na_p.setText("Сейчас на патрулировании: "+druzinnikFiO);
    }

    private void fiksatsia() {
        for (int i = 0; i<listNarusheniya.size();i++){
            if (listNarusheniya.get(i).getObrabotana().equals("Обработана!")){
                Toast.makeText(this,"Заявка обработана!",Toast.LENGTH_LONG).show();
                FirebaseDatabase d = FirebaseDatabase.getInstance();
                DatabaseReference ref = d.getReference().child("Заявки").child(listNarusheniya.get(i).getFIOZayavitelya()).child("obrabotana");

                ref.setValue("Обработана!");
                listView.getChildAt(i).setBackground(null);
            }else{
                Toast.makeText(this,"Выберите заявку",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void clickItem(AdapterView<?> parent, View view, int position, long id) {
        if (view.getBackground()==null&&listNarusheniya.get(position).getObrabotana().equals("не обработана")){
            view.setBackgroundResource(R.color.colorBackItems);
            listNarusheniya.get(position).setObrabotana("Обработана!");
        }else if (view.getBackground()!=null){
            view.setBackground(null);
            listNarusheniya.get(position).setObrabotana("не обработана");
        }else if (!listNarusheniya.get(position).getObrabotana().equals("не обработана")){
            Toast.makeText(this,"Заявка уже обработана",Toast.LENGTH_LONG).show();
        }
    }

    private void downloadDataList(){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                countListData = dataSnapshot.getChildrenCount();

                if (mChildEventListeber == null){
                    mChildEventListeber = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            zaloba = dataSnapshot.getValue(Zaloba.class);
                            listNarusheniya.add(zaloba);

                            if (listNarusheniya.size()==countListData){
                                //confirmList();
                                Log.d(MainActivity.LOG_TAG,"countListData = "+ listNarusheniya.size());

                                String [] arr_1 = new String[listNarusheniya.size()];
                                String [] arr_2 = new String[listNarusheniya.size()];
                                String [] arr_3 = new String[listNarusheniya.size()];
                                String [] arr_4 = new String[listNarusheniya.size()];
                                String [] arr_5 = new String[listNarusheniya.size()];
                                String [] arr_6 = new String[listNarusheniya.size()];
                                String [] arr_7 = new String[listNarusheniya.size()];

                                for (int i = 0; i<listNarusheniya.size();i++){
                                    arr_1[i] = "ФИО заявителя: " + listNarusheniya.get(i).getFIOZayavitelya();
                                    arr_2[i] = "Адрес: " + listNarusheniya.get(i).getAdress();
                                    arr_3[i] = "Характер нарушения: " + listNarusheniya.get(i).getCharacter();
                                    arr_4[i] = "Готовы писать заявление: " + listNarusheniya.get(i).getGotovPisatZayavlenie();
                                    arr_5[i] = "Контакты: " + listNarusheniya.get(i).getKontakti();
                                    arr_6[i] = "Описание нарушения: " + listNarusheniya.get(i).getOpisanie();
                                    arr_7[i] = "На исполнении: " + listNarusheniya.get(i).getObrabotana();
                                }

                                ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(listNarusheniya.size());
                                Map<String, Object> m;

                                for (int i = 0; i < listNarusheniya.size(); i++) {
                                    m = new HashMap<String, Object>();
                                    m.put(ATTRIBUTE_NAME_TEXT_1, arr_1[i]);
                                    m.put(ATTRIBUTE_NAME_TEXT_2, arr_2[i]);
                                    m.put(ATTRIBUTE_NAME_TEXT_3, arr_3[i]);
                                    m.put(ATTRIBUTE_NAME_TEXT_4, arr_4[i]);
                                    m.put(ATTRIBUTE_NAME_TEXT_5, arr_5[i]);
                                    m.put(ATTRIBUTE_NAME_TEXT_6, arr_6[i]);
                                    m.put(ATTRIBUTE_NAME_TEXT_7, arr_7[i]);
                                    data.add(m);
                                }

                                String[] from = { ATTRIBUTE_NAME_TEXT_1,  ATTRIBUTE_NAME_TEXT_2,  ATTRIBUTE_NAME_TEXT_3,
                                        ATTRIBUTE_NAME_TEXT_4,  ATTRIBUTE_NAME_TEXT_5,  ATTRIBUTE_NAME_TEXT_6,ATTRIBUTE_NAME_TEXT_7 };
                                int[] to = { R.id.text_view_1, R.id.text_view_2, R.id.text_view_3,
                                        R.id.text_view_4, R.id.text_view_5, R.id.text_view_6,R.id.text_view_7};

                                simpleAdapter = new SimpleAdapter(Kabinet.this,data,R.layout.item,from,to);
                                listView.setAdapter(simpleAdapter);

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
