package com.example.planningappfrontend.pagesFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.planningappfrontend.R;


public class GoalsFragment extends Fragment {

    public GoalsFragment() {
        // Required empty public constructor
    }


    public static GoalsFragment newInstance() {
       return new GoalsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.fragment_goals, container, false);

        TextView goalTitle = v.findViewById(R.id.goalTitle);
        ScrollView goalDetails = v.findViewById(R.id.goalDetailsScrollView);
        goalTitle.setOnClickListener(v1 -> {
            if (goalDetails.getVisibility() == View.GONE) {
                goalDetails.setVisibility(View.VISIBLE);
            } else {
                goalDetails.setVisibility(View.GONE);
            }
        });


        return v;
    }
}