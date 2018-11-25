package com.example.taras.testapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class AdapterCollection extends FragmentStatePagerAdapter {

    private ArrayList<Integer> fragmentCollection = new ArrayList<Integer>();
    private int numberOfFragments = 1;

    public AdapterCollection(FragmentManager fm) {
        super(fm);
    }

    public int getPagePosition(int pageNumber)
    {
        int pagePosition = 0;
        for(int i = 0; i < numberOfFragments; i++){
            if(fragmentCollection.get(i) == pageNumber){
                pagePosition = i;
                break;
            }
        }
        return  pagePosition;
    }

    public void initializeCollection()
    {
        fragmentCollection.add(1);
        numberOfFragments = fragmentCollection.size();
    }

    public int getPageNumber(int fragmentNumber){return fragmentCollection.get(fragmentNumber);}

    public void addFragment(){
        fragmentCollection.add(fragmentCollection.get(numberOfFragments -1) + 1);
        numberOfFragments = fragmentCollection.size();
    }

    public void deleteFragment(int fragmentNumber){
        if(fragmentNumber != 0) {
            fragmentCollection.remove(fragmentNumber);
            numberOfFragments = fragmentCollection.size();
        }
    }

    @Override
    public Fragment getItem(int position) {
        DemoFragment demoFragment = new DemoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message","" + fragmentCollection.get(position));
        demoFragment.setArguments(bundle);
        return demoFragment;
    }

    @Override
    public int getCount() {return numberOfFragments;}

    @Override
    public int getItemPosition(Object object) { return  POSITION_NONE; }




}

