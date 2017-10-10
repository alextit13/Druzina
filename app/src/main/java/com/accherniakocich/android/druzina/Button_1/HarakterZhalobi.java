package com.accherniakocich.android.druzina.Button_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.accherniakocich.android.druzina.R;

public class HarakterZhalobi extends AppCompatActivity {

    private Button b1,b2,b3,b4,b5,b6;
    private String rayon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harakter_zhalobi);
        init();
    }

    private void init() {

        Intent intent = getIntent();
        rayon = intent.getStringExtra("text_name_rayon");

        b1 = (Button)findViewById(R.id.button_character_zhalobi_1);
        b2 = (Button)findViewById(R.id.button_character_zhalobi_2);
        b3 = (Button)findViewById(R.id.button_character_zhalobi_3);
        b4 = (Button)findViewById(R.id.button_character_zhalobi_4);
        b5 = (Button)findViewById(R.id.button_character_zhalobi_5);
        b6 = (Button)findViewById(R.id.button_character_zhalobi_6);
    }

    public void clickOnButton(View view) {
        Intent intent = new Intent(HarakterZhalobi.this,DannieZayavitelya.class);
        Button button = (Button)view;
        String harakter = button.getText().toString();
        intent.putExtra("harakter",harakter);
        intent.putExtra("rayon",rayon);
        startActivity(intent);
    }
}
