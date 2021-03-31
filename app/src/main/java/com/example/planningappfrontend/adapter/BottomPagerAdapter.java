package com.example.planningappfrontend.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;

import static com.example.planningappfrontend.MainActivity.pageIcons;
import static com.example.planningappfrontend.MainActivity.pageTitles;
import static com.example.planningappfrontend.pagesActivities.ActivityFragmentHolder.getFragmentFromPosition;

public class BottomPagerAdapter extends FragmentPagerAdapter {



    public BottomPagerAdapter(@NonNull FragmentManager fm, int behavior ) {
        super(fm, behavior);

    }


    @Override
    public int getCount() {
        return pageIcons.length;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    public String[] getTitles() {
        return pageTitles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    public void setAllTabIcons(int[] tabIcons, TabLayout tab) {
        for (int i = 0; i <= tabIcons.length - 1; i++) {
            tab.getTabAt(i).setIcon(tabIcons[i]);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return getFragmentFromPosition(position);


    }
}


