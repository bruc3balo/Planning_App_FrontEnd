package com.example.planningappfrontend.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.planningappfrontend.R;
import com.example.planningappfrontend.models.Events;
import com.example.planningappfrontend.util.CalendarCustom;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarGridAdapter extends ArrayAdapter<Date> {
    private final LayoutInflater inflater;
    private final List<Date> dates;
    private final List<Events> events;
    Calendar currentDate = Calendar.getInstance(Locale.ENGLISH);
    int count = 0;
    Calendar selectedDate;
    View selectedPositionView;


    public CalendarGridAdapter(Context context, List<Date> dates, List<Events> events, Calendar selectedDate) {
        super(context, R.layout.custom_calendar_day);
        this.dates = dates;
        this.events = events;
        this.selectedDate = selectedDate;
        inflater = LayoutInflater.from(context);
    }

    @SuppressLint({"InflateParams", "UseCompatLoadingForDrawables"})
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Date monthDate = dates.get(position);
        final Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);

        int DayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int displayDate = dateCalendar.get(Calendar.DATE);
        int displayDay = dateCalendar.get(Calendar.DAY_OF_WEEK);

        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);

        final int currentDay = currentDate.get(Calendar.DAY_OF_WEEK);
        final int currentDateN = currentDate.get(Calendar.DATE);

        TextView dayNumber = null;
        TextView grid_date_event_count = null;
        LinearLayout eventNumberContainer = null;

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.grid_row_cal, null);
            dayNumber = view.findViewById(R.id.grid_date);
            grid_date_event_count = view.findViewById(R.id.grid_date_event_count);
            eventNumberContainer = view.findViewById(R.id.eventNumberContainer);
        }

        view.setBackgroundColor(Color.WHITE);



        setCalendarCurrentMonthUi(currentYear == displayYear, displayMonth == currentMonth, dateCalendar.get(Calendar.DATE) == currentDate.get(Calendar.DATE), currentDate.get(Calendar.DAY_OF_WEEK) == dateCalendar.get(Calendar.DAY_OF_WEEK), view);

        if (dayNumber != null) {
            setTodayUi(displayMonth == currentMonth, displayYear == currentYear, dayNumber);
            dayNumber.setText(String.valueOf(DayNo));
            dayNumber.setOnClickListener(v -> clickDate(dateCalendar, (TextView) v));
            String substring = String.valueOf(position).substring(String.valueOf(position).length() - 1);
           ifEvenShow(substring,eventNumberContainer);
            if (selectedDate != null) {
                if (selectedDate.getTime().equals(monthDate)) {
                    setSelectedPositionView(view);
                     view.setBackgroundColor(Color.WHITE);
                     dayNumber.setTextColor(Color.WHITE);
                     dayNumber.setBackground(getContext().getResources().getDrawable(R.drawable.circle_day_bg_selected));
                    System.out.println("selected date for view is "+selectedDate.getTime() +" for position : "+position);


                }
            }
        }

        return view;
    }

    private void ifEvenShow (String substring, ViewGroup viewGroup) {
        if (substring.equals(String.valueOf(2)) || substring.equals("4")  || substring.equals("6") || substring.equals("8") || substring.equals("0")) {
            viewGroup.setVisibility(View.GONE);
        } else {
            viewGroup.setVisibility(View.VISIBLE);
        }
    }

    private void clickDate(Calendar dateCalendar, TextView textView) {
        CalendarCustom.receiveDates(dateCalendar, getContext());
        textView.setTextColor(Color.RED);
        textView.setBackgroundColor(Color.DKGRAY);
    }


    public View getSelectedPositionView() {
        return selectedPositionView;
    }

    public void setSelectedPositionView(View selectedPositionView) {
        this.selectedPositionView = selectedPositionView;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setCalendarCurrentMonthUi(boolean sameYear, boolean sameMonth, boolean sameDate, boolean sameDay, View view) {
        if (sameYear && sameMonth && sameDate && sameDay) { //today
            view.setBackgroundColor(Color.TRANSPARENT);
            count++;
            System.out.println(count + " counting");
        } else {
            view.setBackgroundColor(Color.WHITE);
        }
    }

    private void setTodayUi(boolean sameMonth, boolean sameYear, TextView dayNumber) {
        if (sameMonth && sameYear) {
            try {
                dayNumber.setTextColor(Color.BLACK);
            } catch (Exception ignored) {

            }
        } else {
            try {
                dayNumber.setTextColor(Color.LTGRAY);
            } catch (Exception ignored) {

            }
        }
    }

    private void set() {

    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Date item) {
        return dates.indexOf(item);
    }

}