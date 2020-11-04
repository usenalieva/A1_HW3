package com.example.a1_homework3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.os.StrictMode;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    private ImageView receivedImage;
    private TextView receivedText;
    private Button openSecAct, sendViaGmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receivedImage = findViewById(R.id.iv_receivedImage);
        receivedText = findViewById(R.id.tv_receivedText);
        openSecAct = findViewById(R.id.btn_openSecAct);
        sendViaGmail = findViewById(R.id.btn_sendViaGmail);


        /** set the Button opening the Second Activity **/
        openSecAct.setOnClickListener(view -> {
            Intent openTheSecondActivity = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(openTheSecondActivity);
        });

        /** Getting the Data from the Second Activity **/
        Intent dataFromSecAct = getIntent();
        String getText = dataFromSecAct.getStringExtra(SecondActivity.SEND_TEXT);
        receivedText.setText(getText);
        receivedImage.setImageURI(SecondActivity.uriOfImage);


        /** Sharing the Received Data via Gmail **/
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE, INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        sendViaGmail.setOnClickListener(view -> {

            Intent shareFiles = new Intent(Intent.ACTION_SEND);
            shareFiles.setData(Uri.parse("mailto:"));
            shareFiles.setType("image/*");
            shareFiles.setType("text/plain");
            shareFiles.putExtra(Intent.EXTRA_SUBJECT, "Here we go...");
            shareFiles.putExtra(Intent.EXTRA_TEXT, getText);
            shareFiles.putExtra(Intent.EXTRA_STREAM, SecondActivity.uriOfImage);
            startActivity(shareFiles);


            /** All the available options:
            startActivity(Intent.createChooser(shareFiles, "Share the data ")); **/

        });
    }
}

