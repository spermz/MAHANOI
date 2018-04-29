package com.seniorproject.kabigonb.mahanoi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.activity.Main2Activity;
import com.seniorproject.kabigonb.mahanoi.dao.LoginDao;
import com.seniorproject.kabigonb.mahanoi.dao.TokenDao;
import com.seniorproject.kabigonb.mahanoi.manager.HttpManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainFragment extends Fragment implements View.OnClickListener, Callback<TokenDao> {

    Button btnSignUp;
    Button btnLogin;
    Button btnForgetPassword;
    TextView etUsername,etPassword;
    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);

        btnSignUp.setOnClickListener(this);
        btnForgetPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        return rootView;

    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnSignUp =  rootView.findViewById(R.id.btnSignUp);
        btnLogin =  rootView.findViewById(R.id.btnLogin);
        btnForgetPassword =  rootView.findViewById(R.id.btnForgetPassword);

        etUsername = rootView.findViewById(R.id.etUsername_login);
        etPassword = rootView.findViewById(R.id.etPassword_login);
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
        if(v == btnLogin) { // TODO: improve Authentication logic

            Call<TokenDao> call = HttpManager.getInstance().getService().userLogin(loginFormDAO());
            call.enqueue(this);
            btnLogin.setEnabled(false);
            //Intent intentLogIn = new Intent(getActivity(), Main2Activity.class);
            //startActivity(intentLogIn);
        }
        else if(v == btnSignUp) {
            getFragmentManager().beginTransaction().
                    replace(R.id.contentContainer, SignupFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
        else if(v == btnForgetPassword) {// TODO: implement password reset logic

            getFragmentManager().beginTransaction()
                    .replace(R.id.contentContainer,ForgetpasswordFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }

    }

    private LoginDao loginFormDAO() {

        LoginDao loginForm = new LoginDao();
        loginForm.setUsername(etUsername.getText().toString());
        loginForm.setPassword(etPassword.getText().toString());

        return loginForm;
    }


    @Override
    public void onResponse(Call<TokenDao> call, Response<TokenDao> response) {
        btnLogin.setEnabled(true);
        if(response.isSuccessful())
        {
            TokenDao token = response.body();
            if(token.getErrorMessage() != null)
            {
                Toast.makeText(Contextor.getInstance().getContext(),token.getErrorMessage(),Toast.LENGTH_SHORT).show();
            }
            else if(token.getStatusMessage() != null)
            {
                Toast.makeText(Contextor.getInstance().getContext(),token.getStatusMessage(),Toast.LENGTH_SHORT).show();
            }
            else
            {
                Bundle bundle = new Bundle();
                bundle.putString("token",token.getToken());
                bundle.putString("userName",etUsername.getText().toString());

                Intent intentLogIn = new Intent(getActivity(), Main2Activity.class);
                intentLogIn.putExtra("tokenBundle",bundle);

                startActivity(intentLogIn);
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
    public void onFailure(Call<TokenDao> call, Throwable t) {
        btnLogin.setEnabled(true);
        Toast.makeText(Contextor.getInstance().getContext(),t.toString(),Toast.LENGTH_SHORT).show();
    }
}

/*        if(response.isSuccessful())
        {
            Toast.makeText(getActivity(),call.getClass().toString(),Toast.LENGTH_LONG).show();
         //   Intent intentLogIn = new Intent(getActivity(), Main2Activity.class);
         //   startActivity(intentLogIn);
        }
        else
        {
            try {
                Toast.makeText(getActivity(),response.errorBody().string(),Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

/*        Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_LONG).show();*/