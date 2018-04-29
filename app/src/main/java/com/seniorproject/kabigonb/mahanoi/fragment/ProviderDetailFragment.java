package com.seniorproject.kabigonb.mahanoi.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.activity.MapsActivity;
import com.seniorproject.kabigonb.mahanoi.dao.DeniedDao;
import com.seniorproject.kabigonb.mahanoi.dao.MatchMakingDao;
import com.seniorproject.kabigonb.mahanoi.dao.OpnListDataDao;
import com.seniorproject.kabigonb.mahanoi.dao.ProviderDataDao;
import com.seniorproject.kabigonb.mahanoi.dao.RequestFormDao;
import com.seniorproject.kabigonb.mahanoi.manager.HttpManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("unused")
public class ProviderDetailFragment extends Fragment implements View.OnClickListener {



    TextView tv_providerDetail_ServiceType,tv_providerDetail_providerName
            ,tv_providerDetail_telephoneNumber,tv_providerDetail_email
            ,tv_providerDetail_serviceDetail;

    Button btnServiceAccept,btnServiceDenied;

    OpnListDataDao dao;

    public ProviderDetailFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ProviderDetailFragment newInstance(OpnListDataDao dao) {
        ProviderDetailFragment fragment = new ProviderDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("dao",dao);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);


        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_provider_detail, container, false);

        dao = getArguments().getParcelable("dao");

        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        tv_providerDetail_ServiceType = rootView.findViewById(R.id.tv_providerDetail_ServiceType);
        tv_providerDetail_providerName = rootView.findViewById(R.id.tv_providerDetail_providerName);
        tv_providerDetail_telephoneNumber = rootView.findViewById(R.id.tv_providerDetail_telephoneNumber);
        tv_providerDetail_email = rootView.findViewById(R.id.tv_providerDetail_email);
        tv_providerDetail_serviceDetail = rootView.findViewById(R.id.tv_providerDetail_serviceDetail);

        btnServiceAccept = rootView.findViewById(R.id.btnServiceAccept);
        btnServiceDenied = rootView.findViewById(R.id.btnServiceDenied);

        btnServiceAccept.setOnClickListener(this);
        btnServiceDenied.setOnClickListener(this);

        Call<ProviderDataDao> call = HttpManager.getInstance().getService().showProvider(dataForm(dao));
        call.enqueue(new Callback<ProviderDataDao>() {
            @Override
            public void onResponse(Call<ProviderDataDao> call, Response<ProviderDataDao> response) {

                if(response.isSuccessful())
                {
                    ProviderDataDao dao = response.body();

                    textViewDataSet(dao);

                }
                else {
                    try {
                        Toast.makeText(Contextor.getInstance().getContext(),response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ProviderDataDao> call, Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private ProviderDataDao dataForm(OpnListDataDao dao) {

        ProviderDataDao providerDataDao = new ProviderDataDao();
        SharedPreferences prefs = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        String token = prefs.getString("token",null);

        providerDataDao.setProviderUserName(dao.getProviderUsername());
        providerDataDao.setToken(token);

        return providerDataDao;
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
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    @Override
    public void onClick(View v) {

        if(v == btnServiceDenied)
        {
            btnServiceDenied.setEnabled(false);
            Call<DeniedDao> call = HttpManager.getInstance().getService().offerDenied(deniedForm());
            call.enqueue(deniedDaoCallback);
        }

        if(v == btnServiceAccept)
        {
            btnServiceAccept.setEnabled(false);

            Intent intent = new Intent(getActivity(), MapsActivity.class);
            intent.putExtra("dao",dao);
            startActivity(intent);

        }

    }

    private DeniedDao deniedForm() {

        DeniedDao deniedDao = new DeniedDao();
        SharedPreferences prefs = getActivity().getSharedPreferences("token",Context.MODE_PRIVATE);

        deniedDao.setToken(prefs.getString("token",null));
        deniedDao.setUsername(prefs.getString("userName",null));
        deniedDao.setResponseId(dao.getResponseId());

        return deniedDao;
    }

    private MatchMakingDao matchMakingForm(OpnListDataDao opnListDataDao) {

        MatchMakingDao dao = new MatchMakingDao();
        SharedPreferences prefs = getActivity().getSharedPreferences("token",Context.MODE_PRIVATE);

        dao.setToken(prefs.getString("token",null));
        dao.setUserName(prefs.getString("userName",null));
        dao.setOfferId(opnListDataDao.getOfferId());

        return dao;
    }


    private void textViewDataSet(ProviderDataDao dao) {

        tv_providerDetail_providerName.setText(dao.getFirstName()+ " " + dao.getLastName());
        tv_providerDetail_telephoneNumber.setText(dao.getTelephoneNumber());
        tv_providerDetail_email.setText(dao.getEmail());
        tv_providerDetail_serviceDetail.setText(dao.getDetail());

        switch(dao.getTypeService())
        {
            case 1:
                tv_providerDetail_ServiceType.setText("Food Service");
                break;
            case 2:
                tv_providerDetail_ServiceType.setText("Electric Service");
                break;
            case 3:
                tv_providerDetail_ServiceType.setText("Plumbing Service");
                break;
            case 4:
                tv_providerDetail_ServiceType.setText("Cleaning Service");
                break;
            default:
                tv_providerDetail_ServiceType.setText("");
        }

    }

    Callback<MatchMakingDao> responseMatchMaking = new Callback<MatchMakingDao>() {
        @Override
        public void onResponse(Call<MatchMakingDao> call, Response<MatchMakingDao> response) {

            btnServiceAccept.setEnabled(true);

            if (response.isSuccessful()) {
                Toast.makeText(Contextor.getInstance().getContext()
                        , response.body().getStatusMessage()
                        , Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                try {
                    Toast.makeText(Contextor.getInstance().getContext()
                            , response.errorBody().string()
                            , Toast.LENGTH_SHORT)
                            .show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onFailure(Call<MatchMakingDao> call, Throwable t) {

            btnServiceAccept.setEnabled(true);

            Toast.makeText(Contextor.getInstance().getContext()
                    , t.toString()
                    , Toast.LENGTH_SHORT)
                    .show();

        }
    };

    Callback<DeniedDao> deniedDaoCallback = new Callback<DeniedDao>() {
        @Override
        public void onResponse(Call<DeniedDao> call, Response<DeniedDao> response) {

            btnServiceDenied.setEnabled(true);

            if(response.isSuccessful())
            {
                DeniedDao dao = response.body();
                if(dao.getErrorMessage() != null)
                {
                    Toast.makeText(Contextor.getInstance().getContext()
                            , dao.getErrorMessage()
                            , Toast.LENGTH_SHORT)
                            .show();
                }
                else if(!dao.getStatusMessage().equals("บันทึกข้อมูลสำเร็จ"))
                {
                    Toast.makeText(Contextor.getInstance().getContext()
                            , dao.getStatusMessage()
                            , Toast.LENGTH_SHORT)
                            .show();
                }
                else
                {
                    Toast.makeText(Contextor.getInstance().getContext()
                            , dao.getStatusMessage()
                            , Toast.LENGTH_SHORT)
                            .show();
                    getActivity().finish();
                }
            }
            else
            {
                try {
                    Toast.makeText(Contextor.getInstance().getContext()
                            , response.errorBody().string()
                            , Toast.LENGTH_SHORT)
                            .show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onFailure(Call<DeniedDao> call, Throwable t) {

            btnServiceDenied.setEnabled(true);
            Toast.makeText(Contextor.getInstance().getContext()
                    , t.toString()
                    , Toast.LENGTH_SHORT)
                    .show();
        }
    };

}
