package com.seniorproject.kabigonb.mahanoi.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.dao.CloseListDataDao;
import com.seniorproject.kabigonb.mahanoi.dao.OpnListDataDao;
import com.seniorproject.kabigonb.mahanoi.dao.TokenDao;
import com.seniorproject.kabigonb.mahanoi.fragment.AccountFragment;
import com.seniorproject.kabigonb.mahanoi.fragment.CloseFragment;
import com.seniorproject.kabigonb.mahanoi.fragment.OpenFragment;
import com.seniorproject.kabigonb.mahanoi.fragment.RequestFragment;
import com.seniorproject.kabigonb.mahanoi.fragment.ServiceFragment;
import com.seniorproject.kabigonb.mahanoi.manager.HttpManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

public class Main2Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
        , Callback<TokenDao>
        , OpenFragment.OpenFragmentListener
        , CloseFragment.CloseFragmentListener{

    BottomNavigationView bnvMenu;
    Toolbar toolbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();

        Bundle bundle = intent.getBundleExtra("tokenBundle");
        TokenDao tokenDao = new TokenDao();
        tokenDao.setToken(bundle.getString("token"));
        tokenDao.setUserName(bundle.getString("userName"));

        saveToken(tokenDao);

        initInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer2,ServiceFragment.newInstance())
                    .commit();
            bnvMenu.setSelectedItemId(R.id.service);
        }

        bnvMenu.setOnNavigationItemSelectedListener(this);

    }

    private void saveToken(TokenDao tokenDao) {

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token",tokenDao.getToken());
        editor.putString("userName",tokenDao.getUserName());
        editor.apply();

    }

    private void initInstance() {
        toolbar2 =  findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        bnvMenu = findViewById(R.id.bnvMenu);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.service :
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer2,ServiceFragment.newInstance())
                        .commit();
                return true;
            case R.id.request :
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer2, RequestFragment.newInstance())
                        .commit();
                return true;
            case R.id.account : //TODO: Implement profile update logic
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer2, AccountFragment.newInstance())
                        .commit();
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menuLoguot)
        {

            Call<TokenDao> call = HttpManager.getInstance().getService().userLogout(makeLogOutToken());
            call.enqueue(this);
        }

        return true;
    }

    private TokenDao makeLogOutToken() {

        TokenDao tokenDao = new TokenDao();
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("token", Context.MODE_PRIVATE);

        String value = prefs.getString("token",null);
        String value2 = prefs.getString("userName",null);

        tokenDao.setToken(value);
        tokenDao.setUserName(value2);
        return tokenDao;
    }

    @Override
    public void onResponse(Call<TokenDao> call, Response<TokenDao> response) {
        if(response.isSuccessful())
        {
            TokenDao token = response.body();
            if(token.getErrorMessage() != null)
            {
                Toast.makeText(getApplicationContext(),token.getErrorMessage(),Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),token.getStatusMessage(),Toast.LENGTH_LONG).show();
                removeToken();
                Intent intentLogIn = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentLogIn);
                finish();
            }
        }
        else
        {
            try {
                Toast.makeText(getApplicationContext(),response.errorBody().string(),Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeToken() {

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token","");
        editor.putString("userName","");
        editor.apply();
    }

    @Override
    public void onFailure(Call<TokenDao> call, Throwable t) {
        Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOfferItemClicked(OpnListDataDao dao) {

        Intent intent = new Intent(Main2Activity.this,OpenServiceSelectedActivity.class);
        intent.putExtra("dao",dao);
        startActivity(intent);

    }

    @Override
    public void onRequestItemClicked(CloseListDataDao dao) {

        Intent intent = new Intent(Main2Activity.this,CloseServiceSelectedActivity.class);
        intent.putExtra("dao",dao);
        startActivity(intent);
    }
}
/*
        tlRequestTab.addTab(tlRequestTab.newTab().setText("Open"));
        tlRequestTab.addTab(tlRequestTab.newTab().setText("Close"));
        tlRequestTab.removeAllTabs();*/