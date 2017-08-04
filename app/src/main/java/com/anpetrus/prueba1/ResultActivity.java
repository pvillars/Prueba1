package com.anpetrus.prueba1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        result(getIntent());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sharePic(getImage(), nameIn, getResources().getString(R.string.message_day));

                } else {
                    Toast.makeText(ResultActivity.this, R.string.permission_denied_read_external_extorage, Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private String nameIn = "";

    private void result(Intent intent) {

        nameIn = intent.getStringExtra(Constants.EXTRA_NAME).toUpperCase().trim();
        int genderSelected = intent.getIntExtra(Constants.EXTRA_GENDER_SELECTED, -1);

        final ImageView imageResult = (ImageView) findViewById(R.id.imageResult);
        TextView textResult = (TextView) findViewById(R.id.textResult);

        switch (genderSelected) {
            case 1:
                textResult.setText(nameIn + getResources().getString(R.string.message_day_male));
                imageResult.setImageResource(R.mipmap.beer);
                break;
            case 2:
                textResult.setText(nameIn + getResources().getString(R.string.message_day_female));
                imageResult.setImageResource(R.mipmap.flower);
                break;
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.sendImage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int MyVersion = Build.VERSION.SDK_INT;
                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    ActivityCompat.requestPermissions(ResultActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    sharePic(getImage(), nameIn, getResources().getString(R.string.message_day));
                }
            }
        });

    }

    private Bitmap getImage() {
        LinearLayout shareLayout = (LinearLayout) this.findViewById(R.id.contentResultLayout);
        shareLayout.setDrawingCacheEnabled(true);
        shareLayout.buildDrawingCache();
        Bitmap bm = shareLayout.getDrawingCache();
        //ByteArrayOutputStream bytes = new ByteArrayOutputStream();
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
                    File.separator + nameImage + ".jpg");

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
            startActivity(Intent.createChooser(share, getResources().getString(R.string.title_share_image)));
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage());
        }

    }
}
