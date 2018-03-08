package com.seniorproject.kabigonb.mahanoi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.fragment.ElectricianServiceRequestFragment;

public class ServiceElectricianActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_electrician);

        initInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer_electrician, ElectricianServiceRequestFragment.newInstance()).commit();
        }


    }

    private void initInstance() {
        toolbar =  findViewById(R.id.toolbar_electrician);
        toolbar.setTitle("Request Electric Service");
        setSupportActionBar(toolbar);
    }
}
