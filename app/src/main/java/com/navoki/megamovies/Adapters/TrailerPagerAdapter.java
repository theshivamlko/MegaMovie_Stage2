package com.navoki.megamovies.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.navoki.megamovies.Fragments.TrailerFragment;

import java.util.ArrayList;

/**
 * Created by Shivam Srivastava on 6/7/2018.
 */
public class TrailerPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> list;

    public TrailerPagerAdapter(FragmentManager fm, ArrayList<String> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return TrailerFragment.newInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
