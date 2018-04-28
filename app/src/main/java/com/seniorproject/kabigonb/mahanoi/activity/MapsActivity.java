package com.seniorproject.kabigonb.mahanoi.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.dao.MatchMakingDao;
import com.seniorproject.kabigonb.mahanoi.dao.OpenListDao;
import com.seniorproject.kabigonb.mahanoi.dao.OpnListDataDao;
import com.seniorproject.kabigonb.mahanoi.manager.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
        , GoogleMap.OnMapClickListener
        , GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener
        , OnCompleteListener
        , View.OnClickListener
        , Callback<MatchMakingDao> {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private SupportMapFragment mapFragment;
    private Button btnResponseConfirm;
    private OpnListDataDao dao;

    private Double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        dao = getIntent().getParcelableExtra("dao");

        initInstance();
        requestLocation();

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        btnResponseConfirm.setOnClickListener(this);

    }

    private void requestLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(getApplicationContext()).requestLocationUpdates(locationRequest, new LocationCallback(), Looper.myLooper());
    }


    private void initInstance() {
        //Init Instance here
        btnResponseConfirm = findViewById(R.id.btnResponseConfirm);

    }

    @Override
    protected void onStart() {
        super.onStart();

        googleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng position = new LatLng(lat, lng);

        mMap.setOnMapClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17));
        mMap.addMarker(new MarkerOptions().position(position)
                .draggable(true)
                .title("You are Here"));


    }


    @Override
    public void onMapClick(LatLng latLng) {

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng)
                .draggable(true)
                .title("This is the place for service"));
        lat = latLng.latitude;
        lng = latLng.longitude;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        FusedLocationProviderClient mFuseLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task location = mFuseLocationProviderClient.getLastLocation();
        location.addOnCompleteListener(this);

    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onComplete(@NonNull Task task) {

        if(task.isSuccessful())
        {
            Location currentLocation = (Location) task.getResult();
            lat = currentLocation.getLatitude();
            lng = currentLocation.getLongitude();
            mapFragment.getMapAsync(this);

        }
        else
        {
            Toast.makeText(getApplicationContext()
                    ,"Current Location is null"
                    ,Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void onClick(View v) {

        if(v == btnResponseConfirm)
        {
            btnResponseConfirm.setEnabled(false);
            Call<MatchMakingDao> call = HttpManager.getInstance().getService().userConfirmOffer(matchMakingForm());
            call.enqueue(this);
        }

    }

    private MatchMakingDao matchMakingForm() {

        MatchMakingDao matchMakingDao = new MatchMakingDao();
        SharedPreferences prefs = getSharedPreferences("token",MODE_PRIVATE);

        matchMakingDao.setOfferId(dao.getOfferId());
        matchMakingDao.setToken(prefs.getString("token",null));
        matchMakingDao.setUserName(prefs.getString("userName",null));
        matchMakingDao.setLongtitude(lng);
        matchMakingDao.setLatitude(lat);

        return matchMakingDao;

    }

    @Override
    public void onResponse(Call<MatchMakingDao> call, Response<MatchMakingDao> response) {

        btnResponseConfirm.setEnabled(true);

        if(response.isSuccessful())
        {
            MatchMakingDao dao = response.body();

            if(dao.getErrorMessage() != null)
            {
                Toast.makeText(getApplicationContext()
                        ,dao.getErrorMessage()
                        ,Toast.LENGTH_SHORT)
                        .show();
            }

            else if(dao.getStatusMessage().equals("save request"))
            {
                Toast.makeText(getApplicationContext()
                        ,"Service Accepted"
                        ,Toast.LENGTH_SHORT)
                        .show();
                finish();
            }

            else
            {
                Toast.makeText(getApplicationContext()
                        ,dao.getStatusMessage()
                        ,Toast.LENGTH_SHORT)
                        .show();
            }

        }
        else
        {
            Toast.makeText(getApplicationContext()
                    ,response.errorBody().toString()
                    ,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(Call<MatchMakingDao> call, Throwable t) {

        btnResponseConfirm.setEnabled(true);

        Toast.makeText(getApplicationContext()
                ,t.getMessage()
                ,Toast.LENGTH_SHORT).show();

    }
}
