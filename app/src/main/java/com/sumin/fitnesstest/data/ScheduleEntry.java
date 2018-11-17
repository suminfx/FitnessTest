package com.sumin.fitnesstest.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "schedule")
public class ScheduleEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String teacherName;
    private String nameOfRoom;
    private String timeStart;
    private String timeEnd;
    private int dayOfWeek;

    public ScheduleEntry(int id, String title, String description, String teacherName, String nameOfRoom, String timeStart, String timeEnd, int dayOfWeek) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.teacherName = teacherName;
        this.nameOfRoom = nameOfRoom;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.dayOfWeek = dayOfWeek;
    }

    @Ignore
    public ScheduleEntry(String title, String description, String teacherName, String nameOfRoom, String timeStart, String timeEnd, int dayOfWeek) {
        this.title = title;
        this.description = description;
        this.teacherName = teacherName;
        this.nameOfRoom = nameOfRoom;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.dayOfWeek = dayOfWeek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getNameOfRoom() {
        return nameOfRoom;
    }

    public void setNameOfRoom(String nameOfRoom) {
        this.nameOfRoom = nameOfRoom;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
