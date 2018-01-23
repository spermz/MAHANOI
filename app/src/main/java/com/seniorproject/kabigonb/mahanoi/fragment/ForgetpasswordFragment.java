package com.seniorproject.kabigonb.mahanoi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.seniorproject.kabigonb.mahanoi.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ForgetpasswordFragment extends Fragment implements View.OnClickListener {

    Button btnResetPassword;
    public ForgetpasswordFragment() {
        super();
    }

    public static ForgetpasswordFragment newInstance() {
        ForgetpasswordFragment fragment = new ForgetpasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forgetpassword, container, false);
        initInstances(rootView);
        btnResetPassword.setOnClickListener(this);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnResetPassword = rootView.findViewById(R.id.btnResetPassword);
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
    public void onClick(View v) { //TODO: Implement password reset logic
        if( v == btnResetPassword)
        {
            getFragmentManager().beginTransaction()
                    .replace(R.id.contentContainer,MainFragment.newInstance())
                    .commit();
        }
    }
}
