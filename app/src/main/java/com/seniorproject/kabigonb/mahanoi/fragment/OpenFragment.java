package com.seniorproject.kabigonb.mahanoi.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.adapter.OpenServiceListAdapter;
import com.seniorproject.kabigonb.mahanoi.dao.OpenListDao;
import com.seniorproject.kabigonb.mahanoi.dao.OpnListDataDao;
import com.seniorproject.kabigonb.mahanoi.manager.HttpManager;
import com.seniorproject.kabigonb.mahanoi.manager.OpenServiceManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;


@SuppressWarnings("unused")
public class OpenFragment extends Fragment implements Callback<OpenListDao>
        , SwipeRefreshLayout.OnRefreshListener
        , AbsListView.OnScrollListener
        , AdapterView.OnItemClickListener {

    public interface OpenFragmentListener {
        void onOfferItemClicked(OpnListDataDao dao);
    }

    ListView openListView;
    OpenServiceListAdapter openServiceListAdapter;
    SwipeRefreshLayout srlOpenFragment;
    public OpenFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OpenFragment newInstance() {
        OpenFragment fragment = new OpenFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_open, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        openListView = rootView.findViewById(R.id.lvOpenRequest);
        srlOpenFragment = rootView.findViewById(R.id.srlOpenFragment);

        openServiceListAdapter = new OpenServiceListAdapter();
        openListView.setAdapter(openServiceListAdapter);

        srlOpenFragment.setOnRefreshListener(this);
        openListView.setOnScrollListener(this);
        openListView.setOnItemClickListener(this);
        reloadData();
    }

    private void reloadData() {
        Call<OpenListDao> call = HttpManager.getInstance().getService().loadOpenService(openListDaoForm());
        call.enqueue(this);
    }

    private OpenListDao openListDaoForm() {

        OpenListDao dao = new OpenListDao();
        SharedPreferences prefs = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);

        dao.setToken(prefs.getString("token",null));
        dao.setUserName(prefs.getString("userName",null));

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
    public void onResponse(Call<OpenListDao> call, Response<OpenListDao> response) {

        srlOpenFragment.setRefreshing(false);

        if(response.isSuccessful())
        {
            OpenListDao dao = response.body();
            if(dao.getErrorMessage() != null)
            {
                Toast.makeText(Contextor.getInstance().getContext(),dao.getErrorMessage(),Toast.LENGTH_SHORT).show();
            }

            openServiceListAdapter.setDao(dao);
            OpenServiceManager.getInstance().setDao(dao);
            openServiceListAdapter.notifyDataSetChanged();
        }

        else {
            Toast.makeText(Contextor.getInstance().getContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(Call<OpenListDao> call, Throwable t) {

        srlOpenFragment.setRefreshing(false);
        Toast.makeText(Contextor.getInstance().getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        reloadData();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        srlOpenFragment.setEnabled(firstVisibleItem == 0);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        OpnListDataDao dao = OpenServiceManager.getInstance().getDao().getResult().get(position);
        OpenFragmentListener listener = (OpenFragmentListener) getActivity();
        listener.onOfferItemClicked(dao);
    }
}
