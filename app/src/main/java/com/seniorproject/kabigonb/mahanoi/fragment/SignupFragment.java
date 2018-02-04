package com.seniorproject.kabigonb.mahanoi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.dao.RegisterDao;
import com.seniorproject.kabigonb.mahanoi.manager.HttpManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class SignupFragment extends Fragment implements View.OnClickListener, Callback<RegisterDao> {

    Button btnRegister;
    EditText etEmail,etPassword,etName,etLastName,etCitizenId,etNumber,etUserName;
    public SignupFragment() {
        super();
    }

    public static SignupFragment newInstance() {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        initInstances(rootView);
        btnRegister.setOnClickListener(this);

        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnRegister =   rootView.findViewById(R.id.btnRegister);
        etEmail = rootView.findViewById(R.id.etEmail);
        etPassword  =   rootView.findViewById(R.id.etPassword);
        etName      =   rootView.findViewById(R.id.etName);
        etLastName  =   rootView.findViewById(R.id.etLastName);
        etCitizenId =   rootView.findViewById(R.id.etCitizenId);
        etNumber    =   rootView.findViewById(R.id.etPhoneNumber);
        etUserName  =   rootView.findViewById(R.id.etUserName);

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

        if(v == btnRegister) {

            Call<RegisterDao> call = HttpManager.getInstance().getService().registerUser(regFormDAO());
            call.enqueue(this);

            getFragmentManager().beginTransaction().
                    replace(R.id.contentContainer, MainFragment.newInstance())
                    .commit();
        }

        }

    private RegisterDao regFormDAO() {

        RegisterDao registerForm = new RegisterDao();
        registerForm.setUsername(etUserName.getText().toString());
        registerForm.setEmail(etEmail.getText().toString());
        registerForm.setPassword(etPassword.getText().toString());
        registerForm.setFirstname(etName.getText().toString());
        registerForm.setLastname(etLastName.getText().toString());
        registerForm.setCitizenId(etCitizenId.getText().toString());
        registerForm.setNumber(etNumber.getText().toString());
        return registerForm;

    }

    @Override
    public void onResponse(Call<RegisterDao> call, Response<RegisterDao> response) {

        if(response.isSuccessful())
        {
            RegisterDao signupResponse = response.body();
            if(signupResponse.getErrorMessage() != null)
            {
                Toast.makeText(getActivity(), signupResponse.getErrorMessage(), Toast.LENGTH_LONG).show();
            }
            else if(signupResponse.getStatusMessage() != null)
            {
                Toast.makeText(getActivity(), signupResponse.getStatusMessage(), Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getActivity(), "Register Successful", Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction()
                                    .replace(R.id.contentContainer,MainFragment.newInstance())
                                    .commit();
            }

        }
        else
        {
            try {
                Toast.makeText(getActivity(),response.errorBody().string(),Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onFailure(Call<RegisterDao> call, Throwable t) {
        Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_LONG).show();
    }
}

