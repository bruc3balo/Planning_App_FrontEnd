package com.example.planningappfrontend.pagesFragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.planningappfrontend.MainActivity;
import com.example.planningappfrontend.R;

import static com.example.planningappfrontend.MainActivity.BOTTOM_NAVIGATION;
import static com.example.planningappfrontend.MainActivity.restartActivity;
import static com.example.planningappfrontend.MainActivity.restartApp;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private ListPreference layoutPreference;
    private PreferenceManager preferenceManager;

    private SharedPreferences defaultSharedPreference;

    public static final String LAYOUT_SETTING_KEY = "Layout";
    public static final String LAYOUT_PREFERENCE_CATEGORY_KEY = "Layout Preference Category";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        preferenceManager = getPreferenceManager();
        defaultSharedPreference = PreferenceManager.getDefaultSharedPreferences(requireContext());

    }






    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        defaultSharedPreference.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        defaultSharedPreference.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals(LAYOUT_SETTING_KEY)) {
            requireActivity().finish();
            startActivity(new Intent(requireContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        //restartActivity(requireActivity(),requireActivity().getIntent());
        //restartApp(requireContext());
    }




}