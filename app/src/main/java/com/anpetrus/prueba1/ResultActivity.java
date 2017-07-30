package com.anpetrus.prueba1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        final String nameIn = intent.getStringExtra("NAME").toUpperCase().trim();
        boolean genderMale = intent.getBooleanExtra("GENDER_MALE", true); //Default true

        final ImageView imageResult = (ImageView) findViewById(R.id.imageResult);
        TextView textResult = (TextView) findViewById(R.id.textResult);

        if (genderMale) {
            textResult.setText(nameIn + ", eres el mejor, SALUD!!!");
            imageResult.setImageResource(R.mipmap.beer);
        } else {
            textResult.setText(nameIn + ", eres la mejor y la más hermoza!!!");
            imageResult.setImageResource(R.mipmap.flower);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.sendImage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // imageResult.setImageBitmap(getImage());
                sharePic(getImage(),nameIn , "Mejorando el día");

            }
        });
    }

    public Bitmap getImage() {
        LinearLayout shareLayout = (LinearLayout) this.findViewById(R.id.contentResultLayout);
        shareLayout.setDrawingCacheEnabled(true);
        shareLayout.buildDrawingCache();
        Bitmap bm = shareLayout.getDrawingCache();
        // ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return bm;
    }

    private void sharePic(Bitmap image, String nameImage, String message) {

        //Se guarda la imagen en la SDCARD
        File f;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            f = new File(Environment.getExternalStorageDirectory() + File.separator +
                     File.separator + nameImage +".jpg");

            f.createNewFile();

            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            bytes.flush();
            bytes.close();
            //compartir imagen
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            share.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(share, "Compartir imagen"));
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage());
        }

    }
}
