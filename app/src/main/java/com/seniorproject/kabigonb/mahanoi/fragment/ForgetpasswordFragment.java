package com.seniorproject.kabigonb.mahanoi.fragment;

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
import com.seniorproject.kabigonb.mahanoi.dao.ChangePasswordDao;
import com.seniorproject.kabigonb.mahanoi.manager.HttpManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgetpasswordFragment extends Fragment implements View.OnClickListener {

    Button btnResetPassword;
    EditText etCitizenId_forget,etPassword_forget,etConfirmPassword_forget,etUserName_forget;

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
        etCitizenId_forget = rootView.findViewById(R.id.etCitizenId_forget);
        etPassword_forget = rootView.findViewById(R.id.etPassword_forget);
        etConfirmPassword_forget = rootView.findViewById(R.id.etConfirmPassword_forget);
        etUserName_forget = rootView.findViewById(R.id.etUserName_forget);

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
    public void onClick(View v) {
        if( v == btnResetPassword)
        {
            btnResetPassword.setEnabled(false);

            Call<ChangePasswordDao> call = HttpManager.getInstance().getService().resetPassword(resetForm());
            call.enqueue(resetCallBack);
        }
    }

    private ChangePasswordDao resetForm() {
        ChangePasswordDao dao = new ChangePasswordDao();

        dao.setCitizenId(etCitizenId_forget.getText().toString());
        dao.setUserName(etUserName_forget.getText().toString());
        dao.setPassword(etPassword_forget.getText().toString());
        dao.setConfirmPassword(etConfirmPassword_forget.getText().toString());

        return dao;

    }

    Callback<ChangePasswordDao> resetCallBack = new Callback<ChangePasswordDao>() {
        @Override
        public void onResponse(Call<ChangePasswordDao> call, Response<ChangePasswordDao> response) {

            btnResetPassword.setEnabled(true);

            if(response.isSuccessful())
            {
                ChangePasswordDao dao = response.body();

                if(dao.getErrorMessage() != null)
                {
                    Toast.makeText(Contextor.getInstance().getContext()
                            ,dao.getErrorMessage()
                            ,Toast.LENGTH_SHORT)
                            .show();
                }

                else if(!dao.getStatusMessage().equals("เปลี่ยนรหัสผ่านแล้ว"))
                {
                    Toast.makeText(Contextor.getInstance().getContext()
                            ,dao.getStatusMessage()
                            ,Toast.LENGTH_SHORT)
                            .show();
                }

                else
                {
                    Toast.makeText(Contextor.getInstance().getContext()
                            ,dao.getStatusMessage()
                            ,Toast.LENGTH_SHORT)
                            .show();

                    getFragmentManager().beginTransaction()
                            .replace(R.id.contentContainer,MainFragment.newInstance())
                            .commit();
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
        public void onFailure(Call<ChangePasswordDao> call, Throwable t) {

            btnResetPassword.setEnabled(true);
            Toast.makeText(Contextor.getInstance().getContext()
                    ,t.toString()
                    ,Toast.LENGTH_SHORT)
                    .show();

        }
    };
}
