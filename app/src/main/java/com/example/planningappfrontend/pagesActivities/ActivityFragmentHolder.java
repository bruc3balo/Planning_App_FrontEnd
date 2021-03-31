package com.example.planningappfrontend.pagesActivities;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.planningappfrontend.R;
import com.example.planningappfrontend.pagesFragments.GoalsFragment;
import com.example.planningappfrontend.pagesFragments.JournalFragment;
import com.example.planningappfrontend.pagesFragments.NotesFragment;
import com.example.planningappfrontend.pagesFragments.ReminderFragment;
import com.example.planningappfrontend.pagesFragments.SettingsFragment;
import com.example.planningappfrontend.pagesFragments.SummaryFragment;
import com.example.planningappfrontend.pagesFragments.TimetableFragment;
import com.example.planningappfrontend.pagesFragments.TodoFragment;

public class ActivityFragmentHolder extends AppCompatActivity {
    private FrameLayout activityFragmentHolder;
    private FragmentManager fragmentManager;
    private Fragment fragment;

    public static final String CHOSEN_FRAGMENT = "Chosen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        activityFragmentHolder = findViewById(R.id.activityFragmentHolder);
        fragmentManager = getSupportFragmentManager();

        Bundle extra =  getIntent().getExtras();
        if (extra != null) {
            fragment = getFragmentFromPosition(extra.getInt(CHOSEN_FRAGMENT));
            fragmentManager.beginTransaction().add(activityFragmentHolder.getId(),getFragmentFromPosition(extra.getInt(CHOSEN_FRAGMENT))).commit();
        }

    }

    public static Fragment getFragmentFromPosition (int position) {
        Fragment fragment;
        switch (position) {
            default: case 0:
                fragment = new JournalFragment();
                break;
            case 1:

                fragment = new GoalsFragment();
                break;

            case 2:
                fragment = new NotesFragment();
                break;

            case 3:
                fragment = new ReminderFragment();
                break;

            case 4:
                fragment = new TimetableFragment();
                break;

            case 5:
                fragment = new TodoFragment();
                break;

            case 6:
                fragment = new SummaryFragment();
                break;

            case 7:
                fragment = new SettingsFragment();
                break;
        }
        return fragment;
    }
}