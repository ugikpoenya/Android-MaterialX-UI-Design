package com.ugikpoenya.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ugikpoenya.appmanager.Prefs;
import com.ugikpoenya.materialx.ui.design.data.DataGenerator;
import com.ugikpoenya.materialx.ui.design.intro.IntroPagerAdapter;
import com.ugikpoenya.materialx.ui.design.model.Image;
import com.ugikpoenya.materialx.ui.design.utils.Tools;
import com.ugikpoenya.sampleapp.databinding.ActivityMainBinding;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private IntroPagerAdapter.MyViewPagerAdapter myViewPagerAdapter;
    private List<Image> items = new ArrayList<>();
    private Runnable runnable = null;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        initSlider();
        Field[] fields = R.string.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            String str = "";
            int resId = getResources().getIdentifier(fields[i].getName(), "string", getPackageName());
            str += fields[i].getName() + " = ";
            if (resId != 0) {
                str += getResources().getString(resId);
            }
            str += "\n";
            binding.stringValue.append(str);
        }
    }

    private void initSlider() {
        items = DataGenerator.getListImage(this, R.array.intro_image);
        IntroPagerAdapter.onChangeListener viewPagerPageChangeListener = new IntroPagerAdapter.onChangeListener(this, items.size(), binding.layoutDots, R.color.grey_900);
        myViewPagerAdapter = new IntroPagerAdapter.MyViewPagerAdapter(this, items, R.layout.item_slider_image);
        binding.imagePager.setAdapter(myViewPagerAdapter);
        binding.imagePager.addOnPageChangeListener(viewPagerPageChangeListener);

        startAutoSlider(myViewPagerAdapter.getCount());
    }

    private void startAutoSlider(final int count) {
        runnable = () -> {
            int pos = binding.imagePager.getCurrentItem();
            pos = pos + 1;
            if (pos >= count) pos = 0;
            binding.imagePager.setCurrentItem(pos);
            handler.postDelayed(runnable, 2000);
        };
        handler.postDelayed(runnable, 2000);
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

    public void showDarkTheme(View view) {
        startActivity(new Intent(getApplicationContext(), DarkThemeActivity.class));
    }

    @Override
    public void onBackPressed() {
        Tools.exitApp(this);
    }

}