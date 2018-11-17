package com.sumin.fitnesstest.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ScheduleEntry.class}, version = 1, exportSchema = false)
public abstract class ScheduleDatabase extends RoomDatabase {

    private static final String DB_NAME = "schedule.db";
    private static final Object LOCK = new Object();

    private static ScheduleDatabase database;

    public static ScheduleDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context, ScheduleDatabase.class, DB_NAME).build();
        }
        return database;
    }

    public abstract ScheduleDao scheduleDao();
}
