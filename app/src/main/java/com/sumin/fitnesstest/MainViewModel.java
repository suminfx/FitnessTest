package com.sumin.fitnesstest;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.sumin.fitnesstest.data.ScheduleDatabase;
import com.sumin.fitnesstest.data.ScheduleEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static ScheduleDatabase scheduleDatabase;

    public MainViewModel(@NonNull Application application) {
        super(application);
        scheduleDatabase = ScheduleDatabase.getInstance(getApplication().getApplicationContext());
    }

    public LiveData<List<ScheduleEntry>> getAllSchedule() {
        return scheduleDatabase.scheduleDao().getAllSchedule();
    }

    public void insertNewScheduleEntry(ScheduleEntry scheduleEntry) {
        new InsertDataTask().execute(scheduleEntry);
    }

    public void deleteAllData() {
        new DeleteDataTask().execute();
    }

    private static class InsertDataTask extends AsyncTask<ScheduleEntry, Void, Void> {
        @Override
        protected Void doInBackground(ScheduleEntry... scheduleEntries) {
            if (scheduleEntries != null && scheduleEntries.length > 0) {
                scheduleDatabase.scheduleDao().insertScheduleEntry(scheduleEntries[0]);
            }
            return null;
        }
    }

    private static class DeleteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            scheduleDatabase.scheduleDao().deleteAllData();
            return null;
        }
    }

}
