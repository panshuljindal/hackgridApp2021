package com.adgvit.hackgrid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.adgvit.hackgrid.R;
import com.adgvit.hackgrid.fragments.faq;
import com.adgvit.hackgrid.fragments.partners;
import com.adgvit.hackgrid.fragments.profile;
import com.adgvit.hackgrid.fragments.timeline;
import com.adgvit.hackgrid.fragments.tracks;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new timeline()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfragment = null;
                switch (item.getItemId()){
                    case R.id.navigation_timeline:
                        selectedfragment = new timeline();
                        break;
                    case R.id.navigation_partners:
                        selectedfragment = new partners();
                        break;
                    case R.id.navigation_tracks:
                        selectedfragment = new tracks();
                        break;
                    case R.id.navigation_faq:
                        selectedfragment = new faq();
                        break;
                    case R.id.navigation_profile:
                        selectedfragment = new profile();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selectedfragment).commit();
                return  true;
            }
        });


    }
}