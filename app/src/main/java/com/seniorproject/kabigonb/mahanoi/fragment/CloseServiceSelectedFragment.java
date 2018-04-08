package com.seniorproject.kabigonb.mahanoi.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.dao.CloseListDataDao;
import com.seniorproject.kabigonb.mahanoi.dao.ProviderDataDao;
import com.seniorproject.kabigonb.mahanoi.manager.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("unused")
public class CloseServiceSelectedFragment extends Fragment {

    CloseListDataDao dao;

    TextView tvCloseService_serviceType,tvCloseService_providerName
            ,tvCloseService_providerEmail,tvCloseService_providerPhone,tvCloseService_providerDetail;

    public CloseServiceSelectedFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CloseServiceSelectedFragment newInstance(CloseListDataDao dao) {
        CloseServiceSelectedFragment fragment = new CloseServiceSelectedFragment();
        Bundle args = new Bundle();
        args.putParcelable("dao",dao);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        dao = getArguments().getParcelable("dao");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_close_service_selected, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        tvCloseService_serviceType = rootView.findViewById(R.id.tvCloseService_serviceType);
        tvCloseService_providerName = rootView.findViewById(R.id.tvCloseService_providerName);
        tvCloseService_providerEmail = rootView.findViewById(R.id.tvCloseService_providerEmail);
        tvCloseService_providerPhone = rootView.findViewById(R.id.tvCloseService_providerPhone);
        tvCloseService_providerDetail = rootView.findViewById(R.id.tvCloseService_providerDetail);

        switch(dao.getTypeService())
        {
            case 1:
                tvCloseService_serviceType.setText("Food Service");
                break;
            case 2:
                tvCloseService_serviceType.setText("Electric Service");
                break;
            case 3:
                tvCloseService_serviceType.setText("Plumbing Service");
                break;
            case 4:
                tvCloseService_serviceType.setText("Cleaning Service");
                break;
                default:tvCloseService_serviceType.setText("");
        }

        loadProviderData();
    }

    private void loadProviderData() {
        Call<ProviderDataDao> call = HttpManager.getInstance().getService().showProvider(callForm());
        call.enqueue(loadProviderCallBack);
    }

    private ProviderDataDao callForm() {
        ProviderDataDao providerDataDao = new ProviderDataDao();
        SharedPreferences prefs = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);

        providerDataDao.setProviderUserName(dao.getProviderName());
        providerDataDao.setToken(prefs.getString("token",null));

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

    Callback<ProviderDataDao> loadProviderCallBack = new Callback<ProviderDataDao>() {
        @Override
        public void onResponse(Call<ProviderDataDao> call, Response<ProviderDataDao> response) {

            if(response.isSuccessful())
            {
                ProviderDataDao providerDataDao = response.body();
                if(providerDataDao.getErrorMessage() != null)
                {
                    Toast.makeText(Contextor.getInstance().getContext()
                            ,providerDataDao.getErrorMessage()
                            ,Toast.LENGTH_SHORT).show();
                }
                else {
                    textViewDataSet(providerDataDao);
                }
            }
            else
            {
                Toast.makeText(Contextor.getInstance().getContext()
                        , response.errorBody().toString()
                        , Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(Call<ProviderDataDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext()
                    , t.getMessage()
                    , Toast.LENGTH_SHORT).show();
        }
    };

    private void textViewDataSet(ProviderDataDao providerDataDao) {

        tvCloseService_providerName.setText(providerDataDao.getFirstName() + " " + providerDataDao.getLastName());
        tvCloseService_providerEmail.setText(providerDataDao.getEmail());
        tvCloseService_providerPhone.setText(providerDataDao.getTelephoneNumber());
        tvCloseService_providerDetail.setText(providerDataDao.getDetail());

    }

}
