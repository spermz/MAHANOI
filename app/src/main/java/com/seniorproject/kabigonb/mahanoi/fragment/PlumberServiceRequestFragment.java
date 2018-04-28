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
public class PlumberServiceRequestFragment extends Fragment implements View.OnClickListener, Callback<RequestFormDao> {

    RadioGroup service_plumber_radioGroup;

    EditText service_plumber_plumbingSystemPart
            ,service_plumber_problem
            ,service_plumber_servicePlace
            ,service_plumber_detail;

    Button  btnPlumberServiceRequest;

    public PlumberServiceRequestFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static PlumberServiceRequestFragment newInstance() {
        PlumberServiceRequestFragment fragment = new PlumberServiceRequestFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_plumber_service_request, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        service_plumber_radioGroup = rootView.findViewById(R.id.service_plumber_radioGroup);

        service_plumber_plumbingSystemPart = rootView.findViewById(R.id.service_plumber_plumbingSystemPart);
        service_plumber_problem = rootView.findViewById(R.id.service_plumber_problem);
        service_plumber_servicePlace = rootView.findViewById(R.id.service_plumber_servicePlace);
        service_plumber_detail = rootView.findViewById(R.id.service_plumber_detail);

        btnPlumberServiceRequest = rootView.findViewById(R.id.btnPlumberServiceRequest);
        btnPlumberServiceRequest.setOnClickListener(this);

    }

    private RequestFormDao daoRequestForm() {

        RequestFormDao dao = new RequestFormDao();
        SharedPreferences prefs = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);

        dao.setToken(prefs.getString("token",null));
        dao.setUserName(prefs.getString("userName",null));

        dao.setProblem(service_plumber_problem.getText().toString());
        dao.setPlaceType(service_plumber_servicePlace.getText().toString());
        dao.setTypeService(3);
        dao.setToolsCheck(service_plumber_plumbingSystemPart.getText().toString());
        dao.setMoreDetail(service_plumber_detail.getText().toString());

        switch(service_plumber_radioGroup.getCheckedRadioButtonId())
        {
            case R.id.rdbPlumber1:
                dao.setTypeInfo("Install");
                break;
            case R.id.rdbPlumber2:
                dao.setTypeInfo("Repair");
                break;
            case R.id.rdbPlumber3:
                dao.setTypeInfo("Replace");
                break;
            case R.id.rdbPlumber4:
                dao.setTypeInfo("Removal");
                break;
            case R.id.rdbPlumber5:
                dao.setTypeInfo("Others");
                break;
            default:
                dao.setTypeInfo("");
        }

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
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    @Override
    public void onClick(View v) {

        if(v == btnPlumberServiceRequest)
        {
            if(service_plumber_radioGroup.getCheckedRadioButtonId() == -1)
            {
                Toast.makeText(Contextor.getInstance().getContext(),"Please select kind of work.",Toast.LENGTH_SHORT).show();
            }
            else if(service_plumber_plumbingSystemPart.getText().toString().equals(""))
            {
                Toast.makeText(Contextor.getInstance().getContext(),"Please fill the system part.",Toast.LENGTH_SHORT).show();
            }
            else if(service_plumber_problem.getText().toString().equals(""))
            {
                Toast.makeText(Contextor.getInstance().getContext(),"Please fill the problem.",Toast.LENGTH_SHORT).show();
            }
            else if(service_plumber_servicePlace.getText().toString().equals(""))
            {
                Toast.makeText(Contextor.getInstance().getContext(),"Please fill the service place type.",Toast.LENGTH_SHORT).show();
            }
            else
            {
                btnPlumberServiceRequest.setEnabled(false);
                Call<RequestFormDao> call = HttpManager.getInstance().getService().userRequest(daoRequestForm());
                call.enqueue(this);
            }
        }

    }

    @Override
    public void onResponse(Call<RequestFormDao> call, Response<RequestFormDao> response) {

        btnPlumberServiceRequest.setEnabled(true);

        if(response.isSuccessful())
        {
            RequestFormDao dao = response.body();

            if(dao.getErrorMessage() != null)
            {

                Toast.makeText(Contextor.getInstance().getContext(),dao.getErrorMessage(),Toast.LENGTH_SHORT).show();

            }

            else
            {
                Toast.makeText(Contextor.getInstance().getContext(), "Request has been sent", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

        }

        else
        {
            Toast.makeText(Contextor.getInstance().getContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(Call<RequestFormDao> call, Throwable t) {

        btnPlumberServiceRequest.setEnabled(true);
        Toast.makeText(Contextor.getInstance().getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
    }
}
