package com.accherniakocich.android.druzina.Button_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.accherniakocich.android.druzina.MainActivity;
import com.accherniakocich.android.druzina.R;

public class Rayons extends AppCompatActivity {

    private Button b_1,b_2,b_3,b_4,b_5,b_6,b_7,b_8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rayons);

        init();
    }

    private void init() {
        b_1 = (Button) findViewById(R.id.button_rayon_center);
        b_2 = (Button) findViewById(R.id.button_rayon_oktyabrskiy);
        b_3 = (Button) findViewById(R.id.button_rayon_zaeltsovskiy);
        b_4 = (Button) findViewById(R.id.button_rayon_kalininskiy);
        b_5 = (Button) findViewById(R.id.button_rayon_dzerzhinskiy);
        b_6 = (Button) findViewById(R.id.button_rayon_kirovskiy);
        b_7 = (Button) findViewById(R.id.button_rayon_lininskiy);
        b_8 = (Button) findViewById(R.id.button_rayon_ZD);
    }

    public void clickOnButton(View view) {
        Intent intent = new Intent(Rayons.this,HarakterZhalobi.class);
        Button button = (Button)view;
        //Log.d(MainActivity.LOG_TAG,"text = " + button.getText());
        intent.putExtra("text_name_rayon",button.getText().toString());
        startActivity(intent);
    }
}
