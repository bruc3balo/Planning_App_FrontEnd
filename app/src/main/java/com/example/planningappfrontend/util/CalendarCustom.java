package com.example.planningappfrontend.util;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.planningappfrontend.R;
import com.example.planningappfrontend.adapter.CalendarGridAdapter;
import com.example.planningappfrontend.models.Events;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarCustom extends LinearLayout {
    private int DAYS_COUNT = 0;
    int[] rainbow = new int[]{
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };

    int[] monthSeason = new int[]{2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};
    LinearLayout header;
    Button btnToday;
    ImageButton btnPrev;
    ImageButton btnNext;
    static TextView txtDateMonth,txtDisplayDate,txtDateYear;
    GridView gridView;
    public static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.ENGLISH);
    static SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    CalendarGridAdapter calendarGridAdapter;
    List<Date> dates = new ArrayList<>();
    List<Events> events = new ArrayList<>();

    public CalendarCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initControl(context, attrs);
    }

    public CalendarCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public CalendarCustom(Context context) {
        super(context);
        this.context = context;
        assignUiElements();
    }

    private void assignUiElements() {
        header = findViewById(R.id.calendar_header);
        btnPrev = findViewById(R.id.calendar_prev_button);
        btnNext = findViewById(R.id.calendar_next_button);

        txtDateMonth = findViewById(R.id.date_display_month);
        txtDateYear = findViewById(R.id.date_display_year);
        txtDisplayDate = findViewById(R.id.date_display_date);

        btnToday = findViewById(R.id.date_display_today);
        btnToday.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goToToday(Calendar.getInstance());
            }
        });
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { calendar.add(Calendar.MONTH, -1);setUpCalendar();setBlackText(); }
        });

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { calendar.add(Calendar.MONTH, 1);setUpCalendar();setBlackText(); }
        });
        gridView = findViewById(R.id.calendar_grid);
        setUpCalendar();
        setSeasonColor();
        goToToday(calendar);
    }

    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        inflater.inflate(R.layout.custom_calendar_day, this);
        assignUiElements();
        setRedText();
    }

    private void goToToday(Calendar calendar) {
        String currentDate = dateFormat.format(calendar.getTime());
        String currentMonth = monthFormat.format(calendar.getTime());
        String currentYear = yearFormat.format(calendar.getTime());

        txtDisplayDate.setText(currentDate);
        txtDateMonth.setText(currentMonth);
        txtDateYear.setText(currentYear);
        setRedText();

        dates.clear();

        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        calendarGridAdapter = new CalendarGridAdapter(context, dates, events);
        gridView.setAdapter(calendarGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(Color.LTGRAY);
            }
        });

    }

    private void setUpCalendar() {
        String currentDate = dateFormat.format(calendar.getTime());
        String currentMonth = monthFormat.format(calendar.getTime());
        String currentYear = yearFormat.format(calendar.getTime());

        txtDisplayDate.setText(currentDate);
        txtDateMonth.setText(currentMonth);
        txtDateYear.setText(currentYear);

        dates.clear();


        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        calendarGridAdapter = new CalendarGridAdapter(context, dates, events);
        gridView.setAdapter(calendarGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(Color.LTGRAY);
            }
        });

    }

    public static void receiveDates(Date date) {
        setCheckDate(date);
    }

    private static void setBlackText() {
        txtDateMonth.setTextColor(Color.BLACK);
        txtDateYear.setTextColor(Color.BLACK);
        txtDisplayDate.setTextColor(Color.BLACK);
    }

    private static void setRedText() {
        txtDateMonth.setTextColor(Color.RED);
        txtDateYear.setTextColor(Color.RED);
        txtDisplayDate.setTextColor(Color.RED);
    }

    private static void setCheckDate(Date date) {
        txtDateMonth.setText(monthFormat.format(date));
        txtDateYear.setText(yearFormat.format(date));
        txtDisplayDate.setText(dateFormat.format(date));
        setBlackText();
        Calendar todayDate = Calendar.getInstance();
        if (monthFormat.format(date).equals(monthFormat.format(todayDate.getTime())) && yearFormat.format(date).equals(yearFormat.format(todayDate.getTime())) && dateFormat.format(date).equals(dateFormat.format(todayDate.getTime()))) {
            setRedText();
        }
        System.out.println("Showing event" + dateFormat.format(date) + monthFormat.format(date) + yearFormat.format(date));
    }

    private void setSeasonColor() {
        int month = calendar.get(Calendar.MONTH);
        int season = monthSeason[month];
        int color = rainbow[season];

        header.setBackgroundColor(getResources().getColor(color));
    }

}
