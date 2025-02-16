package com.example.tp1_1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ThirdActivity extends Activity {

    /**
     * Cette activité a été abandonnée car pas utile, elle a été remplacée par une fenêtre de dialogue
     */

    private TextView tvPhoneNumber;
    private Button btnCall;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        btnCall = findViewById(R.id.btnCall);
        ImageView imgPhone = findViewById(R.id.imgPhone);

        // Get phone number from intent
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("telephone");

        // Display phone number
        if (phoneNumber != null) {
            tvPhoneNumber.setText(getString(R.string.display_phone_number) + " " + phoneNumber);
        }

        // Set button action to make a call
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            }
        });
    }
}
