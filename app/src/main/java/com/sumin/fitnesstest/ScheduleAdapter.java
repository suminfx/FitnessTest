package com.sumin.fitnesstest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sumin.fitnesstest.data.ScheduleEntry;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private Context context;
    private final List<ScheduleEntry> scheduleEntries = new ArrayList<>();

    public ScheduleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_item, viewGroup, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder scheduleViewHolder, int i) {
        ScheduleEntry scheduleEntry = scheduleEntries.get(i);
        scheduleViewHolder.textViewTitle.setText(scheduleEntry.getTitle());
        scheduleViewHolder.textViewDescription.setText(scheduleEntry.getDescription());
        scheduleViewHolder.textViewTeacherName.setText(scheduleEntry.getTeacherName());
        scheduleViewHolder.textViewNumberOfRoom.setText(scheduleEntry.getNameOfRoom());
        String time = String.format("%s - %s", scheduleEntry.getTimeStart(), scheduleEntry.getTimeEnd());
        scheduleViewHolder.textViewTime.setText(time);
        String dayOfWeek;
        int numberOfDayOfWeek = scheduleEntry.getDayOfWeek();
        if (numberOfDayOfWeek >= 1 && numberOfDayOfWeek <= 7) {
            numberOfDayOfWeek--;
        } else {
            numberOfDayOfWeek = 0;
        }
        dayOfWeek = context.getResources().getStringArray(R.array.days_of_week)[numberOfDayOfWeek];
        scheduleViewHolder.textViewDayOfWeek.setText(dayOfWeek);
    }

    @Override
    public int getItemCount() {
        return scheduleEntries.size();
    }

    public List<ScheduleEntry> getScheduleEntries() {
        return scheduleEntries;
    }

    public void clearData() {
        this.scheduleEntries.clear();
    }

    public void setScheduleEntries(List<ScheduleEntry> scheduleEntries) {
        this.scheduleEntries.clear();
        this.scheduleEntries.addAll(scheduleEntries);
        notifyDataSetChanged();
    }

    public void addScheduleEntries(List<ScheduleEntry> scheduleEntries) {
        this.scheduleEntries.addAll(scheduleEntries);
        notifyDataSetChanged();
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewTeacherName;
        private TextView textViewNumberOfRoom;
        private TextView textViewTime;
        private TextView textViewDayOfWeek;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewTitle = itemView.findViewById(R.id.textViewTitle);
            this.textViewDescription = itemView.findViewById(R.id.textViewDescription);
            this.textViewTeacherName = itemView.findViewById(R.id.textViewTeacherName);
            this.textViewNumberOfRoom = itemView.findViewById(R.id.textViewNumberOfRoom);
            this.textViewTime = itemView.findViewById(R.id.textViewTime);
            this.textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
        }
    }
}
