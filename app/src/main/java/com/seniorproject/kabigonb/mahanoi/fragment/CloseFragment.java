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
import android.widget.ListView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.seniorproject.kabigonb.mahanoi.R;
import com.seniorproject.kabigonb.mahanoi.adapter.CloseServiceListAdapter;
import com.seniorproject.kabigonb.mahanoi.dao.CloseListDao;
import com.seniorproject.kabigonb.mahanoi.dao.CloseListDataDao;
import com.seniorproject.kabigonb.mahanoi.manager.CloseServiceManager;
import com.seniorproject.kabigonb.mahanoi.manager.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CloseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    public interface CloseFragmentListener {
        void onRequestItemClicked(CloseListDataDao dao);
    }

    ListView closeListView;
    CloseServiceListAdapter closeServiceListAdapter;
    SwipeRefreshLayout srlClose;

    public CloseFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CloseFragment newInstance() {
        CloseFragment fragment = new CloseFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_close, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        closeListView = rootView.findViewById(R.id.lvCloseRequest);
        srlClose = rootView.findViewById(R.id.srlClose);

        closeServiceListAdapter = new CloseServiceListAdapter();
        closeListView.setAdapter(closeServiceListAdapter);

        srlClose.setOnRefreshListener(this);
        closeListView.setOnScrollListener(this);
        closeListView.setOnItemClickListener(this);
        loadData();

    }

    private void loadData() {

        Call<CloseListDao> call = HttpManager.getInstance().getService().loadCloseService(loadListForm());
        call.enqueue(loadListResponse);

    }

    private CloseListDao loadListForm() {

        CloseListDao dao = new CloseListDao();
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

    Callback<CloseListDao> loadListResponse = new Callback<CloseListDao>() {
        @Override
        public void onResponse(Call<CloseListDao> call, Response<CloseListDao> response) {

            srlClose.setRefreshing(false);

            if(response.isSuccessful())
            {

                CloseListDao dao = response.body();

                if(dao.getErrorMessage() != null)
                {
                    Toast.makeText(Contextor.getInstance().getContext(),dao.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }

                closeServiceListAdapter.setDao(dao);
                CloseServiceManager.getInstance().setDao(dao);
                closeServiceListAdapter.notifyDataSetChanged();


            }
            else
            {
                Toast.makeText(Contextor.getInstance().getContext()
                        ,response.errorBody().toString()
                        ,Toast.LENGTH_SHORT)
                        .show();
            }




        }

        @Override
        public void onFailure(Call<CloseListDao> call, Throwable t) {

            srlClose.setRefreshing(false);

            Toast.makeText(Contextor.getInstance().getContext()
                    ,t.getMessage()
                    ,Toast.LENGTH_SHORT)
                    .show();
        }
    };

    @Override
    public void onRefresh() {

        loadData();

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        srlClose.setEnabled(firstVisibleItem == 0);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CloseListDataDao dao = CloseServiceManager.getInstance().getDao().getResult().get(position);
        CloseFragmentListener listener = (CloseFragmentListener) getActivity();
        listener.onRequestItemClicked(dao);

    }
}
