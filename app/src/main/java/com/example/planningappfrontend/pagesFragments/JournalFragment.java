package com.example.planningappfrontend.pagesFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.planningappfrontend.R;

public class JournalFragment extends Fragment {

    public JournalFragment() {
        // Required empty public constructor
    }



    public static JournalFragment newInstance() {
        return new JournalFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_jorunal, container, false);

        return v;
    }
}