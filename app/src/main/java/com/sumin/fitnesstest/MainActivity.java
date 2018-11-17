package com.sumin.fitnesstest;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sumin.fitnesstest.data.ScheduleEntry;
import com.sumin.fitnesstest.utils.JSONUtils;
import com.sumin.fitnesstest.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONArray> {

    //Views
    private ScheduleAdapter scheduleAdapter;
    private SwipeRefreshLayout swipeRecyclerViewSchedule;
    private ProgressBar progressBarLoading;

    //Loading data
    private LoaderManager loaderManager;
    private static final int LOADER_ID = 137;
    private MainViewModel mainViewModel;

    //cache
    private boolean wasLoaded = false;
    private final String EXTRA_WAS_LOADED = "was loaded";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerViewSchedule = findViewById(R.id.recyclerViewSchedule);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        recyclerViewSchedule.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        scheduleAdapter = new ScheduleAdapter(this);
        recyclerViewSchedule.setAdapter(scheduleAdapter);
        loaderManager = LoaderManager.getInstance(this);
        swipeRecyclerViewSchedule = findViewById(R.id.swipeRecyclerViewSchedule);
        swipeRecyclerViewSchedule.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startLoading();
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_WAS_LOADED)) {
            wasLoaded = savedInstanceState.getBoolean(EXTRA_WAS_LOADED);
        }
        if (!wasLoaded) {
            startLoading();
        }
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getAllSchedule().observe(this, new Observer<List<ScheduleEntry>>() {
            @Override
            public void onChanged(@Nullable List<ScheduleEntry> scheduleEntries) {
                scheduleAdapter.setScheduleEntries(scheduleEntries);
            }
        });
    }

    private void startLoading() {
        if (NetworkUtils.isInternetConnection(this)) {
            loaderManager.restartLoader(LOADER_ID, null, this);
        } else {
            Toast.makeText(this, R.string.warning_no_internet_connection, Toast.LENGTH_SHORT).show();
            hideLoadingProcess();
        }
    }

    private void hideLoadingProcess() {
        swipeRecyclerViewSchedule.setRefreshing(false);
        progressBarLoading.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public Loader<JSONArray> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new NetworkUtils.JSONFromWebLoader(this, new NetworkUtils.JSONFromWebLoader.OnLoadingProcessListener() {
            @Override
            public void onProcessStart() {
                if (!swipeRecyclerViewSchedule.isRefreshing()) {
                    progressBarLoading.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONArray> loader, JSONArray jsonArray) {
        if (jsonArray == null) {
            Toast.makeText(this, R.string.warning_error_with_loading, Toast.LENGTH_SHORT).show();
        }
        List<ScheduleEntry> scheduleEntries = JSONUtils.getScheduleFromJSON(jsonArray);
        if (!scheduleEntries.isEmpty()) {
            mainViewModel.deleteAllData();
            for (ScheduleEntry scheduleEntry : scheduleEntries) {
                mainViewModel.insertNewScheduleEntry(scheduleEntry);
            }
        }
        wasLoaded = true;
        hideLoadingProcess();
        loaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONArray> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_WAS_LOADED, wasLoaded);
    }
}
