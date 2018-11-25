package com.example.taras.testapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;


public class AdapterCollection extends FragmentPagerAdapter {

    private long baseId = 0;

    public AdapterCollection(FragmentManager fm) {
        super(fm);
    }
    private int mycounter = 1;


    public void addFragment(){
        this.mycounter += 1;
    }

    public void deleteFragment(){
        if(mycounter != 1) {
            this.mycounter -= 1;

        }
    }

    @Override
    public Fragment getItem(int position) {
        DemoFragment demoFragment = new DemoFragment();
        Bundle bundle = new Bundle();
        position = position+1;
        bundle.putString("message","" + position);
        demoFragment.setArguments(bundle);

        return demoFragment;
    }


    @Override
    public int getCount() {
        return mycounter;
    }

    @Override
    public long getItemId(int position) {
        // give an ID different from position when position has been changed
        return baseId + position;
    }

    @Override
    public int getItemPosition(Object object) {
        // refresh all fragments when data set changed
        return PagerAdapter.POSITION_NONE;
    }

    public void notifyChangeInPosition(int n) {
        // shift the ID returned by getItemId outside the range of all previous fragments
        baseId += getCount() + n;
    }



}

