package com.curiosityrover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.curiosityrover.design.ViewPagerAdapter;
import com.curiosityrover.fragments.DataFetchFragment;
import com.google.android.material.tabs.TabLayout;



public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    AboutFragment about;
    ViewPagerAdapter viewPagerAdapter;
    boolean isClicked = false;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabview);


        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Curiosity Rover");

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(viewPagerAdapter);

    }

    public void displayAbout(View view){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_ltr, R.anim.exit_ltr, R.anim.enter_ltr, R.anim.exit_ltr);


        about = (AboutFragment) manager.findFragmentByTag("about_fragment");

        if(!isClicked){
            transaction.add(R.id.sample_layout, AboutFragment.class, null,"about_fragment")
                    .addToBackStack(null);
        }else {
            about = (AboutFragment) manager.findFragmentById(R.id.sample_layout);
            transaction.remove(about);
        }
        isClicked = !isClicked;
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isClicked = false;
    }
}