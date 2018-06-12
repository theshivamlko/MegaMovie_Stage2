package com.navoki.megamovies.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.navoki.megamovies.fragments.TrailerFragment;

import java.util.List;

/**
 * Created by Shivam Srivastava on 6/7/2018.
 */
public class TrailerPagerAdapter extends FragmentPagerAdapter {

    private List<String> list;

    public TrailerPagerAdapter(FragmentManager fm, List<String> list) {
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
