package com.seniorproject.kabigonb.mahanoi.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.fragment.AccountFragment;
import com.seniorproject.kabigonb.mahanoi.fragment.RequestFragment;
import com.seniorproject.kabigonb.mahanoi.fragment.ServiceFragment;

public class Main2Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bnvMenu;
    Toolbar toolbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer2,ServiceFragment.newInstance())
                    .commit();
            bnvMenu.setSelectedItemId(R.id.service);
        }

        bnvMenu.setOnNavigationItemSelectedListener(this);

    }

    private void initInstance() {
        toolbar2 =  findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        bnvMenu = findViewById(R.id.bnvMenu);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.service :
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer2,ServiceFragment.newInstance())
                        .commit();
                return true;
            case R.id.request :
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer2, RequestFragment.newInstance())
                        .commit();
                return true;
            case R.id.account : //TODO: Implement profile update logic
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer2, AccountFragment.newInstance())
                        .commit();
                return true;
        }
        return false;
    }
}
/*
        tlRequestTab.addTab(tlRequestTab.newTab().setText("Open"));
        tlRequestTab.addTab(tlRequestTab.newTab().setText("Close"));
        tlRequestTab.removeAllTabs();*/