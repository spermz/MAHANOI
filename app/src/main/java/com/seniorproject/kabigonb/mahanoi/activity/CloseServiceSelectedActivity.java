package com.seniorproject.kabigonb.mahanoi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.dao.CloseListDataDao;
import com.seniorproject.kabigonb.mahanoi.fragment.CloseServiceSelectedFragment;

public class CloseServiceSelectedActivity extends AppCompatActivity {


    Toolbar toolbarCloseServiceSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_service_selected);

        CloseListDataDao dao = getIntent().getParcelableExtra("dao");

        initInstance();


        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainerCloseServiceSelected, CloseServiceSelectedFragment.newInstance(dao))
                    .commit();
        }
    }

    private void initInstance() {
        toolbarCloseServiceSelected = findViewById(R.id.toolbarCloseServiceSelected);
        setSupportActionBar(toolbarCloseServiceSelected);

    }
}
