package com.example.instademo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.instademo.Fragment.HomeFragment;
import com.example.instademo.Fragment.NotificationFragment;
import com.example.instademo.Fragment.ProfileFragment;
import com.example.instademo.Fragment.SearchFragment;
import com.example.instademo.R;
import com.example.instademo.dto.UserDTO;
import com.example.instademo.mapper.UserMapper;
import com.example.instademo.model.User;
import com.example.instademo.utils.LogedUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private Fragment homeFragment;
    private Fragment searchFragment;
    private Fragment notificationFragment;
    private Fragment profileFragment;
    private FrameLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initComponent();
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        transaction.add(R.id.fragment_container, new HomeFragment()).commit();
    }

    private void initComponent() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        notificationFragment = new NotificationFragment();
        profileFragment = new ProfileFragment();

        mainLayout = findViewById(R.id.fragment_container);
    }

    public void replaceFragment(Fragment fragment) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        replaceFragment(homeFragment);
                        break;
                    case R.id.nav_search:
                        searchFragment = new SearchFragment();
                        replaceFragment(searchFragment);
                        break;
                    case R.id.nav_add:
                        startActivity(new Intent(HomeActivity.this, PostActivity.class));
                        break;
                    case R.id.nav_noti:
                        notificationFragment = new NotificationFragment();
                        replaceFragment(notificationFragment);
                        break;
                    case R.id.nav_profile:
                        Bundle bundle = new Bundle();
                        bundle.putInt("type",0);
                        profileFragment = new ProfileFragment();
                        profileFragment.setArguments(bundle);
                        replaceFragment(profileFragment);
                        break;
                }
                return true;
            };
}