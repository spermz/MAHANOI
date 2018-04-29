package com.seniorproject.kabigonb.mahanoi.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.dao.RegisterDao;
import com.seniorproject.kabigonb.mahanoi.dao.TokenDao;
import com.seniorproject.kabigonb.mahanoi.manager.HttpManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {

    EditText    teAccountName,teAccountNumber,teAccountLastName;
    Button btn_Account_Update;

    public AccountFragment() {
        super();
    }

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        teAccountName = rootView.findViewById(R.id.teAccountName);
        teAccountNumber = rootView.findViewById(R.id.teAccountNumber);
        teAccountLastName = rootView.findViewById(R.id.teAccountLastName);

        btn_Account_Update = rootView.findViewById(R.id.btn_Account_Update);

        btn_Account_Update.setOnClickListener(this);

        Call<RegisterDao> call = HttpManager.getInstance().getService().loadUserData(loadFrom());
        call.enqueue(callbackLoadForm);

    }

    private RegisterDao loadFrom() {

        RegisterDao dao = new RegisterDao();
        SharedPreferences prefs = getActivity().getSharedPreferences("token",Context.MODE_PRIVATE);

        dao.setUsername(prefs.getString("userName",null));
        dao.setToken(prefs.getString("token",null));

        return dao;
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

        if (v == btn_Account_Update)
        {
            btn_Account_Update.setEnabled(false);
            Call<RegisterDao> call = HttpManager.getInstance().getService().userUpdate(updateForm());
            call.enqueue(updateCallBack);
        }
    }

    private RegisterDao updateForm() {

        RegisterDao dao = new RegisterDao();
        SharedPreferences prefs = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        dao.setToken(prefs.getString("token",null));
        dao.setUsername(prefs.getString("userName",null));


        dao.setFirstname(teAccountName.getText().toString());
        dao.setLastname(teAccountLastName.getText().toString());
        dao.setNumber(teAccountNumber.getText().toString());



        return dao;

    }

    Callback<RegisterDao> updateCallBack = new Callback<RegisterDao>() {
        @Override
        public void onResponse(Call<RegisterDao> call, Response<RegisterDao> response) {

            btn_Account_Update.setEnabled(true);

            if(response.isSuccessful())
            {
                RegisterDao dao = response.body();

                if(dao.getErrorMessage() != null)
                {
                    Toast.makeText(Contextor.getInstance().getContext()
                            ,dao.getErrorMessage()
                            ,Toast.LENGTH_SHORT)
                            .show();
                }
                else
                {
                    Toast.makeText(Contextor.getInstance().getContext()
                            ,dao.getStatusMessage()
                            ,Toast.LENGTH_SHORT)
                            .show();
                }
            }

            else
            {
                try {
                    Toast.makeText(Contextor.getInstance().getContext()
                            ,response.errorBody().string()
                            ,Toast.LENGTH_SHORT)
                            .show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onFailure(Call<RegisterDao> call, Throwable t) {

            btn_Account_Update.setEnabled(true);

            Toast.makeText(Contextor.getInstance().getContext()
                    ,t.toString()
                    ,Toast.LENGTH_SHORT)
                    .show();
        }
    };

    Callback<RegisterDao> callbackLoadForm = new Callback<RegisterDao>() {
        @Override
        public void onResponse(Call<RegisterDao> call, Response<RegisterDao> response) {

            if(response.isSuccessful())
            {
                RegisterDao dao = response.body();

                teAccountName.setText(dao.getFirstname());
                teAccountNumber.setText(dao.getNumber());
                teAccountLastName.setText(dao.getLastname());

            }
            else
            {
                try {
                    Toast.makeText(Contextor.getInstance().getContext()
                            ,response.errorBody().string()
                            ,Toast.LENGTH_SHORT)
                            .show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onFailure(Call<RegisterDao> call, Throwable t) {

            Toast.makeText(Contextor.getInstance().getContext()
                    ,t.toString()
                    ,Toast.LENGTH_SHORT)
                    .show();
        }
    };


}
