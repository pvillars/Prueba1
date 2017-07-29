package com.anpetrus.prueba1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.sendImage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Mostrar opciones de envio de imagen", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
