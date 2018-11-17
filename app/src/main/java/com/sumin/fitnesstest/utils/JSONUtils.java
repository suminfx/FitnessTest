package com.sumin.fitnesstest.utils;

import android.util.Log;

import com.sumin.fitnesstest.data.ScheduleEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtils {

    private static final String KEY_TITLE = "name";
    private static final String KEY_START_TIME = "startTime";
    private static final String KEY_END_TIME = "endTime";
    private static final String KEY_TEACHER_NAME = "teacher";
    private static final String KEY_PLACE = "place";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DAY_OF_WEEK = "weekDay";

    public static List<ScheduleEntry> getScheduleFromJSON(JSONArray jsonArray) {
        List<ScheduleEntry> scheduleEntries = new ArrayList<>();
        if (jsonArray == null) {
            return scheduleEntries;
        }
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectSchedule = jsonArray.getJSONObject(i);
                String title = jsonObjectSchedule.getString(KEY_TITLE);
                String startTime = jsonObjectSchedule.getString(KEY_START_TIME);
                String endTime = jsonObjectSchedule.getString(KEY_END_TIME);
                String teacherName = jsonObjectSchedule.getString(KEY_TEACHER_NAME);
                String nameOfRoom = jsonObjectSchedule.getString(KEY_PLACE);
                String description = jsonObjectSchedule.getString(KEY_DESCRIPTION);
                int dayOfWeek = jsonObjectSchedule.getInt(KEY_DAY_OF_WEEK);
                ScheduleEntry scheduleEntry = new ScheduleEntry(title, description, teacherName, nameOfRoom, startTime, endTime, dayOfWeek);
                scheduleEntries.add(scheduleEntry);
            }
        } catch (JSONException e) {
            Log.i("JSONUtils", "Exc");
            e.printStackTrace();
        }
        return scheduleEntries;
    }
}
