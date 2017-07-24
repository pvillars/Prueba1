package com.anpetrus.prueba1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String name=intent.getStringExtra("NAME");
        boolean genderMale = intent.getBooleanExtra("GENDER_MALE", true); //Default true
        String gender = (genderMale) ? "Masculino":"Femenino";
        Toast.makeText(this,"Name: "+name+", Gender: " + gender , Toast.LENGTH_LONG).show();
    }
}
