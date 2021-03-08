package com.example.protabler.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.protabler.Fragment.fridayFragment;
import com.example.protabler.Fragment.mondayFragment;
import com.example.protabler.Fragment.saturdayFragment;
import com.example.protabler.Fragment.thursdayFragment;
import com.example.protabler.Fragment.tuesdayFragment;
import com.example.protabler.Fragment.wednesdayFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    public PagerAdapter(FragmentManager fm, int numOfTabs) {

        super(fm);
        this.numOfTabs=numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new mondayFragment();

            case 1:
                return new tuesdayFragment();

            case 2:
                return new wednesdayFragment();

            case 3:
                return new thursdayFragment();

            case 4:
                return new fridayFragment();

            case 5:
                return new saturdayFragment();

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
