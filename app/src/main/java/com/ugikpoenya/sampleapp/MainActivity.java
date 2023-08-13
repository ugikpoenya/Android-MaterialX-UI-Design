package com.ugikpoenya.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ugikpoenya.appmanager.Prefs;
import com.ugikpoenya.materialx.ui.design.utils.Tools;
import com.ugikpoenya.sampleapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
    }

    private void initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Sample App");
        getSupportActionBar().setSubtitle("Welcome to Ugikpoenya");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void showIntro(View view) {
        new Prefs(this).setVersion_code(0);
        startActivity(new Intent(getApplicationContext(), IntroActivity.class));
        finish();
    }

    public void showList(View view) {
        startActivity(new Intent(getApplicationContext(), ListActivity.class));
    }

    public void showTab(View view) {
        startActivity(new Intent(getApplicationContext(), TabActivity.class));
    }

    public void showBottomNavigation(View view) {
        startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
    }

    public void showProgress(View view) {
        startActivity(new Intent(getApplicationContext(), ProgressActivity.class));
    }

    public void showSearch(View view) {
        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
    }

    @Override
    public void onBackPressed() {
        Tools.exitApp(this);
    }

}