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

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



@SuppressWarnings("unused")
public class FoodServiceRequestFragment extends Fragment implements View.OnClickListener, Callback<RequestFormDao> {

    Button btnFoodServiceRequestNext;
    EditText etFoodServiceRequest_detail,etFoodServiceRequest_amount;
    RadioGroup service_food_radioGroup;

    public FoodServiceRequestFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static FoodServiceRequestFragment newInstance() {
        FoodServiceRequestFragment fragment = new FoodServiceRequestFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_food_service_request, container, false);
        initInstances(rootView, savedInstanceState);

        btnFoodServiceRequestNext.setOnClickListener(this);

        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnFoodServiceRequestNext = rootView.findViewById(R.id.btnFoodServiceRequestNext);
        etFoodServiceRequest_detail = rootView.findViewById(R.id.etFoodServiceRequest_detail);
        etFoodServiceRequest_amount = rootView.findViewById(R.id.etFoodServiceRequest_amount);
        service_food_radioGroup = rootView.findViewById(R.id.service_food_radioGroup);

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
        if(v == btnFoodServiceRequestNext)
        {
            if(service_food_radioGroup.getCheckedRadioButtonId() == -1)
            {
                Toast.makeText(Contextor.getInstance().getContext(),"Please select kind of work.",Toast.LENGTH_SHORT).show();
            }
            else if(etFoodServiceRequest_amount.getText().toString().equals(""))

            {
                Toast.makeText(Contextor.getInstance().getContext(),"Please fill some menu.",Toast.LENGTH_SHORT).show();
            }
            else
            {
                btnFoodServiceRequestNext.setEnabled(false);
                Call<RequestFormDao> call = HttpManager.getInstance().getService().userRequest(getRequestForm());
                call.enqueue(this);
            }
        }
    }

    private RequestFormDao getRequestForm() {
        RequestFormDao formDao = new RequestFormDao() ;
        SharedPreferences prefs = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);

        String value = prefs.getString("token",null);
        String value2 = prefs.getString("userName",null);

        switch( service_food_radioGroup.getCheckedRadioButtonId() )
        {
            case R.id.rdbFood1:
                formDao.setTypeInfo("Ice Cream");
                break;
            case R.id.rdbFood2:
                formDao.setTypeInfo("Roti");
                break;
            case R.id.rdbFood3:
                formDao.setTypeInfo("Fried sausage");
                break;
            case R.id.rdbFood4:
                formDao.setTypeInfo("Legumes");
                break;
            default:
                formDao.setTypeInfo("");
        }


        formDao.setMoreDetail(etFoodServiceRequest_detail.getText().toString());
        formDao.setAmount(etFoodServiceRequest_amount.getText().toString());
        formDao.setToken(value);
        formDao.setUserName(value2);
        formDao.setTypeService(1);
        return formDao;
    }


    @Override
    public void onResponse(Call<RequestFormDao> call, Response<RequestFormDao> response) {

        btnFoodServiceRequestNext.setEnabled(true);

        if(response.isSuccessful())
        {
            RequestFormDao Response = response.body();
            if(Response.getErrorMessage() != null)
            {
                Toast.makeText(Contextor.getInstance().getContext(), Response.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(Contextor.getInstance().getContext(), "Request has been sent", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

        }
        else
        {
            try {
                Toast.makeText(Contextor.getInstance().getContext(),response.errorBody().string(),Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onFailure(Call<RequestFormDao> call, Throwable t) {
        btnFoodServiceRequestNext.setEnabled(true);
        Toast.makeText(Contextor.getInstance().getContext(),t.toString(),Toast.LENGTH_SHORT).show();
    }
}
