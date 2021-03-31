package com.example.planningappfrontend.pagesFragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.planningappfrontend.R;


public class ReminderFragment extends Fragment {

    private boolean monb = false,tueb = false,wedb = false,thurb = false,frib = false,satb = false,sunb = false;

    public ReminderFragment() {
        // Required empty public constructor
    }


    public static ReminderFragment newInstance() {
        return new ReminderFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_reminder, container, false);

        View reminderIndicator1 = v.findViewById(R.id.reminderIndicator1), reminderIndicator2 = v.findViewById(R.id.reminderIndicator2);
        TextView sun = v.findViewById(R.id.sunRv), mon = v.findViewById(R.id.monRv), tue = v.findViewById(R.id.tueRv), wed = v.findViewById(R.id.wedRv), thur = v.findViewById(R.id.thurRv), fri = v.findViewById(R.id.friRv), sat = v.findViewById(R.id.satRv);
        sun.setOnClickListener(v1 -> {
            sunb = !sunb;
            if (sunb) {
                sun.setBackground(getResources().getDrawable(R.drawable.circle_day_bg_selected));
                sun.setTextColor(Color.WHITE);
            } else {
                sun.setBackground(getResources().getDrawable(R.drawable.circle_day_bg));
                sun.setTextColor(Color.BLACK);
            }
        });
        mon.setOnClickListener(v2 ->{
            monb = !monb;
            if (sunb) {
                mon.setBackground(getResources().getDrawable(R.drawable.circle_day_bg_selected));
                mon.setTextColor(Color.WHITE);
            } else {
                mon.setBackground(getResources().getDrawable(R.drawable.circle_day_bg));
                mon.setTextColor(Color.BLACK);
            }
        });

        tue.setOnClickListener(v3 -> {
            tueb = !tueb;
            if (tueb) {
                tue.setBackground(getResources().getDrawable(R.drawable.circle_day_bg_selected));
                tue.setTextColor(Color.WHITE);
            } else {
                tue.setBackground(getResources().getDrawable(R.drawable.circle_day_bg));
                tue.setTextColor(Color.BLACK);
            }
        });
        wed.setOnClickListener(v4 -> {
            wedb = !wedb;
            if (tueb) {
                wed.setBackground(getResources().getDrawable(R.drawable.circle_day_bg_selected));
                wed.setTextColor(Color.WHITE);
            } else {
                wed.setBackground(getResources().getDrawable(R.drawable.circle_day_bg));
                wed.setTextColor(Color.BLACK);
            }
        });

        thur.setOnClickListener(v5 -> {
            thurb = !thurb;
            if (tueb) {
                thur.setBackground(getResources().getDrawable(R.drawable.circle_day_bg_selected));
                thur.setTextColor(Color.WHITE);
            } else {
                thur.setBackground(getResources().getDrawable(R.drawable.circle_day_bg));
                thur.setTextColor(Color.BLACK);
            }
        });

        fri.setOnClickListener(v6 -> {
            frib = !frib;
            if (tueb) {
                fri.setBackground(getResources().getDrawable(R.drawable.circle_day_bg_selected));
                fri.setTextColor(Color.WHITE);
            } else {
                fri.setBackground(getResources().getDrawable(R.drawable.circle_day_bg));
                fri.setTextColor(Color.BLACK);
            }
        });

        sat.setOnClickListener(v7 -> {
            satb = !satb;
            if (satb) {
                sat.setBackground(getResources().getDrawable(R.drawable.circle_day_bg_selected));
                sat.setTextColor(Color.WHITE);
            } else {
                sat.setBackground(getResources().getDrawable(R.drawable.circle_day_bg));
                sat.setTextColor(Color.BLACK);
            }
        });


        SwitchCompat reminderSwitch = v.findViewById(R.id.reminderSwitch);
        reminderSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                reminderIndicator1.setBackgroundColor(getResources().getColor(R.color.green));
                reminderIndicator2.setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                reminderIndicator1.setBackgroundColor(getResources().getColor(R.color.orange));
                reminderIndicator2.setBackgroundColor(getResources().getColor(R.color.orange));
            }
        });
        return v;
    }

    private void toggleDayTv(TextView tv,boolean dayTrue) {

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void toggleDay(TextView tv, boolean day) {
        day = !day;
        Drawable d = tv.getBackground();
        if (day) {
            tv.setBackground(null);
            tv.setBackground(getResources().getDrawable(R.drawable.circle_day_bg_selected));
            Toast.makeText(requireContext(), d.toString(), Toast.LENGTH_SHORT).show();
        } else {
            tv.setBackground(null);
            tv.setBackground(getResources().getDrawable(R.drawable.circle_day_bg));
            Toast.makeText(requireContext(), d.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}