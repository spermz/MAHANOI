package com.seniorproject.kabigonb.mahanoi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.activity.MapsActivity;
import com.seniorproject.kabigonb.mahanoi.activity.ServiceCleaningActivity;
import com.seniorproject.kabigonb.mahanoi.activity.ServiceElectricianActivity;
import com.seniorproject.kabigonb.mahanoi.activity.ServiceFoodActivity;
import com.seniorproject.kabigonb.mahanoi.activity.ServicePlumberActivity;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ServiceFragment extends Fragment implements View.OnClickListener {

    ImageButton im_btn_Food,im_btn_Plumber,im_btn_electrician,im_btn_cleaning;

    public ServiceFragment() {
        super();
    }

    public static ServiceFragment newInstance() {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service, container, false);
        initInstances(rootView);
        im_btn_Food.setOnClickListener(this);
        im_btn_Plumber.setOnClickListener(this);
        im_btn_electrician.setOnClickListener(this);
        im_btn_cleaning.setOnClickListener(this);

        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        im_btn_Food = rootView.findViewById(R.id.im_btn_Food);
        im_btn_Plumber =rootView.findViewById(R.id.im_btn_Plumber);
        im_btn_electrician = rootView.findViewById(R.id.im_btn_electrician);
        im_btn_cleaning = rootView.findViewById(R.id.im_btn_cleaning);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    @Override
    public void onClick(View v) {

       if(v == im_btn_Food)
       {
           Intent intentFood = new Intent(getActivity(), ServiceFoodActivity.class);
           startActivity(intentFood);

       }
       else if(v == im_btn_Plumber)
       {
           Intent intentPlumber = new Intent(getActivity(), ServicePlumberActivity.class);
           startActivity(intentPlumber);
       }
       else if (v == im_btn_electrician)
       {
           Intent intentElectrician = new Intent(getActivity(), ServiceElectricianActivity.class);
           startActivity(intentElectrician);
       }
       else if(v == im_btn_cleaning)
       {
           Intent intentCleaning = new Intent(getActivity(), ServiceCleaningActivity.class);
           startActivity(intentCleaning);
       }

    }
}
