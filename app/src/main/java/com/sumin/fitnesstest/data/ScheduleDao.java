package com.sumin.fitnesstest.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ScheduleDao {
    @Query("SELECT * FROM schedule ORDER BY dayOfWeek")
    LiveData<List<ScheduleEntry>> getAllSchedule();

    @Insert
    void insertScheduleEntry(ScheduleEntry scheduleEntry);

    @Query("DELETE FROM schedule")
    void deleteAllData();
}
