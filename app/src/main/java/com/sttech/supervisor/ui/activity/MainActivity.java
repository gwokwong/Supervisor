package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.sttech.supervisor.R;
import com.sttech.supervisor.ui.fragment.HomeFragment;
import com.sttech.supervisor.ui.fragment.MeFragment;


public class MainActivity extends TActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        initView();
    }

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    private HomeFragment homeFragment;
    private MeFragment meFragment;

    private void initView() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        meFragment = new MeFragment();
        fragmentManager.beginTransaction().replace(R.id.content, homeFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content, homeFragment).commit();
                    return true;
                case R.id.navigation_me:
                    transaction.replace(R.id.content, meFragment).commit();
                    return true;
            }
            return false;
        }

    };

}

