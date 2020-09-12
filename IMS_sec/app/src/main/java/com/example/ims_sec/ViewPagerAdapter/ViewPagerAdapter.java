package com.example.ims_sec.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragment = new ArrayList<>();
    private final List<String> Titles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragment.get(position);
    }

    @Override
    public int getCount() {
        return fragment.size();
    }

    public CharSequence getPageTitle(int position){
        return Titles.get(position);

    }

    public void AddFragment(Fragment fragments, String title) {
        fragment.add(fragments);
        Titles.add(title);
    }



}
