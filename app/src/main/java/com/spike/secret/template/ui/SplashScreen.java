package com.spike.secret.template.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.spike.secret.template.ui.landing.LandingActivity;

/**
 * Created by Shyam on 2/5/17.
 */

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
        finish();
    }
}
