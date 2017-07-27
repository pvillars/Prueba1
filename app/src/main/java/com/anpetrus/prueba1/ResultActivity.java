package com.anpetrus.prueba1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String nameIn = intent.getStringExtra("NAME").toUpperCase();
        boolean genderMale = intent.getBooleanExtra("GENDER_MALE", true); //Default true

        ImageView imageResult = (ImageView) findViewById(R.id.imageResult);
        TextView textResult = (TextView) findViewById(R.id.textResult);

        if (genderMale) {
            textResult.setText(nameIn.trim() + ", eres el mejor, SALUD!!!");
            imageResult.setImageResource(R.mipmap.beer);
        } else {
            textResult.setText(nameIn.trim() + ", eres la mejor y la más hermoza!!!");
            imageResult.setImageResource(R.mipmap.flower);
        }
    }
}
