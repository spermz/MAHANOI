package com.seniorproject.kabigonb.mahanoi.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.dao.RequestFormDao;
import com.seniorproject.kabigonb.mahanoi.manager.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CleaningServiceRequestFragment extends Fragment implements View.OnClickListener, Callback<RequestFormDao> {

    EditText service_cleaning_serviceType,service_cleaning_servicePlace,service_cleaning_amount,service_cleaning_details;
    Button btnPlumberServiceRequest;

    public CleaningServiceRequestFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CleaningServiceRequestFragment newInstance() {
        CleaningServiceRequestFragment fragment = new CleaningServiceRequestFragment();
        Bundle args = new Bundle();
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
        View rootView = inflater.inflate(R.layout.fragment_cleaning_service_request, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        service_cleaning_serviceType = rootView.findViewById(R.id.service_cleaning_serviceType);
        service_cleaning_servicePlace = rootView.findViewById(R.id.service_cleaning_servicePlace);
        service_cleaning_amount = rootView.findViewById(R.id.service_cleaning_amount);
        service_cleaning_details = rootView.findViewById(R.id.service_cleaning_details);

        btnPlumberServiceRequest = rootView.findViewById(R.id.btnPlumberServiceRequest);
        btnPlumberServiceRequest.setOnClickListener(this);

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

        if(v == btnPlumberServiceRequest)
        {

            if(service_cleaning_serviceType.getText().toString().equals(""))
            {
                Toast.makeText(Contextor.getInstance().getContext(),"Please fill kind of work.",Toast.LENGTH_SHORT).show();
            }
            else if(service_cleaning_servicePlace.getText().toString().equals(""))
            {
                Toast.makeText(Contextor.getInstance().getContext(),"Please fill service place.",Toast.LENGTH_SHORT).show();
            }
            else if(service_cleaning_amount.getText().toString().equals(""))
            {
                Toast.makeText(Contextor.getInstance().getContext(),"Please fill number of rooms.",Toast.LENGTH_SHORT).show();
            }
            else{
                btnPlumberServiceRequest.setEnabled(false);
                Call<RequestFormDao> call = HttpManager.getInstance().getService().userRequest(requestDaoForm());
                call.enqueue(this);
            }

        }

    }

    private RequestFormDao requestDaoForm() {

        RequestFormDao dao = new RequestFormDao();
        SharedPreferences prefs = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);

        dao.setToken(prefs.getString("token",null));
        dao.setUserName(prefs.getString("userName",null));

        dao.setTypeInfo(service_cleaning_serviceType.getText().toString());
        dao.setPlaceType(service_cleaning_servicePlace.getText().toString());
        dao.setAmount(service_cleaning_amount.getText().toString());
        dao.setMoreDetail(service_cleaning_details.getText().toString());
        dao.setTypeService(4);

        return dao;
    }

    @Override
    public void onResponse(Call<RequestFormDao> call, Response<RequestFormDao> response) {

        btnPlumberServiceRequest.setEnabled(true);

        if(response.isSuccessful())
        {
            RequestFormDao dao = response.body();

            if(dao.getErrorMessage() != null)
            {
                Toast.makeText(Contextor.getInstance().getContext(), dao.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(Contextor.getInstance().getContext(), "Request has been sent", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

        }
        else {

            Toast.makeText(Contextor.getInstance().getContext(),response.errorBody().toString(),Toast.LENGTH_SHORT);

        }
    }

    @Override
    public void onFailure(Call<RequestFormDao> call, Throwable t) {

        btnPlumberServiceRequest.setEnabled(true);

        Toast.makeText(Contextor.getInstance().getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

    }
}
