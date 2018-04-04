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
import android.widget.RadioGroup;
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
public class ElectricianServiceRequestFragment extends Fragment implements View.OnClickListener, Callback<RequestFormDao> {

    EditText service_electrician_problem
            ,service_electrician_servicePlace
            ,service_electrician_detail;

    RadioGroup service_electrician_radioGroup;

    Button btnElectricianServiceRequest;

    public ElectricianServiceRequestFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ElectricianServiceRequestFragment newInstance() {
        ElectricianServiceRequestFragment fragment = new ElectricianServiceRequestFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_electrician_service_request, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        service_electrician_problem = rootView.findViewById(R.id.service_electrician_problem);
        service_electrician_servicePlace = rootView.findViewById(R.id.service_electrician_servicePlace);
        service_electrician_detail = rootView.findViewById(R.id.service_electrician_detail);

        service_electrician_radioGroup = rootView.findViewById(R.id.service_electrician_radioGroup);

        btnElectricianServiceRequest = rootView.findViewById(R.id.btnElectricianServiceRequest);

        btnElectricianServiceRequest.setOnClickListener(this);


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

        if(v == btnElectricianServiceRequest)
        {
            Call<RequestFormDao> call = HttpManager.getInstance().getService().userRequest(requestDaoForm());
            call.enqueue(this);
        }

    }

    private RequestFormDao requestDaoForm() {
        RequestFormDao dao = new RequestFormDao();
        SharedPreferences prefs = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);

        dao.setUserName(prefs.getString("userName",null));
        dao.setToken(prefs.getString("token",null));
        dao.setTypeService(2);
        dao.setProblem(service_electrician_problem.getText().toString());
        dao.setPlaceType(service_electrician_servicePlace.getText().toString());
        dao.setMoreDetail(service_electrician_detail.getText().toString());

        switch (service_electrician_radioGroup.getCheckedRadioButtonId())
        {
            case R.id.rdbElectrician1:
                dao.setTypeInfo("Install");
                break;
            case R.id.rdbElectrician2:
                dao.setTypeInfo("Repair");
                break;
            case R.id.rdbElectrician3:
                dao.setTypeInfo("Troubleshoot problems");
                break;
            case R.id.rdbElectrician4:
                dao.setTypeInfo("Others");
                break;
            default:dao.setTypeInfo("");
        }

        return dao;
    }

    @Override
    public void onResponse(Call<RequestFormDao> call, Response<RequestFormDao> response) {

        if(response.isSuccessful())
        {
            RequestFormDao dao = response.body();

            if(dao.getErrorMessage() != null)
            {
                Toast.makeText(getContext(), dao.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getContext(), "Request has been sent", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

        }
        else {
            Toast.makeText(Contextor.getInstance().getContext(),response.errorBody().toString(),Toast.LENGTH_SHORT);

        }

    }

    @Override
    public void onFailure(Call<RequestFormDao> call, Throwable t) {

        Toast.makeText(Contextor.getInstance().getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

    }
}
