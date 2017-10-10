package com.accherniakocich.android.druzina.Button_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.accherniakocich.android.druzina.R;

public class Registration extends AppCompatActivity {

    private EditText et_1,et_2,et_3,et_4,et_5;
    private Button button_registration_zastup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabinet);

        init();
    }

    private void init() {
        et_1 = (EditText)findViewById(R.id.FIODrizinnika);
        et_2 = (EditText)findViewById(R.id.rayon);
        et_3 = (EditText)findViewById(R.id.ulitsa_marshrut);
        et_4 = (EditText)findViewById(R.id.phone);
        et_5 = (EditText)findViewById(R.id.number_of_udostoverenie);

        button_registration_zastup = (Button) findViewById(R.id.button_registration_zastup);
        button_registration_zastup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton();
            }
        });
    }

    private void clickButton() {

    }
}
