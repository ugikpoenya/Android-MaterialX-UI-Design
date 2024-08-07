package com.ugikpoenya.sampleapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ugikpoenya.appmanager.FirebaseManager;
import com.ugikpoenya.appmanager.Prefs;
import com.ugikpoenya.appmanager.ServerManager;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseManager firebaseManager = new FirebaseManager();
        firebaseManager.setBaseUrl(this,"https://ugikpoenya-app-manager-default-rtdb.asia-southeast1.firebasedatabase.app/");
        firebaseManager.setApiKey(this,"-O2sR7wdll6d9-nIrCOZ");

        ServerManager serverManager = new ServerManager();
        serverManager.setBaseUrl(this, "https://master.ugikpoenya.net/api/");
        serverManager.setApiKey(this, "DA8BB129F7C1ED5BD07046961C995A77");

        firebaseManager.getItemDelay(this, 3000, () -> {
            if (new Prefs(this).getVersion_code() == BuildConfig.VERSION_CODE) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), IntroActivity.class));
            }
            finish();
            return null;
        });
    }
}
