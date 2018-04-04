package com.seniorproject.kabigonb.mahanoi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.dao.OpnListDataDao;
import com.seniorproject.kabigonb.mahanoi.fragment.ProviderDetailFragment;
import com.seniorproject.kabigonb.mahanoi.manager.HttpManager;

import retrofit2.http.HTTP;

public class OpenServiceSelectedActivity extends AppCompatActivity {

    Toolbar toolbarServiceSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_service_selected);

        OpnListDataDao dao = getIntent().getParcelableExtra("dao");

        initInstance();

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainerOpenServiceSelected, ProviderDetailFragment.newInstance(dao))
                    .commit();
        }
    }

    private void initInstance() {

        toolbarServiceSelected = findViewById(R.id.toolbarServiceSelected);
        setSupportActionBar(toolbarServiceSelected);


    }

}
